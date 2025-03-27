package com.op.citybag.demos.common.DTO.city;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;


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
public class QueryCityContentDTO {

    /**
     * 用户id(可以为空)
     */
    private String userId;

    /**
     * 城市id
     */
    @NotBlank(message = "城市id不能为空")
    private String cityId;

    /**
     * 当前页码
     */
    @NotNull(message = "页码不能为空")
    @Min(value = 1, message = "页码最小为1")
    private Integer pageNum;

    /**
     * 分页大小
     */
    @NotNull(message = "分页大小不能为空")
    @Min(value = 1, message = "分页大小最小为1")
    @Max(value = 50, message = "分页大小最大为50")
    private Integer pageSize;
}
