package com.ynhuang.atomic;

import java.util.concurrent.atomic.AtomicReference;

import com.sun.javafx.UnmodifiableArrayList;

/**
 * 主要是修改0这个值
 * 
 * @author ynhuang
 *
 */
public class AtomicRefrenceTest {

	private static AtomicReference<Integer> atomicReference = new AtomicReference<Integer>(0);

	public static void main(String[] args) {
		atomicReference.compareAndSet(0, 2);
		atomicReference.compareAndSet(0, 1);
		atomicReference.compareAndSet(1, 3);
		atomicReference.compareAndSet(2, 4);
		atomicReference.compareAndSet(3, 5);
		System.out.println(atomicReference.get());

	}
}
