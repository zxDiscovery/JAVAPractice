package com.zxxa.filter;

import java.io.IOException;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;


@WebFilter(filterName = "MyFilter", urlPatterns = "/filter")
public class MyFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("init");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println("filter");
        
        chain.doFilter(request,response);
    }

    @Override
    public void destroy() {
        System.out.println("destory");
    }

}

