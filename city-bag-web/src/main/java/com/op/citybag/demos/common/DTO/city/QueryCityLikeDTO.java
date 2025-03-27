package com.op.citybag.demos.common.DTO.city;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.validation.annotation.Validated;

/**
 * @Author: 原神
 * @Description:
 * @Date: 2025/3/10 19:02
 * @Version: 1.0
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Validated
public class QueryCityLikeDTO {

    /**
     * 城市名称
     */
    @NotBlank(message = "城市名称不能为空")
    private String cityName;

//    /**
//     * 当前页码
//     */
//    @NotNull(message = "页码不能为空")
//    @Min(value = 1, message = "页码最小为1")
//    private Integer pageNum;
//
//    /**
//     * 分页大小
//     */
//    @NotNull(message = "分页大小不能为空")
//    @Min(value = 1, message = "分页大小最小为1")
//    @Max(value = 50, message = "分页大小最大为50")
//    private Integer pageSize;

}
