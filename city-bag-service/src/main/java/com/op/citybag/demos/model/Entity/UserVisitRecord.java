package com.op.citybag.demos.model.Entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Author: 原神
 * @Description:
 * @Date: 2025/3/22 17:12
 * @Version: 1.0
 */
@Data
@TableName("user_visit_record")
public class UserVisitRecord {

    @TableId(type = IdType.AUTO)
    private Long id;

    @TableField("visit_record_id")
    private String visitRecordId;

    @TableField("user_id")
    private String userId;

    @TableField("entity_type")
    private String entityType;

    @TableField("entity_id")
    private String entityId;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @TableLogic
    private Boolean isDeleted;
}
