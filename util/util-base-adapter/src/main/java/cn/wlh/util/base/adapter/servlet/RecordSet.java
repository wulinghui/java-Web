package cn.wlh.util.base.adapter.servlet;

import java.util.List;
import java.util.Map;
import java.util.Set;

import cn.wlh.util.base.adapter.java.util.AdapaterList;
import cn.wlh.util.base.adapter.java.util.JavaUtilFactory;

public class RecordSet extends AdapaterList<Map<String, Object>> implements JsonInterface{
	
	public RecordSet(List<Map<String, Object>> source) {
		super(source);
	}
	
	public <T> T getOne(int index , String key ) {
		return (T) get(index).get(key);
	}
	
	public <T> T getOneOfFirst(String key) {
		return getOne(0, key);
	}
	
	/**只放已有的key,没有其他多余的操作。
	 * @param index
	 * @param key
	 * @param value
	 * @return
	 */
	public RecordSet putOne(int index , String key , Object value) {
		get(index).put(key, value);
		return this;
	}
	public RecordSet putOneOfFirst( String key , Object value ,int newflag) {
		return putOneOfAutoAdd(0, key, value, newflag);
	}
	/** 一定可以put进去。
	 * @param index
	 * @param key
	 * @param value
	 * @param newflag @seee cn.wlh.util.base.adapter.java.util.JavaUtilFactory.newflag(int)
	 * @return
	 */
	public RecordSet putOneOfAutoAdd(int index , String key , Object value ,int newflag) {
		Map<String, Object> map = this.get(index);
		if( map == null ) {
			map = JavaUtilFactory.newMap(newflag);
		}
		map.put(key, value);
		return this;
	}

	@Override
	public String toJson() {
		
		return null;
	}
}
