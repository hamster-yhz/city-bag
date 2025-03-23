package com.op.citybag.demos.consumer;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.op.citybag.demos.exception.AppException;
import com.op.citybag.demos.mapper.UserVisitRecordMapper;
import com.op.citybag.demos.model.Entity.UserCollection;
import com.op.citybag.demos.model.Entity.UserVisitRecord;
import com.op.citybag.demos.model.common.Common;
import com.op.citybag.demos.model.common.GlobalServiceStatusCode;
import com.op.citybag.demos.model.common.RedisKey;
import com.op.citybag.demos.rabbitmq.RabbitMQConfig;
import com.op.citybag.demos.redis.IRedisService;
import com.op.citybag.demos.utils.SnowflakeIdWorker;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

/**
 * @Author: 原神
 * @Description:
 * @Date: 2025/3/22 17:14
 * @Version: 1.0
 */
@Component
@Slf4j
public class UserVisitRecordConsumer {

    @Autowired
    private UserVisitRecordMapper userVisitRecordMapper;

    @Autowired
    private IRedisService redisService;

    @RabbitListener(queues = RabbitMQConfig.USER_VISIT_QUEUE)
    public void receiveMessage(UserVisitRecord record) {

        String userId = record.getUserId();
        String entityType = record.getEntityType();
        String entityId = record.getEntityId();

        // 判断是否已记录
        LambdaQueryWrapper<UserVisitRecord> queryWrapper = new LambdaQueryWrapper<UserVisitRecord>()
                .eq(UserVisitRecord::getUserId, userId)
                .eq(UserVisitRecord::getEntityType, entityType)
                .eq(UserVisitRecord::getEntityId, entityId)
                .eq(UserVisitRecord::getIsDeleted, 0);
        UserVisitRecord userVisitRecord = userVisitRecordMapper.selectOne(queryWrapper);
        if (userVisitRecord != null) {
            // 已记录,更新时间
            userVisitRecordMapper.update(record, queryWrapper);
            return;
        }

        String lockKey = RedisKey.VISIT_RECORD_LOCK_PREFIX + userId + ":" + entityType + ":" + entityId;
        RLock lock = null;
        try {

            log.info("正在尝试记录浏览历史,userId: {},entityType: {},entityId: {}", userId, entityType, entityId);
            lock = redisService.getLock(lockKey);
            if (lock.tryLock(3, 10, TimeUnit.SECONDS)) {
                try {
                    userVisitRecord = userVisitRecordMapper.selectOne(queryWrapper);
                    if (userVisitRecord != null) {
                        // 已记录,更新时间
                        userVisitRecordMapper.update(record, queryWrapper);
                        return;
                    }
                    userVisitRecordMapper.insert(record);
                } catch (Exception e) {
                    throw new AppException(GlobalServiceStatusCode.ALREADY_COLLECTED.getMessage(), String.valueOf(GlobalServiceStatusCode.ALREADY_COLLECTED.getCode()));
                }
            }

        } catch (InterruptedException e) {
            //TODO 异常处理
            Thread.currentThread().interrupt();
            log.error("操作被中断：{}", e.getMessage());
        } finally {
            redisService.unLock(lock);
        }
        log.info("记录浏览记录成功,userId: {},entityType: {},entityId: {}", userId, entityType, entityId);

    }
}