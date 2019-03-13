package cn.wlh.util.base;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import cn.wlh.util.base._File.FileFilter;
import cn.wlh.util.base._File.HandlerFile;
import cn.wlh.util.base._Method.MethodFilter;


  

public abstract class _Class {
	/**系统所有的Class*/
	private final static Map<String,Class<?> > classMap = new HashMap<String,Class<?> >();
	public static Class<?> [] obj2Class(Object...objects){
		int len = objects.length;
		Class<?> [] parameterTypes = new Class<?> [ len ];
		for (int i = 0; i < len ; i++) {
			parameterTypes[i] = objects[i].getClass();
		}
		return parameterTypes;
	}
	/**
	 * @return the classMap
	 */
	public static Map<String, Class<?>> getClassMap() {
		return classMap;
	}
	/** 获得类在父类上实现的泛型。自己在强转一下。 
	 * cn.wlh.util.base.ClassUtilTest.getClassArguments()
	 * @param cla 类。
	 * @return
	 */
	public static Type[] getClassArgumentsOfSuper(Class<?> cla) {
		Class<?> claSuper = cla;
		Type genericSuperclass = claSuper.getGenericSuperclass();
		ParameterizedType parameterizedType = null;
		while(  true ) {
			if(genericSuperclass instanceof ParameterizedType) {
				parameterizedType = (ParameterizedType) genericSuperclass;
				break;//
			}else {
				claSuper = claSuper.getSuperclass();
				genericSuperclass = claSuper.getGenericSuperclass();
			}
		}
		return parameterizedType.getActualTypeArguments();
	}
	/**获得类在实现接口上实现的泛型。自己在强转一下。
	 * cn.wlh.util.base.ClassUtilTest.getClassArguments()
	 * TODO 没有实现只是提供思路。
	 * @param cla
	 * @return
	 */
	public static Type[] getClassArgumentsOfInterface(Class<?> cla) {
		cla.getGenericInterfaces();//方法和上面的一样。
		return null;
	}
	
