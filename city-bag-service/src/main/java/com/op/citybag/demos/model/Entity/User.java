/*
 * Copyright 2013-2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.op.citybag.demos.model.Entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @Author: 原神
 * @Description: 用户实体类
 * @Date: 2025/2/26 19:42
 * @Version: 1.0
 */

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName("user")
public class User {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("user_id")
    private String userId;

    @TableField("user_name")
    private String userName;

    @TableField("english_name")
    private String englishName;

    @TableField("image_url")
    private String imageUrl;

    @TableField("phone")
    private String phone;

    /**
     * 1-微信
     * 2-学号
     */
    @TableField("auth_type")
    private Integer authType;

    @TableField("auth_key")
    private String authKey;

    @TableField("auth_secret")
    private String authSecret;

    @TableField("openid")
    private String openid;

    @TableField("unionid")
    private String unionid;

    @TableField("stu_id")
    private String stuId;

    @TableField("password")
    private String password;

    @TableField("gender")
    private Integer gender;

    @TableField("birthday")
    @JsonFormat(pattern = "yyyy/MM/dd", timezone = "GMT+8")
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate birthday;

    @TableField("personalized_signature")
    private String personalizedSignature;

    /**
     * 0: 普通用户
     * 1-vip用户
     * 2-管理员
     * 3-超级管理员
     */
    @TableField("jurisdiction")
    private Integer jurisdiction;

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
