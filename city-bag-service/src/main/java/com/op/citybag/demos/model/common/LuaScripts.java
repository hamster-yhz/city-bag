package com.op.citybag.demos.model.common;

/**
 * @Author: 原神
 * @Description:
 * @Date: 2025/3/3 20:20
 * @Version: 1.0
 */
public class LuaScripts {
    // 点赞操作脚本（原子性增减）
    public static final String LIKE_SCRIPT =
            "local key = KEYS[1]\n" + //用户点赞集合key
                    "local userKey = KEYS[2]\n" + //操作锁key
                    "local action = ARGV[1]\n" + //操作类型
                    "local expire = ARGV[2]\n" + //用户点赞集合过期时间
                    "local userId = ARGV[3]\n\n" + //用户id(用于加入用户点赞集合)

                    "if redis.call('EXISTS', userKey) == 1 then\n" +
                    "    return 0\n" +
                    "end\n\n" +

                    "if action == 'like' then\n" +  // 点赞分支
                    "    if redis.call('SADD', key, userId) == 1 then\n" +
                    "        redis.call('EXPIRE', key, expire)\n" +
                    "        redis.call('SET', userKey, 1, 'EX', 5)\n" +
                    "        return 1\n" +
                    "    end\n" +
                    "elseif action == 'unlike' then\n" +  // 取消点赞分支
                    "    if redis.call('SREM', key, userId) == 1 then\n" +
                    "        redis.call('SET', userKey, 1, 'EX', 5)\n" +
                    "        return -1\n" +  // 返回负值表示取消
                    "    end\n" +
                    "end\n" +
//                    "return 0";
                    "return 1";// 默认为点赞操作
}
