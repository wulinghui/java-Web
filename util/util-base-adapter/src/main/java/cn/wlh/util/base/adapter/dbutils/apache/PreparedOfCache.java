package cn.wlh.util.base.adapter.dbutils.apache;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import cn.wlh.util.base.adapter.java.util.AddAfterSeeListInterface;
import cn.wlh.util.base.adapter.java.util.CacheValuesMap;

/**
 * @author 吴灵辉 之前的 cn.wlh.util.base.adapter.dbutils.apache.GetSql2.Prepared
 *         <br/>
 *         和cn.wlh.util.base.adapter.dbutils.apache.AnalysisSQL <br/>
 *         一:他们都是用集合方式对应索引的--不利于存储<br/>
 *         二:同时他们是以参数的多少来进行遍历的操作的在这一维度是O(N)操作. <br/>
 *         但是 如果重复的参数多,map的集合大.这对map.get()方法是不利的O(N+1.2.3.4....)。<br/>
 *         综上所述 , 他不利于缓存，不利于操作多的参数
 *         <hr/>
 *         所以这里采用以遍历Map的形式..set多处重复的参数。以达到真正的O(N)<br/>
 *         <br/>
 *         一句原生的jdbc , 最开始是想自己写个从后往前解析的工具.把=?前面的字符串给获得出来。只对于 shenqing = ?这种情况.
 */
public class PreparedOfCache {
	PreparedOfCache(){}
	static PreparedOfCache prepared[] = new PreparedOfCache[] { new PreparedOfCache(),
			 new PreparedNamedOfCache(':')
	};
	char[] fileterChar = { ' ', '\n', '\t', '\'', '"' };
	char[] startChar = { '?', '=' };

	protected void setFileterChar(char[] fileterChar) {
		this.fileterChar = fileterChar;
	}

	protected void setStartChar(char[] startChar) {
		this.startChar = startChar;
	}

	protected void setFlags(boolean[] flags) {
		this.flags = flags;
	}

	boolean flags[] = new boolean[startChar.length]; // = ?的标记

	/**
	 * 获得 shenqing = ? 的shenqing数组
	 * 
	 * @param sql
	 * @return shenqing
	 */
	public static PreparedOfCache getInstance(String id) {
		if ("".equals(id)) {
			return prepared[0];
		} else if (":".equals(id)) {
			return prepared[1];
		}
		return null;
	}

	/**
	 * 最后一个是sql , 其他是key.
	 * 
	 * @param query
	 * @param beanFlag
	 * @return
	 */
	public CachePreparedMap getParameter(String sql) {
		// 先把sql的空格 \t \n全换成一个' ' 暂时不需要.
		char[] strChar = sql.toCharArray();
		int length = strChar.length;
		int endLength = startChar.length;
		List<Character> chars = new ArrayList<Character>();
		Object[] array;
		String str;
		// List<String> strs = new ArrayList<>();
		CachePreparedMap preparedMap = new CachePreparedMap();
		int indexOf = 1; // 对应索引的位置
		// 循环解析char
		for (int i = length; length > endLength; i--) { // 2
			char c = strChar[i];

			if (flags[0]) {// 有? 接下判断=
				if (flags[1] == true && !lookUp(c, fileterChar)) { // 正在进行 并不在过滤数组里面.
					chars.add(c);
				} else if (startChar[1] == c) { // 为=的时候
					flags[1] = true;
					// 在进行之前的过滤数组里面. "s = ?" 的 " = ?"这些
				} else if (lookUp(c, fileterChar) && flags[1] == false) {

				} else if (chars.size() > 0) { // 正在进行 并在过滤数组里面.. "shenq ingh = ?" 的 "shenq ingh"
					flags[0] = false;
					flags[1] = false;
					array = chars.toArray();
					str = String.valueOf(array);
					// strs.add(str);
					preparedMap.putOneOfAutoAdd(str, indexOf);
					chars.clear();
					indexOf++;
				} else if (chars.size() == 0) { // //正在进行 并在过滤数组里面.. "shenq ingh = ?" 的 "ingh ="

				}
			} else if (c == startChar[0]) {
				flags[0] = true; // 有? 下次循环判断=
			}
		}
		// strs.add(sql);
		preparedMap.setFinalSql(sql);
		preparedMap.saveAllKeyAndValue();
		return preparedMap;
	}

	// 是否在数组中.
	public boolean lookUp(char c, char[] strChar) {
		for (char d : strChar) {
			if (d == c)
				return true; // 在
		}
		return false;// 不在
	}

	/**
	 * @author 吴灵辉<br/>
	 *         缓存参数key对应的索引。<br/>
	 *         如： shenqingh = 1, 3 , 6,7 --key在1 3 6 7 位置上使用了.<br/>
	 *         不是以前的 1 = shenqingh<br/>
	 *         3 = shenqingh<br/>
	 *         6 = shenqingh<br/>
	 *         7 = shenqingh <br/>
	 *         而且里面的List是定长的数组。觉不多余一个空间。
	 */
	public static class CachePreparedMap extends CacheValuesMap<String, Integer> {
		int size; // 所有值的大小

		String finalSql;// 最终的sql语句

		@Override
		public void save() {
			throw new RuntimeException("请使用saveAllKeyAndValue方法..");
		}

		@Override
		public AddAfterSeeListInterface<Integer> put(String key, AddAfterSeeListInterface<Integer> value) {
			throw new RuntimeException("请使用putOneOfAutoAdd方法..");
		}

		@Override
		public CacheValuesMap<String, Integer> putOneOfAutoAdd(String key, Integer value) {
			size++;
			return super.putOneOfAutoAdd(key, value);
		}

		// /**自动++
		// * @param key
		// */
		// public void valueAutoAdd(String key) {
		// AddAfterSeeListInterface<Integer> addAfterSeeListInterface = get(key);
		// if( addAfterSeeListInterface == null) {
		// addAfterSeeListInterface = new AddAfterSeeImp<>();
		// super.put(key, addAfterSeeListInterface);
		// }
		// Integer integer = addAfterSeeListInterface.get( size() -1 );
		// // 这里的Value可能是自增的,这里的addAfterSeeListInterface是存放对应的索引位置的..
		// }
		/**
		 * 转化为定长 save -- Map和所有的List
		 */
		public void saveAllKeyAndValue() {
			// 所有的List
			Collection<AddAfterSeeListInterface<Integer>> values = super.values();
			// TODO 不知道是不是真实的。有时间试试他里面我们没有实现的方法，报null就知道了。
			Iterator<AddAfterSeeListInterface<Integer>> iterator = values.iterator();
			while (iterator.hasNext()) {
				AddAfterSeeListInterface<Integer> next = iterator.next();
				next.save();
			}
			// map
			super.save();
		}

		public String getFinalSql() {
			return finalSql;
		}
		/**
		 * 会自动转大写..
		 * @param finalSql
		 */
		public void setFinalSql(String finalSql) {
			this.finalSql = finalSql.toUpperCase();
		}

		public int getSize() {
			return size;
		}
	}
}
