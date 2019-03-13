package cn.wlh.util.base.adapter.dbutils.apache;

import java.beans.PropertyDescriptor;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.sql.DataSource;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.ColumnListHandler;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import cn.wlh.util.base.adapter.dbutils.apache.DaoMethodReturnCache.AllDaoSuperOfSingle;
import cn.wlh.util.base.adapter.dbutils.apache.DaoMethodReturnCache.CachePreparedMap;

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
public abstract class DaoMethodReturnCache2<K1, V1, K2, V2, Context extends Map<K1, V1>, DataBus extends Map<K2, V2>, Table extends List<?>, Set extends List<V2>, Recored extends DataBus, Value extends V2> implements DaoMethodReturnCacheInterface<K1, V1, K2, V2, Context, DataBus, Table, Set, Recored, Value> {
	protected QueryRunner query;
	//  子类...
	// final static MapListHandler table = new MapListHandler();
	// final static ColumnListHandler<Object> set = new
	// ColumnListHandler<Object>(1);
	// final static MapHandler record = new MapHandler();
	// final static ScalarHandler value = new ScalarHandler(1);

	public DaoMethodReturnCache2(DataSource dataSource) {
		Objects.requireNonNull(dataSource);
		query = new QueryRunner(dataSource);
	}
	
	/* (non-Javadoc)
	 * @see cn.wlh.util.base.adapter.dbutils.apache.DaoMethodReturnCacheInterface#getTable(cn.wlh.util.base.adapter.dbutils.apache.DaoMethodReturnCache.AllDaoSuperOfSingle, java.lang.String, DataBus, Context)
	 */
	@Override
	public Table getTable(AllDaoSuperOfSingle daoSingle, String methodName, DataBus dataBus, Context context)
			throws SQLException {
		return getTable(query.getDataSource().getConnection(), daoSingle, methodName, dataBus, context);
	}

	/* (non-Javadoc)
	 * @see cn.wlh.util.base.adapter.dbutils.apache.DaoMethodReturnCacheInterface#getSet(cn.wlh.util.base.adapter.dbutils.apache.DaoMethodReturnCache.AllDaoSuperOfSingle, java.lang.String, DataBus, Context)
	 */
	@Override
	public Set getSet(AllDaoSuperOfSingle daoSingle, String methodName, DataBus dataBus, Context context)
			throws SQLException {
		return getSet(query.getDataSource().getConnection(), daoSingle, methodName, dataBus, context);
	}

	/* (non-Javadoc)
	 * @see cn.wlh.util.base.adapter.dbutils.apache.DaoMethodReturnCacheInterface#getRecord(cn.wlh.util.base.adapter.dbutils.apache.DaoMethodReturnCache.AllDaoSuperOfSingle, java.lang.String, DataBus, Context)
	 */
	@Override
	public Recored getRecord(AllDaoSuperOfSingle daoSingle, String methodName, DataBus dataBus, Context context)
			throws SQLException {
		return getRecord(query.getDataSource().getConnection(), daoSingle, methodName, dataBus, context);
	}

	/* (non-Javadoc)
	 * @see cn.wlh.util.base.adapter.dbutils.apache.DaoMethodReturnCacheInterface#getValue(cn.wlh.util.base.adapter.dbutils.apache.DaoMethodReturnCache.AllDaoSuperOfSingle, java.lang.String, DataBus, Context)
	 */
	@Override
	public Value getValue(AllDaoSuperOfSingle daoSingle, String methodName, DataBus dataBus, Context context)
			throws SQLException {
		return getValue(query.getDataSource().getConnection(), daoSingle, methodName, dataBus, context);
	}
	public int update(AllDaoSuperOfSingle daoSingle, String methodName, DataBus dataBus, Context context) throws SQLException {
		return update(query.getDataSource().getConnection(), daoSingle, methodName, dataBus, context);
	}
	//////////////////////////////////////////////////////////////////////////////////
	// connection
	/////////////////////////////////////////////////////////////////////////////////
	public int update(Connection conn, AllDaoSuperOfSingle daoSingle, String methodName, DataBus dataBus, Context context)
			throws SQLException {
		CachePreparedMap invoke = DaoMethodReturnCache.invoke(daoSingle, methodName, new Object[] { context, dataBus });
		Object[] params = DaoMethodReturnCache.getAllValue(invoke, dataBus);
		String sql = invoke.getFinalSql();
		return query.update(sql, params);
	}

	
	/* (non-Javadoc)
	 * @see cn.wlh.util.base.adapter.dbutils.apache.DaoMethodReturnCacheInterface#getTable(java.sql.Connection, cn.wlh.util.base.adapter.dbutils.apache.DaoMethodReturnCache.AllDaoSuperOfSingle, java.lang.String, DataBus, Context)
	 */
	@Override
	public Table getTable(Connection conn, AllDaoSuperOfSingle daoSingle, String methodName, DataBus dataBus,
			Context context) throws SQLException {
		CachePreparedMap invoke = DaoMethodReturnCache.invoke(daoSingle, methodName, new Object[] { context, dataBus });
		Object[] params = DaoMethodReturnCache.getAllValue(invoke, dataBus);
		String sql = invoke.getFinalSql();
		
		return getTable(conn, sql, params);
	}

