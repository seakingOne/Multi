package com.ynhuang.threadlocal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class ThreadLocalTest {
	public static class MyRunnable1 implements Runnable {

		private ThreadLocal<Integer> threadlocal = new ThreadLocal<Integer>();

		@Override
		public void run() {
			threadlocal.set(new Random().nextInt(100));
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println(Thread.currentThread().getName() + " : " + threadlocal.get());
		}
	}

	public static void main(String[] args) {
//		System.out.println("start");
//		MyRunnable1 runnable = new MyRunnable1();
//		Thread thread1 = new Thread(runnable);
//		Thread thread2 = new Thread(runnable);
//		thread1.start();
//		thread2.start();
		
		//返回一个固定大小的arraylist
		
	}

}
