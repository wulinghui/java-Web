package com.novalue;

import java.sql.SQLException;
import java.util.Map;

import dao.sql.OraclePreStatSql;
import dao.sql.SqlBcui;
import springboot.com.wlh.biz.mdj.Test;
import springboot.com.wlh.dao.mdj.Z_FA_WJSP_SCZTB;
import util.base._Map;
import util.extend.Confi;
import util.extend.Dao;

public class Config extends Confi {

	@Override
	public Class<?> getDaoAllMethodByMethodOfPackage() {
		return Z_FA_WJSP_SCZTB.class;
	}

	@Override
	public Class<?> getBizAllMethodByMethodOfPackage() {
		return Test.class;
	}

	@Override
	public String[] drivers() {
		return new String[] {
				"oracle.jdbc.driver.OracleDriver"
		};
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, SqlBcui> sqlBcuis()  {
		Object[] objs = null;
		try {
			objs = new Object[] {
				 "oracle-first",new OraclePreStatSql("jdbc:oracle:thin:@10.50.160.2:1522:DZSPSCP", "data_user", "gwssi123", 20)//第一个数据源.
			};
		} catch (SQLException e) {
			e.printStackTrace();
		}
//		_Exception.handleAll(new _Exception.Print(){
//			public Object normal() throws Throwable {
//				return null;
//			}
//		});
		return _Map.array2Map(objs, 2);
	}

	@Override
	public Map<String, Dao> complierDaos() {
		// TODO Auto-generated method stub
		return null;
	}

}
