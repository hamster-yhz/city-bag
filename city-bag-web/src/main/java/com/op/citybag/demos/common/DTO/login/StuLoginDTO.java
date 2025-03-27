package com.op.citybag.demos.common.DTO.login;

import jakarta.validation.constraints.NotBlank;
import lombok.*;


/**
 * @Author: 原神
 * @Description:
 * @Date: 2025/1/21 11:11
 * @Version: 1.0
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StuLoginDTO {

    /**
     * 学号
     */
    @NotBlank(message = "学号不能为空")
    String stuId;

    /**
     * 密码
     */
    @NotBlank(message = "密码不能为空")
    String password;

//    /**
//     * 微信登陆获取手机号的code, 通过wx.getPhoneNumber() 获取
//     */
//    @NotBlank(message = "phoneCode不能为空")
//    String phoneCode;

}
