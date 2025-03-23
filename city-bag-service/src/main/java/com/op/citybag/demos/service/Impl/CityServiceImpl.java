package com.op.citybag.demos.service.Impl;

import com.alibaba.cloud.context.utils.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.op.citybag.demos.exception.AppException;
import com.op.citybag.demos.mapper.*;
import com.op.citybag.demos.model.Entity.*;
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
import com.op.citybag.demos.rabbitmq.RabbitMQConfig;
import com.op.citybag.demos.redis.RedissonService;
import com.op.citybag.demos.service.ICityService;
import com.op.citybag.demos.utils.Entity2VO;
import com.op.citybag.demos.utils.SplitUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
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

    @Autowired
    private UserCollectionMapper userCollectionMapper;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    public CityVO querySingleCity(String userId, String cityId) {

        String cacheKey = RedisKey.CITY_INFO + cityId;

        // 尝试从缓存获取
        CityVO cityVO = redissonService.getValue(cacheKey);
        if (cityVO != null) {
            log.info("从缓存获取城市信息: {}", cityId);
            // 设置收藏状态
            cityVO.setIsCollect(queryCollection(userId, Common.CITY, cityVO.getCityId()));
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

        // 设置封面图片
        cityVO.setCityImg(ossDemoService.generatePresignedUrl(city.getImageUrl(),  Common.QUERY_COVER_TIME));

        // 设置收藏状态
        cityVO.setIsCollect(queryCollection(userId, Common.CITY, cityVO.getCityId()));

        // 发送用户访问记录
        sendUserVisitRecord(userId, Common.CITY, cityVO.getCityId());

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
    public ScenicSpotListVO queryCityScenicSpot(String userId, String cityId, Integer pageNum, Integer pageSize) {

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
                        .IsCollect(queryCollection(userId, Common.SCENIC_SPOT, spot.getScenicSpotId()))
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
    public FoodListVO queryCityFood(String userId, String cityId, Integer pageNum, Integer pageSize) {
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
                        .IsCollect(queryCollection(userId, Common.FOOD, food.getFoodId()))
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
    public DormitoryListVO queryCityDormitory(String userId, String cityId, Integer pageNum, Integer pageSize) {
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
                        .IsCollect(queryCollection(userId, Common.DORMITORY, dorm.getDormitoryId()))
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
    public DormitoryVO querySingleDormitory(String dormitoryId, String userId) {
        String cacheKey = RedisKey.DORMITORY_INFO + dormitoryId;

        // 尝试从缓存获取
        DormitoryVO dormitoryVO = redissonService.getValue(cacheKey);
        if (dormitoryVO != null) {
            log.info("从缓存获取住宿信息: {}", dormitoryId);
            // 收藏
            dormitoryVO.setIsCollect(queryCollection(userId, Common.DORMITORY, dormitoryId));
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

        // 封面图
        dormitoryVO.setDormitoryImg(ossDemoService.generatePresignedUrl(dormitory.getImageUrl(),  Common.QUERY_COVER_TIME));

        // 相册图
        List<String> photoUrls = SplitUtil.splitBySlash(dormitory.getPhotoUrl()).stream()
                .map(url -> ossDemoService.generatePresignedUrl(url, Common.QUERY_COVER_TIME))
                .toList();
        dormitoryVO.setPhotoUrl(photoUrls);
        dormitoryVO.setPhotoCount(photoUrls.size());

        // 标签数量
        dormitoryVO.setTagCount(dormitoryVO.getTagList().size());

        // 收藏
        dormitoryVO.setIsCollect(queryCollection(userId, Common.DORMITORY, dormitoryId));

        // 发送用户访问记录
        sendUserVisitRecord(userId, Common.DORMITORY, dormitoryId);

        // 写入缓存
        redissonService.setValue(cacheKey, dormitoryVO, Common.REDIS_EXPIRE_TIME_30_MINUTES);
        return dormitoryVO;
    }

    @Override
    public ScenicSpotVO querySingleScenicSpot(String scenicSpotId, String userId) {
        String cacheKey = RedisKey.SCENIC_SPOT_INFO + scenicSpotId;

        ScenicSpotVO scenicSpotVO = redissonService.getValue(cacheKey);
        if (scenicSpotVO != null) {
            log.info("从缓存获取景点信息: {}", scenicSpotId);
            scenicSpotVO.setIsCollect(queryCollection(userId, Common.SCENIC_SPOT, scenicSpotId));
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

        // 转换VO
        scenicSpotVO = Entity2VO.ScenicSpot2ScenicSpotVO(spot);

        // 封面图
        scenicSpotVO.setScenicSpotImg(ossDemoService.generatePresignedUrl(spot.getImageUrl(),  Common.QUERY_COVER_TIME));

        // 相册图
        List<String> photoUrls = SplitUtil.splitBySlash(spot.getPhotoUrl()).stream()
                .map(url -> ossDemoService.generatePresignedUrl(url, Common.QUERY_COVER_TIME))
                .toList();
        scenicSpotVO.setPhotoUrl(photoUrls);
        scenicSpotVO.setPhotoCount(photoUrls.size());

        // 标签数量
        scenicSpotVO.setTagCount(scenicSpotVO.getTagList().size());

        // 收藏
        scenicSpotVO.setIsCollect(queryCollection(userId, Common.SCENIC_SPOT, scenicSpotId));

        // 记录用户访问记录
        sendUserVisitRecord(userId, Common.SCENIC_SPOT, scenicSpotId);

        // 写入缓存
        redissonService.setValue(cacheKey, scenicSpotVO, Common.REDIS_EXPIRE_TIME_30_MINUTES);

        return scenicSpotVO;
    }

    @Override
    public FoodVO querySingleFood(String foodId, String userId) {

        // 尝试从缓存获取
        String cacheKey = RedisKey.FOOD_INFO + foodId;
        FoodVO foodVO = redissonService.getValue(cacheKey);
        if (foodVO != null) {
            foodVO.setIsCollect(queryCollection(userId, Common.FOOD, foodId));
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

        // 转换VO
        foodVO = Entity2VO.Food2FoodVO(food);

        // 封面图
        foodVO.setFoodImg(ossDemoService.generatePresignedUrl(food.getImageUrl(),  Common.QUERY_COVER_TIME));

        // 相册图
        List<String> photoUrls = SplitUtil.splitBySlash(food.getPhotoUrl()).stream()
                .map(url -> ossDemoService.generatePresignedUrl(url, Common.QUERY_COVER_TIME))
                .toList();
        foodVO.setPhotoUrl(photoUrls);
        foodVO.setPhotoCount(photoUrls.size());

        // 标签数量
        foodVO.setTagCount(foodVO.getTagList().size());

        // 收藏
        foodVO.setIsCollect(queryCollection(userId, Common.FOOD, foodId));

        // 记录浏览历史
        sendUserVisitRecord(userId, Common.FOOD, foodId);

        // 写入缓存
        redissonService.setValue(cacheKey, foodVO, Common.REDIS_EXPIRE_TIME_30_MINUTES);

        return foodVO;
    }

    /**
     * 查询是否已收藏
     * @param userId
     * @param entityType
     * @param entityId
     * @return
     */
    private Integer queryCollection(String userId, String entityType, String entityId) {
        // 未登录
        if (StringUtils.isEmpty(userId)) {
            return 0;
        }
        // 检查是否已收藏
        if(userCollectionMapper.exists(new LambdaQueryWrapper<UserCollection>()
                .eq(UserCollection::getUserId, userId)
                .eq(UserCollection::getEntityType, entityType)
                .eq(UserCollection::getEntityId, entityId)
                .eq(UserCollection::getIsDeleted, 0))){
            return 1;
        }else{
            return 0;
        }
    }

    /**
     * 发送用户访问记录
     * @param userId
     * @param entityType
     * @param entityId
     */
    private void sendUserVisitRecord(String userId, String entityType, String entityId) {
        if (StringUtils.isEmpty(userId)) {
            return;
        }
        UserVisitRecord record = new UserVisitRecord();
        record.setUserId(userId);
        record.setEntityType(entityType);
        record.setEntityId(entityId);
        rabbitTemplate.convertAndSend(RabbitMQConfig.USER_VISIT_QUEUE, record);
    }

}
