package cn.wlh.util.extend.dao.sql.oracle;

import java.sql.SQLException;

/**
 * @author wlh
 * OraclePreparedStatementSplitTableSql--È«Ãû:²ð±í
 */
public class OraPreSpliTab extends OraclePreStatSql {

	public OraPreSpliTab(String url, String user, String password,int i)
			throws SQLException {
		super(url, user, password,i);
	}
	
	public void splitTab(String sql,boolean isXuykRs,Object word) throws SQLException{
		super.operationDao(sql, isXuykRs, word);
	}
}
