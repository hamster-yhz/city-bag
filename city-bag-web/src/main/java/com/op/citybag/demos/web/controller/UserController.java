package com.op.citybag.demos.web.controller;

import cn.binarywang.wx.miniapp.api.WxMaService;
import com.op.citybag.demos.model.Entity.User;
import com.op.citybag.demos.service.IUserService;
import com.op.citybag.demos.web.common.OPResult;
import com.op.citybag.demos.web.common.dto.ChangePasswordDTO;
import com.op.citybag.demos.web.common.dto.StuLoginDTO;
import com.op.citybag.demos.web.common.dto.WxLoginDTO;
import com.op.citybag.demos.model.VO.LoginVO;
import com.op.citybag.demos.service.ILoginService;
import com.op.citybag.demos.web.common.dto.UserDTO;
import com.op.citybag.demos.web.constraint.LoginVerification;
import com.op.citybag.demos.web.constraint.SelfPermissionVerification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: 原神
 * @Description:
 * @Date: 2025/1/18 18:51
 * @Version: 1.0
 */

//${app.config.api-version}
@Slf4j
@Validated
@RestController()
@CrossOrigin("${app.config.cross-origin}")
@RequestMapping("/api/v1/auth/")
@RequiredArgsConstructor
public class UserController {

    @Autowired
    private final ILoginService loginService;

    @Autowired
    private final WxMaService wxMaService;

    @Autowired
    private final HttpServletRequest request;

    @Autowired
    private final IUserService userService;

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
    @LoginVerification
    @SelfPermissionVerification
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
     * 修改个人信息
     */
    @LoginVerification
    @SelfPermissionVerification
    @PostMapping("modifyUserInfo")
    public OPResult modifyUserInfo(@RequestBody UserDTO userDTO){
        try{
            log.info("修改个人信息开始,userId:{}",userDTO.getUserId());
            User user =User.builder()
                    .userId(userDTO.getUserId())
                    .userName(userDTO.getUserName())
                    .gender(userDTO.getGender())
                    .birthday(userDTO.getBirthday())
                    .personalizedSignature(userDTO.getPersonalizedSignature())
                    .build();
            userService.modifyUserInfo(user);
            log.info("修改个人信息成功,userId:{}",userDTO.getUserId());
            return OPResult.SUCCESS();
        }catch (Exception e){
            log.error("修改个人信息失败,userId:{},cuz:{}",userDTO.getUserId(),e.getMessage());
            return OPResult.FAIL(e);
        }
    }

    /**
     * 查询个人信息
     */
    @LoginVerification
    @SelfPermissionVerification
    @PostMapping("queryUserInfo")
    public OPResult queryUserInfo(@RequestBody UserDTO userDTO, HttpServletRequest request){
        try{
            LoginVO loginVO = userService.queryUserInfo(userDTO.getUserId());
            log.info("查询个人信息成功,userId:{}",loginVO.getUserId());
            return OPResult.SUCCESS(loginVO);
        }catch (Exception e){
            log.error("查询个人信息失败,userId:{},cuz:{}",userDTO.getUserId(),e.getMessage());
            return OPResult.FAIL(e);
        }
    }

    /**
     * 更改密码
     * @param changePasswordDTO
     * @return
     */
    @LoginVerification
    @SelfPermissionVerification
    @PostMapping("changePassword")
    public OPResult changePassword(@RequestBody ChangePasswordDTO changePasswordDTO){
        try{
            log.info("更改密码开始,stuId:{}",changePasswordDTO.getStuId());

            loginService.stuChangePassword(changePasswordDTO.getStuId(), changePasswordDTO.getPassword(),changePasswordDTO.getNewPassword());

            log.info("更改密码成功,stuId:{}",changePasswordDTO.getStuId());
            return OPResult.SUCCESS();
        }catch (Exception e){
            log.error("更改密码失败,stuId:{},cuz:{}",changePasswordDTO.getStuId(),e.getMessage());
            return OPResult.FAIL(e);
        }
    }
}
