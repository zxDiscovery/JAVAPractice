package com.zxxa.dp.cor;

import java.util.ArrayList;
import java.util.List;

public class Main {
	public static void main(String[] args) {
		
		Request rq = new Request();
		rq.str = "大家好:), <script>, 欢迎访问zxxa.com, 大家都是996";
		
		Response rp = new Response();
		rp.str = "response: ";
		
		FilterChain fc = new FilterChain();
		
		fc.addFilter(new HtmlFilter()).addFilter(new SensitiveFilter()).addFilter(new FaceFilter()).addFilter(new UrlFilter());
		
		fc.doFilter(rq, rp, fc);
		
		System.out.println(rq.str);
		System.out.println(rp.str);
		
		/*
		Msg msg = new Msg();
		msg.setMsg("大家好:), <script>, 欢迎访问zxxa.com, 大家都是996");
		
		System.out.println(msg);
		
		FilterChain fc = new FilterChain();
		FilterChain fc2 = new FilterChain();
		
		fc.addFilter(new HtmlFilter()).addFilter(new SensitiveFilter());
		fc2.addFilter(new FaceFilter()).addFilter(new UrlFilter());
		
		fc.addFilter(fc2);
		
		fc.doFilter(msg);
		
		System.out.println(msg);
		*/
	}
}

class Request {
	String str;
}

class Response {
	String str;
}

class Msg extends Request{
	String name;
	String msg;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	@Override
	public String toString() {
		return "Msg{" + "msg='" + msg + "\'" + "}";
	}
}

interface Filter{
	boolean doFilter(Request rq, Response rs, FilterChain fc);
}

class HtmlFilter implements Filter{

	@Override
	public boolean doFilter(Request rq, Response rp, FilterChain fc) {
		rq.str = rq.str.replace('<', '[').replace('>', ']') + "++HtmlFilter";
		fc.doFilter(rq, rp, fc);
		rp.str += "--HtmlFilter";
		return true;
	}
}

class SensitiveFilter implements Filter{

	@Override
	public boolean doFilter(Request rq, Response rp, FilterChain fc) {
		rq.str = rq.str.replace("996", "995") + "++SensitiveFilter";
		fc.doFilter(rq, rp, fc);
		rp.str += "--SensitiveFilter";
		return true;
	}
}

class FaceFilter implements Filter{

	@Override
	public boolean doFilter(Request rq, Response rp, FilterChain fc) {
		rq.str = rq.str.replace(":)", "^_^") + "++FaceFilter";
		fc.doFilter(rq, rp, fc);
		rp.str += "--FaceFilter";
		return true;
	}
}

class UrlFilter implements Filter{

	@Override
	public boolean doFilter(Request rq, Response rp, FilterChain fc) {
		rq.str = rq.str.replace("zxxa.com", "zxxa.vip.com") + "++UrlFilter";
		fc.doFilter(rq, rp, fc);
		rp.str += "--UrlFilter";
		return true;
	}
}

class FilterChain implements Filter{
	List<Filter> filters = new ArrayList<>();
	int index = 0;
	
	public FilterChain addFilter(Filter f) {
		filters.add(f);
		return this;
	}
	
	public boolean doFilter(Request rq, Response rp, FilterChain fc) {

		if (index == filters.size()) {
			return false;
		}
		Filter f = filters.get(index);
		index++;
		
		return f.doFilter(rq, rp, fc);
	}
}
