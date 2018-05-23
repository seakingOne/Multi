package com.ynhuang.queue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.SynchronousQueue;

/**
 * 阻塞式队列的使用 1、add方法在添加元素的时候，若超出了度列的长度会直接抛出异常
 * 2、put方法，若向队尾添加元素的时候发现队列已经满了会发生阻塞一直等待空间，以加入元素
 * 3、offer方法在添加元素时，如果发现队列已满无法添加的话，会直接返回false。
 * 
 * @author ynhuang
 *
 */
public class UnBoundQueue {
	// 定义长度为2的阻塞式队列,加入元素大于2会处于阻塞状态
	static ArrayBlockingQueue<String> arrayBlockingQueue = new ArrayBlockingQueue<String>(2);
	// 定义无界队列（也可定义长度）
	static LinkedBlockingQueue<String> blockingQueue = new LinkedBlockingQueue<String>(2);
	// 定义一个生产信息立马被消费的队列
	static SynchronousQueue<String> synchronousQueue = new SynchronousQueue<String>(false);

	public static void main(String[] args) {
		try {
			// arrayBlockingQueue.put("123");
			arrayBlockingQueue.put("1234");
			arrayBlockingQueue.put("ynhuang");
			// poll取得最先进入的元素并且删除
			System.out.println("arrayBlockingQueue取得最先加入的元素:" + arrayBlockingQueue.poll());
			arrayBlockingQueue.add("hjg");
			// peek取得最先进入的元素不会删除
			System.out.println("arrayBlockingQueue取得最先加入的元素:" + arrayBlockingQueue.peek());
			
			System.out.println("arrayBlockingQueue队列长度为:" + arrayBlockingQueue.size());

			blockingQueue.offer("666");
			blockingQueue.offer("admin");
			blockingQueue.offer("1234");
			System.out.println(blockingQueue.peek());
			System.out.println("blockingQueue队列长度为:" + blockingQueue.size());

			// 发现数据放不进去，一直处于阻塞状态
			boolean isOne = synchronousQueue.offer("111111");
			System.out.println(isOne);
			System.out.println("1:"+Thread.currentThread().getName());
			ExecutorService executorService = Executors.newCachedThreadPool();
			executorService.execute(new Runnable() {

				@Override
				public void run() {
					System.out.println("2:"+Thread.currentThread().getName());
					System.out.println("take获取到的值："+synchronousQueue.poll());

				}
			});
			System.out.println(synchronousQueue.peek());
			System.out.println(synchronousQueue.size());
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

}
