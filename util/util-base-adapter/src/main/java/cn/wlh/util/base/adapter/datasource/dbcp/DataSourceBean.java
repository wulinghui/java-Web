package cn.wlh.util.base.adapter.datasource.dbcp;

import cn.wlh.util.base.adapter.java.util.AdapterProperties;

public class DataSourceBean extends AdapterProperties {

	/***/
	public static final long serialVersionUID = -7941694332113344603L;
	
	public DataSourceBean(String driverClassName,String url,String username,String password) {
		put(PROP_DRIVERCLASSNAME, driverClassName);
		put(PROP_URL, url);
		put(PROP_USERNAME, username);
		put(PROP_PASSWORD, password);
	}
    public static final String PROP_DEFAULTAUTOCOMMIT = "defaultAutoCommit";
    public static final String PROP_DEFAULTREADONLY = "defaultReadOnly";
    public static final String PROP_DEFAULTTRANSACTIONISOLATION = "defaultTransactionIsolation";
    public static final String PROP_DEFAULTCATALOG = "defaultCatalog";
    public static final String PROP_DEFAULTSCHEMA = "defaultSchema";
    public static final String PROP_CACHESTATE = "cacheState";
    public static final String PROP_DRIVERCLASSNAME = "driverClassName";
    public static final String PROP_LIFO = "lifo";
    public static final String PROP_MAXTOTAL = "maxTotal";
    public static final String PROP_MAXIDLE = "maxIdle";
    public static final String PROP_MINIDLE = "minIdle";
    public static final String PROP_INITIALSIZE = "initialSize";
    public static final String PROP_MAXWAITMILLIS = "maxWaitMillis";
    public static final String PROP_TESTONCREATE = "testOnCreate";
    public static final String PROP_TESTONBORROW = "testOnBorrow";
    public static final String PROP_TESTONRETURN = "testOnReturn";
    public static final String PROP_TIMEBETWEENEVICTIONRUNSMILLIS = "timeBetweenEvictionRunsMillis";
    public static final String PROP_NUMTESTSPEREVICTIONRUN = "numTestsPerEvictionRun";
    public static final String PROP_MINEVICTABLEIDLETIMEMILLIS = "minEvictableIdleTimeMillis";
    public static final String PROP_SOFTMINEVICTABLEIDLETIMEMILLIS = "softMinEvictableIdleTimeMillis";
    public static final String PROP_EVICTIONPOLICYCLASSNAME = "evictionPolicyClassName";
    public static final String PROP_TESTWHILEIDLE = "testWhileIdle";
    public static final String PROP_PASSWORD = "password";
    public static final String PROP_URL = "url";
    public static final String PROP_USERNAME = "username";
    public static final String PROP_VALIDATIONQUERY = "validationQuery";
    public static final String PROP_VALIDATIONQUERY_TIMEOUT = "validationQueryTimeout";
    public static final String PROP_JMX_NAME = "jmxName";

    /**
     * The property name for connectionInitSqls. The associated value String must be of the form [query;]*
     */
    public static final String PROP_CONNECTIONINITSQLS = "connectionInitSqls";
    public static final String PROP_ACCESSTOUNDERLYINGCONNECTIONALLOWED = "accessToUnderlyingConnectionAllowed";
    public static final String PROP_REMOVEABANDONEDONBORROW = "removeAbandonedOnBorrow";
    public static final String PROP_REMOVEABANDONEDONMAINTENANCE = "removeAbandonedOnMaintenance";
    public static final String PROP_REMOVEABANDONEDTIMEOUT = "removeAbandonedTimeout";
    public static final String PROP_LOGABANDONED = "logAbandoned";
    public static final String PROP_ABANDONEDUSAGETRACKING = "abandonedUsageTracking";
    public static final String PROP_POOLPREPAREDSTATEMENTS = "poolPreparedStatements";
    public static final String PROP_MAXOPENPREPAREDSTATEMENTS = "maxOpenPreparedStatements";
    public static final String PROP_CONNECTIONPROPERTIES = "connectionProperties";
    public static final String PROP_MAXCONNLIFETIMEMILLIS = "maxConnLifetimeMillis";
    public static final String PROP_LOGEXPIREDCONNECTIONS = "logExpiredConnections";
    public static final String PROP_ROLLBACK_ON_RETURN = "rollbackOnReturn";
    public static final String PROP_ENABLE_AUTOCOMMIT_ON_RETURN = "enableAutoCommitOnReturn";
    public static final String PROP_DEFAULT_QUERYTIMEOUT = "defaultQueryTimeout";
    public static final String PROP_FASTFAIL_VALIDATION = "fastFailValidation";

    /**
     * Value string must be of the form [STATE_CODE,]*
     */
    public static final String PROP_DISCONNECTION_SQL_CODES = "disconnectionSqlCodes";

    /*
     * Block with obsolete properties from DBCP 1.x. Warn users that these are ignored and they should use the 2.x
     * properties.
     */
    public static final String NUPROP_MAXACTIVE = "maxActive";
    public static final String NUPROP_REMOVEABANDONED = "removeAbandoned";
    public static final String NUPROP_MAXWAIT = "maxWait";

    /*
     * Block with properties expected in a DataSource This props will not be listed as ignored - we know that they may
     * appear in Resource, and not listing them as ignored.
     */
    public static final String SILENTPROP_FACTORY = "factory";
    public static final String SILENTPROP_SCOPE = "scope";
    public static final String SILENTPROP_SINGLETON = "singleton";
    public static final String SILENTPROP_AUTH = "auth";
}
