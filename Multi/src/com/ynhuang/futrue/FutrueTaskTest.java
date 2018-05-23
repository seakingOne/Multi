package com.ynhuang.futrue;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

import com.ynhuang.futrue.FutrueTest.MyCallable;

/**
 * 得到线程处理的结果
 * 
 * @author ynhuang
 *
 */
public class FutrueTaskTest {

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		FutureTask<String> futureTask = new FutureTask<String>(new Callable<String>() {

			@Override
			public String call() throws Exception {
				System.out.println("--------");
				Thread.sleep(5000);
				return "done";
			}

		});
		new Thread(futureTask).start();
		System.out.println(futureTask.get());
	}

}
