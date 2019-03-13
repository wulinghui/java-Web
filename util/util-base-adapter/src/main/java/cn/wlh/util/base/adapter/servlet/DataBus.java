package cn.wlh.util.base.adapter.servlet;

import java.util.Map;

import cn.wlh.util.base.adapter.java.util.AdapterMap;
import cn.wlh.util.base.adapter.java.util.JavaUtilFactory;

public class DataBus extends AdapterMap<String,Object> {

	/**
	 * @param source
	 * @param i  @seee cn.wlh.util.base.adapter.java.util.JavaUtilFactory.newflag(int)
	 *          其余的就不new Map了
	 */
	private DataBus(Map<String, Object> source , int i) {
		super(source);
		this.newFlag = i ;
	}
	/**记录new级别的标识*/
	final int newFlag;  
	/**
	 * @param  i   @seee cn.wlh.util.base.adapter.java.util.JavaUtilFactory.newflag(int)
	 */
	public DataBus(int i) {
		this( JavaUtilFactory.newMap( i ) , i);
	}
	/**
	 *  适用于在方法体里面使用查询和修改 
	 */
	public DataBus() {
		this( JavaUtilFactory.SELECT_OF_METHOD );
	}
	@Override
	public void clear() {
		super.setSource( new DataBus(this.newFlag) );
	}
	@Override
	public void putAll(Map m) {
		if( size() == 0 ) {
			super.setSource(m);
		}
		super.putAll(m);
	}
	public void putAll0(DataBus m) {
		this.putAll(m);
	}
}
