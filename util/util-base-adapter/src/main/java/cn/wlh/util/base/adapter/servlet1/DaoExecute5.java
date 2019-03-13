package cn.wlh.util.base.adapter.servlet1;

import java.sql.SQLException;

import javax.sql.DataSource;

import cn.wlh.util.base.adapter.bean.ioc.IOC1;
import cn.wlh.util.base.adapter.datasource.dbcp.DBCPUtils;
import cn.wlh.util.base.adapter.dbutils.apache.DaoMethodReturnCache.CachePreparedMap;
import cn.wlh.util.base.adapter.dbutils.apache.DaoMethodReturnCache.Sql99;
import cn.wlh.util.base.adapter.dbutils.apache.PreparedOfCache;
import cn.wlh.util.base.adapter.java.util.AdapterProperties;
import cn.wlh.util.base.adapter.servlet.Context2;
import cn.wlh.util.base.adapter.servlet.DataBus2;

/**
 * @author 吴灵辉
 * 直接执行dao内容的.
 * 使用示例...
 */
public class DaoExecute5 extends DaoExecute6{
	
	/**生产库*/
	static final String product = "product";
	static {
		DBCPUtils.putInstanceOfDataSource(product, new AdapterProperties().regsiterByArray(new Object []{
			 "userName" , "wode" 
			,"passWord" , "123456" 
			,"thin:@..." , "....."
		}));
	}
	public static final DaoExecute5 productDao = new DaoExecute5( DBCPUtils.getInstance( product ).getDataSource() );
	
	protected DaoExecute5(DataSource dataSource) {
		super(dataSource);
	}
	/////////spring mvc过来的时候..
	public static void init() {
		Context3 context2 = new Context3() {};
	}
	public static void main(String[] args) throws SQLException {
		
		DaoExecute5 productdao2 = DaoExecute5.productDao;
		Context3 context2 = new Context3();          
		UserTable latestBeanOfSingle = IOC1.getLatestBeanOfSingle(UserTable.class);       
		TableDate table2 = productdao2.getTable(latestBeanOfSingle, "getAll", new Record(), context2);
		Object object = table2.get(1).get("zz");
	}
	public static final PreparedOfCache prepared = PreparedOfCache.getInstance("");  
	//  cn.wlh.util.base.adapter.dbutils.apache.DaoMethodReturnCache.AllDaoSuperOfSingle
	public static class UserTable extends Sql99<Record>{
		@cn.wlh.util.base.adapter.dbutils.apache.NoCache
		//cn.wlh.util.base.adapter.dbutils.apache.DaoMethodReturnCache.CachePreparedMap
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
