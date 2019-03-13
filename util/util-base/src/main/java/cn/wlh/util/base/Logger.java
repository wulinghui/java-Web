package cn.wlh.util.base;

import org.slf4j.LoggerFactory;
import org.slf4j.Marker;

public abstract class Logger {
	static int flag;//设置采用那个框架的标识。如果采用了门面模式还出现迁移困难，那么就很棒了。也就是说一个系统只采用一个日志框架。
	public static char TRACE_FLAG = 't';
	public static char INFO_FLAG = 'i';
	public static char DEBUG_FLAG = 'd';
	public static char WARN_FLAG = 'w';
	public static char ERROR_FLAG = 'e';
	
	org.slf4j.Logger LOGGER;
	
	Logger(org.slf4j.Logger lOGGER) {
		super();
		LOGGER = lOGGER;
	}

	/**
	 * @param flag the flag to set
	 */
	static void setFlag(int flag) {
		Logger.flag = flag;
	}
	public static Logger getLogger(Class<?> cla) {
		return new Logger1(LoggerFactory.getLogger(cla));
	}
	public void log(char flag , String info) {
		switch(flag) {
			default:
			case 't':
				trace(info);
			break;
			case 'i':
				info(info);
			break;
			case 'd':
				debug(info);
				break;
			case 'w':
				warn(info);
				break;
			case 'e':
				error(info);
				break;
		}
	}
	/**
	 * @param arg0
	 * @param arg1
	 * @param arg2
	 * @param arg3
	 * @see org.slf4j.Logger#debug(org.slf4j.Marker, java.lang.String, java.lang.Object, java.lang.Object)
	 */
	public void debug(Marker arg0, String arg1, Object arg2, Object arg3) {
		LOGGER.debug(arg0, arg1, arg2, arg3);
	}

	/**
	 * @param arg0
	 * @param arg1
	 * @param arg2
	 * @see org.slf4j.Logger#debug(org.slf4j.Marker, java.lang.String, java.lang.Object[])
	 */
	public void debug(Marker arg0, String arg1, Object... arg2) {
		LOGGER.debug(arg0, arg1, arg2);
	}

	/**
	 * @param arg0
	 * @param arg1
	 * @param arg2
	 * @see org.slf4j.Logger#debug(org.slf4j.Marker, java.lang.String, java.lang.Object)
	 */
	public void debug(Marker arg0, String arg1, Object arg2) {
		LOGGER.debug(arg0, arg1, arg2);
	}

	/**
	 * @param arg0
	 * @param arg1
	 * @param arg2
	 * @see org.slf4j.Logger#debug(org.slf4j.Marker, java.lang.String, java.lang.Throwable)
	 */
	public void debug(Marker arg0, String arg1, Throwable arg2) {
		LOGGER.debug(arg0, arg1, arg2);
	}

	/**
	 * @param arg0
	 * @param arg1
	 * @see org.slf4j.Logger#debug(org.slf4j.Marker, java.lang.String)
	 */
	public void debug(Marker arg0, String arg1) {
		LOGGER.debug(arg0, arg1);
	}

	/**
	 * @param arg0
	 * @param arg1
	 * @param arg2
	 * @see org.slf4j.Logger#debug(java.lang.String, java.lang.Object, java.lang.Object)
	 */
	public void debug(String arg0, Object arg1, Object arg2) {
		LOGGER.debug(arg0, arg1, arg2);
	}

	/**
	 * @param arg0
	 * @param arg1
	 * @see org.slf4j.Logger#debug(java.lang.String, java.lang.Object[])
	 */
	public void debug(String arg0, Object... arg1) {
		LOGGER.debug(arg0, arg1);
	}

	/**
	 * @param arg0
	 * @param arg1
	 * @see org.slf4j.Logger#debug(java.lang.String, java.lang.Object)
	 */
	public void debug(String arg0, Object arg1) {
		LOGGER.debug(arg0, arg1);
	}

	/**
	 * @param arg0
	 * @param arg1
	 * @see org.slf4j.Logger#debug(java.lang.String, java.lang.Throwable)
	 */
	public void debug(String arg0, Throwable arg1) {
		LOGGER.debug(arg0, arg1);
	}

	/**
	 * @param arg0
	 * @see org.slf4j.Logger#debug(java.lang.String)
	 */
	public void debug(String arg0) {
		LOGGER.debug(arg0);
	}

	/**
	 * @param arg0
	 * @param arg1
	 * @param arg2
	 * @param arg3
	 * @see org.slf4j.Logger#error(org.slf4j.Marker, java.lang.String, java.lang.Object, java.lang.Object)
	 */
	public void error(Marker arg0, String arg1, Object arg2, Object arg3) {
		LOGGER.error(arg0, arg1, arg2, arg3);
	}

