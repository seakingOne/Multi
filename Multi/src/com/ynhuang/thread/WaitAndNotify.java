package com.ynhuang.thread;

import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

/**
 * 线程间的通信 wait和notify
 * 
 * @author ynhuang
 *
 */
public class WaitAndNotify {
	// 定义一个全局变量
	private volatile static boolean isStop = false;

	public static void main(String[] args) {
		final ArrayList list = new ArrayList();
		// 定义一个锁对象
		// final Object lock = new Object();
		final CountDownLatch countDownLatch = new CountDownLatch(1);
		Thread t1 = new Thread(new Runnable() {

			@Override
			public void run() {

				for (int i = 0; i <= 100; i++) {
					// synchronized (lock) {
					list.add(i);
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					System.out.println("当前线程：" + Thread.currentThread().getName() + "添加了一个元素..");
					if (list.size() == 5) {
						isStop = true;
						System.out.println("通知t2线程此时size为5了");
						// lock.notify();
						countDownLatch.countDown();
					}

				}
			}

			// }
		}, "t1");

		Thread t2 = new Thread(new Runnable() {

			@Override
			public void run() {
				// synchronized (lock) {
				while (true) {
					System.out.println("111111");
					if (list.size() != 5) {
						try {
							System.out.println("t2进入...");
							// lock.wait();
							countDownLatch.await();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					
					System.out.println("当前线程：" + Thread.currentThread().getName() + "收到通知线程停止..");
					throw new RuntimeException();

				}
			}

			// }
		}, "t2");

		t2.start();
		t1.start();

	}

}
