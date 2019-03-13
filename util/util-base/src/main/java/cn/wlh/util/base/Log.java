package cn.wlh.util.base;

import java.util.ResourceBundle;
import java.util.function.Supplier;
import java.util.logging.Filter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

public interface Log {

	/**
	 * @return
	 * @see java.util.logging.Logger#getResourceBundle()
	 */
	ResourceBundle getResourceBundle();

	/**
	 * @return
	 * @see java.util.logging.Logger#getResourceBundleName()
	 */
	String getResourceBundleName();

	/**
	 * @param newFilter
	 * @throws SecurityException
	 * @see java.util.logging.Logger#setFilter(java.util.logging.Filter)
	 */
	void setFilter(Filter newFilter) throws SecurityException;

	/**
	 * @return
	 * @see java.util.logging.Logger#getFilter()
	 */
	Filter getFilter();

	/**
	 * @param record
	 * @see java.util.logging.Logger#log(java.util.logging.LogRecord)
	 */
	void log(LogRecord record);

	/**
	 * @param level
	 * @param msg
	 * @see java.util.logging.Logger#log(java.util.logging.Level, java.lang.String)
	 */
	void log(Level level, String msg);

	/**
	 * @param level
	 * @param msgSupplier
	 * @see java.util.logging.Logger#log(java.util.logging.Level, java.util.function.Supplier)
	 */
	void log(Level level, Supplier<String> msgSupplier);

	/**
	 * @param level
	 * @param msg
	 * @param param1
	 * @see java.util.logging.Logger#log(java.util.logging.Level, java.lang.String, java.lang.Object)
	 */
	void log(Level level, String msg, Object param1);

	/**
	 * @param level
	 * @param msg
	 * @param params
	 * @see java.util.logging.Logger#log(java.util.logging.Level, java.lang.String, java.lang.Object[])
	 */
	void log(Level level, String msg, Object[] params);

	/**
	 * @param level
	 * @param msg
	 * @param thrown
	 * @see java.util.logging.Logger#log(java.util.logging.Level, java.lang.String, java.lang.Throwable)
	 */
	void log(Level level, String msg, Throwable thrown);

	/**
	 * @param level
	 * @param thrown
	 * @param msgSupplier
	 * @see java.util.logging.Logger#log(java.util.logging.Level, java.lang.Throwable, java.util.function.Supplier)
	 */
	void log(Level level, Throwable thrown, Supplier<String> msgSupplier);

	/**
	 * @param level
	 * @param sourceClass
	 * @param sourceMethod
	 * @param msg
	 * @see java.util.logging.Logger#logp(java.util.logging.Level, java.lang.String, java.lang.String, java.lang.String)
	 */
	void logp(Level level, String sourceClass, String sourceMethod, String msg);

	/**
	 * @param level
	 * @param sourceClass
	 * @param sourceMethod
	 * @param msgSupplier
	 * @see java.util.logging.Logger#logp(java.util.logging.Level, java.lang.String, java.lang.String, java.util.function.Supplier)
	 */
	void logp(Level level, String sourceClass, String sourceMethod, Supplier<String> msgSupplier);

	/**
	 * @param level
	 * @param sourceClass
	 * @param sourceMethod
	 * @param msg
	 * @param param1
	 * @see java.util.logging.Logger#logp(java.util.logging.Level, java.lang.String, java.lang.String, java.lang.String, java.lang.Object)
	 */
	void logp(Level level, String sourceClass, String sourceMethod, String msg, Object param1);

	/**
	 * @param level
	 * @param sourceClass
	 * @param sourceMethod
	 * @param msg
	 * @param params
	 * @see java.util.logging.Logger#logp(java.util.logging.Level, java.lang.String, java.lang.String, java.lang.String, java.lang.Object[])
	 */
	void logp(Level level, String sourceClass, String sourceMethod, String msg, Object[] params);

	/**
	 * @param level
	 * @param sourceClass
	 * @param sourceMethod
	 * @param msg
	 * @param thrown
	 * @see java.util.logging.Logger#logp(java.util.logging.Level, java.lang.String, java.lang.String, java.lang.String, java.lang.Throwable)
	 */
	void logp(Level level, String sourceClass, String sourceMethod, String msg, Throwable thrown);

