package cn.wlh.framework.ioc.factory;

/**
 * @author �����
 * ����ֻ�Ǹ�����һ��ģ�壬��һ��Ҫʵ������ӿڣ�����һ��Ҫ��������ӿڵĹ淶��
 * @param <T>
 */
interface FactoryInterface<T> { 
	T getInstance( Object  [] pars);
	T getInstance( Class<? extends T> cla,Object  [] pars);
	
	T getInstance();
	T getInstance(Class<? extends T> key);
}
