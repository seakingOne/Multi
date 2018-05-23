package com.ynhuang.atomic;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

/**
 * 
 * @author ynhuang
 *
 */
public class AtomicIntegerFieldUpdaterTest {
	private volatile int count = 100;

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	private static AtomicIntegerFieldUpdater<AtomicIntegerFieldUpdaterTest> atomicIntegerFieldUpdater = AtomicIntegerFieldUpdater
			.newUpdater(AtomicIntegerFieldUpdaterTest.class, "count");

	public static void main(String[] args) {
		AtomicIntegerFieldUpdaterTest atomicIntegerFieldUpdaterTest = new AtomicIntegerFieldUpdaterTest();
		if (atomicIntegerFieldUpdater.compareAndSet(atomicIntegerFieldUpdaterTest, 100, 200)) {
			System.out.println(atomicIntegerFieldUpdaterTest.getCount());
		}
	}
}
