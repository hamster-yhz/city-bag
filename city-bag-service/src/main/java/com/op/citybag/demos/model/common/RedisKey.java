package com.op.citybag.demos.model.common;

/**
 * @Author: 严豪哲
 * @Description: redis的key前缀
 * @Date: 2024/11/10 23:26
 * @Version: 1.0
 */

public class RedisKey {

    public static final String TOKEN = "op:city_bag:login:token:";

    public static final String NEW_USER_LOCK = "op:city_bag:new_user_lock:";

    public final static String USER_INFO = "op:city_bag:user:info:";

    public final static String USER_TO_TOKEN = "op:city_bag:user_to_token";

    public static final String DORMITORY_INFO = "city_bag:dormitory:info:";

    public static final String SCENIC_SPOT_INFO = "city_bag:scenic:info:";

    public static final String FOOD_INFO = "city_bag:food:info:";

    public static final String CITY_INFO = "city_bag:city:info:";

    public static final String CITY_BAG = "city_bag:";

    public static final String INFO = ":info";

    // 存储实体点赞集合
    public static final String LIKE_SET = "city_bag:like:set:";

    // 分布式锁
    public static final String LIKE_LOCK = "city_bag:like:lock:";

    // 用户操作锁
    public static final String LIKE_USER_LOCK = "city_bag:like:user:";

}
