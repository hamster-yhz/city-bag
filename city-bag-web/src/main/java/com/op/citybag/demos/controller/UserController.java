package com.op.citybag.demos.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.op.citybag.demos.common.DTO.user.CollectionDTO;
import com.op.citybag.demos.common.DTO.user.CollectionOrHistoryQueryDTO;
import com.op.citybag.demos.common.DTO.user.UserDTO;
import com.op.citybag.demos.common.OPResult;
import com.op.citybag.demos.mapper.UserMapper;
import com.op.citybag.demos.model.Entity.User;
import com.op.citybag.demos.model.VO.page.list.CollectionListVO;
import com.op.citybag.demos.model.VO.page.list.UserVisitRecordListVO;
import com.op.citybag.demos.model.VO.user.UserVO;
import com.op.citybag.demos.service.IUserService;
import com.op.citybag.demos.constraint.LoginVerification;
import com.op.citybag.demos.constraint.SelfPermissionVerification;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;


/**
 * @Author: 原神
 * @Description:
 * @Date: 2025/1/18 18:51
 * @Version: 1.0
 */

//${app.config.api-version}
@Slf4j
@RestController()
@CrossOrigin("${app.config.cross-origin}")
@RequestMapping("/api/v1/user/")//测试
@RequiredArgsConstructor
public class UserController {

    @Autowired
    private final IUserService userService;

    /**
     * 修改个人信息
     */
    @LoginVerification
    @SelfPermissionVerification
    @PostMapping("modifyUserInfo")
    public OPResult modifyUserInfo(@Valid @RequestBody UserDTO userDTO) {

            log.info("修改个人信息开始,userId:{}", userDTO.getUserId());
            User user = User.builder()
                    .userId(userDTO.getUserId())
                    .userName(userDTO.getUserName())
                    .gender(userDTO.getGender())
                    .birthday(userDTO.getBirthday())
                    .personalizedSignature(userDTO.getPersonalizedSignature())
                    .build();
            userService.modifyUserInfo(user);
            log.info("修改个人信息成功,userId:{}", userDTO.getUserId());
            return OPResult.SUCCESS();

    }

    /**
     * 头像上传 (文件大小限制为5MB)
     * @param file
     * @param userId
     * @return
     */
    @PostMapping("uploadAvatar")
//    @LoginVerification
//    @SelfPermissionVerification
    public OPResult uploadAvatar(
            @RequestPart("file") MultipartFile file,
            @RequestHeader("userId") String userId) {

        if(userId == null){
            return OPResult.FAIL("userId不能为空");
        }

        try {
            log.info("头像上传开始,userId:{}", userId);

            userService.updateUserAvatar(userId, file);

            log.info("头像上传成功,userId:{}", userId);
            return OPResult.SUCCESS();
        } catch (Exception e) {
            log.error("头像上传失败", e);
            return OPResult.FAIL(e);
        }
    }

    /**
     * 查询个人信息
     */
//    @LoginVerification
//    @SelfPermissionVerification
    @PostMapping("queryUserInfo")
    public OPResult queryUserInfo(@Valid @RequestBody UserDTO userDTO, HttpServletRequest request) {

            UserVO userInfo = userService.queryUserInfo(userDTO.getUserId());
            log.info("查询个人信息成功,userId:{}", userInfo.getUserId());
            return OPResult.SUCCESS(userInfo);

    }

    /**
     * 收藏
     *
     * @param collectionDTO
     * @return
     */
//    @SelfPermissionVerification
//    @LoginVerification
    @PostMapping("collect")
    public OPResult addCollection(@Valid @RequestBody CollectionDTO collectionDTO) {

            userService.addCollection(collectionDTO.getUserId(),
                    collectionDTO.getEntityType(), collectionDTO.getEntityId());
            return OPResult.SUCCESS();

    }

    /**
     * 取消收藏
     *
     * @param collectionDTO
     * @return
     */
    //    @SelfPermissionVerification
//    @LoginVerification
    @PostMapping("uncollect")
    public OPResult removeCollection(@Valid @RequestBody CollectionDTO collectionDTO) {

            userService.removeCollection(collectionDTO.getUserId(),
                    collectionDTO.getEntityType(), collectionDTO.getEntityId());
            return OPResult.SUCCESS();

    }

    /**
     * 获取收藏列表
     *
     * @param collectionOrHistoryQueryDTO
     * @return
     */
//    @SelfPermissionVerification
//    @LoginVerification
    @PostMapping("collections")
    public OPResult getCollections(@Valid @RequestBody CollectionOrHistoryQueryDTO collectionOrHistoryQueryDTO) {

            log.info("获取收藏列表开始 userId:{}", collectionOrHistoryQueryDTO.getUserId());

            CollectionListVO collections = userService.getCollections(collectionOrHistoryQueryDTO.getUserId(), collectionOrHistoryQueryDTO.getEntityType(), collectionOrHistoryQueryDTO.getPageNum(), collectionOrHistoryQueryDTO.getPageSize());
            return OPResult.SUCCESS(collections);

    }

    /**
     * 获取用户浏览历史
     * @param
     */
    @PostMapping("history")
    public OPResult getHistory(@Valid @RequestBody CollectionOrHistoryQueryDTO collectionOrHistoryQueryDTO) {

            log.info("获取用户浏览历史开始 userId:{}", collectionOrHistoryQueryDTO.getUserId());

            UserVisitRecordListVO userVisitRecords = userService.getUserVisitRecords(collectionOrHistoryQueryDTO.getUserId(), collectionOrHistoryQueryDTO.getEntityType(), collectionOrHistoryQueryDTO.getPageNum(), collectionOrHistoryQueryDTO.getPageSize());

            return OPResult.SUCCESS(userVisitRecords);

    }

}
