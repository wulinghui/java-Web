package cn.wlh.util.base.adapter.dbutils.apache;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.wlh.util.base.adapter.datasource.dbcp.DBCPUtils;

/**
 * @author 吴灵辉
 * 一句原生的jdbc , 最开始是想自己写个从后往前解析的工具.把=?前面的字符串给获得出来。但是对于下面这总就有些困难了。
 * insert into users(username,password,email,birthday) values(?,?,?,?)
 * 百度:NamedPreparedStatement
 */
public class GetSql2 {
	DBCPUtils instance = DBCPUtils.getInstance("null");
	Connection connection = instance.getConnection();
	//第一种方法.建议在参数少,可读性高的时候使用。
	private void test() throws SQLException {
		String sql = "insert into users(username,password,email,birthday) values(?,?,?,?)";
		PreparedStatement prepareStatement = connection.prepareStatement(sql);
		int i = 1;
		prepareStatement.setObject(i++, "张三");
		prepareStatement.setObject(i++, "123456");
		prepareStatement.setObject(i++, "123456@QQ.COM");
		prepareStatement.setObject(i++, "1995.10.11");
	}
	//第二种方法. 建议在参数多,提高可读性高。
	//NamedPreparedStatement 从前往后解析.遇到':'开始  遇到' '结束。
	private void test1() throws SQLException {
		//详情见百度.
		String sql =  "insert into users(username,password,email,birthday) "
				+ "values(:name,:pw,:email,:birth)";
		Connection con = connection;
		String name = null;
		String address = null;
		NamedPreparedStatement p = new NamedPreparedStatement(con, sql);
		p.setString("name", name);
		p.setString("pw", address);
		p.execute();
	}
	
	//第3种方法. 建议在参数多, 且 shenqingh = ? 这总键值对的
	private void test2() throws SQLException {
		Map map = new HashMap();
		String query = "select * from people where (first_name = ? or last_name = ?) and address = ?";
		PreparedStatement prepareStatement = connection.prepareStatement(query);
		List<String> parameter = new Prepared().getParameter(query);
//		int i = 1 , l = 0;
//		prepareStatement.setObject(i++, map.get(parameter.get(l++) ) );
//		prepareStatement.setObject(i++, map.get(parameter.get(l++) ) );
//		prepareStatement.setObject(i++, map.get(parameter.get(l++) ) );
		setParameter(prepareStatement, parameter, map);
//		prepareStatement.ex
		ResultSet executeQuery = prepareStatement.executeQuery();
	}
	//第3种方法. 建议在参数多, 且 shenqingh = ? 这总键值对的
	private void test2_() throws SQLException {
		Map map = new HashMap();
		String query = "select * from people where (first_name = ? or last_name = ?) and address = ?";
		PreparedStatement prepared = prepared(map, query, connection);
		ResultSet executeQuery = prepared.executeQuery();
		int executeUpdate = prepared.executeUpdate();
	}
	private void setParameter(PreparedStatement prepareStatement ,List<String> parameter ,Map map ) throws SQLException {
		int size = parameter.size();
		for (int i = 0; i < size; i++) {
			prepareStatement.setObject(i+1, map.get(parameter.get(i) ) );
		}
	}
	public PreparedStatement prepared( Map map,String query  ,Connection connection) throws SQLException {
		PreparedStatement prepareStatement = connection.prepareStatement(query);
		List<String> parameter = new Prepared().getParameter(query);
		setParameter(prepareStatement, parameter, map);
		return prepareStatement;
	}
	/**
	 * @author 吴灵辉
	 *一句原生的jdbc , 最开始是想自己写个从后往前解析的工具.把=?前面的字符串给获得出来。只对于 shenqing = ?这种情况.
	 */
	public static class Prepared{
		protected Prepared(){}
		static Prepared prepared []=new Prepared[] {
				new Prepared() , 
				new AnalysisSQL(':')
		};
		char [] fileterChar = {
			' ','\n','\t','\'','"'	
		};
		char [] startChar = {
			'?' , '='	
		};
		protected void setFileterChar(char[] fileterChar) {
			this.fileterChar = fileterChar;
		}
		protected void setStartChar(char[] startChar) {
			this.startChar = startChar;
		}
		protected void setFlags(boolean[] flags) {
			this.flags = flags;
		}
		boolean flags []= new boolean[startChar.length];  // = ?的标记
		/**获得 shenqing = ? 的shenqing数组
		 * @param sql
		 * @return shenqing 
		 */
		public static Prepared getInstance(String id) {
			if( "".equals(id)) {
				return prepared[0];
			}else if( ":".equals(id) ){
				return prepared[1];
			}
			return null;
		}
		/**最后一个是sql , 其他是key.
		 * @param query
		 * @param beanFlag
		 * @return
		 */
		public List<String> getParameter(String sql) {
			//先把sql的空格 \t \n全换成一个' '  暂时不需要.
			char[] strChar = sql.toCharArray();
			int length = strChar.length;
			int endLength = startChar.length;
			List<Character> chars = new ArrayList<Character>();
			Object[] array ; 
			String str;
			List<String> strs = new ArrayList<>();
			//循环解析char
			for (int i = length; length >  endLength; i--) { // 2 
				char c = strChar[i];
				
				if( flags[0] ) {//有? 接下判断=
					if(flags[1] ==true && !lookUp(c , fileterChar)  ) { //   正在进行  并不在过滤数组里面.
						chars.add(c);
					}else if( startChar[1] == c ) { //   为=的时候     
						flags[1]  = true; 
					// 在进行之前的过滤数组里面. "s = ?" 的 " = ?"这些	
					}else if(lookUp(c , fileterChar) && flags[1] == false) {
						
					}else if( chars.size() > 0 ){ //正在进行  并在过滤数组里面.. "shenq ingh = ?" 的 "shenq ingh"
						flags[0] = false;
						flags[1] = false;
						array = chars.toArray();
						str = String.valueOf(array);
						strs.add(str);
						chars.clear();
					}else  if( chars.size() == 0 ){ // //正在进行  并在过滤数组里面.. "shenq ingh = ?" 的 "ingh ="
						
					}
				}else if( c == startChar[0] ) {
					 flags[0]  = true; // 有? 下次循环判断=
				}
			}
			strs.add(sql);
			return strs;
		}
		// 是否在数组中.
		public boolean lookUp(char c , char[] strChar) {
			for (char d : strChar) {
				if( d == c) return true; //在
			}
			return false;//不在
		}
	}
	
//	public class NamedPreparedStatement{
//		char [] start;
//		char [] end;
//		boolean flag;
//		List<String> para = new ArrayList<String>(); 
//它使用HashMap将SQL语句种对应的变量转换成问号	
//然后再创建一个对应的PreparedStatement语句，并且保持了对应变量和索引的映射关系，这两个类是兼容的，所以你可以分别加以利用。		
//	}
}
