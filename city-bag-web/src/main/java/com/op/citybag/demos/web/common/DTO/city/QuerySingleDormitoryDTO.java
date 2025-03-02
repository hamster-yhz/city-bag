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
public class QuerySingleDormitoryDTO {

    /**
     * 宿舍id
     */
    @NotBlank(message = "宿舍id不能为空")
    private String dormitoryId;
}
