package springboot.com.wlh.dao;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.novalue.Util;

import dao.sql.Dao;
import dao.sql.Word;
import util.base._Exception;
import util.base._Method.MethodFilter;
import util.extend.Confi;
import util.extend.MethodOfPackage;

/**
 * @author wlh 语法糖. 让用户操作更简单
 */
@Component("proxy1") // 组件
public class OracleDaoOfJPA {
	@Autowired
	EntityManager manager;

	public void setManager(EntityManager manager) {
		this.manager = manager;
	}

	public Integer commit(String sql, Word word) {
		return (Integer) operationDao(sql, false, word);
	}

	// public Integer commit(String table,String method,Word word) throws
	// IllegalAccessException, IllegalArgumentException, InvocationTargetException {
	// return commit( getSql(table, method,word) ,word);
	// }
	public List<Map<String, Object>> query(String sql, Word word) {
		return (List<Map<String, Object>>) operationDao(sql, true, word);
	}

	public List<Map<String, Object>> query(String table, String method, Word word)
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		return query(getSql(table, method, word), word);
	}
      
	// public DaoProxy execute(String table,String method,Word word,String out)
	// throws IllegalAccessException, IllegalArgumentException,
	// InvocationTargetException {
	// //把输出依附在word里面...
	// word.put(out , operationDao( getSql(table, method ,word) ,(boolean) word.get(
	// Word.RS ) , word ) );
	// return this;
	// }
	/**
	 * @param sql
	 * @param isXuykRs
	 *            -- true 查询 -- false -- 事务
	 * @param words
	 * @return
	 * @throws SQLException
	 */
	protected Object operationDao(String sql, boolean isXuykRs, Object word) {
		// 获得sql语句
		if (manager == null)
			System.out.println("manager====" + manager);
		Query q = manager.createNativeQuery(sql);
		// setPara
		// 判断根据不同的类型,执行不同的操作.
		if (word != null) {
			if (word instanceof Collection) {
				// 是List的操作,通常是:word.values
				Collection words = (Collection) word;
				int i = 0;
				for (Object object : words) {
					i++;
					q.setParameter(i + 1, object);
				}
			} else if (word instanceof Object[]) {
				// 数组的操作
				Object[] words = (Object[]) word;
				int i = 0;
				int len = words.length;
				for (; i < len; i++) {
					q.setParameter(i + 1, words[i]);
				}
			} else if (word instanceof Word) {
				// Word对象的操作
				Word words = (Word) word;
				Object[] inpts = (Object[]) words.get(Word.SQL_INPUT);// 这里就需要Word注意了
				if (inpts != null) {// return ps;//防止为空.
					int i = 0;
					int len = inpts.length;
					for (; i < len; i++) {
						q.setParameter(i + 1, // 通过inpts来获得输入的值
								words.get(inpts[i]));
					}
				}
			}
		}
		// 2执行 3赋值
		if (isXuykRs) {
			q.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			return q.getResultList();
		} else {
			return q.executeUpdate();
		}
	}

	public static final MethodOfPackage.Simple methodsOfDao = _Exception
			.toRuntime(new _Exception.ToNullPointerException<MethodOfPackage.Simple>() {
				@Override
				public MethodOfPackage.Simple handle() throws Throwable {
					return new MethodOfPackage.Simple(Confi.config.getDaoAllMethodByMethodOfPackage(), false,
							MethodFilter.OBJECT);
				}
			});

	protected String getSql(String table, String method, Word word)
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		return (String) methodsOfDao.invoke(null, table, method, word);
	}

	/** 加了规定 建议用他来添加table */
	public Object execute(String table, String method, Word word, Object sqlInputs)
			throws SQLException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		// dao必须是静态方法 ,且参数是 Word 或者 无参(null)
		// Object daoInner = methodsOfDao.invoke(null, table, method, daoArgs);
		// 增加缓存,不用系统的invoke.用原生的..
		Entry<Class<?>, Map<String, Method>> map = methodsOfDao.getEntry(table);
		// 放入Word中.
		word.put(Word.DAO_ClASS, map);   
		// 使用,把Word---没有降维的.放进去
		Object daoInner = getDaoInner(map, method, word);            
		if (daoInner == null)
			return null;
		return execute(daoInner, sqlInputs);
	}

	public Object execute(String method, Word word, Object sqlInputs)
			throws SQLException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		// 使用缓存,不用系统的invoke.用原生的..
		Entry<Class<?>, Map<String, Method>> map = (Entry<Class<?>, Map<String, Method>>) word.get(Word.DAO_ClASS);
		// 使用,把Word---没有降维的.放进去
		Object daoInner = getDaoInner(map, method, word);
		if (daoInner == null)
			return null;
		return execute(daoInner, sqlInputs);
	}

	public Object execute(Word word)
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, SQLException {
		return execute((String) word.get(Word.DAO_METHOD), word, word.get(Word.SQL_INPUT));
	}

	public OracleDaoOfJPA execute(Word word, String out)
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, SQLException {
		word.put(out, execute(word));
		return this;
	}

	// daoInner -- sql语句
	public Object execute(Object daoInner, Object sqlInputs) throws SQLException {
		String sql = daoInner.toString().toUpperCase();
		// 通过sql.startsWith("SELECT")来判断.
		return operationDao(sql, sql.startsWith("SELECT"), sqlInputs);
	}

	// 为了可以使用Spring Bean Aop
	protected Object getDaoInner(Entry<Class<?>, Map<String, Method>> map, String method, Word word)
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		// 获得Spring Bean;
		Object obj = null;
		try {
			obj = Util.SpringBean.getBean(map.getKey());
		}catch(Throwable e) {
			//org.springframework.beans.factory.NoSuchBeanDefinitionException --
			//没有找到.报错
			System.out.println( e.getMessage() );
		}
		// 反射Bean..
		if (obj != null) map.getValue().get(method).invoke(obj, word);
		// 反射 静态方法..
		return map.getValue().get(method).invoke(null, word);
	}
}
