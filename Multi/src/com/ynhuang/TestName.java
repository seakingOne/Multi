package com.ynhuang;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自定义注解写法 ElementType的取值：
 * 1.CONSTRUCTOR:用于描述构造器 
 * 2.FIELD:用于描述域
 * 3.LOCAL_VARIABLE:用于描述局部变量 
 * 4.METHOD:用于描述方法 
 * 5.PACKAGE:用于描述包 
 * 6.PARAMETER:用于描述参数
 * 7.TYPE:用于描述类、接口(包括注解类型) 或enum声明
 * 
 * @author ynhuang
 *
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME) // 注解存在的范围 RUNTIME通过反射的时候可以拿到
									// SOURCE编译的时候就可以忽略掉
@Documented
public @interface TestName {
	public String value() default "admin";
}
