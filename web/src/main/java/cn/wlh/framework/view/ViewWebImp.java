package cn.wlh.framework.view;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.Collection;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import cn.wlh.framework.ioc.factory.AppFactoryMan;
import cn.wlh.framework.web.WebConfig;
import cn.wlh.framework.web.dao.DaoInterfaceWebImp;
import cn.wlh.framework.web.entity.Context;
import cn.wlh.util.base.ClassUtil;

/**
 * @author �����
 * Ĭ�Ϸ�����Ϊtxn��ͷ�ķ����Ҳ���ΪContext��View��ķ����ı�׼��   �磺 txn1234(Context);
 * ���ﲻ�ṩ����ת�������ǵ�ת������Context����ת����
 * ��Ӧִ�е�·��������/1234.����ĺ�׺
 */
public class ViewWebImp extends cn.wlh.framework.view.ViewImp {
	/**��׺��*/
	final String perfix;
	public ViewWebImp(String packageName, String perfix) {     
//		super(packageName); 2018��9��3��17:57:13s
		this.perfix = perfix;
		init(ClassUtil.getClassSet(packageName));  
	}
	/* (non-Javadoc)
	 * @see cn.wlh.framework.view.ViewInterfaceAdapt#getInvokeArgs(java.lang.reflect.Method, javax.servlet.ServletRequest, javax.servlet.ServletResponse)
	 */
	protected Object[] getInvokeArgs(Method handler, ServletRequest request, ServletResponse response) {
		Context context = new Context();
		context.setRequst(request);
		Context.THREAD_CONTEXT.set(context);
		return new Object[] {context};
	}
	/* (non-Javadoc)
	 * @see cn.wlh.framework.view.ViewInterfaceAdapt#invokeMethod(java.lang.reflect.Method, java.lang.Object, java.lang.Object[])
	 */
	@Override
	protected void invokeMethod(Method handler, Object obj, Object[] args)
			throws Throwable {
		DaoInterfaceWebImp[] allDaoSource = WebConfig.getAllDaoSource();
		try {
			super.invokeMethod(handler, obj, args);
			for (DaoInterfaceWebImp daoInterfaceWebImp : allDaoSource) {
				daoInterfaceWebImp.getConnection().commit();
			}
		} catch (Throwable e) {
			for (DaoInterfaceWebImp daoInterfaceWebImp : allDaoSource) {
				try {
					daoInterfaceWebImp.getConnection().rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
			throw e;
		}finally {
			Context.THREAD_CONTEXT.remove();
			for (DaoInterfaceWebImp daoInterfaceWebImp : allDaoSource) {
				daoInterfaceWebImp.closeThreadConnection();
			}
		}
	}

	/* (non-Javadoc)
	 * @see cn.wlh.framework.view.ViewInterfaceAdapt#getUri(javax.servlet.ServletRequest)
	 */
	@Override
	protected String getUri(ServletRequest request) {
		return super.getUri(request);
	}

	/**
	 * ͨ��IOC�������ִ�еĶ���
	 */
	@Override
	protected Object getInvokeObject(Method handler, ServletRequest request) {
		return AppFactoryMan.getInstance(handler.getDeclaringClass());
	}

	/**��Ӧִ�е�·��������/1234.����ĺ�׺
	 */
	protected String toUri(Method method) {
		Class<?> declaringClass = method.getDeclaringClass();
		String uri = declaringClass.getSimpleName() + "/";
		String name = method.getName();
		name = name.substring(3, name.length());
		return "/"+uri + name + "." + this.perfix;  
	}
	/* (non-Javadoc)
	 * @see cn.wlh.framework.view.ViewInterfaceAdapt#canInitHandler(java.lang.reflect.Method)
	 */
	@Override
	protected boolean canInitHandler(Method method) {
		Class<?>[] parameterTypes = method.getParameterTypes();
//		if(parameterTypes.length==0) return false;
		if(parameterTypes.length!=1) return false;
		return method.getName().startsWith("txn") && Context.class.equals(parameterTypes[0]);
	}
	/**
	 * @return the perfix
	 */
	public String getPerfix() {
		return perfix;
	}
	
}