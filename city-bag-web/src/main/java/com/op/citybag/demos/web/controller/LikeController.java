package com.op.citybag.demos.web.controller;

import com.op.citybag.demos.service.ILikeService;
import com.op.citybag.demos.web.common.DTO.like.LikeRequestDTO;
import com.op.citybag.demos.web.common.DTO.login.WxLoginDTO;
import com.op.citybag.demos.web.common.OPResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: 原神
 * @Description:
 * @Date: 2025/3/3 18:53
 * @Version: 1.0
 */
//${app.config.api-version}
@Slf4j
@Validated
@RestController()
@CrossOrigin("${app.config.cross-origin}")
@RequestMapping("/api/v1/auth/")//测试
@RequiredArgsConstructor
public class LikeController {

    @Autowired
    private final ILikeService likeService;

    @PostMapping("/toggle")
    public OPResult toggleLike(@RequestBody LikeRequestDTO likeRequestDTO) {

        try {
            log.info("点赞开始,用户");
            likeService.toggleLike(likeRequestDTO.getUserId(), likeRequestDTO.getEntityId(),likeRequestDTO.getEntityType(), likeRequestDTO.getAction());
            return OPResult.SUCCESS();
        }
        catch (Exception e) {
            return OPResult.FAIL(e);
        }

    }
}
