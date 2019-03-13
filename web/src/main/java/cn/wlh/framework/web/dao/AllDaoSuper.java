package cn.wlh.framework.web.dao;

import java.util.List;
import java.util.Map;

import cn.wlh.framework.web.entity.Context;
import cn.wlh.framework.web.entity.Record;
import cn.wlh.util.base.RecordNewOfLog;
import cn.wlh.util.base._Method;
import cn.wlh.util.base._Method.MethodFilter;
import cn.wlh.util.base.adapter.dbutils.apache.DaoMethodReturnCache.CachePreparedMap;
import cn.wlh.util.base.adapter.java.util.JavaUtilFactory;

/**
 * @author ����� ����һ��Ҫ�ǵ����ġ�
 */
public class AllDaoSuper extends RecordNewOfLog {
	public AllDaoSuper() {
		super('d');
		// Ϊÿ��ʵ����û��棬����������Լ����еķ�����
	}
	/**
	 * һ��ԭ����jdbc , �ʼ�����Լ�д���Ӻ���ǰ�����Ĺ���.��=?ǰ����ַ�������ó�����ֻ���� shenqing = ?�������. ���һ����sql
	 * , ������key.
	 * 
	 * @param sql
	 * @return
	 */
	protected List<String> analySqlJDBC(String sql) {
		List<String> parameter = DaoHelper.analySqlJDBC(sql);
		parameter.add(sql);
		return parameter;
	}
	/**
	 * ���һ����sql , ������key. ���Զ���Ĺ���,������sql��<br/>
	 * insert into users(username,password,email,birthday)
	 * values(:name,:pw,:email,:birth) <br/>
	 * <br/>
	 * [name, pw ,email, birth,<br/>
	 * insert into users(username,password,email,birthday) values(?,?,?,?) ]<br/>
	 * 
	 * @param sql
	 * @return
	 */
	protected List<String> analySqlName(String sql) {
		return DaoHelper.analySqlName(sql);
	}
	/*private List<String> demo(Context context , Record input){
		return this.analySqlJDBC("select * from user_table where name = ?");
	}
	private List<String> demo1(Context context , Record input){
		return this.analySqlJDBC("insert into users(username,password,email,birthday)" + 
				"values(:name,:pw,:email,:birth)");
	}*/
}
