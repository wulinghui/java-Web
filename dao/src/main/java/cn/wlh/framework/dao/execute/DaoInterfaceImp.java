package cn.wlh.framework.dao.execute;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import cn.wlh.util.base.JavaUtilFactory;

/**
 * @author �����
 *	һ��ԭ����jdbc , �ʼ�����Լ�д���Ӻ���ǰ�����Ĺ���.��=?ǰ����ַ�������ó�����ֻ���� shenqing = ?�������.
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
	//�ṩ����Ŀ��ӡ�
	public <T> T execute(String sql, Connection con, Class<T> returnClass, Map<?, Object> pars,
			List<String> parsKeylist) throws SQLException {
		PreparedStatement prepareStatement = null;
//		int len = list.size() - 1;
		try {
//			prepareStatement = con.prepareStatement( list.get(len) );
			prepareStatement = con.prepareStatement( sql );
			//��ֵ
			int i = 1;
			//���������N(2);
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
//	boolean flags [];  // = ?�ı���߳�����
	/**���ǲ���
	 * @param query
	 * @param beanFlag
	 * @return
	 */
	public List<String> getParameter(String sql) {
		boolean flags []= new boolean[startChar.length];
		//�Ȱ�sql�Ŀո� \t \nȫ����һ��' '  ��ʱ����Ҫ.
		char[] strChar = sql.toCharArray();
		int length = strChar.length;
		int endLength = startChar.length;
		List<Character> chars = JavaUtilFactory.newList(JavaUtilFactory.SELECT_OF_METHOD);
		Object[] array ; 
		String str;
		List<String> strs = JavaUtilFactory.newList(JavaUtilFactory.SELECT_OF_METHOD);
		//ѭ������char
		for (int i = length; length >  endLength; i--) { // 2 
			char c = strChar[i];
			
			if( flags[0] ) {//��? �����ж�=
				if(flags[1] ==true && !lookUp(c , fileterChar)  ) { //   ���ڽ���  �����ڹ�����������.
					chars.add(c);
				}else if( startChar[1] == c ) { //   Ϊ=��ʱ��     
					flags[1]  = true; 
				// �ڽ���֮ǰ�Ĺ�����������. "s = ?" �� " = ?"��Щ	
				}else if(lookUp(c , fileterChar) && flags[1] == false) {
					
				}else if( chars.size() > 0 ){ //���ڽ���  ���ڹ�����������.. "shenq ingh = ?" �� "shenq ingh"
					flags[0] = false;
					flags[1] = false;
					array = chars.toArray();
					str = String.valueOf(array);
					strs.add(str);
					chars.clear();
				}else  if( chars.size() == 0 ){ // //���ڽ���  ���ڹ�����������.. "shenq ingh = ?" �� "ingh ="
					
				}
			}else if( c == startChar[0] ) {
				 flags[0]  = true; // ��? �´�ѭ���ж�=
			}
		}
//		strs.add(sql);
		return strs;
	}
	/**�Ƿ���������.
	 * @param c
	 * @param strChar
	 * @return true��
	 */
	protected boolean lookUp(char c , char[] strChar) {
		for (char d : strChar) {
			if( d == c) return true; //��
		}
		return false;//����
	}
    /**
     * ��ȡ���ݿ�����
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
    /**Ĭ���Ѿ�close Connection��;
     * @throws SQLException
     */
    public void closeThreadConnection() throws SQLException {
    	Connection conn = CONNECTION_HOLDER.get();
    	CONNECTION_HOLDER.remove();
    	conn.close();
    }
	/** ������Ϊ���ӱ����߳��л�á�
	 */
	@Override
	public Connection getConnection() throws SQLException {
		return getThreadConnection(getDataSource());
	}
	public Connection getThreadConnection() throws SQLException {
		return getThreadConnection(getDataSource());
	}
}
