package cn.wlh.util.extend;

import java.util.HashMap;
import java.util.Map;


/**
 * @author wlh
 * spring有	就不写了,等需要的时候再写...
 * single---Spring ...我们只是用为了foreach一个所有的子类.的DL封装一次...@see _Class.DependencyLookUp.getSub
 */
public abstract class IOC {
	static Map<String,Object> map = new HashMap<String, Object>();
//	public static void put(Class<?> cla){
//		try {
//			String key = cla.getName();
//			
//			map.put(, _Class.newObj(  cla ));
//		}  catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
}
