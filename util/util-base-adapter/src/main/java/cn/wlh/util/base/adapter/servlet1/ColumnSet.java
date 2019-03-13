package cn.wlh.util.base.adapter.servlet1;

/**
 * @author 吴灵辉 RecordSet2 -- 改名为ColumnSet集合 这是聚合json的jar 和实现List -- 通过 json 或者
 *         里面的属性实现.
 * 
 */
public class ColumnSet extends AbstractJSONArray<Object> {

	@Override
	public Object get(int index) {
		return super.json.get(index);
	}
	
}
