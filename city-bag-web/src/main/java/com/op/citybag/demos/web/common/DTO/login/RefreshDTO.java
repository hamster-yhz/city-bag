package com.op.citybag.demos.web.common.DTO.login;

import lombok.*;
import org.springframework.validation.annotation.Validated;

/**
 * @Author: 原神
 * @Description:
 * @Date: 2025/3/12 21:01
 * @Version: 1.0
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Validated
public class RefreshDTO {

    public static final String REFRESH_TOKEN = "refresh_token";
}
