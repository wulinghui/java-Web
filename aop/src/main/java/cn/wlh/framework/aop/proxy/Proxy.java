package cn.wlh.framework.aop.proxy;

/**
 * ����ӿ�
 *
 * @since 1.0.0
 */ 
public interface Proxy {  
 
    /**
     * ִ�д���
     * ִ����ʽ����
     * result = proxyChain.doProxyChain();ִ�С�
     */
    Object doProxy(ProxyChain proxyChain) throws Throwable;
} 