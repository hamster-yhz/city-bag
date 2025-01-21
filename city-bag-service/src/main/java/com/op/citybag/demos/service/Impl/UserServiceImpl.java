package com.op.citybag.demos.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.op.citybag.demos.exception.AppException;
import com.op.citybag.demos.mapper.UserMapper;
import com.op.citybag.demos.model.Entity.User;
import com.op.citybag.demos.model.RedisKey;
import com.op.citybag.demos.model.VO.LoginVO;
import com.op.citybag.demos.model.common.Common;
import com.op.citybag.demos.model.common.GlobalServiceStatusCode;
import com.op.citybag.demos.redis.RedissonService;
import com.op.citybag.demos.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: 原神
 * @Description:
 * @Date: 2025/1/20 14:56
 * @Version: 1.0
 */

@Service
@Slf4j
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RedissonService redissonService;

    @Override
    public LoginVO queryUserInfo(String userId) {
        log.info("尝试从redis中获取用户信息,userId: {}",userId);
        LoginVO userInfo = redissonService.getValue(RedisKey.USER_INFO + userId);
        if(userInfo!= null) {
            return userInfo;
        }

        log.info("从数据库中查询用户信息,userId: {}",userId);
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq(Common.USER_ID, userId);
        User user = userMapper.selectOne(wrapper);
        if (user != null) {
            LoginVO loginVO = User2LoginVO(user);
            log.info("正在缓存用户信息,userId: {}",userId);
            redissonService.setValue(RedisKey.USER_INFO + userId, loginVO);
            return loginVO;
        }
        return null;
    }

    @Override
    public void modifyUserInfo(User user) {

        log.info("正在修改用户信息,userId: {}",user.getUserId());
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq(Common.USER_ID, user.getUserId());

        int rowsAffected = userMapper.update(user, wrapper);
        if (rowsAffected > 0) {
            log.info("用户信息,修改成功,受影响的行数:{},userId: {}",rowsAffected, user.getUserId());
        } else {
            log.info("用户信息,修改失败,未找到匹配的用户或更新操作未执行,userId: {}",user.getUserId());
            throw new AppException(String.valueOf(GlobalServiceStatusCode.USER_UPDATE_ERROR.getCode()), GlobalServiceStatusCode.USER_UPDATE_ERROR.getMessage());
        }


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
