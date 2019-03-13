package cn.wlh.util.extend.wrap;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wlh
 * @param <V>	--用户自己规定..
 */
public  class SSV<V> extends V_Map<String,String,V>{
	
	@Override
	protected Map<String, V> newSubMap() {
		return new HashMap<String,V>();
	}
	@Override
	protected Map<String, Map<String, V>> newObject() {
		return new HashMap<String, Map<String,V>>();
	}
	public  void putAll(Map<String, Map<String,V> >m) {getSource().putAll(m);}
}
