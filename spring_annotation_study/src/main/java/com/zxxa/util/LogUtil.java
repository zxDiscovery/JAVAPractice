package com.zxxa.util;
import java.lang.reflect.Method;
import java.util.Arrays;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LogUtil {
	
	@Before("execution(public Integer com.zxxa.aoppro.MyCalculator.add(Integer, Integer))")
	public static void start() {
		System.out.println("方法开始执行, 参数是：");
	}
	
	@AfterReturning("execution(public Integer com.zxxa.aoppro.MyCalculator.add(Integer, Integer))")
    public static void stop(){
        System.out.println("XXX方法执行结束，结果是：");
    }
    
	@AfterThrowing("execution(public Integer com.zxxa.aoppro.MyCalculator.add(Integer, Integer))")
    public static void logException(){
        System.out.println("方法出现异常：");
    }
    
    @After("execution(public Integer com.zxxa.aoppro.MyCalculator.add(Integer, Integer))")
    public static void end(){
        System.out.println("方法执行结束了......");
    }

}
