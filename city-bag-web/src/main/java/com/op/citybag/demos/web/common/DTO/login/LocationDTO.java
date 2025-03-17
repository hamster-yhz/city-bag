package com.op.citybag.demos.web.common.DTO.login;

import lombok.*;
import org.springframework.validation.annotation.Validated;

/**
 * @Author: 原神
 * @Description:
 * @Date: 2025/3/17 19:09
 * @Version: 1.0
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Validated
public class LocationDTO {
    // 微信定位的纬度
    private Double latitude;
    // 微信定位的经度
    private Double longitude;
    // 城市名称（前端可传可不传）
    private String cityName;
}
