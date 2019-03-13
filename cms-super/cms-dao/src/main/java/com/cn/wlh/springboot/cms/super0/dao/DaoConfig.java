package com.cn.wlh.springboot.cms.super0.dao;

import java.util.Properties;

import cn.wlh.util.base.adapter.beanutil.apache.BeanUtil;
import cn.wlh.util.base.adapter.datasource.dbcp.DBCPUtils;
import cn.wlh.util.base.adapter.java.util.AdapterProperties;
import cn.wlh.util.base.adapter.servlet1.DaoExecute6;

public class DaoConfig {
	static final String   TEST_KEY = "test";
	static {
		Properties prop = new Properties();
//		prop.load(DBCPUtils.class.getClassLoader().getResourceAsStream("dbcpconfig.properties"));//根据DBCPUtil的classes的路径，加载配置文件
		
		
		DBCPUtils.putInstanceOfDataSource("test", new AdapterProperties().regsiterByArray(new Object[] {
				 "driverClassName" , "oracle.jdbc.driver.OracleDriver"
				,"url","jdbc:oracle:thin:@10.50.160.2:1522:DZSPSCP" 
				,"username","data_user"
				,"password", "gwssi123"
		}));
		  
//		DBCPUtils.putInstanceOfDataSource(TEST_KEY , prop);
	}
	public static final DaoExecute6 TEST = new DaoExecute6(DBCPUtils.getInstance(TEST_KEY).getDataSource() );
	
}
