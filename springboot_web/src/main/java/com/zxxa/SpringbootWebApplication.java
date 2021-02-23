package com.zxxa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.zxxa.filter.MyHttpSessionListener;
import com.zxxa.servlet.MyServlet;

@SpringBootApplication
@ServletComponentScan
public class SpringbootWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootWebApplication.class, args);
	}
	
	@Bean
	public ServletRegistrationBean<MyServlet> getServletRegistrationBean(){
		ServletRegistrationBean<MyServlet> bean = new ServletRegistrationBean<>(new MyServlet(), "/s2");
		bean.setLoadOnStartup(1);
		return bean;
	}

	@Bean
	public ServletListenerRegistrationBean listenerRegist() {
		ServletListenerRegistrationBean srb = new ServletListenerRegistrationBean();
		srb.setListener(new MyHttpSessionListener());
		System.out.println("listener");
		return srb;
	}
}