	/**
	 * @param arg0
	 * @param arg1
	 * @param arg2
	 * @see org.slf4j.Logger#error(org.slf4j.Marker, java.lang.String, java.lang.Object[])
	 */
	public void error(Marker arg0, String arg1, Object... arg2) {
		LOGGER.error(arg0, arg1, arg2);
	}

	/**
	 * @param arg0
	 * @param arg1
	 * @param arg2
	 * @see org.slf4j.Logger#error(org.slf4j.Marker, java.lang.String, java.lang.Object)
	 */
	public void error(Marker arg0, String arg1, Object arg2) {
		LOGGER.error(arg0, arg1, arg2);
	}

	/**
	 * @param arg0
	 * @param arg1
	 * @param arg2
	 * @see org.slf4j.Logger#error(org.slf4j.Marker, java.lang.String, java.lang.Throwable)
	 */
	public void error(Marker arg0, String arg1, Throwable arg2) {
		LOGGER.error(arg0, arg1, arg2);
	}

	/**
	 * @param arg0
	 * @param arg1
	 * @see org.slf4j.Logger#error(org.slf4j.Marker, java.lang.String)
	 */
	public void error(Marker arg0, String arg1) {
		LOGGER.error(arg0, arg1);
	}

	/**
	 * @param arg0
	 * @param arg1
	 * @param arg2
	 * @see org.slf4j.Logger#error(java.lang.String, java.lang.Object, java.lang.Object)
	 */
	public void error(String arg0, Object arg1, Object arg2) {
		LOGGER.error(arg0, arg1, arg2);
	}

	/**
	 * @param arg0
	 * @param arg1
	 * @see org.slf4j.Logger#error(java.lang.String, java.lang.Object[])
	 */
	public void error(String arg0, Object... arg1) {
		LOGGER.error(arg0, arg1);
	}

	/**
	 * @param arg0
	 * @param arg1
	 * @see org.slf4j.Logger#error(java.lang.String, java.lang.Object)
	 */
	public void error(String arg0, Object arg1) {
		LOGGER.error(arg0, arg1);
	}

	/**
	 * @param arg0
	 * @param arg1
	 * @see org.slf4j.Logger#error(java.lang.String, java.lang.Throwable)
	 */
	public void error(String arg0, Throwable arg1) {
		LOGGER.error(arg0, arg1);
	}

	/**
	 * @param arg0
	 * @see org.slf4j.Logger#error(java.lang.String)
	 */
	public void error(String arg0) {
		LOGGER.error(arg0);
	}

	/**
	 * @return
	 * @see org.slf4j.Logger#getName()
	 */
	public String getName() {
		return LOGGER.getName();
	}

	/**
	 * @param arg0
	 * @param arg1
	 * @param arg2
	 * @param arg3
	 * @see org.slf4j.Logger#info(org.slf4j.Marker, java.lang.String, java.lang.Object, java.lang.Object)
	 */
	public void info(Marker arg0, String arg1, Object arg2, Object arg3) {
		LOGGER.info(arg0, arg1, arg2, arg3);
	}

	/**
	 * @param arg0
	 * @param arg1
	 * @param arg2
	 * @see org.slf4j.Logger#info(org.slf4j.Marker, java.lang.String, java.lang.Object[])
	 */
	public void info(Marker arg0, String arg1, Object... arg2) {
		LOGGER.info(arg0, arg1, arg2);
	}

	/**
	 * @param arg0
	 * @param arg1
	 * @param arg2
	 * @see org.slf4j.Logger#info(org.slf4j.Marker, java.lang.String, java.lang.Object)
	 */
	public void info(Marker arg0, String arg1, Object arg2) {
		LOGGER.info(arg0, arg1, arg2);
	}

	/**
	 * @param arg0
	 * @param arg1
	 * @param arg2
	 * @see org.slf4j.Logger#info(org.slf4j.Marker, java.lang.String, java.lang.Throwable)
	 */
	public void info(Marker arg0, String arg1, Throwable arg2) {
		LOGGER.info(arg0, arg1, arg2);
	}

	/**
	 * @param arg0
	 * @param arg1
	 * @see org.slf4j.Logger#info(org.slf4j.Marker, java.lang.String)
	 */
	public void info(Marker arg0, String arg1) {
		LOGGER.info(arg0, arg1);
	}

	/**
	 * @param arg0
	 * @param arg1
	 * @param arg2
	 * @see org.slf4j.Logger#info(java.lang.String, java.lang.Object, java.lang.Object)
	 */
	public void info(String arg0, Object arg1, Object arg2) {
		LOGGER.info(arg0, arg1, arg2);
	}

