package cn.wlh.util.base.adapter.bean.ioc;

import java.util.HashMap;
import java.util.Map;

/*
在做项目的时候，突然想自己做个IOC的框架。
1.为什么spring的@Auto..注入,没有版本控制(为了满足开闭原则系统级的用一个类代替另一个类的类型注入，系统级的策略使用)。如果用注解的形式无法控制一个接口实现类的版本，用@Requred(name=’’)这就成了硬编码了。
如果放在xml里面将变得非常庞大,且需要重启(热编译)。
2.Spring无法在方法中注入，只能通过底层的BeanFactory里面的bean来获得里面的bean。
3.直接用Spring框架无法实现系统与第三方jar的解耦。

先不考虑。就先封装BeanFactory的使用。接口 <T> T getBean(Class<T> c , String beanName);
 这就是一个万能工厂，代替spring的注入，里面使用spring的bean.和提供了new的使用，里面默认有一个最高版本的bean，同时提供动态的改变new和使用bean的 set功能。3这个先不考虑，成本大同时这里已经相对解耦了。


试了一下:
这样带来的效率问题只有在java10(switch支持表达式)中才可以根除.
1.这里需要维护数据库--实现记忆功能.(系统级别的策略模式.这个功能是必须的). --不算问题
2.一个Map集合的内存和查找效率, 再在一个IFactory里面new一个具体的类. (2重查找效率问题.)   --主要是一个Map的维护.
3.在维护的时候不直观,需要先找到是谁注册的key,然后再对照数据库看具体的getBean方法.         --对维护来说是个噩梦.就想烽火台的封装(各种配置表一样.) 
 */
public  class IOC {
	static Map<Class,IFactory> allFactory = new HashMap<Class,IFactory>(); 
	/** 注册
	 * @param cla
	 * @param id
	 */
	public static void register(Class cla, IFactory id) {
		allFactory.put(cla, id);
	}
	/** 获得Bean
	 * @param cla
	 * @param id
	 * @return
	 */
	public static <T> T getBean(Class<T> cla, String id) {
		return (T) allFactory.get(cla).getBean(id);
	}
}
