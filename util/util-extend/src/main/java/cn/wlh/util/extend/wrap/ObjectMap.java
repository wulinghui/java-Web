package cn.wlh.util.extend.wrap;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * @author 吴灵辉
 * 用这个Map代替所有的对象。
 * @param <K> 属性名
 * @param <V> 属性值
 * 这里的Map是通过数组定长完成的，所以空间和效率在一定程度上都是可以接受的--和原始的类相比肯定慢。
 * 但是不能把他代替所有的对象，否则强类型语言的优势体现不了。这个的应用场景在于：
 * 1： 当我这个类是完全按照其他的结构与之对应，我在这里只不过是个翻译的过程，--也就是说类的属性结构完全有个参考的标准，如：POJO与表结构完全对应。
 * 2: 当我对某一个业务熟悉为了之后的可拓展性，该对象属性有一定规律，我完全可以控制属性走向。我可以提取这个可变业务为可配置的业务。
 */
public class ObjectMap<K, V> implements Map<K, V> {
	Object[] key;
	Object[] value;
	int size;
	public ObjectMap(int length) {
		init(length);
	}
	/**初始化数组
	 * @param length
	 */
	protected void init(int length) {
		key = new Object[length];
		value = new Object[length];
	}
	@Override
	public int size() {
		return size;
	}

	@Override
	public boolean isEmpty() {
		return size == 0 ? true : false;
	}
	public boolean containsKey(Object key) {
		return getKeyIndex(key) == -1 ? false : true;
	}
	/**获得key所对应的下标
	 * @param key
	 * @return -1 没有查到
	 */
	protected int getKeyIndex(Object key) {
		Objects.requireNonNull(key);
		int len = this.key.length;
		for (int i = 0; i < len; i++) {
			if( key.equals( this.key[i]) ) return i;
		}
		return -1;
	}
	@Override
	public boolean containsValue(Object value) {
		int j = -1;
		int len = this.value.length;
		for (int i = 0; i < len; i++) {
			if( value.equals( this.value[i]) ) j=i;
		}
		return j == -1 ? false : true;
	}

	@Override
	public V get(Object key) {
		int keyIndex = getKeyIndex(key);
		return keyIndex == -1 ? null : (V) this.value[keyIndex];
	}

	@Override
	public V put(K key, V value) {
		int keyIndex = getKeyIndex(key);
		if( keyIndex == -1 ) {
			this.key[size] = key;
			this.value[size] = value;
			this.size++;
			return null;
		}else {
			V v = (V) this.value[keyIndex];
			this.key[keyIndex] = key;
			this.value[keyIndex] = value;
			return v;
		}
	}

	@Override
	public V remove(Object key) {
		int keyIndex = getKeyIndex(key);
		if( keyIndex == -1 ) {
			return null;
		}else {
			this.key[keyIndex] = null;
			this.size--;
			return (V) this.value[keyIndex];
		}
	}
	@Override
	public void clear() {
		init(key.length);
	}
	
	/**
	 * 提供通过this.size遍历时真实的数组下标.
	 * 出现这样的主要原因是put方法没有对数组的值加以控制，中间的内容可能为null,导致存在碎片
	 * @param size 是长度不是下标
	 * @return  -1为错误的size..
	 * 否则以下方法都不好实现。
	 */
	protected int getRealIndex(int size) {
		for (int i = 0; i < this.key.length; i++) {
			if( this.key[i] != null ) size--;
			if( size == 0 ) return i;
		}
		return -1;
	}
	/**
	 * 
	 * 主要原因是put方法没有对数组的值加以控制，中间的内容可能为null
	 */
	@Override
	public void putAll(Map<? extends K, ? extends V> map) {
		if ( map.size() > this.key.length ) Objects.requireNonNull(null); 
//		Object key2 ;
//		Object value2 ; 
//		for (Entry<? extends K, ? extends V> object : map.entrySet()) {
//			 key2 = object.getKey();
//			 value2 = object.getValue();
//		}
		
	}

	

	@Override
	public Set<K> keySet() {
		Set set = new HashSet();
		Object e;
		for (int i = 0; i < this.size; i++) {
			int realIndex = getRealIndex(i);
			set.add( this.key [ realIndex ]);
		}
		return set;
	}

	@Override
	public Collection<V> values() {
		ArrayList arrayList = new ArrayList();
		Object e;
		for (int i = 0; i < this.size; i++) {
			int realIndex = getRealIndex(i);
			arrayList.add( this.value [ realIndex ]);
		}
		return arrayList;
	}

	@Override
	public Set<Entry<K, V>> entrySet() {
		int keyIndex;
		MyEntry<Object,Object> entry;
		Set set = new HashSet();
		for (int i = 0; i < this.size; i++) {
			keyIndex = this.getKeyIndex(i);
			entry = new MyEntry<Object,Object>(key[keyIndex], value[keyIndex]);
			set.add(entry);
		}
		return set;
	}
	public class MyEntry<K,V> implements Entry<K,V>{
		K key;
		V value;
		public MyEntry(K key, V value) {
			super();
			this.key = key;
			this.value = value;
		}

		@Override
		public K getKey() {
			return key;
		}

		@Override
		public V getValue() {
			return value;
		}

		@Override
		public V setValue(V value) {
			V value1 = this.value;
			this.value = value;
			return value1;
		}
	}
}
