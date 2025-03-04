package com.op.citybag.demos.redis;

import jakarta.annotation.Resource;
import org.redisson.api.*;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Redis 服务 - Redisson
 */
@Service("redissonService")
public class RedissonService implements IRedisService {

    @Resource
    private RedissonClient redissonClient;

    public <T> void setValue(String key, T value) {
        redissonClient.<T>getBucket(key).set(value);
    }

    public void setValueExpired(String key, long expired) {
        redissonClient.getBucket(key).expire(expired, TimeUnit.MILLISECONDS);
    }

    @Override
    public <T> void setValue(String key, T value, long expired) {
        RBucket<T> bucket = redissonClient.getBucket(key);
        bucket.set(value, Duration.ofMillis(expired).toMillis(), TimeUnit.MILLISECONDS);
    }

    public <T> T getValue(String key) {
        return redissonClient.<T>getBucket(key).get();
    }

    @Override
    public <T> RQueue<T> getQueue(String key) {
        return redissonClient.getQueue(key);
    }

    @Override
    public <T> RBlockingQueue<T> getBlockingQueue(String key) {
        return redissonClient.getBlockingQueue(key);
    }

    @Override
    public <T> RDelayedQueue<T> getDelayedQueue(RBlockingQueue<T> rBlockingQueue) {
        return redissonClient.getDelayedQueue(rBlockingQueue);
    }

    @Override
    public void setAtomicLong(String key, long value) {
        redissonClient.getAtomicLong(key).set(value);
    }

    @Override
    public Long getAtomicLong(String key) {
        return redissonClient.getAtomicLong(key).get();
    }

    @Override
    public long incr(String key) {
        return redissonClient.getAtomicLong(key).incrementAndGet();
    }

    @Override
    public long incrBy(String key, long delta) {
        return redissonClient.getAtomicLong(key).addAndGet(delta);
    }

    @Override
    public long decr(String key) {
        return redissonClient.getAtomicLong(key).decrementAndGet();
    }

    @Override
    public long decrBy(String key, long delta) {
        return redissonClient.getAtomicLong(key).addAndGet(-delta);
    }

    @Override
    public void remove(String key) {
        redissonClient.getBucket(key).delete();
    }

    @Override
    public boolean isExists(String key) {
        return redissonClient.getBucket(key).isExists();
    }

    public void addToSet(String key, String value) {
        RSet<String> set = redissonClient.getSet(key);
        set.add(value);
    }

    public void removeFromSet(String key, String value) {
        RSet<String> set = redissonClient.getSet(key);
        set.remove(value);
    }

    public Set<String> getSetMembers(String key) {
        RSet<String> set = redissonClient.getSet(key);
        return set.readAll();
    }

    public boolean isSetMember(String key, String value) {
        RSet<String> set = redissonClient.getSet(key);
        return set.contains(value);
    }

    public void setSetExpired(String key, long expired) {
        RSet<String> set = redissonClient.getSet(key);
        set.expire(Duration.ofMillis(expired));
    }

    public void addToList(String key, String value) {
        RList<String> list = redissonClient.getList(key);
        list.add(value);
    }

    public String getFromList(String key, int index) {
        RList<String> list = redissonClient.getList(key);
        return list.get(index);
    }

    @Override
    public <K, V> RMap<K, V> getMap(String key) {
        return redissonClient.getMap(key);
    }


    public <T> void addToMap(String key, String field, T value) {
        RMap<String, T> map = redissonClient.getMap(key);
        map.put(field, value);
    }

    public void addToMap(String key, String field, String value) {
        RMap<String, String> map = redissonClient.getMap(key);
        map.put(field, value);
    }

    public void setMapExpired(String key, long expired) {
        RMap<String, String> map = redissonClient.getMap(key);
        map.expire(Duration.ofMillis(expired));
    }

    public Long getMapExpired(String key) {
        RMap<String, String> map = redissonClient.getMap(key);
        return map.remainTimeToLive();
    }

    public Map<String, String> getMapToJavaMap(String key) {
        RMap<String, String> map = redissonClient.getMap(key);
        return map.readAllMap();
    }

    public void removeFromMap(String key, String field) {
        RMap<String, String> map = redissonClient.getMap(key);
        map.remove(field);
    }

    @Override
    public <K, V> V getFromMap(String key, K field) {
        return redissonClient.<K, V>getMap(key).get(field);
    }

    public void addToSortedSet(String key, String value) {
        RSortedSet<String> sortedSet = redissonClient.getSortedSet(key);
        sortedSet.add(value);
    }

    @Override
    public RLock getLock(String key) {
        return redissonClient.getLock(key);
    }

    @Override
    public void unLock(RLock lock) {
        if (lock != null && lock.isHeldByCurrentThread()) {
            lock.unlock();
        }
    }

    @Override
    public RLock getFairLock(String key) {
        return redissonClient.getFairLock(key);
    }

    @Override
    public RReadWriteLock getReadWriteLock(String key) {
        return redissonClient.getReadWriteLock(key);
    }

    @Override
    public RSemaphore getSemaphore(String key) {
        return redissonClient.getSemaphore(key);
    }

    @Override
    public RPermitExpirableSemaphore getPermitExpirableSemaphore(String key) {
        return redissonClient.getPermitExpirableSemaphore(key);
    }

    @Override
    public RCountDownLatch getCountDownLatch(String key) {
        return redissonClient.getCountDownLatch(key);
    }

    @Override
    public <T> RBloomFilter<T> getBloomFilter(String key) {
        return redissonClient.getBloomFilter(key);
    }

    @Override
    public Boolean setNx(String key) {
        return redissonClient.getBucket(key).trySet("lock");
    }

    @Override
    public Boolean setNx(String key, long expired, TimeUnit timeUnit) {
        return redissonClient.getBucket(key).trySet("lock", expired, timeUnit);
    }

    @Override
    public Long executeLuaScript(String scriptContent, Collection<String> keys, Object... args) {
        RScript script = redissonClient.getScript();

        List<Object> keyList = new ArrayList<>(keys);
        return script.eval(
                RScript.Mode.READ_WRITE,
                scriptContent,
                RScript.ReturnType.VALUE,
                keyList,
                args
        );
    }

    @Override
    public <T> T executeLuaScriptBySha(String shaDigest, Collection<String> keys, Object... args) {
        RScript script = redissonClient.getScript();
        List<Object> keyList = new ArrayList<>(keys);
        return script.evalSha(
                RScript.Mode.READ_WRITE,
                shaDigest,
                RScript.ReturnType.VALUE,
                keyList,
                args
        );
    }
}
