package com.zxxa.filter;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class MyHttpSessionListener implements HttpSessionListener {

	public static int online = 0;
	
	@Override
	public void sessionCreated(HttpSessionEvent se) {
		System.out.println("The session has been created");
		online++;
	}
	
	public void sessionDestroyed(HttpSessionEvent se) {
		System.out.println("The session has been destroyed");
		online--;
	}
}
