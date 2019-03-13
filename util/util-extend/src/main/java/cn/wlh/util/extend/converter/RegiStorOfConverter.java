package cn.wlh.util.extend.converter;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public  abstract class RegiStorOfConverter<Src,Tar> {
	public final Class<Src> srcClass;
	public final Class<Tar> tarClass;
	public RegiStorOfConverter() {
		//获得父类RegiStorOfConverter<Src,Tar>..
		ParameterizedType superclass = (ParameterizedType) this.getClass().getGenericSuperclass();
		//当前类的泛型实例.. 
		Type[] actualTypeArguments = superclass.getActualTypeArguments();
		srcClass = (Class<Src>) actualTypeArguments[0];
		tarClass = (Class<Tar>) actualTypeArguments[1];
    //			put
		AbstarctConverter.registorMap.put(srcClass, tarClass , this);
	}
	public abstract Tar toConverter(Src src);
	
	public Tar toConverter(Src obj, Object... objects) {
		return toConverter(obj);
	}
}