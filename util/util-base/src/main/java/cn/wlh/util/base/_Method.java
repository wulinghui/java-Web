package cn.wlh.util.base;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import cn.wlh.util.base._Class.PackageAllClass;
import cn.wlh.util.base.interfaces._Filter;


public abstract class _Method {
	/**
	 * @param tar
	 * @param name
	 * @param pars
	 * @param declared true -- 自己的所有方法(不包括父类.)    	false -- 自己的所有的公开方法(包括父类.)
	 * @return
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 */
	public static Method getMethod(Class<?> tar , String name ,Class<?>[] pars,boolean declared) throws SecurityException, NoSuchMethodException{
		Method e = declared ? tar.getDeclaredMethod(name,pars) : tar.getMethod(name,pars);
		//取消权限校验
		e.setAccessible(true);
		return e;
	}
//	public static Method getMethod(Class<?> tar , String name ,boolean declared) {
//  这里是想以name 获得 同一name的所有方法下的第一个...但是这里不支持..	
//		return declared ? tar.getme  
//	}
	/** @see getMethod 的获得方法就知道--用户需要指定标识
	 * @return 父类的公开方法或者自己的私有方法 -----把所有的可能的方法(父类和私有.)
	 * @throws NoSuchMethodException 
	 * @throws SecurityException 
	 */
	public static Method getAllMethod(Class<?> tar , String name ,Class<?>[] pars) throws SecurityException, NoSuchMethodException{
		Method method = getMethod(tar, name, pars, true);
		return method == null ? getMethod(tar, name, pars, false) : method;
	}
	/**
	 * 把method放到map里面
	 * 并取消权限校验
	 * @param map
	 * @param declared true -- 自己的所有方法(不包括父类.)   	false -- 自己的所有的公开方法(包括父类.)
	 * @param tar
	 * @param fi
	 */
	public static  void getMethods(Map map, boolean declared ,Class<?> tar,_Filter<Method> fi){
		//获得method
		Method[] mes = declared ? tar.getDeclaredMethods() : tar.getMethods();
		//取消权限校验
		Method.setAccessible(mes, true);
		//遍历method
		for (Method method : mes) {
			//获得Alias,Alias优先
			if( fi.accept(method)){
				Alias alias = method.getAnnotation(Alias.class);
				if( alias == null){
					map.put(method.getName(),  method);
				}else{
					map.put(alias.value(),  method );
				}
			}
		}
	}
	/**把所有的method(父类和私有.)放到map里面*/
	public static void getAllMethods(Map<String,Method> map,Class<?> tar,MethodFilter fi){ getMethods(map, false, tar, fi);getMethods(map, true, tar, fi); }
	/*** 获得一个包下所有的方法..
	 *  * @param declared true -- 自己的所有方法(不包括父类.)   	false -- 自己的所有的公开方法(包括父类.)
	 * @throws ClassNotFoundException */
	public static void getMethodsOfPackage(Map<Class<?>,Map<String,Method>> map,Class<?> packa,boolean declared ,_Filter<Method> fi) throws ClassNotFoundException{
		PackageAllClass pac = new PackageAllClass(packa);
		for (Class<?> tar : pac.ALL_CLASS.values()) {
			//map00000--存储一个类的方法
			Map<String,Method> map00000 = new HashMap<String, Method>();
			getMethods(map00000, declared, tar, fi);
			//放入map中..
			map.put(  tar , map00000);
		}
	}
	/**把所有的method(父类和私有.)放到map里面*/
	public static Map<String,Method> getFullMethods(Class<?> tar){
		Map<String,Method> map = new HashMap<String, Method>();
		getAllMethods(map, tar, (MethodFilter) MethodFilter.NO_FILTER);
		return map;
	}
	
	public static interface MethodFilter extends _Filter<Method>{
		/** 过滤Object 的方法  	*/
		MethodFilter OBJECT = new MethodFilter(){
			public boolean accept(Method t) {
				String name = t.getName();
				if( 	"toString".equals(name) ||  "getClass".equals(name) ||  
						"equals".equals(name) ||  "hashCode".equals(name) ||   
						"notify".equals(name) ||  "notifyAll".equals(name) ||  
						"wait".equals(name)   ) return false;
				return true;
			}
		};
		/** 获得Biz的方法  以txn开头*/
		MethodFilter BIZ = new MethodFilter(){
			@Override
			public boolean accept(Method t) {
				String name = t.getName();
				return name!=null && name.startsWith("txn");
			}
			
		};
		/** 获得Biz的方法  以get开头*/
		MethodFilter GET = new MethodFilter(){
			@Override
			public boolean accept(Method t) {
				String name = t.getName();
				return name!=null && name.startsWith("get");
			}
		};
	}
	
	/**
	 * @author wlh 
	 * 别名
	 */
	@Target({ElementType.TYPE,ElementType.ANNOTATION_TYPE,ElementType.CONSTRUCTOR,
		ElementType.FIELD,ElementType.LOCAL_VARIABLE,ElementType.METHOD,ElementType.PACKAGE,
		ElementType.PARAMETER})
	@Retention(RetentionPolicy.RUNTIME)
	public static @interface Alias{String value();}
}
