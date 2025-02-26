package com.op.citybag.demos.model.Entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.*;

import java.time.LocalDateTime;

/**
 * @Author: 原神
 * @Description: 美食实体类
 * @Date: 2025/2/26 19:46
 * @Version: 1.0
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName("food")
public class Food {
    @TableId(type = IdType.INPUT)
    private Long id;

    @TableField("food_id")
    private String foodId;

    @TableField("food_name")
    private String foodName;

    @TableField("introduce")
    private String introduce;

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

