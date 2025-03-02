package com.op.citybag.demos.service;

import com.op.citybag.demos.model.Entity.User;
import com.op.citybag.demos.model.VO.user.UserVO;

public interface IUserService {

    /**
     * 查询用户信息
     *
     * @param userId
     * @return
     */
    UserVO queryUserInfo(String userId);

    /**
     * 更改用户信息
     *
     * @param user
     * @return
     */
    void modifyUserInfo(User user);
}