	/**
	 * @param level
	 * @param sourceClass
	 * @param sourceMethod
	 * @param thrown
	 * @param msgSupplier
	 * @see java.util.logging.Logger#logp(java.util.logging.Level, java.lang.String, java.lang.String, java.lang.Throwable, java.util.function.Supplier)
	 */
	void logp(Level level, String sourceClass, String sourceMethod, Throwable thrown, Supplier<String> msgSupplier);

	/**
	 * @param level
	 * @param sourceClass
	 * @param sourceMethod
	 * @param bundleName
	 * @param msg
	 * @deprecated
	 * @see java.util.logging.Logger#logrb(java.util.logging.Level, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	void logrb(Level level, String sourceClass, String sourceMethod, String bundleName, String msg);

	/**
	 * @param level
	 * @param sourceClass
	 * @param sourceMethod
	 * @param bundleName
	 * @param msg
	 * @param param1
	 * @deprecated
	 * @see java.util.logging.Logger#logrb(java.util.logging.Level, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.Object)
	 */
	void logrb(Level level, String sourceClass, String sourceMethod, String bundleName, String msg, Object param1);

	/**
	 * @param level
	 * @param sourceClass
	 * @param sourceMethod
	 * @param bundleName
	 * @param msg
	 * @param params
	 * @deprecated
	 * @see java.util.logging.Logger#logrb(java.util.logging.Level, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.Object[])
	 */
	void logrb(Level level, String sourceClass, String sourceMethod, String bundleName, String msg, Object[] params);

	/**
	 * @param level
	 * @param sourceClass
	 * @param sourceMethod
	 * @param bundle
	 * @param msg
	 * @param params
	 * @see java.util.logging.Logger#logrb(java.util.logging.Level, java.lang.String, java.lang.String, java.util.ResourceBundle, java.lang.String, java.lang.Object[])
	 */
	void logrb(Level level, String sourceClass, String sourceMethod, ResourceBundle bundle, String msg,
			Object... params);

	/**
	 * @param level
	 * @param sourceClass
	 * @param sourceMethod
	 * @param bundleName
	 * @param msg
	 * @param thrown
	 * @deprecated
	 * @see java.util.logging.Logger#logrb(java.util.logging.Level, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.Throwable)
	 */
	void logrb(Level level, String sourceClass, String sourceMethod, String bundleName, String msg, Throwable thrown);

	/**
	 * @param level
	 * @param sourceClass
	 * @param sourceMethod
	 * @param bundle
	 * @param msg
	 * @param thrown
	 * @see java.util.logging.Logger#logrb(java.util.logging.Level, java.lang.String, java.lang.String, java.util.ResourceBundle, java.lang.String, java.lang.Throwable)
	 */
	void logrb(Level level, String sourceClass, String sourceMethod, ResourceBundle bundle, String msg,
			Throwable thrown);

	/**
	 * @param sourceClass
	 * @param sourceMethod
	 * @see java.util.logging.Logger#entering(java.lang.String, java.lang.String)
	 */
	void entering(String sourceClass, String sourceMethod);

	/**
	 * @param sourceClass
	 * @param sourceMethod
	 * @param param1
	 * @see java.util.logging.Logger#entering(java.lang.String, java.lang.String, java.lang.Object)
	 */
	void entering(String sourceClass, String sourceMethod, Object param1);

	/**
	 * @param sourceClass
	 * @param sourceMethod
	 * @param params
	 * @see java.util.logging.Logger#entering(java.lang.String, java.lang.String, java.lang.Object[])
	 */
	void entering(String sourceClass, String sourceMethod, Object[] params);

	/**
	 * @param sourceClass
	 * @param sourceMethod
	 * @see java.util.logging.Logger#exiting(java.lang.String, java.lang.String)
	 */
	void exiting(String sourceClass, String sourceMethod);

	/**
	 * @param sourceClass
	 * @param sourceMethod
	 * @param result
	 * @see java.util.logging.Logger#exiting(java.lang.String, java.lang.String, java.lang.Object)
	 */
	void exiting(String sourceClass, String sourceMethod, Object result);

	/**
	 * @param sourceClass
	 * @param sourceMethod
	 * @param thrown
	 * @see java.util.logging.Logger#throwing(java.lang.String, java.lang.String, java.lang.Throwable)
	 */
	void throwing(String sourceClass, String sourceMethod, Throwable thrown);

