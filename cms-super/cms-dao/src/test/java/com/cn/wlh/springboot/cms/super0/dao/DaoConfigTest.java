package com.cn.wlh.springboot.cms.super0.dao;

import java.sql.Connection;
import java.sql.SQLException;

import org.junit.Test;

import cn.wlh.util.base.adapter.servlet1.DaoExecute6;

public class DaoConfigTest {
	DaoExecute6 test = DaoConfig.TEST;
	Connection connection ;
	{
		try {
			connection = test.getDataSource().getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	@Test
	public void test0() throws SQLException {
		connection = test.getDataSource().getConnection();
		Object value = test.getValue(connection, "select shenqingh from z_fa_wjsp_scztb");
		System.out.println( value.getClass().getName() );
		System.out.println( value );
	}
}
