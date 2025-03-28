package com.op.citybag.demos.service.buildphoto;

import java.util.List;

public interface ImageStrategy {

    /**
     * 构建图片并落库
     * @param entityId
     * @param entityName
     * @return
     */
    String buildPhoto(String entityId, String entityName);

    /**
     * 构建图片并落库
     * @param entityId
     * @param entityName
     * @return
     */
    List<String> buildPhotoList(String entityId, String entityName, int size);

//    /**
//     * 构建图片并落库(默认五张)
//     * @param entityId
//     * @param entityName
//     * @return
//     */
//    List<String> buildPhotoList(String entityId, String entityName);

    /**
     * 获取图片构建策略
     * @return
     */
    StrategyEnum getName();
}