	public static <T> T invoker( Class<?> cla ,String name  ) throws ClassNotFoundException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, InstantiationException {
//		Class<?> cla = Class.forName( className );
		return (T) cla.getMethod(name).invoke( cla.newInstance() );
	}
	/** 循环反射指定的方法
	 * @param tag	--目标对象
	 * @param map00000 -- 放methods的空map
	 * @param fi   --  过滤指定的method
	 * @param declared -- true -- 自己的所有方法   	false -- 公开方法
	 * @param args  --反射的参数
	 * @return  -- key = method   Value = 值.
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	public static <T> Map<String, Object> forInvoker( Object tag , Map<String, Method> map00000, MethodFilter fi, boolean declared , Object... args  ) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		_Method.getMethods(map00000, declared, tag.getClass(), fi);
		Map<String,Object> map = new HashMap<String, Object>();
		for (Entry<String, Method> element : map00000.entrySet()) {
			try {
				map.put( element.getKey(), element.getValue().invoke(tag, args));
			}catch(Throwable e) {}
		}
		return map;
	}
	/**循环反射指定的公开方法*/
	public static <T> Map<String, Object> forInvoker( Object tag , MethodFilter fi,  Object... args  ) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Map<String,Method> map00000 = new HashMap<String, Method>();
		return forInvoker(tag, map00000, fi, false, args);
	}
	public static Class<?> forName(String className){
		Class<?> cla = classMap.get(className);
		if( cla == null ) {
			try {
				cla = Class.forName(className);
			} catch (ClassNotFoundException e) {
				throw new NullPointerException();
			}
			classMap.put(className, cla);
		}
		return cla;
	}
	/** 获得匿名|局部-内部类的名字*/
	public static String getInterName(Class<?> cla,String index) { return cla.getClass().getName() + "$" + index;}
	/** 通过反射new对象*/
	public  static <T> T newObj(Class<T> cla) {
		try {
			return cla.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			return null;
		}
	}
	/**把完全限定名转化成不同系统的路径分隔符 */
	public static String cla2Path(String classFullName){return _File.toSeparator(classFullName);}
	/**把不同系统的路径分隔符转化成完全限定名*/
	public static String path2Cla(String pathSeparater){return _File.toPoint(pathSeparater);}
	/** 所有父类 */
	public static Map<String,Class<?>> getAllSuper(Class<?> cla){
		if(cla == null) throw new NullPointerException();
		Map<String,Class<?>> map = new LinkedHashMap<String,Class<?>>();
		do{
			cla = cla.getSuperclass();
		 	if( cla == null ) return map;
		 	map.put(cla.getName(), cla);
		}while(true);
	}
	public static Class<?> lookClassBySimple(Collection<Class<?>> set,String simple){
		for (Class<?> class1 : set) {
			if( class1.getSimpleName().equals(simple) ) return class1;
		}
		return null;
	}
	/** 所有的接口*/
	public static Map<String,Class<?>> getAllInter(Class<?> cla){
		if( cla == null) throw new NullPointerException();
		//获得当前的类直接实现的接口
		Map<String,Class<?>> map = interfaces2Map(cla);
		//获得所有父类,遍历所有的父类
		for (Class<?> iterable_element : getAllSuper(cla).values() ) {
			//放入map中
			map.putAll( interfaces2Map(iterable_element) ) ;
		}
		//	获得所有接口的父类.并放入map中
		Map<String,Class<?>> map000 =   new LinkedHashMap<String,Class<?>>();
		map000.putAll(map);
		for (Class<?> iterable_element : map000.values()) {
			map.putAll( getAllSuper(iterable_element) ) ;
		}
		return map;
	}
	/** 获得所有的父类和所有的接口*/
	public static Map<String,Class<?>> getAllSupAndInter(Class<?> cla){
		Map<String,Class<?>> map = getAllSuper(cla);
		map.putAll( getAllInter(cla)  );
		return map;
	}
	/**当前的类直接实现的接口*/
	protected static Map<String,Class<?>> interfaces2Map(Class<?> cla){
		if( cla == null) throw new NullPointerException();
		Map<String,Class<?>> map = new LinkedHashMap<String,Class<?>>();
		for (Class<?> iterable_element : cla.getInterfaces()) {
			map.put( iterable_element.getName(), iterable_element);
		}
		return map;
	}
	/**DI依赖查找的基础,获得所有父类和接口.*/
	public static class DependencyLookUp{
		public Map< Class<?> , Set< Class<?> > > map = new HashMap< Class<?> , Set< Class<?> > >();
		public DependencyLookUp(Class<?> packa) throws ClassNotFoundException{
			PackageSupAndInter ps = new PackageSupAndInter(packa);
			/* 倒推 -- 
			 * 		把key 放入value 
			 * 		把value 放入key
			 */
			//遍历类
			for (Class<?> key : ps.map.keySet() ) {
				//遍历所有的父类和所有的接口
				for (Class<?> supInter : ps.map.get(key).values() ) {
					Set< Class<?> > set = map.get(supInter);//父类的存储
//					if(set ==null) set = new HashSet<Class<?>>() ;
//					set.add( key );//添加到set
//					map.put(supInter, set);
					if( set == null){
						 set = new HashSet<Class<?>>() ;
						 map.put(supInter, set);//少一次put的机会..
					}
					set.add( key );//添加到set
				}
			}
		}
		/**获得子类*/
		public Set< Class<?> > getSub(Class<?> key){return map.get(key);}
	}
	/** Key - 把包名下面所有的java文件转成类名
	 *  Value - Key所有的父类和所有的接口
	 * */
	public static class PackageSupAndInter{
		public final Map<Class<?> , Map<String,Class<?>> > map = new HashMap<Class<?>, Map<String,Class<?>>>();
		public PackageSupAndInter(Class<?> packa) throws ClassNotFoundException{
			Map<String,Class<?>> clas = new PackageAllClass(packa).ALL_CLASS;
			// 获得所有类的父类和接口
			for (Class<?> iterable_element : clas.values()) {
				map.put( iterable_element,
				getAllSupAndInter(iterable_element));
			}
		}  
	}
	/** 把包名下面所有的java文件转成类名	*/
	public static class PackageAllClass{
		public Map<String,Class<?>> ALL_CLASS = new HashMap<String,Class<?>>();
		public PackageAllClass(Class<?> cla) throws ClassNotFoundException  {
			ToClass a = new ToClass(cla);
			// TODO sysout
			_File.forFile(new File( _File.package2Path( cla ) ), FileFilter.JAVA , a );
			// 转化
			ALL_CLASS.putAll( a.ALL_CLASS );
		}
	}
	/** 把文件的路径名2类名*/
	protected static class ToClass implements HandlerFile<ClassNotFoundException>{
		public  Map<String,Class<?>> ALL_CLASS = new HashMap<String,Class<?>>();
		Class<?> cla;
		public ToClass(Class<?> cla) {
			this.cla = cla;
		}
		public void handler(File file) throws ClassNotFoundException{
			String className = _File.absolutePath2ClassName( file.getAbsolutePath() , this.cla);
			//存储
//			System.out.println("ToClass+"+className);  
			ALL_CLASS.put(className, Class.forName(className) );
		}
	}
	
	
	
	
	
	
	
}