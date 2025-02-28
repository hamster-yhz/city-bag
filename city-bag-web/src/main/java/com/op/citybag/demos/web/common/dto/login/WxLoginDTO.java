package com.op.citybag.demos.web.common.dto.login;

import lombok.*;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;

/**
 * @Author: 原神
 * @Description:
 * @Date: 2025/1/18 20:06
 * @Version: 1.0
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Validated
public class WxLoginDTO {

    /**
     * 微信登陆零时码code，通过wx.login() 获取
     */
    @NotBlank(message = "code不能为空")
    String code;

    /**
     * 微信登陆获取手机号的code, 通过wx.getPhoneNumber() 获取
     */
    @NotBlank(message = "phoneCode不能为空")
    String phoneCode;
}