	/* (non-Javadoc)
	 * @see cn.wlh.util.base.adapter.dbutils.apache.DaoMethodReturnCacheInterface#getSet(java.sql.Connection, cn.wlh.util.base.adapter.dbutils.apache.DaoMethodReturnCache.AllDaoSuperOfSingle, java.lang.String, DataBus, Context)
	 */
	@Override
	public Set getSet(Connection conn, AllDaoSuperOfSingle daoSingle, String methodName, DataBus dataBus,
			Context context) throws SQLException{
		CachePreparedMap invoke = DaoMethodReturnCache.invoke(daoSingle, methodName, new Object[] { context, dataBus });
		Object[] params = DaoMethodReturnCache.getAllValue(invoke, dataBus);
		String sql = invoke.getFinalSql();
		return getSet(conn, sql, params);
	}

	/* (non-Javadoc)
	 * @see cn.wlh.util.base.adapter.dbutils.apache.DaoMethodReturnCacheInterface#getRecord(java.sql.Connection, cn.wlh.util.base.adapter.dbutils.apache.DaoMethodReturnCache.AllDaoSuperOfSingle, java.lang.String, DataBus, Context)
	 */
	@Override
	public  Recored getRecord(Connection conn, AllDaoSuperOfSingle daoSingle, String methodName,
			DataBus dataBus, Context context) throws SQLException{
		CachePreparedMap invoke = DaoMethodReturnCache.invoke(daoSingle, methodName, new Object[] { context, dataBus });
		Object[] params = DaoMethodReturnCache.getAllValue(invoke, dataBus);
		String sql = invoke.getFinalSql();
		
		return getRecored(conn, sql, params);
	}

	/* (non-Javadoc)
	 * @see cn.wlh.util.base.adapter.dbutils.apache.DaoMethodReturnCacheInterface#getValue(java.sql.Connection, cn.wlh.util.base.adapter.dbutils.apache.DaoMethodReturnCache.AllDaoSuperOfSingle, java.lang.String, DataBus, Context)
	 */
	@Override
	public  Value getValue(Connection conn, AllDaoSuperOfSingle daoSingle, String methodName, DataBus dataBus,
			Context context) throws SQLException{
		CachePreparedMap invoke = DaoMethodReturnCache.invoke(daoSingle, methodName, new Object[] { context, dataBus });
		Object[] params = DaoMethodReturnCache.getAllValue(invoke, dataBus);
		String sql = invoke.getFinalSql();
		return getValue(conn, sql, params);
	}

	/* (non-Javadoc)
	 * @see cn.wlh.util.base.adapter.dbutils.apache.DaoMethodReturnCacheInterface#getTable(java.sql.Connection, java.lang.String, java.lang.Object)
	 */
	@Override
	public abstract Table getTable(Connection conn, String sql, Object... params) throws SQLException;

	/* (non-Javadoc)
	 * @see cn.wlh.util.base.adapter.dbutils.apache.DaoMethodReturnCacheInterface#getSet(java.sql.Connection, java.lang.String, java.lang.Object)
	 */
	@Override
	public abstract Set getSet(Connection conn, String sql, Object... params) throws SQLException;

	/* (non-Javadoc)
	 * @see cn.wlh.util.base.adapter.dbutils.apache.DaoMethodReturnCacheInterface#getRecored(java.sql.Connection, java.lang.String, java.lang.Object)
	 */
	@Override
	public abstract Recored getRecored(Connection conn, String sql, Object... params) throws SQLException;

	/* (non-Javadoc)
	 * @see cn.wlh.util.base.adapter.dbutils.apache.DaoMethodReturnCacheInterface#getValue(java.sql.Connection, java.lang.String, java.lang.Object)
	 */
	@Override
	public abstract Value getValue(Connection conn, String sql, Object... params) throws SQLException;


	// protected void //用泛型...
	// public static interface Handler<T>{
	//
	// }
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
