package cn.wlh.framework.ioc.factory;

import java.util.Arrays;
import java.util.Map;

public class EnumFactory extends SingleFactory {
	SingleFactory singleFactory;
	/**
	 * Ĭ���ø���ķ�����
	 */
	public EnumFactory() {
		
	}
	/**
	 * @param singleFactory �����޸�ʹ�������ࡣ 
	 */
	/**
	 * @param logFlag 'e' 'w' 'i' 'd' 't' 
	 * @param singleFactory
	 */
	public EnumFactory(char logFlag,SingleFactory singleFactory) {
		super(logFlag);
		this.singleFactory = singleFactory;
	}

	/**
	 * @param pars ��һ���Ǳ�ʶ���������Ƿ���Ĳ�����
	 */
	public <T>  T getInstance(Class<? extends T> key, Object[] pars) {
		Object object =  getObjectFromMap(key , pars );
		if (object == null) {
			// �����á�
			object = newObject(key, pars);
			// singleMap.put(singleObjectKey, object);
			put(key, object, pars);
		}
		return (T) object;
	}
	/**
	 * @param pars ��һ���Ǳ�ʶ���������Ƿ���Ĳ�����
	 */
	protected Object newObject(Class<?> key, Object[] pars) {
		if(pars == null || pars.length ==0 ) {
			//֮ǰĬ�ϵġ�
		}else {
			//��һ���Ǳ�ʶ
			pars = Arrays.copyOfRange(pars, 1, pars.length);
		}
		if(singleFactory == null) {
			return super.newObject(key, pars);
		}else {
			return singleFactory.newObject(key, pars);
		}
	}
	/**
	 * @param key
	 * @param object
	 * @param pars
	 * @see cn.wlh.framework.ioc.factory.SingleFactory#put(java.lang.Class, java.lang.Object, java.lang.Object[])
	 */
	public void put(Class<?> key, Object object, Object[] pars) {
		if(singleFactory == null) {
			super.put(key, object, pars);
		}else {
			singleFactory.put(key, object, pars);
		}
		singleFactory.put(key, object, pars);
	}
	/**
	 * @return
	 * @see cn.wlh.framework.ioc.factory.SingleFactory#getSingleMap()
	 */
	public Map<SingleObjectKey, Object> getSingleMap() {
		if(singleFactory == null) {
			return super.getSingleMap();
		}else {
			return singleFactory.getSingleMap();
		}
	}
	/**
	 * @param key
	 * @param value
	 * @return
	 * @see cn.wlh.framework.ioc.factory.SingleFactory#put(cn.wlh.framework.ioc.factory.SingleFactory.SingleObjectKey, java.lang.Object)
	 */
	public Object put(SingleObjectKey key, Object value) {
		if(singleFactory == null) {
			return super.put(key, value);
		}else {
			return singleFactory.put(key, value);
		}
	}
}