	/**
	 * @param arg0
	 * @param arg1
	 * @see org.slf4j.Logger#info(java.lang.String, java.lang.Object[])
	 */
	public void info(String arg0, Object... arg1) {
		LOGGER.info(arg0, arg1);
	}

	/**
	 * @param arg0
	 * @param arg1
	 * @see org.slf4j.Logger#info(java.lang.String, java.lang.Object)
	 */
	public void info(String arg0, Object arg1) {
		LOGGER.info(arg0, arg1);
	}

	/**
	 * @param arg0
	 * @param arg1
	 * @see org.slf4j.Logger#info(java.lang.String, java.lang.Throwable)
	 */
	public void info(String arg0, Throwable arg1) {
		LOGGER.info(arg0, arg1);
	}

	/**
	 * @param arg0
	 * @see org.slf4j.Logger#info(java.lang.String)
	 */
	public void info(String arg0) {
		LOGGER.info(arg0);
	}

	/**
	 * @return
	 * @see org.slf4j.Logger#isDebugEnabled()
	 */
	public boolean isDebugEnabled() {
		return LOGGER.isDebugEnabled();
	}

	/**
	 * @param arg0
	 * @return
	 * @see org.slf4j.Logger#isDebugEnabled(org.slf4j.Marker)
	 */
	public boolean isDebugEnabled(Marker arg0) {
		return LOGGER.isDebugEnabled(arg0);
	}

	/**
	 * @return
	 * @see org.slf4j.Logger#isErrorEnabled()
	 */
	public boolean isErrorEnabled() {
		return LOGGER.isErrorEnabled();
	}

	/**
	 * @param arg0
	 * @return
	 * @see org.slf4j.Logger#isErrorEnabled(org.slf4j.Marker)
	 */
	public boolean isErrorEnabled(Marker arg0) {
		return LOGGER.isErrorEnabled(arg0);
	}

	/**
	 * @return
	 * @see org.slf4j.Logger#isInfoEnabled()
	 */
	public boolean isInfoEnabled() {
		return LOGGER.isInfoEnabled();
	}

	/**
	 * @param arg0
	 * @return
	 * @see org.slf4j.Logger#isInfoEnabled(org.slf4j.Marker)
	 */
	public boolean isInfoEnabled(Marker arg0) {
		return LOGGER.isInfoEnabled(arg0);
	}

	/**
	 * @return
	 * @see org.slf4j.Logger#isTraceEnabled()
	 */
	public boolean isTraceEnabled() {
		return LOGGER.isTraceEnabled();
	}

	/**
	 * @param arg0
	 * @return
	 * @see org.slf4j.Logger#isTraceEnabled(org.slf4j.Marker)
	 */
	public boolean isTraceEnabled(Marker arg0) {
		return LOGGER.isTraceEnabled(arg0);
	}

	/**
	 * @return
	 * @see org.slf4j.Logger#isWarnEnabled()
	 */
	public boolean isWarnEnabled() {
		return LOGGER.isWarnEnabled();
	}

	/**
	 * @param arg0
	 * @return
	 * @see org.slf4j.Logger#isWarnEnabled(org.slf4j.Marker)
	 */
	public boolean isWarnEnabled(Marker arg0) {
		return LOGGER.isWarnEnabled(arg0);
	}

	/**
	 * @param arg0
	 * @param arg1
	 * @param arg2
	 * @param arg3
	 * @see org.slf4j.Logger#trace(org.slf4j.Marker, java.lang.String, java.lang.Object, java.lang.Object)
	 */
	public void trace(Marker arg0, String arg1, Object arg2, Object arg3) {
		LOGGER.trace(arg0, arg1, arg2, arg3);
	}

	/**
	 * @param arg0
	 * @param arg1
	 * @param arg2
	 * @see org.slf4j.Logger#trace(org.slf4j.Marker, java.lang.String, java.lang.Object[])
	 */
	public void trace(Marker arg0, String arg1, Object... arg2) {
		LOGGER.trace(arg0, arg1, arg2);
	}

	/**
	 * @param arg0
	 * @param arg1
	 * @param arg2
	 * @see org.slf4j.Logger#trace(org.slf4j.Marker, java.lang.String, java.lang.Object)
	 */
	public void trace(Marker arg0, String arg1, Object arg2) {
		LOGGER.trace(arg0, arg1, arg2);
	}

	/**
	 * @param arg0
	 * @param arg1
	 * @param arg2
	 * @see org.slf4j.Logger#trace(org.slf4j.Marker, java.lang.String, java.lang.Throwable)
	 */
	public void trace(Marker arg0, String arg1, Throwable arg2) {
		LOGGER.trace(arg0, arg1, arg2);
	}

