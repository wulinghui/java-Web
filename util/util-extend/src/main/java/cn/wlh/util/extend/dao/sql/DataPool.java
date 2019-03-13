package cn.wlh.util.extend.dao.sql;

import java.sql.Array;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.NClob;
import java.sql.PreparedStatement;
import java.sql.SQLClientInfoException;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Savepoint;
import java.sql.Statement;
import java.sql.Struct;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Executor;

import cn.wlh.util.extend.wrap.WrapObject;

/**
 * @author wlh
 * 数据连接池,一个数据库对应一个对象
 */
public class DataPool {
	public  ThreadLocal<WrapConnection> connectionHolder = new ThreadLocal<WrapConnection>();
	protected List<WrapConnection> connPool = new ArrayList<WrapConnection>();
	protected int index = 0; // 操作数组的指针
	
	public DataPool(String url,String user,String password,int count) throws SQLException{
		// 异常自动结束
		int i = 0;
		do {
		   connPool.add( new WrapConnection( DriverManager.getConnection( url , user , password ) )  );
		   i++;
		}while( i > count );
	}
	
	/**
	 * 获得Connection的包装类
	 * @param conn,要获得的conn
	 * @return 在pool的下标
	 */
	public WrapConnection getWrapConnection() {
		WrapConnection connection = connectionHolder.get();
		int index;
		if (null == connection) {
			index = getIndex();
			connection = connPool.get(index);
			boolean idle = connection.isIdle;
			synchronized (connection) {
				if ( !idle ) {
					notifyAll();// 唤醒所有的使用这个pool的线程，叫他们尽快使用这个线程
//					Thread.sleep(1000);
				}
				idle = false;
			}
			connectionHolder.set(connection);
		}
		return connection;
	}
	/**
	 * 销毁所有连接,确保类卸载
	 * @throws SQLException 
	 */
	public void destroy() throws SQLException {
		int len = connPool.size();
		for (int i = 0; i < len; i++) {
			connPool.get(i).destroy();;
		}
		connPool = null;
		System.gc();
	}
	/** 
	 * 
	 * 包装类...保证conn不被close
	 * 
	 * 
	 * @author wlh
	 *
	 */
	protected final class WrapConnection extends WrapObject<Connection> implements Connection{
		/**
		 * 是否空闲,true空闲
		 */
		private boolean isIdle = true;
		public WrapConnection(Connection conn) {
			super.k = conn;
		}
		@Override
		protected Connection newObject() {
			return null;
		}
		
