package com.op.citybag.demos.service.Impl.task;

import com.op.citybag.demos.redis.IRedisService;
import com.op.citybag.demos.model.common.RedisKey;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class LikeSyncScheduler {

    private final IRedisService redisService;

    // 每小时执行一次（整点执行）
    @Scheduled(cron = "0 #{new java.util.Random().nextInt(1)} * * * *")
    public void syncLikeCounts() {
        log.info("开始清除用户点赞数据");

        redisService.remove(RedisKey.LIKE_SET);

        log.info("清除用户点赞数据完成");
    }
}
