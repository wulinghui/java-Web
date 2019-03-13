package cn.wlh.util.base.adapter.java.util;

import java.util.List;
/**
 * @author 吴灵辉
 *有一个应用场景：
  当add完了之后，我确定不再需要add了，接下来就只是查询或者修改了。这时候用定长的数组就行了
 * @param <E>
 */
public interface AddAfterSeeListInterface<E> extends List<E> {

	boolean isSave();
	
	/**不再添加。
	 * save之后好多方法都会报null
	 * 
	 */
	void save();
	
}