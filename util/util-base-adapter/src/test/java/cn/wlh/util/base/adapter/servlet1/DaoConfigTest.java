package cn.wlh.util.base.adapter.servlet1;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Properties;
import java.util.Set;

import javax.sql.DataSource;

import org.junit.Before;
import org.junit.Test;

import cn.wlh.util.base.adapter.datasource.dbcp.DBCPUtils;
import cn.wlh.util.base.adapter.java.util.JavaUtilFactory;
import oracle.jdbc.driver.OracleConnection;

public class DaoConfigTest{
	  String   TEST_KEY = "test";
	  DaoExecute6 TEST;
	  Connection conn ;
//	@Before
	public void papred() throws SQLException, IOException {
		if( DBCPUtils.getInstance(TEST_KEY) != null) return;
		Properties prop = new Properties();
		prop.load(DBCPUtils.class.getClassLoader().getResourceAsStream("dbcpconfig.properties"));//根据DBCPUtil的classes的路径，加载配置文件
		DBCPUtils.putInstanceOfDataSource(TEST_KEY , prop);
//		DBCPUtils.putInstanceOfDataSource("test", new AdapterProperties().regsiterByArray(new Object[] {
//				 "driverClassName" , "oracle.jdbc.driver.OracleDriver"
//				,"url","jdbc:oracle:thin:@10.50.160.2:1522:DZSPSCP" 
//				,"username","data_user"
//				,"password", "gwssi123"
//		}));
		DataSource dataSource = DBCPUtils.getInstance(TEST_KEY).getDataSource();
		 TEST = new DaoExecute6( dataSource );
		 
		 conn = dataSource.getConnection();
		 
		 
		 System.out.println( "===========================papred=================" );
	}
//	@Test  
//	public void test0() throws Exception {
//		Object value = TEST.getValue(conn, "select shenqingh from z_fa_wjsp_scztb where rownum = 1");
//		System.out.println( value );
//		
//	}
	
	
	
//	@Test
//	public void test1() throws SQLException {
//		Record recored = TEST.getRecored(conn, "select * from z_fa_wjsp_scztb where rownum = 1");
//		System.out.println( recored );
//	}
	
	
//	@Test
//	public void test3() throws SQLException {
//		ColumnSet set = TEST.getSet(conn, "select shenqingh from z_fa_wjsp_scztb where rownum between 1 and 10");
//		System.out.println( set );
//	}
	
	
//	@Test
//	public void test4() throws SQLException {
//		TableDate table = TEST.getTable(conn, "select * from z_fa_wjsp_scztb where rownum between 1 and 10");
//		for (int i = 0; i < table.size(); i++) {
//			Record record = table.get(i);
//			System.out.println( record );
//		}
//	}
	
/*	@Test
	public void test5() throws SQLException {
		
		
//		Context3 context = new Context3(null);
		
		//代替框架模拟使用---MyContext....
		Context3 context = new cn.wlh.util.base.adapter.servlet1.DaoExecute6.MyContext();
		Record dataBus = new Record();
		TableDate table = TEST.getTable(new cn.wlh.util.base.adapter.dbutils.apache.DaoMethodReturnCache.Sql99<Record>() {
			publicpublic CachePreparedMap getFalv_sczt(Context3 context, Record dataBus){
//				dataBus.requireNonEmpty();
				CachePreparedMap cache = new CachePreparedMap();
				cache.setFinalSql("select * from z_fa_wjsp_scztb where rownum between 1 and 10");
				return cache;
			}
		}, "getFalv_sczt", dataBus, context);
		for (int i = 0; i < table.size(); i++) {
			Record record = table.get(i);
			System.out.println( record );
		}
		
	}*/
	
	
}
