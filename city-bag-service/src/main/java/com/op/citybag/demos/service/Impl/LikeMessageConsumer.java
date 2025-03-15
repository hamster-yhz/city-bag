package com.op.citybag.demos.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.op.citybag.demos.exception.AppException;
import com.op.citybag.demos.mapper.CityMapper;
import com.op.citybag.demos.mapper.DormitoryMapper;
import com.op.citybag.demos.mapper.FoodMapper;
import com.op.citybag.demos.mapper.ScenicSpotMapper;
import com.op.citybag.demos.model.common.GlobalServiceStatusCode;
import com.op.citybag.demos.model.common.RedisKey;
import com.op.citybag.demos.model.message.LikeMessage;
import com.op.citybag.demos.rabbitmq.RabbitMQConfig;
import com.op.citybag.demos.redis.RedissonService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Component
@RequiredArgsConstructor
@Slf4j
public class LikeMessageConsumer {

    private final CityMapper cityMapper;

    private final FoodMapper foodMapper;

    private final DormitoryMapper dormitoryMapper;

    private final ScenicSpotMapper scenicSpotMapper;

    private final RedissonService redissionService;

    @RabbitListener(queues = RabbitMQConfig.LIKE_QUEUE)
    public void processLikeMessage(@Payload LikeMessage message,
                                   @Header(AmqpHeaders.CONSUMER_QUEUE) String queue) {
        String[] parts = message.getEntityId().split(":");
        String entityType = parts[0];
        String id = parts[1];

        switch (entityType) {
            case "city":
                updateWithOptimisticLock(cityMapper, entityType, id, message.getDelta());
                break;
            case "food":
                updateWithOptimisticLock(foodMapper, entityType,id, message.getDelta());
                break;
            case "dormitory":
                updateWithOptimisticLock(dormitoryMapper, entityType, id, message.getDelta());
                break;
            case "scenic_spot":
                updateWithOptimisticLock(scenicSpotMapper, entityType, id, message.getDelta());
                break;
        }
    }

    private <T> void updateWithOptimisticLock(BaseMapper<T> mapper, String entityType, String id, int delta) {
        int retry = 3;

        // 根据实体类型确定业务ID字段名
//        String bizIdField = switch (entityType) {
//            case "city" -> "city_id";
//            case "food" -> "food_id";
//            case "dormitory" -> "dormitory_id";
//            case "scenic_spot" -> "scenic_spot_id";
//            default -> throw new IllegalArgumentException("无效的实体类型");
//        };

        // 按业务ID查询
        QueryWrapper<T> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(entityType+"_id", id);
        T entity = mapper.selectOne(queryWrapper);

        if (entity == null) {
            log.info("未找到对应实体 entityType:{} bizId:{}", entityType, id);
            throw new AppException(GlobalServiceStatusCode.ENTITY_NOT_EXIST.getMessage(), String.valueOf(GlobalServiceStatusCode.ENTITY_NOT_EXIST.getCode()));
        }

        while (retry-- > 0) {

            try {
                log.info("正在尝试从数据库更新 entityType:{} : id:{} 的点赞数量", entityType, id);
                // 反射设置version和likeCount
                Method setVersion = entity.getClass().getMethod("setVersion", Integer.class);
                Method getVersion = entity.getClass().getMethod("getVersion");
                Method setLikeCount = entity.getClass().getMethod("setLikeCount", Integer.class);

                Integer currentVersion = (Integer) getVersion.invoke(entity);
                setVersion.invoke(entity, currentVersion + 1);
                setLikeCount.invoke(entity, (Integer) entity.getClass()
                        .getMethod("getLikeCount").invoke(entity) + delta);

                UpdateWrapper<T> wrapper = new UpdateWrapper<>();
                wrapper.eq(entityType+"_id", id)
                        .eq("version", currentVersion);

                if (mapper.update(entity, wrapper) > 0) {
                    // 更新成功清除本地缓存 Redis+DB双写
                    String key = RedisKey.CITY_BAG_CACHE + entityType + RedisKey.INFO + id;
                    redissionService.remove(key);
                    return;
                }
            } catch (Exception e) {
                log.info("更新点赞数失败: {}", e.getMessage());
            }
        }
        log.info("乐观锁重试失败 entityType:{} id:{}", entityType, id);
    }
}
