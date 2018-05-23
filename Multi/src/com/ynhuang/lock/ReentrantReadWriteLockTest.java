package com.ynhuang.lock;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 读写锁： 读可以并发读，多个线程同时读； 写只能单线程去写，类似于synchronized； 加入写锁时需要先释放读锁，不然容易产生死锁
 * 
 * 获取 writeLock 时 一定是在没有线程获取 readLock 或 writeLock 时才获取成功，取 readLock 的过程中,
 * 若此时有线程已获取写锁 或 同步等待队列 里面有获取 writeLock 的线程, 则一定会等待获取writeLock成功并释放或放弃获取
 * 后才能获取（死锁时, 已获取 readLock 的线程还能重复获取 readLock)
 * 
 * 感觉有点共享锁的味道：加锁可以多个读，但是不能写，除非释放了读锁
 * 
 * @author ynhuang
 *
 */
public class ReentrantReadWriteLockTest {
	private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
	private final Lock readLock = lock.readLock();
	private final Lock writeLock = lock.writeLock();

	// 定义一个map容器
	static Map<String, String> map = new HashMap<String, String>();

	public String getDate(String key) {
		readLock.lock();
		try {
			return map.get(key);
		} finally {
			readLock.unlock();
		}
	}

	public Set<String> getDataAll() {
		readLock.lock();
		try {
			return map.keySet();
		} finally {
			readLock.unlock();
		}
	}

	public String putDate(String key, String value) {
		writeLock.lock();
		try {
			readLock.lock();
			return map.put(key, value);
		} finally {
			writeLock.unlock();
		}
	}

	public static void main(String[] args) {
		new ReentrantReadWriteLockTest().putDate("11", "admin");
		System.out.println(new ReentrantReadWriteLockTest().getDate("11"));
	}
}
