package com.op.citybag.demos.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.op.citybag.demos.exception.AppException;
import com.op.citybag.demos.mapper.CityMapper;
import com.op.citybag.demos.mapper.DormitoryMapper;
import com.op.citybag.demos.mapper.FoodMapper;
import com.op.citybag.demos.mapper.ScenicSpotMapper;
import com.op.citybag.demos.model.Entity.City;
import com.op.citybag.demos.model.Entity.Dormitory;
import com.op.citybag.demos.model.Entity.Food;
import com.op.citybag.demos.model.Entity.ScenicSpot;
import com.op.citybag.demos.model.VO.page.cover.CityCoverVO;
import com.op.citybag.demos.model.VO.page.cover.DormitoryCoverVO;
import com.op.citybag.demos.model.VO.page.cover.FoodCoverVO;
import com.op.citybag.demos.model.VO.page.cover.ScenicSpotCoverVO;
import com.op.citybag.demos.model.VO.page.list.CityListVO;
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
import org.checkerframework.checker.units.qual.C;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
    public CityVO querySingleCity(String cityId) {

        String cacheKey = RedisKey.CITY_INFO + cityId;

        // 尝试从缓存获取
        CityVO cityVO = redissonService.getValue(cacheKey);
        if (cityVO != null) {
            log.info("从缓存获取住宿信息: {}", cityId);
            return cityVO;
        }

        // 查询数据库
        QueryWrapper<City> wrapper = new QueryWrapper<>();
        wrapper.eq(Common.CITY_ID, cityId);
        City city = cityMapper.selectOne(wrapper);

        if (city == null) {
            log.info("未找到城市: {}", cityId);
            throw new AppException(String.valueOf(GlobalServiceStatusCode.CITY_NOT_EXIST.getCode()), GlobalServiceStatusCode.CITY_NOT_EXIST.getMessage());
        }

        // 转换VO
        cityVO = Entity2VO.City2CityVO(city);
        cityVO.setCityImg(ossDemoService.generatePresignedUrl(city.getImageUrl(),  Common.QUERY_COVER_TIME));

        // 写入缓存
        redissonService.setValue(cacheKey, cityVO, Common.REDIS_EXPIRE_TIME_30_MINUTES);
        return cityVO;
    }

    @Override
    public CityListVO queryCityLike(String cityName, Integer pageNum, Integer pageSize) {

        Page<City> page = new Page<>(pageNum, pageSize);
        IPage<City> cityPage = cityMapper.selectPage(page,
                new QueryWrapper<City>().like(Common.CITY_NAME, cityName));

        List<CityCoverVO> coverList = cityPage.getRecords().stream()
                .map(city -> CityCoverVO.builder()
                        .cityId(city.getCityId())
                        .cityName(city.getCityName())
//                        .cityImg(ossDemoService.generatePresignedUrl(city.getImageUrl(), Common.QUERY_COVER_TIME))
                        .build())
                .collect(Collectors.toList());

        return CityListVO.builder()
                .cityList(coverList)
                .pageNum(pageNum)
                .pageSize(pageSize)
                .total(cityPage.getTotal())
                .build();
    }

    @Override
    public CityListVO queryCityLike(String cityName) {
        return queryCityLike(cityName, Common.DEFAULT_PAGE_NUM, Common.DEFAULT_PAGE_SIZE);
    }

    @Override
    public ScenicSpotListVO queryCityScenicSpot(String cityId, Integer pageNum, Integer pageSize) {

        // 构建分页对象
        Page<ScenicSpot> page = new Page<>(pageNum, pageSize);
        IPage<ScenicSpot> spotPage = scenicSpotMapper.selectPage(page,
                new LambdaQueryWrapper<ScenicSpot>()
                        .eq(ScenicSpot::getCityId, cityId));

        List<ScenicSpotCoverVO> coverList = spotPage.getRecords().stream()
                .map(spot -> ScenicSpotCoverVO.builder()
                        .scenicSpotId(spot.getScenicSpotId())
                        .scenicSpotName(spot.getScenicSpotName())
                        .scenicSpotImg(ossDemoService.generatePresignedUrl(spot.getImageUrl(),  Common.QUERY_COVER_TIME))
                        .visitTime(spot.getVisitTime())
                        .address(spot.getAddress())
                        .build())
                .collect(Collectors.toList());

        return ScenicSpotListVO.builder()
                .cityId(cityId)
                .scenicSpotList(coverList)
                .pageNum(pageNum)
                .pageSize(pageSize)
                .total(spotPage.getTotal())
                .build();
    }

    @Override
    public FoodListVO queryCityFood(String cityId, Integer pageNum, Integer pageSize) {
        // 查询美食封面列表
        Page<Food> page = new Page<>(pageNum, pageSize);
        IPage<Food> foodPage = foodMapper.selectPage(page,
                new LambdaQueryWrapper<Food>()
                        .eq(Food::getCityId, cityId)
                        .select(Food::getFoodId, Food::getFoodName, Food::getImageUrl));

        List<FoodCoverVO> coverList = foodPage.getRecords().stream()
                .map(food -> FoodCoverVO.builder()
                        .foodId(food.getFoodId())
                        .foodName(food.getFoodName())
                        .foodImg(ossDemoService.generatePresignedUrl(food.getImageUrl(),  Common.QUERY_COVER_TIME))
                        .build())
                .collect(Collectors.toList());

        return FoodListVO.builder()
                .cityId(cityId)
                .foodList(coverList)
                .pageNum(pageNum)
                .pageSize(pageSize)
                .total(foodPage.getTotal())
                .build();
    }

    @Override
    public DormitoryListVO queryCityDormitory(String cityId, Integer pageNum, Integer pageSize) {
        // 查询住宿封面列表
        Page<Dormitory> page = new Page<>(pageNum, pageSize);
        IPage<Dormitory> dormPage = dormitoryMapper.selectPage(page,
                new LambdaQueryWrapper<Dormitory>()
                        .eq(Dormitory::getCityId, cityId)
                        .select(Dormitory::getDormitoryId, Dormitory::getDormitoryName, Dormitory::getImageUrl));

        List<DormitoryCoverVO> coverList = dormPage.getRecords().stream()
                .map(dorm -> DormitoryCoverVO.builder()
                        .dormitoryId(dorm.getDormitoryId())
                        .dormitoryName(dorm.getDormitoryName())
                        .dormitoryImg(ossDemoService.generatePresignedUrl(dorm.getImageUrl(),  Common.QUERY_COVER_TIME))
                        .build())
                .collect(Collectors.toList());

        return DormitoryListVO.builder()
                .cityId(cityId)
                .dormitoryList(coverList)
                .pageNum(pageNum)
                .pageSize(pageSize)
                .total(dormPage.getTotal())
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
        QueryWrapper<Dormitory> wrapper = new QueryWrapper<>();
        wrapper.eq(Common.DORMITORY_ID, dormitoryId);
        Dormitory dormitory = dormitoryMapper.selectOne(wrapper);

        if (dormitory == null) {
            log.info("未找到住宿信息: {}", dormitoryId);
            throw new AppException(String.valueOf(GlobalServiceStatusCode.DORMITORY_NOT_EXIST.getCode()), GlobalServiceStatusCode.DORMITORY_NOT_EXIST.getMessage());
        }

        // 转换VO
        dormitoryVO = Entity2VO.Dormitory2DormitoryVO(dormitory);
        dormitoryVO.setDormitoryImg(ossDemoService.generatePresignedUrl(dormitory.getImageUrl(),  Common.QUERY_COVER_TIME));

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

        // 查询数据库
        QueryWrapper<ScenicSpot> wrapper = new QueryWrapper<>();
        wrapper.eq(Common.SCENIC_SPOT_ID, scenicSpotId);
        ScenicSpot spot = scenicSpotMapper.selectOne(wrapper);

        if (spot == null) {
            log.info("未找到景点信息: {}", scenicSpotId);
            throw new AppException(String.valueOf(GlobalServiceStatusCode.SCENIC_SPOT_NOT_EXIST.getCode()), GlobalServiceStatusCode.SCENIC_SPOT_NOT_EXIST.getMessage());
        }

        scenicSpotVO = Entity2VO.ScenicSpot2ScenicSpotVO(spot);
        scenicSpotVO.setScenicSpotImg(ossDemoService.generatePresignedUrl(spot.getImageUrl(),  Common.QUERY_COVER_TIME));

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

        // 查询数据库
        QueryWrapper<Food> wrapper = new QueryWrapper<>();
        wrapper.eq(Common.FOOD_ID, foodId);
        Food food = foodMapper.selectOne(wrapper);

        if (food == null) {
            log.info("未找到美食信息: {}", foodId);
            throw new AppException(String.valueOf(GlobalServiceStatusCode.FOOD_NOT_EXIST.getCode()), GlobalServiceStatusCode.FOOD_NOT_EXIST.getMessage());
        }

        foodVO = Entity2VO.Food2FoodVO(food);
        foodVO.setFoodImg(ossDemoService.generatePresignedUrl(food.getImageUrl(),  Common.QUERY_COVER_TIME));

        redissonService.setValue(cacheKey, foodVO, Common.REDIS_EXPIRE_TIME_30_MINUTES);
        return foodVO;
    }
}
