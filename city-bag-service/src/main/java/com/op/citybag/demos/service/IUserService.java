package com.op.citybag.demos.service;

import com.op.citybag.demos.model.Entity.User;
import com.op.citybag.demos.model.VO.user.CollectionListVO;
import com.op.citybag.demos.model.VO.user.CollectionVO;
import com.op.citybag.demos.model.VO.user.UserVO;
import org.springframework.web.multipart.MultipartFile;

public interface IUserService {

    /**
     * 查询用户信息
     *
     * @param userId
     * @return
     */
    UserVO queryUserInfo(String userId);

    /**
     * 更改用户信息
     *
     * @param user
     * @return
     */
    void modifyUserInfo(User user);

    /**
     * 更改用户头像
     *
     * @param userId
     * @param file
     * @return
     */
    void updateUserAvatar(String userId, MultipartFile file);

    /**
     * 添加收藏
     *
     * @param userId
     * @param entityType
     * @param entityId
     * @return
     */
    void addCollection(String userId, String entityType, String entityId);

    /**
     * 查询是否收藏
     *
     * @param userId
     * @param entityType
     * @param entityId
     * @return
     */
    Boolean queryCollection(String userId, String entityType, String entityId);

    /**
     * 移除收藏
     *
     * @param userId
     * @param entityType
     * @param entityId
     * @return
     */
    void removeCollection(String userId, String entityType, String entityId);

    /**
     * 获取收藏列表
     *
     * @param
     * @return
     */
    CollectionListVO getCollections(String userId, String entityType, Integer pageNum, Integer pageSize);
}
