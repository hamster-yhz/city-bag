package com.op.citybag.demos.web.common.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

/**
 * @Author: 原神
 * @Description:
 * @Date: 2025/1/19 17:42
 * @Version: 1.0
 */

@Getter
@Setter
@Builder
@Validated
public class AIDto {

    String input;
}
