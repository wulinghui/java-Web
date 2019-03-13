package cn.wlh.util.extend.dao;

import java.util.Map;

import cn.wlh.util.extend.Confi;

/**
 * @author wlh
 * 统一dao入口,
 * 子类使用适配,使得用户更简单的操作
 */
public abstract class DaoProxy{
	DaoProxy(){}    
	private static final Map<String,SqlBcui> map ;
	static{
		//先加载类驱动.
		String [] drivers = Confi.config.drivers();
		for (int i = 0; i < drivers.length; i++) {
			try {
				Class.forName( drivers[i] );
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		//给map
		map = Confi.config.sqlBcuis();
	}
	//map的初始化  key=类名+别名   	value=SqlBcui对象
	public final  static <T extends SqlBcui> T get(Class<T> cla,String key){   
		return (T) map.get( key );
	}
}
