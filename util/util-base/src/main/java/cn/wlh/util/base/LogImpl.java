package cn.wlh.util.base;

import java.util.ResourceBundle;
import java.util.function.Supplier;
import java.util.logging.Filter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

/**
 * @author Œ‚¡Èª‘
 *
 */ 
public class LogImpl implements Log {
	
	Logger log;
	
	 LogImpl(Logger log) {
		super();
		this.log = log;
	}
	public static Log getLogger(String name) {   
		return new LogImpl( Logger.getLogger(name) );
	}
	/**
	 * @return
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return log.hashCode();
	}

	/**
	 * @param obj
	 * @return
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object obj) {
		return log.equals(obj);
	}

	/**
	 * @return
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return log.toString();
	}

	/* (non-Javadoc)
	 * @see cn.wlh.util.base.Log#getResourceBundle()
	 */
	@Override
	public ResourceBundle getResourceBundle() {
		return log.getResourceBundle();
	}

	/* (non-Javadoc)
	 * @see cn.wlh.util.base.Log#getResourceBundleName()
	 */
	@Override
	public String getResourceBundleName() {
		return log.getResourceBundleName();
	}

	/* (non-Javadoc)
	 * @see cn.wlh.util.base.Log#setFilter(java.util.logging.Filter)
	 */
	@Override
	public void setFilter(Filter newFilter) throws SecurityException {
		log.setFilter(newFilter);
	}

	/* (non-Javadoc)
	 * @see cn.wlh.util.base.Log#getFilter()
	 */
	@Override
	public Filter getFilter() {
		return log.getFilter();
	}

	/* (non-Javadoc)
	 * @see cn.wlh.util.base.Log#log(java.util.logging.LogRecord)
	 */
	@Override
	public void log(LogRecord record) {
		log.log(record);
	}

	/* (non-Javadoc)
	 * @see cn.wlh.util.base.Log#log(java.util.logging.Level, java.lang.String)
	 */
	@Override
	public void log(Level level, String msg) {
		log.log(level, msg);
	}

	/* (non-Javadoc)
	 * @see cn.wlh.util.base.Log#log(java.util.logging.Level, java.util.function.Supplier)
	 */
	@Override
	public void log(Level level, Supplier<String> msgSupplier) {
		log.log(level, msgSupplier);
	}

	/* (non-Javadoc)
	 * @see cn.wlh.util.base.Log#log(java.util.logging.Level, java.lang.String, java.lang.Object)
	 */
	@Override
	public void log(Level level, String msg, Object param1) {
		log.log(level, msg, param1);
	}

	/* (non-Javadoc)
	 * @see cn.wlh.util.base.Log#log(java.util.logging.Level, java.lang.String, java.lang.Object[])
	 */
	@Override
	public void log(Level level, String msg, Object[] params) {
		log.log(level, msg, params);
	}

	/* (non-Javadoc)
	 * @see cn.wlh.util.base.Log#log(java.util.logging.Level, java.lang.String, java.lang.Throwable)
	 */
	@Override
	public void log(Level level, String msg, Throwable thrown) {
		log.log(level, msg, thrown);
	}

	/* (non-Javadoc)
	 * @see cn.wlh.util.base.Log#log(java.util.logging.Level, java.lang.Throwable, java.util.function.Supplier)
	 */
	@Override
	public void log(Level level, Throwable thrown, Supplier<String> msgSupplier) {
		log.log(level, thrown, msgSupplier);
	}

	/* (non-Javadoc)
	 * @see cn.wlh.util.base.Log#logp(java.util.logging.Level, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public void logp(Level level, String sourceClass, String sourceMethod, String msg) {
		log.logp(level, sourceClass, sourceMethod, msg);
	}

	/* (non-Javadoc)
	 * @see cn.wlh.util.base.Log#logp(java.util.logging.Level, java.lang.String, java.lang.String, java.util.function.Supplier)
	 */
	@Override
	public void logp(Level level, String sourceClass, String sourceMethod, Supplier<String> msgSupplier) {
		log.logp(level, sourceClass, sourceMethod, msgSupplier);
	}

	/* (non-Javadoc)
	 * @see cn.wlh.util.base.Log#logp(java.util.logging.Level, java.lang.String, java.lang.String, java.lang.String, java.lang.Object)
	 */
	@Override
	public void logp(Level level, String sourceClass, String sourceMethod, String msg, Object param1) {
		log.logp(level, sourceClass, sourceMethod, msg, param1);
	}

	/* (non-Javadoc)
	 * @see cn.wlh.util.base.Log#logp(java.util.logging.Level, java.lang.String, java.lang.String, java.lang.String, java.lang.Object[])
	 */
	@Override
	public void logp(Level level, String sourceClass, String sourceMethod, String msg, Object[] params) {
		log.logp(level, sourceClass, sourceMethod, msg, params);
	}

	/* (non-Javadoc)
	 * @see cn.wlh.util.base.Log#logp(java.util.logging.Level, java.lang.String, java.lang.String, java.lang.String, java.lang.Throwable)
	 */
	@Override
	public void logp(Level level, String sourceClass, String sourceMethod, String msg, Throwable thrown) {
		log.logp(level, sourceClass, sourceMethod, msg, thrown);
	}

