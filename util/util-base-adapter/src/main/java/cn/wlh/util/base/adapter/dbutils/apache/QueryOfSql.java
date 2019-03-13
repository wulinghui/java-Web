package cn.wlh.util.base.adapter.dbutils.apache;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import cn.wlh.util.base._String;

public class QueryOfSql extends Query{

	public QueryOfSql(DataSource dataSource) {
		super(dataSource);
		selectSql = selectSql();
		insertSql = insertSql();
	}
	String tableName = "WULINGHUI_SQL";
	String sqlField = "SQL";
	String keysField = "KEYS";
	String idField = tableName + "_ID";
	String idSequence = "seq_"+ tableName + "_ID";;
	String selectSql;
	String insertSql;
	private String keyJion;
	protected String insertSql() {
		if( insertSql != null ) return insertSql;
		StringBuilder sb = new StringBuilder("INSERT INTO ");
		sb.append(tableName).append('(');
		sb.append(idField).append(",");
		sb.append(sqlField).append(",");
		sb.append(keysField).append(") VALUES( ");
		sb.append(idSequence).append(".next()");
		sb.append(",?,?)");
		return sb.toString();
	}
	protected String selectSql() {
		if( selectSql != null ) return selectSql;
		StringBuilder sb = new StringBuilder("SELECT");
		sb.append(sqlField).append(" sqlField,");
		sb.append(keysField).append(" keysField");
		sb.append(" FROM ").append(tableName);
		sb.append(" WHERE ").append(idField);
		sb.append(" = ?");
		return  sb.toString();
	}
	protected String[] selectSqlAndKey(String id) throws SQLException {
		String [] sqlAndKey = new String[2];
		Map<String, Object> query = super.query( selectSql() , super.record , id );
		sqlAndKey[0] =  query.get("sqlField").toString();  // select * from user where id = ? and  shenqingh = ? and zt=? 这里没有联系
		sqlAndKey[1] =  query.get("keysField").toString(); // id,shenqingh,zt,
		return sqlAndKey;
	}
	/** 分析组装sql 和 keys 
	 * @param sql
	 * @param beanFlag
	 * @return 
	 */
	public String []  analysis(String sql, String beanFlag) {
		String [] sqlAndKey = new String[2];
		List<String> parameterOfNamed = super.getParameterOfNamed(sql, beanFlag);
		sqlAndKey[0] = super.popNamedList(parameterOfNamed);
		sqlAndKey[1] = _String.join(keyJion, parameterOfNamed);
		return sqlAndKey;
	}
	/**保存到数据库中.
	 * @param sql
	 * @param beanFlag
	 * @throws SQLException
	 */
	public void save(String sql, String beanFlag) throws SQLException {
		String[] analysis = analysis(sql, beanFlag);
		super.update(this.insertSql, analysis[0] , analysis[1]);
	}
	/**
	 * 这里beanFlag就没有用了。
	 * 
	 * 
	 */
	@Override
	public int updateOfNamed(String id, Map map, String beanFlag) throws SQLException {
//		List<String> parameterOfNamed = getParameterOfNamed(sql , beanFlag);
//		String query = popNamedList(parameterOfNamed);
//		Object[] array = toArray(map, parameterOfNamed);
//		
//		return super.update(query, array);
		String [] sqlAndKey = selectSqlAndKey(id);
		Object[] array = super.toArray(map, sqlAndKey[1].split(this.keyJion) );
		
		return super.update(sqlAndKey[0], array);
	}
	

	@Override
	public Object queryValueOfNamed(String sql, Map map, String beanFlag) throws SQLException {
//		List<String> parameterOfNamed = getParameterOfNamed(sql , beanFlag);
//		sql = popNamedList(parameterOfNamed);
//		Object[] array = toArray(map, parameterOfNamed);
		String [] sqlAndKey = selectSqlAndKey(sql);
		Object[] array = super.toArray(map, sqlAndKey[1].split(this.keyJion) );
		sql = sqlAndKey[0];
		
		return super.query(sql, value, array);

	}

	@Override
	public Map<String, Object> queryRecordOfNamed(String sql, Map map, String beanFlag) throws SQLException {
		String [] sqlAndKey = selectSqlAndKey(sql);
		Object[] array = super.toArray(map, sqlAndKey[1].split(this.keyJion) );
		sql = sqlAndKey[0];
		
		return super.query(sql, record, array);
	}

	@Override
	public List<Object> querySetOfNamed(String sql, Map map, String beanFlag) throws SQLException {
		String [] sqlAndKey = selectSqlAndKey(sql);
		Object[] array = super.toArray(map, sqlAndKey[1].split(this.keyJion) );
		sql = sqlAndKey[0];
		
		return super.query(sql, set, array);
	}

	@Override
	public List<Map<String, Object>> queryTableOfNamed(String sql, Map map, String beanFlag) throws SQLException {
		String [] sqlAndKey = selectSqlAndKey(sql);
		Object[] array = super.toArray(map, sqlAndKey[1].split(this.keyJion) );
		sql = sqlAndKey[0];
		
		return super.query(sql, table, array);
	}

	@Override
	public Object queryValue(String sql, Map map) throws SQLException {
//		List<String> parameter = getParameter(sql);
//		Object[] array = this.toArray(map, parameter);
		String [] sqlAndKey = selectSqlAndKey(sql);
		Object[] array = super.toArray(map, sqlAndKey[1].split(this.keyJion) );
		sql = sqlAndKey[0];
		
		return super.query(sql, value, array);
	}

	@Override
	public Map<String, Object> queryRecord(String sql, Map map) throws SQLException {
		String [] sqlAndKey = selectSqlAndKey(sql);
		Object[] array = super.toArray(map, sqlAndKey[1].split(this.keyJion) );
		sql = sqlAndKey[0];
		
		return super.query(sql, record, array);
	}

	@Override
	public List<Object> querySet(String sql, Map map) throws SQLException {
		String [] sqlAndKey = selectSqlAndKey(sql);
		Object[] array = super.toArray(map, sqlAndKey[1].split(this.keyJion) );
		sql = sqlAndKey[0];
		
		return super.query(sql, set, array);
	}

	@Override
	public List<Map<String, Object>> queryTable(String sql, Map map) throws SQLException {
		String [] sqlAndKey = selectSqlAndKey(sql);
		Object[] array = super.toArray(map, sqlAndKey[1].split(this.keyJion) );
		sql = sqlAndKey[0];
		
		return super.query(sql, table, array);
	}
	
}
