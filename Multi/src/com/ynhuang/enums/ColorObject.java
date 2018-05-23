package com.ynhuang.enums;

/**
 * 枚举来实例化对象，线程安全，推荐使用
 * 
 * @author ynhuang
 *
 */
public class ColorObject {

	// 私有构造
	private ColorObject() {

	}

	// 对象实例化
	public static ColorObject getInstance() {
		return ColorEnum.COLOR.getInstance();
	}

	private enum ColorEnum {
		COLOR;
		private ColorObject colorObject;

		// JVM保证这个方法绝对只调用一次，保证每次只有一个实例化对象
		private ColorEnum() {
			colorObject = new ColorObject();
		}

		public ColorObject getInstance() {
			return colorObject;
		}
	}
}
