package cn.wlh.framework.aop.proxy1;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

/**
 * Create by zxb on 2017/4/22
 */
public class DBQueryProxy2 implements MethodInterceptor {
 
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        System.out.println("Here in interceptor 2£¡");
        return methodProxy.invokeSuper(o, objects);
    }
    
    
}
