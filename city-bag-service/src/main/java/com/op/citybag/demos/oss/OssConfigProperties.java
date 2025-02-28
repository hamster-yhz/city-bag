/*
 * Copyright 2013-2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.op.citybag.demos.oss;

import com.aliyun.oss.OSS;

import com.aliyun.oss.OSSClientBuilder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * OSS common config.
 *
 * @author <a href="mailto:chenxilzx1@gmail.com">theonefx</a>
 */

// ... 保留原有版权声明 ...

@Configuration
@ConfigurationProperties(prefix = "oss")
@Getter
@Setter
public class OssConfigProperties {

	// 从配置文件读取配置
	private String endpoint;
	private String accessKeyId;
	private String accessKeySecret;
	private String bucketName;

	// 创建OSS客户端Bean
	@Bean
	public OSS ossClient() {
		return new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
	}
}