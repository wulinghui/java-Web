package cn.wlh.framework.ioc.factory;

import static cn.wlh.util.base.RecordNewOfLog.RECORD_NEW_OBJECT_OF_LOG;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import cn.wlh.util.base.JavaUtilFactory;
import cn.wlh.util.base._Class;
import cn.wlh.util.base._Serializt;;

/**
 * @author 吴灵辉
 * 这是通过反射执行方法的。只需要有固定的方法名就行了。
 * 先从缓存中获得方法并执行。没有就从METHOD_SET里面获得的。
 * 因为jdk是擦除式泛型，所以无法在本类中获得泛型的。这里也就要求传入的工厂返回类型必须是确定的，不可以的泛型。
 */
public abstract class AppFactoryMan{
	AppFactoryMan(){}
	private static LinkedHashSet<Object> FACTORY_SET = new LinkedHashSet<Object>();
	private static List<FactoryMethodValue> METHOD_SET = new ArrayList<FactoryMethodValue>();
	private static Map<FactoryMethodKey,FactoryMethodValue> FACTORY_METHOD_MAP = JavaUtilFactory.newMap(JavaUtilFactory.SELECT_OF_FIELD); 
	private static final char LOG_FLAG = 'w';
	public static final String FACTORY_METHOD_NAME = "getInstance";
	/**添加静态方法*/
	public static void addFactory(Class<? extends Object> factoryClass) {
		addFactory(null, factoryClass,0);
	}
	/**添加工厂Bean*/
	public static void addFactory(Object factoryBean) {
		Objects.requireNonNull(factoryBean);
		addFactory(factoryBean, factoryBean.getClass(),0);
	}
	private static void addFactory(Object factoryBean , Class<? extends Object> class1, int index  ) {
		Objects.requireNonNull(class1);
		RECORD_NEW_OBJECT_OF_LOG.log(LOG_FLAG, "usering addFactory");
		//添加到Bean中
		if(factoryBean == null) {
			factoryBean = class1;//类
		}
		//如果重复就不再增加方法了。
		if( !FACTORY_SET.add(factoryBean) ) return; 
		//获得所有的规定的方法名
		for (Method method : class1.getDeclaredMethods()) {
			addFactoryMethod(factoryBean,method,index);
		}
		for (Method method : class1.getMethods()) {
			addFactoryMethod(factoryBean,method,index);
		}
	}
	/**
	 * @param obj
	 * @param method
	 * 一般一个容器在初始化工厂的时候就顺便初始化Bean了，缓存中就有了。所以这里和第一还是最后没有关系。
	 * 当时有一个场景，我的一个工厂是在运行中在添加进去的，这时候缓存里面遍历一遍在到方法里面遍历一遍。这是O(N2)
	 * 所以把他放到第一个提高一点效率。
	 * @param index 
	 */
	protected static void addFactoryMethod(Object obj,Method method, int index) {
		if(FACTORY_METHOD_NAME.equals(method.getName())) {
			synchronized (METHOD_SET) {
				METHOD_SET.add(index, new FactoryMethodValue(obj, method));//当一个系统需要代替一个系统是，但是那个系统已经整合
				//			METHOD_SET.add(0, new FactoryMethodValue(obj, method));//当一个系统需要代替一个系统是，但是那个系统已经整合
				//			METHOD_SET.add(new FactoryMethodValue(obj, method)); //这里先不设置权限，等放入缓存中在设置
			}
		}
	}
	/**有些时候系统和系统整合可能代理一个工厂的行为。这时候就需要这个方法了。
	 * 但是我们不做系统的破坏者，自己去找对应的工厂，我们不做帮凶。
	 * @param srcBean
	 * @param newBean
	 */
	public static void repalceFactoryAddClearCache(Object srcBean ,Object newBean) {
		repalceFactory(srcBean, newBean);
		clearCache();
	}
	public static void repalceFactory(Object srcBean ,Object newBean) {
		if( srcBean == null || newBean == null) return;
		synchronized (FACTORY_SET) {
			for (Object object : FACTORY_SET) {
				if(object.equals(srcBean)) {
					FACTORY_SET.remove(object);
					FACTORY_SET.add(newBean);
					synchronized (METHOD_SET) {
						int i = 0;
						List<Integer> listIndex = JavaUtilFactory.newList(JavaUtilFactory.INSERT_OF_METHOD);
						for (FactoryMethodValue factoryMethodValue : METHOD_SET) {
							if( factoryMethodValue.getInvokeObj().equals(object) ) {
								METHOD_SET.remove(object);
								listIndex.add(i);//记录位置。
							}
							i++;
						}
						//TODO 就先添加到最后把有问题再改，一般来说替换的话位置也是需要更改的。
//						addFactory(newBean);
						Class<?> class1;
						if(newBean instanceof Class) {
							class1 = (Class<?>) newBean;
						}else {
							class1 = newBean.getClass();
						}
						for (Integer integer : listIndex) {
							addFactory(newBean, class1, integer);
						}
					}
					break;
				}
			}
		}
	}
	public static void clearCache() {
		synchronized (FACTORY_METHOD_MAP) {
			FACTORY_METHOD_MAP.clear();
		}
	}
	/**这是去找方法名为cn.wlh.framework.ioc.factory.AppFactoryMan.FACTORY_METHOD_NAME的方法。
	 * 返回值是T的子类，先无参再第一个参数类型是Class.class的方法。
	 * @param key
	 * @return
	 */
	public static <T> T getInstance(Class<? extends T> key ) {
		return getInstance(null,true,key , null);
	}
	public static <T> T getInstance( Class<? extends T> key, Object... pars) {
		return getInstance(null, true, key, pars);
	}
	/**遍历所有的工厂，并把对象放入list里面中
	 * @param key
	 * @param pars
	 * @return
	 */
	public static  Collection<Object> getInstances( Class<?> key, Object... pars) {
		return getInstanceMap(key, pars).values();
	}
	/**遍历所有的工厂，并把对象放入Map中
	 * @param key 
	 * @param pars
	 * @return
	 */
	public static  Map<ReturnMapKey, Object> getInstanceMap( Class<?> key, Object... pars) {
		Map<ReturnMapKey,Object> newMap = JavaUtilFactory.newMap(JavaUtilFactory.SELECT_OF_METHOD);
		getInstance(newMap, false, key, pars);
		return newMap;
	}
	/**isStore -true存储*/
	protected static Object invokeAndStore(FactoryMethodKey methodKey, Method method, FactoryMethodValue methodValue0,Object [] args ,boolean isStore ){
		Object obj = null;
		try {
			method.setAccessible(true);
			obj = method.invoke(methodValue0.getInvokeObj(),args);
			if (isStore) {
				synchronized (FACTORY_METHOD_MAP) {
					if (obj != null)
						FACTORY_METHOD_MAP.put(methodKey, methodValue0);
				} 
			}
		} catch (Exception e) {
		}
		return obj;
	}
	protected static Object invokeAndStore(FactoryMethodKey methodKey, Method method, FactoryMethodValue methodValue0,boolean isStore)
			 {
		Object obj = null;
		try {
			method.setAccessible(true);
			obj = method.invoke(methodValue0.getInvokeObj());
			if (isStore) {
				synchronized (FACTORY_METHOD_MAP) {
					if (obj != null)
						FACTORY_METHOD_MAP.put(methodKey, methodValue0);
				} 
			}
		} catch (Exception e) {
		}
		return obj;
	}
	/**这是去找方法名为cn.wlh.framework.ioc.factory.AppFactoryMan.FACTORY_METHOD_NAME的方法。
	 * 返回值是T的子类，在以参数是Object[]的执行。
	 * 注意key不在执行方法参数里面。这在使用的时候需要注意
	 * @param map		遍历所有的工厂，并把对象放入Map中
	 * @param isBreak	是否退出，false的话-遍历所有的工厂
	 * @param key
	 * @param pars
	 * @return
	 */
	private static <T> T getInstance(Map<ReturnMapKey,Object> map, boolean isBreak, Class<? extends T> key, Object... pars) {
		//校验
		Objects.requireNonNull(key, "key is null");
		//生成key
		FactoryMethodKey methodKey = new FactoryMethodKey();
		Object ret = null;
		T t = null;
		T temp = null;
		methodKey.setClassType(key);
		methodKey.setPars(pars);
		//获得反射对象。
		FactoryMethodValue methodValue = FACTORY_METHOD_MAP.get(methodKey);
		if(methodValue !=null && isBreak) {
			try {
				//反射。
				Method method = methodValue.getMethod();
//				ret = method.invoke(methodValue.getInvokeObj(), pars); 2018年9月4日14:16:18
				ret = okArgsAndinvokeMethod(method, methodKey, methodValue, key, pars,false);
				t = (T) ret;
			} catch (Exception e) {
			}
		}else {  
			Method method = null;
			Class<?>[] parameterTypes;
			for (FactoryMethodValue methodValue0 : METHOD_SET) {
				try {
					method = methodValue0.getMethod();
					/*
					 * 返回类型是用户输入的子类。这里限定了万能的接口了（返回值是Object的）。
					 * 直接走
					 */
//				if(key.isAssignableFrom(method.getReturnType())  ) {
					//这个也不行，我们限定死了用户输入是具体实现类。
					if(method.getReturnType().isAssignableFrom(key)  ) {
						ret = okArgsAndinvokeMethod(method, methodKey, methodValue0, key, pars,true);
						if( ret == null) continue;
						//由于返回值是父类，无法确定他能否成功。
						if( t == null )
							t = (T) ret;//异常继续
							//退出。
							if(isBreak) {
								break;
							}else {
								temp = (T) ret;
								map.put(new ReturnMapKey(methodValue0, methodKey), temp);
								continue;
							}
					}
				} catch (Exception e) {
					
				}
			}
		}
		return t;
	}
	/**确定执行方法的参数并是否存储
	 * @param method
	 * @param methodKey
	 * @param methodValue0
	 * @param key
	 * @param pars
	 * @param isStore true-存储
	 * @return
	 */
	protected static Object okArgsAndinvokeMethod(Method method,FactoryMethodKey methodKey,FactoryMethodValue methodValue0,Class<?> key,Object[] pars,boolean isStore) {
		Class<?>[] parameterTypes = method.getParameterTypes();
		Object ret;
		//无参，不通过异常中止。
		if( parameterTypes == null ) {
			return null;
		}
		//
		int length = parameterTypes.length;
		//没有参数
		if (   length == 0 ) {
			ret = invokeAndStore( methodKey, method, methodValue0 ,isStore);
		//一个参数且是Class的时候
		}else if (length == 1 && Class.class.equals(parameterTypes[0])){
			ret = invokeAndStore( methodKey, method, methodValue0 , new Object[] { key },isStore);
		//一个参数且不是Class的时候
		}else if (length == 1 && !Class.class.equals(parameterTypes[0])){
			//这里我们转换一下不要用原生的pars
			ret = invokeAndStore( methodKey, method, methodValue0, new Object[] { pars[0] } ,isStore);
		//参数大于2且第一个是Class的时候 -- 这是以key 和  pars一起反射的。
		}else if( length > 2 &&  Class.class.equals(parameterTypes[0])) {
			pars = Arrays.copyOfRange(pars, 1, length);
			ret = invokeAndStore( methodKey, method, methodValue0, new Object[] { key ,pars } ,isStore);
		//其他情况，大部分是pars is object[]
		}else {
			ret = invokeAndStore( methodKey, method, methodValue0, pars ,isStore);
		}
		return ret;
	}
	/**
	 * @author 吴灵辉
	 * 放回的
	 */
	public static class ReturnMapKey{
		FactoryMethodValue methodValue;
		FactoryMethodKey methodKey;
		public ReturnMapKey(FactoryMethodValue methodValue, FactoryMethodKey methodKey) {
			super();
			this.methodValue = methodValue;
			this.methodKey = methodKey;
		}
		/**
		 * @return the methodValue
		 */
		public FactoryMethodValue getMethodValue() {
			return methodValue;
		}
		/**
		 * @return the methodKey
		 */
		public FactoryMethodKey getMethodKey() {
			return methodKey;
		}
		/* (non-Javadoc)
		 * @see java.lang.Object#hashCode()
		 */
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((methodKey == null) ? 0 : methodKey.hashCode());
			result = prime * result + ((methodValue == null) ? 0 : methodValue.hashCode());
			return result;
		}
		/* (non-Javadoc)
		 * @see java.lang.Object#equals(java.lang.Object)
		 */
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			ReturnMapKey other = (ReturnMapKey) obj;
			if (methodKey == null) {
				if (other.methodKey != null)
					return false;
			} else if (!methodKey.equals(other.methodKey))
				return false;
			if (methodValue == null) {
				if (other.methodValue != null)
					return false;
			} else if (!methodValue.equals(other.methodValue))
				return false;
			return true;
		}
		@Override
		public String toString() {
			return "ReturnMapKey [methodValue=" + methodValue + ", methodKey=" + methodKey + "]";
		}
	}
	/**
	 * @return the fACTORY_SET
	 */
	public static Set<Object> getFactorySet() {
		return cloneSet(FACTORY_SET,LinkedHashSet.class);
	}
	protected static Set cloneSet(Collection<?> FACTORY_SET ,Class<? extends Collection> returnClass) {
		Object obj = null;
		try {
			obj = _Serializt.clone(FACTORY_SET);
		} catch (Exception e) {
		}
		if( obj == null ) {
			Collection set = _Class.newObj(returnClass);
			set.addAll(FACTORY_SET);
			obj = set;
		}
		return  (Set) obj;
	}
	/**
	 * @return the mETHOD_SET
	 */
	public static Set<FactoryMethodValue> getFactoryMethodValueSet() {
		return cloneSet(METHOD_SET,LinkedHashSet.class);
	}
	public static class FactoryMethodValue{
		private Object invokeObj;//反射的对象。
		private Method method;
		public FactoryMethodValue(Object invokeObj, Method method) {
			super();
			this.invokeObj = invokeObj;
			this.method = method;
		}
		/**
		 * @return the invokeObj
		 */
		public Object getInvokeObj() {
			return invokeObj;
		}
		/**
		 * @return the method
		 */
		public Method getMethod() {
			return method;
		}
	}
	static class FactoryMethodKey{
		private Class<?> classType;
		/**
		 * 这里缓存的是类型还是用户完全输出的？
		 */
		private Object[] pars;
		/**
		 * @param classType the classType to set
		 */
		public void setClassType(Class<?> classType) {
			this.classType = classType;
		}
		/**
		 * @param pars the pars to set
		 */
		public void setPars(Object[] pars) {
			this.pars = pars;
		}
		/* (non-Javadoc)
		 * @see java.lang.Object#hashCode()
		 */
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((classType == null) ? 0 : classType.hashCode());
			result = prime * result + Arrays.hashCode(pars);
			return result;
		}
		/* (non-Javadoc)
		 * @see java.lang.Object#equals(java.lang.Object)
		 */
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			FactoryMethodKey other = (FactoryMethodKey) obj;
			if (classType == null) {
				if (other.classType != null)
					return false;
			} else if (!classType.equals(other.classType))
				return false;
			if (!Arrays.equals(pars, other.pars))
				return false;
			return true;
		}
	}
	
	
	
}