	/**
	 * @param arg0
	 * @param arg1
	 * @see org.slf4j.Logger#trace(org.slf4j.Marker, java.lang.String)
	 */
	public void trace(Marker arg0, String arg1) {
		LOGGER.trace(arg0, arg1);
	}

	/**
	 * @param arg0
	 * @param arg1
	 * @param arg2
	 * @see org.slf4j.Logger#trace(java.lang.String, java.lang.Object, java.lang.Object)
	 */
	public void trace(String arg0, Object arg1, Object arg2) {
		LOGGER.trace(arg0, arg1, arg2);
	}

	/**
	 * @param arg0
	 * @param arg1
	 * @see org.slf4j.Logger#trace(java.lang.String, java.lang.Object[])
	 */
	public void trace(String arg0, Object... arg1) {
		LOGGER.trace(arg0, arg1);
	}

	/**
	 * @param arg0
	 * @param arg1
	 * @see org.slf4j.Logger#trace(java.lang.String, java.lang.Object)
	 */
	public void trace(String arg0, Object arg1) {
		LOGGER.trace(arg0, arg1);
	}

	/**
	 * @param arg0
	 * @param arg1
	 * @see org.slf4j.Logger#trace(java.lang.String, java.lang.Throwable)
	 */
	public void trace(String arg0, Throwable arg1) {
		LOGGER.trace(arg0, arg1);
	}

	/**
	 * @param arg0
	 * @see org.slf4j.Logger#trace(java.lang.String)
	 */
	public void trace(String arg0) {
		LOGGER.trace(arg0);
	}

	/**
	 * @param arg0
	 * @param arg1
	 * @param arg2
	 * @param arg3
	 * @see org.slf4j.Logger#warn(org.slf4j.Marker, java.lang.String, java.lang.Object, java.lang.Object)
	 */
	public void warn(Marker arg0, String arg1, Object arg2, Object arg3) {
		LOGGER.warn(arg0, arg1, arg2, arg3);
	}

	/**
	 * @param arg0
	 * @param arg1
	 * @param arg2
	 * @see org.slf4j.Logger#warn(org.slf4j.Marker, java.lang.String, java.lang.Object[])
	 */
	public void warn(Marker arg0, String arg1, Object... arg2) {
		LOGGER.warn(arg0, arg1, arg2);
	}

	/**
	 * @param arg0
	 * @param arg1
	 * @param arg2
	 * @see org.slf4j.Logger#warn(org.slf4j.Marker, java.lang.String, java.lang.Object)
	 */
	public void warn(Marker arg0, String arg1, Object arg2) {
		LOGGER.warn(arg0, arg1, arg2);
	}

	/**
	 * @param arg0
	 * @param arg1
	 * @param arg2
	 * @see org.slf4j.Logger#warn(org.slf4j.Marker, java.lang.String, java.lang.Throwable)
	 */
	public void warn(Marker arg0, String arg1, Throwable arg2) {
		LOGGER.warn(arg0, arg1, arg2);
	}

	/**
	 * @param arg0
	 * @param arg1
	 * @see org.slf4j.Logger#warn(org.slf4j.Marker, java.lang.String)
	 */
	public void warn(Marker arg0, String arg1) {
		LOGGER.warn(arg0, arg1);
	}

	/**
	 * @param arg0
	 * @param arg1
	 * @param arg2
	 * @see org.slf4j.Logger#warn(java.lang.String, java.lang.Object, java.lang.Object)
	 */
	public void warn(String arg0, Object arg1, Object arg2) {
		LOGGER.warn(arg0, arg1, arg2);
	}

	/**
	 * @param arg0
	 * @param arg1
	 * @see org.slf4j.Logger#warn(java.lang.String, java.lang.Object[])
	 */
	public void warn(String arg0, Object... arg1) {
		LOGGER.warn(arg0, arg1);
	}

	/**
	 * @param arg0
	 * @param arg1
	 * @see org.slf4j.Logger#warn(java.lang.String, java.lang.Object)
	 */
	public void warn(String arg0, Object arg1) {
		LOGGER.warn(arg0, arg1);
	}

	/**
	 * @param arg0
	 * @param arg1
	 * @see org.slf4j.Logger#warn(java.lang.String, java.lang.Throwable)
	 */
	public void warn(String arg0, Throwable arg1) {
		LOGGER.warn(arg0, arg1);
	}

	/**
	 * @param arg0
	 * @see org.slf4j.Logger#warn(java.lang.String)
	 */
	public void warn(String arg0) {
		LOGGER.warn(arg0);
	}
	
}
