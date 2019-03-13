package cn.wlh.util.base.adapter.log;

import java.util.Map;

import org.apache.commons.logging.impl.Log4JLogger;

/**
 * @author 吴灵辉
 * 这个类是
 * 实现纵向控制：在父类(系统Object)中放入一个标识用于控制该类。
	该标识的读取走数据库管理。
 */
public class AllSuperOfLog <Context extends Map>{
	boolean flagOfLog;
	Log4JLogger log;
	public void setFlagOfLog(boolean flagOfLog) {
		this.flagOfLog = flagOfLog;
	}
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//在所有的api中添加上下文,以达到横向控制	
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
	public void trace(Context context,Object message) {
		log.trace(message);
	}
	public void trace(Context context,Object message, Throwable t) {
		log.trace(message, t);
	}
	public void debug(Context context,Object message) {
		log.debug(message);
	}
	public void debug(Context context,Object message, Throwable t) {
		log.debug(message, t);
	}
	public void info(Context context,Object message) {
		log.info(message);
	}
	public void info(Context context,Object message, Throwable t) {
		log.info(message, t);
	}
	public void warn(Context context,Object message) {
		log.warn(message);
	}
	public void warn(Context context,Object message, Throwable t) {
		log.warn(message, t);
	}
	public void error(Context context,Object message) {
		log.error(message);
	}
	public void error(Context context,Object message, Throwable t) {
		log.error(message, t);
	}
	public void fatal(Context context,Object message) {
		log.fatal(message);
	}
	public void fatal(Context context,Object message, Throwable t) {
		log.fatal(message, t);
	}
	public boolean isDebugEnabled() {
		return log.isDebugEnabled();
	}
	public boolean isErrorEnabled() {
		return log.isErrorEnabled();
	}
	public boolean isFatalEnabled() {
		return log.isFatalEnabled();
	}
	public boolean isInfoEnabled() {
		return log.isInfoEnabled();
	}
	public boolean isTraceEnabled() {
		return log.isTraceEnabled();
	}
	public boolean isWarnEnabled() {
		return log.isWarnEnabled();
	}
	
}
