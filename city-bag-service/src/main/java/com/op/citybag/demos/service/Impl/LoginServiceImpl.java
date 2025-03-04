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
import com.op.citybag.demos.service.ILoginService;
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
 * @Description: 登陆服务实现类
 * @Date: 2025/1/18 20:00
 * @Version: 1.0
 */

@Service
@Slf4j
@Transactional
public class LoginServiceImpl implements ILoginService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RedissonService redissonService;

    @Override
    public LoginVO wxLogin(String openid, String phoneNumber) {

        //查询用户
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(Common.OPENID, openid).eq(Common.TABLE_LOGIC, Common.NOT_DELETE);
        User user = userMapper.selectOne(queryWrapper);

        user = createWxUser(user, openid, phoneNumber, queryWrapper);

        String token = createToken(user);

        LoginVO loginVO = Entity2VO.User2LoginVO(user);
        loginVO.setAccessToken(token);

        return loginVO;
    }

    @Override
    public LoginVO stuLogin(String stuId, String password) {
        //查询用户
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(Common.OPENID, stuId).eq(Common.TABLE_LOGIC, Common.NOT_DELETE);
        User user = userMapper.selectOne(queryWrapper);

        if (user == null) {
            throw new AppException(String.valueOf(GlobalServiceStatusCode.USER_ACCOUNT_NOT_EXIST.getCode()), GlobalServiceStatusCode.USER_ACCOUNT_NOT_EXIST.getMessage());
        } else if (!user.getPassword().equals(password)) {
            throw new AppException(String.valueOf(GlobalServiceStatusCode.USER_CREDENTIALS_ERROR.getCode()), GlobalServiceStatusCode.USER_CREDENTIALS_ERROR.getMessage());
        } else {
            String token = createToken(user);

            LoginVO loginVO = Entity2VO.User2LoginVO(user);
            loginVO.setAccessToken(token);

            return loginVO;
        }

    }

    @Override
    public void stuChangePassword(String stuId, String password, String newPassword) {

        //查询用户
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(Common.OPENID, stuId).eq(Common.TABLE_LOGIC, Common.NOT_DELETE);
        User user = userMapper.selectOne(queryWrapper);

        log.info("正在修改用户密码,userId: {}", user.getUserId());

        if (user == null) {
            throw new AppException(String.valueOf(GlobalServiceStatusCode.USER_ACCOUNT_NOT_EXIST.getCode()), GlobalServiceStatusCode.USER_ACCOUNT_NOT_EXIST.getMessage());
        } else if (!user.getPassword().equals(password)) {
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
     * 创建新用户
     *
     * @param user
     * @param openid
     * @param phoneNumber
     * @param queryWrapper
     */
    private User createWxUser(User user, String openid, String phoneNumber, QueryWrapper<User> queryWrapper) {
        if (user == null) {
            log.info("正在注册,openid:{},phoneNumber:{}", openid, phoneNumber);
            user = User.builder()
                    .userId(SnowflakeIdWorker.nextIdStr())
                    .phone(phoneNumber)
                    .openid(openid)
                    .userName("op")
                    .build();
            //创建用户

            String key = RedisKey.NEW_USER_LOCK + openid;
            RLock lock = null;
            try {
                lock = redissonService.getLock(key + openid);

                lock.tryLock(Common.TRY_LOCK_TIME, TimeUnit.SECONDS);
                if (userMapper.selectOne(queryWrapper) == null) {
                    userMapper.insert(user);
                }
            } catch (Exception e) {
                log.info("注册失败,openid:{},phoneNumber:{}", openid, phoneNumber);
                throw new AppException(String.valueOf(GlobalServiceStatusCode.USER_ACCOUNT_REGISTER_ERROR.getCode()), GlobalServiceStatusCode.USER_ACCOUNT_REGISTER_ERROR.getMessage());
            } finally {
                redissonService.unLock(lock);
            }

            log.info("注册成功,openid:{},phoneNumber:{}", openid, phoneNumber);
        } else {
            //老用户,已经查询出用户信息
            log.info("用户已存在,openid:{},phoneNumber:{}", openid, phoneNumber);
        }
        return user;
    }

    private void updatePassword(String userId, String newPassword) {
        // 创建 UpdateWrapper 对象
        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
        // 设置更新条件
        updateWrapper.eq(Common.USER_ID, userId);
        // 设置要更新的字段和值
        updateWrapper.set(Common.PASSWORD, newPassword);
        // 执行更新操作
        int rowsAffected = userMapper.update(null, updateWrapper);

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
