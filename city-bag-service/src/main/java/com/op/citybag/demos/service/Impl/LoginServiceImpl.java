package com.op.citybag.demos.service.Impl;

import cn.hutool.jwt.JWT;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.op.citybag.demos.mapper.UserMapper;
import com.op.citybag.demos.model.Entity.User;
import com.op.citybag.demos.model.RedisKey;
import com.op.citybag.demos.model.VO.LoginVO;
import com.op.citybag.demos.model.common.Common;
import com.op.citybag.demos.redis.RedissonService;
import com.op.citybag.demos.service.ILoginService;
import com.op.citybag.demos.utils.SnowflakeIdGenerator;
import com.op.citybag.demos.utils.SnowflakeIdWorker;
import com.op.citybag.demos.utils.TokenUtil;
import io.micrometer.core.instrument.util.TimeUtils;
import jodd.time.TimeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

/**
 * @Author: 原神
 * @Description:
 * @Date: 2025/1/18 20:00
 * @Version: 1.0
 */

@Service
@Slf4j
public class LoginServiceImpl implements ILoginService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RedissonService redissonService;


    @Override
    public LoginVO login(String openid, String phoneNumber) {

        //查询用户
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(Common.OPENID, openid).eq(Common.TABLE_LOGIC, Common.NOT_DELETE);
        User user = userMapper.selectOne(queryWrapper);

        if(user == null){
            log.info("正在注册,openid:{},phoneNumber:{}",openid,phoneNumber);
            user=User.builder()
                    .userId(SnowflakeIdWorker.nextIdStr())
                    .phone(phoneNumber)
                    .openid(openid)
                    .userName("op")
                    .build();
            //创建用户
            try {
                redissonService.getLock(RedisKey.NEW_USER_LOCK+openid).tryLock(Common.TRY_LOCK_TIME, TimeUnit.SECONDS);
                if(userMapper.selectOne(queryWrapper) == null) {
                    userMapper.insert(user);
                }
            } catch (Exception e) {
                log.info("注册失败,openid:{},phoneNumber:{}",openid,phoneNumber);
                throw new RuntimeException(e);
            } finally {
                redissonService.unLock(RedisKey.NEW_USER_LOCK+openid);
            }

            log.info("注册成功,openid:{},phoneNumber:{}",openid,phoneNumber);
        }else {
            //老用户,已经查询出用户信息

        }

        clearToken(user.getUserId());

        //生成token 存储token
        String token = TokenUtil.getAccessToken(user.getUserId(), phoneNumber);
        String key = RedisKey.TOKEN+token;
        redissonService.addToMap(key,Common.OPENID,user.getOpenid());
        redissonService.addToMap(key,Common.TABLE_LOGIC,String.valueOf(Common.NOT_DELETE));
        redissonService.addToMap(key,Common.USER_ID,user.getUserId());
        redissonService.setMapExpired(key,Common.MONTH);

        redissonService.setValue(RedisKey.USER_TO_TOKEN+user.getUserId(),token);
        redissonService.setValueExpired(RedisKey.USER_TO_TOKEN+user.getUserId(),Common.MONTH);

        LoginVO loginVO = User2LoginVO(user);
        loginVO.setAccessToken(token);

        return loginVO;
    }

    @Override
    public void logout(String userId) {
        log.info("正在退出登录,userId:{}",userId);
        clearToken(userId);
        log.info("退出登录成功,userId:{}",userId);
    }

    private void clearToken(String userId) {
        String token = redissonService.getValue(RedisKey.USER_TO_TOKEN + userId);
        if(token != null){
            redissonService.remove(RedisKey.TOKEN+token);
        }
        redissonService.remove(RedisKey.USER_TO_TOKEN+userId);
    }

    private LoginVO User2LoginVO(User user) {
        return LoginVO.builder()
                .userId(user.getUserId())
                .phone(user.getPhone())
                .userName(user.getUserName())
                .gender(user.getGender())
                .birthday(user.getBirthday())
                .personalizedSignature(user.getPersonalizedSignature())
                .jurisdiction(user.getJurisdiction())
                .likeCount(user.getLikeCount())
                .updateTime(user.getUpdateTime())
                .build();
    }
}
