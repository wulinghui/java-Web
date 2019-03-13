package com.cn.wlh.boot.complier.doman;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 吴灵辉
 * 这是生成XML的实体类，不适用于解析类的使用。
 */
public class XmlEntity {
	Map<String,String> global = new HashMap<>();
	Map<String,InnerXml> innerxml = new HashMap<>();
	/** 获得global的属性。
	 * @param key
	 * @return
	 */
	public String getSuper(String key) {
		return global.get(key);
	}
	/** 设置global的属性。
	 * @param key
	 * @param value
	 * @return
	 */
	public String setSuper(String key,String value) {
		return global.put(key,value);
	}
	/**获得内部的属性。
	 * @param name
	 * @return
	 */
	public InnerXml getInnerxml(String name) {
		return innerxml.get(name);
	}
		public class InnerXml{
			String superPath;
		Map<String,String> inner = new HashMap<>();
		Map<String,String> sql = new HashMap<>();
		Map<String,String> check = new HashMap<>();
		/** 
		 * @param name
		 */
		public InnerXml(String name) {
			super();
			innerxml.put(name, this);
		}
		/** value不为null才放入.
		 * @param key -- 
		 * @param inner 
		 * @param sql 
		 * @param check
		 */
		public void set( String key , String inner ,String sql ,String check  ) {
			if( inner != null ) this.inner.put(key, inner);
			if( sql != null ) this.sql.put(key, sql);
			if( check != null ) this.check.put(key, check);
		}
		public String getInner(String key) {
			return inner.get(key);
		}
		public String getSql(String key) {
			return sql.get(key);
		}
		public String getCheckr(String key) {
			return check.get(key);
		}
		public String getSuperPath() {
			return superPath;
		}
		public void setSuperPath(String superPath) {
			this.superPath = superPath;
		}
		@Override
		public String toString() {
			return "InnerXml [superPath=" + superPath + ", inner=" + inner + ", sql=" + sql + ", check=" + check + "]";
		}
		
	}
}
