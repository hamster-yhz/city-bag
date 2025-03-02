package com.op.citybag.demos.web.controller;

import cn.binarywang.wx.miniapp.api.WxMaService;
import com.op.citybag.demos.model.VO.login.LoginVO;
import com.op.citybag.demos.service.ILoginService;
import com.op.citybag.demos.web.common.OPResult;
import com.op.citybag.demos.web.common.DTO.login.ChangePasswordDTO;
import com.op.citybag.demos.web.common.DTO.login.StuLoginDTO;
import com.op.citybag.demos.web.common.DTO.login.WxLoginDTO;
import com.op.citybag.demos.web.common.DTO.user.UserDTO;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: 原神
 * @Description:
 * @Date: 2025/2/26 20:55
 * @Version: 1.0
 */

//${app.config.api-version}
@Slf4j
@Validated
@RestController()
@CrossOrigin("${app.config.cross-origin}")
@RequestMapping("/api/v1/auth/")//测试
@RequiredArgsConstructor
public class LoginController {


    @Autowired
    private final ILoginService loginService;

    @Autowired
    private final WxMaService wxMaService;

    @Autowired
    private final HttpServletRequest request;

    /**
     * 微信登陆
     */
    @PostMapping("wxlogin")
    public OPResult wxLogin(@RequestBody WxLoginDTO wxLoginDTO) {
        try{

            // 微信登陆获取openid和phoneNumber
//            WxMaJscode2SessionResult session = wxMaService.getUserService().getSessionInfo(wxLoginDTO.getCode());
//            String openid = session.getOpenid();
//            WxMaPhoneNumberInfo numberInfo = wxMaService.getUserService().getNewPhoneNoInfo(wxLoginDTO.getPhoneCode());
//            String phoneNumber = numberInfo.getPhoneNumber();

            // 测试用
            String openid = wxLoginDTO.getCode();
            String phoneNumber = wxLoginDTO.getPhoneCode();

            log.info("微信登陆开始,openid:{},phoneNumber:{}",openid,phoneNumber);

            LoginVO loginVO = loginService.wxLogin(openid,phoneNumber);

            log.info("微信登陆成功,openid:{},phoneNumber:{}",openid,phoneNumber);
            return OPResult.SUCCESS(loginVO);
        }catch (Exception e){
            log.error("微信登陆失败,cuz:{}",e);
            return OPResult.FAIL(e);
        }
    }

    /**
     * 学号登陆
     */
    @PostMapping("stulogin")
    public OPResult stuLogin(@RequestBody StuLoginDTO stuLoginDTO) {
        try{
            log.info("学号登陆开始,stuId:{}",stuLoginDTO.getStuId());

            LoginVO loginVO = loginService.stuLogin(stuLoginDTO.getStuId(), stuLoginDTO.getPassword());

            log.info("学号登陆成功,stuId:{}",stuLoginDTO.getStuId());
            return OPResult.SUCCESS(loginVO);
        }catch (Exception e){
            log.error("学号登陆失败,stuId:{},cuz:{}",stuLoginDTO.getStuId(),e.getMessage());
            return OPResult.FAIL(e);
        }
    }

    /**
     * 退出登陆
     */
    @PostMapping("logout")
//    @LoginVerification
//    @SelfPermissionVerification
    public OPResult logout(@RequestBody UserDTO userDTO){
        try{
            log.info("退出登陆开始,userId:{}",userDTO.getUserId());
            loginService.logout(userDTO.getUserId());
            log.info("退出登陆成功,userId:{}",userDTO.getUserId());
            return OPResult.SUCCESS();
        }catch (Exception e){
            log.error("退出登陆失败,userId:{},cuz:{}",userDTO.getUserId(),e.getMessage());
            return OPResult.FAIL(e);
        }
    }

    /**
     * 更改密码
     * @param changePasswordDTO
     * @return
     */
//    @LoginVerification
//    @SelfPermissionVerification
    @PostMapping("changePassword")
    public OPResult changePassword(@RequestBody ChangePasswordDTO changePasswordDTO) {
        try {
            log.info("更改密码开始,stuId:{}", changePasswordDTO.getStuId());

            loginService.stuChangePassword(changePasswordDTO.getStuId(), changePasswordDTO.getPassword(), changePasswordDTO.getNewPassword());

            log.info("更改密码成功,stuId:{}", changePasswordDTO.getStuId());
            return OPResult.SUCCESS();
        } catch (Exception e) {
            log.error("更改密码失败,stuId:{},cuz:{}", changePasswordDTO.getStuId(), e.getMessage());
            return OPResult.FAIL(e);
        }
    }
}
