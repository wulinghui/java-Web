package cn.wlh.util.base.adapter.servlet1;

import java.beans.PropertyDescriptor;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Objects;

import javax.sql.DataSource;

import org.apache.commons.dbutils.QueryRunner;

import cn.wlh.util.base.adapter.dbutils.apache.DaoMethodReturnCache;
import cn.wlh.util.base.adapter.dbutils.apache.DaoMethodReturnCache.AllDaoSuperOfSingle;
import cn.wlh.util.base.adapter.dbutils.apache.DaoMethodReturnCache.CachePreparedMap;
import cn.wlh.util.base.adapter.dbutils.apache.DaoMethodReturnCacheInterface;

/**
 * 	@author 吴灵辉  <br/>
 * 	cn.wlh.util.base.adapter.dbutils.apache.DaoExecute.main(String[]) <br/>
	//1.返回值太长..且没有扩展方法的使用 <br/>
	//2.父类定死了..这里没法改了...传入的值可以枚举,但是返回值不能. <br/>
	//3.productdao2.table -- 把第三方jar暴露给用户了，不利于将来的迁移。<br/>
	总结:在一个类中只要有他内部的类.就用聚合模式..
	TODO insert 也需要Handle?这个有网的时候看看API..
 */
/**
 * @author 吴灵辉
 *
 * @param <K1>
 *            Context 的key
 * @param <V1>
 *            Context 的value
 * @param <K2>
 *            DataBus 的key
 * @param <V2>
 *            DataBus 的value
 * @param <Context>
 *            Map 上下文
 * @param <DataBus>
 *            Map 单个Bean
 * @param <Table>
 *            查询表的内容
 * @param <Set>
 *            查询单列多行的内容
 * @param <Recored>
 *            查询单行的内容
 * @param <Value>
 *            查询数值的内容
 */
public abstract  class DaoMethodReturnCache3 implements DaoMethodReturnCacheInterface<String, Object, String, Object, Context3, Record, TableDate, ColumnSet, Record, Object> {
	protected QueryRunner query;
	
	public DaoMethodReturnCache3(DataSource dataSource) {
		Objects.requireNonNull(dataSource);
		query = new QueryRunner(dataSource);
	}
	
	@Override
	public int update(Connection conn, AllDaoSuperOfSingle daoSingle, String methodName, Record dataBus,
			Context3 context) throws SQLException {
		CachePreparedMap invoke = DaoMethodReturnCache.invoke(daoSingle, methodName, new Object[] { context, dataBus });
		Object[] params = DaoMethodReturnCache.getAllValue(invoke, dataBus);
		String sql = invoke.getFinalSql();
		return query.update(sql, params);
	}

	@Override
	public int update(AllDaoSuperOfSingle daoSingle, String methodName, Record dataBus, Context3 context)
			throws SQLException {
		return update(query.getDataSource().getConnection(), daoSingle, methodName, dataBus, context);
	}

	@Override
	public TableDate getTable(AllDaoSuperOfSingle daoSingle, String methodName, Record dataBus, Context3 context)
			throws SQLException {
		return getTable(query.getDataSource().getConnection(), daoSingle, methodName, dataBus, context);
	}

	@Override
	public ColumnSet getSet(AllDaoSuperOfSingle daoSingle, String methodName, Record dataBus, Context3 context)
			throws SQLException {
		return getSet(query.getDataSource().getConnection(), daoSingle, methodName, dataBus, context);
	}

	@Override
	public Record getRecord(AllDaoSuperOfSingle daoSingle, String methodName, Record dataBus, Context3 context)
			throws SQLException {
		return getRecord(query.getDataSource().getConnection(), daoSingle, methodName, dataBus, context);
	}

	@Override
	public Object getValue(AllDaoSuperOfSingle daoSingle, String methodName, Record dataBus, Context3 context)
			throws SQLException {
		return getValue(query.getDataSource().getConnection(), daoSingle, methodName, dataBus, context);
	}

