package com.op.citybag.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
public class SchedulerConfig {
    // 可选：配置自定义线程池
    // @Bean
    // public TaskScheduler taskScheduler() {
    //    ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
    //    scheduler.setPoolSize(5);
    //    scheduler.setThreadNamePrefix("scheduled-task-");
    //    return scheduler;
    // }
}
