package com.op.citybag.demos.model.Entity;

import com.baomidou.mybatisplus.annotation.*;
import com.sun.jdi.Type;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("user_collection")
public class UserCollection {

    @TableId(type = IdType.AUTO)
    private Long id;

    @TableField("collection_id")
    private String collectionId;

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
