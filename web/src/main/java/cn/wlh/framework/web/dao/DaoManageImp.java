package cn.wlh.framework.web.dao;

import java.util.Objects;

import cn.wlh.framework.dao.DaoInterface;

/**
 * @author 吴灵辉
 * 父类默认DefaultDaoInterface规则，不适合了。
 */
public class DaoManageImp extends cn.wlh.framework.dao.DaoManageImp {

	//父类不满足了。
	@Override
	public void putDaoInterface(Object id, DaoInterface daoInterface) {
		if( daoInterface == null || id == null) {
			Objects.requireNonNull(daoInterface, "DaoInterface is null");
		}
		if(DaoHelper.DEFAULT_DAO_INTERFACE_ID.equals(id.toString())) setDefaultDaoInterface(daoInterface);
		getDaoInterfaceMap().put(id.toString(), daoInterface);
	}
	
}
