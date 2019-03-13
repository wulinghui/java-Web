package cn.wlh.framework.dao;

/**
 * @author 吴灵辉
 * 不考虑事务传播就够了。
 */
public enum DaoManagerFacatory{
	FACTORY;
	
	static DaoManageInterface daoManageInterface = new DaoManageImp();
	
	public  DaoManageInterface getInstance(){
		return daoManageInterface;
	}

	/**
	 * @param daoManageInterface the daoManageInterface to set
	 */
	public static void setDaoManageInterface(DaoManageInterface daoManageInterface) {
		synchronized(DaoManagerFacatory.daoManageInterface) {
			DaoManagerFacatory.daoManageInterface = daoManageInterface;
		}
	}
}
