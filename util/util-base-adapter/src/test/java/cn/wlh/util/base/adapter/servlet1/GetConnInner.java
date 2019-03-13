package cn.wlh.util.base.adapter.servlet1;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.Map.Entry;

import org.junit.Ignore;
import org.junit.Test;

public class GetConnInner {  
//	@Test
//	public void getUserNameAndPassWord() throws Exception {
//		Class.forName("oracle.jdbc.driver.OracleDriver");
//		Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@10.50.160.2:1522:DZSPSCP" , "data_user", "gwssi123");
////		OracleConnection  
//		// oracle.jdbc.driver.T4CConnection
//		System.out.println( "=conn===" + conn.getClass() );
//		Field[] fields = getFields(conn.getClass());
//		Field.setAccessible(fields, true);
//		for (Field field : fields) {
//			System.out.println( field.getName() + "====" + field.get(conn) );
//		}
//		conn.createStatement();
//	}
	
//	@Test
//	public void getUserNameAndPassWord1() throws Exception {
//		Class.forName("oracle.jdbc.driver.OracleDriver");
//		Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@10.50.160.2:1522:DZSPSCP" , "data_user", "gwssi123");
//		
////		Field declaredField = DriverManager.class.getDeclaredField("registeredDrivers");
////		declaredField.setAccessible(true);
////		Object object = declaredField.get(null);
////		System.out.println( "=object===" + object );
//		
//		Map<String,Object> map = new HashMap<String,Object>();
//		getAllValueByRefalced1(map, conn);
//		System.out.println( "=conn===" + map );
//		for (Entry<String, Object> class1 : map.entrySet()) {
//			System.out.println( class1.getKey() + "====" + class1.getValue() );
//			/*
//			 * oracle.jdbc.driver.T4CConnectionpassword====gwssi123
//			 */
//		}
//	}
	
