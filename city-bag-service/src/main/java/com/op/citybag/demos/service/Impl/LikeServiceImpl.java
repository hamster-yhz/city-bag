package com.op.citybag.demos.service.Impl;

import com.op.citybag.demos.mapper.CityMapper;
import com.op.citybag.demos.mapper.DormitoryMapper;
import com.op.citybag.demos.mapper.FoodMapper;
import com.op.citybag.demos.mapper.ScenicSpotMapper;
import com.op.citybag.demos.model.common.LuaScripts;
import com.op.citybag.demos.model.common.RedisKey;
import com.op.citybag.demos.model.message.LikeMessage;
import com.op.citybag.demos.rabbitmq.RabbitMQConfig;
import com.op.citybag.demos.redis.IRedisService;
import com.op.citybag.demos.service.ILikeService;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

/**
 * @Author: 原神
 * @Description:
 * @Date: 2025/3/3 19:36
 * @Version: 1.0
 */
@Service
@Slf4j
public class LikeServiceImpl implements ILikeService {

    @Autowired
    private IRedisService redisService;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private DormitoryMapper dormitoryMapper;

    @Autowired
    private CityMapper cityMapper;

    @Autowired
    private FoodMapper foodMapper;

    @Autowired
    private ScenicSpotMapper scenicSpotMapper;

    @Override
    public void toggleLike(String userId, String entityId, String entityType, String action) {
        String lockKey = RedisKey.LIKE_LOCK + entityType + ":" + entityId;
        String setKey = RedisKey.LIKE_SET + entityType + ":" + entityId;
        String userLockKey = RedisKey.LIKE_USER_LOCK + userId + ":" + entityType + ":" + entityId;

        RLock lock = null;

        try {

            log.info("尝试 {} ,用户:{},实体:{},类型:{}", action, userId, entityId, entityType);
            // 尝试获取分布式锁
            lock = redisService.getLock(lockKey);
            if (lock.tryLock(3, 10, TimeUnit.SECONDS)) {
                // 执行Lua脚本
                Long result = redisService.executeLuaScript(
                        LuaScripts.LIKE_SCRIPT,
                        Arrays.asList(setKey, userLockKey),
                        action, "864000", userId
                );


                if (result != null) {
                    int delta = result.intValue();
                    if (delta != 0) {
                        // 发送消息到消息队列
                        LikeMessage message = new LikeMessage(
                                entityType + ":" + entityId,
                                delta,
                                System.currentTimeMillis()
                        );
                        rabbitTemplate.convertAndSend(
                                RabbitMQConfig.LIKE_EXCHANGE,
                                RabbitMQConfig.LIKE_ROUTING_KEY,
                                message
                        );

                        // 立即更新Redis缓存(暂时不需要)
                        //updateRedisCache(entityType, entityId, result.intValue());
                    }
                }
            }
        } catch (InterruptedException e) {
            //TODO 异常处理
            Thread.currentThread().interrupt();
            log.error("操作被中断：{}", e.getMessage());
        } finally {
            redisService.unLock(lock);
        }
    }

    private void updateRedisCache(String entityType, String entityId, int delta) {
        String cacheKey = getCacheKey(entityType, entityId);
        RLock cacheLock = redisService.getLock(cacheKey + ":lock");

        try {
            if (cacheLock.tryLock(2, 5, TimeUnit.SECONDS)) {
                // 双检锁保证缓存更新原子性
                Object cached = redisService.getValue(cacheKey);
                if (cached == null) {
                    // 缓存不存在时从DB加载
                    Object entity = loadFromDB(entityType, entityId);
                    if (entity != null) {
                        redisService.setValue(cacheKey, entity, 3600 * 1000); // 1小时过期
                    }
                } else {
                    // 原子操作更新点赞数
                    redisService.executeLuaScript(
                            "redis.call('HINCRBY', KEYS[1], 'likeCount', ARGV[1])",
                            Collections.singletonList(cacheKey),
                            delta
                    );
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.error("缓存更新中断：{}", e.getMessage());
        } finally {
            cacheLock.unlock();
        }
    }

    private String getCacheKey(String entityType, String entityId) {
        return switch (entityType) {
            case "dormitory" -> RedisKey.DORMITORY_INFO + entityId;
            case "city" -> RedisKey.CITY_INFO + entityId;
            case "food" -> RedisKey.FOOD_INFO + entityId;
            case "scenic_spot" -> RedisKey.SCENIC_SPOT_INFO + entityId;
            default -> throw new IllegalArgumentException("无效的实体类型");
        };
    }

    private Object loadFromDB(String entityType, String entityId) {
        // 根据类型调用不同Mapper查询（需注入对应Mapper）
        return switch (entityType) {
            case "dormitory" -> dormitoryMapper.selectById(entityId);
            case "city" -> cityMapper.selectById(entityId);
            case "food" -> foodMapper.selectById(entityId);
            case "scenic_spot" -> scenicSpotMapper.selectById(entityId);
            default -> null;
        };
    }

}

