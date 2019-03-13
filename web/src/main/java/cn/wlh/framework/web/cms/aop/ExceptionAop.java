package cn.wlh.framework.web.cms.aop;

import java.lang.reflect.Method;

import cn.wlh.framework.aop.proxy.Proxy;
import cn.wlh.framework.aop.proxy.ProxyChain;
import cn.wlh.util.base.Logger;

/**
 * @author 吴灵辉
 * 获得交易异常。
 */
public class ExceptionAop implements Proxy {
	private static final Logger log = Logger.getLogger(ExceptionAop.class);
	@Override
	public Object doProxy(ProxyChain proxyChain) throws Throwable {
		try {
			return proxyChain.doProxyChain();
		} catch (Throwable e) {
			Method targetMethod = proxyChain.getTargetMethod();
			if( targetMethod.getName().startsWith("txn") ) {
				StringBuilder sb =new StringBuilder("MethodParams:");
				Object[] methodParams = proxyChain.getMethodParams();
				if( methodParams!=null && methodParams.length !=0 ) {
					for (Object object : methodParams) {
						object = LogAop.ArrayFont(object);
						sb.append(object).append('\t');
					}
				}
				sb.deleteCharAt(sb.length()-1);
				sb.append('\n');
				sb.append(targetMethod);
				sb.append( "throws Exception:");
				log.error(sb.toString(), e);
			}
			throw e;
		}
	}

}
