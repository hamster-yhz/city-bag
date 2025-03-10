package com.op.citybag.demos.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.op.citybag.demos.exception.AppException;
import com.op.citybag.demos.mapper.UserMapper;
import com.op.citybag.demos.model.Entity.User;
import com.op.citybag.demos.model.VO.login.LoginVO;
import com.op.citybag.demos.model.common.Common;
import com.op.citybag.demos.model.common.GlobalServiceStatusCode;
import com.op.citybag.demos.model.common.RedisKey;
import com.op.citybag.demos.redis.RedissonService;
import com.op.citybag.demos.service.INewLoginService;
import com.op.citybag.demos.utils.Entity2VO;
import com.op.citybag.demos.utils.SnowflakeIdWorker;
import com.op.citybag.demos.utils.TokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.TimeUnit;

/**
 * @Author: 原神
 * @Description:
 * @Date: 2025/3/7 18:52
 * @Version: 1.0
 */

@Service
@Slf4j
@Transactional
public class NewLoginServiceImpl implements INewLoginService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RedissonService redissonService;

    @Override
    public LoginVO wxLogin(String openid) {

        //进来先查询用户是否存在
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(Common.AUTH_TYPE, Common.WX_USER)
                .eq(Common.AUTH_KEY, openid)
                .eq(Common.TABLE_LOGIC, Common.NOT_DELETE);

        User user = userMapper.selectOne(queryWrapper);

        if (user == null) {
            log.info("用户不存在,正在注册");
            user = createUser(Common.WX_USER, openid, null);
        } else {
            log.info("用户已存在,userId:{}", user.getUserId());
        }

        //生成token
        String token = createToken(user);

        LoginVO loginVO = Entity2VO.User2LoginVO(user);
        loginVO.setAccessToken(token);

        return loginVO;
    }

    @Override
    public LoginVO stuLogin(String stuId, String password) {

        //进来先查询用户是否存在
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(Common.AUTH_TYPE, Common.STU_USER)
                .eq(Common.AUTH_KEY, stuId)
                .eq(Common.TABLE_LOGIC, Common.NOT_DELETE);

        User user = userMapper.selectOne(queryWrapper);

        if (user == null) {
            log.info("用户不存在,正在注册");
            user = createUser(Common.STU_USER, stuId, password);
        } else {
            log.info("用户已存在,userId:{}", user.getUserId());
        }

        if (!user.getAuthSecret().equals(password)) {
            log.info("密码错误,userId:{}", user.getUserId());
            throw new AppException(String.valueOf(GlobalServiceStatusCode.USER_CREDENTIALS_ERROR.getCode()), GlobalServiceStatusCode.USER_CREDENTIALS_ERROR.getMessage());
        }

        //生成token
        String token = createToken(user);

        LoginVO loginVO = Entity2VO.User2LoginVO(user);
        loginVO.setAccessToken(token);

        return loginVO;

    }

    @Override
    public void stuChangePassword(String stuId, String password, String newPassword) {

        //查询用户
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(Common.AUTH_TYPE, Common.STU_USER)
                .eq(Common.AUTH_KEY, stuId)
                .eq(Common.TABLE_LOGIC, Common.NOT_DELETE);

        User user = userMapper.selectOne(queryWrapper);

        log.info("正在尝试修改用户密码,userId: {}", user.getUserId());

        if (user == null) {
            log.info("用户不存在");
            throw new AppException(String.valueOf(GlobalServiceStatusCode.USER_ACCOUNT_NOT_EXIST.getCode()), GlobalServiceStatusCode.USER_ACCOUNT_NOT_EXIST.getMessage());
        } else if (!user.getAuthSecret().equals(password)) {
            log.info("密码错误,userId:{}", user.getUserId());
            throw new AppException(String.valueOf(GlobalServiceStatusCode.USER_CREDENTIALS_ERROR.getCode()), GlobalServiceStatusCode.USER_CREDENTIALS_ERROR.getMessage());
        } else {
            updatePassword(user.getUserId(), newPassword);
            log.info("密码修改成功,userId: {}", user.getUserId());
        }

    }

    @Override
    public void logout(String userId) {
        log.info("正在退出登录,userId:{}", userId);
        clearToken(userId);
        log.info("退出登录成功,userId:{}", userId);
    }

    /**
     * 创建新用户（支持多认证方式）
     *
     * @param authType   认证类型 1-学号 2-微信
     * @param authKey    认证标识（学号/openid）
     * @param authSecret 认证凭证（密码/unionid）
     */
    private User createUser(Integer authType, String authKey, String authSecret) {

        //查询用户
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(Common.AUTH_TYPE, authType)
                .eq(Common.AUTH_KEY, authKey)
                .eq(Common.TABLE_LOGIC, Common.NOT_DELETE);

        log.info("正在注册,登陆类型:{} ,key:{}", authType, authKey);

        //预先创建用户
        User user = User.builder()
                .userId(SnowflakeIdWorker.nextIdStr())
                .userName("user")
                .authType(authType)
                .authKey(authKey)
                .authSecret(authSecret)
                .gender(1)
                .personalizedSignature("欢迎登陆CityBag")
                .build();


        //创建用户
        String key = RedisKey.NEW_USER_LOCK + authType.toString() + ":" + authKey;
        RLock lock = null;
        try {
            lock = redissonService.getLock(key);

            lock.tryLock(Common.TRY_LOCK_TIME, TimeUnit.SECONDS);
            User checkUserExit = userMapper.selectOne(queryWrapper);
            if (checkUserExit != null) {
                log.info("用户已存在，userId:{}", checkUserExit.getUserId());
                return checkUserExit;
            }
            // 真正创建用户
            userMapper.insert(user);
        } catch (Exception e) {
            log.info("注册失败");
            throw new AppException(String.valueOf(GlobalServiceStatusCode.USER_ACCOUNT_REGISTER_ERROR.getCode()), GlobalServiceStatusCode.USER_ACCOUNT_REGISTER_ERROR.getMessage());
        } finally {
            redissonService.unLock(lock);
        }

        log.info("注册成功,userId:{}", user.getUserId());

        return user;
    }


    private void updatePassword(String userId, String newPassword) {

        // 创建 UpdateWrapper 对象
        UpdateWrapper<User> userUpdateWrapper = new UpdateWrapper<>();

        // 设置更新条件
        userUpdateWrapper.eq(Common.USER_ID, userId);

        // 设置要更新的字段和值
        userUpdateWrapper.set(Common.PASSWORD, newPassword);

        // 执行更新操作
        int rowsAffected = userMapper.update(null, userUpdateWrapper);

        if (rowsAffected > 0) {
            log.info("密码更新成功，受影响的行数: {}", rowsAffected);
        } else {
            log.info("密码更新失败，未找到匹配的用户或更新操作未执行");
        }
    }

    /**
     * 创建token
     */
    private String createToken(User user) {

        clearToken(user.getUserId());

        String token = TokenUtil.getAccessToken(user.getUserId(), user.getPhone());
        String key = RedisKey.TOKEN + token;

        redissonService.addToMap(key, Common.TABLE_LOGIC, String.valueOf(Common.NOT_DELETE));
        redissonService.addToMap(key, Common.USER_ID, user.getUserId());
        redissonService.setMapExpired(key, Common.MONTH);

        redissonService.setValue(RedisKey.USER_TO_TOKEN + user.getUserId(), token);
        redissonService.setValueExpired(RedisKey.USER_TO_TOKEN + user.getUserId(), Common.MONTH);

        return token;
    }

    /**
     * 清除token
     */
    private void clearToken(String userId) {
        String token = redissonService.getValue(RedisKey.USER_TO_TOKEN + userId);
        if (token != null) {
            redissonService.remove(RedisKey.TOKEN + token);
        }
        redissonService.remove(RedisKey.USER_TO_TOKEN + userId);
    }

}