	@Override
	public TableDate getTable(Connection conn, AllDaoSuperOfSingle daoSingle, String methodName, Record dataBus,
			Context3 context) throws SQLException {
		CachePreparedMap invoke = DaoMethodReturnCache.invoke(daoSingle, methodName, new Object[] { context, dataBus });
		Object[] params = DaoMethodReturnCache.getAllValue(invoke, dataBus);
		String sql = invoke.getFinalSql();
		
		return getTable(conn, sql, params);
	}

	@Override
	public ColumnSet getSet(Connection conn, AllDaoSuperOfSingle daoSingle, String methodName, Record dataBus,
			Context3 context) throws SQLException {
		CachePreparedMap invoke = DaoMethodReturnCache.invoke(daoSingle, methodName, new Object[] { context, dataBus });
		Object[] params = DaoMethodReturnCache.getAllValue(invoke, dataBus);
		String sql = invoke.getFinalSql();
		return getSet(conn, sql, params);
	}

	@Override
	public Record getRecord(Connection conn, AllDaoSuperOfSingle daoSingle, String methodName, Record dataBus,
			Context3 context) throws SQLException {
		CachePreparedMap invoke = DaoMethodReturnCache.invoke(daoSingle, methodName, new Object[] { context, dataBus });
		Object[] params = DaoMethodReturnCache.getAllValue(invoke, dataBus);
		String sql = invoke.getFinalSql();
		
		return getRecored(conn, sql, params);
	}

	@Override
	public Object getValue(Connection conn, AllDaoSuperOfSingle daoSingle, String methodName, Record dataBus,
			Context3 context) throws SQLException {
		CachePreparedMap invoke = DaoMethodReturnCache.invoke(daoSingle, methodName, new Object[] { context, dataBus });
		Object[] params = DaoMethodReturnCache.getAllValue(invoke, dataBus);
		String sql = invoke.getFinalSql();
		return getValue(conn, sql, params);
	}

	@Override
	public abstract TableDate getTable(Connection conn, String sql, Object... params) throws SQLException;

	@Override
	public abstract ColumnSet getSet(Connection conn, String sql, Object... params) throws SQLException;

	@Override
	public abstract Record getRecored(Connection conn, String sql, Object... params) throws SQLException;

	@Override
	public abstract Object getValue(Connection conn, String sql, Object... params) throws SQLException;
	
	/*
	 * 下面是org.apache.commons.dbutils.QueryRunner的API (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */

	/* (non-Javadoc)
	 * @see cn.wlh.util.base.adapter.dbutils.apache.DaoMethodReturnCacheInterface#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		return query.equals(obj);
	}

	/* (non-Javadoc)
	 * @see cn.wlh.util.base.adapter.dbutils.apache.DaoMethodReturnCacheInterface#batch(java.sql.Connection, java.lang.String, java.lang.Object[][])
	 */
	@Override
	public int[] batch(Connection conn, String sql, Object[][] params) throws SQLException {
		return query.batch(conn, sql, params);
	}

	/* (non-Javadoc)
	 * @see cn.wlh.util.base.adapter.dbutils.apache.DaoMethodReturnCacheInterface#batch(java.lang.String, java.lang.Object[][])
	 */
	@Override
	public int[] batch(String sql, Object[][] params) throws SQLException {
		return query.batch(sql, params);
	}

	/* (non-Javadoc)
	 * @see cn.wlh.util.base.adapter.dbutils.apache.DaoMethodReturnCacheInterface#getDataSource()
	 */
	@Override
	public DataSource getDataSource() {
		return query.getDataSource();
	}

	/* (non-Javadoc)
	 * @see cn.wlh.util.base.adapter.dbutils.apache.DaoMethodReturnCacheInterface#isPmdKnownBroken()
	 */
	@Override
	public boolean isPmdKnownBroken() {
		return query.isPmdKnownBroken();
	}

	/* (non-Javadoc)
	 * @see cn.wlh.util.base.adapter.dbutils.apache.DaoMethodReturnCacheInterface#toString()
	 */
	@Override
	public String toString() {
		return query.toString();
	}

