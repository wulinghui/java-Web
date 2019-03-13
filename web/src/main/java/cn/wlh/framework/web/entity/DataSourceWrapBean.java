package cn.wlh.framework.web.entity;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.dbcp2.BasicDataSourceFactory;

import cn.wlh.framework.web.dao.DaoHelper;
import cn.wlh.framework.web.dao.DaoInterfaceWebImp;
import cn.wlh.util.base.exception.ThrowableFacade;

public class DataSourceWrapBean extends cn.wlh.util.base.adapter.datasource.dbcp.DataSourceBean {
	private DaoInterfaceWebImp daoInterface;
	/***/
	private static final long serialVersionUID = 1L;
	private final String driverClassName,  url,  username,  password,dataSourceBeanId;
	public DataSourceWrapBean(String dataSourceBeanId,String driverClassName, String url, String username, String password){
		super(driverClassName, url, username, password);
		this.driverClassName = driverClassName;
		this.url = url;
		this.username = username;
		this.password = password;
		this.dataSourceBeanId = dataSourceBeanId;
	}
	public void init() {
		BasicDataSource dataSource = null;
		try {
			dataSource = BasicDataSourceFactory.createDataSource(this);
		} catch (Exception e) {
			ThrowableFacade.THROW_RUN.doThrowOfRun(e , this.toString());
		}
		daoInterface = new DaoInterfaceWebImp(dataSource);
		//这里还没有加载这个就直接用了。报错null
		DaoHelper.DaoFacade.putDaoInterface( dataSourceBeanId  ,  daoInterface );
	}
	/**
	 * @return the daoInterface
	 */
	public DaoInterfaceWebImp getDaoInterface() {
		return daoInterface;
	}
	/**
	 * @return the driverClassName
	 */
	public String getDriverClassName() {
		return driverClassName;
	}
	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}
	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}
	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * @return the dataSourceBeanId
	 */
	public String getDataSourceBeanId() {
		return dataSourceBeanId;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((daoInterface == null) ? 0 : daoInterface.hashCode());
		result = prime * result + ((dataSourceBeanId == null) ? 0 : dataSourceBeanId.hashCode());
		result = prime * result + ((driverClassName == null) ? 0 : driverClassName.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((url == null) ? 0 : url.hashCode());
		result = prime * result + ((username == null) ? 0 : username.hashCode());
		return result;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		DataSourceWrapBean other = (DataSourceWrapBean) obj;
		if (daoInterface == null) {
			if (other.daoInterface != null)
				return false;
		} else if (!daoInterface.equals(other.daoInterface))
			return false;
		if (dataSourceBeanId == null) {
			if (other.dataSourceBeanId != null)
				return false;
		} else if (!dataSourceBeanId.equals(other.dataSourceBeanId))
			return false;
		if (driverClassName == null) {
			if (other.driverClassName != null)
				return false;
		} else if (!driverClassName.equals(other.driverClassName))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (url == null) {
			if (other.url != null)
				return false;
		} else if (!url.equals(other.url))
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "DataSourceBean [driverClassName=" + driverClassName + ", url=" + url + ", username=" + username
				+ ", password=" + password + ", dataSourceBeanId=" + dataSourceBeanId + "]";
	}
}
