package com.op.citybag.demos.model.Entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.*;

import java.time.LocalDateTime;

/**
 * @Author: 原神
 * @Description: 景点实体类
 * @Date: 2025/2/26 19:47
 * @Version: 1.0
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName("scenic_spot")
public class ScenicSpot {
    @TableId(type = IdType.INPUT)
    private Long id;

    @TableField("scenic_spot_id")
    private String scenicSpotId;

    @TableField("scenic_spot_name")
    private String scenicSpotName;

    @TableField("english_name")
    private String englishName;

    @TableField("introduce")
    private String introduce;

    @TableField("image_url")
    private String imageUrl;

    @TableField("open_time")
    private String openTime;

    @TableField("price")
    private String price;

    @TableField("address")
    private String address;

    @TableField("phone")
    private String phone;

    @TableField("visit_time")
    private String visitTime;

    @TableField("scenic_spot_list")
    private String scenicSpotList;

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
