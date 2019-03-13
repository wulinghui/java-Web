package cn.wlh.util.base.adapter.dbutils.apache;

import java.beans.PropertyDescriptor;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;

import javax.sql.DataSource;

import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.AbstractListHandler;
import org.apache.commons.dbutils.handlers.ColumnListHandler;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import cn.wlh.util.base._Method;
import cn.wlh.util.base._Method.MethodFilter;
import cn.wlh.util.base.adapter.java.lang.AbstractStringBuilder;
import cn.wlh.util.base.adapter.java.util.AddAfterSeeListInterface;
import cn.wlh.util.base.adapter.java.util.JavaUtilFactory;

/**
 * @author 吴灵辉<br/>
 *         dao执行方法后的返回值。<br/>
 *         这里的返回值是指 sql语句和对应的索引<br/>
 *         [name, pw ,email, birth,<br/>
 *         insert into users(username,password,email,birthday) values(?,?,?,?)
 *         ]<br/>
 */
public class DaoMethodReturnCache<Context extends Map , DataBus extends Map> extends org.apache.commons.dbutils.QueryRunner {
	/*
	 * cn.wlh.util.base.adapter.dbutils.apache.GetSql2.Prepared <br/>
	 * 和cn.wlh.util.base.adapter.dbutils.apache.AnalysisSQL <br/> 他的实现,这个缓存.太占内存
	 */
	// Map<Class,List<String>> cacheMap =
	// JavaUtilFactory.newMap(JavaUtilFactory.SELECT_OF_FIELD);

	// Map<Class, CachePreparedMap> cacheMap =
	// JavaUtilFactory.newMap(JavaUtilFactory.SELECT_OF_FIELD); //@see
	// AllDaoSuperOfSingle.class

	// static Map<Class<? extends AllDaoSuperOfSingle>, AllDaoSuperOfSingle>
	// cacheMap = JavaUtilFactory.newMap(JavaUtilFactory.SELECT_OF_FIELD); //@see
	// AllDaoSuperOfSingle.class

	// public static CachePreparedMap invoke(Class cla , String methodName ,Object
	// [] objs ) {
	// //先从cache里面找...这里有个问题,如果Map变大了O(N-2)的效率将变低.不如直接反射.
	// }

	// public static CachePreparedMap invoke(AllDaoSuperOfSingle daoSingle, String
	// methodName, Object[] objs) {
	//
	// }
	public DaoMethodReturnCache(DataSource dataSource) {
		super(dataSource);
		if (dataSource == null)
			throw new NullPointerException();
	}
	/**jdk的类*/
	public void fillStatementWithBean(PreparedStatement stmt, Object bean, PropertyDescriptor[] properties)
			throws SQLException {
		// TODO Auto-generated method stub
		super.fillStatementWithBean(stmt, bean, properties);
	}

	// /*
	// * 不管是DBCPUtils.close 还是 这里的close都是归还给连接池.
	// */
	// protected void close() throws SQLException {
	// // 构造方法保证了 super.ds!=null
	// close(super.ds.getConnection());
	// } 用户归还了,还玩什么..
	public final static MapListHandler table = new MapListHandler();
	public final static ColumnListHandler<Object> set = new ColumnListHandler<Object>(1);
	public final static MapHandler record = new MapHandler();
//	public final  ResultSetHandler<DataBus> record1 = new MapHandler();
	public final static ScalarHandler value = new ScalarHandler(1);

	public List<Map<String, Object>> getTable(AllDaoSuperOfSingle daoSingle, String methodName, DataBus dataBus,
			Context context) throws SQLException {
		return this.query(daoSingle, methodName, dataBus, table, context);
	}

	public List<Object> getSet(AllDaoSuperOfSingle daoSingle, String methodName, DataBus dataBus, Context context)
			throws SQLException {
		return this.query(daoSingle, methodName, dataBus, set, context);
	}

	public Map<String, Object> getRecord(AllDaoSuperOfSingle daoSingle, String methodName, DataBus dataBus, Context context)
			throws SQLException {
		return this.query(daoSingle, methodName, dataBus, record, context);
	}

	public <T> T getValue(AllDaoSuperOfSingle daoSingle, String methodName, DataBus dataBus, Context context)
			throws SQLException {
		return (T) this.query(daoSingle, methodName, dataBus, value, context);
	}
	//////////////////////////////////////////////////////////////////////////////////
	//   connection
	/////////////////////////////////////////////////////////////////////////////////
	public List<Map<String, Object>> getTable(Connection conn,AllDaoSuperOfSingle daoSingle, String methodName, DataBus dataBus,
			Context context) throws SQLException {
		return this.query(conn,daoSingle, methodName, dataBus, table, context);
	}

