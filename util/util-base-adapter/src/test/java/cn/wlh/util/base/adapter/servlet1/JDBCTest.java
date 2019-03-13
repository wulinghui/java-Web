package cn.wlh.util.base.adapter.servlet1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class JDBCTest {
	public static void main(String[] args) throws Exception {
		Class.forName( "oracle.jdbc.driver.OracleDriver" );
		String url = "jdbc:oracle:thin:@10.50.160.2:1522:DZSPSCP";
		String user = "data_user";
		String password = "gwssi123";
		Connection connection = DriverManager.getConnection( url , user , password );
		PreparedStatement prepareStatement = connection.prepareStatement("select * from z_fa_wjsp_scztb");
		ResultSet executeQuery = prepareStatement.executeQuery();
		while( executeQuery.next()) {
			Object object = executeQuery.getObject(1);
			System.out.println(object);
		}
	}
}
