package cn.wlh.util.base.adapter.servlet1;

import java.util.Map;

import javax.servlet.ServletRequest;

import cn.wlh.util.base._Exception;
import cn.wlh.util.base.adapter.java.util.JavaUtilFactory;
import cn.wlh.util.base.adapter.servlet.RequstMap;

/**
 * @author 吴灵辉 json
 * 
 */
public class Context3 extends Record {
	/*
	 * json里面的值是用于与页面交互用的. 所以尽量不要直接放入到json里面.先放入到一个的Map里面记录。
	 * 使用的时候也从里面取,但是需要当显示的时候放入到json里面。 TODO
	 * 这里有个问题，dao这里也要分层吗?可能一个交易里面biz的数据有很多,dao的数据也有很多..
	 */
	public static final int MAP_LEVEL = JavaUtilFactory.INSERT_OF_METHOD;
	
	/** 页面传过来的参数 */
	/** 页面传过来的数据,不准清空 */
	protected  RequstMap requst;
	/*
	 * protected 给系统子类预留使用..
	 */
	/** 业务Map */ // 懒加载
	protected Map<String, Object> businessMap;// = JavaUtilFactory.newMap( MAP_LEVEL );
	/** 数据Map=========== 简单类名-方法名 (唯一标识了) */
	protected Map<String, Object> daoMap = JavaUtilFactory.newMap(MAP_LEVEL);

	public Context3(/*ServletRequest source*/) {
		super(JavaUtilFactory.INSERT_OF_FIELD);
//		requst = new RequstMap(source); //这样junit测试..就通过不了了.
	}
	
	public RequstMap getRequst() {
		return requst;
	}

	public void setRequst(ServletRequest requst) {
		if( this.requst == null) 
		this.requst = new RequstMap(requst);
	}

	/**
	 * 不准用户使用.直接异常
	 */
	public static final String EXCEPTION_MASEEGE = "不准使用.直接异常";

	/**
	 * 不允许用户往下操作了
	 */
	public static void notAllowUser() {
		throw new RuntimeException(EXCEPTION_MASEEGE);
	}

	@Override
	public void putAll(Map m) {
		this.notAllowUser();
	}

	/**
	 * 清空View--Json的内容 ..
	 */
	public void clearView() {
		init(super.DEFAULT_LEVEL);
		// this.notAllowUser();
	}

	/*
	 *
	 * 
	 * 
	 * 
	 */
	public String getOne(String key) {
		return requst.getOne(key);
	}

	public String getOne(String key, int arrayIndex) {
		return requst.getOne(key, arrayIndex);
	}

	public String[] get(Object key) {
		return requst.get(key);
	}

	public String[] put(String key, String[] value) {
		return requst.put(key, value);
	}

	public RequstMap putOne(String key, String value, int arrayIndex) {
		return requst.putOne(key, value, arrayIndex);
	}

	public RequstMap putOneOfAutoAdd(String key, String value, int arrayIndex) {
		return requst.putOneOfAutoAdd(key, value, arrayIndex);
	}

	public RequstMap putOne(String key, String value) {
		return requst.putOne(key, value);
	}

	/**
	 * 页面的回显示操作,放到json-View里面.
	 */
	public static final String BACK_VIEW_KEY = "select-key"; 
	public void backView() {
//		json.putAll(requst); //这样从json里面获得某个,或者移除某个值的化性能有所下降..同时如果移除requst也很困难.
		json.put(BACK_VIEW_KEY, requst);
	}

	/*
	 * 
	 * 
	 * 
	 * 
	 */
	public void registerBiz(String key, Object value) {
		// 懒加载.
		if (businessMap == null)
			businessMap = JavaUtilFactory.newMap(MAP_LEVEL);
		businessMap.put(key, value);
	}

	public Object getBizRecord(String key) {
		return businessMap.get(key);
	}

	public Object getDaoRecord(String key) {
		return daoMap.get(key);
	}

	/**
	 * 不提供强制从view , biz, dao里面查找的结果集方法. <br/>
	 * 以免被滥用.<br/>
	 * 也就是说使用者必须清楚知道自己是从哪个地方获得的数据.<br/>
	 * 我们只对View提供了remove的操作.其他的记录集都不会被移除。<br/>
	 * 最多就是从dao中拷贝一份给view,也就保证了中途不会被篡改.从哪里放的一定可以从哪里取.<br/>
	 * 
	 * @param key
	 * @return
	 */
	public Object getRecord(String key) {
		throw new RuntimeException(EXCEPTION_MASEEGE);
	}

	////////////////////////////////////////////////////////////////////////////////////////////////
	// 给系统使用的.系统用他的子类 直接属性变protected--直接操作
	///////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * 系统用于记录Dao的结果集的
	 * 
	 * @param key
	 *            简单类名-方法名 (唯一标识了)
	 * @param value
	 *            结果集
	 */
	protected void registerDao(String key, Object value) {
		daoMap.put(key, value);
	}
	protected void clearBiz() {
		businessMap = JavaUtilFactory.newMap(MAP_LEVEL);
	}
	protected void clearDao() {
		daoMap = JavaUtilFactory.newMap(MAP_LEVEL);
	}
}
