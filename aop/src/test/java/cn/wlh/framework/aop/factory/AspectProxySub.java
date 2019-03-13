package cn.wlh.framework.aop.factory;

import java.lang.reflect.Method;

import cn.wlh.framework.aop.proxy.AspectProxy;

public class AspectProxySub extends AspectProxy {

	/* (non-Javadoc)
	 * @see cn.wlh.framework.aop.proxy.AspectProxy#before(java.lang.Class, java.lang.reflect.Method, java.lang.Object[])
	 */
	@Override
	public void before(Class<?> cls, Method method, Object[] params) throws Throwable {
		System.out.println("1111111111111");
	}

	/* (non-Javadoc)
	 * @see cn.wlh.framework.aop.proxy.AspectProxy#after(java.lang.Class, java.lang.reflect.Method, java.lang.Object[], java.lang.Object)
	 */
	@Override
	public void after(Class<?> cls, Method method, Object[] params, Object result) throws Throwable {
		System.out.println("22222222222");
	}
}
