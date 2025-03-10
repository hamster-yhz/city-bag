package com.op.citybag.demos.model.Entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.*;

import java.time.LocalDateTime;

/**
 * @Author: 原神
 * @Description:
 * @Date: 2025/3/6 21:04
 * @Version: 1.0
 */

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName("auth")
public class Auth {
    @TableId(type = IdType.AUTO)
    private Long id;

    @TableField("user_id")
    private String userId;

    @TableField("phone")
    private String phone;

    /**
     * 1:微信 2:学号
     */
    @TableField("auth_type")
    private Integer authType;

    @TableField("auth_key")
    private String authKey;

    @TableField("auth_secret")
    private String authSecret;

    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(value = "update_time", fill = FieldFill.UPDATE)
    private LocalDateTime updateTime;

    @TableLogic
    @TableField("is_deleted")
    private Integer isDeleted;

}
