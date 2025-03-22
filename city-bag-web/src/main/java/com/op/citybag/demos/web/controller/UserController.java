package com.op.citybag.demos.web.controller;

import com.op.citybag.demos.model.Entity.User;
import com.op.citybag.demos.model.VO.page.list.CollectionListVO;
import com.op.citybag.demos.model.VO.page.list.UserVisitRecordListVO;
import com.op.citybag.demos.model.VO.user.UserVO;
import com.op.citybag.demos.service.IUserService;
import com.op.citybag.demos.web.common.DTO.user.CollectionDTO;
import com.op.citybag.demos.web.common.DTO.user.CollectionOrHistoryQueryDTO;
import com.op.citybag.demos.web.common.DTO.user.UserDTO;
import com.op.citybag.demos.web.common.OPResult;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


/**
 * @Author: 原神
 * @Description:
 * @Date: 2025/1/18 18:51
 * @Version: 1.0
 */

//${app.config.api-version}
@Slf4j
@Validated
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
//    @LoginVerification
//    @SelfPermissionVerification
    @PostMapping("modifyUserInfo")
    public OPResult modifyUserInfo(@RequestBody UserDTO userDTO) {
        try {
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
        } catch (Exception e) {
            log.error("修改个人信息失败,userId:{},cuz:{}", userDTO.getUserId(), e.getMessage());
            return OPResult.FAIL(e);
        }
    }

    /**
     * 头像上传 (文件大小限制为5MB)
     * @param file
     * @param userId
     * @return
     */
    @PostMapping("uploadAvatar")
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
    public OPResult queryUserInfo(@RequestBody UserDTO userDTO, HttpServletRequest request) {
        try {
            UserVO userInfo = userService.queryUserInfo(userDTO.getUserId());
            log.info("查询个人信息成功,userId:{}", userInfo.getUserId());
            return OPResult.SUCCESS(userInfo);
        } catch (Exception e) {
            log.error("查询个人信息失败,userId:{},cuz:{}", userDTO.getUserId(), e.getMessage());
            return OPResult.FAIL(e);
        }
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
    public OPResult addCollection(@RequestBody CollectionDTO collectionDTO) {
        try {
            userService.addCollection(collectionDTO.getUserId(),
                    collectionDTO.getEntityType(), collectionDTO.getEntityId());
            return OPResult.SUCCESS();
        } catch (Exception e) {
            log.error("收藏失败 userId:{}", collectionDTO.getUserId(), e);
            return OPResult.FAIL(e);
        }
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
    public OPResult removeCollection(@RequestBody CollectionDTO collectionDTO) {
        try {
            userService.removeCollection(collectionDTO.getUserId(),
                    collectionDTO.getEntityType(), collectionDTO.getEntityId());
            return OPResult.SUCCESS();
        } catch (Exception e) {
            log.error("取消收藏失败 userId:{}", collectionDTO.getUserId(), e);
            return OPResult.FAIL(e);
        }
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
    public OPResult getCollections(@RequestBody CollectionOrHistoryQueryDTO collectionOrHistoryQueryDTO) {
        try {
            log.info("获取收藏列表开始 userId:{}", collectionOrHistoryQueryDTO.getUserId());

            CollectionListVO collections = userService.getCollections(collectionOrHistoryQueryDTO.getUserId(), collectionOrHistoryQueryDTO.getEntityType(), collectionOrHistoryQueryDTO.getPageNum(), collectionOrHistoryQueryDTO.getPageSize());
            return OPResult.SUCCESS(collections);

        } catch (Exception e) {
            log.error("获取收藏列表失败 userId:{}", collectionOrHistoryQueryDTO.getUserId(), e);
            return OPResult.FAIL(e);
        }
    }

    /**
     * 获取用户浏览历史
     * @param
     */
    @PostMapping("history")
    public OPResult getHistory(@RequestBody CollectionOrHistoryQueryDTO collectionOrHistoryQueryDTO) {
        try {
            log.info("获取用户浏览历史开始 userId:{}", collectionOrHistoryQueryDTO.getUserId());

            UserVisitRecordListVO userVisitRecords = userService.getUserVisitRecords(collectionOrHistoryQueryDTO.getUserId(), collectionOrHistoryQueryDTO.getEntityType(), collectionOrHistoryQueryDTO.getPageNum(), collectionOrHistoryQueryDTO.getPageSize());

            return OPResult.SUCCESS(userVisitRecords);
        } catch (Exception e){
            log.error("获取用户浏览历史失败 userId:{}", collectionOrHistoryQueryDTO.getUserId(), e);
            return OPResult.FAIL(e);
        }
    }

}
