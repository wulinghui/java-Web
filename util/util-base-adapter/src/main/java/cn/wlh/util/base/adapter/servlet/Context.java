package cn.wlh.util.base.adapter.servlet;

import java.util.Map;
import java.util.function.BiFunction;

import cn.wlh.util.base.adapter.java.util.JavaUtilFactory;

public class Context extends DataBus{
	public Context() {
		super(JavaUtilFactory.INSERT_OF_FIELD);
	}
	/**
	 * 不准使用.直接异常
	 */
	public static final String  EXCEPTION_MASEEGE = "不准使用.直接异常";
	public static void notAllowUser(){
		throw new RuntimeException( EXCEPTION_MASEEGE);
	}
	@Override
	public void putAll(Map m) {
		this.notAllowUser();
	}
	@Override
	public void putAll0(DataBus m) {
		this.notAllowUser();
	}
//	@Override
//	public void replaceAll(BiFunction<? super String, ? super Object, ? extends Object> function) {
//		this.notAllowUser();
//	}
	@Override
	public void clear() {
		this.notAllowUser();
	}
	@Override
	protected void setSource(Map<String, Object> source) {
		this.notAllowUser();
	}
	
}
