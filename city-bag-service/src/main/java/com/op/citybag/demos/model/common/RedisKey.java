package com.op.citybag.demos.model.common;

/**
 * @Author: 严豪哲
 * @Description: redis的key前缀
 * @Date: 2024/11/10 23:26
 * @Version: 1.0
 */

public class RedisKey {

    /**
     * token
     */
    public static final String TOKEN = "op:city_bag:login:token:";

    public static final String ACCESS_TOKEN = "op:city_bag:login:token:access_token:";

    public static final String REFRESH_TOKEN = "op:city_bag:login:token:refresh_token:";

    public final static String USER_TO_TOKEN = "op:city_bag:login:user_to_token";

    /**
     * 缓存
     */
    public final static String USER_INFO = "op:city_bag:cache:user_info:";

    public static final String DORMITORY_INFO = "op:city_bag:cache:dormitory_info:";

    public static final String SCENIC_SPOT_INFO = "op:city_bag:cache:scenic_info:";

    public static final String FOOD_INFO = "op:city_bag:cache:food_info:";

    public static final String CITY_INFO = "op:city_bag:cache:city_info:";

    public static final String CITY_BAG = "op:city_bag:";

    public static final String CITY_BAG_CACHE = "op:city_bag:cache:";

    public static final String INFO = "_info";

    /**
     * 点赞
     */
    public static final String LIKE_SET = "op:city_bag:like:set:";

    /**
     * 锁
     */
    // 分布式锁
    public static final String LIKE_LOCK = "op:city_bag:like:lock:";
    public static final String COLLECTION_LOCK_PREFIX = "op:city_bag:collection:lock:";
    public static final String VISIT_RECORD_LOCK_PREFIX = "op:city_bag:visit_record:lock:";
    // 用户操作锁
    public static final String LIKE_USER_LOCK = "op:city_bag:like:user:";
    // 新用户锁
    public static final String NEW_USER_LOCK = "op:city_bag:new_user_lock:";

}
