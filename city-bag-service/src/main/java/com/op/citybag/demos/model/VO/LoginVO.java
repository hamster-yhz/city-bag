package com.op.citybag.demos.model.VO;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @Author: 原神
 * @Description:
 * @Date: 2025/1/18 19:58
 * @Version: 1.0
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class LoginVO {

    /**
     * 访问令牌
     */
    private String accessToken;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 用户手机号
     */
    private String phone;

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
    private LocalDate updateTime;

}

