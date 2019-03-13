package cn.wlh.util.extend.dao.sql.oracle;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import cn.wlh.util.extend.dao.Word;
import cn.wlh.util.extend.dao.sql.DataPool;
import cn.wlh.util.extend.dao.sql.RelationSql;

/**
 * @author wlh
 * oracle数据库的预处理简单操作.
 */
public  class OraclePreStatSql extends RelationSql<PreparedStatement,Object> {

	public OraclePreStatSql(String url, String user, String password,int poolLength)
			throws SQLException {
		super(new DataPool(url, user, password,poolLength));
	}

	@Override
	protected PreparedStatement setInput(String sql, Connection conn,
			Object word) throws SQLException{
		// 获得ps
		PreparedStatement ps =  conn.prepareStatement(sql);
		//判断根据不同的类型,执行不同的操作.
		if( word != null) {
			if(word instanceof Collection){
				//是List的操作,通常是:word.values
				Collection words = (Collection) word;
				int i = 0;
				for (Object object : words) {
					i++;
					ps.setObject( i, object);
				}
			}else if( word instanceof Object[]){
				//数组的操作
				Object [] words = (Object[]) word;
				int i = 0;
				int len = words.length;
				for (; i < len; i++) {
					ps.setObject(i + 1, words[i]);
				}
			}else if( word instanceof Word ){
				//Word对象的操作
				Word words = (Word) word;
				Object [] inpts = (Object[]) words.get( Word.SQL_INPUT );//这里就需要Word注意了
				if( inpts == null) return ps;//防止为空.
				int i = 0;
				int len = inpts.length;
				for (; i < len; i++) {
					ps.setObject(i + 1,//通过inpts来获得输入的值 
							words.get(inpts[i]) );
				}
			}
		}
		ps.setMaxRows(50);//最大行 
		ps.setMaxFieldSize(50);//最大列
		return ps;
	}
	@Override
	protected List getOutput(String sql, PreparedStatement ps, ResultSet rs)
			throws SQLException {
		int count;
		List list = new ArrayList();
		rs = ps.executeQuery();
		ResultSetMetaData rsmd = ps.getMetaData();
		count = rsmd.getColumnCount();
//		System.out.println(count);
		while (rs.next()) {
			Map<String, Object> map = new Word();
			for (int j = 0; j < count; j++) {
				String colN = rsmd.getColumnName(j + 1);
				map.put(colN, rs.getObject(colN));
			}
			list.add(map);
		}
//		System.out.println(list);
		return list;
	}

	@Override
	protected Integer getOutput(String sql, PreparedStatement stat)
			throws SQLException {
		return stat.executeUpdate();
	}
}
