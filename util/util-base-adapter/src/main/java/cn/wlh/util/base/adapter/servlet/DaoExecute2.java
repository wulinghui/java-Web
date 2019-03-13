package cn.wlh.util.base.adapter.servlet;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.sql.DataSource;

import org.apache.commons.dbutils.ResultSetHandler;

import cn.wlh.util.base.adapter.dbutils.apache.DaoMethodReturnCache;

/**
 * @author 吴灵辉
 * 固定泛型..
 * 实现Context2执行dao方法获得结果集的绑定
 * 
 */
public class DaoExecute2  extends DaoMethodReturnCache<Context2	, DataBus2>{
	public DaoExecute2(DataSource dataSource) {
		super(dataSource);
	}
	/*
	 * 
	 * 里面其他的方法都调用的这个口子.所以控制这4个方法就控制了其他的方法了.
	 * 
	 * 
	 * 
	 */
	@Override
	public <T> T query(Connection conn, AllDaoSuperOfSingle daoSingle, String methodName, DataBus2 dataBus,
			ResultSetHandler<T> rsh, Context2 context) throws SQLException {
		T update = super.query(conn, daoSingle, methodName, dataBus, rsh, context);
		MyContext con = (MyContext) context;
		con.registerDao( keyRule(daoSingle, methodName) , update);
		return update; 
	}

	@Override
	public <T> T query(AllDaoSuperOfSingle daoSingle, String methodName, DataBus2 dataBus, ResultSetHandler<T> rsh,
			Context2 context) throws SQLException {
		T update = super.query(daoSingle, methodName, dataBus, rsh, context);
		MyContext con = (MyContext) context;
		con.registerDao( keyRule(daoSingle, methodName) , update);
		return update;
	}

	@Override
	public int update(Connection conn, AllDaoSuperOfSingle daoSingle, String methodName, DataBus2 dataBus,
			Context2 context) throws SQLException {
		int update = super.update(conn, daoSingle, methodName, dataBus, context);
		MyContext con = (MyContext) context;
		con.registerDao( keyRule(daoSingle, methodName) , update);
		return update;
	}

	@Override
	public int update(AllDaoSuperOfSingle daoSingle, String methodName, DataBus2 dataBus, Context2 context)
			throws SQLException {
		int update = super.update(daoSingle, methodName, dataBus, context);
		MyContext con = (MyContext) context;
		con.registerDao( keyRule(daoSingle, methodName) , update);
		return update;
	}
	/** 获得方法的规则..
	 * @param daoSingle --类名和表名一致
	 * @param methodName
	 * @return tableName-methodName
	 */
	protected String keyRule(AllDaoSuperOfSingle daoSingle, String methodName) {
		return daoSingle.getClass().getSimpleName().toUpperCase() + "-" + methodName;
	}
	
	/**
	 * @author 吴灵辉
	 * 在系统new Context2()时.
	 * 实际new MyContext();
	 * 但给用户给Context2..
	 */
	public static class MyContext extends Context2{

		public MyContext(ServletRequest source) {
			super(source);
		}

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
		public Map<String,Object> getBizMap() {
			return super.businessMap;
		}
		public Map<String,Object> getDaoMap() {
			return super.daoMap;
		}
	}
}
