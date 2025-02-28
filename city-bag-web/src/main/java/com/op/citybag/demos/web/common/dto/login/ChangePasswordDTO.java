package com.op.citybag.demos.web.common.dto.login;

import lombok.*;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;

/**
 * @Author: 原神
 * @Description:
 * @Date: 2025/1/21 11:30
 * @Version: 1.0
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Validated
public class ChangePasswordDTO {

        /**
         * 用户id
         */
        @NotBlank(message = "用户id不能为空")
        String userId;

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

        /**
         * 新密码
         */
        @NotBlank(message = "新密码不能为空")
        String newPassword;

}
