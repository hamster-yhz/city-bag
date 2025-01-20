package com.op.citybag.demos.service;

import com.op.citybag.demos.model.Entity.User;
import com.op.citybag.demos.model.VO.LoginVO;

public interface IUserService {

    /**
     * 查询用户信息
     * @param userId
     * @return
     */
    LoginVO queryUserInfo(String userId);

    /**
     * 更改用户信息
     * @param user
     * @return
     */
    void modifyUserInfo(User user);
}
