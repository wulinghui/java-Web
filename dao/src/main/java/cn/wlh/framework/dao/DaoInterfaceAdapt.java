package cn.wlh.framework.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Map;

import javax.sql.DataSource;

import cn.wlh.util.base.RecordNewOfLog;

public abstract class DaoInterfaceAdapt extends RecordNewOfLog implements DaoInterface {
	final DataSource dataSource;
	public DaoInterfaceAdapt(DataSource dataSource) {
		this( 'd' , dataSource);
	}
	public DaoInterfaceAdapt(char flag, DataSource dataSource) {
		super(flag);
		this.dataSource = dataSource;
	}
	public DataSource getDataSource() {
		return dataSource;
	}
	/** 这里只是简单的从dataSource里面获得。
	 * @return
	 * @throws SQLException
	 */
	protected  Connection getConnection() throws SQLException {
		return dataSource.getConnection();
	}

	public <T> T execute(String sql, Class<T> returnClass, Object... pars) throws SQLException {
		return execute(sql, getConnection(), returnClass, pars);
	}

	public <T> T execute(String sql, Class<T> returnClass, Collection<Object> pars) throws SQLException {
		return execute(sql, getConnection(), returnClass, pars);
	}

	public <T> T execute(String sql, Class<T> returnClass, Map<?, Object> pars) throws SQLException {
		return execute(sql, getConnection(), returnClass, pars);
	}
	
	
	
	public int update(String sql, Connection con, Object... pars) throws SQLException {
		return execute(sql, con,  int.class, pars);
	}

	public int update(String sql, Connection con, Collection<Object> pars) throws SQLException {
		return execute(sql, con,  int.class, pars);
	}

	public int update(String sql, Connection con, Map<?, Object> pars) throws SQLException {
		return execute(sql, con,  int.class, pars);
	}

	public int update(String sql, Object... pars) throws SQLException {
		return execute(sql, int.class, pars);
	}

	public int update(String sql, Collection<Object> pars) throws SQLException {
		return execute(sql, int.class, pars);
	}

	public int update(String sql, Map<?, Object> pars) throws SQLException {
		return execute(sql, int.class, pars);
	}

}
