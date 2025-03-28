package com.op.citybag.demos.service.buildphoto;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.op.citybag.demos.mapper.ScenicSpotMapper;
import com.op.citybag.demos.model.Entity.Food;
import com.op.citybag.demos.model.Entity.ScenicSpot;
import com.op.citybag.demos.Pexels.PexelsClientServiceImpl;
import com.op.citybag.demos.model.common.Common;
import com.op.citybag.demos.utils.SplitUtil;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


/**
 * @Author: 原神
 * @Description:
 * @Date: 2025/3/27 22:13
 * @Version: 1.0
 */
@Component
@NoArgsConstructor
public class ScenicSpotImageStrategy implements ImageStrategy {

    @Autowired
    private ScenicSpotMapper scenicSpotMapper;

    @Autowired
    private PexelsClientServiceImpl pexelsClientServiceImpl;

    @Override
    public StrategyEnum getName() {
        return StrategyEnum.SCENIC_SPOT;
    }

    @Override
    public String buildPhoto(String entityId, String entityName) {

        String newImageUrl = pexelsClientServiceImpl.searchOnePhoto(entityName);

        UpdateWrapper<ScenicSpot> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq(Common.SCENIC_SPOT_ID, entityId)
                .set(Common.IMG_URL, newImageUrl);

        scenicSpotMapper.update(updateWrapper);

        return newImageUrl;

    }

    @Override
    public List<String> buildPhotoList(String entityId, String entityName, int size) {

        List<String> imageUrlList = pexelsClientServiceImpl.searchFivePhotos(entityName);

        String imgurls = SplitUtil.unsplitByUnderscore(imageUrlList);

        UpdateWrapper<ScenicSpot> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq(Common.SCENIC_SPOT_ID, entityId)
                .set(Common.PHOTO_URL, imgurls);

        scenicSpotMapper.update(updateWrapper);

        return imageUrlList;
    }

//    @Override
//    public List<String> buildPhotoList(String entityId, String entityName) {
//        return buildPhotoList(entityId, entityName, 5);
//    }
}