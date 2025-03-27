package com.op.citybag.demos.model.Entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.*;

import java.time.LocalDateTime;

/**
 * @Author: 原神
 * @Description:
 * @Date: 2025/2/26 19:43
 * @Version: 1.0
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName("dormitory")
public class Dormitory {
    @TableId(type = IdType.INPUT)
    private Long id;

    @TableField("dormitory_id")
    private String dormitoryId;

    @TableField("dormitory_name")
    private String dormitoryName;

    @TableField("introduce")
    private String introduce;

    @TableField("image_url")
    private String imageUrl;

    @TableField("price")
    private String price;

    @TableField("address")
    private String address;

    @TableField("phone")
    private String phone;

    @TableField("photo_url")
    private String photoUrl;

    @TableField("tag_list")
    private String tagList;

    @TableField("type")
    private Integer type;

    @Version
    private Integer version;

    @TableField("like_count")
    private Integer likeCount;

    @TableField("city_id")
    private String cityId;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.UPDATE)
    private LocalDateTime updateTime;

    @TableField("is_deleted")
    @TableLogic
    private Boolean deleted;
}
