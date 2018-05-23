package com.ynhuang.pool;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class FixedThreadPool {

	/***
	 * 静态内部内
	 * 
	 * @author ynhuang
	 *
	 */
	private static class MyThisThread implements Runnable {
		public MyThisThread() {

		}

		@Override
		public void run() {
			System.out.println("定时打印这条信息！");
		}

	}

	/**
	 * 返回结果的任务调度
	 * 
	 * @author ynhuang
	 *
	 */
	private static class ThreadResult implements Callable<String> {
		public ThreadResult() {

		}

		@Override
		public String call() throws Exception {
			String i = "10";
			return i;
		}

	}

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		// ExecutorService fixedThreadPool = Executors.newFixedThreadPool(3);
		// 底层队列继承自SynchronousQueue，没有缓冲的队列，其他线程池都是继承自无界队列LinkedBlockingQueue
		// ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
		// 单线程的线程池是固定了最大的线程数有且只为1
		// ExecutorService singleThreadExecutor =
		// Executors.newSingleThreadExecutor();
		// ExecutorService scheduledThreadPool =
		// Executors.newScheduledThreadPool(3);
		ScheduledExecutorService scheduled = Executors.newScheduledThreadPool(3);
		FutureTask<String> futureTask = new FutureTask<String>(new ThreadResult());
		scheduled.submit(futureTask);
		System.out.println(futureTask.get());
		scheduled.scheduleWithFixedDelay(new MyThisThread(), 0, 2, TimeUnit.SECONDS); // 每隔2s打印一次
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		// for (int i = 0; i < 20; i++) {
		// final int index = i;
		// fixedThreadPool.execute(new Runnable() {
		//
		// @Override
		// public void run() {
		// System.out.println("当前是第" + index + "个线程");
		// try {
		// Thread.sleep(2000);
		// } catch (InterruptedException e) {
		// e.printStackTrace();
		// }
		// }
		// });
		// }
		// fixedThreadPool.shutdown();
	}

}
