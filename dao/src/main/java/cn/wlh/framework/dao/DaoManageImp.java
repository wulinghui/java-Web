package cn.wlh.framework.dao;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;

import cn.wlh.util.base.JavaUtilFactory;

public class DaoManageImp extends DaoManageInterfaceAdapt {
	public DaoManageImp() {
		super('w');
	}
	private DaoInterface defaultDaoInterface;
	private Map<String , DaoInterface> daoInterfaceMap = JavaUtilFactory.newMap(JavaUtilFactory.SELECT_OF_FIELD);
	public DaoInterface getDefaultDaoInterface() {
		return defaultDaoInterface;
	}
	
	/**
	 * @return the daoInterfaceMap
	 */
	public Map<String, DaoInterface> getDaoInterfaceMap() {
		return daoInterfaceMap;
	}

	/**
	 * @param defaultDaoInterface the defaultDaoInterface to set
	 */
	public void setDefaultDaoInterface(DaoInterface defaultDaoInterface) {
		this.defaultDaoInterface = defaultDaoInterface;
	}

	public DaoInterface getDaoInterface(Object id) {
		return daoInterfaceMap.get(id.toString());
	}

	public void putDaoInterface(Object id, DaoInterface daoInterface) {
		if( daoInterface == null || id == null) Objects.requireNonNull(daoInterface, "DaoInterface is null");
		if( defaultDaoInterface == null ) defaultDaoInterface = daoInterface;
		daoInterfaceMap.put(id.toString(), daoInterface);
	}

	public DaoInterface[] getDefaultDaoInterfaces() {
		Collection<DaoInterface> values = daoInterfaceMap.values();
		DaoInterface[] daoInterfaces = new DaoInterface[values.size()];
		values.toArray(daoInterfaces);
		return daoInterfaces;
	}

//	/** 这里有个问题，如果第一个用了'a' , 第二次用 'b'。b就不好用了用的还是a。
//	 *  这里就需要使用getDaoInterface(id).getDataSource()
//	 */
//	public Connection getThreadConnection(Object id) throws SQLException {
//		getDaoInterface(id).getThreadConnection();
//		return DaoInterfaceImp.getThreadConnection(getDaoInterface(id).getDataSource());
//	}
//
//	public void closeThreadConnection(Object id) throws SQLException {
//		DaoInterfaceImp.closeThreadConnection();
//	}
}