	/* (non-Javadoc)
	 * @see cn.wlh.util.base.Log#logp(java.util.logging.Level, java.lang.String, java.lang.String, java.lang.Throwable, java.util.function.Supplier)
	 */
	@Override
	public void logp(Level level, String sourceClass, String sourceMethod, Throwable thrown,
			Supplier<String> msgSupplier) {
		log.logp(level, sourceClass, sourceMethod, thrown, msgSupplier);
	}

	/* (non-Javadoc)
	 * @see cn.wlh.util.base.Log#logrb(java.util.logging.Level, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public void logrb(Level level, String sourceClass, String sourceMethod, String bundleName, String msg) {
		log.logrb(level, sourceClass, sourceMethod, bundleName, msg);
	}

	/* (non-Javadoc)
	 * @see cn.wlh.util.base.Log#logrb(java.util.logging.Level, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.Object)
	 */
	@Override
	public void logrb(Level level, String sourceClass, String sourceMethod, String bundleName, String msg,
			Object param1) {
		log.logrb(level, sourceClass, sourceMethod, bundleName, msg, param1);
	}

	/* (non-Javadoc)
	 * @see cn.wlh.util.base.Log#logrb(java.util.logging.Level, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.Object[])
	 */
	@Override
	public void logrb(Level level, String sourceClass, String sourceMethod, String bundleName, String msg,
			Object[] params) {
		log.logrb(level, sourceClass, sourceMethod, bundleName, msg, params);
	}

	/* (non-Javadoc)
	 * @see cn.wlh.util.base.Log#logrb(java.util.logging.Level, java.lang.String, java.lang.String, java.util.ResourceBundle, java.lang.String, java.lang.Object)
	 */
	@Override
	public void logrb(Level level, String sourceClass, String sourceMethod, ResourceBundle bundle, String msg,
			Object... params) {
		log.logrb(level, sourceClass, sourceMethod, bundle, msg, params);
	}

	/* (non-Javadoc)
	 * @see cn.wlh.util.base.Log#logrb(java.util.logging.Level, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.Throwable)
	 */
	@Override
	public void logrb(Level level, String sourceClass, String sourceMethod, String bundleName, String msg,
			Throwable thrown) {
		log.logrb(level, sourceClass, sourceMethod, bundleName, msg, thrown);
	}

	/* (non-Javadoc)
	 * @see cn.wlh.util.base.Log#logrb(java.util.logging.Level, java.lang.String, java.lang.String, java.util.ResourceBundle, java.lang.String, java.lang.Throwable)
	 */
	@Override
	public void logrb(Level level, String sourceClass, String sourceMethod, ResourceBundle bundle, String msg,
			Throwable thrown) {
		log.logrb(level, sourceClass, sourceMethod, bundle, msg, thrown);
	}

	/* (non-Javadoc)
	 * @see cn.wlh.util.base.Log#entering(java.lang.String, java.lang.String)
	 */
	@Override
	public void entering(String sourceClass, String sourceMethod) {
		log.entering(sourceClass, sourceMethod);
	}

	/* (non-Javadoc)
	 * @see cn.wlh.util.base.Log#entering(java.lang.String, java.lang.String, java.lang.Object)
	 */
	@Override
	public void entering(String sourceClass, String sourceMethod, Object param1) {
		log.entering(sourceClass, sourceMethod, param1);
	}

	/* (non-Javadoc)
	 * @see cn.wlh.util.base.Log#entering(java.lang.String, java.lang.String, java.lang.Object[])
	 */
	@Override
	public void entering(String sourceClass, String sourceMethod, Object[] params) {
		log.entering(sourceClass, sourceMethod, params);
	}

	/* (non-Javadoc)
	 * @see cn.wlh.util.base.Log#exiting(java.lang.String, java.lang.String)
	 */
	@Override
	public void exiting(String sourceClass, String sourceMethod) {
		log.exiting(sourceClass, sourceMethod);
	}

	/* (non-Javadoc)
	 * @see cn.wlh.util.base.Log#exiting(java.lang.String, java.lang.String, java.lang.Object)
	 */
	@Override
	public void exiting(String sourceClass, String sourceMethod, Object result) {
		log.exiting(sourceClass, sourceMethod, result);
	}

	/* (non-Javadoc)
	 * @see cn.wlh.util.base.Log#throwing(java.lang.String, java.lang.String, java.lang.Throwable)
	 */
	@Override
	public void throwing(String sourceClass, String sourceMethod, Throwable thrown) {
		log.throwing(sourceClass, sourceMethod, thrown);
	}

	/* (non-Javadoc)
	 * @see cn.wlh.util.base.Log#severe(java.lang.String)
	 */
	@Override
	public void severe(String msg) {
		log.severe(msg);
	}

	/* (non-Javadoc)
	 * @see cn.wlh.util.base.Log#warning(java.lang.String)
	 */
	@Override
	public void warning(String msg) {
		log.warning(msg);
	}

	/* (non-Javadoc)
	 * @see cn.wlh.util.base.Log#info(java.lang.String)
	 */
	@Override
	public void info(String msg) {
		log.info(msg);
	}

