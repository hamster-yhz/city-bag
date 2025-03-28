package com.op.citybag.demos.service.buildphoto;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author: 原神
 * @Description:
 * @Date: 2025/3/27 22:13
 * @Version: 1.0
 */

@Component
@Slf4j
public class BuildPhotoService implements ApplicationContextAware {

    private Map<StrategyEnum, ImageStrategy> imageStrategyMap = new ConcurrentHashMap<>();

    public String buildPhoto(StrategyEnum strategyEnum, String entityId, String entityName) {

        ImageStrategy imageStrategy = imageStrategyMap.get(strategyEnum);
        if (imageStrategy != null) {
            log.info("开始构建图片,entityId:{}",entityId);
            return imageStrategy.buildPhoto(entityId, entityName);
        }else {
            log.info("没有找到对应的策略,entityId:{}",entityId);
            return null;
        }
    }

    public List<String> buildPhotoList(StrategyEnum strategyEnum, String entityId, String entityName, Integer size) {

        ImageStrategy imageStrategy = imageStrategyMap.get(strategyEnum);
        if (imageStrategy != null) {
            log.info("开始构建图片,entityId:{}",entityId);
            return imageStrategy.buildPhotoList(entityId, entityName, size);
        }else {
            log.info("没有找到对应的策略,entityId:{}",entityId);
            return null;
        }
    }

    public List<String> buildPhotoList(StrategyEnum strategyEnum, String entityId, String entityName) {
        return buildPhotoList(strategyEnum, entityId, entityName, 5);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

        Map<String, ImageStrategy> beansOfType = applicationContext.getBeansOfType(ImageStrategy.class);

        beansOfType.values().forEach(imageStrategy -> {
            StrategyEnum name = imageStrategy.getName();
            imageStrategyMap.put(name, imageStrategy);
        });

    }

}
