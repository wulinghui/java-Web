package cn.wlh.framework.web;

import cn.wlh.framework.aop.factory.AopFactory;
import cn.wlh.framework.dao.DaoInterface;
import cn.wlh.framework.ioc.factory.AppFactoryMan;
import cn.wlh.framework.ioc.factory.SingleFactory;
import cn.wlh.framework.view.ViewInterface;
import cn.wlh.framework.view.ViewWebImp;
import cn.wlh.framework.web.dao.DaoHelper;
import cn.wlh.framework.web.dao.DaoInterfaceWebImp;
import cn.wlh.framework.web.entity.DataSourceWrapBean;
import cn.wlh.framework.web.factory.AopBeanFactory;

public abstract class WebConfig {
	public final static ViewInterface DO_POST_VIEW = new ViewWebImp("cn.wlh.framework.web.cms.view.dopost", "do");
	public final static ViewInterface DO_GET_VIEW = new ViewWebImp("cn.wlh.framework.web.cms.view.doget", "do");
	public final static ViewInterface AJAX_POST_VIEW = new ViewWebImp("cn.wlh.framework.web.cms.view.ajax.post",
			"ajax");
	public final static ViewInterface AJAX_GET_VIEW = new ViewWebImp("cn.wlh.framework.web.cms.view.ajax.get", "ajax");
	public final static AopFactory AOP_FACTORY = new AopBeanFactory("cn.wlh.framework.web.cms");
	public final static SingleFactory SINGLE_FACTORY = new SingleFactory();
	static {
		// AppFactoryMan.addFactory(new AopFactory("cn.wlh.framework"));
		AppFactoryMan.addFactory(SINGLE_FACTORY);
		AppFactoryMan.addFactory(AOP_FACTORY);
	}
	static {
		new DataSourceWrapBean(DaoHelper.DEFAULT_DAO_INTERFACE_ID, "oracle.jdbc.driver.OracleDriver",
				"jdbc:oracle:thin:@10.50.160.2:1522:DZSPSCP", "data_user", "gwssi123").init();
	}
	public static DaoInterfaceWebImp[] getAllDaoSource() {
		DaoInterface[] defaultDaoInterfaces = DaoHelper.DaoFacade.getDefaultDaoInterfaces();
		DaoInterfaceWebImp[] DaoInterfaceWebImps = new DaoInterfaceWebImp[defaultDaoInterfaces.length];
		int i = 0;
		for (DaoInterface daoInterfaceWebImp : defaultDaoInterfaces) {
			DaoInterfaceWebImps[i++] = (DaoInterfaceWebImp) daoInterfaceWebImp;
		}
		return DaoInterfaceWebImps;
	}
}
