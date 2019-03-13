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
 * @author �����
 * ����ͨ������ִ�з����ġ�ֻ��Ҫ�й̶��ķ����������ˡ�
 * �ȴӻ����л�÷�����ִ�С�û�оʹ�METHOD_SET�����õġ�
 * ��Ϊjdk�ǲ���ʽ���ͣ������޷��ڱ����л�÷��͵ġ�����Ҳ��Ҫ����Ĺ����������ͱ�����ȷ���ģ������Եķ��͡�
 */
public abstract class AppFactoryMan{
	AppFactoryMan(){}
	private static LinkedHashSet<Object> FACTORY_SET = new LinkedHashSet<Object>();
	private static List<FactoryMethodValue> METHOD_SET = new ArrayList<FactoryMethodValue>();
	private static Map<FactoryMethodKey,FactoryMethodValue> FACTORY_METHOD_MAP = JavaUtilFactory.newMap(JavaUtilFactory.SELECT_OF_FIELD); 
	private static final char LOG_FLAG = 'w';
	public static final String FACTORY_METHOD_NAME = "getInstance";
	/**��Ӿ�̬����*/
	public static void addFactory(Class<? extends Object> factoryClass) {
		addFactory(null, factoryClass,0);
	}
	/**��ӹ���Bean*/
	public static void addFactory(Object factoryBean) {
		Objects.requireNonNull(factoryBean);
		addFactory(factoryBean, factoryBean.getClass(),0);
	}
	private static void addFactory(Object factoryBean , Class<? extends Object> class1, int index  ) {
		Objects.requireNonNull(class1);
		RECORD_NEW_OBJECT_OF_LOG.log(LOG_FLAG, "usering addFactory");
		//��ӵ�Bean��
		if(factoryBean == null) {
			factoryBean = class1;//��
		}
		//����ظ��Ͳ������ӷ����ˡ�
		if( !FACTORY_SET.add(factoryBean) ) return; 
		//������еĹ涨�ķ�����
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
	 * һ��һ�������ڳ�ʼ��������ʱ���˳���ʼ��Bean�ˣ������о����ˡ���������͵�һ�������û�й�ϵ��
	 * ��ʱ��һ���������ҵ�һ��������������������ӽ�ȥ�ģ���ʱ�򻺴��������һ���ڵ������������һ�顣����O(N2)
	 * ���԰����ŵ���һ�����һ��Ч�ʡ�
	 * @param index 
	 */
	protected static void addFactoryMethod(Object obj,Method method, int index) {
		if(FACTORY_METHOD_NAME.equals(method.getName())) {
			synchronized (METHOD_SET) {
				METHOD_SET.add(index, new FactoryMethodValue(obj, method));//��һ��ϵͳ��Ҫ����һ��ϵͳ�ǣ������Ǹ�ϵͳ�Ѿ�����
				//			METHOD_SET.add(0, new FactoryMethodValue(obj, method));//��һ��ϵͳ��Ҫ����һ��ϵͳ�ǣ������Ǹ�ϵͳ�Ѿ�����
				//			METHOD_SET.add(new FactoryMethodValue(obj, method)); //�����Ȳ�����Ȩ�ޣ��ȷ��뻺����������
			}
		}
	}
	/**��Щʱ��ϵͳ��ϵͳ���Ͽ��ܴ���һ����������Ϊ����ʱ�����Ҫ��������ˡ�
	 * �������ǲ���ϵͳ���ƻ��ߣ��Լ�ȥ�Ҷ�Ӧ�Ĺ��������ǲ������ס�
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
								listIndex.add(i);//��¼λ�á�
							}
							i++;
						}
						//TODO ������ӵ������������ٸģ�һ����˵�滻�Ļ�λ��Ҳ����Ҫ���ĵġ�
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
	/**����ȥ�ҷ�����Ϊcn.wlh.framework.ioc.factory.AppFactoryMan.FACTORY_METHOD_NAME�ķ�����
	 * ����ֵ��T�����࣬���޲��ٵ�һ������������Class.class�ķ�����
	 * @param key
	 * @return
	 */
	public static <T> T getInstance(Class<? extends T> key ) {
		return getInstance(null,true,key , null);
	}
	public static <T> T getInstance( Class<? extends T> key, Object... pars) {
		return getInstance(null, true, key, pars);
	}
	/**�������еĹ��������Ѷ������list������
	 * @param key
	 * @param pars
	 * @return
	 */
	public static  Collection<Object> getInstances( Class<?> key, Object... pars) {
		return getInstanceMap(key, pars).values();
	}
	/**�������еĹ��������Ѷ������Map��
	 * @param key 
	 * @param pars
	 * @return
	 */
	public static  Map<ReturnMapKey, Object> getInstanceMap( Class<?> key, Object... pars) {
		Map<ReturnMapKey,Object> newMap = JavaUtilFactory.newMap(JavaUtilFactory.SELECT_OF_METHOD);
		getInstance(newMap, false, key, pars);
		return newMap;
	}
	/**isStore -true�洢*/
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
	/**����ȥ�ҷ�����Ϊcn.wlh.framework.ioc.factory.AppFactoryMan.FACTORY_METHOD_NAME�ķ�����
	 * ����ֵ��T�����࣬���Բ�����Object[]��ִ�С�
	 * ע��key����ִ�з����������档����ʹ�õ�ʱ����Ҫע��
	 * @param map		�������еĹ��������Ѷ������Map��
	 * @param isBreak	�Ƿ��˳���false�Ļ�-�������еĹ���
	 * @param key
	 * @param pars
	 * @return
	 */
	private static <T> T getInstance(Map<ReturnMapKey,Object> map, boolean isBreak, Class<? extends T> key, Object... pars) {
		//У��
		Objects.requireNonNull(key, "key is null");
		//����key
		FactoryMethodKey methodKey = new FactoryMethodKey();
		Object ret = null;
		T t = null;
		T temp = null;
		methodKey.setClassType(key);
		methodKey.setPars(pars);
		//��÷������
		FactoryMethodValue methodValue = FACTORY_METHOD_MAP.get(methodKey);
		if(methodValue !=null && isBreak) {
			try {
				//���䡣
				Method method = methodValue.getMethod();
//				ret = method.invoke(methodValue.getInvokeObj(), pars); 2018��9��4��14:16:18
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
					 * �����������û���������ࡣ�����޶������ܵĽӿ��ˣ�����ֵ��Object�ģ���
					 * ֱ����
					 */
//				if(key.isAssignableFrom(method.getReturnType())  ) {
					//���Ҳ���У������޶������û������Ǿ���ʵ���ࡣ
					if(method.getReturnType().isAssignableFrom(key)  ) {
						ret = okArgsAndinvokeMethod(method, methodKey, methodValue0, key, pars,true);
						if( ret == null) continue;
						//���ڷ���ֵ�Ǹ��࣬�޷�ȷ�����ܷ�ɹ���
						if( t == null )
							t = (T) ret;//�쳣����
							//�˳���
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
	/**ȷ��ִ�з����Ĳ������Ƿ�洢
	 * @param method
	 * @param methodKey
	 * @param methodValue0
	 * @param key
	 * @param pars
	 * @param isStore true-�洢
	 * @return
	 */
	protected static Object okArgsAndinvokeMethod(Method method,FactoryMethodKey methodKey,FactoryMethodValue methodValue0,Class<?> key,Object[] pars,boolean isStore) {
		Class<?>[] parameterTypes = method.getParameterTypes();
		Object ret;
		//�޲Σ���ͨ���쳣��ֹ��
		if( parameterTypes == null ) {
			return null;
		}
		//
		int length = parameterTypes.length;
		//û�в���
		if (   length == 0 ) {
			ret = invokeAndStore( methodKey, method, methodValue0 ,isStore);
		//һ����������Class��ʱ��
		}else if (length == 1 && Class.class.equals(parameterTypes[0])){
			ret = invokeAndStore( methodKey, method, methodValue0 , new Object[] { key },isStore);
		//һ�������Ҳ���Class��ʱ��
		}else if (length == 1 && !Class.class.equals(parameterTypes[0])){
			//��������ת��һ�²�Ҫ��ԭ����pars
			ret = invokeAndStore( methodKey, method, methodValue0, new Object[] { pars[0] } ,isStore);
		//��������2�ҵ�һ����Class��ʱ�� -- ������key ��  parsһ����ġ�
		}else if( length > 2 &&  Class.class.equals(parameterTypes[0])) {
			pars = Arrays.copyOfRange(pars, 1, length);
			ret = invokeAndStore( methodKey, method, methodValue0, new Object[] { key ,pars } ,isStore);
		//����������󲿷���pars is object[]
		}else {
			ret = invokeAndStore( methodKey, method, methodValue0, pars ,isStore);
		}
		return ret;
	}
	/**
	 * @author �����
	 * �Żص�
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
		private Object invokeObj;//����Ķ���
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
		 * ���ﻺ��������ͻ����û���ȫ����ģ�
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