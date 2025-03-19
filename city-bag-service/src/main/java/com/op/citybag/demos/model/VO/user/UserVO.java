package com.op.citybag.demos.model.VO.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @Author: 原神
 * @Description:
 * @Date: 2025/3/2 20:17
 * @Version: 1.0
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class UserVO {

    /**
     * 用户id
     */
    private String userId;

    /**
     * 用户手机号
     */
    private String phone;

    /**
     * 用户头像
     */
    private String avatarUrl;

    /**
     * 用户学号
     */
    private String stuId;

    /**
     * 用户权mi==名
     */
    private String userName;

    /**
     * 用户性别
     */
    private Integer gender;

    /**
     * 用户生日
     */
    @JsonFormat(pattern = "yyyy/MM/dd", timezone = "GMT+8")
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate birthday;

    /**
     * 用户个性签名
     */
    private String personalizedSignature;

    /**
     * 权限
     * 0: 普通用户
     * 1-vip用户
     * 2-管理员
     * 3-超级管理员
     */
    private Integer jurisdiction;

    /**
     * 用户获赞数
     */
    private Integer likeCount;

    /**
     * 最近登陆时间
     */
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss", timezone = "GMT+8")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime updateTime;

}
