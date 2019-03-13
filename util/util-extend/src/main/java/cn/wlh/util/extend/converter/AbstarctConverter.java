package cn.wlh.util.extend.converter;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import cn.wlh.util.extend.wrap.K1_k2_V_MapOfCache;

/**
 * @author 吴灵辉
 * 提供不同类转化不同类的方法。
 * 我们这里只提供转化,不做多余的校验判断...
 */
public abstract class AbstarctConverter {
	private AbstarctConverter(){};
	static K1_k2_V_MapOfCache<Class<?> , Class<?> , RegiStorOfConverter> registorMap = new K1_k2_V_MapOfCache();
	
	public static <T> T executConverter(Object obj , Class<T> tarClass) {
		RegiStorOfConverter regiStorOfConverter = getConverterBean(obj, tarClass);
		return (T) regiStorOfConverter.toConverter(obj);
	}
	public static <T> T executConverter(Object obj , Class<T> tarClass , Object ...paramss ) {
		RegiStorOfConverter regiStorOfConverter = getConverterBean(obj, tarClass);
		return (T) regiStorOfConverter.toConverter(obj , paramss);
	}
	/*//示例
	public static  class RegiStorOfConverterEx extends RegiStorOfConverter<String,Integer>{
		 static {
			 //注册一次
			 new RegiStorOfConverterEx();
		 }
		@Override
		public Integer toConverter(String src) {
			return Integer.parseInt(src);
		}
	 }
	public static void main(String[] args) throws ClassNotFoundException {
		System.out.println(111);
		Class.forName("cn.wlh.util.extend.converter.StringToInt");
		
		Integer executConverter = AbstarctConverter.executConverter("12341", Integer.class);
		System.out.println( executConverter +1 );
		//这样的类不可以
		Class.forName("cn.wlh.util.extend.converter.AbstarctConverter.RegiStorOfConverterEx");
	}*/

	private static <T> RegiStorOfConverter getConverterBean(Object obj, Class<T> tarClass) {
		if( obj == null ) return null;
		Class<? extends Object> srcClass1 = obj.getClass();
		RegiStorOfConverter regiStorOfConverter = registorMap.get(srcClass1,tarClass );
		return regiStorOfConverter;
	}
}
