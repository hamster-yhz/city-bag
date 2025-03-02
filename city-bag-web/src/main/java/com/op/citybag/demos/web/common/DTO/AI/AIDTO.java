package com.op.citybag.demos.web.common.DTO.AI;

import lombok.*;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;

/**
 * @Author: 原神
 * @Description:
 * @Date: 2025/1/19 17:42
 * @Version: 1.0
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Validated
public class AIDTO {

    @NotBlank(message = "输入不能为空")
    String input;
}
