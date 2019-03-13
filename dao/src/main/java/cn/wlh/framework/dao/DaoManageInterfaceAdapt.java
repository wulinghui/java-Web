package cn.wlh.framework.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Map;

import cn.wlh.util.base.RecordNewOfLog;

public abstract class DaoManageInterfaceAdapt extends RecordNewOfLog implements DaoManageInterface{
	public DaoManageInterfaceAdapt(char flag) {
		super(flag);
	}
	public DaoManageInterfaceAdapt() {
		this('w');
	}
	/* (non-Javadoc)
	 * @see cn.wlh.framework.dao.DaoManageInterface#closeDefaultThreadConnection()
	 */
	public void closeDefaultThreadConnection() throws SQLException {
		getDefaultDaoInterface().getThreadConnection();
	}
	public void closeThreadConnection(Object id) throws SQLException {
		getDaoInterface(id).closeThreadConnection();
	}
	
	
	public Connection getThreadConnection(Object id) throws SQLException {
		return getDaoInterface(id).getThreadConnection();
	}
	public Connection getDefaultThreadConnection() throws SQLException {
		return getDefaultDaoInterface().getThreadConnection();
	}
	/**
	 * @param sql
	 * @param con
	 * @param returnClass
	 * @param pars
	 * @return
	 * @throws SQLException
	 * @see cn.wlh.framework.dao.DaoInterface#execute(java.lang.String, java.sql.Connection, java.lang.Class, java.lang.Object[])
	 */
	public <T> T execute(String sql, Connection con, Class<T> returnClass, Object... pars) throws SQLException {
		return getDefaultDaoInterface().execute(sql, con, returnClass, pars);
	}

	/**
	 * @param sql
	 * @param con
	 * @param returnClass
	 * @param pars
	 * @return
	 * @throws SQLException
	 * @see cn.wlh.framework.dao.DaoInterface#execute(java.lang.String, java.sql.Connection, java.lang.Class, java.util.Collection)
	 */
	public <T> T execute(String sql, Connection con, Class<T> returnClass, Collection<Object> pars)
			throws SQLException {
		return getDefaultDaoInterface().execute(sql, con, returnClass, pars);
	}

	/**
	 * @param sql
	 * @param con
	 * @param returnClass
	 * @param pars
	 * @return
	 * @throws SQLException
	 * @see cn.wlh.framework.dao.DaoInterface#execute(java.lang.String, java.sql.Connection, java.lang.Class, java.util.Map)
	 */
	public <T> T execute(String sql, Connection con, Class<T> returnClass, Map<?, Object> pars) throws SQLException {
		return getDefaultDaoInterface().execute(sql, con, returnClass, pars);
	}

	/**
	 * @param sql
	 * @param returnClass
	 * @param pars
	 * @return
	 * @throws SQLException
	 * @see cn.wlh.framework.dao.DaoInterface#execute(java.lang.String, java.lang.Class, java.lang.Object[])
	 */
	public <T> T execute(String sql, Class<T> returnClass, Object... pars) throws SQLException {
		return getDefaultDaoInterface().execute(sql, returnClass, pars);
	}

	/**
	 * @param sql
	 * @param returnClass
	 * @param pars
	 * @return
	 * @throws SQLException
	 * @see cn.wlh.framework.dao.DaoInterface#execute(java.lang.String, java.lang.Class, java.util.Collection)
	 */
	public <T> T execute(String sql, Class<T> returnClass, Collection<Object> pars) throws SQLException {
		return getDefaultDaoInterface().execute(sql, returnClass, pars);
	}

	/**
	 * @param sql
	 * @param returnClass
	 * @param pars
	 * @return
	 * @throws SQLException
	 * @see cn.wlh.framework.dao.DaoInterface#execute(java.lang.String, java.lang.Class, java.util.Map)
	 */
	public <T> T execute(String sql, Class<T> returnClass, Map<?, Object> pars) throws SQLException {
		return getDefaultDaoInterface().execute(sql, returnClass, pars);
	}

	/**
	 * @param sql
	 * @param con
	 * @param pars
	 * @return
	 * @throws SQLException
	 * @see cn.wlh.framework.dao.DaoInterface#update(java.lang.String, java.sql.Connection, java.lang.Object[])
	 */
	public int update(String sql, Connection con, Object... pars) throws SQLException {
		return getDefaultDaoInterface().update(sql, con, pars);
	}

