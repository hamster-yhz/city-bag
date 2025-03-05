package com.op.citybag.demos.web.common.DTO.like;

import lombok.*;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.*;

/**
 * @Author: 原神
 * @Description:
 * @Date: 2025/3/3 18:55
 * @Version: 1.0
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Validated
public class LikeRequestDTO {

    /**
     * 用户id
     */
    @NotBlank(message = "用户id不能为空")
    private String userId;

    /**
     * 操作类型 操作类型只能是like|unlike
     */
    @NotBlank(message = "操作类型不能为空")
    @Pattern(regexp = "^(like|unlike)$",
            message = "操作类型只能是like|unlike")
    private String action;

    /**
     * 实体id
     */
    @NotBlank(message = "实体id不能为空")
    private String entityId;

    /**
     * 实体类型 实体类型只能是city/food/dormitory/scenic_spot
     */
    @NotBlank(message = "实体类型不能为空")
    @Pattern(regexp = "^(city|food|dormitory|scenic_spot)$",
            message = "实体类型只能是city/food/dormitory/scenic_spot")
    private String entityType;
}
