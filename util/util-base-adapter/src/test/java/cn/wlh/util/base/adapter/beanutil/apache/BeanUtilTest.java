package cn.wlh.util.base.adapter.beanutil.apache;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.BeanUtilsBean2;
import org.apache.log4j.Logger;

public class BeanUtilTest extends BeanUtils {
	Logger log;
	
	/**
	 * @param message
	 * @param t
	 * @see org.apache.log4j.Category#info(java.lang.Object, java.lang.Throwable)
	 */
	public void info(Object message, Throwable t) {
		log.info(message, t);
	}

	//BeanUtilsBean 与  BeanUtilsBean2 的用处
	public void test() {
		BeanUtilsBean.getInstance();
		// 继承BeanUtilsBean
		BeanUtilsBean2.getInstance();
		//他们只是对BeanUtils的增强.不是继承关系.
	}
}
