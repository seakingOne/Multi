package com.ynhuang.futrue;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * 得到线程处理的结果
 * 
 * @author ynhuang
 *
 */
public class FutrueTest {

	static class MyCallable implements Callable<String> {

		@Override
		public String call() throws Exception {
			System.out.println("--------");
			Thread.sleep(5000);
			return "done";
		}

	}

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		ExecutorService executorService = Executors.newCachedThreadPool();
		// submit提交有返回值
		Future<String> future = executorService.submit(new MyCallable());
		System.out.println(future.get());
		executorService.shutdown();
	}

}
