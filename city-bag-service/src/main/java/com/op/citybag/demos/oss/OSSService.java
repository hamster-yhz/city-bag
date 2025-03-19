package com.op.citybag.demos.oss;

import org.springframework.web.multipart.MultipartFile;

public interface OSSService {

    String generatePresignedUrl(String objectKey, int expireSeconds);

    String uploadCompressedImage(MultipartFile file, String userId);
}
