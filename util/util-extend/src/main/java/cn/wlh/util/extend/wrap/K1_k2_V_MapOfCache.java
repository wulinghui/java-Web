package cn.wlh.util.extend.wrap;

import java.util.Map;

import cn.wlh.util.base.JavaUtilFactory;

public class K1_k2_V_MapOfCache<K1, K2, V> extends K1_k2_V_Map<K1, K2, V> {
	int flag;
	Map<K2,V> cache;
	public K1_k2_V_MapOfCache() {
		this(JavaUtilFactory.SELECT_OF_FIELD);
	}
	public K1_k2_V_MapOfCache(int flag) {
		super(flag);
		this.flag = flag;
	}
	//提高一点点效率,
//	@Override
//	public Map<K2, V> get(Object key) {
//		cache = super.get(key);
//		return cache;
//	}
	
	@Override 
	public Map<K2, V> getOrNewSub(K1 key) {
		cache = super.getOrNewSub(key);
		return cache;
	}

	@Override
	protected Map<K2, V> newSubMap() {
		return JavaUtilFactory.newMap(flag);
	}

}
