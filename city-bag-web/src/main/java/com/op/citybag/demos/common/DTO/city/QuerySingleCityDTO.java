package com.op.citybag.demos.common.DTO.city;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

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
public class QuerySingleCityDTO {

    /**
     * 用户id(可以为空)
     */
    private String userId;

    /**
     * 城市id
     */
    @NotBlank(message = "城市id不能为空")
    private String cityId;

}
