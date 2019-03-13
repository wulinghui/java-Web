package cn.wlh.framework.ioc.factory;

public class EnumOrNewFactor extends EnumFactory{
	final String newFlag;
	/**
	 * Ĭ���ø���ķ�����
	 */
	public EnumOrNewFactor() {
		super();
		newFlag = "";
	}
	/**
	 * @param singleFactory �����޸�ʹ�������ࡣ 
	 */
	public EnumOrNewFactor(SingleFactory singleFactory,String newFlag) {
		super('w',singleFactory);
		this.newFlag = newFlag;
	}
	
	/* (non-Javadoc)
	 * @see cn.wlh.framework.ioc1.factory.EnumFactory#getInstance(java.lang.Class, java.lang.Object[])
	 */
	@Override
	public <T>  T getInstance(Class<? extends T> classType,  Object[] pars) {
		try {
			if(pars[0].equals(this.newFlag)) {
				return (T) newObject(  classType, pars);
			}
		}catch (ArrayIndexOutOfBoundsException e){
		}
		return (T) super.getInstance( classType,pars);
	}
	
}
