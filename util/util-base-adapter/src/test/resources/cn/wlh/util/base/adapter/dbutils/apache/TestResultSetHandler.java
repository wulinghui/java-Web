package cn.wlh.util.base.adapter.dbutils.apache;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ArrayHandler;
import org.apache.commons.dbutils.handlers.ArrayListHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ColumnListHandler;
import org.apache.commons.dbutils.handlers.KeyedHandler;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.junit.Test;

import cn.wlh.util.base.adapter.datasource.dbcp.DBCPUtils;

public class TestResultSetHandler {
	@Test//ArrayHandler:适合取1条记录。把该条记录的每列值封装到一个数组中Object[]
	public void tese1() throws SQLException{
		QueryRunner qr = new QueryRunner(DBCPUtils.getDataSource());
		Object[] arr  = qr.query("select * from users", new ArrayHandler());
		
		for (Object o : arr) {
			System.out.println(o);
		}
	}
	
	@Test//ArrayListHandler:适合取多条记录。把每条记录的每列值封装到一个数组中Object[]，把数组封装到一个List中
	public void tese2() throws SQLException{
		QueryRunner qr = new QueryRunner(DBCPUtils.getDataSource());
		List<Object[]> query = qr.query("select * from users", new ArrayListHandler());
		
		for (Object[] os : query) {
			for (Object o : os) {
				System.out.println(o);
			}
			System.out.println("--------------");
		}
	}
	
	@Test //ColumnListHandler:取某一列的数据。封装到List中。
	public void tese3() throws SQLException{
		QueryRunner qr = new QueryRunner(DBCPUtils.getDataSource());
		List<Object> list = (List<Object>) qr.query("select username,password from users", new ColumnListHandler(1));
		
		for (Object o : list) {
			System.out.println(o);
		}
	}
	
	@Test //KeyedHandler:取多条记录，每一条记录封装到一个Map中，再把这个Map封装到另外一个Map中，key为指定的字段值。
	public void tese4() throws SQLException{
		QueryRunner qr = new QueryRunner(DBCPUtils.getDataSource());
		//大Map的key是表中的某列数据，小Map中的key是表的列名，所以大Map的key是Object类型，小Map的key是String类型
		Map<Object,Map<String,Object>> map = (Map<Object, Map<String, Object>>) qr.query("select * from users", new KeyedHandler(1));
		
		for (Map.Entry<Object, Map<String,Object>> m : map.entrySet()) {
			System.out.println(m.getKey());//大Map中key值就是id列的值
			for (Map.Entry<String, Object> mm : m.getValue().entrySet()) {
				System.out.println(mm.getKey()+"\t"+mm.getValue());//取出小Map中的列名和列值
			}
			System.out.println("---------------------");
		}
		
	}
	
	
	@Test//MapHandler:适合取1条记录。把当前记录的列名和列值放到一个Map中
	public void tese5() throws SQLException{
		QueryRunner qr = new QueryRunner(DBCPUtils.getDataSource());
		Map<String,Object> map = qr.query("select * from users where id=?", new MapHandler(),20);
		
		for (Map.Entry<String, Object> m : map.entrySet()) {
			System.out.println(m.getKey()+"\t"+m.getValue());
		}
		
	}
	
	
	@Test//MapListHandler:适合取多条记录。把每条记录封装到一个Map中，再把Map封装到List中
	public void tese6() throws SQLException{
		QueryRunner qr = new QueryRunner(DBCPUtils.getDataSource());
		List<Map<String,Object>> list = qr.query("select * from users", new MapListHandler());
		
		for (Map<String, Object> map : list) {
			for (Map.Entry<String, Object> m : map.entrySet()) {
				System.out.println(m.getKey()+"\t"+m.getValue());
			}
			System.out.println("---------------");
		}
	}
	
	@Test //ScalarHandler:适合取单行单列数据
	public void tese7() throws SQLException{
		QueryRunner qr = new QueryRunner(DBCPUtils.getDataSource());
		Object o = qr.query("select count(*) from users", new ScalarHandler(1));
		System.out.println(o.getClass().getName());
	}
	
	@Test //BeanHandler:适合取单行单列数据
	public void tese8() throws SQLException{
		QueryRunner qr = new QueryRunner(DBCPUtils.getDataSource());
		User user = qr.query("select * from users where id=?", new BeanHandler<User>(User.class),1);
		System.out.println(user);
	}
	
	
	@Test //BeanListHandler 
	public void tese9() throws SQLException{
		QueryRunner qr = new QueryRunner(DBCPUtils.getDataSource());
		List<User> list = qr.query("select * from users where id=?", new BeanListHandler<User>(User.class),1);
		
		System.out.println(list.size());
	}
}
