package cn.wlh.framework.aop.proxy1;

import net.sf.cglib.proxy.CallbackFilter;
import net.sf.cglib.proxy.MethodInterceptor;

/**
 * @author �����
 * ������ʹ�����������Cglibֻ����ǿһ����
 * �������Ƕ�ѡһִ�С���Ȼ����ֱ����ʽ���ã����ǻ�����һ���ĳ�������ʹ�á�
 * @see cn.wlh.framework.aop.proxy1.TestCGLibProxy.main(String[])
 */
public interface ProxyInterface extends CallbackFilter{
	MethodInterceptor[] getMethodInterceptors();
}


