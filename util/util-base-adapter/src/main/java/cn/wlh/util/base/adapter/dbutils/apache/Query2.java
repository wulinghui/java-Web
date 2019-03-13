package cn.wlh.util.base.adapter.dbutils.apache;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.sql.DataSource;

/**
 * @author 吴灵辉
 * 这里有个问题,当一个 Map 里面的key 在里面用了多次的时候。每次都去遍历，这效率有些慢。
 * 1.在获得索引的时候获得一个set<key,index>;
 */
public class Query2 extends Query {

	public Query2(DataSource dataSource) {
		super(dataSource);
	}
	
//	public <E,V> Object[] toArray(Map<E,V> map,List<E> list) {
//		int size = list.size();
//		Map<String ,Integer [] > temp = new HashMap<>();
//		Set set;
//		for (int i = 0; i < size ; i++) {
//			// 如果不加标记.这种是不可取的..反而更慢。 --解析，或者使用。
	        // 加标记,麻烦。增加解析和使用者的困难。
//		}
//	}
	
}
