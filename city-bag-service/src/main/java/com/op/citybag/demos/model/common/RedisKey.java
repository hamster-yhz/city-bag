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

}
