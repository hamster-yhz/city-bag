package com.op.citybag.demos.web.controller;

import cn.binarywang.wx.miniapp.api.WxMaService;
import com.op.citybag.demos.model.Entity.User;
import com.op.citybag.demos.service.IUserService;
import com.op.citybag.demos.web.common.OPResult;
import com.op.citybag.demos.web.common.dto.login.ChangePasswordDTO;
import com.op.citybag.demos.web.common.dto.login.StuLoginDTO;
import com.op.citybag.demos.web.common.dto.login.WxLoginDTO;
import com.op.citybag.demos.model.VO.LoginVO;
import com.op.citybag.demos.service.ILoginService;
import com.op.citybag.demos.web.common.dto.user.UserDTO;
import com.op.citybag.demos.web.constraint.LoginVerification;
import com.op.citybag.demos.web.constraint.SelfPermissionVerification;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


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
@RequestMapping("/api/v1/auth/")//测试
@RequiredArgsConstructor
public class UserController {

    @Autowired
    private final IUserService userService;

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

}
