package cn.wlh.framework.web.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.dbcp2.BasicDataSourceFactory;

import cn.wlh.framework.dao.DaoInterface;
import cn.wlh.framework.dao.execute.DaoInterfaceImp;
import cn.wlh.framework.dao.execute.DaoInterfaceImp2SQL;
import cn.wlh.framework.web.entity.ColumnSet;
import cn.wlh.framework.web.entity.DaoBeanFlag;
import cn.wlh.framework.web.entity.Record;
import cn.wlh.framework.web.entity.TableDate;
import cn.wlh.util.base.adapter.datasource.dbcp.DBCPUtils;
import cn.wlh.util.base.adapter.datasource.dbcp.DataSourceBean;

public class DaoInterfaceWebImp extends DaoInterfaceImp {
	DaoInterfaceImp daoInterfaceImp;
	public DaoInterfaceWebImp(DataSource dataSource) {
		super(dataSource);
	}
	
	
	/**
	 * @param dataSource
	 * @param daoInterfaceImp 这里只采用他的解析方式。getPar..
	 */
	public DaoInterfaceWebImp(DataSource dataSource, DaoInterfaceImp daoInterfaceImp) {
		super(dataSource);
		this.daoInterfaceImp = daoInterfaceImp;
	}
	
	/**
	 * 默认采用 shenqing = ? 这方式，如果传入了其他的就以它的为主
	 */
	public List<String> getParameter(String sql) {
		if( daoInterfaceImp != null) daoInterfaceImp.getParameter(sql);
		return super.getParameter(sql);
	}
	//提供缓存的口子。
	/**父类没有的方法
	 * 最后一个是sql语句。
	 * 其他的都是参数标识。
	 * @param con
	 * @param returnClass
	 * @param pars
	 * @param parsKeylist
	 * @return
	 * @throws SQLException
	 */
	public final <T extends DaoBeanFlag> T execute(Connection con, Class<T> returnClass, Map<?, Object> pars,
				Object[] parsKeylist) throws SQLException {
			PreparedStatement prepareStatement = null;
			int len = parsKeylist.length - 1;
			try {
				prepareStatement = con.prepareStatement( parsKeylist[len].toString() );
				//赋值
				int i = 1;
				//这里遍历了N(2);
				for (int j = 0; j < len; j++) {
					prepareStatement.setObject(i++, pars.get( parsKeylist[i] ) );
				}
				//return 
				return (T) returnResult(returnClass, prepareStatement);
			} finally {
				release(prepareStatement);
			}
	}
	/** 父类不好用了，final方法。
	 *  (non-Javadoc)
	 * @see cn.wlh.framework.dao.execute.AbstractDaoInterfaceImp#returnResult(java.lang.Class, java.sql.PreparedStatement)
	 */
	protected final Object returnResult(Class returnClass, PreparedStatement prepareStatement) throws SQLException {
		String returnClassName = returnClass.getName();
		switch(returnClassName) {
			case "int":
				return  (Integer)prepareStatement.executeUpdate() ;
			case "cn.wlh.framework.web.entity.Record"://一条记录
				ResultSet rs = prepareStatement.executeQuery();
				Record record = new Record();
				//只取第一条
				if( rs.next() ) {
					ResultSetMetaData rsmd = prepareStatement.getMetaData();
					int count = rsmd.getColumnCount();
					for (int j = 0; j < count; j++) {
						String colN = rsmd.getColumnName(j + 1);
						record.put(colN, rs.getObject(colN));
					}
				}
				return record;
			case "cn.wlh.framework.web.entity.TableDate":
				rs = prepareStatement.executeQuery();
				TableDate table = new TableDate();
					int count;
					ResultSetMetaData rsmd = prepareStatement.getMetaData();
					count = rsmd.getColumnCount();
					while (rs.next()) {
						Record map = new Record();
						for (int j = 0; j < count; j++) {
							String colN = rsmd.getColumnName(j + 1);
							map.put(colN, rs.getObject(colN));
						}
						table.add(map);
					}
				return table;
			case "cn.wlh.framework.web.entity.ColumnSet":
				rs = prepareStatement.executeQuery();
				ColumnSet set = new ColumnSet();
				//默认获得第一列。
				while (rs.next()) {
					set.add( rs.getObject(1) );
				}
				return set;
			//自己挖的坑自己填。。
			case "cn.wlh.framework.web.entity.DaoBeanFlag":
				rs = prepareStatement.executeQuery();
				//默认获得第一列第一行
				if(rs.next()) {
					return new Value(rs.getObject(1));
				}
				return new Value("");//空字符串。
			default: return null;
		}
//		return super.returnResult(returnClass, prepareStatement);
	}
	/*private*/ class  Value implements DaoBeanFlag{
		Object obj;
		public Value(Object obj) {
			this.obj = obj;
		}
		/**
		 * @return the obj
		 */
		public Object getObj() {
			return obj;
		}

		/**
		 * @param obj the obj to set
		 */
		public void setObj(Object obj) {
			this.obj = obj;
		}

		@Override
		public String toString() {
			return obj.toString();
		}
	}
	/*
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
	
	 */
	
	
	
	
	
	public static void main(String[] args) {
		print(Record.class);
		print(TableDate.class);
		print(ColumnSet.class);
		print(DaoBeanFlag.class);
		System.out.println(String.class);
		System.out.println( int.class);
		System.out.println( int.class.getName());
	}
	static void print(Class cla) {
		System.out.println(cla.getName());
	}
}
