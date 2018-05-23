package com.ynhuang.arithmetic;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

import br.com.objectos.core.lang.Preconditions;

/**
 * 高并发限流指令牌算法
 * 
 * @author ynhuang
 *
 */
public class TokenArithmetic {

	// 默认桶的大小 即最大瞬间流量是64M
	private static final int DEFAULT_BUCKET_SIZE = 1024 * 1024 * 64;

	// 一个桶的单位是1字节
	private int everyTokenSize = 1;

	// 瞬间最大流量
	private int maxFlowRate;

	// 平均流量
	private int avgFlowRate;

	// 队列缓存桶数量
	private ArrayBlockingQueue<Byte> tokenQueue = null;

	// 定时任务的线程池
	private ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();

	private volatile boolean isStart = false;

	// 可重入式锁，true表示的是公平锁
	private ReentrantLock lock = new ReentrantLock(true);

	private static final byte A_CHAR = 'a';

	private TokenArithmetic() {

	}

	private TokenArithmetic(int maxFlowRate, int avgFlowRate) {
		this.maxFlowRate = maxFlowRate;
		this.avgFlowRate = avgFlowRate;
	}

	private TokenArithmetic(int everyTokenSize, int maxFlowRate, int avgFlowRate) {
		this.everyTokenSize = everyTokenSize;
		this.maxFlowRate = maxFlowRate;
		this.avgFlowRate = avgFlowRate;
	}

	private static TokenArithmetic tokenArithmetic = new TokenArithmetic();

	public static TokenArithmetic getTokenArithmetic() {
		return tokenArithmetic;
	}

	// 添加令牌 tokenNum=512
	public void addTokens(Integer tokenNum) throws InterruptedException {
		// 遍历加入请求，如果桶满了不再加入新的令牌
		for (int i = 0; i < tokenNum; i++) {
			tokenQueue.put(Byte.valueOf(A_CHAR));
		}
	}

	// 算法入口
	public TokenArithmetic build() {
		start();
		return this;
	}

	public void start() {
		// 初始化桶队列大小 maxFlowRate=1024 avgFlowRate=512
		if (maxFlowRate != 0) {
			tokenQueue = new ArrayBlockingQueue<Byte>(maxFlowRate);
		}
		// 初始化令牌
		TokenProducer tokenProducer = new TokenProducer(avgFlowRate, this);
		// 定时任务，模拟每隔1s去请求
		scheduledExecutorService.scheduleAtFixedRate(tokenProducer, 0, 1, TimeUnit.SECONDS);
		isStart = true;
	}

	public void stop() {
		isStart = false;
		// 关闭线程池
		scheduledExecutorService.shutdown();
	}

	private String stringCopy(String data, int copyNum) {
		StringBuilder sbuilder = new StringBuilder(data.length() * copyNum);
		for (int i = 0; i < copyNum; i++) {
			sbuilder.append(data);
		}
		return sbuilder.toString();
	}

	/**
	 * 获取足够的令牌个数
	 * 
	 * @param bytes
	 * @return
	 */
	private boolean getTokens(byte[] dataSize) {
		Preconditions.checkNotNull(dataSize);
		Preconditions.checkArgument(isStart, "please invoke start method first !");
		System.out.println("dataSize大小:" + dataSize.length);
		int needTokenNum = dataSize.length / everyTokenSize + 1;// 传输内容大小对应的桶个数
		System.out.println("需要桶的个数：" + needTokenNum);
		System.out.println("tokenQueue队列的大小:" + tokenQueue.size());
		final ReentrantLock lock = this.lock;// 加锁
		lock.lock();
		try {
			boolean result = needTokenNum <= tokenQueue.size(); // 是否存在足够的桶数量
			if (!result) {
				return false;
			}
			int tokenCount = 0;
			for (int i = 0; i < needTokenNum; i++) {
				// 从队列中取出元素并且在队列中删除
				Byte poll = tokenQueue.poll();
				if (poll != null) {
					tokenCount++;
				}
			}
			return tokenCount == needTokenNum;
		} finally {
			lock.unlock();
		}
	}

	/**
	 * 类部类
	 * 
	 * @author ynhuang
	 *
	 */
	class TokenProducer implements Runnable {
		private int avgFlowRate;
		private TokenArithmetic tokenArithmetic;

		public TokenProducer(int avgFlowRate, TokenArithmetic tokenArithmetic) {
			this.avgFlowRate = avgFlowRate;
			this.tokenArithmetic = tokenArithmetic;
		}

		@Override
		public void run() {
			try {
				tokenArithmetic.addTokens(avgFlowRate);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

	public static void main(String[] args) throws Exception {
		TokenArithmetic tokenArithmetic = TokenArithmetic.getTokenArithmetic().avgFlowRate(512).maxFlowRate(1024)
				.build();
		BufferedWriter bw = new BufferedWriter(
				new OutputStreamWriter(new FileOutputStream("/Users/mac/Desktop/1.txt")));
		String data = "xxxx";// 四个字节
		for (int i = 0; i <= 1000; i++) {
			Random random = new Random();
			int i1 = random.nextInt(100);
			boolean tokens = tokenArithmetic.getTokens(tokenArithmetic.stringCopy(data, i1).getBytes());
			TimeUnit.MILLISECONDS.sleep(2000);
			if (tokens) {
				bw.write("指令加入成功 --- index:" + i1);
				System.out.println("指令加入成功 --- index:" + i1);
			} else {
				bw.write("指令加入被拒绝 --- index" + i1);
				System.out.println("指令加入被拒绝 --- index" + i1);
			}
			bw.newLine();
			bw.flush();
		}
		bw.close();
	}

	public boolean isStarted() {
		return isStart;
	}

	public TokenArithmetic everyTokenSize(int everyTokenSize) {
		this.everyTokenSize = everyTokenSize;
		return this;
	}

	public TokenArithmetic maxFlowRate(int maxFlowRate) {
		this.maxFlowRate = maxFlowRate;
		return this;
	}

	public TokenArithmetic avgFlowRate(int avgFlowRate) {
		this.avgFlowRate = avgFlowRate;
		return this;
	}

}
