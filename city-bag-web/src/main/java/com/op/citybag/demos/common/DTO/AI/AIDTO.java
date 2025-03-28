package com.op.citybag.demos.common.DTO.AI;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.validation.annotation.Validated;



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
