package com.op.citybag.demos.common;

import lombok.Builder;
import lombok.Data;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: 原神
 * @Description:
 * @Date: 2025/3/16 22:15
 * @Version: 1.0
 */

@Data
@Builder
public class OPResultStream{

    private String code;
    private String msg;
    private Object data;

    public static OPResultStream STREAM(SseEmitter sseEmitter) {
        return OPResultStream.builder()
                .code("200")
                .msg("success")
                .data(Collections.singletonMap("type", "stream"))
                .build();
    }

    // 流式数据包装
    public static void sendStreamData(SseEmitter emitter, Object content) throws IOException {
        Map<String, Object> data = new HashMap<>();
        data.put("content", content);
        data.put("timestamp", System.currentTimeMillis());

        emitter.send(SseEmitter.event()
                .data(OPResultStream.builder()
                        .code("200")
                        .msg("success")
                        .data(data)
                        .build()
                ));
    }
}
