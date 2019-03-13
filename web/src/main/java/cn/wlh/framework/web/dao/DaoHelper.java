package cn.wlh.framework.web.dao;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.dbcp2.BasicDataSourceFactory;

import  cn.wlh.framework.dao.DaoInterface;
import cn.wlh.framework.dao.DaoManageInterface;
import cn.wlh.framework.dao.execute.AbstractDaoInterfaceImp;
import cn.wlh.framework.dao.execute.DaoInterfaceImp;
import cn.wlh.framework.dao.execute.DaoInterfaceImp2SQL;
import cn.wlh.framework.ioc.factory.AppFactoryMan;
import cn.wlh.framework.web.entity.ColumnSet;
import cn.wlh.framework.web.entity.Context;
import cn.wlh.framework.web.entity.DaoBeanFlag;
import cn.wlh.framework.web.entity.Record;
import cn.wlh.framework.web.entity.TableDate;
import cn.wlh.util.base.JavaUtilFactory;
import cn.wlh.util.base.RecordNewOfLog;
import cn.wlh.util.base.adapter.datasource.dbcp.DBCPUtils;
import cn.wlh.util.base.exception.ThrowableFacade;

public abstract class DaoHelper {
	DaoHelper(){}
	static {
		System.out.println("-----------------------");
	}
	public static final String DEFAULT_DAO_INTERFACE_ID = "DEFAULT_DAO_INTERFACE_ID";
	/**��Ϊ��ϵͳ��Ҫ�����DaoInterface������DaoInterfaceImp������*/
	public static final DaoManageInterface DaoFacade = AppFactoryMan.getInstance(DaoManageImp.class);  
	/**ֻ��Ϊ���ṩ�����ķ���*/
	private final static DaoInterfaceImp GET_PARS_OF_JDBC = new DaoInterfaceImp(null); 
	private final static DaoInterfaceImp2SQL GET_PARS_OF_NAME = new DaoInterfaceImp2SQL(null);
	static {
		System.out.println("-----------------------");
	}
	/**���ǲ���û��sql
	 * @param sql
	 * @return
	 */
	public static final List<String> analySqlJDBC(String sql) {
		return GET_PARS_OF_JDBC.getParameter(sql);
	}
	/**���һ����sql , ������key.
	 * @param sql
	 * @return
	 */
	public static final List<String> analySqlName(String sql) {
		return GET_PARS_OF_NAME.getParameter(sql);
	}
	public static final String  queryValue(Class<?> daoClass ,String methodName, Context conText,Record inputMap) {
		return queryValue(daoClass, methodName, conText, inputMap, DEFAULT_DAO_INTERFACE_ID);
	}
	public static final String  queryValue(Class<?> daoClass ,String methodName, Context conText,Record inputMap,String daoSourceid) {
		return execute(daoClass, methodName, conText, inputMap, DaoBeanFlag.class, daoSourceid).toString();
	}
	public static final ColumnSet  querySet(Class<?> daoClass ,String methodName, Context conText,Record inputMap) {
		return querySet(daoClass, methodName, conText, inputMap, DEFAULT_DAO_INTERFACE_ID);
	}
	public static final ColumnSet  querySet(Class<?> daoClass ,String methodName, Context conText,Record inputMap,String daoSourceid) {
		return execute(daoClass, methodName, conText, inputMap, ColumnSet.class, daoSourceid);
	}
	public static final TableDate  queryTable(Class<?> daoClass ,String methodName, Context conText,Record inputMap) {
		return queryTable(daoClass, methodName, conText, inputMap, DEFAULT_DAO_INTERFACE_ID);
	}
	public static final TableDate  queryTable(Class<?> daoClass ,String methodName, Context conText,Record inputMap,String daoSourceid) {
		return execute(daoClass, methodName, conText, inputMap, TableDate.class, daoSourceid);
	}
	public static final Record  queryRecord(Class<?> daoClass ,String methodName, Context conText,Record inputMap) {
		return queryRecord(daoClass, methodName, conText, inputMap, DEFAULT_DAO_INTERFACE_ID);
	}
	public static final Record  queryRecord(Class<?> daoClass ,String methodName, Context conText,Record inputMap,String daoSourceid) {
		return execute(daoClass, methodName, conText, inputMap, Record.class, daoSourceid);
	}
	static final Map<MethodMapKey,Object> METHOD_MAP = JavaUtilFactory.newMap(JavaUtilFactory.SELECT_OF_FIELD); 
	/**
	 * @param daoClass   ִ�е�Dao��
	 * @param methodName ������ -- �������������� (ConText ,Record )
	 * @param conText	 �����ģ�ͬʱҲ��
	 * @param inputMap   
	 * @param returnClass  
	 * @param daoSourceid ����ԴID
	 * @return returnClassSub
	 */
	public static final <T extends DaoBeanFlag> T  execute(Class<?> daoClass ,String methodName, Context conText,Record inputMap,Class<T> returnClass,String daoSourceid) {
		//���
		DaoInterfaceImp daoInterface = (DaoInterfaceImp) DaoFacade.getDaoInterface(daoSourceid);
		//new map key
		MethodMapKey key = new MethodMapKey(daoClass, methodName);
		//get method or Object[]
		Object object = METHOD_MAP.get(key);
		Object returnObject = null;
		//�����һ��û�л���
		try {
			if(object == null ) {
				//����ֻ��ñ����ġ�
				Method declaredMethod = daoClass.getDeclaredMethod(methodName, Context.class , Record.class );
				//У��.
				Class<?> returnType = declaredMethod.getReturnType();
				//no List no Object[]
				if( !returnType.equals(List.class) && !returnType.equals(Object[].class) ) {
					ThrowableFacade.SYSTEM.interrupt(  "execute dao method : ", declaredMethod ,  "returnType is not List or Object[] " , "please check code");
				}
				declaredMethod.setAccessible(true);
				//������ķ���������Ƕ�̬sql 
				if( declaredMethod.getName().startsWith("no") ) {
					returnObject = execute(daoClass, conText, inputMap, returnClass, daoInterface, declaredMethod);
					//���뷽����
					METHOD_MAP.put(key, declaredMethod);
				}else {
					//ִ��dao�������ص�����
					Object invokeMethodObject = invokeMethod(daoClass, conText, inputMap, declaredMethod);
					//list to arrays
					if(invokeMethodObject instanceof List ) { 
						List list = (List) invokeMethodObject;
						invokeMethodObject = list.toArray();
					}
					returnObject = executArrasys(inputMap, returnClass, daoInterface, invokeMethodObject);
					METHOD_MAP.put(key, invokeMethodObject );
				}
			//���������ֱ��ִ��
			}else if( object instanceof Object[] ) {
				//ֱ��ִ�С�
				return executArrasys(inputMap, returnClass, daoInterface, object);
			//�����Method 
			}else if( object instanceof Method ) {
				Method method = (Method) object;
				return execute(daoClass, conText, inputMap, returnClass, daoInterface, method);
			}else{
				ThrowableFacade.SYSTEM.interrupt( "map key of ", key , "\t is error " , "please code" );
			}
		} catch (Exception e) {
			ThrowableFacade.THROW_RUN.doThrowOfRun(e);
		}
		return (T) returnObject;
	}
	protected static <T extends DaoBeanFlag> T execute(Class<?> daoClass, Context conText, Record inputMap,
			Class<T> returnClass, DaoInterfaceImp daoInterface, Method method)
			throws IllegalAccessException, InvocationTargetException, SQLException {
		Object invoke = invokeMethod(daoClass, conText, inputMap, method);
		return executeListOrArrays(inputMap, returnClass, daoInterface, invoke);
	}
	protected static <T extends DaoBeanFlag> T executeListOrArrays(Record inputMap, Class<T> returnClass,
			DaoInterfaceImp daoInterface, Object invoke) throws SQLException {
		if( invoke instanceof List ) {
			return executeList(inputMap, returnClass, daoInterface, invoke);
		}else {
			return executArrasys(inputMap, returnClass, daoInterface, invoke);
		}
	}
	protected static <T extends DaoBeanFlag> T executeList(Record inputMap, Class<T> returnClass,
			DaoInterfaceImp daoInterface, Object invoke) throws SQLException {
		List<Object> parsKeylist= (List<Object>) invoke;
		return daoInterface.execute(parsKeylist.get(parsKeylist.size()-1).toString(), daoInterface.getConnection(), returnClass, inputMap, parsKeylist);
	}
	protected static Object invokeMethod(Class<?> daoClass, Context conText, Record inputMap, Method method)
			throws IllegalAccessException, InvocationTargetException {
		Object obj = AppFactoryMan.getInstance(daoClass);
		//method parType have to conText , record
		Object args = new Object[] {conText , inputMap };
		Object invoke = method.invoke(obj, args);
		return invoke;
	}
	protected static <T extends DaoBeanFlag> T executArrasys(Record inputMap, Class<T> returnClass,
			DaoInterfaceImp daoInterface, Object object) throws SQLException {
		Object [] parsKeylist = (Object[]) object;
		return daoInterface.execute(parsKeylist[parsKeylist.length - 1].toString(), daoInterface.getConnection(), returnClass, inputMap, parsKeylist);
	}
	//���������key
	private static class MethodMapKey{
		Class<?> daoClass;
		String methodName;
		public MethodMapKey(Class<?> daoClass, String methodName) {
			super();
			this.daoClass = daoClass;
			this.methodName = methodName;
		}
		/* (non-Javadoc)
		 * @see java.lang.Object#hashCode()
		 */
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((daoClass == null) ? 0 : daoClass.hashCode());
			result = prime * result + ((methodName == null) ? 0 : methodName.hashCode());
			return result;
		}
		/* (non-Javadoc)
		 * @see java.lang.Object#equals(java.lang.Object)
		 */
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			MethodMapKey other = (MethodMapKey) obj;
			if (daoClass == null) {
				if (other.daoClass != null)
					return false;
			} else if (!daoClass.equals(other.daoClass))
				return false;
			if (methodName == null) {
				if (other.methodName != null)
					return false;
			} else if (!methodName.equals(other.methodName))
				return false;
			return true;
		}
		/* (non-Javadoc)
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString() {
			return "MethodMapKey [daoClass=" + daoClass + ", methodName=" + methodName + "]";
		}
	}
	public static final void release(ResultSet rs)  {
		AbstractDaoInterfaceImp.release(rs);
	}
	public static final void release(Statement stat)  {
		AbstractDaoInterfaceImp.release(stat);
	}
	public static final void release(Connection conn)  {
		AbstractDaoInterfaceImp.release(conn);
	}
	public static final void release(ResultSet rs,Statement stat,Connection conn)  {
		AbstractDaoInterfaceImp.release(rs, stat, conn);	
	}
	
	/**
	 * @param id
	 * @return
	 * @throws SQLException
	 * @see cn.wlh.framework.dao.DaoFacade#getThreadConnection(java.lang.Object)
	 */
	public  static Connection getThreadConnection(Object id) throws SQLException {
		return DaoFacade.getThreadConnection(id);
	}
	/**
	 * @throws SQLException
	 * @see cn.wlh.framework.dao.DaoFacade#closeThreadConnection()
	 */
	public  static void closeThreadConnection(Object id) throws SQLException {
		DaoFacade.closeThreadConnection(id);
	}
	/**
	 * @return
	 * @throws SQLException
	 * @see cn.wlh.framework.dao.DaoFacade#getDefaultThreadConnection()
	 */
	public  static Connection getDefaultThreadConnection() throws SQLException {
		return DaoFacade.getDefaultThreadConnection();
	}
	/**
	 * @throws SQLException
	 * @see cn.wlh.framework.dao.DaoFacade#closeDefaultThreadConnection()
	 */
	public  static void closeDefaultThreadConnection() throws SQLException {
		DaoFacade.closeDefaultThreadConnection();
	}
	

	/**
	 * @param sql
	 * @param con
	 * @param pars
	 * @return
	 * @throws SQLException
	 * @see cn.wlh.framework.dao.DaoFacade#update(java.lang.String, java.sql.Connection, java.lang.Object[])
	 */
	public static int update(String sql, Connection con, Object... pars) throws SQLException {
		return DaoFacade.update(sql, con, pars);
	}
	
	/**
	 * @param sql
	 * @param con
	 * @param pars
	 * @return
	 * @throws SQLException
	 * @see cn.wlh.framework.dao.DaoFacade#update(java.lang.String, java.sql.Connection, java.util.Collection)
	 */
	public static int update(String sql, Connection con, Collection<Object> pars) throws SQLException {
		return DaoFacade.update(sql, con, pars);
	}

	/**
	 * @param sql
	 * @param con
	 * @param pars
	 * @return
	 * @throws SQLException
	 * @see cn.wlh.framework.dao.DaoFacade#update(java.lang.String, java.sql.Connection, java.util.Map)
	 */
	public static int update(String sql, Connection con, Map<?, Object> pars) throws SQLException {
		return DaoFacade.update(sql, con, pars);
	}

	/**
	 * @param sql
	 * @param pars
	 * @return
	 * @throws SQLException
	 * @see cn.wlh.framework.dao.DaoFacade#update(java.lang.String, java.lang.Object[])
	 */
	public static int update(String sql, Object... pars) throws SQLException {
		return DaoFacade.update(sql, pars);
	}
	/**
	 * @param sql
	 * @param pars
	 * @return
	 * @throws SQLException
	 * @see cn.wlh.framework.dao.DaoFacade#update(java.lang.String, java.util.Collection)
	 */
	public static int update(String sql, Collection<Object> pars) throws SQLException {
		return DaoFacade.update(sql, pars);
	}

	/**
	 * @param sql
	 * @param pars
	 * @return
	 * @throws SQLException
	 * @see cn.wlh.framework.dao.DaoFacade#update(java.lang.String, java.util.Map)
	 */
	public static int update(String sql, Map<?, Object> pars) throws SQLException {
		return DaoFacade.update(sql, pars);
	}    
	
}
