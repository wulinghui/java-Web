package cn.wlh.framework.aop.factory;

import java.util.Set;

import cn.wlh.framework.aop.proxy1.ProxyInterface;
import cn.wlh.framework.ioc.factory.SingleFactory;
import cn.wlh.util.base._Class;
import net.sf.cglib.proxy.Enhancer;

/**
 * @author 吴灵辉 不建议使用它，这里的Cglib只能增强一个。 这里他是多选一执行。虽然不能直接链式调用，但是还是有一定的场景可以使用。
 */
public class ProxyFactory extends SingleFactory {
	final Enhancer enhancer;
	public ProxyFactory(ProxyInterface proxyInterface) {
		this(new Enhancer());
		this.enhancer.setCallbacks(proxyInterface.getMethodInterceptors());
		this.enhancer.setCallbackFilter(proxyInterface);
	}

	public ProxyFactory(Enhancer enhancer) {
		this.enhancer = enhancer;
	}
	/**
	 * @param classSet 为Class初始化。这里就实现了饿加载或者说登记的功能。
	 */
	public void init(Set<Class<?>> classSet) {
		for (Class<?> class1 : classSet) {
			try {
				getInstance(class1);
			} catch (Throwable e) {
				//给他报错，不往缓存中放入就行了。
			}
		}
	}
	/**
	 * @return the enhancer
	 */
	public Enhancer getEnhancer() {
		return enhancer;
	}


	
	@Override
	public Object newObject(Class<?> key, Object[] pars) {
		synchronized(this) {
			return createBean(enhancer,key, pars);
		}
	}
	
	/**静态方法提供给其他类使用。
	 * @param enhancer 为他加锁了。
	 * @param key
	 * @param pars
	 * @return
	 */
	public static Object createBean(Enhancer enhancer,Class<?> key, Object[] pars) {
			synchronized (enhancer) {
				enhancer.setSuperclass(key);
				if (pars == null || pars.length == 0) {
					return  enhancer.create();
				} else {
					Class<?>[] obj2Class = _Class.obj2Class(pars);
					return  enhancer.create(obj2Class, pars);
				}
			}
	}
}
