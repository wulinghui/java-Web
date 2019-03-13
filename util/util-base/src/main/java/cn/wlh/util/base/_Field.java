package cn.wlh.util.base;

import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.Map;

import cn.wlh.util.base._Method.Alias;
import cn.wlh.util.base.interfaces._Filter;

public class _Field {
	/** 只是简单的获得该类和父类所有的属性值，但是不获得实例变量(类)的值
	 * @param map
	 * @param FieldVale
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	public static void getAllValueByRefalced1(Map<String,Object> map, Object tar) throws Exception {
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
	private static void putMap(Map<String, Object> map, Object tar, Class<?> cla, Field field) throws IllegalAccessException {
		String key = cla.getName() +"->" + field.getName();
		field.setAccessible(true);
		map.put(key, field.get(tar) );
	}
	
	public static Field getField(Class<?> tar,boolean declared ,String name ) throws SecurityException, NoSuchFieldException{
		Field f = declared ? tar.getDeclaredField(name) : tar.getField(name);
		f.setAccessible(true);
		return f;
	}
	public static Field getAllField(Class<?> tar ,String name ) throws SecurityException, NoSuchFieldException{
		Field fie = getField(tar, true, name);
		return fie == null ? getField(tar,  false ,name) : fie;
	}
	/** declared true-- 私有*/
	public static Field[] getFields(Class<?> tar,boolean declared  ) throws SecurityException, NoSuchFieldException{
		return declared ? tar.getDeclaredFields() : tar.getFields();
	}
	public static void getFields(Map<String,Field> map,boolean declared ,Class<?> tar,_Filter<Field> fi ) throws SecurityException, NoSuchFieldException{
		//获得属性
		Field[] f = getFields(tar, declared);
		//取消校验
		Field.setAccessible(f, true);
		//遍历f
		for (Field field : f) {
			if(fi.accept(field)){
			//获得Alias,Alias优先
				Alias alias = field.getAnnotation(Alias.class);
				if( alias == null){
					map.put(field.getName(), field);
				}else{
					map.put(alias.value(), field );
				}
			}
		}
	}
	/**获得静态属性值并放入map中*/
	public static <V> void getObjectFieldValues(Map<String,V> map,boolean declared ,Object obj,_Filter<Field> fi ) throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException{
		//获得属性
		Class<?> tar = obj.getClass();
				Field[] f = getFields(tar, declared);
				//取消校验
				Field.setAccessible(f, true);
				//遍历f
				for (Field field : f) {
					if(fi.accept(field)){
						//获得Alias,Alias优先
							Alias alias = field.getAnnotation(Alias.class);
							if( alias == null){
								map.put(field.getName(), (V) field.get(obj) );
							}else{
								map.put(alias.value(), (V) field.get(obj) );
							}
						}
				}
	}
	
		
	/**获得静态属性值并放入map中*/
	public static <V> void getStaticFieldValues(Map<String,V> map,boolean declared ,Class<?> tar,_Filter<Field> fi ) throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException{
		//获得属性
		Field[] f = getFields(tar, declared);
		//取消校验
		Field.setAccessible(f, true);
		//遍历f
		for (Field field : f) {
			if(fi.accept(field)){
				//获得Alias,Alias优先
					Alias alias = field.getAnnotation(Alias.class);
					if( alias == null){
						map.put(field.getName(),(V) field.get(null) );
					}else{
						map.put(alias.value(),(V) field.get(null) );
					}
				}
		}
	}
	public static void getAllFields(Map<String,Field> map,Class<?> tar,_Filter<Field> fi) throws SecurityException, NoSuchFieldException{ getFields(map, false, tar, fi);getFields(map, true, tar, fi); }
	public static Map<String,Field> getAllFields(Class<?> tar) throws SecurityException, NoSuchFieldException{
		Map<String,Field> map = new LinkedHashMap<String, Field>();
		getAllFields(map,tar,_Filter.NO_FILTER);
		return map;
	}
	/** 通过反射获得值
	 * @param obj 对象
	 * @param fieldName 值的名称
	 * @return
	 */
	public static <T> T getValueByField(Object obj , String fieldName ) {
		Field field;
		try {
			field = getAllField(obj.getClass(), fieldName);
			field.setAccessible(true);   
			return (T) field.get(obj);
		}catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}
}
