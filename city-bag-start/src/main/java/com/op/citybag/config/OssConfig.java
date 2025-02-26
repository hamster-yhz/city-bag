package com.op.citybag.config;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: 原神
 * @Description:
 * @Date: 2025/2/23 16:03
 * @Version: 1.0
 */
@Configuration
public class OssConfig {
    @Value("${alibaba.cloud.access-key}")
    private String accessKeyId;

    @Value("${alibaba.cloud.secret-key}")
    private String accessKeySecret;

    @Value("${alibaba.cloud.oss.endpoint}")
    private String endpoint;

    @Bean
    public OSS ossClient() {
        return new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
    }
}
