//package com.op.citybag.demos.web.controller;
//
//import com.op.citybag.demos.web.common.OPResult;
//import com.op.citybag.demos.web.common.dto.AIDto;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.ai.chat.client.ChatClient;
//import org.springframework.validation.annotation.Validated;
//import org.springframework.web.bind.annotation.*;
//
//import javax.servlet.http.HttpServletRequest;
//
///**
// * @Author: 原神
// * @Description:
// * @Date: 2025/1/19 17:08
// * @Version: 1.0
// */
//
//@Slf4j
//@Validated
//@RestController()
//@CrossOrigin("${app.config.cross-origin}")
//@RequestMapping("/api/${app.config.api-version}/ai/")
//public class AIController {
//
//    //    @Autowired
//    private final ChatClient chatClient;
//
//    public AIController(ChatClient.Builder chatClientBuilder) {
//        this.chatClient = chatClientBuilder.build();
//    }
//
//    @PostMapping("chat")
//    public OPResult chat(@RequestBody AIDto aiDto, HttpServletRequest request) {
//
//        String answer = chatClient.prompt()
//                .user(aiDto.getInput())
//                .call()
//                .content();
//        return OPResult.SUCCESS();
//
//    }
//
//}
