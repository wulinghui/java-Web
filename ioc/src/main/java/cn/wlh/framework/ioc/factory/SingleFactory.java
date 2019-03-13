package cn.wlh.framework.ioc.factory;

import java.util.Arrays;
import java.util.Map;

import cn.wlh.util.base.JavaUtilFactory;
import cn.wlh.util.base.RecordNewOfLog;
import cn.wlh.util.base._Class;
import cn.wlh.util.base.exception.ThrowableFacade;

public class SingleFactory extends RecordNewOfLog{
	public SingleFactory(char flag) {
		super(flag);
	}
	public SingleFactory() {
		this('e');
	}
	private Map<SingleObjectKey, Object> singleMap = JavaUtilFactory.newMap(JavaUtilFactory.SELECT_OF_FIELD);
	

	protected Object newObject(Class<?> key, Object[] pars) {
		Class<?>[] obj2Class = null;
		if(pars != null) obj2Class = _Class.obj2Class(pars);
		try {
			if( obj2Class ==null || obj2Class.length ==0 ) {
				return key.newInstance();
			}else {     
				return  key.getConstructor(obj2Class).newInstance(pars);
			}
		} catch (Exception e) {
			ThrowableFacade.THROW_RUN.doThrowOfRun(e);
		}
		return null;
	}

	protected Object getObjectFromMap(Class<?> key, Object[] pars) {
		SingleObjectKey singleObjectKey = new SingleObjectKey(key, pars);
		return singleMap.get(singleObjectKey);
	}

	public void put(Class<?> key, Object object, Object[] pars) {
		SingleObjectKey singleObjectKey = new SingleObjectKey(key, pars);
		singleMap.put(singleObjectKey, object);
	}

	public class SingleObjectKey {
		Class<?> key;
		Object[] pars;

		public SingleObjectKey(Class<?> key, Object[] pars) {
			super();
			this.key = key;
			this.pars = pars;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Object#hashCode()
		 */
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + getOuterType().hashCode();
			result = prime * result + ((key == null) ? 0 : key.hashCode());
			result = prime * result + Arrays.hashCode(pars);
			return result;
		}

		/*
		 * (non-Javadoc)
		 * 
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
			SingleObjectKey other = (SingleObjectKey) obj;
			if (!getOuterType().equals(other.getOuterType()))
				return false;
			if (key == null) {
				if (other.key != null)
					return false;
			} else if (!key.equals(other.key))
				return false;
			if (!Arrays.equals(pars, other.pars))
				return false;
			return true;
		}

		private SingleFactory getOuterType() {
			return SingleFactory.this;
		}

	}

	/**
	 * @return the singleMap
	 */
	public Map<SingleObjectKey, Object> getSingleMap() {
		return singleMap;
	}

	/**
	 * @param key
	 * @param value
	 * @return
	 * @see java.util.Map#put(java.lang.Object, java.lang.Object)
	 */
	public Object put(SingleObjectKey key, Object value) {
		return singleMap.put(key, value);
	}
	public <T> T getInstance(Class<? extends T> key) {  
		// new Map的key
		// SingleObjectKey singleObjectKey = new SingleObjectKey(key, pars);
		// Object object = singleMap.get(singleObjectKey);
		System.out.println("77777777777777777777");
		Object[] pars = new Object[] {};
		Object object =  getObjectFromMap(key , pars );
		if (object == null) {
			System.out.println("77777777object == null777777");
			// 反射获得。
			object = newObject(key, pars);
			// singleMap.put(singleObjectKey, object);
			put(key, object, pars);
		}
		return (T) object;
	}

	
}
