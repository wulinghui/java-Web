package cn.wlh.framework.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Map;

import javax.sql.DataSource;

/**
 * @author 吴灵辉
 * 管理DaoInterface。
 */
public interface DaoManageInterface {
	DaoInterface getDefaultDaoInterface();
	//为了提供遍历的时候。
	DaoInterface[] getDefaultDaoInterfaces();
	DaoInterface getDaoInterface(Object id);
	void putDaoInterface(Object id,DaoInterface DaoInterface);
	Connection getDefaultThreadConnection() throws SQLException;
	void closeDefaultThreadConnection() throws SQLException;
	/**获得线程级别的Conn
	 *
	 * 这里有个问题，如果第一个用了'a' , 第二次用 'b'。b就不好用了用的还是a。
	 * 这里就需要使用getDaoInterface(id).getDataSource()，--把static 变成实例
	 * 所以通过id获得线程级别不同的Conntecion标识。
	 * @param id
	 * @return
	 * @throws SQLException
	 */
	Connection getThreadConnection(Object id) throws SQLException;
    /**关闭线程里面的Conn。其他的jdbc都被关闭了。
     * @param id 
     */ 
    void closeThreadConnection(Object id) throws SQLException;
	//管
	//理
	//通过daoInterfaceId找到daoInterface再执行。
    <T> T execute(Object daoInterfaceId, String sql ,Connection con ,Class<T> returnClass , Object ... pars )throws SQLException;
	<T> T execute(Object daoInterfaceId, String sql ,Connection con, Class<T> returnClass , Collection<Object> pars )throws SQLException;
	<T> T execute(Object daoInterfaceId, String sql ,Connection con, Class<T> returnClass , Map<?,Object> pars )throws SQLException;
	<T> T execute(Object daoInterfaceId, String sql , Class<T> returnClass , Object ... pars ) throws  SQLException;
	<T> T execute(Object daoInterfaceId, String sql , Class<T> returnClass , Collection<Object> pars )throws SQLException;
	<T> T execute(Object daoInterfaceId, String sql , Class<T> returnClass , Map<?,Object> pars )throws SQLException;
	int update(Object daoInterfaceId, String sql ,Connection con, Object ... pars )throws SQLException;
	int update(Object daoInterfaceId, String sql ,Connection con, Collection<Object> pars )throws SQLException;
	int update(Object daoInterfaceId, String sql ,Connection con, Map<?,Object> pars )throws SQLException;
	int update(Object daoInterfaceId, String sql , Object ... pars )throws SQLException;
	int update(Object daoInterfaceId, String sql , Collection<Object> pars )throws SQLException;
	int update(Object daoInterfaceId, String sql , Map<?,Object> pars )throws SQLException;
	//默认的
	<T> T execute( String sql ,Connection con ,Class<T> returnClass , Object ... pars )throws SQLException;
	<T> T execute( String sql ,Connection con, Class<T> returnClass , Collection<Object> pars )throws SQLException;
	<T> T execute( String sql ,Connection con, Class<T> returnClass , Map<?,Object> pars )throws SQLException;
	<T> T execute( String sql , Class<T> returnClass , Object ... pars ) throws  SQLException;
	<T> T execute( String sql , Class<T> returnClass , Collection<Object> pars )throws SQLException;
	<T> T execute( String sql , Class<T> returnClass , Map<?,Object> pars )throws SQLException;
	int update( String sql ,Connection con, Object ... pars )throws SQLException;
	int update( String sql ,Connection con, Collection<Object> pars )throws SQLException;
	int update( String sql ,Connection con, Map<?,Object> pars )throws SQLException;
	int update( String sql , Object ... pars )throws SQLException;
	int update( String sql , Collection<Object> pars )throws SQLException;
	int update( String sql , Map<?,Object> pars )throws SQLException;
}
