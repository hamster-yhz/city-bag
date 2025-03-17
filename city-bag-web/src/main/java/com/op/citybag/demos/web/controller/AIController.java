package com.op.citybag.demos.web.controller;

import com.op.citybag.demos.model.VO.page.list.CityListVO;
import com.op.citybag.demos.model.VO.page.list.DormitoryListVO;
import com.op.citybag.demos.model.VO.page.list.FoodListVO;
import com.op.citybag.demos.model.VO.page.list.ScenicSpotListVO;
import com.op.citybag.demos.model.VO.page.object.CityVO;
import com.op.citybag.demos.model.VO.page.object.DormitoryVO;
import com.op.citybag.demos.model.VO.page.object.FoodVO;
import com.op.citybag.demos.model.VO.page.object.ScenicSpotVO;
import com.op.citybag.demos.service.ICityService;
import com.op.citybag.demos.utils.SnowflakeIdWorker;
import com.op.citybag.demos.web.common.DTO.AI.AIDTO;
import com.op.citybag.demos.web.common.OPResult;
import com.op.citybag.demos.web.common.OPResultStream;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * @Author: 原神
 * @Description:
 * @Date: 2025/1/19 17:08
 * @Version: 1.0
 */

//${app.config.api-version}
@Slf4j
@Validated
@RestController()
@CrossOrigin("${app.config.cross-origin}")
@RequestMapping("/api/v1/ai/")//测试
@RequiredArgsConstructor
public class AIController {

    private final ChatClient chatClient;

    @Autowired
    private ICityService cityService;

    @Autowired
    public AIController(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    /**
     * AI聊天(非流式)
     *
     * @param AIDTO
     * @return
     */
    @PostMapping("chat")
    public OPResult chat(@RequestBody AIDTO AIDTO) {
        try {
            String answer = chatClient.prompt()
                    .user(AIDTO.getInput() + " 请用200字内的简洁回答")
                    .call()
                    .content();
            return OPResult.SUCCESS(answer);
        } catch (Exception e) {
            log.error("AI聊天失败,cuz:{}", e);
            return OPResult.FAIL(e);
        }

    }

    /**
     * AI聊天(流式)
     *
     * @param AIDTO
     * @return
     */
    @PostMapping("streamChat")
    public SseEmitter streamChat(@RequestBody AIDTO AIDTO) {

        SseEmitter emitter = new SseEmitter(30_000L); // 30秒超时

        chatClient.prompt()
                .user(u -> u.text(AIDTO.getInput() + " 请用200字内的简洁回答"))
                .stream()
                .content()
                .subscribe(
                        content -> {
                            try {
                                OPResultStream.sendStreamData(emitter, content);
                                emitter.send(SseEmitter.event()
                                        .data(content)
                                        .id(SnowflakeIdWorker.nextIdStr()));
                            } catch (IOException e) {
                                throw new RuntimeException("SSE发送失败", e);
                            }
                        },
                        emitter::completeWithError,
                        emitter::complete
                );

        return emitter;
    }

    /**
     * 知识问答
     * @param AIDTO
     * @return
     */
    @PostMapping("local-knowledge")
    public OPResult localKnowledgeChat(@RequestBody AIDTO AIDTO) {
        try {
            // 1. 从问题中提取关键信息
            String entities = extractEntities(AIDTO.getInput());

            if (entities == null) {
                return OPResult.FAIL("请用/包裹指定城市，例如：/北京/有哪些景点？");
            }

            // 2. 根据实体查询数据库
            String context = buildKnowledgeContext(entities);

            if (context == null) {
                return OPResult.FAIL("暂未收录该城市数据");
            }

            // 3. 构建带知识库的提示词
            String prompt = String.format("基于以下知识回答问题（200字内）：\n%s\n问题：%s",
                    context, AIDTO.getInput());

            // 4. 调用AI生成回答
            String answer = chatClient.prompt()
                    .user(prompt)
                    .call()
                    .content();

            return OPResult.SUCCESS(answer);
        } catch (Exception e) {
            log.error("知识问答失败", e);
            return OPResult.FAIL("暂时无法回答这个问题");
        }
    }

    // 关键词提取逻辑 可用NLP增强
    private String extractEntities(String question) {

        // 使用正则提取被/包裹的城市名称
        Pattern pattern = Pattern.compile("/(.*?)/");
        Matcher matcher = pattern.matcher(question);
        String entities = matcher.find() ? matcher.group(1) : null;

        return entities;
    }

    // 根据实体查询数据库
    private String buildKnowledgeContext(String params) {
        StringBuilder context = new StringBuilder();

        CityListVO cityListVO = cityService.queryCityLike(params);

        if (cityListVO.getCityList().isEmpty()) {
            return null;
        }

        String cityId = cityListVO.getCityList().get(0).getCityId();

        // 1. 获取城市基础信息
        CityVO city = cityService.querySingleCity(cityId);
        if (city != null) {
            context.append("【城市概况】\n")
                    .append("城市名称：").append(city.getCityName()).append("\n")
                    .append("城市介绍：").append(city.getCityIntroduce()).append("\n");
        }

        // 2. 获取美食数据
        FoodListVO foodListVO = cityService.queryCityFood(cityId, 1, 10);
        if (!foodListVO.getFoodList().isEmpty()) {
            context.append("【特色美食】\n");
            foodListVO.getFoodList().forEach(food -> context
                    .append("美食名称：").append(food.getFoodName())
                    .append("\n"));
        }

        // 3. 获取景点数据
        ScenicSpotListVO scenicSpotListVO = cityService.queryCityScenicSpot(cityId, 1, 10);
        if (!scenicSpotListVO.getScenicSpotList().isEmpty()) {
            context.append("【推荐景点】\n");
            scenicSpotListVO.getScenicSpotList().forEach(spot -> context
                    .append("景点名称：").append(spot.getScenicSpotName())
                    .append("景点地址：").append(spot.getAddress())
                    .append("景点参观时间：").append(spot.getVisitTime())
                    .append("\n"));
        }

        // 4. 获取住宿数据
        DormitoryListVO dormitoryListVO = cityService.queryCityDormitory(cityId, 1, 10);
        if (!dormitoryListVO.getDormitoryList().isEmpty()) {
            context.append("\n【住宿推荐】\n");
            dormitoryListVO.getDormitoryList().forEach(dorm -> context
                    .append("住宿名称：").append(dorm.getDormitoryName())
                    .append("\n"));
        }

        return context.toString();

    }

}



