package com.op.citybag.demos.common.DTO.login;

import jakarta.validation.constraints.NotBlank;
import lombok.*;


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
public class WxLoginDTO {

    /**
     * 微信登陆零时码code，通过wx.login() 获取
     */
    @NotBlank(message = "code不能为空")
    String code;

//    /**
//     * 微信登陆获取手机号的code, 通过wx.getPhoneNumber() 获取
//     */
//    @NotBlank(message = "phoneCode不能为空")
//    String phoneCode;
}
