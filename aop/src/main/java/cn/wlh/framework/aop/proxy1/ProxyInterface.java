package cn.wlh.framework.aop.proxy1;

import net.sf.cglib.proxy.CallbackFilter;
import net.sf.cglib.proxy.MethodInterceptor;

/**
 * @author 吴灵辉
 * 不建议使用它，这里的Cglib只能增强一个。
 * 这里他是多选一执行。虽然不能直接链式调用，但是还是有一定的场景可以使用。
 * @see cn.wlh.framework.aop.proxy1.TestCGLibProxy.main(String[])
 */
public interface ProxyInterface extends CallbackFilter{
	MethodInterceptor[] getMethodInterceptors();
}


