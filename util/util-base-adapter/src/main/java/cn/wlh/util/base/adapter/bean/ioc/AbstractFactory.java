package cn.wlh.util.base.adapter.bean.ioc;

public class AbstractFactory implements IFactory{
	private final String ids [];
	byte aaa [] = new byte[] {}; 
	/**
	 * @param cla 默认注册了自己.
	 * IOC.register(cla, this); 
	 */
	public AbstractFactory(Class cla){
		IOC.register(cla, this);
		
		ids = new String[] {
				"00","11"
		};// 通过数据库来获得,一(Class)对多(String) 
	}
	/* (non-Javadoc)
	 * @see cn.wlh.util.base.adapter.bean.ioc.IFactory#getBean(java.lang.String)
	 */
	@Override
	public  Object getBean(String id) {
//		byte parseByte = Byte.parseByte(id);
//		byte zz = aaa[parseByte];
//		if(_11.equals(id)) {
//			//new 1;
//		}else if("22".equals(id)) {
//			//new 2;
//		}
		String string = ids[0];
		//如何变化?
		switch( id ) {
		//java9 不支持表达式..
//		case string:
//			//new 1;
//		case ids[1]:
			//new 2;
		}
		// 这里就有效率问题..
		if( this.ids[0].equals(id) ) {
			
		}else if(this.ids[1].equals(id)){
			
		}
		//
		 
		return null;
	}
	/** 把this.ids[one] 和 this.ids[two] 的内容给交换.从而达到更换new对象的方法  
	 * @param one
	 * @param two
	 */
	public void setOrderBy(int one , int two) {
		String str = this.ids[one];
		
		//把one的内容给换了 , 同时更新数据库的内容.
		updateData( this.ids[one] , this.ids[two]);
		this.ids[one] = this.ids[two];
		
		
		//把two的内容给换了 , 同时更新数据库的内容.
		updateData( this.ids[two] , str );
		this.ids[two] = str;
		
	}
	/** 更新数据库.用以维护.
	 * @param old 旧的值
	 * @param new0 新的值
	 */
	public void updateData(String old , String new0) {
		String sql = 
				"update data_factory f , set f.value = '" + new0 
				+ "' where f.key='" + this.getClass().getName()
				+ "' and f.value='" + old + "'";
		
	}
}
