package cn.wlh.framework.dao.execute;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import cn.wlh.framework.dao.DaoInterfaceAdapt;
import cn.wlh.util.base.JavaUtilFactory;

public abstract class AbstractDaoInterfaceImp extends DaoInterfaceAdapt {
	public AbstractDaoInterfaceImp(char flag, DataSource dataSource) {
		super(flag, dataSource);
	}

	public <T> T execute(String sql, Connection con, Class<T> returnClass, Object... pars) throws SQLException {
		PreparedStatement prepareStatement = null;
		try {
			prepareStatement = con.prepareStatement(sql);
			//赋值
			int i = 1;
			for (Object object : pars) {
				prepareStatement.setObject(i++, object);
			}
			//return 
			return (T) returnResult(returnClass, prepareStatement);
		} finally {
			release(prepareStatement);
		}
	}
	
	public <T> T execute(String sql, Connection con, Class<T> returnClass, Collection<Object> pars)
			throws SQLException {
		PreparedStatement prepareStatement = null;
//		prepareStatement.addBatch(sql);
//		prepareStatement.clearParameters();
		try {
			prepareStatement = con.prepareStatement(sql);
			//赋值
			int i = 1;
			for (Object object : pars) {
				prepareStatement.setObject(i++, object);
			}
			//return 
			return (T) returnResult(returnClass, prepareStatement);
		} finally {
			release(prepareStatement);
		}
	}

	public abstract <T> T execute(String sql, Connection con, Class<T> returnClass, Map<?, Object> pars) throws SQLException;
	//用户无法强转。
//	protected <T> T execute0(String sql, Connection con, Class<T> returnClass, Iterable<Object> pars) throws SQLException {
//		PreparedStatement prepareStatement = null;
//		try {
//			prepareStatement = con.prepareStatement(sql);
//			//赋值
//			int i = 1;
//			for (Object object : pars) {
//				prepareStatement.setObject(i++, object);
//			}
//			//return 
//			return (T) returnResult(returnClass, prepareStatement);
//		} finally {
//			release(prepareStatement);
//		}
//	}
//	public static void main(String[] args) {
//		System.out.println(int.class.getName());
//	}
	
	/**这里只提供了二种类型。一个int.class一个List.class
	 * 子类对他需要扩展时，应该在他前面扩展。
	 * @param returnClass
	 * @param prepareStatement
	 * @return
	 * @throws SQLException
	 */
	protected Object returnResult(Class returnClass, PreparedStatement prepareStatement) throws SQLException  {
		if(returnClass.equals(int.class)) {
			return  ( (Integer)prepareStatement.executeUpdate() );
		}else {
			ResultSet rs = null;
			List list = JavaUtilFactory.newList(JavaUtilFactory.SELECT_OF_METHOD);
			try {
				rs = prepareStatement.executeQuery();
				int count;
				ResultSetMetaData rsmd = prepareStatement.getMetaData();
				count = rsmd.getColumnCount();
				//				System.out.println(count);
				while (rs.next()) {
					Map<String, Object> map = JavaUtilFactory.newMap(JavaUtilFactory.SELECT_OF_METHOD);
					for (int j = 0; j < count; j++) {
						String colN = rsmd.getColumnName(j + 1);
						map.put(colN, rs.getObject(colN));
					}
					list.add(map);
				} 
			} finally {
				release(rs);
			}
			return  list;
		}
	}
	public static final void release(ResultSet rs)  {
		if (rs != null)
			try {
				rs.close();
			} catch (Exception e) {
			}
	}
	public static final void release(Statement rs)  {
		if (rs != null)
			try {
				rs.close();
			} catch (Exception e) {
			}
	}
	public static final void release(Connection rs)  {
		if (rs != null)
			try {
				rs.close();
			} catch (Exception e) {
			}
	}
	public static final void release(ResultSet rs,Statement stat,Connection conn)  {
			release(rs);
			release(stat);
			release(conn);
	}  
}