	@Test@Ignore
	public void getUserNameAndPassWord3()  {
		try {
			getUserNameAndPassWord2();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Ignore
	public void getUserNameAndPassWord2() throws Exception {  
		Class.forName("oracle.jdbc.driver.OracleDriver");  
		Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@10.50.160.2:1522:DZSPSCP" , "data_user", "gwssi123");
		
//		Field declaredField = DriverManager.class.getDeclaredField("registeredDrivers");
//		declaredField.setAccessible(true);
//		Object object = declaredField.get(null);
//		System.out.println( "=object===" + object );
		
		Map<String,Object> map = new HashMap<String,Object>();
		getAllValueByRefalced2(map, conn);
		System.out.println( "=conn===" + map );
		for (Entry<String, Object> class1 : map.entrySet()) {
			System.out.println( class1.getKey() + "====" + class1.getValue() );
			/*
			 * oracle.jdbc.driver.T4CConnectionpassword====gwssi123
			 */
		}
	}
	
	
	
//	@Test
//	public void getAllValueByRefalced() throws Exception {
//		Map<String,Object> map = new HashMap<String,Object>();
//		getAllValueByRefalced( map, "我的TEST", TEST);
//		System.out.println( "----------------------" );
//		System.out.println( map );
//	}
	/**递归的获得一个对象所有的属性值
	 * @param obj 
	 * @throws NoSuchFieldException 
	 * @throws SecurityException 
	 */
	protected Class<?>[] nodigui = new Class<?>[] {
		 Byte.class, Short.class,Integer.class , Long.class
		,Boolean.class ,Character.class , Float.class , Double.class
		, String.class
	};
	//不同递归,--获得聚合类的值..
	public void getAllValueByRefalced(Map<String,Object> map ,String FieldKey ,Object FieldVale) throws Exception {
		if( FieldVale == null ) return;
		Class<?> cla = FieldVale.getClass();
		//判断是否递归
		if( lookUp( cla , nodigui) ) {
			map.put(FieldKey, FieldVale);
		}else {
//			Map<String, Field> allFields = _Field.getAllFields(cla);
//			for (Entry<String, Field> element : allFields.entrySet()) {
//				Field value = element.getValue();
//				Object object = .get(obj);
//			}
			Field[] fields = getFields(cla);
			Field.setAccessible(fields, true);
			for (Field field : fields) {
				String key = getKey(field, FieldVale);
				Object vale = field.get(FieldVale);
				getAllValueByRefalced(map, key, vale);
			}
		}
		System.out.println( map.size()  ); 
	}
	
	/** 只是简单的获得该类和父类所有的属性值，但是不获得实例变量(类)的值
	 * @param map
	 * @param FieldVale
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	public void getAllValueByRefalced1(Map<String,Object> map, Object tar) throws Exception {
		Class<?> cla = tar.getClass();
		if( cla == null ) return;
		do { //_Field
			for (Field field : cla.getFields()) {
				putMap(map, tar, cla, field);
			}
			for (Field field : cla.getDeclaredFields()) {
				putMap(map, tar, cla, field);
			}
			cla = cla.getSuperclass() ;
		}while( cla == null || !cla.equals(Object.class) );
	}
	/**真的恶心..包装一层层又一层...
	 * 这里以cn.wlh.util.base.adapter.servlet1.DaoConfigTest.getAllValueByRefalced1(Map<String, Object>, Object)
	 * 为一维..之后判断里面的值是不是需要过滤的内容.
	 * 不是就进去以Map获得里面的内容放入第一维..这样形成2叉树...存放
	 * @param map
	 * @param tar
	 * @throws Exception
	 */
	Set<Class<?>> set = new HashSet<>();
	public void getAllValueByRefalced2(Map<String,Object> map, Object tar) throws Exception {
		//一维
		getAllValueByRefalced1(map, tar);
		//二维三维...多维...
		for (Entry<String, Object> ele : map.entrySet()) {
			Object value = ele.getValue();
			//校验
			if( value == null ) continue;
			String key = ele.getKey();
			// 可能在类与类之间互相引用-死循环....可以了..
//			if( lookUp(value.getClass(), nodigui) ) {
			Class<? extends Object> tarClass = value.getClass();
			if( lookUp(tarClass, set) ) {
				//过滤掉的,不理他.
			} else {
				//把tarClass放入过滤里面,避免死循环
				this.set.add(tarClass);
				
				Map<String,Object> map222 = new HashMap<>();//第二个Map
				//递归
				getAllValueByRefalced2(map222, value);
				//代替key..
				map.put(key, map222);
				
			}
		}
	}
	private void putMap(Map<String, Object> map, Object tar, Class<?> cla, Field field) throws IllegalAccessException {
		String key;
		key = cla.getName() +"->" + field.getName();
		field.setAccessible(true);
		map.put(key, field.get(tar) );
	}
	
	
	
	protected Field[] getFields(Class<?> cla) {
		return cla.getDeclaredFields();
	}
	protected String getKey(Field field , Object obj) {
		return obj.getClass().getName() + "-" + field.getName();
	}
	/** 通过类名是否包含某些类名 ,可以变化的..
	 * @param tar
	 * @param strs
	 * @return
	 */
	protected boolean lookUp(Class<?> tar ,Collection<Class<?>> classs ) {
		if( tar == null ) return false;
		if( lookUp(tar, this.nodigui ) ) {
			return true;
		}
		Iterator<Class<?>> iterator = classs.iterator();
		while( iterator.hasNext()) {
			Class<?> next = iterator.next();
			if( tar.equals(next) ) return true;
		}
		return false;
	}
	/** 通过类名是否包含某些类名
	 * @param tar
	 * @param strs
	 * @return
	 */
	protected boolean lookUp(Class<?> tar ,Class<?>[] classs ) {
		Objects.requireNonNull(tar);
//		for (Class<?> class1 : classs) {
//			if( tar.equals(class1) ) return true;
//		}
		String name = tar.getName();
		if( name.startsWith("java") || lookUp(name , strs)  )  return true;
		return false;
	}
	String [] strs = {
			"java.lang" , "java.util" 
	};
	/** 通过类名是否包含某些内容....
	 * @param tar
	 * @param strs
	 * @return
	 */
	protected boolean lookUp(String tar , String[] strs) {
		tar = String.valueOf(tar);
		for (String string : strs) {
			if( tar.contains(string) ) return true;
		}
		return false;
	}
}
