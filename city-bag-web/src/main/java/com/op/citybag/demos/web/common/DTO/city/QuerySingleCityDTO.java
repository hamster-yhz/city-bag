package com.op.citybag.demos.web.common.DTO.city;

import lombok.*;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;

/**
 * @Author: 原神
 * @Description:
 * @Date: 2025/2/26 20:53
 * @Version: 1.0
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Validated
public class QuerySingleCityDTO {

    /**
     * 城市名称(精确查询)
     */
    @NotBlank(message = "城市名称不能为空")
    private String cityName;

}
