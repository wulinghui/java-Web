package cn.wlh.framework.dao.execute;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import cn.wlh.util.base.JavaUtilFactory;

/**
 * @author 吴灵辉
 *	一句原生的jdbc , 最开始是想自己写个从后往前解析的工具.把=?前面的字符串给获得出来。只对于 shenqing = ?这种情况.
 * @see cn.wlh.util.base.adapter.dbutils.apache.GetSql2.Prepared
 */
public class DaoInterfaceImp extends AbstractDaoInterfaceImp {
	
	private /*static*/ final ThreadLocal<Connection> CONNECTION_HOLDER = new ThreadLocal<Connection>();
	
	public DaoInterfaceImp( DataSource dataSource) {
		super('w', dataSource);
		this.fileterChar = new char[]{
				' ','\n','\t','\'','"'	
		};
		this.startChar = new char[]{
				'?' , '='	
		};
	}
	public DaoInterfaceImp( DataSource dataSource, char[] fileterChar, char[] startChar) {
		super('w', dataSource);
		this.fileterChar = fileterChar;
		this.startChar = startChar;
	}

	@Override
	public <T> T execute(String sql, Connection con, Class<T> returnClass, Map<?, Object> pars) throws SQLException {
		List<String> list = getParameter(sql);
		return execute(sql, con, returnClass, pars, list);
	}
	//提供缓存的口子。
	public <T> T execute(String sql, Connection con, Class<T> returnClass, Map<?, Object> pars,
			List<String> parsKeylist) throws SQLException {
		PreparedStatement prepareStatement = null;
//		int len = list.size() - 1;
		try {
//			prepareStatement = con.prepareStatement( list.get(len) );
			prepareStatement = con.prepareStatement( sql );
			//赋值
			int i = 1;
			//这里遍历了N(2);
//			for (int j = 0; j < len; j++) {
			for (int j = 0; j < parsKeylist.size(); j++) {
				prepareStatement.setObject(i++, pars.get( parsKeylist.get(j)) );
			}
			//return 
			return (T) returnResult(returnClass, prepareStatement);
		} finally {
			release(prepareStatement);
		}
	}

	char [] fileterChar ;
	char [] startChar ;
//	boolean flags [];  // = ?的标记线程问题
	/**都是参数
	 * @param query
	 * @param beanFlag
	 * @return
	 */
	public List<String> getParameter(String sql) {
		boolean flags []= new boolean[startChar.length];
		//先把sql的空格 \t \n全换成一个' '  暂时不需要.
		char[] strChar = sql.toCharArray();
		int length = strChar.length;
		int endLength = startChar.length;
		List<Character> chars = JavaUtilFactory.newList(JavaUtilFactory.SELECT_OF_METHOD);
		Object[] array ; 
		String str;
		List<String> strs = JavaUtilFactory.newList(JavaUtilFactory.SELECT_OF_METHOD);
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
//		strs.add(sql);
		return strs;
	}
	/**是否在数组中.
	 * @param c
	 * @param strChar
	 * @return true在
	 */
	protected boolean lookUp(char c , char[] strChar) {
		for (char d : strChar) {
			if( d == c) return true; //在
		}
		return false;//不在
	}
    /**
     * 获取数据库连接
     * @throws SQLException 
     */
	public /*static*/ Connection getThreadConnection(DataSource DATA_SOURCE) throws SQLException {
        Connection conn = CONNECTION_HOLDER.get();
        if (conn == null) {
            conn = DATA_SOURCE.getConnection();
            conn.setAutoCommit(false);
            CONNECTION_HOLDER.set(conn);
        }
        return conn;
    }
//	public static void removeThreadConnection()  {
//		CONNECTION_HOLDER.remove();
//	}
    /**默认已经close Connection了;
     * @throws SQLException
     */
    public void closeThreadConnection() throws SQLException {
    	Connection conn = CONNECTION_HOLDER.get();
    	CONNECTION_HOLDER.remove();
    	conn.close();
    }
	/** 更改行为，从本地线程中获得。
	 */
	@Override
	public Connection getConnection() throws SQLException {
		return getThreadConnection(getDataSource());
	}
	public Connection getThreadConnection() throws SQLException {
		return getThreadConnection(getDataSource());
	}
}
