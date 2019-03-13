package cn.wlh.framework.web.cms.aop;

import java.lang.reflect.Method;

import cn.wlh.framework.aop.proxy.Proxy;
import cn.wlh.framework.aop.proxy.ProxyChain;
import cn.wlh.util.base.Logger;

/**
 * @author 吴灵辉
 * 执行时间记录。
 */
public class ExecuteTimeAop implements Proxy{
	private static final Logger log = Logger.getLogger(ExecuteTimeAop.class);
	@Override
	public Object doProxy(ProxyChain proxyChain) throws Throwable {
		Method targetMethod = proxyChain.getTargetMethod();
		if( targetMethod.getName().startsWith("txn") ) {
			long nanoTime = System.nanoTime();
			Object doProxyChain = proxyChain.doProxyChain();
			long nanoTime1 = System.nanoTime();
			log.debug(proxyChain.getTargetMethod() + "\texecute time:" + (nanoTime1 - nanoTime) + "nanoTime");
			return doProxyChain;
		}else {
			return proxyChain.doProxyChain();
		}
	}
}