	/**
	 * @param sql
	 * @param con
	 * @param pars
	 * @return
	 * @throws SQLException
	 * @see cn.wlh.framework.dao.DaoInterface#update(java.lang.String, java.sql.Connection, java.util.Collection)
	 */
	public int update(String sql, Connection con, Collection<Object> pars) throws SQLException {
		return getDefaultDaoInterface().update(sql, con, pars);
	}

	/**
	 * @param sql
	 * @param con
	 * @param pars
	 * @return
	 * @throws SQLException
	 * @see cn.wlh.framework.dao.DaoInterface#update(java.lang.String, java.sql.Connection, java.util.Map)
	 */
	public int update(String sql, Connection con, Map<?, Object> pars) throws SQLException {
		return getDefaultDaoInterface().update(sql, con, pars);
	}

	/**
	 * @param sql
	 * @param pars
	 * @return
	 * @throws SQLException
	 * @see cn.wlh.framework.dao.DaoInterface#update(java.lang.String, java.lang.Object[])
	 */
	public int update(String sql, Object... pars) throws SQLException {
		return getDefaultDaoInterface().update(sql, pars);
	}

	/**
	 * @param sql
	 * @param pars
	 * @return
	 * @throws SQLException
	 * @see cn.wlh.framework.dao.DaoInterface#update(java.lang.String, java.util.Collection)
	 */
	public int update(String sql, Collection<Object> pars) throws SQLException {
		return getDefaultDaoInterface().update(sql, pars);
	}

	/**
	 * @param sql
	 * @param pars
	 * @return
	 * @throws SQLException
	 * @see cn.wlh.framework.dao.DaoInterface#update(java.lang.String, java.util.Map)
	 */
	public int update(String sql, Map<?, Object> pars) throws SQLException {
		return getDefaultDaoInterface().update(sql, pars);
	}


	public <T> T execute(Object daoInterfaceId, String sql, Connection con, Class<T> returnClass, Object[] pars)
			throws SQLException {
		return getDaoInterface(daoInterfaceId).execute(sql, con, returnClass, new Object[] {});
	}

	public <T> T execute(Object daoInterfaceId, String sql, Connection con, Class<T> returnClass,
			Collection<Object> pars) throws SQLException {
		return getDaoInterface(daoInterfaceId).execute(sql, con, returnClass, pars);
	}

	public <T> T execute(Object daoInterfaceId, String sql, Connection con, Class<T> returnClass, Map<?, Object> pars)
			throws SQLException {
		return getDaoInterface(daoInterfaceId).execute(sql, con, returnClass, pars);
	}

	public <T> T execute(Object daoInterfaceId, String sql, Class<T> returnClass, Object... pars) throws SQLException {
		return getDaoInterface(daoInterfaceId).execute(sql,  returnClass, pars);
	}

	public <T> T execute(Object daoInterfaceId, String sql, Class<T> returnClass, Collection<Object> pars)
			throws SQLException {
		return getDaoInterface(daoInterfaceId).execute(sql,  returnClass, pars);
	}

	public <T> T execute(Object daoInterfaceId, String sql, Class<T> returnClass, Map<?, Object> pars)
			throws SQLException {
		return getDaoInterface(daoInterfaceId).execute(sql,  returnClass, pars);
	}

	public int update(Object daoInterfaceId, String sql, Connection con, Object... pars) throws SQLException {
		return getDaoInterface(daoInterfaceId).update(sql, con  , pars);
	}

	public int update(Object daoInterfaceId, String sql, Connection con, Collection<Object> pars) throws SQLException {
		return getDaoInterface(daoInterfaceId).update(sql, con  , pars);
	}

	public int update(Object daoInterfaceId, String sql, Connection con, Map<?, Object> pars) throws SQLException {
		return getDaoInterface(daoInterfaceId).update(sql, con  , pars);
	}

	public int update(Object daoInterfaceId, String sql, Object... pars) throws SQLException {
		return getDaoInterface(daoInterfaceId).update(sql  , pars);
	}

	public int update(Object daoInterfaceId, String sql, Collection<Object> pars) throws SQLException {
		return getDaoInterface(daoInterfaceId).update(sql  , pars);
	}

	public int update(Object daoInterfaceId, String sql, Map<?, Object> pars) throws SQLException {
		return getDaoInterface(daoInterfaceId).update(sql  , pars);
	}
}
