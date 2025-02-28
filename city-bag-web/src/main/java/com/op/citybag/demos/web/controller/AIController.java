package com.op.citybag.demos.web.controller;

import com.op.citybag.demos.web.common.OPResult;
import com.op.citybag.demos.web.common.dto.AI.AIDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


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
    public AIController(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    /**
     * AI聊天(非流式)
     * @param AIDTO
     * @return
     */
    @PostMapping("chat")
    public OPResult chat(@RequestBody AIDTO AIDTO) {
        try {
            String answer = chatClient.prompt()
                    .user(AIDTO.getInput())
                    .call()
                    .content();
            return OPResult.SUCCESS(answer);
        } catch (Exception e) {
            log.error("AI聊天失败,cuz:{}", e);
            return OPResult.FAIL(e);
        }

    }

}
