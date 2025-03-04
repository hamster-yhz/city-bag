package com.op.citybag.demos.model.Entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.*;

import java.time.LocalDateTime;

/**
 * @Author: 原神
 * @Description: 城市实体类
 * @Date: 2025/2/26 19:42
 * @Version: 1.0
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName("city")
public class City {
    @TableId(type = IdType.INPUT)
    private Long id;

    @TableField("city_id")
    private String cityId;

    @TableField("city_name")
    private String cityName;

    @TableField("city_introduce")
    private String cityIntroduce;

    @TableField("food_introduce")
    private String foodIntroduce;

    @TableField("scenic_spot_introduce")
    private String scenicSpotIntroduce;

    @TableField("image_url")
    private String imageUrl;

    @Version
    private Integer version;

    @TableField("like_count")
    private Integer likeCount;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.UPDATE)
    private LocalDateTime updateTime;

    @TableField("is_deleted")
    @TableLogic
    private Boolean deleted;
}


