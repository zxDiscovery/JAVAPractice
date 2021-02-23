package com.zxxa.springstudy.annotation;

import java.lang.annotation.Documented;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD,ElementType.METHOD})
@Documented
public @interface MyAnnotation {
    String name() default "zhangsan";
	
}

class Test{
	@MyAnnotation(name="hehe")
	private String name;
	
	@MyAnnotation
	public void hehe() {
		
	}
}