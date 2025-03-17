package com.op.citybag.demos.web.common.DTO.city;

import lombok.*;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;

/**
 * @Author: 原神
 * @Description:
 * @Date: 2025/3/2 23:25
 * @Version: 1.0
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Validated
public class QuerySingleFoodDTO {

    /**
     * 用户ID(可以为空)
     */
    private String userId;

    /**
     * 食物ID
     */
    @NotBlank(message = "食物ID不能为空")
    private String foodId;
}
