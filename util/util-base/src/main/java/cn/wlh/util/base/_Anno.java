package cn.wlh.util.base;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.Map;

public abstract class _Anno {
	/**遍历anno所有的值
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException */
	public static  Map<String,Object> forAnno(Annotation  anno) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException{
		//由于,循环方法就是有序的,但是不是按照定义的顺序,目前不知道他的顺序定义,所以默认是无序的.
		Map<String,Object> map = new LinkedHashMap<String,Object>();
		for (Method method : anno.getClass().getDeclaredMethods()) {
			String meName = method.getName();
			if( !"equals".equals(meName) &&  !"toString".equals(meName) && 
					!"hashCode".equals(meName) && !"annotationType".equals(meName) ){
				Object a = null;
				a = method.invoke(anno);
				map.put(meName, a);
			}
		}
		return map;
	}
	public static Map<String,Annotation> annos2Map(Annotation [] annos){
		Map<String,Annotation> map = new LinkedHashMap<String, Annotation>();
		for (Annotation anno : annos ) {
			map.put(anno.getClass().getSimpleName(), anno);
		}
		return map;
	}
}
