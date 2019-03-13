package cn.wlh.framework.web.factory;

import cn.wlh.framework.dao.DaoInterface;
import cn.wlh.framework.dao.DaoManageInterface;
import cn.wlh.framework.ioc.factory.AppFactoryMan;

/**
 * @author 
 */
public abstract class DaoIOCFactory{  
	private DaoIOCFactory(){}
	private final static DaoManageInterface instance = AppFactoryMan.getInstance(DaoManageInterface.class);
	public static DaoInterface getInstance() {
		return instance.getDefaultDaoInterface();
	}
	public static DaoInterface getInstance( Object pars) {
		return instance.getDaoInterface(pars);
	}
}