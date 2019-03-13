package cn.wlh.util.extend;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import cn.wlh.util.base._Method;
import cn.wlh.util.base._Method.MethodFilter;

/**
 * @author wlh
 * 获得一个包下所有的方法..包括子包
 * 以  简单的类名  和 方法名  存储
 */
public abstract class MethodOfPackage {
	MethodOfPackage(){}
	String packag;
	protected Map<Class<?>,Map<String,Method>> methods = new HashMap<Class<?>,Map<String,Method>>();
	/**
	 * @param packag --包名
	 * @param declared   true -- 自己的所有方法   	false -- 公开方法
	 */
	public MethodOfPackage(Class<?> packag,boolean declared , MethodFilter filter) throws ClassNotFoundException {
		_Method.getMethodsOfPackage(methods,packag,declared,filter);
		//TODO sysout
//		System.out.println( "MethodOfPackage="+methods);  
	}
	/** --- 为了满足不同的需要,写成abstract
	 * @param simple --简单的类名
	 * @param name   --方法名
	 * @param args null-执行无参的方法.
	 * @return 
	 */
	public abstract Method getMethod(String simple,String name);

	public Object invoke(Object obj,String simple,String name,Object... args ) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		if( args == null )
			return getMethod(simple, name).invoke(obj);
		return getMethod(simple, name).invoke(obj, args);
	}
	
	
	/** 通过简单的类名获得类 */
	public static class Simple extends MethodOfPackage{
		/** 通过简单的类名获得类 */
		public Simple(Class<?> packag, boolean declared, MethodFilter filter) throws ClassNotFoundException {
			super(packag, declared, filter);
		}
		@Override
		public Method getMethod(String simple, String name) {
			for (Entry<Class<?>, Map<String, Method>> iterable_element : methods.entrySet() ) {
				if ( iterable_element.getKey().getSimpleName().equals(simple) ) 
					return iterable_element.getValue().get(name);
			} 
			return null;
		}
		public Map<String,Method> getMethodMap(String simple){
			for (Entry<Class<?>, Map<String, Method>> iterable_element : methods.entrySet() ) {
				if ( iterable_element.getKey().getSimpleName().equals(simple) ) 
					return iterable_element.getValue();
			}
			return null;
		}
		public Entry<Class<?>, Map<String, Method>> getEntry(String simple){
			for (Entry<Class<?>, Map<String, Method>> iterable_element : methods.entrySet() ) {
				if ( iterable_element.getKey().getSimpleName().equals(simple) ) 
					return iterable_element;
			}
			return null;
		}
	}
	/** 通过完全限定名获得类 */
	public static class FullName extends MethodOfPackage{
		/** 通过完全限定名获得类 */
		public FullName(Class<?> packag, boolean declared, MethodFilter filter) throws ClassNotFoundException {
			super(packag, declared, filter);
		}
		public Method getMethod(String fullClassName, String name) {
//			System.out.println("FullName=="+fullClassName);
			try {   
//				System.out.println( "FullName=="+Class.forName(fullClassName));
				return methods.get( Class.forName(fullClassName)  ).get(name);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();  
				return null;
			}
		} 
		public Map<String,Method> getMethodMap(Class<?> clas){
			return super.methods.get(clas);
		}
	}
}
