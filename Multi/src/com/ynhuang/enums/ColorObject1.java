package com.ynhuang.enums;

/**
 * 自定义枚举格式 枚举的构造私有化
 * 
 * @author ynhuang
 *
 */
public enum ColorObject1 {
	ORIGIN("橙色", 1), RED("红色", 2);
	private String name;
	private int index;

	private ColorObject1(String name, int index) {
		this.name = name;
		this.index = index;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}
}
