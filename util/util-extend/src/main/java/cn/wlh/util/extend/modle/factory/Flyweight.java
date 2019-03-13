package cn.wlh.util.extend.modle.factory;

import java.util.HashMap;
import java.util.Map;

import cn.wlh.util.base._Field;
import cn.wlh.util.base.interfaces._Filter;

/**
 * @author wlh
 * Class<?> tar 里面的多例就是共享的.
 * 
 * @param <T> -- 工享对象
 */
public class Flyweight<T>{  
	Map<String,T> flyMap = new HashMap<String,T>();
	private Flyweight(){}
	
	public static <T1> Flyweight<T1> newInstance(Class<?> tar) throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
		//通过反射获得. c类的所有 public static 属性,并放入map中  
		Flyweight f = new Flyweight();     
		_Field.getStaticFieldValues( f.flyMap, false, tar, _Filter.NO_FILTER);    
		return f;
	}
	
	public <A> ZiLi<T,A> newBean(String key, A a){
		ZiLi<T,A>  zi = new ZiLi<T,A>(  flyMap.get(key) ,a);
		return zi;
	}
	//不能手动new        
	/** 包装类 */
	private class ZiLi<T,A>{    
		T t;//共享的
		A a;//变化的
		
		public ZiLi(T t, A a) {
			super();
			this.t = t;
			this.a = a;
		}
		public A getA() {
			return a;
		}
		public void setA(A a) {
			this.a = a;
		}
		public T getT() {
			return t;
		}
	}
}
