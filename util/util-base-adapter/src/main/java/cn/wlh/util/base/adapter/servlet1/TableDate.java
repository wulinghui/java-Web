package cn.wlh.util.base.adapter.servlet1;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

/**
 * @author 吴灵辉 RecordSet2 -- 改名为ColumnSet集合 这是聚合json的jar 和实现List -- 通过 json 或者
 *         里面的属性实现.
 * 
 */
public class TableDate extends AbstractJSONArray<Record> {

	@Override
	public Record get(int index) {
		Object object = json.get(index);
		//看看真实的类.
//		System.out.println( object.getClass().getName() );
		Record db = new Record();
		//看看真实的类.
		if( object instanceof JSONObject) {
//			db = new Record();
			JSONObject new_name = (JSONObject) object;
			db.putAll(new_name);
		}
		return db;
	}

//	public void addAll(List<Map<String, Object>> query2) {
//		super.json.addAll(query2);
//	}
	
	
}
