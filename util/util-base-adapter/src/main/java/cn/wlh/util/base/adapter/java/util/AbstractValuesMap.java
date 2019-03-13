package cn.wlh.util.base.adapter.java.util;

import java.util.Map;

public class AbstractValuesMap<K, V> extends AdapterMap<K, V> {

	public AbstractValuesMap(Map<K, V> source) {
		super(source);
	}
	
	/*
	 * @see 
	cn.wlh.util.base.adapter.java.util.CacheValuesMap.putOneOfAutoAdd(K, V)
	这个方法不好提。接口也不好建。。只能3个集合用到一个的时候，一个开始建
	 */

}
