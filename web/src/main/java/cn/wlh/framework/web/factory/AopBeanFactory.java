package cn.wlh.framework.web.factory;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import cn.wlh.framework.aop.factory.AopFactory;
import cn.wlh.framework.ioc.factory.AppFactoryMan;
import cn.wlh.util.base._Field;

/**
 * @author �����
 * ����Ϊ���Ը�ֵ�������ֵ�Ǵ����������õģ������ǵݹ�Ļ�á�
 */
public class AopBeanFactory extends AopFactory {
	public AopBeanFactory(String basePackageNama) {
		this(basePackageNama,null );
	}
	public AopBeanFactory(String basePackageNama,Set<Class<?>> CAN_NEW_SET) {
		super(basePackageNama);
		this.CAN_NEW_SET = CAN_NEW_SET;
	}
	/**����new�����ͼ���*/
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
		//�Ƿ����new����
		if( CAN_NEW_SET ==null || targetClass == null || noExsits(targetClass) ) return null;
		//new 
		return newInstanceNoCheck(targetClass);
	}
	/**ע��ǧ��Ҫ���á�
	 * @param targetClass
	 * @return
	 */
	public final Object newInstanceNoCheck(Class<?> targetClass) {
		Object instance =super.newInstance(targetClass);
		addFiledValue(instance);
		return instance;
	}
	/**�Ƿ񲻴��ڼ�������
	 * @param targetClass true-������
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
//				value.set(instance, getInstance( value.getType() )); // �ݹ���
				//�������л�á�
				value.set(instance,  AppFactoryMan.getInstance(value.getType()) );
			}
		} catch (Exception e) {
			
		}
	}
	
}
