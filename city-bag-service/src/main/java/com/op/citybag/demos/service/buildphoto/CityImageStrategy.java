package com.op.citybag.demos.service.buildphoto;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.op.citybag.demos.Pexels.PexelsClientServiceImpl;
import com.op.citybag.demos.mapper.CityMapper;
import com.op.citybag.demos.mapper.DormitoryMapper;
import com.op.citybag.demos.model.Entity.City;
import com.op.citybag.demos.model.Entity.Dormitory;
import com.op.citybag.demos.model.common.Common;
import com.op.citybag.demos.service.Impl.CityServiceImpl;
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
public class CityImageStrategy implements ImageStrategy {

    @Autowired
    private CityMapper cityMapper;

    @Autowired
    private PexelsClientServiceImpl pexelsClientServiceImpl;

    @Override
    public StrategyEnum getName() {
        return StrategyEnum.CITY;
    }

    @Override
    public String buildPhoto(String entityId, String entityName) {

        String newImageUrl = pexelsClientServiceImpl.searchOnePhoto(entityName);

        UpdateWrapper<City> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq(Common.CITY_ID, entityId)
                .set(Common.IMG_URL, newImageUrl);

        cityMapper.update(updateWrapper);

        return newImageUrl;

    }

    @Override
    public List<String> buildPhotoList(String entityId, String entityName, int size) {

//        List<String> imageUrlList = new ArrayList<>();
//
//        for(int i = 0; i < size; i++){
//            String newImageUrl = pexelsClientServiceImpl.searchOnePhoto(entityName);
//            imageUrlList.add(newImageUrl);
//        }
//
//        String imgurls = SplitUtil.unsplitByUnderscore(imageUrlList);
//
//        UpdateWrapper<City> updateWrapper = new UpdateWrapper<>();
//        updateWrapper.eq(Common.CITY_ID, entityId)
//                .set(Common, imgurls);
//
//        cityMapper.update(updateWrapper);
//
//        return imageUrlList;
        return null;
    }

//    @Override
//    public List<String> buildPhotoList(String entityId, String entityName) {
//        return buildPhotoList(entityId, entityName, 5);
//    }
}