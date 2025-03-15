package com.op.citybag.demos.service;

import com.op.citybag.demos.model.VO.login.LoginVO;
import com.op.citybag.demos.model.VO.login.TokenVO;

public interface INewLoginService {
    /**
     * 微信登录
     * @param
     * @return
     */
    LoginVO wxLogin(String openid);

    /**
     * 学号登录
     * @param
     * @return
     */
    LoginVO stuLogin(String stuId,String password);

    /**
     * 修改密码
     *
     * @param stuId
     * @param password
     * @param newPassword
     */
    void stuChangePassword(String stuId,String password,String newPassword);

    /**
     * 登出
     * @return
     */
    void logout(String userId);

    /**
     * 刷新token
     * @param refreshToken
     * @return
     */
    TokenVO refreshToken(String refreshToken);
}
