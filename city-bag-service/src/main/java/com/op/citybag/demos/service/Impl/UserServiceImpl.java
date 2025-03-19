package com.op.citybag.demos.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.op.citybag.demos.exception.AppException;
import com.op.citybag.demos.mapper.*;
import com.op.citybag.demos.model.Entity.*;
import com.op.citybag.demos.model.VO.user.CollectionListVO;
import com.op.citybag.demos.model.VO.user.CollectionVO;
import com.op.citybag.demos.model.VO.user.UserVO;
import com.op.citybag.demos.model.common.Common;
import com.op.citybag.demos.model.common.GlobalServiceStatusCode;
import com.op.citybag.demos.model.common.RedisKey;
import com.op.citybag.demos.oss.OSSService;
import com.op.citybag.demos.redis.RedissonService;
import com.op.citybag.demos.service.IUserService;
import com.op.citybag.demos.utils.Entity2VO;
import com.op.citybag.demos.utils.SnowflakeIdWorker;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @Author: 原神
 * @Description: 用户服务实现类
 * @Date: 2025/1/20 14:56
 * @Version: 1.0
 */

@Service
@Slf4j
@Transactional
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RedissonService redissonService;

    @Autowired
    private UserCollectionMapper userCollectionMapper;

    @Autowired
    private OSSService ossService;

    @Autowired
    private CityMapper cityMapper;

    @Autowired
    private DormitoryMapper dormitoryMapper;

    @Autowired
    private FoodMapper foodMapper;

    @Autowired
    private ScenicSpotMapper scenicSpotMapper;

    @Override
    public UserVO queryUserInfo(String userId) {
        log.info("尝试从redis中获取用户信息,userId: {}", userId);
        UserVO userInfo = redissonService.getValue(RedisKey.USER_INFO + userId);
        if (userInfo != null) {
            return userInfo;
        }

        log.info("从数据库中查询用户信息,userId: {}", userId);
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq(Common.USER_ID, userId);
        User user = userMapper.selectOne(wrapper);
        if (user != null) {
            UserVO userVO = Entity2VO.User2UserVO(user);
            userVO.setAvatarUrl(ossService.generatePresignedUrl(user.getImageUrl(), Common.QUERY_COVER_TIME));
            log.info("正在缓存用户信息,userId: {}", userId);
            redissonService.setValue(RedisKey.USER_INFO + userId, userVO);
            redissonService.setValueExpired(RedisKey.USER_INFO + userId, Common.REDIS_EXPIRE_TIME_10_MINUTES);
            return userVO;
        }
        return null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void modifyUserInfo(User user) {

        log.info("正在修改用户信息,userId: {}", user.getUserId());

        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq(Common.USER_ID, user.getUserId());

        int rowsAffected = userMapper.update(user, wrapper);
        if (rowsAffected > 0) {
            log.info("用户信息,修改成功,受影响的行数:{},userId: {}", rowsAffected, user.getUserId());
        } else {
            log.info("用户信息,修改失败,未找到匹配的用户或更新操作未执行,userId: {}", user.getUserId());
            throw new AppException(String.valueOf(GlobalServiceStatusCode.USER_UPDATE_ERROR.getCode()), GlobalServiceStatusCode.USER_UPDATE_ERROR.getMessage());
        }


    }

    @Override
    public void updateUserAvatar(String userId, MultipartFile file) {
        String avatarUrl = null;

        if (file != null) {
            avatarUrl = ossService.uploadCompressedImage(file, userId);
            log.info("用户头像上传成功,avatarUrl: {}", avatarUrl);
        }

        User user = User.builder().
                userId(userId)
                .imageUrl(avatarUrl)
                .build();
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq(Common.USER_ID, user.getUserId());
        int rowsAffected = userMapper.update(user, wrapper);
        if (rowsAffected > 0) {
            log.info("用户头像修改成功,受影响的行数:{},userId: {}", rowsAffected, user.getUserId());
        } else {
            log.info("用户头像修改失败,未找到匹配的用户或更新操作未执行,userId: {}", user.getUserId());
            throw new AppException(String.valueOf(GlobalServiceStatusCode.USER_UPDATE_ERROR.getCode()), GlobalServiceStatusCode.USER_UPDATE_ERROR.getMessage());
        }
    }

    @Override
    public void addCollection(String userId, String entityType, String entityId) {

        // 检查是否已收藏
        if (userCollectionMapper.exists(new LambdaQueryWrapper<UserCollection>()
                .eq(UserCollection::getUserId, userId)
                .eq(UserCollection::getEntityType, entityType)
                .eq(UserCollection::getEntityId, entityId)
                .eq(UserCollection::getIsDeleted, 0))) {
            throw new AppException(GlobalServiceStatusCode.ALREADY_COLLECTED.getMessage(), String.valueOf(GlobalServiceStatusCode.ALREADY_COLLECTED.getCode()));
        }

        String lockKey = RedisKey.COLLECTION_LOCK_PREFIX + userId + ":" + entityType + ":" + entityId;
        RLock lock = null;
        try {
            UserCollection collection = UserCollection.builder()
                    .collectionId(SnowflakeIdWorker.nextIdStr())
                    .userId(userId)
                    .entityType(entityType)
                    .entityId(entityId)
                    .build();
            log.info("正在尝试收藏,userId: {},entityType: {},entityId: {}", userId, entityType, entityId);
            lock = redissonService.getLock(lockKey);
            if (lock.tryLock(3, 10, TimeUnit.SECONDS)) {
                try {
                    userCollectionMapper.insert(collection);
                } catch (Exception e) {
                    throw new AppException(GlobalServiceStatusCode.ALREADY_COLLECTED.getMessage(), String.valueOf(GlobalServiceStatusCode.ALREADY_COLLECTED.getCode()));
                }
            }


        } catch (InterruptedException e) {
            //TODO 异常处理
            Thread.currentThread().interrupt();
            log.error("操作被中断：{}", e.getMessage());
        } finally {
            redissonService.unLock(lock);
        }
        log.info("收藏成功,userId: {},entityType: {},entityId: {}", userId, entityType, entityId);
    }

    @Override
    public Boolean queryCollection(String userId, String entityType, String entityId) {
        // 检查是否已收藏
        return userCollectionMapper.exists(new LambdaQueryWrapper<UserCollection>()
                .eq(UserCollection::getUserId, userId)
                .eq(UserCollection::getEntityType, entityType)
                .eq(UserCollection::getEntityId, entityId)
                .eq(UserCollection::getIsDeleted, 0));
    }

    @Override
    public void removeCollection(String userId, String entityType, String entityId) {

        log.info("正在取消收藏,userId: {},entityType: {},entityId: {}", userId, entityType, entityId);

        try {
            userCollectionMapper.delete(new LambdaUpdateWrapper<UserCollection>()
                    .eq(UserCollection::getUserId, userId)
                    .eq(UserCollection::getEntityType, entityType)
                    .eq(UserCollection::getEntityId, entityId)
                    .set(UserCollection::getIsDeleted, 1));
        } catch (Exception e) {
            log.error("取消收藏失败,userId: {},entityType: {},entityId: {}", userId, entityType, entityId);
            throw new AppException(GlobalServiceStatusCode.SYSTEM_SERVICE_ERROR.getMessage(), String.valueOf(GlobalServiceStatusCode.SYSTEM_SERVICE_ERROR.getCode()));
        }

        log.info("取消收藏成功,userId: {},entityType: {},entityId: {}", userId, entityType, entityId);
    }

    @Override
    public CollectionListVO getCollections(String userId, String entityType, Integer pageNum, Integer pageSize) {

        log.info("正在获取用户收藏列表,userId: {},entityType: {},pageNum: {},pageSize: {}", userId, entityType, pageNum, pageSize);
        Page<UserCollection> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<UserCollection> wrapper = new LambdaQueryWrapper<UserCollection>()
                .eq(UserCollection::getUserId, userId)
                .eq(UserCollection::getIsDeleted, Common.NOT_DELETE);

        if (entityType != null) {
            wrapper.eq(UserCollection::getEntityType, entityType);
        }

        IPage<UserCollection> result = userCollectionMapper.selectPage(page, wrapper);

        List<CollectionVO> collectionList = result.getRecords().stream().map(collection -> {
            CollectionVO collectionVO = new CollectionVO();
            collectionVO.setCollectionId(collection.getCollectionId());
            collectionVO.setEntityType(collection.getEntityType());
            collectionVO.setEntityId(collection.getEntityId());
            collectionVO.setCollectionTime(collection.getCreateTime());

            String entityImg = null;
            String entityName = null;


            // 根据不同类型获取信息
            switch (collection.getEntityType()) {
                case Common.CITY: // 城市
                    QueryWrapper<City> cityQueryWrapper = new QueryWrapper<City>()
                            .eq(Common.CITY_ID, collection.getEntityId());
                    City city = cityMapper.selectOne(cityQueryWrapper);
                    entityImg = ossService.generatePresignedUrl(city.getImageUrl(), 10000000);
                    entityName = city.getCityName();
                    break;
                case Common.SCENIC_SPOT: // 景点
                    QueryWrapper<ScenicSpot> scenicSpotQueryWrapper = new QueryWrapper<ScenicSpot>()
                            .eq(Common.SCENIC_SPOT_ID, collection.getEntityId());
                    ScenicSpot scenicSpot = scenicSpotMapper.selectOne(scenicSpotQueryWrapper);
                    entityImg = ossService.generatePresignedUrl(scenicSpot.getImageUrl(), 10000000);
                    entityName = scenicSpot.getScenicSpotName();
                    break;
                case Common.FOOD: // 美食
                    QueryWrapper<Food> foodQueryWrapper = new QueryWrapper<Food>()
                            .eq(Common.FOOD_ID, collection.getEntityId());
                    Food food = foodMapper.selectOne(foodQueryWrapper);
                    entityImg = ossService.generatePresignedUrl(food.getImageUrl(), 10000000);
                    entityName = food.getFoodName();
                    break;
                case Common.DORMITORY: // 住宿
                    QueryWrapper<Dormitory> dormitoryQueryWrapper = new QueryWrapper<Dormitory>()
                            .eq(Common.DORMITORY_ID, collection.getEntityId());
                    Dormitory dormitory = dormitoryMapper.selectOne(dormitoryQueryWrapper);
                    entityImg = ossService.generatePresignedUrl(dormitory.getImageUrl(), 10000000);
                    entityName = dormitory.getDormitoryName();
                    break;
                default:
                    break;
            }

            collectionVO.setEntityImg(entityImg);
            collectionVO.setEntityName(entityName);

            return collectionVO;
        }).collect(Collectors.toList());

        log.info("获取用户收藏列表成功,userId: {},entityType: {},pageNum: {},pageSize: {}", userId, entityType, pageNum, pageSize);

        return CollectionListVO.builder()
                .collectionList(collectionList)
                .pageNum(pageNum)
                .pageSize(pageSize)
                .total(result.getTotal())
                .build();
    }

}