	/* (non-Javadoc)
	 * @see cn.wlh.util.base.Log#config(java.lang.String)
	 */
	@Override
	public void config(String msg) {
		log.config(msg);
	}

	/* (non-Javadoc)
	 * @see cn.wlh.util.base.Log#fine(java.lang.String)
	 */
	@Override
	public void fine(String msg) {
		log.fine(msg);
	}

	/* (non-Javadoc)
	 * @see cn.wlh.util.base.Log#finer(java.lang.String)
	 */
	@Override
	public void finer(String msg) {
		log.finer(msg);
	}

	/* (non-Javadoc)
	 * @see cn.wlh.util.base.Log#finest(java.lang.String)
	 */
	@Override
	public void finest(String msg) {
		log.finest(msg);
	}

	/* (non-Javadoc)
	 * @see cn.wlh.util.base.Log#severe(java.util.function.Supplier)
	 */
	@Override
	public void severe(Supplier<String> msgSupplier) {
		log.severe(msgSupplier);
	}

	/* (non-Javadoc)
	 * @see cn.wlh.util.base.Log#warning(java.util.function.Supplier)
	 */
	@Override
	public void warning(Supplier<String> msgSupplier) {
		log.warning(msgSupplier);
	}

	/* (non-Javadoc)
	 * @see cn.wlh.util.base.Log#info(java.util.function.Supplier)
	 */
	@Override
	public void info(Supplier<String> msgSupplier) {
		log.info(msgSupplier);
	}

	/* (non-Javadoc)
	 * @see cn.wlh.util.base.Log#config(java.util.function.Supplier)
	 */
	@Override
	public void config(Supplier<String> msgSupplier) {
		log.config(msgSupplier);
	}

	/* (non-Javadoc)
	 * @see cn.wlh.util.base.Log#fine(java.util.function.Supplier)
	 */
	@Override
	public void fine(Supplier<String> msgSupplier) {
		log.fine(msgSupplier);
	}

	/* (non-Javadoc)
	 * @see cn.wlh.util.base.Log#finer(java.util.function.Supplier)
	 */
	@Override
	public void finer(Supplier<String> msgSupplier) {
		log.finer(msgSupplier);
	}

	/* (non-Javadoc)
	 * @see cn.wlh.util.base.Log#finest(java.util.function.Supplier)
	 */
	@Override
	public void finest(Supplier<String> msgSupplier) {
		log.finest(msgSupplier);
	}

	/* (non-Javadoc)
	 * @see cn.wlh.util.base.Log#setLevel(java.util.logging.Level)
	 */
	@Override
	public void setLevel(Level newLevel) throws SecurityException {
		log.setLevel(newLevel);
	}

	/* (non-Javadoc)
	 * @see cn.wlh.util.base.Log#getLevel()
	 */
	@Override
	public Level getLevel() {
		return log.getLevel();
	}

	/* (non-Javadoc)
	 * @see cn.wlh.util.base.Log#isLoggable(java.util.logging.Level)
	 */
	@Override
	public boolean isLoggable(Level level) {
		return log.isLoggable(level);
	}

	/* (non-Javadoc)
	 * @see cn.wlh.util.base.Log#getName()
	 */
	@Override
	public String getName() {
		return log.getName();
	}

	/* (non-Javadoc)
	 * @see cn.wlh.util.base.Log#addHandler(java.util.logging.Handler)
	 */
	@Override
	public void addHandler(Handler handler) throws SecurityException {
		log.addHandler(handler);
	}

	/* (non-Javadoc)
	 * @see cn.wlh.util.base.Log#removeHandler(java.util.logging.Handler)
	 */
	@Override
	public void removeHandler(Handler handler) throws SecurityException {
		log.removeHandler(handler);
	}

	/* (non-Javadoc)
	 * @see cn.wlh.util.base.Log#getHandlers()
	 */
	@Override
	public Handler[] getHandlers() {
		return log.getHandlers();
	}

	/* (non-Javadoc)
	 * @see cn.wlh.util.base.Log#setUseParentHandlers(boolean)
	 */
	@Override
	public void setUseParentHandlers(boolean useParentHandlers) {
		log.setUseParentHandlers(useParentHandlers);
	}

	/* (non-Javadoc)
	 * @see cn.wlh.util.base.Log#getUseParentHandlers()
	 */
	@Override
	public boolean getUseParentHandlers() {
		return log.getUseParentHandlers();
	}

	/* (non-Javadoc)
	 * @see cn.wlh.util.base.Log#setResourceBundle(java.util.ResourceBundle)
	 */
	@Override
	public void setResourceBundle(ResourceBundle bundle) {
		log.setResourceBundle(bundle);
	}

	/* (non-Javadoc)
	 * @see cn.wlh.util.base.Log#getParent()
	 */
	@Override
	public Logger getParent() {
		return log.getParent();
	}

	/* (non-Javadoc)
	 * @see cn.wlh.util.base.Log#setParent(java.util.logging.Logger)
	 */
	@Override
	public void setParent(Logger parent) {
		log.setParent(parent);
	}
	
	
}
