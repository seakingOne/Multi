package com.ynhuang.lock;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * lock锁（JUC）基于JDK实现， synchronized基于JVM实现； ReentrantLock 可重入锁，可以中断锁
 * 
 * @author ynhuang
 *
 */
public class ReentrantLockTest {
	// 公平锁
	private static Lock lock = new ReentrantLock(true);

	// 请求总数
	public static int clientTotal = 5000;

	// 同时并发执行的线程数
	public static int threadTotal = 200;

	public static int count = 0;

	public static void main(String[] args) {
		ExecutorService executorService = Executors.newCachedThreadPool();
		final Semaphore semaphore = new Semaphore(threadTotal);
		for (int i = 0; i < clientTotal; i++) {
			executorService.execute(new Runnable() {
				@Override
				public void run() {
					try {
						semaphore.acquire();
						add();
						semaphore.release();
					} catch (Exception e) {

					}

				}
			});
		}
		executorService.shutdown();
		System.out.println("count的数量为：" + count);
	}

	private static void add() {
		lock.lock();
		try {
			// 对count数量累加
			count++;
		} finally {
			lock.unlock();
		}

	}

}
