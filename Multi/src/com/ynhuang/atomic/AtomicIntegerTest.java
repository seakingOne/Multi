package com.ynhuang.atomic;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 
 * @author ynhuang
 *
 */
public class AtomicIntegerTest {
	// 请求总数
	public static int clientTotal = 5000;

	// 同时并发执行的线程数
	public static int threadTotal = 200;

	public static int count = 0;

	public static AtomicInteger atomicInteger = new AtomicInteger(0);

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
		System.out.println("atomicInteger的数量为：" + atomicInteger);
	}

	private static void add() {
		atomicInteger.incrementAndGet();
		// 对count数量累加
		count++;

	}

}
