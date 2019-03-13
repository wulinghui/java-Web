package cn.wlh.framework.aop.factory;

import java.util.Set;

import cn.wlh.framework.aop.proxy1.ProxyInterface;
import cn.wlh.framework.ioc.factory.SingleFactory;
import cn.wlh.util.base._Class;
import net.sf.cglib.proxy.Enhancer;

/**
 * @author ����� ������ʹ�����������Cglibֻ����ǿһ���� �������Ƕ�ѡһִ�С���Ȼ����ֱ����ʽ���ã����ǻ�����һ���ĳ�������ʹ�á�
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
	 * @param classSet ΪClass��ʼ���������ʵ���˶����ػ���˵�ǼǵĹ��ܡ�
	 */
	public void init(Set<Class<?>> classSet) {
		for (Class<?> class1 : classSet) {
			try {
				getInstance(class1);
			} catch (Throwable e) {
				//�����������������з�������ˡ�
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
	
	/**��̬�����ṩ��������ʹ�á�
	 * @param enhancer Ϊ�������ˡ�
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
