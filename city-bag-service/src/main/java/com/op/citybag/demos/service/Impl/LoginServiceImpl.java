package com.op.citybag.demos.service.Impl;

import cn.hutool.crypto.digest.BCrypt;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.op.citybag.demos.exception.AppException;
import com.op.citybag.demos.mapper.AuthMapper;
import com.op.citybag.demos.mapper.UserMapper;
import com.op.citybag.demos.model.Entity.Auth;
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

    @Autowired
    private AuthMapper authMapper;

    @Override
    public LoginVO wxLogin(String openid, String phoneNumber) {
        //查询用户认证关联表
        QueryWrapper<Auth> wxQueryWrapper = new QueryWrapper<>();
        wxQueryWrapper.eq(Common.AUTH_TYPE, 1)
                .eq(Common.AUTH_KEY, openid)
                .eq(Common.TABLE_LOGIC, Common.NOT_DELETE);

        QueryWrapper<Auth> otherQueryWrapper = new QueryWrapper<>();
        otherQueryWrapper.eq(Common.PHONE_NUMBER, phoneNumber)
                .eq(Common.TABLE_LOGIC, Common.NOT_DELETE);

        User user = null;
        if (authMapper.selectOne(wxQueryWrapper) == null) {
            if (authMapper.selectOne(otherQueryWrapper) == null) {
                log.info("用户不存在");
                user = createUser(0, openid, null, phoneNumber);
            } else {
                log.info("用户已存在,正在绑定微信");
                user = bindAuth(authMapper.selectOne(otherQueryWrapper).getUserId(), 1, openid, null, phoneNumber);
            }
        }

        String token = createToken(user);

        LoginVO loginVO = Entity2VO.User2LoginVO(user);
        loginVO.setAccessToken(token);

        return loginVO;
    }

    @Override
    public LoginVO stuLogin(String stuId, String password, String phoneNumber) {

        //查询用户认证关联表
        QueryWrapper<Auth> stuQueryWrapper = new QueryWrapper<>();
        stuQueryWrapper.eq(Common.AUTH_TYPE, 1)
                .eq(Common.AUTH_KEY, stuId)
                .eq(Common.TABLE_LOGIC, Common.NOT_DELETE);

        QueryWrapper<Auth> otherQueryWrapper = new QueryWrapper<>();
        otherQueryWrapper.eq(Common.PHONE_NUMBER, phoneNumber)
                .eq(Common.TABLE_LOGIC, Common.NOT_DELETE);

        User user = null;
        if (authMapper.selectOne(stuQueryWrapper) == null) {
            if (authMapper.selectOne(otherQueryWrapper) == null) {
                log.info("用户不存在");
                user = createUser(0, stuId, password, phoneNumber);
            } else {
                log.info("用户已存在,正在绑定学号");
                user = bindAuth(authMapper.selectOne(otherQueryWrapper).getUserId(), 1, stuId, password, phoneNumber);
            }
        }

        if (!user.getPassword().equals(password)) {
            log.info("密码错误");
            throw new AppException(String.valueOf(GlobalServiceStatusCode.USER_CREDENTIALS_ERROR.getCode()), GlobalServiceStatusCode.USER_CREDENTIALS_ERROR.getMessage());
        }

        String token = createToken(user);

        LoginVO loginVO = Entity2VO.User2LoginVO(user);
        loginVO.setAccessToken(token);

        return loginVO;

    }

    private User bindAuth(String userId, int i, String authKey, String authSecret, String phone) {
        User user = userMapper.selectById(userId);
        if (user == null) {
            log.info("用户不存在");
            throw new AppException(String.valueOf(GlobalServiceStatusCode.USER_ACCOUNT_NOT_EXIST.getCode()), GlobalServiceStatusCode.USER_ACCOUNT_NOT_EXIST.getMessage());
        }
        Auth auth = Auth.builder()
              .authType(i)
              .authKey(authKey)
              .authSecret(authSecret)
              .userId(userId)
              .phone(phone)
              .build();
        authMapper.insert(auth);
        return user;
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
     * 创建新用户（支持多认证方式）
     *
     * @param authType   认证类型 1-学号 2-微信
     * @param authKey    认证标识（学号/openid）
     * @param authSecret 认证凭证（密码/unionid）
     */
    private User createUser(Integer authType, String authKey, String authSecret, String phoneNumber) {

        //查询用户
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(Common.PHONE_NUMBER, phoneNumber)
                .eq(Common.TABLE_LOGIC, Common.NOT_DELETE);
        User user = userMapper.selectOne(queryWrapper);

        if (user == null) {

            log.info("正在注册,登陆类型:{} ,key:{},secret:{},phoneNumber:{}", authType, authKey, authSecret, phoneNumber);

            //预先创建用户
            user = preCreateUser(authType, authKey, authSecret, phoneNumber);
            //预先创建认证关联
            Auth auth = preCreateAuth(authType, authKey, authSecret, phoneNumber, user.getUserId());
            // 预先创建认证关联查询
            QueryWrapper<Auth> phoneNumberCheck = new QueryWrapper<Auth>().eq(Common.PHONE_NUMBER, phoneNumber);

            //创建用户
            String key = RedisKey.NEW_USER_LOCK + phoneNumber;
            RLock lock = null;
            try {
                lock = redissonService.getLock(key);

                lock.tryLock(Common.TRY_LOCK_TIME, TimeUnit.SECONDS);
                if (authMapper.selectOne(phoneNumberCheck) != null) {
                    log.info("手机号已被注册");
                    throw new AppException(String.valueOf(GlobalServiceStatusCode.USER_ACCOUNT_EXIST.getCode()), GlobalServiceStatusCode.USER_ACCOUNT_EXIST.getMessage());
                }
                // 真正创建用户
                if (userMapper.selectOne(queryWrapper) == null) {
                    userMapper.insert(user);
                }
                // 真正创建认证关联
                if (authMapper.selectOne(phoneNumberCheck) == null) {
                    authMapper.insert(auth);
                }
            } catch (Exception e) {
                log.info("注册失败,phoneNumber:{}", phoneNumber);
                throw new AppException(String.valueOf(GlobalServiceStatusCode.USER_ACCOUNT_REGISTER_ERROR.getCode()), GlobalServiceStatusCode.USER_ACCOUNT_REGISTER_ERROR.getMessage());
            } finally {
                redissonService.unLock(lock);
            }

            log.info("注册成功,phoneNumber:{}", phoneNumber);
        } else {
            //老用户,已经查询出用户信息
            log.info("用户已存在,phoneNumber:{}", phoneNumber);
        }
        return user;
    }

    /**
     * 预先创建用户
     */
    private User preCreateUser(Integer authType, String authKey, String authSecret, String phoneNumber) {
        //预先创建用户
        User user = User.builder()
                .userId(SnowflakeIdWorker.nextIdStr())
                .phone(phoneNumber)
                .userName("op")
                .personalizedSignature("欢迎登陆CityBag")
                .build();

        // 根据认证类型设置不同字段
        if (authType == 1) { // 学号认证
            user.setStuId(authKey);
            user.setPassword(BCrypt.hashpw(authSecret, BCrypt.gensalt(66)));//BCrypt加密算法
        } else if (authType == 2) { // 微信认证
            user.setOpenid(authKey);
            user.setUnionid(authSecret); // 这里authSecret实际存unionId
        }

        return user;
    }

    /**
     * 预先创建认证关联
     */
    private Auth preCreateAuth(Integer authType, String authKey, String authSecret, String phoneNumber, String userId) {

        return Auth.builder()
                .userId(userId)
                .phone(phoneNumber)
                .authType(authType)
                .authKey(authKey)
                .authSecret(authSecret)
                .build();

    }

    private void updatePassword(String userId, String newPassword) {
        // 创建 UpdateWrapper 对象
        UpdateWrapper<User> userUpdateWrapper = new UpdateWrapper<>();
        UpdateWrapper<Auth> authUpdateWrapper = new UpdateWrapper<>();
        // 设置更新条件
        userUpdateWrapper.eq(Common.USER_ID, userId);
        authUpdateWrapper.eq(Common.USER_ID, userId);
        // 设置要更新的字段和值
        userUpdateWrapper.set(Common.PASSWORD, newPassword);
        authUpdateWrapper.set(Common.PASSWORD, newPassword);
        // 执行更新操作
        int rowsAffected = userMapper.update(null, userUpdateWrapper)
                + authMapper.update(null, authUpdateWrapper);

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

        String token = TokenUtil.getAccessToken(user.getUserId());
        String key = RedisKey.ACCESS_TOKEN + token;

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
            redissonService.remove(RedisKey.ACCESS_TOKEN + token);
        }
        redissonService.remove(RedisKey.USER_TO_TOKEN + userId);
    }

}
