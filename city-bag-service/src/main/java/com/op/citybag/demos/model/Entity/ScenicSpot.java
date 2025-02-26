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

    @TableField("introduce")
    private String introduce;

    @TableField("type")
    private Integer type;

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
