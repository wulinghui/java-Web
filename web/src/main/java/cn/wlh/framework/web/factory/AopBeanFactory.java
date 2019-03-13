package cn.wlh.framework.web.factory;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import cn.wlh.framework.aop.factory.AopFactory;
import cn.wlh.framework.ioc.factory.AppFactoryMan;
import cn.wlh.util.base._Field;

/**
 * @author 吴灵辉
 * 他会为属性赋值。里面的值是从容器里面获得的，并不是递归的获得。
 */
public class AopBeanFactory extends AopFactory {
	public AopBeanFactory(String basePackageNama) {
		this(basePackageNama,null );
	}
	public AopBeanFactory(String basePackageNama,Set<Class<?>> CAN_NEW_SET) {
		super(basePackageNama);
		this.CAN_NEW_SET = CAN_NEW_SET;
	}
	/**可以new的类型集合*/
	final Set<Class<?>> CAN_NEW_SET;
	@Override
	public Object getInstance(Class<?> key) {
		Object instance = super.getInstance(key);
		addFiledValue(instance);
		return instance;
	}

	/* (non-Javadoc)
	 * @see cn.wlh.framework.aop.factory.AopFactory#newInstance(java.lang.Class)
	 */
	@Override
	public Object newInstance(Class<?> targetClass) {
		//是否可以new对象。
		if( CAN_NEW_SET ==null || targetClass == null || noExsits(targetClass) ) return null;
		//new 
		return newInstanceNoCheck(targetClass);
	}
	/**注意千万不要滥用。
	 * @param targetClass
	 * @return
	 */
	public final Object newInstanceNoCheck(Class<?> targetClass) {
		Object instance =super.newInstance(targetClass);
		addFiledValue(instance);
		return instance;
	}
	/**是否不存在集合里面
	 * @param targetClass true-不存在
	 * @return
	 */
	protected boolean noExsits(Class<?> targetClass ) {
		for (Class<?> class1 : CAN_NEW_SET) {
			if( targetClass.equals(class1) ) return false;
		}
		return true;
	}
	protected void addFiledValue(Object instance) {
		try {
			Map<String, Field> allFields = _Field.getAllFields(instance.getClass());
			for (Entry<String, Field> element : allFields.entrySet()) {
				Field value = element.getValue();
//				value.set(instance, getInstance( value.getType() )); // 递归获得
				//从容器中获得。
				value.set(instance,  AppFactoryMan.getInstance(value.getType()) );
			}
		} catch (Exception e) {
			
		}
	}
	
}
