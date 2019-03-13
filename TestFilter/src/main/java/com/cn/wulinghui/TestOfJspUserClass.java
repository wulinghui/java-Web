package com.cn.wulinghui;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class TestOfJspUserClass implements javax.servlet.Filter{
static ThreadLocal<ServletRequest>	REQUEST = new ThreadLocal<>();
static ThreadLocal<ServletResponse>	RESPONSE = new ThreadLocal<>();
	
 public void wodeceshi() {
	 System.out.println("xxx");     
 }

@Override
public void init(FilterConfig filterConfig) throws ServletException {
	// TODO Auto-generated method stub
	System.out.println("init");
}

@Override
public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
		throws IOException, ServletException {
	// TODO Auto-generated method stub
	System.out.println("doFilter1111");
	REQUEST.set(request);
	RESPONSE.set(response);
	chain.doFilter(request, response);
	REQUEST.remove();
	RESPONSE.remove();
	System.out.println("doFilter2222");
}

@Override
public void destroy() {
	// TODO Auto-generated method stub
	System.out.println("destroy");
}

/**
 * @return
 * @see java.lang.ThreadLocal#get()
 */
public static ServletRequest getRequest() {
	return REQUEST.get();
}

/**
 * @return
 * @see java.lang.ThreadLocal#get()
 */
public static ServletResponse getResponse() {
	return RESPONSE.get();
}

}
