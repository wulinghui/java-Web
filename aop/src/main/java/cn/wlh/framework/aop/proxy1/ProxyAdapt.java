package cn.wlh.framework.aop.proxy1;

import java.lang.reflect.Method;

import cn.wlh.util.base.RecordNewOfLog;
import net.sf.cglib.proxy.MethodInterceptor;

public abstract class ProxyAdapt extends RecordNewOfLog  implements ProxyInterface, MethodInterceptor {
	public ProxyAdapt(char flag) {
		super('d');
	}

	public int accept(Method method) {
		return 0;
	}

	public MethodInterceptor[] getMethodInterceptors() {
		return new MethodInterceptor[]{this};
	}
}
