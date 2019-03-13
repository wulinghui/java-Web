package cn.wlh.util.base.adapter.datasource.dbcp;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSourceFactory;

/**
 * @author 吴灵辉
 * 这里的构造方法只是简单的控制了一下,没有整和Bean
 */
public class DBCPUtils {
	/**把ds改成变量就可以一个系统有多个数据源了,注意使用多例*/
	private  DataSource ds = null;
	/** 统一管理*/
	private static Map<String,DBCPUtils> map = new HashMap<String,DBCPUtils>();
	/** 
	 * @param prop
	 * 
	 */
	DBCPUtils(Properties prop){
		try {
		    ds = BasicDataSourceFactory.createDataSource(prop);//得到一个数据源 
		} catch (Exception e) {
			e.printStackTrace();
			throw new ExceptionInInitializerError("初始化错误，请检查配置文件");
		}
	}
	public  DataSource getDataSource(){
		return ds;
	}
	public  Connection getConnection(){
		try {
			return ds.getConnection();
		} catch (SQLException e) {
			throw new RuntimeException("服务器忙。。。");
//			Thread.currentThread().sleep( 1000 * 60 );
		}
	}
	
	public static void release(Connection conn,Statement stmt,ResultSet rs){
		//关闭资源
				if(rs!=null){
					try {
						rs.close();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				if(stmt!=null){
					try {
						stmt.close();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				if(conn!=null){
					try {
						conn.close();//关闭
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
	}
	public static void putInstanceOfDataSource(String id,Properties prop) {
		if( map.get(id) != null ) throw new RuntimeException("数据源已存在"); 
		DBCPUtils value = new DBCPUtils(prop);
		map.put(id, value);
	} 
	public static DBCPUtils getInstance(String id) {
		return map.get(id);
	}
}
