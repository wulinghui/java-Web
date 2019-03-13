package cn.wlh.util.base.adapter.bean.ioc;

import java.util.Map;

import cn.wlh.util.base.adapter.java.util.JavaUtilFactory;

/**
 * @author 吴灵辉 根据降费项目的三次开发..得到以下感悟.. 前言:一开始3年，之后6年，现在十年。每次cvs提交的人名不同，原作者已经离开。
 *         看第二和第一作者的想法有些出入，我每次还得对版本，猜想法。最后总结出：里面大部分的逻辑都可以封装的--提代码的。
 *         但是第一作者没有这样子做，第二作者加入自己的逻辑走了一点弯路，到我最后都做完才知道完全可以做成一个cms提供给用户配置。
 *         这里就基于这个场景所产生的以下想法: 传统的IOC只能控制对象,不能控制基础类型。
 * 这个功能的实现:其实早就有了。如从数据库中取代码集，从配置文件中取配置。只不过这些东西，用户不能操作的，同时第一次使用的时候需要主动维护配置表。
 * 这个方法必须使用一个默认的值，如果这个对象太大时，将降低系统的运行速度。也就是说这个他适用于简单类的使用，或者默认值是已经存在的实例且链接简单--如没有从容器中查找的单例。
 * 这个方法不能滥用..
 */
public abstract class IOC2 extends IOC1 {
	/**用于缓存Map*/
	Map<String,Object> cacheMap = JavaUtilFactory.newMap(JavaUtilFactory.INSERT_OF_FIELD);
	/**从指定的存储获得内容，如果没有就返回默认值--也就是系统上线时的内容..
	 * @param id 对应数据库的主键。
	 * @param decript 描述这个是提示用户的。
	 * @param defaultValue
	 *            如果没有就返回默认值
	 * @return 匿名内部类写具体的实现,走那个数据库(或者其他存储方式)由于具体的实现类存在本系统中了我们只需要的他的信息就是一个完整的实例了(所以可以通过序列化,如果不行就只能通过反射实现了).如果没有就返回默认值
	 */
	public <T> T getSimapleBean(String id, String decript, T defaultValue) {
		String key = getKeyNameRuleUserMap(id, decript);
		//先从缓存中取..
		Object t = cacheMap.get(key);
		if( t == null ) {
			t = getSimapleBean(id, decript);
			//数据库没有--初始化
			if( t == null ) {
				t = defaultValue;
			}
			//
			cacheMap.put(key, t);
			//
			storeSimapleBean(id, decript, defaultValue);
		}else {
			return (T) t;
		}
//		T t = (T) getSimapleBean(id, decript);
		if (t == null)
			return defaultValue;
		
		return (T) t;
	}
	/**提供缓存key的匿名规则。
	 * @param id
	 * @param decript
	 * @return
	 */
	protected  String getKeyNameRuleUserMap(String id, String decript) {
		return id+decript;
	}
//	protected abstract Object getSimapleBean(String id, String decript);
	//实现示例
	/**从指定的存储获得内容
	 * @param id
	 * @param decript
	 * @return
	 */
	protected  Object getSimapleBean(String id, String decript) {
		// id ..
		Integer valueOf = Integer.valueOf(id);
		switch( valueOf ) {
			//降费年度
			case 0:
				return 3;
		}
		//decript 描述是唯一的.(一个)
		switch( decript ) {
		//降费年度
		case "降费年度":
			return 10;
		}
		return null;
	}
	/**
	 * 存储Bean
	 */
	protected abstract void storeSimapleBean(String id, String decript,Object defaultValue);
}
