package com.zxxa.aoppro;

import org.springframework.context.support.ClassPathXmlApplicationContext;


public class Mytest {

	public static void main(String[] args) {
		
		Mytest t = new Mytest();
		t.test02();
	}
	
	public void test02() {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		Calculator calculator = context.getBean("myCalculator", Calculator.class);
		calculator.add(1, 2);
	}
}
