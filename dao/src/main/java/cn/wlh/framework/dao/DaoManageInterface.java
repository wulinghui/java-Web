package cn.wlh.framework.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Map;

import javax.sql.DataSource;

/**
 * @author �����
 * ����DaoInterface��
 */
public interface DaoManageInterface {
	DaoInterface getDefaultDaoInterface();
	//Ϊ���ṩ������ʱ��
	DaoInterface[] getDefaultDaoInterfaces();
	DaoInterface getDaoInterface(Object id);
	void putDaoInterface(Object id,DaoInterface DaoInterface);
	Connection getDefaultThreadConnection() throws SQLException;
	void closeDefaultThreadConnection() throws SQLException;
	/**����̼߳����Conn
	 *
	 * �����и����⣬�����һ������'a' , �ڶ����� 'b'��b�Ͳ��������õĻ���a��
	 * �������Ҫʹ��getDaoInterface(id).getDataSource()��--��static ���ʵ��
	 * ����ͨ��id����̼߳���ͬ��Conntecion��ʶ��
	 * @param id
	 * @return
	 * @throws SQLException
	 */
	Connection getThreadConnection(Object id) throws SQLException;
    /**�ر��߳������Conn��������jdbc�����ر��ˡ�
     * @param id 
     */ 
    void closeThreadConnection(Object id) throws SQLException;
	//��
	//��
	//ͨ��daoInterfaceId�ҵ�daoInterface��ִ�С�
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
	//Ĭ�ϵ�
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
