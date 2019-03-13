package cn.wlh.framework.aop.factory;

import cn.wlh.framework.aop.proxy.Proxy;
import cn.wlh.framework.aop.proxy.ProxyChain;

public class AspectProxySub1 implements Proxy {

	public Object doProxy(ProxyChain proxyChain) throws Throwable {
		System.out.println("AspectProxySub111111111111111");
		Object doProxyChain = proxyChain.doProxyChain();
		System.out.println("AspectProxySub2222222222222222");
		return doProxyChain;
	}

}
