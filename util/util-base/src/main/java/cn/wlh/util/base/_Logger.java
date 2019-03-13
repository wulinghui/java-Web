package cn.wlh.util.base;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 吴灵辉 日志的门面模式,避免直接引用第三方jar.
 */
public class _Logger {
	protected   _Logger() {} // 单例
	
	Map<String, LoggerAdapter> map = new HashMap<String, LoggerAdapter>();
	static String defaultLogger = "0";
	static _Logger loger = new _Logger();
	public static void setDefaultLogger(String defaultLogger) {
		_Logger.defaultLogger = defaultLogger;
	}
	public static void setLoger(_Logger loger) {
		_Logger.loger = loger;
	}
	/** 放入日志对象,同时确定他的默认级别.子类重写这里的内容-改变不同的级别.*/
	public _Logger putNewInstance(LoggerAdapter obj) {
		if(obj == null) return loger;
		switch( obj.getClass().getName() ) {
			case "org.apche.log4j.adaptor":
				map.put("0", obj);
				break;
		}
		return loger;
	}
	/** 
	 * @param greade 基数为各个框架的,偶数预留给我们来实现人性化的日志.
	 * 1-10 第一个整合的框架    11-20 第二个整合的框架  21-30....
	 * 00-09 第一个整合的框架   10-19  第二个整合的框架  20-29 ....
	 * 不同的框架打印的格式不同.从而实现更人性化的日志.
	 * @param a
	 * @param replacement
	 * @return
	 */
	public _Logger log(int greade, String a, Object... replacement) {
		int _greade = greade % 10; //真正的级别
		String _key = greade / 10 + "";  //确定框架.
		map.get(_key).log(_greade, a, replacement);
		return loger;
	}
	
	public _Logger info( String a, Object... replacement) {
		map.get(defaultLogger).info(a, replacement);
		return loger;
	}

	public _Logger debug( String a, Object... replacement) {
		map.get(defaultLogger).info(a, replacement);
		return loger;
	}

	public _Logger warn( String a, Object... replacement) {
		map.get(defaultLogger).info(a, replacement);
		return loger;
	}

	public _Logger error( String a, Object... replacement) {
		map.get(defaultLogger).info(a, replacement);
		return loger;
	}
	
	/**
	 * @author 吴灵辉
	 * 用于转化各个日志框架 - 这里只处理单个框架
	 * 
	 */
	public static interface LoggerAdapter{
		/**
		 * @param greade  这里就是0-9.不能加前缀
		 * @param a
		 * @param replacement
		 */
		public void log(int greade, String a, Object... replacement) ;

		public void info( String a, Object... replacement) ;

		public  void debug( String a, Object... replacement) ;

		public  void warn( String a, Object... replacement) ;

		public  void error( String a, Object... replacement) ;
		
	}
}
