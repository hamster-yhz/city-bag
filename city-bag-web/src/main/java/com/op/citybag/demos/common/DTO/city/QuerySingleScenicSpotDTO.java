package com.op.citybag.demos.common.DTO.city;

import jakarta.validation.constraints.NotBlank;
import lombok.*;


/**
 * @Author: 原神
 * @Description:
 * @Date: 2025/3/2 23:23
 * @Version: 1.0
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuerySingleScenicSpotDTO {

    /**
     * 用户ID(可以为空)
     */
    private String userId;

    /**
     * 景点ID
     */
    @NotBlank(message = "景点ID不能为空")
    private String scenicSpotId;
}
