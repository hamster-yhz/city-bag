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
            "local key = KEYS[1]\n" +
                    "local userKey = KEYS[2]\n" +
                    "local action = ARGV[1]\n" +  // 新增action参数
                    "local expire = ARGV[2]\n\n" +

                    "if redis.call('EXISTS', userKey) == 1 then\n" +
                    "    return 0\n" +
                    "end\n\n" +

                    "if action == 'like' then\n" +  // 新增判断分支
                    "    if redis.call('SADD', key, ARGV[3]) == 1 then\n" +
                    "        redis.call('EXPIRE', key, expire)\n" +
                    "        redis.call('SET', userKey, 1, 'EX', 5)\n" +
                    "        return 1\n" +
                    "    end\n" +
                    "elseif action == 'unlike' then\n" +  // 新增取消点赞分支
                    "    if redis.call('SREM', key, ARGV[3]) == 1 then\n" +
                    "        redis.call('EXPIRE', key, expire)\n" +
                    "        redis.call('SET', userKey, 1, 'EX', 5)\n" +
                    "        return -1\n" +  // 返回负值表示取消
                    "    end\n" +
                    "end\n" +
                    "return 0";
}
