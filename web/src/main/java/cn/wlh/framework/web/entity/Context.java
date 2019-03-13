package cn.wlh.framework.web.entity;

import cn.wlh.util.base.adapter.servlet1.Context3;

public class Context extends Context3 {
	public static final ThreadLocal<Context> THREAD_CONTEXT = new ThreadLocal<Context>();
	public static Context getContext() {
		return THREAD_CONTEXT.get();
	}  
}
