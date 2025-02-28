package com.op.citybag.demos.oss;

public interface OSSService {

    String generatePresignedUrl(String objectKey, int expireSeconds);

}
