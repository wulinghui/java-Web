package cn.wlh.util.base.exception;

import java.util.Map;
import java.util.Objects;

import cn.wlh.util.base.JavaUtilFactory;

/**
 * @author 吴灵辉
 * 享元模式,实现一些不变类的共享..
 */@Deprecated //@see cn.wlh.util.base.exception.AbstractExceptionOfArrayCache<T>
public class AbstractExceptionOfFlyWeight<K,T extends Throwable> {
	/***/
	private static final long serialVersionUID = 3870803932126272408L;
	// 以Message为Key..
	Map<K ,T> cache = JavaUtilFactory.newMap(JavaUtilFactory.SELECT_OF_METHOD);
	
	protected void put(K key , T e) {
		e.getStackTrace();
		cache.put(key, e);
	}
	
	protected T get(K key) {
		return cache.get(key);
	}
	
	protected final  void excuteThrows(T e) throws T {
		throw e;
	}
	
//	static RuntimeException exsitsException = new RuntimeException("Exception exsits not can new");
//	//强制要求..不准他new..
//	public AbstractExceptionOfFlyWeight(Object message) {
//		if( cache.get(message) != null ) throw exsitsException;
//	}
//	
//	public  AbstractExceptionOfFlyWeight newInstance(String message) {
//		AbstractExceptionOfFlyWeight abstractExceptionOfFlyWeight = cache.get(message);
//		if( abstractExceptionOfFlyWeight == null) {
//			abstractExceptionOfFlyWeight = new AbstractExceptionOfFlyWeight(message);
//			cache.put(message, abstractExceptionOfFlyWeight);
//		}
//		return abstractExceptionOfFlyWeight;
//	}
	
	
	
	
	
	
	
	
	
	
//	public static abstract class Excep<T extends Throwable>{
//		T exceptionBean;
//		public Excep(T exceptionBean) {
//			Objects.requireNonNull(exceptionBean , "exceptionBean is null , new exceptionBean failer");
//			this.exceptionBean = exceptionBean;
//		}
//
//		public final  void excute() throws T {
//			throw exceptionBean;
//		};
//	}
//	public static  class Excep111 extends Excep<RuntimeException>{
//		public Excep111(RuntimeException exceptionBean) {
//			super(exceptionBean);
//		}
////		public void excute() throws RuntimeException {
////			throw new RuntimeException();
////		}
//	}
//	public static void main(String[] args) {
//		new Excep111(null).excute(); //这样也可以..不用抛异常...
//	}
}
