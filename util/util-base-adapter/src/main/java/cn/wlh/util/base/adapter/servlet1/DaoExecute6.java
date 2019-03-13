package cn.wlh.util.base.adapter.servlet1;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.sql.DataSource;

import org.apache.commons.dbutils.handlers.ColumnListHandler;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import cn.wlh.util.base.adapter.dbutils.apache.DaoMethodReturnCache.AllDaoSuperOfSingle;

/**
 * @author 吴灵辉 固定泛型.. 实现Context2执行dao方法获得结果集的绑定
 * 
 */
// <K1, V1, K2, V2, Context extends Map<K1, V1>, DataBus extends Map<K2, V2>,
// Table extends List<DataBus>, Set extends List<V2>, Recored extends DataBus,
// Value extends V2> implements DaoMethodReturnCacheInterface<K1, V1, K2, V2,
// Context, DataBus, Table, Set, Recored, Value> {

public class DaoExecute6 extends DaoMethodReturnCache3 {

	public DaoExecute6(DataSource dataSource) {
		super(dataSource);
	}

	/**
	 * 获得方法的规则..
	 * 
	 * @param daoSingle
	 *            --类名和表名一致
	 * @param methodName
	 * @return tableName-methodName
	 */
	protected String keyRule(AllDaoSuperOfSingle daoSingle, String methodName) {
		return daoSingle.getClass().getSimpleName().toUpperCase() + "-" + methodName;
	}

	@Override
	public int update(Connection conn, AllDaoSuperOfSingle daoSingle, String methodName, Record dataBus,
			Context3 context) throws SQLException {
		int update = super.update(conn, daoSingle, methodName, dataBus, context);
		MyContext con = (MyContext) context;
		con.registerDao(keyRule(daoSingle, methodName), update);
		return update;
	}

	@Override
	public TableDate getTable(Connection conn, AllDaoSuperOfSingle daoSingle, String methodName, Record dataBus,
			Context3 context) throws SQLException {
		TableDate table2 = super.getTable(conn, daoSingle, methodName, dataBus, context);
		MyContext con = (MyContext) context;
		con.registerDao(keyRule(daoSingle, methodName), table2);
		return table2;
	}

	@Override
	public ColumnSet getSet(Connection conn, AllDaoSuperOfSingle daoSingle, String methodName, Record dataBus,
			Context3 context) throws SQLException {
		ColumnSet set2 = super.getSet(conn, daoSingle, methodName, dataBus, context);
		MyContext con = (MyContext) context;
		con.registerDao(keyRule(daoSingle, methodName), set2);
		return set2;
	}

	@Override
	public Record getRecord(Connection conn, AllDaoSuperOfSingle daoSingle, String methodName, Record dataBus,
			Context3 context) throws SQLException {
		Record record2 = super.getRecord(conn, daoSingle, methodName, dataBus, context);
		MyContext con = (MyContext) context;
		con.registerDao(keyRule(daoSingle, methodName), record2);
		return record2;
	}

	@Override
	public Object getValue(Connection conn, AllDaoSuperOfSingle daoSingle, String methodName, Record dataBus,
			Context3 context) throws SQLException {
		Object value2 = super.getValue(conn, daoSingle, methodName, dataBus, context);
		MyContext con = (MyContext) context;
		con.registerDao(keyRule(daoSingle, methodName), value2);
		return value2;
	}

	// 子类...
	final static MapListHandler table = new MapListHandler();
	final static ColumnListHandler<Object> set = new ColumnListHandler<Object>(1);
	final static MapHandler record = new MapHandler();
	final static ScalarHandler value = new ScalarHandler(1);

	@Override
	public TableDate getTable(Connection conn, String sql, Object... params) throws SQLException {
		List<Map<String, Object>> query2 = super.query.query(conn, sql, table, params);
		TableDate set = new TableDate();
//		set.addAll(query2);
		Record db;
		for (Map<String, Object> map : query2) {
			db = new Record();
			db.putAll(map);
			set.add(db);
		}
		return set;
	}

	@Override
	public ColumnSet getSet(Connection conn, String sql, Object... params) throws SQLException {
		List<Object> query2 = super.query.query(conn, sql, set, params);
		ColumnSet set = new ColumnSet();
		set.addAll(query2);
		return set;
	}

	@Override
	public Record getRecored(Connection conn, String sql, Object... params) throws SQLException {
		Map<String, Object> query2 = super.query.query(conn, sql, record, params);
		Record db = new Record();
		db.putAll(query2);
		;
		return db;
	}

	@Override
	public Object getValue(Connection conn, String sql, Object... params) throws SQLException {
		return super.query.query(conn, sql, value, params);
	}

	/////////////////////////////////////////////////////////////
	// 登记
	////////////////////////////////////////////////////////////

	/**
	 * @author 吴灵辉 在系统new Context2()时. 实际new MyContext(); 但给用户给Context2..
	 */
	public static class MyContext extends Context3 {


		@Override
		public void registerDao(String key, Object value) {
			super.registerDao(key, value);
		}

		@Override
		public void clearBiz() {
			super.clearBiz();
		}

		@Override
		public void clearDao() {
			super.clearDao();
		}

		public Map<String, Object> getBizMap() {
			return super.businessMap;
		}

		public Map<String, Object> getDaoMap() {
			return super.daoMap;
		}
	}

}
