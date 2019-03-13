package cn.wlh.util.base.adapter.java.util;

import cn.wlh.util.base.adapter.java.lang.AddAfterSeeImp;

/**缓存ValuesMap
 * @author 吴灵辉
 *
 * @param <K>
 * @param <V>
 */
public class CacheValuesMap<K, V> extends AddAfterSeeMapImp<K, AddAfterSeeListInterface<V>> {
//	@Override
//	public AddAfterSeeListInterface<V> put(K key, AddAfterSeeListInterface<V> value) {
//		AddAfterSeeListInterface<V> addAfterSeeListInterface = get(key);
//		if( addAfterSeeListInterface == null ) {
//			addAfterSeeListInterface = new AddAfterSeeImp<>();
//			
//		}
//		return super.put(key, value);
//	}
	/**一定可以put进去。
	 * @param index
	 * @param key
	 * @param value
	 * @param newflag @seee cn.wlh.util.base.adapter.java.util.JavaUtilFactory.newflag(int)
	 * @return
	 */
	public CacheValuesMap<K, V> putOneOfAutoAdd(K key, V value) {
		AddAfterSeeListInterface<V> addAfterSeeListInterface = get(key);
		if( addAfterSeeListInterface == null ) {
			addAfterSeeListInterface = new AddAfterSeeImp<>();
			put(key, addAfterSeeListInterface);
		}
			addAfterSeeListInterface.add(value);
			return this;
	}
}
