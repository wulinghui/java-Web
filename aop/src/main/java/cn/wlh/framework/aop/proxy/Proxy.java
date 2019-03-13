package cn.wlh.framework.aop.proxy;

/**
 * 代理接口
 *
 * @since 1.0.0
 */ 
public interface Proxy {  
 
    /**
     * 执行代理
     * 执行链式代理
     * result = proxyChain.doProxyChain();执行。
     */
    Object doProxy(ProxyChain proxyChain) throws Throwable;
} 