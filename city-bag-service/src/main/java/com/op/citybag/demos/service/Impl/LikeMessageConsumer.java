package com.op.citybag.demos.service.Impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.op.citybag.demos.mapper.CityMapper;
import com.op.citybag.demos.mapper.DormitoryMapper;
import com.op.citybag.demos.mapper.FoodMapper;
import com.op.citybag.demos.mapper.ScenicSpotMapper;
import com.op.citybag.demos.model.common.RedisKey;
import com.op.citybag.demos.model.message.LikeMessage;
import com.op.citybag.demos.redis.RedissonService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
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

    @RabbitListener(queues = "like.queue")
    public void processLikeMessage(LikeMessage message) {
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


        while (retry-- > 0) {
            T entity = mapper.selectById(id);
            if (entity == null) return;

            try {
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
                    String key = RedisKey.CITY_BAG + entityType + RedisKey.INFO + id;
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
