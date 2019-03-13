package cn.wlh.framework.ioc.factory;

import java.util.Map;

import cn.wlh.util.base.JavaUtilFactory;
import cn.wlh.util.base.exception.ExceptionUtil;
import cn.wlh.util.base.exception.ExceptionUtil1.ExceptionCache;
import cn.wlh.util.base.exception.ThrowableFacade;

/**
 * @author 吴灵辉
 */
public class VersionFactor extends EnumFactory{
	private Map<Class<?>,Class<?>> interfaceMapOfVersion = JavaUtilFactory.newMap(JavaUtilFactory.SELECT_OF_FIELD);
	private static final Map<String,VersionFactor> versionFactorMap = JavaUtilFactory.newMap(JavaUtilFactory.SELECT_OF_FIELD);
	static final ExceptionCache EXCEPTION = ThrowableFacade.STACK_TRACES_INFO.new ExceptionCache();
	EnumFactory enumFactory;
	public VersionFactor(String Id,Map<Class<?>, Class<?>> interfaceMapOfVersion) {
		this(Id, interfaceMapOfVersion, null);
	}
	/**
	 * @param interfaceMapOfVersion --key 用户的key   Value 是对应最高版本类。
	 * @throws ExceptionUtil 
	 */
	public VersionFactor(String Id,Map<Class<?>, Class<?>> interfaceMapOfVersion,EnumFactory enumFactory) {
		if( versionFactorMap.get(Id) != null) {
			EXCEPTION.interrupt("new VersionFactor failer please code");
		}
		this.enumFactory = enumFactory;
		this.interfaceMapOfVersion = interfaceMapOfVersion;
		versionFactorMap.put(Id, this);
	}  
	@Override
	public <T> T getInstance(Class<? extends T> key, Object[] pars) {
		if( enumFactory == null  ) {
			return (T) super.getInstance(getMaxVersionClass(key), pars);
		}
		return (T) enumFactory.getInstance(getMaxVersionClass(key), pars);
	}      
	public Class getMaxVersionClass(Class key){
		Class<?> class1 = interfaceMapOfVersion.get(key);
		return  class1 == null ? key : class1;
	}
	/**
	 * @return the interfaceMapOfVersion
	 */
	public Map<Class<?>, Class<?>> getInterfaceMapOfVersion() {
		return interfaceMapOfVersion;
	} 
	public static VersionFactor getVersionFactor(String id) {
		return versionFactorMap.get(id);
	}
}
