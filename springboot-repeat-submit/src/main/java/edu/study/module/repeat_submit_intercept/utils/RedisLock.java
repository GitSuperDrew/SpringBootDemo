package edu.study.module.repeat_submit_intercept.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.params.SetParams;

import java.util.Collections;


/**
 * @author drew
 */
@Service
public class RedisLock {

    private static final Long RELEASE_SUCCESS = 1L;
    private static final String LOCK_SUCCESS = "OK";
    private static final String SET_IF_NOT_EXIST = "NX";
    /**
     * 当前设置 过期时间单位, EX = seconds; PX = milliseconds
     */
    private static final String SET_WITH_EXPIRE_TIME = "EX";
    private static final String RELEASE_LOCK_SCRIPT = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * 该加锁方法仅针对单实例 Redis 可实现分布式加锁
     * 对于 Redis 集群则无法使用
     * <p>
     * 支持重复，线程安全
     *
     * @param lockKey  加锁键
     * @param clientId 加锁客户端唯一标识(采用UUID)
     * @param seconds  锁过期时间(秒)
     * @return
     */
    public boolean tryLock(String lockKey, String clientId, int seconds) {
        return redisTemplate.execute((RedisCallback<Boolean>) redisConnection -> {
            Jedis jedis = (Jedis) redisConnection.getNativeConnection();
            String result = jedis.set(lockKey, clientId, SetParams.setParams().ex(seconds));
            if (LOCK_SUCCESS.equals(result)) {
                return true;
            }
            return false;
        });
    }

    /**
     * 该加锁方法仅针对单实例 Redis 可实现分布式加锁
     * 对于 Redis 集群则无法使用
     * <p>
     * 支持重复，线程安全
     *
     * @param lockKey      加锁键
     * @param clientId     加锁客户端唯一标识(采用UUID)
     * @param milliSeconds 锁过期时间(毫秒)
     * @return
     */
    public boolean tryLock(String lockKey, String clientId, long milliSeconds) {
        return redisTemplate.execute((RedisCallback<Boolean>) redisConnection -> {
            Jedis jedis = (Jedis) redisConnection.getNativeConnection();
            String result = jedis.set(lockKey, clientId, SetParams.setParams().px(milliSeconds));
            if (LOCK_SUCCESS.equals(result)) {
                return true;
            }
            return false;
        });
    }

    /**
     * 与 tryLock 相对应，用作释放锁
     *
     * @param lockKey
     * @param clientId
     * @return
     */
    public boolean releaseLock(String lockKey, String clientId) {
        return redisTemplate.execute((RedisCallback<Boolean>) redisConnection -> {
            Jedis jedis = (Jedis) redisConnection.getNativeConnection();
            Object result = jedis.eval(RELEASE_LOCK_SCRIPT, Collections.singletonList(lockKey),
                    Collections.singletonList(clientId));
            if (RELEASE_SUCCESS.equals(result)) {
                return true;
            }
            return false;
        });
    }
}

