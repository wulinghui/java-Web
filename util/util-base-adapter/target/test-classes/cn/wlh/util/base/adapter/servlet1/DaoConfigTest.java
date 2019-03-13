package cn.wlh.util.base.adapter.servlet1;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import javax.sql.DataSource;

import org.junit.Before;
import org.junit.Test;

import cn.wlh.util.base.adapter.datasource.dbcp.DBCPUtils;
import cn.wlh.util.base.adapter.servlet1.DaoExecute6;

public class DaoConfigTest {
	  String   TEST_KEY = "test";
	  DaoExecute6 TEST;
	  Connection connection ;
	@Before
	public void papred() throws SQLException, IOException {
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
		 
		 connection = dataSource.getConnection();
	}
		
	@Test
	public void test0() throws SQLException {
		connection = TEST.getDataSource().getConnection();
		Object value = TEST.getValue(connection, "select * from z_fa_wjsp_scztb");
		System.out.println( value );
	}
}
