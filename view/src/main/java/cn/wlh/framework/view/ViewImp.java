package cn.wlh.framework.view;

import java.util.Collection;

import cn.wlh.util.base.ClassUtil;
import cn.wlh.util.base.exception.ThrowableFacade;

public class ViewImp extends ViewInterfaceAdapt {
	private Collection<Class<?>> controllerClassSet;
	public ViewImp(Collection<Class<?>> controllerClassSet) {
//		if(controllerClassSet == null ) ThrowableFacade.SYSTEM.interrupt("controllerClassSet is null.please code");
		init(controllerClassSet);
		this.controllerClassSet = controllerClassSet;
	}
	public ViewImp(String packageName) {
		this(ClassUtil.getClassSet(packageName));
	}
	//如果子类的属性需要在父类构造方法之前执行。将不可用。2018年9月3日17:56:52
	public ViewImp(){}
	
	/**
	 * @return the controllerClassSet
	 */
	public Collection<Class<?>> getControllerClassSet() {
		return controllerClassSet;
	}
	
}