	public List<Object> getSet(Connection conn,AllDaoSuperOfSingle daoSingle, String methodName, DataBus dataBus, Context context)
			throws SQLException {
		return this.query(conn,daoSingle, methodName, dataBus, set, context);
	}

	public Map<String, Object> getRecord(Connection conn,AllDaoSuperOfSingle daoSingle, String methodName, DataBus dataBus, Context context)
			throws SQLException {
		return this.query(conn,daoSingle, methodName, dataBus, record, context);
	}

	public <T> T getValue(Connection conn,AllDaoSuperOfSingle daoSingle, String methodName, DataBus dataBus, Context context)
			throws SQLException {
		return (T) this.query(conn,daoSingle, methodName, dataBus, value, context);
	}

	////////////////////////////////////////////////////////////////////////////////////
	//
	///////////////////////////////////////////////////////////////////////////////////
	protected <T> T query(Connection conn, AllDaoSuperOfSingle daoSingle, String methodName, DataBus dataBus,
			ResultSetHandler<T> rsh, Context context) throws SQLException {
		// 这里的参数还没有确定
		CachePreparedMap invoke = invoke(daoSingle, methodName, new Object[] { context, dataBus });
		Object[] params = getAllValue(invoke, dataBus);
		String sql = invoke.getFinalSql();
		return super.query(conn, sql, rsh, params);
	}

	protected <T> T query(AllDaoSuperOfSingle daoSingle, String methodName, DataBus dataBus, ResultSetHandler<T> rsh,
			Context context) throws SQLException {
		// 这里的参数还没有确定
		CachePreparedMap invoke = invoke(daoSingle, methodName, new Object[] { context, dataBus });
		Object[] params = getAllValue(invoke, dataBus);
		String sql = invoke.getFinalSql();

		return super.query(sql, rsh, params);
	}

	public int update(Connection conn, AllDaoSuperOfSingle daoSingle, String methodName, DataBus dataBus, Context context)
			throws SQLException {
		// 这里的参数还没有确定
		CachePreparedMap invoke = invoke(daoSingle, methodName, new Object[] { context, dataBus });
		Object[] params = getAllValue(invoke, dataBus);
		String sql = invoke.getFinalSql();
		return super.update(conn, sql, params);
	}

	public int update(AllDaoSuperOfSingle daoSingle, String methodName, DataBus dataBus, Context context) throws SQLException {
		// 这里的参数还没有确定
		CachePreparedMap invoke = invoke(daoSingle, methodName, new Object[] { context, dataBus });
		Object[] params = getAllValue(invoke, dataBus);
		String sql = invoke.getFinalSql();

		return super.update(sql, params);
	}

	// class Execute{
	// String sql;
	// Object[] params;
	// public Execute(String sql, Object[] params) {
	// this.sql = sql;
	// this.params = params;
	// }
	// }
	// public /*Object[]*/Execute getObjectArray(AllDaoSuperOfSingle daoSingle,
	// String methodName,Map<String,Object> dataBus) {
	// //这里的参数还没有确定
	// CachePreparedMap invoke = invoke(daoSingle, methodName , null );
	// Object [] objs = getAllValue(invoke, dataBus);
	// return new Execute(invoke.getFinalSql() , objs );
	// }
	/**
	 * 通过CachePreparedMap获得dataBus里面的值.并且排列成数组..
	 * 
	 * @param sql
	 * @param dataBus
	 * @return
	 */
	public static Object[] getAllValue(CachePreparedMap sql, Map dataBus) {
		Object[] objs = new Object[sql.getSize()];
		for (Entry<String, AddAfterSeeListInterface<Integer>> element : sql.entrySet()) {
			String key = element.getKey();// 获得key
			Object object = dataBus.get(key);// 获得执行内容.
			// 定位
			AddAfterSeeListInterface<Integer> value = element.getValue();
			int size = value.size();
			for (int i = 0; i < size; i++) {
				Integer integer = value.get(i);
				objs[integer - 1] = object;
			}
		}
		return objs;
	}

