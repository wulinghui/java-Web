package cn.wlh.util.base.adapter.dbutils.apache;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import cn.wlh.util.base.adapter.bean.ioc.IOC1;
import cn.wlh.util.base.adapter.datasource.dbcp.DBCPUtils;
import cn.wlh.util.base.adapter.java.util.AdapterProperties;
import cn.wlh.util.base.adapter.servlet.Context2;
import cn.wlh.util.base.adapter.servlet.DataBus2;
import cn.wlh.util.base.adapter.servlet.RecordSet2;

/**
 * @author 吴灵辉
 * 直接执行dao内容的.
 * 使用示例...
 */
public class DaoExecute extends DaoMethodReturnCache<Context2	, DataBus2>{
	
	/**生产库*/
	static final String product = "product";
	static {
		DBCPUtils.putInstanceOfDataSource(product, new AdapterProperties().regsiterByArray(new Object []{
			 "userName" , "wode" 
			,"passWord" , "123456" 
			,"thin:@..." , "....."
		}));
	}
	public static final DaoExecute productDao = new DaoExecute( DBCPUtils.getInstance( product ).getDataSource() );
	
	protected DaoExecute(DataSource dataSource) {
		super(dataSource);
	}
	/////////spring mvc过来的时候..
	public static void init() {
		Context2 context2 = new Context2(null) {};//实际使用的是他的子类..
	}
	@Override
	public List<Map<String, Object>> getTable(AllDaoSuperOfSingle daoSingle, String methodName, DataBus2 dataBus,
			Context2 context) throws SQLException {
		List<Map<String, Object>> table2 = super.getTable(daoSingle, methodName, dataBus, context);
		RecordSet2 set = new RecordSet2();
		set.addAll(table2);
//		return set; 无法转换..
		return null;
	}
	public static void main(String[] args) throws SQLException {
		//1.返回值太长..且没有扩展方法的使用
		//2.父类定死了..这里没法改了...传入的值可以枚举,但是返回值不能.
		//3.productdao2.table -- 把第三方jar暴露给用户了，不利于将来的迁移。
		
		DaoExecute productdao2 = DaoExecute.productDao;
		Context2 context2 = new Context2(null);
		UserTable latestBeanOfSingle = IOC1.getLatestBeanOfSingle(UserTable.class);
		List<Map<String, Object>> table2 = productdao2.getTable(latestBeanOfSingle, "getAll", new DataBus2(), context2);
//		Map<String, Object> record2 = productdao2.getRecord(new UserTable(), "getAll", new DataBus2(), context2);
		List<Map<String, Object>> query = productDao.query("select * from userTable", productdao2.table);
	}
	public static final PreparedOfCache prepared = PreparedOfCache.getInstance("");  
	public static class UserTable extends Sql99<DataBus2>{
		@NoCache
		public CachePreparedMap getAll(Context2 context , DataBus2 dateBus) {
			PreparedOfCache instance = PreparedOfCache.getInstance("");
			return  (CachePreparedMap) instance.getParameter("select * from userTable");
		}
	}
	public cn.wlh.util.base.adapter.dbutils.apache.PreparedOfCache.CachePreparedMap getAll(Context2 context , DataBus2 dateBus) {
		PreparedOfCache instance = PreparedOfCache.getInstance("");
		return  instance.getParameter("select * from userTable");
	}
}
