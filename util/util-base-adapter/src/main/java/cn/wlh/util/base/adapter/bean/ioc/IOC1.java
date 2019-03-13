package cn.wlh.util.base.adapter.bean.ioc;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import cn.wlh.util.base._Serializt;
import cn.wlh.util.base.adapter.java.util.JavaUtilFactory;
import cn.wlh.util.base.adapter.spring.core.bean.IOCOfSpringBean;

/**
 * @author 吴灵辉 根据之前的使用，IOC工厂不能统一使用,这会带来效率问题。 同时系统级别的策略模式在实际经常变更中也是挺少的。
 * 
 *         所以:new还是单独用指定的工厂。 接口单一实现类还是用Spring @Auto 只有在以下场景中才使用:
 *         1.类级别的版本迭代。 @see
 *         cn.wlh.util.base.adapter.bean.ioc.IOC1.getLatestBean(Class)
 *         2.超管需要自己变更new对象的方法。 (@see
 *         cn.wlh.util.base.adapter.bean.ioc.AbstractFactory1.ClientOfIOC.main(String[]))
 * 
 */
public abstract class IOC1 {
	IOC1() {
	}

	// static Map<String,IFactory> allFactory = new HashMap<String,IFactory>();
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//
	// 2.超管需要自己变更new对象的方法。
	// allFactory 尽量不要暴露个使用者.代码的非法使用性到小心
	// 如果需要修改建议使用反射
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////
	static Map<String, AbstractFactory1> allFactory = JavaUtilFactory.newMap(JavaUtilFactory.SELECT_OF_FIELD);

	/**
	 * 注册所有工厂..
	 * 
	 * @param factory
	 * @param id
	 */
	public static <T extends AbstractFactory1> void register(T factory) {
		allFactory.put(factory.getClass().getName(), factory);
	}

	/**
	 * 获得Bean
	 * 
	 * @param factory
	 * @param id
	 * @return
	 */
	public static <T extends AbstractFactory1<K>, K> K getBean(Class<T> factory, String id) {
		return (K) getAbstractFactory(factory).getBean(id);
	}

	/**通过工厂类来获得实例.
	 * @param factory
	 * @param fieldName
	 * @return
	 */
	public static <T extends AbstractFactory1<K>, K> K getBeanByFactoryField(Class<T> factory, String fieldName) {
		AbstractFactory1 iFactory = getAbstractFactory(factory);
		return (K) getBeanByFactoryField(iFactory, fieldName);
	}
	/**通过工厂类来获得实例.
	 * @param factory
	 * @param fieldName
	 * @return
	 */
	public static <K> K getBeanByFactoryField(AbstractFactory1<K> iFactory, String fieldName) {
		String value = iFactory.getValue(fieldName);
		return (K) iFactory.getBean(value);
	}

	/**获得抽象工厂.
	 * @param factory
	 * @return
	 */
	public static <T extends AbstractFactory1> AbstractFactory1 getAbstractFactory(Class<T> factory) {
		return allFactory.get(factory.getName());
	}

	/////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//
	// 1.类级别的版本迭代。
	// latestBeanOfSingleMap 尽量不要暴露个使用者.代码的非法使用性到小心
	// 如果需要修改建议使用反射
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////
	static Map<Class, Object> latestBeanOfSingleMap = JavaUtilFactory.newMap(JavaUtilFactory.SELECT_OF_FIELD);

	/**
	 * 只获得单例...
	 * 
	 * @param superClass
	 * @return
	 */
	public static <T> T getLatestBeanOfSingle(Class<T> superClass) {
		// 先从缓存中取单例..没有再从springBean取
		Object object = latestBeanOfSingleMap.get(superClass);
		if (object == null) {
			object = getLatestBean(superClass);
			latestBeanOfSingleMap.put(superClass, object);
		}
		return (T) object;
	}

	/**
	 * 容器里面没有就报错 如果使用这个方法应该避免对同一个实例的多次遍历..
	 * 
	 * @param superClass
	 * @return 不一定是单例.他会每次遍历的取值
	 */
	public static <T> T getLatestBean(Class<T> superClass) {
		// 这里必须每次都从容器里面遍历，原因:有可能他里面的会new出新的实例(spring不一定是单例)
		Map<String, T> beansOfType = 
				//				null; 
				IOCOfSpringBean.getBeansOfType(superClass);// TODO 不知道这里能不能实例化对象.
		Iterator<Entry<String, T>> iterator = beansOfType.entrySet().iterator();
		// 容器里面肯定有..不然就报错吧..
		Entry<String, T> temp = iterator.next();
		String tKey = temp.getKey();
		T tValue = temp.getValue();
		String nextKey = null;
		while (iterator.hasNext()) {
			temp = iterator.next();
			nextKey = temp.getKey();
			// 现在的key比之前的key大..就替换,当成老大.
			if (nextKey.compareTo(tKey) > 0) {
				tKey = nextKey;
				tValue = temp.getValue();
			}
		}
		// 返回最大的..
		return (T) tValue;
	}

	/////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//
	// 解决代码的非法使用问题..安全性的使用...
	// 对外不公布的单例模式,把方法改为实例级别
	// 这样使用者就方便了.
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////
//	static IOC1 ioc = new IOC1() {
//	}; // 我可以在这里解决代码的非法使用问题

	/** 更新new对象的策略.. */
	public void updateNewIdea(String factoryClassName, String key1, String key2) {
		allFactory.get(factoryClassName).falgInterchange(key1, key2);
	}

	/** 用于版本回退.. */
	public Object put(Class key, Object value) {
		return latestBeanOfSingleMap.put(key, value);
	}

	public Set<Entry<Class, Object>> entrySet() {
		return latestBeanOfSingleMap.entrySet();
	}

	/////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//
	// 得给超管.修改的信息呀..不给他怎么修改?
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public static Map<String, AbstractFactory1> copyAllFactory() {
		
		
		try {
			return (Map<String, AbstractFactory1>) _Serializt.clone(  allFactory);
		} catch (ClassNotFoundException | IOException e) {
			Map newMap = JavaUtilFactory.newMap(JavaUtilFactory.SELECT_OF_FIELD);
			newMap.putAll(allFactory);
			return newMap;
		}
	}

	public static Map<Class, Object> copyLatestBeanOfSingleMap() {
		try {
			return (Map<Class, Object>) _Serializt.clone(  latestBeanOfSingleMap);
		} catch (ClassNotFoundException | IOException e) {
			Map newMap = JavaUtilFactory.newMap(JavaUtilFactory.SELECT_OF_FIELD);
			newMap.putAll(latestBeanOfSingleMap);
			return newMap;
		}
	}
/////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
//1.当多个系统的时候--系统信息同步--数据库，且
//2.或者系统重启的时候 -- 修改的记录持久化
	//  思路:序列化与反序列化,但是具体怎么同步,怎么存储就不是我们这里关心的事了.
	//  TODO 百度  静态属性可以序列化吗?
//////////////////////////////////////////////////////////////////////////////////////////////////////////////
	static IOC1 ioc = new IOC1() {
		//如果整体IOC类不行，就不贪心一个属性一个属性的同步..如下.
		public  void setAllFactory(Map<String, AbstractFactory1> allFactory) {
			IOC1.allFactory = allFactory;
		}
		public  void setLatestBeanOfSingleMap(Map<Class, Object> latestBeanOfSingleMap) {
			IOC1.latestBeanOfSingleMap = latestBeanOfSingleMap;
		}
	}; // 我可以在这里解决代码的非法使用问题
}