	/**
	 * @author 吴灵辉 控制saveAllKeyAndValue方法的权限..
	 */
	public static final class CachePreparedMap
			extends cn.wlh.util.base.adapter.dbutils.apache.PreparedOfCache.CachePreparedMap {
		@Override 
		public void saveAllKeyAndValue() {
			throw new RuntimeException("请不要使用该方法..这个交给系统使用..");
		}

		/**
		 * 子类也不能使用 控制saveAllKeyAndValue方法的权限..
		 */
		void saveAllKeyAndValue000() {
			super.saveAllKeyAndValue();
		}
	}

	/**
	 * 这里也就规定dao要返回CachePreparedMap且不要saveAllKeyAndValue
	 * 
	 * @param daoSingle
	 * @param methodName
	 * @param objs
	 * @return
	 */
	public static CachePreparedMap invoke(AllDaoSuperOfSingle daoSingle, String methodName, Object[] objs) {
		// CachePreparedMap cachePreparedMap = daoSingle.get(methodName);
		// if( cachePreparedMap == null ) {
		// //判断是不是需要缓存..
		// _Method.getMethod(tar, name, pars, false);
		//
		// }else if( cachePreparedMap.isEmpty() ) { //以空间换时间.不要在单独的Set<Boolean>
		// 集合里面判断了.太慢和耗内存
		// //被注解标记了.不缓存..必须反射.
		// }else {
		//
		// }
		Object object = daoSingle.get(methodName);
		// 判断是不是需要缓存..
		if (object instanceof Method) {
			// 被注解标记了.不缓存..必须反射.
			Method method = (Method) object;
			try {
				CachePreparedMap map = (CachePreparedMap) method.invoke(daoSingle, objs);
				// 没有不缓存注解..
				if (method.getAnnotation(NoCache.class) != null) {
					// save节约内存..
					// map.saveAllKeyAndValue();
					map.saveAllKeyAndValue000();
					daoSingle.put(method.getName(), map);
				}
				return map;
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				e.printStackTrace();
				throw new RuntimeException("定义或者使用方法的参数不正确");
			}
		} else if (object instanceof CachePreparedMap) {
			return (CachePreparedMap) object;
		} else {
			throw new NullPointerException("该类(不包括父类)中没有这个方法..");
		}
	}

	/**
	 * 解决上面的问题:这里有个问题,如果Map变大了O(N-2)的效率将变低.不如直接反射.<br/>
	 * 该类是单例的。 这里需要注意的是子类的方法为私有的最后..
	 */
	public static class AllDaoSuperOfSingle {
		// Map<String, CachePreparedMap> cacheMap =
		// JavaUtilFactory.newMap(JavaUtilFactory.SELECT_OF_FIELD);
		Map<String, Object> cacheMap = JavaUtilFactory.newMap(JavaUtilFactory.SELECT_OF_FIELD);

		protected AllDaoSuperOfSingle() {
			// 登记到cn.wlh.util.base.adapter.dbutils.apache.DaoMethodReturnCache.cacheMap 里面
			// DaoMethodReturnCache.cacheMap.put(this.getClass(), this); //这样还是两次遍历..
			// 这里登记所有子类自己的方法.. 同时标记也就不用是cachePreparedMap了.  true--子类的所有方法.
			_Method.getMethods(this.cacheMap, true, getClass(),  MethodFilter.NO_FILTER);
		}

		public Object get(Object key) {
			return cacheMap.get(key);
		}

		public Object put(String key, CachePreparedMap value) {
			return cacheMap.put(key, value);
		}
	}

	/**
	 * @author 吴灵辉 实现一些简单sql99的规范语句
	 */
	public static class Sql99<DataBus extends Map> extends AllDaoSuperOfSingle {
		public static final String INSERT = "INSERT INTO ";
		public static final String SELECT = "SELECT ";
		public static final String FROM = "FROM ";
		public static final String WHERE = "WHERE ";
		public static final String SET = "SET ";

