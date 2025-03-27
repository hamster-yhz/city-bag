package com.op.citybag.demos.redis;

import org.redisson.api.*;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Redis 服务
 */
public interface IRedisService {

    /**
     * 设置指定 key 的值
     *
     * @param key   键
     * @param value 值
     */
    <T> void setValue(String key, T value);

    /**
     * 设置指定 key 的过期时间
     *
     * @param key
     * @param expired
     */
    public void setValueExpired(String key, long expired);

    /**
     * 设置指定 key 的值
     *
     * @param key     键
     * @param value   值
     * @param expired 过期时间
     */
    <T> void setValue(String key, T value, long expired);

    /**
     * 获取指定 key 的值
     *
     * @param key 键
     * @return 值
     */
    <T> T getValue(String key);

    /**
     * 获取队列
     *
     * @param key 键
     * @param <T> 泛型
     * @return 队列
     */
    <T> RQueue<T> getQueue(String key);

    /**
     * 加锁队列
     *
     * @param key 键
     * @param <T> 泛型
     * @return 队列
     */
    <T> RBlockingQueue<T> getBlockingQueue(String key);

    /**
     * 延迟队列
     *
     * @param rBlockingQueue 加锁队列
     * @param <T>            泛型
     * @return 队列
     */
    <T> RDelayedQueue<T> getDelayedQueue(RBlockingQueue<T> rBlockingQueue);

    /**
     * 设置值
     *
     * @param key   key 键
     * @param value 值
     */
    void setAtomicLong(String key, long value);

    /**
     * 获取值
     *
     * @param key key 键
     */
    Long getAtomicLong(String key);

    /**
     * 自增 Key 的值；1、2、3、4
     *
     * @param key 键
     * @return 自增后的值
     */
    long incr(String key);

    /**
     * 指定值，自增 Key 的值；1、2、3、4
     *
     * @param key 键
     * @return 自增后的值
     */
    long incrBy(String key, long delta);

    /**
     * 自减 Key 的值；1、2、3、4
     *
     * @param key 键
     * @return 自增后的值
     */
    long decr(String key);

    /**
     * 指定值，自增 Key 的值；1、2、3、4
     *
     * @param key 键
     * @return 自增后的值
     */
    long decrBy(String key, long delta);


    /**
     * 移除指定 key 的值
     *
     * @param key 键
     */
    void remove(String key);

    /**
     * 判断指定 key 的值是否存在
     *
     * @param key 键
     * @return true/false
     */
    boolean isExists(String key);

    /**
     * 将指定的值添加到集合中
     *
     * @param key   键
     * @param value 值
     */
    void addToSet(String key, String value);

    /**
     * 删除集合中的指定值
     * @param key
     * @param value
     */
    void removeFromSet(String key, String value);

    /**
     * 获取集合中的所有值
     *
     * @param key 键
     * @return 值
     */
    Set<String> getSetMembers(String key);

    /**
     * 判断指定的值是否是集合的成员
     *
     * @param key   键
     * @param value 值
     * @return 如果是集合的成员返回 true，否则返回 false
     */
    boolean isSetMember(String key, String value);

    /**
     * 设置set的过期时间
     *
     */
    void setSetExpired(String key, long expired);

    /**
     * 将指定的值添加到列表中
     *
     * @param key   键
     * @param value 值
     */
    void addToList(String key, String value);

    /**
     * 获取列表中指定索引的值
     *
     * @param key   键
     * @param index 索引
     * @return 值
     */
    String getFromList(String key, int index);

    /**
     * 获取Map
     *
     * @param key 键
     * @return 值
     */
    <K, V> RMap<K, V> getMap(String key);

    /**
     * 将指定的键值对添加到哈希表中
     *
     * @param key   键
     * @param field 字段
     * @param value 值
     */
    <T> void addToMap(String key, String field, T value);

    /**
     * 将指定的键值对添加到哈希表中
     *
     * @param key   键
     * @param field 字段
     * @param value 值
     */
    void addToMap(String key, String field, String value);

