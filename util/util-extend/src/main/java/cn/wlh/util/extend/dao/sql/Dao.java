package cn.wlh.util.extend.dao.sql;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import cn.wlh.util.base._Exception;
import cn.wlh.util.base._Exception.ToNullPointerException;
import cn.wlh.util.base._Method.MethodFilter;
import cn.wlh.util.extend.Confi;
import cn.wlh.util.extend.MethodOfPackage;
import cn.wlh.util.extend.dao.DaoProxy;
import cn.wlh.util.extend.dao.Word;
import cn.wlh.util.extend.dao.sql.oracle.OraclePreStatSql;
/**
 * @author wlh 
 * 语法糖.   让用户操作更简单
 */
public class Dao {

	OraclePreStatSql dao;

	public Dao(String sourceData) {
		dao = DaoProxy.get(OraclePreStatSql.class, sourceData);
	}

	public Integer commit(String sql, Object word) throws SQLException {
		return (Integer) dao.operationDao(sql, false, word);
	}

	public List<Map<String, Object>> query(String sql, Object word) throws SQLException {
		return (List<Map<String, Object>>) dao.operationDao(sql, true, word);
	}

	
/*
 * 
 *  建议用下面的方法...
 * 	
 */
	public static final MethodOfPackage.Simple methodsOfDao = _Exception
			.toRuntime(new ToNullPointerException<MethodOfPackage.Simple>() {
				@Override
				public MethodOfPackage.Simple handle() throws Throwable {   
					return new MethodOfPackage.Simple(Confi.config.getDaoAllMethodByMethodOfPackage(), false ,MethodFilter.OBJECT );
				}
			});      
	@Deprecated
	protected String getSql(String table, String method, Word word)
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Object obj = word.get(Word.DAO_METHOD_INPUT);//太死板
		
//		Object args [] = obj == null ? new Object[0] : (Object[]) obj; 
		return (String) methodsOfDao.invoke( word.get(Word.DAO_OBJ),//
				table, method,  obj );//        
	}

	public List<Map<String, Object>> query(String table, String method, Word word)
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, SQLException {
		return query(getSql(table, method, word), word.get(Word.SQL_INPUT)  );
	}

	public Dao execute(String table, String method, Word word, String out)
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, SQLException {
		// 把输出依附在word里面...
//		word.put(out, dao.operationDao(getSql(table, method, word), (Boolean) word.get(Word.RS), word.get(Word.DAO_INPUT) ) );
		String str = getSql(table, method, word).toUpperCase();
		word.put(out, dao.operationDao( str , str.startsWith("SELECT") , word.get(Word.SQL_INPUT) ) );
		return this;
	}
	/**代替 getSql()*///完整的
	public Object execute(String table, String method, Object daoObj ,
			Object[] daoArgs , boolean isXuykRs, Object sqlInputs) throws SQLException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		Object daoInner = methodsOfDao.invoke(daoObj, table, method, daoArgs);
		if(daoInner ==null) return null;
		return dao.operationDao(daoInner.toString(), isXuykRs, sqlInputs);
	}
	/** 加了规定 建议用他来添加table*/
	public Object execute(String table, String method, 
			Word word , Object sqlInputs) throws SQLException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		//dao必须是静态方法	,且参数是 Word 或者   无参(null)
//		Object daoInner = methodsOfDao.invoke(null, table, method, daoArgs);
		//增加缓存,不用系统的invoke.用原生的..
		Entry<Class<?>, Map<String, Method>> map =  methodsOfDao.getEntry(table);
		//放入Word中.
		word.put( Word.DAO_ClASS, map );
		//使用,把Word---没有降维的.放进去
		Object daoInner =  getDaoInner(map, method, word);//map.get(method).invoke(null,  word );
		if(daoInner ==null) return null;
		return execute( daoInner , sqlInputs);
	}
	public Object execute( String method, 
			Word word , Object sqlInputs) throws SQLException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		//使用缓存,不用系统的invoke.用原生的..
		Entry<Class<?>, Map<String, Method>> map =  (Entry<Class<?>, Map<String, Method>>) word.get(Word.DAO_ClASS);
		//使用,把Word---没有降维的.放进去
		Object daoInner = getDaoInner(map, method, word);//map.get(method).invoke(null,  word );
		if(daoInner ==null) return null;
		return execute( daoInner , sqlInputs);
	}
	//为了子类可以使用Spring Bean Aop
	protected Object getDaoInner( Entry<Class<?>, Map<String, Method>> map , String method ,Word word) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		//反射 静态方法..
		return map.getValue().get(method).invoke(null,  word );
	}
	public Object execute(Word word) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, SQLException {
		return  execute( (String) word.get(Word.DAO_METHOD) ,  word , word.get( Word.SQL_INPUT ) );
	}
	public Dao execute(Word word,String out) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, SQLException {
		word.put(out, execute(word) ); return this;
	}   
		
		
	//daoInner -- sql语句
	public Object execute(Object daoInner, Object sqlInputs) throws SQLException {
		String sql = daoInner.toString().toUpperCase();
		// 通过sql.startsWith("SELECT")来判断.
		return dao.operationDao( sql , sql.startsWith("SELECT") , sqlInputs);
	}
	
	public Integer commit(String table, String method, Word word)
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, SQLException {
		return commit(getSql(table, method, word), word.get(Word.SQL_INPUT) );
	}
}