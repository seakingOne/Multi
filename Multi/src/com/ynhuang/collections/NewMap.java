package com.ynhuang.collections;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * HashMap，ArrayList本身不是线程安全的
 * 
 * @author ynhuang
 *
 */
public class NewMap {

	private int age;

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public int add() {
		System.out.println("调用了add（）方法：" + "1" + "3");
		return 3;
	}

	public static void main(String[] args) {
		// 1、对其进行加锁
		Map<String, String> map = Collections.synchronizedMap(new HashMap<String, String>());
		map.put("1", "123");
		map.put("2", "555");
		map.put("4", "66");
		map.put("3", "888");
		for (String key : map.keySet()) {
			System.out.println(map.get(key));
		}
		// 2、使用ConcurrentHashMap，内部实现多个hashtable，分段加锁
		Map<String, String> concurrentHashMap = new ConcurrentHashMap<String, String>();
		concurrentHashMap.put("1", "12");
		concurrentHashMap.put("1", "123");
		System.out.println(concurrentHashMap.toString());
		byte[] bs = new byte[2 * 1024 * 1024];

		// 反射机制
		try {
			Class<?> class1 = NewMap.class;
			Method method = class1.getMethod("add");
			Integer reuslt = (Integer) method.invoke(class1.newInstance());
			System.out.println(reuslt.intValue());

		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
		// date日期函数
		Date date = new Date();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss E");
		// simpleDateFormat.format(date);
		System.out.println(simpleDateFormat.format(date));

		// 使用阿里巴巴的json转换包
		// 1、map对象转换为json对象
		JSONObject jsonObject = (JSONObject) JSONObject.toJSON(map);
		// 2、string对象转换为json对象
		String str = "{'name':'admin','age':'123'}";
		JSONObject jsonObject2 = (JSONObject) JSONObject.parse(str);
		// 3、list对象转换为json对象
		List<Map<String, String>> listMap = new ArrayList<Map<String, String>>();
		listMap.add(map);
		String json = ((JSON) JSON.toJSON(listMap)).toJSONString();
		System.out.println(json);
	}

}
