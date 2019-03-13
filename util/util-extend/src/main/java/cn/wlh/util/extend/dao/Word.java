package cn.wlh.util.extend.dao;

import java.util.HashMap;

@SuppressWarnings("serial")
public class Word extends HashMap<String, Object> {
	public static final String SQL = "::sql::";//sql 语句
//	public static final String RS = "::isXuykRs::";//是否需要Rs结果集...   是update 还是 查询操作
	public static final String SQL_INPUT = "::SQL_INPUT::";// 执行jdbc 的 input - set
	public static final String DAO_OBJ = "::DAO_OBJ::"; //获得 dao层的对象
	// 用户不能put,由系统放入...小心多线程.
	public static final String DAO_ClASS = "::DAO_ClASS::"; //获得 dao的类..缓存Map<String,Method>
	public static final String DAO_METHOD = "::DAO_METHOD::"; //获得 dao层的方法
	public static final String DAO_METHOD_INPUT = "::DAO_METHOD_INPUT::";// 方法的输入
	public static final String VIEW_MODEL = "::VIEW_MODEL::";
	String viewHtml;
	public String getViewHtml() {
		return viewHtml;
	}
	public void setViewHtml(String viewHtml) {
		this.viewHtml = viewHtml;
	}
	 Inner viewMap = null;//new Inner( VIEW_MODEL );
	public Word putInputs(Object ...strings ){
		this.put( SQL_INPUT , strings );return this;
	}
	public Word putSql(String strings ){
		this.put( SQL , strings );return this;      
	}
//	public Word putRs(Boolean strings ){
//		this.put( RS , strings );return this;
//	}//
	public Word putDaoMethod(String strings ){
		this.put( DAO_METHOD , strings );return this;
	}
	public Word putDaoMethodInput(Object strings ){
		this.put( DAO_METHOD_INPUT , strings );return this;
	}
	public Inner getVIEW_MODEL(){
		return (Inner) this.get(VIEW_MODEL);
	}
	//全部静态方法...
//	public Word putDao_Object(Object strings ){    
//		this.put( DAO_OBJ , strings );return this;
//	}
	/////////////////////////////
	// 如果以上面的方法,如果.他在一个事务中再次使用多线程,那么上面的方案可能就有问题
	// 
	//////////////////////////////
	/**前缀*/
	public class Prefix{
		String suf;
		public Prefix(String pre) {
			this.suf = pre;
		}
		public Prefix put(String key,Object value){
			Word.this.put(suf+key, value);
//			System.out.println("ok");
			return this;
		}
	}
	/**后缀*/     
	public class Suffix{
		String suf;
		public Suffix(String suf) {
			this.suf = suf;
		}
		public Suffix put(String key,Object value){  
			Word.this.put(key + suf , value);
			return this;
		}
	}
	/** 内部的Word,寄生在Word上
	 * 当一个Word都用Suffix或这Prefix时.
	 * Word,一维的数量将庞大.2茶树查找困难..
	 * 所以用Inner增加维度.
	 * */
	public class Inner extends Word{
		Word word;
		public Inner(String key) {
			Word word = new Word();
//			this.word = word;
			// Word类中登记
			Word.this.put(key,word);
		}
	}
}
