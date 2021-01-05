package com.ita.domain.redis;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.ObjectUtils;

import java.util.concurrent.TimeUnit;

/**
 * @author Dillon Xie
 * @date 1/4/2021
 */
@Slf4j
public class RedisDistributedLock {
    private String key;
    private boolean lock = false;

    private final RedisTemplate redisTemplate;
    private static final Long LOCK_SLEEP_MILLI = Long.parseLong("50");

    public RedisDistributedLock(String key, RedisTemplate redisTemplate) {
        if (ObjectUtils.isEmpty(redisTemplate)) {
            throw new IllegalArgumentException("StringRedisTemplate could not null!");
        }
        this.key = key + "_distributed_lock";
        this.redisTemplate = redisTemplate;
    }

    public boolean lock(long pollingTime, long expire, final TimeUnit unit) {
        long threadId = Thread.currentThread().getId();
        pollingTime = unit.toMillis(pollingTime);
        try {
            int count = 0;
            while (count < pollingTime / LOCK_SLEEP_MILLI) {
                if (this.redisTemplate.opsForValue().setIfAbsent(this.key, "1", expire, unit)) {
                    this.lock = true;
                    log.info("[ThreadId=" + threadId + "] Success get lock ...");
                    return true;
                }
                log.warn(
                        "[ThreadId="
                                + threadId
                                + "]Sorry, lock has been occupied, waiting get lock sleep "
                                + LOCK_SLEEP_MILLI
                                + "ms...");
                Thread.sleep(LOCK_SLEEP_MILLI);
                count++;
            }
            log.error(
                    "[ThreadId="
                            + threadId
                            + "] could not get lock, count={}", count);
        } catch (Exception e) {
            log.info("error");
        }
        return false;
    }

    public void unlock() {
        if (this.lock) {
            this.redisTemplate.delete(key);
            log.warn("[ThreadId=" + Thread.currentThread().getId() + "] Success release the lock ...");
        }
    }

    public boolean isLock() {
        return lock;
    }

    public String getKey() {
        return key;
    }
}
