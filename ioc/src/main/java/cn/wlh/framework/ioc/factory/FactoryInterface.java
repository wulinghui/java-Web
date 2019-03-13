package cn.wlh.framework.ioc.factory;

/**
 * @author 吴灵辉
 * 这里只是给出的一个模板，不一定要实现这个接口，但是一定要符合这个接口的规范。
 * @param <T>
 */
interface FactoryInterface<T> { 
	T getInstance( Object  [] pars);
	T getInstance( Class<? extends T> cla,Object  [] pars);
	
	T getInstance();
	T getInstance(Class<? extends T> key);
}
