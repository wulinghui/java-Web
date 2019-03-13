package cn.wlh.framework.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Map;

import javax.sql.DataSource;

/**
 * @author �����
 * һ��DaoInterface��Ӧһ������Դ��
 * �������Ҫ�����ԵĲ��������̰߳�ȫ���⡣
 * ����CRUD�Ĳ������з�װ
 */
public interface DaoInterface{
	DataSource getDataSource()throws SQLException;
	Connection getThreadConnection() throws SQLException;
	void closeThreadConnection() throws SQLException;
	/**
	 * @param sql
	 * @param con ����
	 * @param returnClass ͨ��������ơ�ִ���Ǹ�������
	 * @param pars ���õĲ���
	 * @return
	 */
	<T> T execute( String sql ,Connection con ,Class<T> returnClass , Object ... pars )throws SQLException;
	<T> T execute( String sql ,Connection con, Class<T> returnClass , Collection<Object> pars )throws SQLException;
	<T> T execute( String sql ,Connection con, Class<T> returnClass , Map<?,Object> pars )throws SQLException;
	/**���Ĭ��connִ�С�
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
