package cn.wlh.util.base.adapter.servlet1;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import javax.sql.DataSource;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import cn.wlh.util.base.adapter.datasource.dbcp.DBCPUtils;
public class DBCPTest {
	
	  String   TEST_KEY = "test";
	  DataSource dataSource;
	  Connection connection ;
	  //
	@Before
	@Ignore
	public void papred() throws SQLException, IOException {
		Properties prop = new Properties();
		prop.load(DBCPUtils.class.getClassLoader().getResourceAsStream("dbcpconfig.properties"));//根据DBCPUtil的classes的路径，加载配置文件
		DBCPUtils.putInstanceOfDataSource(TEST_KEY , prop);
		dataSource = DBCPUtils.getInstance(TEST_KEY).getDataSource();
		try {
			connection = dataSource.getConnection();
		}catch (Throwable e) {
			e.printStackTrace();
			connection = dataSource.getConnection();
		}
	}
		
	@Test@Ignore
	public void test0() throws SQLException {
		PreparedStatement prepareStatement = connection.prepareStatement("select * from z_fa_wjsp_scztb");
		ResultSet executeQuery = prepareStatement.executeQuery();
		while( executeQuery.next()) {
			Object object = executeQuery.getObject(1);
			System.out.println("DBCPTest"+object);
		}
	}
}