		/**
		 * 正常插入 insert into tableName(key1,key2) values (?,?) 通过dataBus里面的key实现动态插入语句
		 * 
		 * @param tableName
		 * @param dataBus
		 * @return
		 */
		protected CachePreparedMap insertOfNormal(String tableName, DataBus dataBus) {
			Objects.requireNonNull(tableName);
			if (dataBus == null || dataBus.isEmpty())
				throw new NullPointerException();
			// 初始化
			StringBuilder sb00 = new StringBuilder(INSERT);
			Append sb = new Append(sb00);
			sb.append(tableName);

			sb.addLeftParenthesis();

			// 获得key1,key2 values (? , ? , ?)
			CachePreparedMap cacheMap = new CachePreparedMap();
			Iterator<Entry<String, Object>> iterable = dataBus.entrySet().iterator();
			Entry<String, Object> next = iterable.next();
			String key = next.getKey();
			Object value2 = next.getValue();
			// 添加key
			sb.append(key);
			int current = 1; //
			int keyIndex = sb.getSb().length(); // 第一个key的位置

			// map登记..
			cacheMap.putOneOfAutoAdd(key, current++);
			// 添加value
			sb.append(" VALUES ");
			sb.addLeftParenthesis();
			sb.addQuestionMark();
			//
			// int ValueIndex = sb.getSb().length();

			while (iterable.hasNext()) {
				key = next.getKey();
				value2 = next.getValue();
				// key
				sb.insertComma(keyIndex);
				sb.insert(keyIndex, key);
				keyIndex = sb.getSb().length();
				// value
				sb.addComma();
				sb.addQuestionMark();
				// map登记..
				cacheMap.putOneOfAutoAdd(key, current++);
			}
			// 补齐)
			sb.insertRightParenthesis(keyIndex);
			sb.addRightParenthesis();
			// save
			cacheMap.saveAllKeyAndValue();
			return cacheMap;
		}

		/**
		 * 插入查询出来的..表级插入 用户只需要写select语句就行. insert into tableName(key1,key2) <br/>
		 * select a.shenqingh key1, b.key2 from a,b where a.id = b.id <br/>
		 * 
		 * @param tableName
		 * @param cacheMap
		 *            这里面放入的是select语句..
		 * @return
		 */
		protected CachePreparedMap insertOfTable(String tableName, CachePreparedMap cacheMap) {
			// 获得select 语句..
			String finalSql = cacheMap.getFinalSql();
			String insertSql = null;
			// 里面的参数这里不用设置。只需要组装好sql语句内容就行。
			// 初始化内容...
			Append sb = new Append(new StringBuilder(INSERT));
			sb.append(tableName);
			sb.addLeftParenthesis();
			// 这里不采用遍历的方式。subString获得.
			// a.shenqingh key1, b.key2
			String substring = finalSql.substring(SELECT.length(), finalSql.indexOf(FROM));
			// 分割所有的 [ a.shenqingh key1 , b.key2 ,....]
			String[] split = substring.split(",");
			// 第一个
			String colName = getColName(split[0]);
			sb.append(colName);
			// 第2,3...个
			for (int i = 1; i < split.length; i++) {
				sb.addComma();
				colName = getColName(split[1]);
				sb.append(colName);
			}
			// 拼接完整的sql语句
			sb.addRightParenthesis();
			sb.append(finalSql);
			cacheMap.setFinalSql(insertSql);
			return cacheMap;
		}

		/**
		 * 动态返回select字段.
		 * 
		 * @param cols
		 * @return SELECT KEY1,KEY2 ... FROM ...自己实现..
		 */
		protected Append dynamicSelect(String... cols) {
			int len;
			Append sb = new Append(new StringBuilder(SELECT));
			// 没有就默认 select * from
			if (cols == null || (len = cols.length) == 0) {
				// throw new NullPointerException();
				// return new Append( new StringBuilder(SELECT + "* " + FROM) );
				sb.append("* ");
				sb.append(FROM);
				return sb;
			}
			// Append sb = new Append( new StringBuilder(SELECT) );
			sb.append(cols[0]);
			for (int i = 1; i < len; i++) {
				sb.addComma();
				sb.append(cols[i]);
			}
			sb.addSpace();
			sb.append(FROM);
			return sb;
		}

		/**
		 * 动态返回过滤字段.<br/>
		 * 自己实现的语句 + <br/>
		 * where key1 = ? AND key2 = ?同时在cacheMap中建好了对应的索引..<br/>
		 * 需要注意的一点.不能重复建key = ? <br/>
		 * 当删除或者DML时候使用这个方法需要特别小心，如果dataBus没有值,将不会有过滤条件。这意味着将操作整张表
		 * @param cols
		 */
		protected void dynamicWhere(CachePreparedMap cacheMap, DataBus dataBus) {
			dynamicWhere(new Append(new StringBuilder(cacheMap.getFinalSql())), cacheMap, dataBus);
		}

