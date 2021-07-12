package com.dongliang.lcnorder.config.lock;

/**
 * @author D-L
 * @version 1.0.0
 * @ClassName DistributedLocker.java
 * @Description
 * @createTime 2021-06-04 23:40:00
 */
public interface DistributedLock {

	void lock(String entityId);

	void unlock(String entityId);

	void lock(String entityId, int timeout);
}
