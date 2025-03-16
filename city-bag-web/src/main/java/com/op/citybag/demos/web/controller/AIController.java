package com.op.citybag.demos.web.controller;

import com.op.citybag.demos.utils.SnowflakeIdWorker;
import com.op.citybag.demos.web.common.OPResult;
import com.op.citybag.demos.web.common.DTO.AI.AIDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;


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
                    .user(AIDTO.getInput()+ " 请用200字内的简洁回答")
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

}
