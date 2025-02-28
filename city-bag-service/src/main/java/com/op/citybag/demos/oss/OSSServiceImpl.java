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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;


@Service
public class OSSServiceImpl implements OSSService {

    @Autowired
    private OSS ossClient;

    @Autowired
    private OssConfigProperties ossConfigProperties;

    @Override
    public String generatePresignedUrl(String objectKey, int expireSeconds) {
        return ossClient.generatePresignedUrl(
                ossConfigProperties.getBucketName(),
                objectKey,
                Date.from(Instant.now().plusSeconds(expireSeconds))
        ).toString();
    }

}
