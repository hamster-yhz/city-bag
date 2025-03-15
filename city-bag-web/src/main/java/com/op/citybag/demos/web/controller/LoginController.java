package com.op.citybag.demos.web.controller;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import com.op.citybag.demos.model.VO.login.LoginVO;
import com.op.citybag.demos.model.VO.login.TokenVO;
import com.op.citybag.demos.service.INewLoginService;
import com.op.citybag.demos.web.common.DTO.login.ChangePasswordDTO;
import com.op.citybag.demos.web.common.DTO.login.RefreshDTO;
import com.op.citybag.demos.web.common.DTO.login.StuLoginDTO;
import com.op.citybag.demos.web.common.DTO.login.WxLoginDTO;
import com.op.citybag.demos.web.common.DTO.user.UserDTO;
import com.op.citybag.demos.web.common.OPResult;
import com.op.citybag.demos.web.constraint.CheckRefreshToken;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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
    private final INewLoginService loginService;

    @Autowired
    private final WxMaService wxMaService;

    @Autowired
    private final HttpServletRequest request;

    /**
     * 微信登陆
     *  @param wxLoginDTO
     *  @return
     */
    @PostMapping("wxlogin")
    public OPResult wxLogin(@RequestBody WxLoginDTO wxLoginDTO) {
        try {

//            // 微信登陆获取openid和phoneNumber
//            WxMaJscode2SessionResult session = wxMaService.getUserService().getSessionInfo(wxLoginDTO.getCode());
//            String openid = session.getOpenid();

//            WxMaPhoneNumberInfo numberInfo = wxMaService.getUserService().getNewPhoneNoInfo(wxLoginDTO.getPhoneCode());
//            String phoneNumber = numberInfo.getPhoneNumber();


//            String unionId = session.getUnionid(); // 需配置开放平台

//            // 测试用
          String openid = wxLoginDTO.getCode();

            log.info("微信登陆开始,openid:{}", openid);

            LoginVO loginVO = loginService.wxLogin(openid);

            log.info("微信登陆成功,openid:{}", openid);
            return OPResult.SUCCESS(loginVO);
        } catch (Exception e) {
            log.error("微信登陆失败,cuz:{}", e);
            return OPResult.FAIL(e);
        }
    }

    /**
     * 学号登陆
     * @param stuLoginDTO
     */
    @PostMapping("stulogin")
    public OPResult stuLogin(@RequestBody StuLoginDTO stuLoginDTO) {
        try {

            log.info("学号登陆开始,stuId:{}", stuLoginDTO.getStuId());

            LoginVO loginVO = loginService.stuLogin(stuLoginDTO.getStuId(), stuLoginDTO.getPassword());

            log.info("学号登陆成功,stuId:{}", stuLoginDTO.getStuId());

            return OPResult.SUCCESS(loginVO);
        } catch (Exception e) {
            log.error("学号登陆失败,stuId:{},cuz:{}", stuLoginDTO.getStuId(), e.getMessage());
            return OPResult.FAIL(e);
        }
    }
    
    /**
     * 刷新token (无感刷新)
     *
     */
    @CheckRefreshToken
    @PostMapping(value = "refresh")
    public OPResult refresh(HttpServletRequest request, HttpServletResponse response) {
        try {
            // 从请求头中获取refreshToken
            String refrashToken = request.getHeader(RefreshDTO.REFRESH_TOKEN);

            log.info("正在访问无感刷新接口,reflashToken:{}", refrashToken);

            TokenVO tokenVO = loginService.refreshToken(refrashToken);

            return OPResult.SUCCESS(tokenVO);

        } catch (Exception e) {
            log.info("访问无感刷新接口失败,reflashToken:{}", request.getHeader(RefreshDTO.REFRESH_TOKEN));
            return OPResult.FAIL(e);
        }
    }

    /**
     * 退出登陆
     */
    @PostMapping("logout")
//    @LoginVerification
//    @SelfPermissionVerification
    public OPResult logout(@RequestBody UserDTO userDTO) {
        try {
            log.info("退出登陆开始,userId:{}", userDTO.getUserId());
            loginService.logout(userDTO.getUserId());
            log.info("退出登陆成功,userId:{}", userDTO.getUserId());
            return OPResult.SUCCESS();
        } catch (Exception e) {
            log.error("退出登陆失败,userId:{},cuz:{}", userDTO.getUserId(), e.getMessage());
            return OPResult.FAIL(e);
        }
    }

    /**
     * 更改密码
     *
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
