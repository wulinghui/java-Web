package cn.wlh.framework.dao;

/**
 * @author �����
 * ���������񴫲��͹��ˡ�
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
