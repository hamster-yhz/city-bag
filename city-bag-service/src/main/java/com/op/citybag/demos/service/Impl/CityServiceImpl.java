package com.op.citybag.demos.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.op.citybag.demos.exception.AppException;
import com.op.citybag.demos.mapper.CityMapper;
import com.op.citybag.demos.mapper.DormitoryMapper;
import com.op.citybag.demos.mapper.FoodMapper;
import com.op.citybag.demos.mapper.ScenicSpotMapper;
import com.op.citybag.demos.model.Entity.City;
import com.op.citybag.demos.model.Entity.Dormitory;
import com.op.citybag.demos.model.Entity.Food;
import com.op.citybag.demos.model.Entity.ScenicSpot;
import com.op.citybag.demos.model.VO.page.cover.DormitoryCoverVO;
import com.op.citybag.demos.model.VO.page.cover.FoodCoverVO;
import com.op.citybag.demos.model.VO.page.cover.ScenicSpotCoverVO;
import com.op.citybag.demos.model.VO.page.list.DormitoryListVO;
import com.op.citybag.demos.model.VO.page.list.FoodListVO;
import com.op.citybag.demos.model.VO.page.list.ScenicSpotListVO;
import com.op.citybag.demos.model.VO.page.object.CityVO;
import com.op.citybag.demos.model.common.GlobalServiceStatusCode;
import com.op.citybag.demos.oss.OSSServiceImpl;
import com.op.citybag.demos.service.ICityService;
import com.op.citybag.demos.utils.Entity2VO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: 原神
 * @Description: 城市服务实现类
 * @Date: 2025/2/26 21:25
 * @Version: 1.0
 */

@Service
@Slf4j
public class CityServiceImpl implements ICityService {

    @Autowired
    private ScenicSpotMapper scenicSpotMapper;

    @Autowired
    private FoodMapper foodMapper;

    @Autowired
    private DormitoryMapper dormitoryMapper;

    @Autowired
    private CityMapper cityMapper;

    @Autowired
    private OSSServiceImpl ossDemoService;

    @Override
    public CityVO querySingleCity(String cityName) {

        // 构建查询条件
        QueryWrapper<City> wrapper = new QueryWrapper<>();
        wrapper.eq("city_name", cityName);

        // 执行查询
        City city = cityMapper.selectOne(wrapper);
        if (city == null) {
            log.info("未找到城市: {}", cityName);
            throw new AppException(String.valueOf(GlobalServiceStatusCode.CITY_NOT_EXIST.getCode()), GlobalServiceStatusCode.CITY_NOT_EXIST.getMessage());
        }

        // 转换为VO
        CityVO cityVO = Entity2VO.City2CityVO(city);

        // 查询城市图片
        String cityImg = ossDemoService.generatePresignedUrl
                (city.getImageUrl(), 1000000000);
        cityVO.setCityImg(cityImg);

        return cityVO;
    }

    @Override
    public ScenicSpotListVO queryCityScenicSpot(String cityId) {

        // 查询景点封面列表
        List<ScenicSpotCoverVO> coverList = scenicSpotMapper.selectList(
                new LambdaQueryWrapper<ScenicSpot>()
                        .eq(ScenicSpot::getCityId, cityId)
                        .select(
                                ScenicSpot::getScenicSpotId,
                                ScenicSpot::getScenicSpotName,
                                ScenicSpot::getImageUrl
                        )
        ).stream().map(spot -> ScenicSpotCoverVO.builder()
                .scenicSpotId(spot.getScenicSpotId())
                .scenicSpotName(spot.getScenicSpotName())
                .scenicSpotImg(ossDemoService.generatePresignedUrl(spot.getImageUrl(), 1000000000))
                .build()
        ).collect(java.util.stream.Collectors.toList());

        return ScenicSpotListVO.builder()
                .cityId(cityId)
                .scenicSpotList(coverList)
                .build();
    }

    @Override
    public FoodListVO queryCityFood(String cityId) {
        // 查询美食封面列表
        List<FoodCoverVO> coverList = foodMapper.selectList(
                new LambdaQueryWrapper<Food>()
                        .eq(Food::getCityId, cityId)
                        .select(
                                Food::getFoodId,
                                Food::getFoodName,
                                Food::getImageUrl
                        )
        ).stream().map(spot -> FoodCoverVO.builder()
                .foodId(spot.getFoodId())
                .foodName(spot.getFoodName())
                .foodImg(ossDemoService.generatePresignedUrl(spot.getImageUrl(), 1000000000))
                .build()
        ).collect(java.util.stream.Collectors.toList());
        return FoodListVO.builder()
                .cityId(cityId)
                .foodList(coverList)
                .build();
    }

    @Override
    public DormitoryListVO queryCityDormitory(String cityId) {
        // 查询住宿封面列表
        List<DormitoryCoverVO> coverList = dormitoryMapper.selectList(
                new LambdaQueryWrapper<Dormitory>()
                        .eq(Dormitory::getCityId, cityId)
                        .select(
                                Dormitory::getDormitoryId,
                                Dormitory::getDormitoryName,
                                Dormitory::getImageUrl
                        )
        ).stream().map(spot -> DormitoryCoverVO.builder()
                .dormitoryId(spot.getDormitoryId())
                .dormitoryName(spot.getDormitoryName())
                .dormitoryImg(ossDemoService.generatePresignedUrl(spot.getImageUrl(), 1000000000))
                .build()
        ).collect(java.util.stream.Collectors.toList());
        return DormitoryListVO.builder()
                .cityId(cityId)
                .dormitoryList(coverList)
                .build();
    }


}
