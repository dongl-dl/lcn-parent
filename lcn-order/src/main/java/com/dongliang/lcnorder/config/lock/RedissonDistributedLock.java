package com.dongliang.lcnorder.config.lock;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;

import java.util.concurrent.TimeUnit;

/**
 * @author D-L
 * @version 1.0.0
 * @ClassName RedissonDistributedLocker.java
 * @Description
 * @createTime 2021-06-04 23:40:00
 */

public class RedissonDistributedLock implements DistributedLock {
	
	private RedissonClient redissonClient;

	@Override
	public void lock(String entityId) {
		RLock lock = redissonClient.getLock(entityId);
		lock.lock();
	}

	@Override
	public void unlock(String entityId) {
		RLock lock = redissonClient.getLock(entityId);
		if(lock!=null){
			if(lock.isLocked() && lock.isHeldByCurrentThread()) {
				lock.unlock();
			}
		}
	}

	@Override
	public void lock(String entityId, int leaseTime) {
		RLock lock = redissonClient.getLock(entityId);
		lock.lock(leaseTime, TimeUnit.SECONDS);
	}

	public void setRedissonClient(RedissonClient redissonClient) {
		this.redissonClient = redissonClient;
	}
}
