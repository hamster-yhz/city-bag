package com.op.citybag.demos.Pexels;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: 原神
 * @Description:
 * @Date: 2025/3/25 19:38
 * @Version: 1.0
 */
@ConfigurationProperties(prefix = "pexels")
@Data
@Configuration
public class PexelsConfigProperties {
    private String apiKey;
    private String baseUrl = "https://api.pexels.com/v1";
}