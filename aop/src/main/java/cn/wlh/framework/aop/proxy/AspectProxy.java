package cn.wlh.framework.aop.proxy;

import java.lang.reflect.Method;

/**
 *  切面代理
 *
 * @since 1.0.0  
 */
public abstract class AspectProxy implements Proxy {

	
    public final Object doProxy(ProxyChain proxyChain) throws Throwable {
        Object result = null;

        Class<?> cls = proxyChain.getTargetClass();
        Method method = proxyChain.getTargetMethod();
        Object[] params = proxyChain.getMethodParams();
        //开始
        begin();
        try {
        	//是否开始，过滤。
            if (intercept(cls, method, params)) {
                before(cls, method, params);
                /*
                 * 这里执行的内容是对称执行的。
                 * 第1和倒数第1。
                 */
                result = proxyChain.doProxyChain();
                
                after(cls, method, params, result);
            } else {
                result = proxyChain.doProxyChain();
            }
        } catch (Throwable e) {
            error(cls, method, params, e);          
            throw e;
        } finally {
            end();
        }  
        return result;
    }

    public void begin() {
    }

    public boolean intercept(Class<?> cls, Method method, Object[] params) throws Throwable {
        return true;
    }

    public void before(Class<?> cls, Method method, Object[] params) throws Throwable {
    }

    public void after(Class<?> cls, Method method, Object[] params, Object result) throws Throwable {
    }

    public void error(Class<?> cls, Method method, Object[] params, Throwable e) {
    }

    public void end() {
    }
}