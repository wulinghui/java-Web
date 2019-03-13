package cn.wlh.util.extend.wrap;

import java.util.Map;

public class ObjectMap1<K, V> extends ObjectMap<K, V> {

	public ObjectMap1(int length) {
		super(length);
	}

	@Override
	public void putAll(Map<? extends K, ? extends V> map) {
//		Set<?> set = map.entrySet();
//		Iterator<Map.Entry<? extends K, ? extends V>> iterator = (Iterator<Entry<? extends K, ? extends V>>) set.iterator();
//		
		int i = 1;
		int leng = super.key.length;
		for (Map.Entry<? extends K, ? extends V> e : map.entrySet()) {
			if( leng > i + this.size() ){
				put(e.getKey(), e.getValue());
				i++;
			}else break;
		}
	}
}
