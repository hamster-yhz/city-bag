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

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * OSS common config.
 *
 * @author <a href="mailto:chenxilzx1@gmail.com">theonefx</a>
 */

@Configuration
public class OssConfig {

	/**
	 * Bucket Name of OSS Example.
	 */
	public static final String BUCKET_NAME = "city-bag";

	@Autowired
	private OSS ossClient;

	/**
	 * You can use these code to check or create oss bucket. Or manage buckets in
	 * <a href="https://oss.console.aliyun.com/bucket">oss console</a>.
	 */
	@Bean
	public ApplicationRunner ossBucketInitRunner() {
		return args -> {
			if (!ossClient.doesBucketExist(BUCKET_NAME)) {
				ossClient.createBucket(BUCKET_NAME);
			}
		};
	}
}
