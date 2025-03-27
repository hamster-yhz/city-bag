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
import com.op.citybag.demos.exception.AppException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;


@Service
public class OSSServiceImpl implements OSSService {

    @Autowired
    private OSS ossClient;

    @Autowired
    private OssConfigProperties ossConfigProperties;

    @Override
    public String generatePresignedUrl(String objectKey, int expireSeconds) {

        // 自动补全扩展名逻辑
//        String fullKey = objectKey.endsWith(".png") ? objectKey : objectKey + ".png";

        if(objectKey == null || objectKey.isEmpty()){
            return null;
        }

        return ossClient.generatePresignedUrl(
                ossConfigProperties.getBucketName(),
                objectKey,
                Date.from(Instant.now().plusSeconds(expireSeconds))
        ).toString();
    }

    @Override
    public String uploadCompressedImage(MultipartFile file, String userId) {
        // 1. 生成唯一文件名
        String fileName = UUID.randomUUID() + "_" + System.currentTimeMillis() + ".jpg";

        // 2. 创建OSS存储路径
        String path = "user/" + userId + "/images/" + fileName;

        try {
            // 3. 直接存储压缩后的图片（无需服务端解压）
            ossClient.putObject(ossConfigProperties.getBucketName(), path, file.getInputStream());

            // 4. 返回访问地址 1day
            return path;
        } catch (IOException e) {
            throw new AppException("文件上传失败");
        }
    }

}
