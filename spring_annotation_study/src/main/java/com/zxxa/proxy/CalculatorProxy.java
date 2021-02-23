package com.zxxa.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

import com.zxxa.aoppro.Calculator;
import com.zxxa.util.LogUtil;

public class CalculatorProxy {
	public static Calculator getProxy(final Object object) {
		ClassLoader loader = object.getClass().getClassLoader();
		
		Class<?>[] interfaces = object.getClass().getInterfaces();
		
		InvocationHandler h = new InvocationHandler() {

			public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
				
				Object result = null;
				
				try {
                    result = method.invoke(object, args);

				} catch (Exception e) {
	//				LogUtil.logException(method, e);
				} finally {
	//				LogUtil.end(method);
				}
				
				return result;
			}
			
		};
		
		Object proxy = Proxy.newProxyInstance(loader, interfaces, h);
		
		return (Calculator)proxy;
	}
}
