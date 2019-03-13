package cn.wlh.util.extend.wrap;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

import cn.wlh.util.base.JavaUtilFactory;
import cn.wlh.util.base._Map;

/**
 * @author 吴灵辉
 * 
 * @param <K1>
 * @param <K2>
 * @param <V>
 */
public abstract class K1_k2_V_Map<K1,K2,V> implements Map<K1,Map<K2,V>>{
	Map<K1,Map<K2,V>> map;
	/**
	 * @param flag  cn.wlh.util.base.JavaUtilFactory
	 */
	public K1_k2_V_Map(int flag) {
		map = JavaUtilFactory.newMap(flag);
	}

	protected abstract Map<K2,V>  newSubMap (); 
	
	public K1_k2_V_Map<K1,K2,V> put(K1 k1,K2 k2,V value){
		 getOrNewSub(k1).put(k2, value);return this;
	 }
	 public K1_k2_V_Map<K1,K2,V> put(K1 k,K2[] k1,V[] v){
			_Map.array2Map( getOrNewSub(k) , k1, v) ;
			return this;
		}
		public K1_k2_V_Map<K1,K2,V> put(K1 k,Object[] kAndv){
			_Map.array2Map( getOrNewSub(k) ,  kAndv , 2) ;
			return this;
		}
	 /**从map里面获得SubMap
		 * @param k 
		 * @return Map<K2,V> 
		 */
		public Map<K2,V> getOrNewSub(K1 k1){
			Map<K2, V> map2 = get(k1);
			if( map2 == null ) {
				map2 = newSubMap();
				put(k1, map2);
			}
			return map2;
		}
	public V get( K1 k1 , K2 k2) {
		Map<K2, V> map2 = get(k1);
		if( map2 == null) return null;
		return map2.get(k2);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public int size() {
		return map.size();
	}

	public boolean isEmpty() {
		return map.isEmpty();
	}

	public boolean containsKey(Object key) {
		return map.containsKey(key);
	}

	public boolean containsValue(Object value) {
		return map.containsValue(value);
	}

	public Map<K2, V> get(Object key) {
		return map.get(key);
	}

	public Map<K2, V> put(K1 key, Map<K2, V> value) {
		return map.put(key, value);
	}

	public Map<K2, V> remove(Object key) {
		return map.remove(key);
	}

	public void putAll(Map<? extends K1, ? extends Map<K2, V>> m) {
		map.putAll(m);
	}

	public void clear() {
		map.clear();
	}

	public Set<K1> keySet() {
		return map.keySet();
	}

	public Collection<Map<K2, V>> values() {
		return map.values();
	}

	public Set<Entry<K1, Map<K2, V>>> entrySet() {
		return map.entrySet();
	}

	public boolean equals(Object o) {
		return map.equals(o);
	}

	public int hashCode() {
		return map.hashCode();
	}
  
	

	public  void forEach(BiConsumer<? super K1, ? super Map<K2, V>> action) {
		map.forEach(action);
	}

	public  void replaceAll(BiFunction<? super K1, ? super Map<K2, V>, ? extends Map<K2, V>> function) {
		map.replaceAll(function);
	}

	public  Map<K2, V> putIfAbsent(K1 key, Map<K2, V> value) {
		return map.putIfAbsent(key, value);
	}

	public  boolean remove(Object key, Object value) {
		return map.remove(key, value);
	}

	public  boolean replace(K1 key, Map<K2, V> oldValue, Map<K2, V> newValue) {
		return map.replace(key, oldValue, newValue);
	}

	public  Map<K2, V> replace(K1 key, Map<K2, V> value) {
		return map.replace(key, value);
	}

	public  Map<K2, V> computeIfAbsent(K1 key, Function<? super K1, ? extends Map<K2, V>> mappingFunction) {
		return map.computeIfAbsent(key, mappingFunction);
	}

	public  Map<K2, V> computeIfPresent(K1 key,
			BiFunction<? super K1, ? super Map<K2, V>, ? extends Map<K2, V>> remappingFunction) {
		return map.computeIfPresent(key, remappingFunction);
	}

	public  Map<K2, V> compute(K1 key,
			BiFunction<? super K1, ? super Map<K2, V>, ? extends Map<K2, V>> remappingFunction) {
		return map.compute(key, remappingFunction);
	}

	public  Map<K2, V> merge(K1 key, Map<K2, V> value,
			BiFunction<? super Map<K2, V>, ? super Map<K2, V>, ? extends Map<K2, V>> remappingFunction) {
		return map.merge(key, value, remappingFunction);
	}
	
}
