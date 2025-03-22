package com.op.citybag.demos.web.common.DTO.user;

import lombok.*;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.*;

/**
 * @Author: 原神
 * @Description:
 * @Date: 2025/3/15 21:44
 * @Version: 1.0
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Validated
public class CollectionOrHistoryQueryDTO {

    /**
     * 用户id
     */
    @NotBlank(message = "用户id不能为空")
    private String userId;

    /**
     * 实体类型
     * 可空，为空时查询所有类型
     * 实体类型可以是city/food/dormitory/scenic_spot
     */
    @Pattern(regexp = "^(city|food|dormitory|scenic_spot)$",
            message = "实体类型只能是city/food/dormitory/scenic_spot 或为空")
    private String entityType;

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
