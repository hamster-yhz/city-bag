package com.op.citybag.demos.service.buildphoto;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.op.citybag.demos.mapper.FoodMapper;
import com.op.citybag.demos.model.Entity.Food;
import com.op.citybag.demos.Pexels.PexelsClientServiceImpl;
import com.op.citybag.demos.model.common.Common;
import com.op.citybag.demos.utils.SplitUtil;
import lombok.NoArgsConstructor;
import net.bytebuddy.agent.builder.AgentBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static cn.hutool.poi.excel.sax.AttributeName.s;


/**
 * @Author: 原神
 * @Description:
 * @Date: 2025/3/27 22:13
 * @Version: 1.0
 */
@Component
@NoArgsConstructor
public class FoodImageStrategy implements ImageStrategy {

    @Autowired
    private FoodMapper foodMapper;

    @Autowired
    private PexelsClientServiceImpl pexelsClientServiceImpl;

    @Override
    public StrategyEnum getName() {
        return StrategyEnum.FOOD;
    }

    @Override
    public String buildPhoto(String entityId, String entityName) {

        String newImageUrl = pexelsClientServiceImpl.searchOnePhoto(entityName);

        UpdateWrapper<Food> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq(Common.FOOD_ID, entityId)
                .set(Common.IMG_URL, newImageUrl);

        foodMapper.update(updateWrapper);

        return newImageUrl;

    }

    @Override
    public List<String> buildPhotoList(String entityId, String entityName, int size) {

        List<String> imageUrlList = pexelsClientServiceImpl.searchFivePhotos(entityName);

        String imgurls = SplitUtil.unsplitByUnderscore(imageUrlList);

        UpdateWrapper<Food> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq(Common.FOOD_ID, entityId)
                        .set(Common.PHOTO_URL, imgurls);

        foodMapper.update(updateWrapper);

        return imageUrlList;
    }

//    @Override
//    public List<String> buildPhotoList(String entityId, String entityName) {
//        return buildPhotoList(entityId, entityName, 5);
//    }
}