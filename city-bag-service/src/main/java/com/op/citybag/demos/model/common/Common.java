package com.op.citybag.demos.model.common;

/**
 * @Author: 严豪哲
 * @Description: redis的key前缀
 * @Date: 2024/11/10 23:26
 * @Version: 1.0
 */

public class Common {

    public static final String OPENID = "openid";

    public static final String TABLE_LOGIC = "is_deleted";

    public static final Integer NOT_DELETE = 0;

    public static final String USER_ID = "user_id";

    public static final long TRY_LOCK_TIME = 5;


    /**
     * 过期时间 秒
     */
    public static final Long MONTH = 30L * 24 * 60 * 60 * 1000; // 一个月的豪秒数

}