	/* (non-Javadoc)
	 * @see cn.wlh.util.base.adapter.dbutils.apache.DaoMethodReturnCacheInterface#fillStatement(java.sql.PreparedStatement, java.lang.Object)
	 */
	@Override
	public void fillStatement(PreparedStatement stmt, Object... params) throws SQLException {
		query.fillStatement(stmt, params);
	}

	/* (non-Javadoc)
	 * @see cn.wlh.util.base.adapter.dbutils.apache.DaoMethodReturnCacheInterface#fillStatementWithBean(java.sql.PreparedStatement, java.lang.Object, java.beans.PropertyDescriptor[])
	 */
	@Override
	public void fillStatementWithBean(PreparedStatement stmt, Object bean, PropertyDescriptor[] properties)
			throws SQLException {
		query.fillStatementWithBean(stmt, bean, properties);
	}

	/* (non-Javadoc)
	 * @see cn.wlh.util.base.adapter.dbutils.apache.DaoMethodReturnCacheInterface#update(java.sql.Connection, java.lang.String)
	 */
	@Override
	public int update(Connection conn, String sql) throws SQLException {
		return query.update(conn, sql);
	}

	/* (non-Javadoc)
	 * @see cn.wlh.util.base.adapter.dbutils.apache.DaoMethodReturnCacheInterface#update(java.sql.Connection, java.lang.String, java.lang.Object)
	 */
	@Override
	public int update(Connection conn, String sql, Object param) throws SQLException {
		return query.update(conn, sql, param);
	}

	/* (non-Javadoc)
	 * @see cn.wlh.util.base.adapter.dbutils.apache.DaoMethodReturnCacheInterface#fillStatementWithBean(java.sql.PreparedStatement, java.lang.Object, java.lang.String)
	 */
	@Override
	public void fillStatementWithBean(PreparedStatement stmt, Object bean, String... propertyNames)
			throws SQLException {
		query.fillStatementWithBean(stmt, bean, propertyNames);
	}

	/* (non-Javadoc)
	 * @see cn.wlh.util.base.adapter.dbutils.apache.DaoMethodReturnCacheInterface#update(java.sql.Connection, java.lang.String, java.lang.Object)
	 */
	@Override
	public int update(Connection conn, String sql, Object... params) throws SQLException {
		return query.update(conn, sql, params);
	}

	/* (non-Javadoc)
	 * @see cn.wlh.util.base.adapter.dbutils.apache.DaoMethodReturnCacheInterface#update(java.lang.String)
	 */
	@Override
	public int update(String sql) throws SQLException {
		return query.update(sql);
	}

	/* (non-Javadoc)
	 * @see cn.wlh.util.base.adapter.dbutils.apache.DaoMethodReturnCacheInterface#update(java.lang.String, java.lang.Object)
	 */
	@Override
	public int update(String sql, Object param) throws SQLException {
		return query.update(sql, param);
	}

	/* (non-Javadoc)
	 * @see cn.wlh.util.base.adapter.dbutils.apache.DaoMethodReturnCacheInterface#update(java.lang.String, java.lang.Object)
	 */
	@Override
	public int update(String sql, Object... params) throws SQLException {
		return query.update(sql, params);
	}

	/* (non-Javadoc)
	 * @see cn.wlh.util.base.adapter.dbutils.apache.DaoMethodReturnCacheInterface#execute(java.sql.Connection, java.lang.String, java.lang.Object)
	 */
	@Override
	public int execute(Connection conn, String sql, Object... params) throws SQLException {
		return query.execute(conn, sql, params);
	}

	/* (non-Javadoc)
	 * @see cn.wlh.util.base.adapter.dbutils.apache.DaoMethodReturnCacheInterface#execute(java.lang.String, java.lang.Object)
	 */
	@Override
	public int execute(String sql, Object... params) throws SQLException {
		return query.execute(sql, params);
	}

	
}
