package com.op.citybag.demos.model.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @Author: 原神
 * @Description: 全局服务响应状态码枚举
 * @Date: 2025/3/2 21:14
 * @Version: 1.0
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
public enum GlobalServiceStatusCode {
    /* 成功, 默认200 */
    SYSTEM_SUCCESS(200, "操作成功"),

    /* 系统错误 负数 */
    SYSTEM_SERVICE_FAIL(-4396, "操作失败"),
    SYSTEM_SERVICE_ERROR(-500, "系统异常"),
    SYSTEM_TIME_OUT(-1, "请求超时"),

    /* 参数错误：1001～2000 */
    PARAM_NOT_VALID(1001, "参数无效"),
    PARAM_IS_BLANK(1002, "参数为空"),
    PARAM_TYPE_ERROR(1003, "参数类型错误"),
    PARAM_NOT_COMPLETE(1004, "参数缺失"),
    PARAM_FAILED_VALIDATE(1005, "参数未通过验证"),

    REQUEST_NOT_VALID(1101, "请求无效"),

    /* 用户错误 2001-3000 */
    USER_NOT_LOGIN(2001, "用户未登录"),
    USER_CREDENTIALS_ERROR(2003, "密码错误"),
    //用户已存在
    USER_ACCOUNT_EXIST(2004, "用户已存在"),
    USER_ACCOUNT_DISABLE(2005, "账号不可用"),
    USER_ACCOUNT_NOT_EXIST(2007, "暂无此账号，请联系管理员"),
    USER_ACCOUNT_USE_BY_OTHERS(2009, "账号下线"),
    USER_ACCOUNT_REGISTER_ERROR(2010, "账号注册错误"),
    USER_UPDATE_ERROR(2011, "用户信息修改失败"),

    USER_TYPE_EXCEPTION(2101, "用户类别异常"),

    USER_NO_PERMISSION(2403, "用户无权限"),
    USER_CAPTCHA_CODE_ERROR(2500, "验证码错误,请重试"),

    /* 主页异常 3001-4000 */
    CITY_NOT_EXIST(3001, "城市不存在"),
    DORMITORY_NOT_EXIST(3002, "住宿不存在"),
    SCENIC_SPOT_NOT_EXIST(3003, "景点不存在"),
    FOOD_NOT_EXIST(3004, "美食不存在"),
    CITY_HAS_NO_SPOT(3005, "城市暂无景点"),
    ENTITY_NOT_EXIST(3006, "实体不存在"),
    OSS_ERROR(3002, "OSS异常"),

    /* 登录模块错误 6001-7000*/
    LOGIN_CODE_EXIT(6001, "需要等待一段时间后才能再次尝试"),
    LOGIN_ACCESS_TOKEN_MISSING(6006, "accessToken缺失"),
    LOGIN_ACCESS_TOKEN_EXPIRED(6007, "accessToken已过期"),
    LOGIN_ACCESS_TOKEN_INVALID(6008, "accessToken无效"),
    LOGIN_FAILED_TO_FORCE_LOGOUT(6010, "强制下线失败"),
    LOGIN_FAILED_TO_LOGOUT(6011, "登出失败"),
    LOGIN_UNKNOWN_ERROR(6012, "登陆模块未知错误,请联系管理员"),

    ;

    private Integer code;
    private String message;

}
