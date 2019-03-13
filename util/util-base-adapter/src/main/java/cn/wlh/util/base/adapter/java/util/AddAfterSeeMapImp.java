package cn.wlh.util.base.adapter.java.util;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

public class AddAfterSeeMapImp<K, V> implements AddAfterSeeMapInterface<K, V>{
	Map<K ,V > map;
	boolean isSave;
	Object keys [];
	Object values [];
	public AddAfterSeeMapImp(Map<K, V> map) {
		super();
		this.map = map;
	}
	public AddAfterSeeMapImp() {
		this(new LinkedHashMap<>());
	}
	
	public int size() {
		if( this.isSave ) return keys.length;
		return map.size();
	}

	public boolean isEmpty() {
		return size() == 0 ? true : false;
	}

	public boolean containsKey(Object key) {
		return map.containsKey(key);
	}

	public boolean containsValue(Object value) {
		return map.containsValue(value);
	}

	public V get(Object key) {
		if( this.isSave ) {
			int indexOfKey = this.indexOfKey( (K) key);
			if(indexOfKey == -1 ) return null;
			return (V) values[ indexOfKey ] ;
		}
		return map.get(key);
	}

	public V put(K key, V value) {
		return map.put(key, value);
	}

	public V remove(Object key) {
		return map.remove(key);
	}

	public void putAll(Map<? extends K, ? extends V> m) {
		map.putAll(m);
	}

	public void clear() {
		map.clear();
	}

	public Set<K> keySet() {
		return map.keySet();
	}

	public Collection<V> values() {
		return map.values();
	}

	public Set<Entry<K, V>> entrySet() {
		return map.entrySet();
	}

	public boolean equals(Object o) {
		return map.equals(o);
	}

	public int hashCode() {
		return map.hashCode();
	}

	public  V getOr(Object key, V Value) {
		return map.getOrDefault(key, Value);
//		return map.getOr(key, Value);
	}

	public  void forEach(BiConsumer<? super K, ? super V> action) {
		map.forEach(action);
	}

	public  void replaceAll(BiFunction<? super K, ? super V, ? extends V> function) {
		map.replaceAll(function);
	}

	public  V putIfAbsent(K key, V value) {
		return map.putIfAbsent(key, value);
	}

	public  boolean remove(Object key, Object value) {
		return map.remove(key, value);
	}

	public  boolean replace(K key, V oldValue, V newValue) {
		return map.replace(key, oldValue, newValue);
	}

	public  V replace(K key, V value) {
		return map.replace(key, value);
	}

	public  V computeIfAbsent(K key, Function<? super K, ? extends V> mappingFunction) {
		return map.computeIfAbsent(key, mappingFunction);
	}

	public  V computeIfPresent(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
		return map.computeIfPresent(key, remappingFunction);
	}

	public  V compute(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
		return map.compute(key, remappingFunction);
	}

	public  V merge(K key, V value, BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
		return map.merge(key, value, remappingFunction);
	}
	@Override
	public boolean isSave() {
		return this.isSave;
	}
	@Override
	public void save() {
		keys = this.map.keySet().toArray();
		values = this.map.values().toArray();
		isSave = true;
		this.map = null;
	}
	@Override
	public int indexOfKey(K k) {
		if( k == null ) return -1;
//		Objects.requireNonNull(k);
		for (int i = 0; i < keys.length; i++) {
			if( k.equals(  keys [i] ) ) return i;  
		}
		return -1;
	}
	@Override
	public V update(K k, V v) {
		int indexOfKey = indexOfKey(k);
	    V old = (V) values [ indexOfKey ];
	    values [ indexOfKey ]  = v;
		return old;
	}
	
}