		/** 真的close */
		protected void destroy() throws SQLException {
			super.k.close();
		}
		@Override
		public <T> T unwrap(Class<T> iface) throws SQLException {
			return super.k.unwrap(iface);
		}
		@Override
		public boolean isWrapperFor(Class<?> iface) throws SQLException {
			return super.k.isWrapperFor(iface);
		}
		@Override
		public Statement createStatement() throws SQLException {
			return super.k.createStatement();
		}
		@Override
		public PreparedStatement prepareStatement(String sql) throws SQLException {
			return super.k.prepareStatement(sql);
		}
		@Override
		public CallableStatement prepareCall(String sql) throws SQLException {
			return super.k.prepareCall(sql);
		}
		@Override
		public String nativeSQL(String sql) throws SQLException {
			return super.k.nativeSQL(sql);
		}
		@Override
		public void setAutoCommit(boolean autoCommit) throws SQLException {
			super.k.setAutoCommit(autoCommit);
		}
		@Override
		public boolean getAutoCommit() throws SQLException {
			return super.k.getAutoCommit();
		}
		@Override
		public void commit() throws SQLException {
			super.k.commit();
		}
		@Override
		public void rollback() throws SQLException {
			super.k.rollback();
		}
		@Override//TODO close  千万不要调用Conn的close();
		public void close() throws SQLException {
			synchronized (this) {
				this.isIdle = false;
			}
			connectionHolder.remove();//从线程中移除
		}
		@Override
		public boolean isClosed() throws SQLException {
			return super.k.isClosed();
		}
		@Override
		public DatabaseMetaData getMetaData() throws SQLException {
			return super.k.getMetaData();
		}
		@Override
		public void setReadOnly(boolean readOnly) throws SQLException {
			super.k.setReadOnly(readOnly);
		}
		@Override
		public boolean isReadOnly() throws SQLException {
			return super.k.isReadOnly();
		}
		@Override
		public void setCatalog(String catalog) throws SQLException {
			super.k.setCatalog(catalog);
		}
		@Override
		public String getCatalog() throws SQLException {
			return super.k.getCatalog();
		}
		@Override
		public void setTransactionIsolation(int level) throws SQLException {
			super.k.setTransactionIsolation(level);
		}
		@Override
		public int getTransactionIsolation() throws SQLException {
			return super.k.getTransactionIsolation();
		}
		@Override
		public SQLWarning getWarnings() throws SQLException {
			return super.k.getWarnings();
		}
		@Override
		public void clearWarnings() throws SQLException {
			super.k.clearWarnings();
		}
		@Override
		public Statement createStatement(int resultSetType, int resultSetConcurrency) throws SQLException {
			return super.k.createStatement(resultSetType, resultSetConcurrency);
		}
		@Override
		public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency)
				throws SQLException {
			return super.k.prepareStatement(sql, resultSetType, resultSetConcurrency);
		}
		@Override
		public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency)
				throws SQLException {
			return super.k.prepareCall(sql, resultSetType, resultSetConcurrency);
		}
		@Override
		public Map<String, Class<?>> getTypeMap() throws SQLException {
			return super.k.getTypeMap();
		}
		@Override
		public void setTypeMap(Map<String, Class<?>> map) throws SQLException {
			super.k.setTypeMap(map);
		}
		@Override
		public void setHoldability(int holdability) throws SQLException {
			super.k.setHoldability(holdability);
		}
		@Override
		public int getHoldability() throws SQLException {
			return super.k.getHoldability();
		}
		@Override
		public Savepoint setSavepoint() throws SQLException {
			return super.k.setSavepoint();
		}
		@Override
		public Savepoint setSavepoint(String name) throws SQLException {
			return super.k.setSavepoint(name);
		}
		@Override
		public void rollback(Savepoint savepoint) throws SQLException {
			super.k.rollback(savepoint);
		}
		@Override
		public void releaseSavepoint(Savepoint savepoint) throws SQLException {
			super.k.releaseSavepoint(savepoint);
		}
		@Override
		public Statement createStatement(int resultSetType, int resultSetConcurrency, int resultSetHoldability)
				throws SQLException {
			return super.k.createStatement(resultSetType, resultSetConcurrency, resultSetHoldability);
		}
		@Override
		public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency,
				int resultSetHoldability) throws SQLException {
			return super.k.prepareStatement(sql, resultSetType, resultSetConcurrency);
		}
		@Override
		public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency,
				int resultSetHoldability) throws SQLException {
			return super.k.prepareCall(sql, resultSetType, resultSetConcurrency);
		}
		@Override
		public PreparedStatement prepareStatement(String sql, int autoGeneratedKeys) throws SQLException {
			return super.k.prepareStatement(sql, autoGeneratedKeys);
		}
		@Override
		public PreparedStatement prepareStatement(String sql, int[] columnIndexes) throws SQLException {
			return super.k.prepareStatement(sql, columnIndexes);
		}
		@Override
		public PreparedStatement prepareStatement(String sql, String[] columnNames) throws SQLException {
			return super.k.prepareStatement(sql, columnNames);
		}
		@Override
		public Clob createClob() throws SQLException {
			return super.k.createClob();
		}
		@Override
		public Blob createBlob() throws SQLException {
			return super.k.createBlob();
		}
		@Override
		public NClob createNClob() throws SQLException {
			return super.k.createNClob();
		}
		@Override
		public SQLXML createSQLXML() throws SQLException {
			return super.k.createSQLXML();
		}
		@Override
		public boolean isValid(int timeout) throws SQLException {
			return super.k.isValid(timeout);
		}
		@Override
		public void setClientInfo(String name, String value) throws SQLClientInfoException {
			super.k.setClientInfo(name, value);
		}
		@Override
		public void setClientInfo(Properties properties) throws SQLClientInfoException {
			super.k.setClientInfo(properties);
		}
		@Override
		public String getClientInfo(String name) throws SQLException {
			return super.k.getClientInfo(name);
		}
		@Override
		public Properties getClientInfo() throws SQLException {
			return super.k.getClientInfo();
		}
		@Override
		public Array createArrayOf(String typeName, Object[] elements) throws SQLException {
			return super.k.createArrayOf(typeName, elements);
		}
		@Override
		public Struct createStruct(String typeName, Object[] attributes) throws SQLException {
			return super.k.createStruct(typeName, attributes);
		}
		@Override
		public void setSchema(String schema) throws SQLException {
			super.k.setSchema(schema);
		}
		@Override
		public String getSchema() throws SQLException {
			return super.k.getSchema();
		}
		@Override
		public void abort(Executor executor) throws SQLException {
			super.k.abort(executor);
		}
		@Override
		public void setNetworkTimeout(Executor executor, int milliseconds) throws SQLException {
			super.k.setNetworkTimeout(executor, milliseconds);
		}
		@Override
		public int getNetworkTimeout() throws SQLException {
			return super.k.getNetworkTimeout();
		}
		
	}
	/**
	 * @return 获得下标
	 */
	private int getIndex() {
		if (index + 1 == connPool.size())
			index = 0;
		return index;
	}
	
}
