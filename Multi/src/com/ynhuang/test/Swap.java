package com.ynhuang.test;

import java.lang.reflect.Field;

/**
 * 
 * @author ynhuang Integer的自动拆装箱的陷阱
 * 
 */
public class Swap {
	// 定义成员变量
	private static int one;

	public static void main(String[] args)
			throws SecurityException, IllegalArgumentException, NoSuchFieldException, IllegalAccessException {
		Integer a = 12;
		Integer b = 2;

		swap(a, b);
		System.out.println("a修改后的值为：" + a);
		System.out.println("b修改后的值为：" + b);

		Integer i1 = new Integer(1);
		Integer i2 = new Integer(2);
		//swapObject(i1, i2);
		System.out.println(i1 + ":" + i2);
		
		Test a1=new Test();
        Test b1=new Test();
        System.out.println("交换前a1:"+a1+"\t+"+"b1:"+b1);
        Swap body=new Swap();
        body.swapObject(a1,b1);
        System.out.println("交换后a1:"+a1+"\t+"+"b1:"+b1);

	}

	private static void swap(Integer a, Integer b)
			throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
		Integer tmp = new Integer(a); // Integer.valueOf(a)使用valueof方法还是需要去判断是否new对象
		Field field = a.getClass().getDeclaredField("value");
		field.setAccessible(true);
		field.set(a, b.intValue());
		field.set(b, tmp);

	}

	private static void swapObject(Test a, Test b) {
		Test temp = new Test();
		temp.a =a.a;
        a.a=b.a;
        b.a=temp.a;
	}

}

class Test{
    public int a;
}
