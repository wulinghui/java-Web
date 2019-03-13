package cn.wlh.util.base.adapter.dbutils.apache;

import java.sql.SQLException;

import javax.sql.DataSource;

import cn.wlh.util.base.adapter.bean.ioc.IOC1;
import cn.wlh.util.base.adapter.datasource.dbcp.DBCPUtils;
import cn.wlh.util.base.adapter.dbutils.apache.DaoMethodReturnCache.CachePreparedMap;
import cn.wlh.util.base.adapter.dbutils.apache.DaoMethodReturnCache.Sql99;
import cn.wlh.util.base.adapter.java.util.AdapterProperties;
import cn.wlh.util.base.adapter.servlet.Context2;
import cn.wlh.util.base.adapter.servlet.DaoExecute4;
import cn.wlh.util.base.adapter.servlet.DataBus2;
import cn.wlh.util.base.adapter.servlet.RecordSet2;

/**
 * @author 吴灵辉
 * 直接执行dao内容的.
 * 使用示例...
 */
public class DaoExecute3 extends DaoExecute4{
	
	/**生产库*/
	static final String product = "product";
	static {
		DBCPUtils.putInstanceOfDataSource(product, new AdapterProperties().regsiterByArray(new Object []{
			 "userName" , "wode" 
			,"passWord" , "123456" 
			,"thin:@..." , "....."
		}));
	}
	public static final DaoExecute3 productDao = new DaoExecute3( DBCPUtils.getInstance( product ).getDataSource() );
	
	protected DaoExecute3(DataSource dataSource) {
		super(dataSource);
	}
	/////////spring mvc过来的时候..
	public static void init() {
		Context2 context2 = new Context2(null) {};
	}
	public static void main(String[] args) throws SQLException {
		
		DaoExecute3 productdao2 = DaoExecute3.productDao;
		Context2 context2 = new Context2(null);
		UserTable latestBeanOfSingle = IOC1.getLatestBeanOfSingle(UserTable.class);
		RecordSet2 table = productdao2.getTable(latestBeanOfSingle, "getAll", new DataBus2(), context2);
//		productdao2.getset
	}
	public static final PreparedOfCache prepared = PreparedOfCache.getInstance("");  
	public static class UserTable extends Sql99<DataBus2>{
		@NoCache
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
