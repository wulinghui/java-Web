package cn.wlh.util.base.adapter.java.util;

import java.util.Map;
import java.util.Properties;

import cn.wlh.util.base._Map;

/**
 * @author 吴灵辉
 * 增强java.util.Properties的功能.
 */
public class AdapterProperties extends Properties {

	/***/
	private static final long serialVersionUID = -1770106762218119615L;
	
	/**把数组的内容按顺序放入Properties里面. <br/>
	 * [ key1 , value1 <br/>
	 * 	 key2 , value2 <br/>
	 *   key3 , value3 ]<br/>
	 * 这样可以用于在构造函数中使用  <br/>
	 * @param array2Map
	 * @return
	 */
	public  AdapterProperties regsiterByArray(Object [] array2Map) {
		_Map.array2Map(this, array2Map, 2);
		return this;
	}
}
