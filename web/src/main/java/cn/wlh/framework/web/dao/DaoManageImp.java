package cn.wlh.framework.web.dao;

import java.util.Objects;

import cn.wlh.framework.dao.DaoInterface;

/**
 * @author �����
 * ����Ĭ��DefaultDaoInterface���򣬲��ʺ��ˡ�
 */
public class DaoManageImp extends cn.wlh.framework.dao.DaoManageImp {

	//���಻�����ˡ�
	@Override
	public void putDaoInterface(Object id, DaoInterface daoInterface) {
		if( daoInterface == null || id == null) {
			Objects.requireNonNull(daoInterface, "DaoInterface is null");
		}
		if(DaoHelper.DEFAULT_DAO_INTERFACE_ID.equals(id.toString())) setDefaultDaoInterface(daoInterface);
		getDaoInterfaceMap().put(id.toString(), daoInterface);
	}
	
}
