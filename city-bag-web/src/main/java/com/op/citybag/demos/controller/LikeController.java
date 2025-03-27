package com.op.citybag.demos.controller;

import com.op.citybag.demos.common.OPResult;
import com.op.citybag.demos.service.ILikeService;
import com.op.citybag.demos.common.DTO.like.LikeRequestDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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
@RequestMapping("/api/v1/like/")//测试
@RequiredArgsConstructor
public class LikeController {

    @Autowired
    private final ILikeService likeService;

    /**
     * 对实体进行点赞或者取消点赞
     * 每个账户每天只能对每个实体进行一次点赞
     * 0点-0点05分之间进行重置
     * @param likeRequestDTO
     * @return
     */
    @PostMapping("/toggle")
    public OPResult toggleLike(@Valid @RequestBody LikeRequestDTO likeRequestDTO) {


            log.info("操作 {} 开始,用户:{},实体:{},类型:{}",likeRequestDTO.getAction(), likeRequestDTO.getUserId(), likeRequestDTO.getEntityId(), likeRequestDTO.getEntityType());
            likeService.toggleLike(likeRequestDTO.getUserId(), likeRequestDTO.getEntityId(),likeRequestDTO.getEntityType(), likeRequestDTO.getAction());
            log.info("操作 {} 成功,用户:{},实体:{},类型:{}",likeRequestDTO.getAction(), likeRequestDTO.getUserId(), likeRequestDTO.getEntityId(), likeRequestDTO.getEntityType());
            return OPResult.SUCCESS();


    }
}
