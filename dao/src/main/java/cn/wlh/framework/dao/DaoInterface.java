package cn.wlh.framework.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Map;

import javax.sql.DataSource;

/**
 * @author 吴灵辉
 * 一个DaoInterface对应一个数据源。
 * 子类最后不要做属性的操作。有线程安全问题。
 * 并对CRUD的操作进行封装
 */
public interface DaoInterface{
	DataSource getDataSource()throws SQLException;
	Connection getThreadConnection() throws SQLException;
	void closeThreadConnection() throws SQLException;
	/**
	 * @param sql
	 * @param con 连接
	 * @param returnClass 通过这个控制。执行那个方法。
	 * @param pars 设置的参数
	 * @return
	 */
	<T> T execute( String sql ,Connection con ,Class<T> returnClass , Object ... pars )throws SQLException;
	<T> T execute( String sql ,Connection con, Class<T> returnClass , Collection<Object> pars )throws SQLException;
	<T> T execute( String sql ,Connection con, Class<T> returnClass , Map<?,Object> pars )throws SQLException;
	/**获得默认conn执行。
	 * @param sql
	 * @param returnClass
	 * @param pars
	 * @return
	 * @throws SQLException 
	 */
	<T> T execute( String sql , Class<T> returnClass , Object ... pars ) throws  SQLException;
	<T> T execute( String sql , Class<T> returnClass , Collection<Object> pars )throws SQLException;
	<T> T execute( String sql , Class<T> returnClass , Map<?,Object> pars )throws SQLException;
	/////////////////////
	//
	//
	/////////////////////
	int update( String sql ,Connection con, Object ... pars )throws SQLException;
	int update( String sql ,Connection con, Collection<Object> pars )throws SQLException;
	int update( String sql ,Connection con, Map<?,Object> pars )throws SQLException;
	int update( String sql , Object ... pars )throws SQLException;
	int update( String sql , Collection<Object> pars )throws SQLException;
	int update( String sql , Map<?,Object> pars )throws SQLException;      
}
