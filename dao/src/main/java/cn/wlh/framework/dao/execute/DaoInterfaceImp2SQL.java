package cn.wlh.framework.dao.execute;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import cn.wlh.util.base.JavaUtilFactory;

/**
 * @author �����
 * ���Զ���Ĺ���,������sql��<br/>
 * insert into users(username,password,email,birthday)  values(:name,:pw,:email,:birth) <br/>
 * <br/>
 * [name, pw ,email, birth,<br/>
 * insert into users(username,password,email,birthday)  values(?,?,?,?) ]<br/>
 * @see cn.wlh.util.base.adapter.dbutils.apache.AnalysisSQL<br/>
 * ���￪ʼ���ð�װ�ࡣ<br/>
 */
public class DaoInterfaceImp2SQL extends DaoInterfaceImp {
	private static final char C = ':';
	DaoInterfaceImp daoInterfaceImp;
	public DaoInterfaceImp2SQL(DataSource dataSource) {
		super(dataSource, new char[]{
				' ','\n','\t','\'','"'	
		}, new char[] {
				C //':'	    
		});
	}
	public DaoInterfaceImp2SQL(DaoInterfaceImp daoInterfaceImp,DataSource dataSource) {
		this(daoInterfaceImp,dataSource,C);
	}
	public DaoInterfaceImp2SQL(DaoInterfaceImp daoInterfaceImp,DataSource dataSource, char c) {
		super(dataSource, new char[]{
				' ','\n','\t','\'','"'	
		}, new char[] {
				c //':'	    
		});
		this.daoInterfaceImp = daoInterfaceImp;
	}
	@Override
	public <T> T execute(String sql, Connection con, Class<T> returnClass, Map<?, Object> pars,List<String> list) throws SQLException {
		if(daoInterfaceImp != null) return daoInterfaceImp.execute(sql, con, returnClass, pars, list);
		PreparedStatement prepareStatement = null;
		int len = list.size() - 1;
		try {
			prepareStatement = con.prepareStatement( list.get(len) );
			//��ֵ
			int i = 1;
			//���������N(2);
			for (int j = 0; j < len; j++) {
				prepareStatement.setObject(i++, pars.get( list.get(j)) );
			}
			return (T) returnResult(returnClass, prepareStatement);
		} finally {
			release(prepareStatement);
		}
	}
	
	/** ���һ����sql , ������key.
	 *  @see cn.wlh.framework.dao.execute.DaoInterfaceImp#getParameter(java.lang.String)
	 */
	public  List<String> getParameter(String sql) {
		if(daoInterfaceImp != null) return daoInterfaceImp.getParameter(sql);
		boolean flags []= new boolean[startChar.length];
		char[] strChar = sql.toCharArray();
		int length = strChar.length;
		int endLength = 0; 
		List<Character> chars = new ArrayList<Character>();
		List<String> strs = JavaUtilFactory.newList(JavaUtilFactory.SELECT_OF_METHOD);
		StringBuilder sb = new StringBuilder();
		char c;
		//ѭ������char
		for (int i = endLength ; i < length; i++) { //udpate delete ����5��֮��
			c = strChar[i];
			if( flags[0] ) {//��: 
				if( lookUp(c , fileterChar) ) { // �� :sd  where  �� sd  where�ո���,
					flags[0] = false;
					addOutPars(chars, strs);
//					chars.clear();
					chars = new ArrayList<Character>();
				}else{ 
					chars.add(c);
				}
			}else if( c == startChar[0] ) {
				 flags[0]  = true; // 
				 sb.append("?"); //�����滻��? ��
			}else {//��ӵ�sb����ȥ��
				sb.append(c);
			}
		}
		//��ֹ���һ��û����ӽ�ȥ��
		if( !chars.isEmpty() )  addOutPars(chars, strs);
			
		// ���һ����   insert into users(username,password,email,birthday)  values(?,?,?,?)
		strs.add(sb.toString());
		return strs;
	}
	/**
	 *  �������Ĳ���������
	 */
	protected final void addOutPars(List<Character> chars, List<String> strs) {
		int len = chars.size();
		char[] array = new char[len];
		String str;
		for (int i = 0; i < len; i++) {
			array[i] = chars.get(i);
		}
		str = String.valueOf(array);
		strs.add(str);
	}
	@Override
	public <T> T execute(String sql, Connection con, Class<T> returnClass, Map<?, Object> pars) throws SQLException {
		if(daoInterfaceImp != null) 
			return daoInterfaceImp.execute(sql, con, returnClass, pars);
		return super.execute(sql, con, returnClass, pars);
	}
	@Override
	protected boolean lookUp(char c, char[] strChar) {
		if(daoInterfaceImp != null) 
			return daoInterfaceImp.lookUp(c, strChar);
		return super.lookUp(c, strChar);
	}
	/* (non-Javadoc)
	 * @see cn.wlh.framework.dao.execute.DaoInterfaceImp#getConnection()
	 */
	@Override
	public Connection getConnection() throws SQLException {
		if(daoInterfaceImp != null) 
			return daoInterfaceImp.getConnection();
		return super.getConnection();
	}
	/* (non-Javadoc)
	 * @see cn.wlh.framework.dao.execute.AbstractDaoInterfaceImp#returnResult(java.lang.Class, java.sql.PreparedStatement)
	 */
	@Override
	protected Object returnResult(Class returnClass, PreparedStatement prepareStatement) throws SQLException {
		if(daoInterfaceImp != null) 
			return daoInterfaceImp.returnResult(returnClass, prepareStatement);
		return super.returnResult(returnClass, prepareStatement);
	}
}
