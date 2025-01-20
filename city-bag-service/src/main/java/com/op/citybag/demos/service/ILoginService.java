package com.op.citybag.demos.service;

import com.op.citybag.demos.model.VO.LoginVO;

public interface ILoginService {

    /**
     * 登录
     * @param
     * @return
     */
    LoginVO login(String openid,String phoneNumber);

    /**
     * 登出
     * @return
     */
    void logout(String userId);
}
