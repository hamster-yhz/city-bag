package com.op.citybag.demos.model.common;

/**
 * @Author: 严豪哲
 * @Description: redis的key前缀
 * @Date: 2024/11/10 23:26
 * @Version: 1.0
 */

public class Common {

    public static final String OPENID = "openid";

    public static final String PASSWORD = "password";

    public static final String TABLE_LOGIC = "is_deleted";

    public static final Integer NOT_DELETE = 0;

    public static final String USER_ID = "user_id";

    public static final String USER_NAME = "user_name";

    public static final String CITY_ID = "city_id";

    public static final String CITY_NAME = "city_name";

    public static final String DORMITORY_ID = "dormitory_id";

    public static final String SCENIC_SPOT_ID = "scenic_spot_id";

    public static final String FOOD_ID = "food_id";

    public static final String PHONE_NUMBER = "phone_number";

    public static final String AUTH_KEY = "auth_key";

    public static final String AUTH_SECRET = "auth_secret";

    public static final String AUTH_TYPE = "auth_type";

    public static final Integer WX_USER = 1;

    public static final Integer STU_USER = 2;

    public static final long TRY_LOCK_TIME = 5;


    /**
     * 默认参数
     */
    public static final Integer DEFAULT_PAGE_NUM = 1;

    public static final Integer DEFAULT_PAGE_SIZE = 50;


    /**
     * 过期时间 秒
     */
    public static final Long MONTH = 30L * 24 * 60 * 60 * 1000L; // 一个月的豪秒数

    public static final Long REDIS_EXPIRE_TIME_10_MINUTES = 10 * 60 * 1000L;

    public static final Long REDIS_EXPIRE_TIME_30_MINUTES = 30 * 60 * 1000L;

    public static final Integer QUERY_COVER_TIME = 60 * 1000;


}
