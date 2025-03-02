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
import com.op.citybag.demos.model.VO.page.object.DormitoryVO;
import com.op.citybag.demos.model.VO.page.object.FoodVO;
import com.op.citybag.demos.model.VO.page.object.ScenicSpotVO;
import com.op.citybag.demos.model.common.Common;
import com.op.citybag.demos.model.common.GlobalServiceStatusCode;
import com.op.citybag.demos.model.common.RedisKey;
import com.op.citybag.demos.oss.OSSServiceImpl;
import com.op.citybag.demos.redis.RedissonService;
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

    @Autowired
    private RedissonService redissonService;

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

    @Override
    public DormitoryVO querySingleDormitory(String dormitoryId) {
        String cacheKey = RedisKey.DORMITORY_INFO + dormitoryId;

        // 尝试从缓存获取
        DormitoryVO dormitoryVO = redissonService.getValue(cacheKey);
        if (dormitoryVO != null) {
            log.info("从缓存获取住宿信息: {}", dormitoryId);
            return dormitoryVO;
        }

        // 查询数据库
        Dormitory dormitory = dormitoryMapper.selectById(dormitoryId);
        if (dormitory == null) {
            log.info("未找到住宿信息: {}", dormitoryId);
            throw new AppException(String.valueOf(GlobalServiceStatusCode.DORMITORY_NOT_EXIST.getCode()), GlobalServiceStatusCode.DORMITORY_NOT_EXIST.getMessage());
        }

        // 转换VO
        dormitoryVO = Entity2VO.Dormitory2DormitoryVO(dormitory);
        dormitoryVO.setDormitoryImg(ossDemoService.generatePresignedUrl(dormitory.getImageUrl(), 1000000000));

        // 写入缓存
        redissonService.setValue(cacheKey, dormitoryVO, Common.REDIS_EXPIRE_TIME_30_MINUTES);
        return dormitoryVO;
    }

    @Override
    public ScenicSpotVO querySingleScenicSpot(String scenicSpotId) {
        String cacheKey = RedisKey.SCENIC_SPOT_INFO + scenicSpotId;

        ScenicSpotVO scenicSpotVO = redissonService.getValue(cacheKey);
        if (scenicSpotVO != null) {
            log.info("从缓存获取景点信息: {}", scenicSpotId);
            return scenicSpotVO;
        }

        ScenicSpot spot = scenicSpotMapper.selectById(scenicSpotId);
        if (spot == null) {
            log.info("未找到景点信息: {}", scenicSpotId);
            throw new AppException(String.valueOf(GlobalServiceStatusCode.SCENIC_SPOT_NOT_EXIST.getCode()), GlobalServiceStatusCode.SCENIC_SPOT_NOT_EXIST.getMessage());
        }

        scenicSpotVO = Entity2VO.ScenicSpot2ScenicSpotVO(spot);
        scenicSpotVO.setScenicSpotImg(ossDemoService.generatePresignedUrl(spot.getImageUrl(), 1000000000));

        redissonService.setValue(cacheKey, scenicSpotVO, Common.REDIS_EXPIRE_TIME_30_MINUTES);
        return scenicSpotVO;
    }

    @Override
    public FoodVO querySingleFood(String foodId) {
        String cacheKey = RedisKey.FOOD_INFO + foodId;

        FoodVO foodVO = redissonService.getValue(cacheKey);
        if (foodVO != null) {
            log.info("从缓存获取美食信息: {}", foodId);
            return foodVO;
        }

        Food food = foodMapper.selectById(foodId);
        if (food == null) {
            log.info("未找到美食信息: {}", foodId);
            throw new AppException(String.valueOf(GlobalServiceStatusCode.FOOD_NOT_EXIST.getCode()), GlobalServiceStatusCode.FOOD_NOT_EXIST.getMessage());
        }

        foodVO = Entity2VO.Food2FoodVO(food);
        foodVO.setFoodImg(ossDemoService.generatePresignedUrl(food.getImageUrl(), 1000000000));

        redissonService.setValue(cacheKey, foodVO, Common.REDIS_EXPIRE_TIME_30_MINUTES);
        return foodVO;
    }
}
