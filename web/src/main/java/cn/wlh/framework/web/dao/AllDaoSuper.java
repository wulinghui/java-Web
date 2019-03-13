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
 * @author 吴灵辉 子类一定要是单例的。
 */
public class AllDaoSuper extends RecordNewOfLog {
	public AllDaoSuper() {
		super('d');
		// 为每个实例获得缓存，最初并放入自己所有的方法。
	}
	/**
	 * 一句原生的jdbc , 最开始是想自己写个从后往前解析的工具.把=?前面的字符串给获得出来。只对于 shenqing = ?这种情况. 最后一个是sql
	 * , 其他是key.
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
	 * 最后一个是sql , 其他是key. 把自定义的规则,解析成sql。<br/>
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
