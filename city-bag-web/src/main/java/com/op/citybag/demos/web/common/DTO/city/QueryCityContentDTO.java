package com.op.citybag.demos.web.common.DTO.city;

import lombok.*;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;

/**
 * @Author: 原神
 * @Description:
 * @Date: 2025/2/27 17:39
 * @Version: 1.0
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Validated
public class QueryCityContentDTO {

    /**
     * 城市id
     */
    @NotBlank(message = "城市id不能为空")
    private String cityId;
}
