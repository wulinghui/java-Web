package cn.wlh.util.base.adapter.dbutils.apache;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;

import org.apache.commons.dbutils.QueryRunner;
import org.junit.Test;

import cn.wlh.util.base.adapter.datasource.dbcp.DBCPUtils;

public class TestCRUD {
	@Test
	public void testInsert() throws SQLException{
		//创建一个QueryRunner对象
		QueryRunner qr = new QueryRunner(DBCPUtils.getDataSource());
		Connection connection = qr.getDataSource().getConnection();
		//执行sql语句
		qr.update("insert into users(username,password,email,birthday) values(?,?,?,?)", "菜10","123","c10@163.com",new Date());
		
	}
	
	@Test
	public void testUpdate() throws SQLException{
		//创建一个QueryRunner对象
		QueryRunner qr = new QueryRunner(DBCPUtils.getDataSource());
		String sql = "update users set username=?,password=? where id=?";
		qr.update(sql, "周杰杰","333",15);
		
		Object [] params = new HashMap<>().values().toArray()	;
		qr.update(sql, params);
	}
	
	
	@Test
	public void testDelete() throws SQLException{
		//创建一个QueryRunner对象
		QueryRunner qr = new QueryRunner(DBCPUtils.getDataSource());
		qr.update("delete from users where id=?", 15);
	}
	
	@Test//批处理，只能执行相同的sql语句
	public void testBatch() throws SQLException{
		//创建一个QueryRunner对象
		QueryRunner qr = new QueryRunner(DBCPUtils.getDataSource());
		
		Object[][] params = new Object[10][];//高维代表执行多少次sql语句
		for (int i = 0; i < params.length; i++) {
			params[i] = new Object[]{"菜10"+i,"123","c10@163.com",new Date()};//给每次执行的sql语句中的？赋值
		}
		qr.batch("insert into users(username,password,email,birthday) values(?,?,?,?)", params );
//		qr.batch(conn, sql, params)  这个不会关闭conn
//		qr.
	}
	@Test
	public void testSourceConn() {
		 PreparedStatement stmt = null;
		 Query q = new Query(DBCPUtils.getDataSource());
		 
		 
//		 stmt.
	}
}
