package cn.wlh.util.base.adapter.bean.ioc;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import cn.wlh.util.base._Field;
import cn.wlh.util.base._String;
import cn.wlh.util.base.adapter.java.util.JavaUtilFactory;
import cn.wlh.util.base.interfaces._Filter;

public abstract class AbstractFactory1<T> {
	
	public static class ClientOfIOC{
		public static void main(String[] args) {
			//这两个在属性不变的时候是等价的。 一旦变化就无法控制了
			List bean = IOC1.getBean(AbstractFactory1OfMedo.class	,  "-1" );
			List bean1 = IOC1.getBean(AbstractFactory1OfMedo.class	,  AbstractFactory1OfMedo.SELECT_OF_METHOD);
			//这个在系统可以在某个时间点变化系统级别的new对象方式。。
			List bean2 = IOC1.getBeanByFactoryField(AbstractFactory1OfMedo.class	, "SELECT_OF_METHOD" );
			
		}
	}
	/**
	 * @author 吴灵辉
	 * 使用示例.. new IFactory 的实现类.的工厂类..
	 */
	public static class AbstractFactory1OfMedo extends AbstractFactory1<List>{
		static {
			try {
				IOC1.register(new AbstractFactory1OfMedo() );
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		/** 适用于在方法体里面使用查询和修改 */ 
		public static final String SELECT_OF_METHOD = "-1";
		/** 适用于在属性级别里面使用查询和修改 */
		public static final String SELECT_OF_FIELD = "2";
		/** 适用于在方法体里面使用增删 */
		public static final String INSERT_OF_METHOD = "1";
		/** 适用于在属性级别里面使用增删 */
		public static final String INSERT_OF_FIELD = "-2";
		
		public AbstractFactory1OfMedo() throws Exception {
			super(List.class);
		}
		public List getBean(String id) {
			switch(id) {
			case "-1":
				return new ArrayList();
			case "2":
				return new LinkedList<>();
			case "1":
				return new ArrayList();
			case "-2":
				return new LinkedList<>();
			}
			return new ArrayList();
		}
	}
	
	final Class<T> interfac;

	/** 使用者的标识集合 */
	final Map<String, String> falgSet ;
	public AbstractFactory1(Class<T> interfac) throws Exception {
		this(interfac, JavaUtilFactory.newMap(JavaUtilFactory.SELECT_OF_METHOD) );
		//把属性放到falgSet里面..
		_Field.getObjectFieldValues(falgSet, false, this, fi);
	}
	public AbstractFactory1(Class<T> interfac, Map<String, String> falgSet) {
		super();
		this.interfac = interfac;
		this.falgSet = falgSet;
	}

	public Class<T> getInterfac() {
		return interfac;
	}

	

	// 获得所有公开静态常量的key和value
	static _Filter<Field> fi = new _Filter<Field>() {
		public boolean accept(Field t) {
			return !_String.lookUp(new String[] { "fi", "interfac", "falgSet" }, t.getName());
		}
	};
	/** 通过属性名获得值.
	 * @param field
	 * @return
	 */
	public String getValue(String field) {
		return this.falgSet.get(field).toString();
	}
	public Map<String, String> getFieldAndValue(){
		Map<String, String> map = JavaUtilFactory.newMap(JavaUtilFactory.SELECT_OF_METHOD);
		map.putAll( this.falgSet);
		return map;
	}

	/**
	 * 标识内容互换。
	 * @param key1
	 * @param key2
	 */
	public void falgInterchange(String key1, String key2) {
		valitater(key1);
		valitater(key2);
		String string = this.falgSet.get(key1);
		this.falgSet.put(key1, this.falgSet.get(key2));
		this.falgSet.put(key2, string);
	}

	private void valitater(String key) {
		if (this.falgSet.get(key) == null)
			throw new NullPointerException();
	}

	public abstract T getBean(String id);

}