		/** 当删除或者DML时候使用这个方法需要特别小心，如果dataBus没有值,将不会有过滤条件。这意味着将操作整张表 */
		protected void dynamicWhere(Append sb, CachePreparedMap cacheMap, DataBus dataBus) {
			// 没有语句.
			if (dataBus == null || dataBus.isEmpty()) // throw new NullPointerException();
				return;
			// 初始化
			// Append sb = new Append( new StringBuilder(cacheMap.getFinalSql()) );
			Iterator<String> iterator = dataBus.keySet().iterator();
			sb.append(WHERE);
			int i = cacheMap.getSize();
			// 第一个
			// sb.append(iterator.next());
			// sb.addPareped();
			String key = iterator.next();
			sb.addPareped(key);
			// cacheMap.putOneOfAutoAdd(key, i++);
			// 没有使用put时i=0.对应setObject-的下标为1
			cacheMap.putOneOfAutoAdd(key, ++i);
			// 第2,3...
			while (iterator.hasNext()) {
				sb.append(" AND ");
				key = iterator.next();
				sb.addPareped(key);
				cacheMap.putOneOfAutoAdd(key, ++i);
			}
			// 放入cacheMap里面去..
			cacheMap.setFinalSql(sb.toString());
			// return cacheMap;
		}

		/**
		 * @see dynamicSet(CachePreparedMap cacheMap,Append sb , Map<String,Object>
		 *      dataBus )
		 */
		protected CachePreparedMap dynamicSet(Append sb, DataBus dataBus) {
			return dynamicSet(new CachePreparedMap(), sb, dataBus);
		}

		/**
		 * 动态返回set字段.<br/>
		 * 自己实现的语句 + <br/>
		 * SET key1 = ?,key2 = ?,同时在cacheMap中建好了对应的索引..<br/>
		 * 需要注意的一点.不能重复建key = ? <br/>
		 * 
		 * @param cols
		 * @return cacheMap --这里还没有把sql语句放进去.. 通常这里使用完了之后会使用dynamicWhere加过滤条件来使用..
		 */
		protected CachePreparedMap dynamicSet(CachePreparedMap cacheMap, Append sb, DataBus dataBus) {
			// 没有语句.
			if (dataBus == null || dataBus.isEmpty()) // throw new NullPointerException();
				return cacheMap;
			// 初始化
			Iterator<String> iterator = dataBus.keySet().iterator();
			sb.append(SET);
			int i = cacheMap.getSize() + 1;
			// 第一个
			// sb.append(iterator.next());
			// sb.addPareped();
			String key = iterator.next();
			sb.addPareped(key);
			cacheMap.putOneOfAutoAdd(key, i++);
			// 第2,3...
			while (iterator.hasNext()) {
				sb.addComma();
				key = iterator.next();
				sb.addPareped(key);
				cacheMap.putOneOfAutoAdd(key, i++);
			}
			return cacheMap;
		}

		/**
		 * 在一个完整的名称中获得真实的列名.<br/>
		 * 如:a.shenqingh key1==key1 <br/>
		 * shenqingh as key1==key1<br/>
		 * shenqingh 'key1'==key1<br/>
		 * a.shenqingh ==shenqingh<br/>
		 * 
		 * @param fullName
		 * @return
		 */
		protected String getColName(String fullName) {
			String[] split = fullName.split(" ");
			switch (split.length) {
			case 1:
				String zz[] = split[0].split(".");
				if (zz.length == 2) {
					return zz[1];
				} else {
					return zz[0];
				}
			case 2:
				return split[1].replaceAll("'", "");
			case 3:
				return split[2].replaceAll("'", "");
			default:
				return "";
			}
		}
	}

	/**
	 * @author 吴灵辉<br/>
	 *         对StringBuilder的增强..<br/>
	 *         有线程安全问题.建议只在方法中使用..<br/>
	 */
	public static class Append extends AbstractStringBuilder {
		public Append(StringBuilder sb) {
			super(sb);
		}

		/**
		 * 添加 ?
		 */
		public final void addQuestionMark() {
			append('?');
		}

		/**
		 * 添加 =?
		 */
		public final void addPareped() {
			append("=?");
		}

		/**
		 * 添加 key=?
		 * 
		 * @param key
		 */
		public final void addPareped(String key) {
			append(key);
			addPareped();
		}

		/**
		 * 添加 空格' '
		 */
		public final void addSpace() {
			append(' ');
		}

		/**
		 * 添加 ,
		 */
		public final void addComma() {
			append(',');
		}

		public final void insertComma(int index) {
			insert(index, ',');
		}

		/**
		 * 添加左括号
		 */
		public final void addLeftParenthesis() {
			append('(');
		}

		/**
		 * 添加右括号
		 * 
		 * @param sb
		 * @
		 */
		public final void addRightParenthesis() {
			append(')');
		}

		public final void insertRightParenthesis(int index) {
			insert(index, ')');
		}
	}
}