    /**
     * 设置指定 key 的过期时间
     *
     * @param key     键
     * @param expired 过期时间(秒)
     */
    void setMapExpired(String key, long expired);


    /**
     * 获取Map的过期时间
     *
     * @param key 键
     * @return 值
     */
    Long getMapExpired(String key);

    /**
     * 获取Map并转换为Java Map
     * @param key 键
     * @return
     */
    Map<String,String> getMapToJavaMap(String key);

    /**
     * 移除哈希表中指定字段的值
     *
     * @param key   键
     * @param field 字段
     */
    void removeFromMap(String key, String field);

    /**
     * 获取哈希表中指定字段的值
     *
     * @param key   键
     * @param field 字段
     * @return 值
     */
    <K, V> V getFromMap(String key, K field);

    /**
     * 将指定的值添加到有序集合中
     *
     * @param key   键
     * @param value 值
     */
    void addToSortedSet(String key, String value);

    /**
     * 获取 Redis 锁（可重入锁）
     *
     * @param key 键
     * @return Lock
     */
    RLock getLock(String key);

    /**
     * 释放 Redis 锁
     *
     * @param lock 锁
     */
    void unLock(RLock lock);

    /**
     * 获取 Redis 锁（公平锁）
     *
     * @param key 键
     * @return Lock
     */
    RLock getFairLock(String key);

    /**
     * 获取 Redis 锁（读写锁）
     *
     * @param key 键
     * @return RReadWriteLock
     */
    RReadWriteLock getReadWriteLock(String key);

    /**
     * 获取 Redis 信号量
     *
     * @param key 键
     * @return RSemaphore
     */
    RSemaphore getSemaphore(String key);

    /**
     * 获取 Redis 过期信号量
     * <p>
     * 基于Redis的Redisson的分布式信号量（Semaphore）Java对象RSemaphore采用了与java.util.concurrent.Semaphore相似的接口和用法。
     * 同时还提供了异步（Async）、反射式（Reactive）和RxJava2标准的接口。
     *
     * @param key 键
     * @return RPermitExpirableSemaphore
     */
    RPermitExpirableSemaphore getPermitExpirableSemaphore(String key);

    /**
     * 闭锁
     *
     * @param key 键
     * @return RCountDownLatch
     */
    RCountDownLatch getCountDownLatch(String key);

    /**
     * 布隆过滤器
     *
     * @param key 键
     * @param <T> 存放对象
     * @return 返回结果
     */
    <T> RBloomFilter<T> getBloomFilter(String key);

    Boolean setNx(String key);

    Boolean setNx(String key, long expired, TimeUnit timeUnit);


    /**
     * 执行 Lua 脚本
     *
     * @param scriptContent 脚本内容
     * @param keys          键
     * @param args          参数
     * @return 返回结果
     */
    Long executeLuaScript(String scriptContent, Collection<String> keys, Object... args);

    /**
     * 通过sha摘要执行预存 Lua 脚本
     *
     * @param shaDigest 脚本内容
     * @param keys      键
     * @param args      参数
     * @param <T>       返回结果
     * @return 返回结果
     */
    <T> T executeLuaScriptBySha(String shaDigest, Collection<String> keys, Object... args);


    /**
     * 添加元素到ZSET并设置过期时间（单位：秒）
     * @param key ZSET键名
     * @param member 元素值
     * @param expireSeconds 过期时间（秒）
     */
    void addToZSetWithExpire(String key, String member, long expireSeconds);

    /**
     * 获取ZSET中未过期的有效元素
     * @param key ZSET键名
     * @return 有效元素集合
     */
    Set<String> getActiveZSetMembers(String key);

    /**
     * 移除ZSET中的元素
     * @param key ZSET键名
     * @param member 元素值
     */
    void removeFromZSet(String key, String member);

    int getZSetSize(String key);

    int getActiveZSetSize(String key);

    void removeMap(String key);

    void removeZSet(String key);
}
