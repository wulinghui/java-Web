package cn.wlh.util.base.adapter.dbutils.apache;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.sql.DataSource;

import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.ColumnListHandler;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import cn.wlh.util.base.adapter.dbutils.apache.GetSql2.Prepared;

/**
 * @author 吴灵辉 这个类,封装了对JDBC的操作，简化了JDBC操作。可以少写代码。
 *         这里需要注意的一点是,参数传入conn的方法是不会关闭(归还给数据连接池)conn.只会关闭rs,stmt. 一个数据连接池一个对象..
 *         术语: --
 *         <table>
 *         <tr>
 *         <td>数值</td>
 *         <td>单行单列</td>
 *         <td>value</td>
 *         </tr>
 *         <tr>
 *         <td>记录</td>
 *         <td>单行多列</td>
 *         <td>record</td>
 *         </tr>
 *         <tr>
 *         <td>集合</td>
 *         <td>多行单列</td>
 *         <td>set</td>
 *         </tr>
 *         <tr>
 *         <td>表</td>
 *         <td>多行多列</td>
 *         <td>table</td>
 *         </tr>
 *         </table>
 */
public class Query extends org.apache.commons.dbutils.QueryRunner {
	public Query(DataSource dataSource) {
		super(dataSource);
		if (dataSource == null)
			throw new NullPointerException();
		
		Connection connection;
		try {
			connection = dataSource.getConnection();
			PreparedStatement prepareStatement = connection.prepareStatement("");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/*
	 * 不管是DBCPUtils.close 还是 这里的close都是归还给连接池.
	 */
	public void close() throws SQLException {
		// 构造方法保证了 super.ds!=null
		close(super.ds.getConnection());
	}

	/**
	 * 小心无序的Map
	 * 
	 * @param map
	 * @return
	 */
	public Object[] toArray(Map map) {

		Object[] params = map.values().toArray();
		return params;
	}

	/**
	 * 以strings里面的数组为顺序,进行获得数组.
	 * 
	 * @param map
	 * @param strings
	 * @return
	 */
	public Object[] toArray(Map map, Object... strings) {
		int length = strings.length;
		Object[] objs = new Object[length];
		for (int i = 0; i < length; i++) {
			Object key = strings[i];
			objs[i] = map.get(key);
		}
		return objs;
	}
	public  Object[] toArray(Map<String,Object> map, Collection<String> set) {
			int size = set.size() ,i = 0;
			Object [] objs = new Object[size];
			Iterator<String> iterator = set.iterator();
			for (; i < size ; i++) {
				objs[i] = map.get(iterator.next());
			}
		return objs;
	}
	// 还有三种封装的方法,见cn.wlh.util.base.adapter.dbutils.apache.GetSql2
	// 这是上面的的第一个方法. 就是原生的API
	public int update(String sql, Map map) throws SQLException {
		return update(sql, toArray(map));
	}

	public int update(String sql, Map map, Object... strings) throws SQLException {
		return update(sql, toArray(map, strings));
	}

	public <T> T query(String sql, ResultSetHandler<T> rsh, Map map) throws SQLException {
		return super.query(sql, toArray(map), rsh);
	}

	public <T> T query(String sql, Map map, ResultSetHandler<T> rsh, Object... strings) throws SQLException {
		return super.query(sql, toArray(map, strings), rsh);
	}

	// 注意关闭
	// 第二种方法. 建议在参数多,提高可读性高。 insert into users(username,password,email,birthday)
	// values(:name,:pw,:email,:birth)
	public NamedPreparedStatement getStatement(String sql, Map<String, Object> map) throws SQLException {
		NamedPreparedStatement p = new NamedPreparedStatement(this.getDataSource().getConnection(), sql);
		setParameter(p, map);
		return p;
	}

	protected void setParameter(NamedPreparedStatement p, Map<String, Object> map) throws SQLException {
		for (Entry<String, Object> element : map.entrySet()) {
			String name = element.getKey();
			Object value = element.getValue();
			p.setObject(name, value);
		}
	}
	
	public int updateOfNamed(String sql, Map map) throws SQLException {
		return updateOfNamed(sql, map, defaultFlag);
	}
	public int updateOfNamed(String sql, Map map, String beanFlag) throws SQLException {
		List<String> parameterOfNamed = getParameterOfNamed(sql , beanFlag);
		String query = popNamedList(parameterOfNamed);
		Object[] array = toArray(map, parameterOfNamed);
		
		return super.update(query, array);
	}
	
	
	public Object queryValueOfNamed(String sql, Map map ) throws SQLException {
		return queryValueOfNamed(sql, map, defaultFlag);
	}
	public Object queryValueOfNamed(String sql, Map map , String beanFlag) throws SQLException {
		List<String> parameterOfNamed = getParameterOfNamed(sql , beanFlag);
		sql = popNamedList(parameterOfNamed);
		Object[] array = toArray(map, parameterOfNamed);
		
		return super.query(sql, value, array);
	}
	
	public Map<String, Object> queryRecordOfNamed(String sql, Map map ) throws SQLException {
		return queryRecordOfNamed(sql, map, defaultFlag);
	}
	public Map<String,Object> queryRecordOfNamed(String sql, Map map , String beanFlag) throws SQLException {
		List<String> parameterOfNamed = getParameterOfNamed(sql , beanFlag);
		sql = popNamedList(parameterOfNamed);
		Object[] array = toArray(map, parameterOfNamed);
		
		return super.query(sql, record, array);
	}
	
	public List<Object> querySetOfNamed(String sql, Map map ) throws SQLException {
		return querySetOfNamed(sql, map, defaultFlag);
	}
	public  List<Object> querySetOfNamed(String sql, Map map , String beanFlag) throws SQLException {
		List<String> parameterOfNamed = getParameterOfNamed(sql , beanFlag);
		sql = popNamedList(parameterOfNamed);
		Object[] array = toArray(map, parameterOfNamed);
		
		return   super.query(sql, set, array);
	}
	
	public List<Map<String, Object>> queryTableOfNamed(String sql, Map map ) throws SQLException {
		return queryTableOfNamed(sql, map, defaultFlag);
	}
	public List<Map<String, Object>> queryTableOfNamed(String sql, Map map , String beanFlag) throws SQLException {
		List<String> parameterOfNamed = getParameterOfNamed(sql , beanFlag);
		sql = popNamedList(parameterOfNamed);
		Object[] array = toArray(map, parameterOfNamed);
		
		return  super.query(sql, table, array);
	}
	
	
	final static String defaultFlag = ":";
	protected List<String> getParameterOfNamed(String query) {
		return cn.wlh.util.base.adapter.dbutils.apache.GetSql2.Prepared.getInstance(defaultFlag).getParameter(query);
	}
	/**最后一个是sql , 其他是key.
	 * @param query
	 * @param beanFlag
	 * @return
	 */
	protected List<String> getParameterOfNamed(String query , String beanFlag) {
		return cn.wlh.util.base.adapter.dbutils.apache.GetSql2.Prepared.getInstance(beanFlag).getParameter(query);
	}
	/** 获得sql语句同时移除
	 * @param query
	 * @return insert into users(username,password,email,birthday)  values(?,?,?,?)
	 */
	protected String popNamedList(List<String> parameterOfNamed) {
		return parameterOfNamed.remove( parameterOfNamed.size()-1 );
	}
	// 注意关闭
	// 第3种方法. 建议在参数多, 且 shenqingh = ? 这总键值对的
	public PreparedStatement prepared(Map map, String query) throws SQLException {
		return prepared(map, query, super.getDataSource().getConnection());
	}
	
	protected Object[] getSqlAndValueArray(String sql, Map map) {
		return getSqlAndValueArray(sql, map, "");
	}
	protected Object[] getSqlAndValueArray(String sql, Map map,String beanFlag) {
		List<String> parameter = this.getParameterOfNamed(sql, beanFlag); 
		sql = popNamedList(parameter);
		Object[] array = this.toArray(map, parameter);
		return new Object[]{  sql , array};
	}
	static ScalarHandler value = new ScalarHandler(1);
	public Object queryValue(String sql, Map map) throws SQLException {
		Object[] sqlAndValueArray = getSqlAndValueArray(sql, map);
		return super.query( (String)sqlAndValueArray[0], value, (Object[]) sqlAndValueArray[1]);
	}
	
	static MapHandler record = new MapHandler();
	public Map<String,Object> queryRecord(String sql, Map map) throws SQLException {
		Object[] sqlAndValueArray = getSqlAndValueArray(sql, map);
		return super.query( (String)sqlAndValueArray[0], record, (Object[]) sqlAndValueArray[1]);
	}
	
	static ColumnListHandler<Object> set =new ColumnListHandler<Object>(1);
	public  List<Object> querySet(String sql, Map map) throws SQLException {
		Object[] sqlAndValueArray = getSqlAndValueArray(sql, map);
		return super.query( (String)sqlAndValueArray[0], set, (Object[]) sqlAndValueArray[1]);
	}

	static MapListHandler table = new MapListHandler();
	public List<Map<String, Object>> queryTable(String sql, Map map) throws SQLException {
		Object[] sqlAndValueArray = getSqlAndValueArray(sql, map);
		return super.query( (String)sqlAndValueArray[0], table, (Object[]) sqlAndValueArray[1]);
	}

	//
	public PreparedStatement prepared(Map map, String query, Connection connection) throws SQLException {
		PreparedStatement prepareStatement = connection.prepareStatement(query);
		List<String> parameter = getParameter(query);
		setParameter(prepareStatement, parameter, map);
		return prepareStatement;
	}

	protected List<String> getParameter(String query) {
		return cn.wlh.util.base.adapter.dbutils.apache.GetSql2.Prepared.getInstance("").getParameter(query);
	}

	protected void setParameter(PreparedStatement prepareStatement, List<String> parameter, Map map)
			throws SQLException {
		int size = parameter.size();
		for (int i = 0; i < size; i++) {
			prepareStatement.setObject(i + 1, map.get(parameter.get(i)));
		}
	}

	/**
	 * 追加字段名.
	 * 
	 * @param sb
	 * @param keys
	 * @return keys[0] + tag + keys[1]
	 */
	public Appendable appendFiled(Appendable sb, String[] keys) {
		int len = keys.length, i = 0;
		try {
			sb.append(keys[i++]);
			for (; i < len; i++) {
				sb.append(',').append(keys[i]);
			}
		} catch (IOException e) {
			// 不可能的异常..
			e.printStackTrace();
		}
		return sb;
	}

	/**
	 * 追加字段名.
	 * 
	 * @param sb
	 * @param keys
	 * @return keys[0] + tag + keys[1]
	 */
	public Appendable appendFiled(Appendable sb, Collection<String> keys) {
		try {
			Iterator<String> iterator = keys.iterator();
			sb.append(iterator.next());
			while (iterator.hasNext()) {
				sb.append(',').append(iterator.next());
			}
		} catch (IOException e) {
			// 不可能的异常..
			e.printStackTrace();
		}
		return sb;
	}

	/**
	 * 追加 '?' 参数.
	 * 
	 * @param sb
	 * @param keysLength
	 * @return ?,?,?
	 */
	public Appendable appendParameter(Appendable sb, int keysLength) {
		try {
			sb.append('?');
			for (int i = 1; i < keysLength; i++) {
				sb.append(',').append('?');
			}
		} catch (IOException e) {
			// 不可能的异常..
			e.printStackTrace();
		}
		return sb;
	}

	/**
	 * @param sb
	 * @param keys
	 * @return keys=?
	 */
	public Appendable appendPrepare(Appendable sb, String keys) {
		try {
			sb.append(keys).append("=?");
		} catch (IOException e) {
			// 不可能的异常..
			e.printStackTrace();
		}
		return sb;
	}

	/**
	 * @param sb
	 * @param keys
	 * @return keys=:keys
	 */
	public Appendable appendNamedPrepare(Appendable sb, String keys) {
		try {
			sb.append(keys).append("=:").append(keys);
		} catch (IOException e) {
			// 不可能的异常..
			e.printStackTrace();
		}
		return sb;
	}

	/*
	 * <select name="用户和状态表的联系"> from uzser u , z_fa_wjsp_scztb zt where zt.id =
	 * u.id and zt.shenqingh = ? and u.name = 'zhangsan'; </select>
	 * 1.生成到oracle管理,我们查询就特别简单了(复用性) 2.真正用的时候:通过id从数据库里面取出来.
	 * 
	 */
	/**
	 * @param id
	 *            id--数据库的id
	 * @param dynamicKeys
	 *            动态返回字段.
	 * @param map
	 *            输入的key和值. shenqingh = ?
	 * @return
	 * @throws SQLException
	 */
	/*
	 * public <T> T execute(String id, String[] dynamicKeys, Map<String, Object>
	 * map) throws SQLException { StringBuilder sb = new StringBuilder("SELECT");
	 * String query = appendFiled(sb, dynamicKeys).append( id ).toString();
	 * 
	 * // PreparedStatement prepared = this.prepared(map, query); Map<String,
	 * Object> executeQuery = executeQuery(prepared); // 关闭 rs
	 * super.close(prepared); return executeQuery; }
	 */
//	public class DynamicSql {
//		
//		
//		public DynamicSql(Map map,StringBuilder sb ){
//			sb.append
//		}
//		
//	}
//	public void dynamicSql(Appendable sb) {
//		
//	}
	/**
	 * @param sql
	 * @param map
	 * @return
	 *         <li>多行返回List<Map<String, Object>></li>
	 *         <li>单行返回 Map<String, Object></li>
	 *         <li>没有查询结果 -- null</li>
	 * 
	 *         <li>Integer影响的行数</li>
	 * @throws SQLException
	 */
	public <T> T execute(String sql, Map<String, Object> map) throws SQLException {
		PreparedStatement prepared = this.prepared(map, sql);
		T t ;
		if (sql.startsWith("SELECT")) {
			ResultSet rs = prepared.executeQuery();
			int row = rs.getRow();
			
			switch (row) {
			case 1:
				 t = (T) putResultSet(rs); //
			case 0:
			case -1:
				t =  null;
			default:
				t =  (T) executeQuery(rs);
			}
//			// 关闭 rs
			super.close(rs);
		} else {
			t =  (T) ((Integer) prepared.executeUpdate());
		}
//		// 关闭 prepared
		super.close(prepared);
		return t;
	}

	protected List<Map<String, Object>> executeQuery(PreparedStatement prepared) throws SQLException {
		ResultSet rs = prepared.executeQuery();
		return executeQuery(rs);
	}

	protected List<Map<String, Object>> executeQuery(ResultSet rs) throws SQLException {
		/** 不能合并到while里面，效率问题 */
		ResultSetMetaData rsmd = rs.getMetaData();
		int cols = rsmd.getColumnCount();
		Map<String, Object> result;

		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		while (rs.next()) {
			result = putResultSet(rs, rsmd, cols);
			list.add(result);
		}
//		// 关闭 rs
//		super.close(rs);
		return list;
	}

	protected Map<String, Object> putResultSet(ResultSet rs) throws SQLException {
		ResultSetMetaData rsmd = rs.getMetaData();
		int cols = rsmd.getColumnCount();
		return putResultSet(rs, rsmd, cols);
	}

	protected Map<String, Object> putResultSet(ResultSet rs, ResultSetMetaData rsmd, int cols) throws SQLException {
		Map<String, Object> result = new HashMap<String, Object>();
		for (int i = 1; i <= cols; i++) {
			putRow(rs, rsmd, result, i);
		}
		return result;
	}

	protected void putRow(ResultSet rs, ResultSetMetaData rsmd, Map<String, Object> result, int i) throws SQLException {
		String columnName = rsmd.getColumnLabel(i);
		if (null == columnName || 0 == columnName.length()) {
			columnName = rsmd.getColumnName(i);
		}
		result.put(columnName, rs.getObject(i));
	}

	/*
	 * super... 具体使用方法见test包下的内容.
	 */
}
