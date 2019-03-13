package cn.wlh.util.base.adapter.java.util;

import java.util.Map;
/**
 * @author 吴灵辉
 *有一个应用场景：
  当add完了之后，我确定不再需要add了，接下来就只是查询或者修改了。这时候用定长的数组就行了
 * @param <E>
 */
public interface AddAfterSeeMapInterface<K, V> extends Map<K, V> {


	boolean isSave();
	
	/**不再添加。
	 * save之后好多方法都会报null
	 * 
	 */
	void save();
	
	/** 获得K在数组中的坐标.
	 * @param k
	 * @return
	 */
	int indexOfKey(K k);
	
	/** 修改值  , save之后不再使用put 
	 * @param k
	 * @param v
	 * @return 原来的值
	 */
	V update(K k , V v);
}
