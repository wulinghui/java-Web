package cn.wlh.util.extend.dao.sql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import cn.wlh.util.extend.dao.SqlBcui;
import cn.wlh.util.extend.dao.sql.DataPool.WrapConnection;

/**
 *	@author wlh
 *	这是一个关系型数据库的传统操作.
 *	不同子类代表不同数据库的操作.  -- 操作不同也算
 *  一个对象代表一个数据库. --不同对象就是拆库
 */
public abstract class RelationSql<Stat extends Statement,Inpts> implements SqlBcui {
	DataPool pool;
	public RelationSql(DataPool pool) {
		this.pool = pool;
	}
	/** 定义的主要的框架,用户只管实现,必要的就行了
	 * 到这里的Data应该是一维的了.放进去的应该也是--这里只管执行,过滤的问题不由他来管.有他的上一层.
	 * @param sql
	 * @param inputJsonMap
	 * @return
	 * @throws TsException
	 * @throws SQLException 
	 */
	public Object operationDao(String sql,boolean isXuykRs,Inpts word ) throws   SQLException {
		Stat stat = null;	
		ResultSet rs = null;
		WrapConnection wc = pool.getWrapConnection();
		Object obj;
		try {
			//获得Statement
			stat = setInput( sql, wc, word);
//			stat.setMaxRows(50);//最大行 //子类
//			stat.setMaxFieldSize(50);//最大列
			//2执行   3赋值
			if( isXuykRs ) obj = getOutput(sql, stat, rs);
			else obj = getOutput(sql, stat);
		}finally {
			release( rs,stat,null );
		}
		return obj;
	}
	
	/** rs stat  每次操作结束释放
	 * @param rs
	 * @param stat	
	 * @param wc  一个事务结束才释放
	 * @throws SQLException
	 */
	public void release(ResultSet rs,Stat stat,WrapConnection wc) throws  SQLException {
		if (rs != null)rs.close();
		if (stat != null)stat.close();
		if(wc!=null) wc.close();  
	}
	protected abstract Stat setInput(String sql, Connection wc, Inpts word) throws SQLException;
	protected abstract Object getOutput(String sql, Stat stat, ResultSet rs) throws  SQLException;
	protected abstract Object getOutput(String sql, Stat stat) throws  SQLException;
}