	/**
	 * @param msg
	 * @see java.util.logging.Logger#severe(java.lang.String)
	 */
	void severe(String msg);

	/**
	 * @param msg
	 * @see java.util.logging.Logger#warning(java.lang.String)
	 */
	void warning(String msg);

	/**
	 * @param msg
	 * @see java.util.logging.Logger#info(java.lang.String)
	 */
	void info(String msg);

	/**
	 * @param msg
	 * @see java.util.logging.Logger#config(java.lang.String)
	 */
	void config(String msg);

	/**
	 * @param msg
	 * @see java.util.logging.Logger#fine(java.lang.String)
	 */
	void fine(String msg);

	/**
	 * @param msg
	 * @see java.util.logging.Logger#finer(java.lang.String)
	 */
	void finer(String msg);

	/**
	 * @param msg
	 * @see java.util.logging.Logger#finest(java.lang.String)
	 */
	void finest(String msg);

	/**
	 * @param msgSupplier
	 * @see java.util.logging.Logger#severe(java.util.function.Supplier)
	 */
	void severe(Supplier<String> msgSupplier);

	/**
	 * @param msgSupplier
	 * @see java.util.logging.Logger#warning(java.util.function.Supplier)
	 */
	void warning(Supplier<String> msgSupplier);

	/**
	 * @param msgSupplier
	 * @see java.util.logging.Logger#info(java.util.function.Supplier)
	 */
	void info(Supplier<String> msgSupplier);

	/**
	 * @param msgSupplier
	 * @see java.util.logging.Logger#config(java.util.function.Supplier)
	 */
	void config(Supplier<String> msgSupplier);

	/**
	 * @param msgSupplier
	 * @see java.util.logging.Logger#fine(java.util.function.Supplier)
	 */
	void fine(Supplier<String> msgSupplier);

	/**
	 * @param msgSupplier
	 * @see java.util.logging.Logger#finer(java.util.function.Supplier)
	 */
	void finer(Supplier<String> msgSupplier);

	/**
	 * @param msgSupplier
	 * @see java.util.logging.Logger#finest(java.util.function.Supplier)
	 */
	void finest(Supplier<String> msgSupplier);

	/**
	 * @param newLevel
	 * @throws SecurityException
	 * @see java.util.logging.Logger#setLevel(java.util.logging.Level)
	 */
	void setLevel(Level newLevel) throws SecurityException;

	/**
	 * @return
	 * @see java.util.logging.Logger#getLevel()
	 */
	Level getLevel();

	/**
	 * @param level
	 * @return
	 * @see java.util.logging.Logger#isLoggable(java.util.logging.Level)
	 */
	boolean isLoggable(Level level);

	/**
	 * @return
	 * @see java.util.logging.Logger#getName()
	 */
	String getName();

	/**
	 * @param handler
	 * @throws SecurityException
	 * @see java.util.logging.Logger#addHandler(java.util.logging.Handler)
	 */
	void addHandler(Handler handler) throws SecurityException;

	/**
	 * @param handler
	 * @throws SecurityException
	 * @see java.util.logging.Logger#removeHandler(java.util.logging.Handler)
	 */
	void removeHandler(Handler handler) throws SecurityException;

	/**
	 * @return
	 * @see java.util.logging.Logger#getHandlers()
	 */
	Handler[] getHandlers();

	/**
	 * @param useParentHandlers
	 * @see java.util.logging.Logger#setUseParentHandlers(boolean)
	 */
	void setUseParentHandlers(boolean useParentHandlers);

	/**
	 * @return
	 * @see java.util.logging.Logger#getUseParentHandlers()
	 */
	boolean getUseParentHandlers();

	/**
	 * @param bundle
	 * @see java.util.logging.Logger#setResourceBundle(java.util.ResourceBundle)
	 */
	void setResourceBundle(ResourceBundle bundle);

	/**
	 * @return
	 * @see java.util.logging.Logger#getParent()
	 */
	Logger getParent();

	/**
	 * @param parent
	 * @see java.util.logging.Logger#setParent(java.util.logging.Logger)
	 */
	void setParent(Logger parent);

}