package cn.wlh.util.base.adapter.java.util;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import cn.wlh.util.extend.wrap.WrapObject1;

public class AdapterMap<K, V> extends WrapObject1<Map<K, V>> implements Map<K, V> {
	public AdapterMap(Map<K, V> source) {
		super(source);
	}
	@Override
	public int size() {
		return this.getSource().size();
	}

	@Override
	public boolean isEmpty() {
		return this.getSource().isEmpty();
	}

	@Override
	public boolean containsKey(Object key) {
		return this.getSource().containsKey(key);
	}

	@Override
	public boolean containsValue(Object value) {
		return this.getSource().containsValue(value);
	}

	@Override
	public V get(Object key) {
		return this.getSource().get(key);
	}

	@Override
	public V put(K key, V value) {
		return this.getSource().put(key, value);
	}

	@Override
	public V remove(Object key) {
		return this.getSource().remove(key);
	}

	@Override
	public void putAll(Map<? extends K, ? extends V> m) {
		this.getSource().putAll(m);
	}

	@Override
	public void clear() {
		this.getSource().clear();
	}

	@Override
	public Set<K> keySet() {
		return this.getSource().keySet();
	}

	@Override
	public Collection<V> values() {
		return this.getSource().values();
	}

	@Override
	public Set<Entry<K, V>> entrySet() {
		return this.getSource().entrySet();
	}

}
