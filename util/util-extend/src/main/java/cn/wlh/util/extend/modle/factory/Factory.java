package cn.wlh.util.extend.modle.factory;

import java.util.HashMap;
import java.util.Map;

/**
 * 该类,把所有操作都由系统来做,效率和不透明.
 * 建议使用public static final 来确定单例和多例.饿汉模式 -接口
 * 使用工厂获得代替new 
 * 用多例+包装    -- 代替享元模式. .--更具针对性.@see Flyweight
 * 核心思想---约定大于规范.
 * 
 * 
 * @author wlh
 * 该类为工厂类,单例,享元,多例,new.
 * 这些工厂类是单例的 
 * @param <S>	S 存储对象
 * @param <T>	T new的对象
 * @param <F>	存储的标识
 * 其实只有享元模式可以用     
 */
public abstract class Factory<S,T,F>{
	
	S s = newStore() ;//store
	protected abstract  S newStore();//获得存储的对象
	//该方法子类可以适配它..
	public T getBean(Class<?> cla,F flag) {
		if( accpet() ) {
			T t = getBeanFormStore(flag);
			if( t == null ) {
				t = getInstance(cla);
				store(t,flag);
			}
			return t;
		}
		return null;
	}
	protected  boolean accpet() {return true;}//是否合法
//	protected abstract boolean isNoNewObj();//判断是否新new对象
//	protected abstract T noNewObj();//不新new对象,从store里面获得
	protected abstract T getBeanFormStore(F key);
	protected abstract  T getInstance(Class<?> cla);//新new对象
	protected abstract void store(T t,F key);//如何存储..

//	public static final Factory<Object,Object> SINGLE = new Factory<Object,Object>(){
	public static class Single<T> extends Factory<T,T,Boolean>{
		protected T getBeanFormStore(Boolean key) {
			return s;
		}
		protected T getInstance(Class<?> cla) {
			try {  
				return (T) cla.newInstance();
			} catch (Exception e) {  
				return null;
			}
		}
		protected void store(T t,Boolean key) {
			this.s = t;
		}
		@Override
		protected T newStore() {
			return null;
		}
	};
//	public static final Factory<Collection<Object>,Object> ENUM = new Factory<Collection<Object>,Object>(){
	public static class Enum0<T> extends Factory<Map<String,T>,T,String>{
		@Override
		protected Map<String, T> newStore() {   
			return new HashMap();
		}
		@Override
		protected T getBeanFormStore(String key) {  
			return super.s.get(key);
		}
		@Override
		protected T getInstance(Class<?> cla) {
			return null;
		}
		@Override
		protected void store(T t,String key) {
			super.s.put(key, t);
		}
	};
	//享元
	public static class XdYr<E,T>{
		Enum0<T> enu = new Enum0<T>();
		public XdYr0<E,T> getBean(E e,Class<T> cla,String flag){
			XdYr0<E,T> xd = new XdYr0<E,T>();
			xd.t = enu.getBean(cla, flag);
			xd.e = e;
			return xd;
		}
	}
	public static class XdYr0<E,T>{
		E e;
		T t;//内享的对象
		public E getE() {
			return e;
		}
		public void setE(E e) {
			this.e = e;
		}
		public T getT() {
			return t;
		}
		public void setT(T t) {
			this.t = t;
		}
	}
	//new 对象
	@Deprecated/**--效率太低....*/
	public static <C> C getBean(Class<C> c) {
		C obj = null;
		//获得某包下的所有方法,
		//通过return 类型确定方法,
		//反射,
		//--效率太低....
		return obj;
	}
}
