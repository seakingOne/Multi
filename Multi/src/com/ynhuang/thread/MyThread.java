package com.ynhuang.thread;

/**
 * 获取临界值
 * 
 * @author ynhuang
 *
 */
public class MyThread extends Thread {

	private volatile int count = 5;

	@Override
	public synchronized void run() {
		count--;
		System.out.println("当前的线程名：" + this.currentThread().getName() + "，count=" + count);
	}

	public static void main(String[] args) {
		MyThread myThread = new MyThread();
		Thread t1 = new Thread(myThread, "t1");
		Thread t2 = new Thread(myThread, "t2");
		Thread t3 = new Thread(myThread, "t3");
		t1.start();
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		t2.start();
		t3.start();
	}
}
