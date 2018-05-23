package com.ynhuang.thread;

public class VolatileTest extends Thread {
	private volatile static int count = 100;

	@Override
	public void run() {
		count--;
		System.out.println(currentThread().getName() + ":" + count);
	}

	public static void main(String[] args) throws InterruptedException {
		VolatileTest volatileTest = new VolatileTest();
		Thread thread = new Thread(volatileTest, "第一个线程");
		Thread thread1 = new Thread(volatileTest, "第二个线程");
		Thread thread2 = new Thread(volatileTest, "第三个线程");
		Thread thread3 = new Thread(volatileTest, "第四个线程");
		Thread thread4 = new Thread(volatileTest, "第五个线程");
		thread.start();
		Thread.sleep(3000);
		//第一个线程开始等待第二个或者其他线程启动，此时会释放资源
		thread.wait();
		thread1.start();
		//如果第二个线程开始启动，此时会给第一个线程发送消息
		thread1.notify();
		thread2.start();
		thread3.start();
		thread4.start();
		// Thread.sleep(4000);
		System.out.println("count:" + VolatileTest.count);
	}

}
