package cn.wlh.framework.ioc.factory;

import org.junit.Test;

public class VersionFactoryTest0 {
	public void aaa() {
		System.out.println("11111111");
	}
	@Test
	public void test() {
		AppFactoryMan.addFactory(new SingleFactory() {});
		VersionFactoryTest0 instance = AppFactoryMan.getInstance(VersionFactoryTest0.class);
		System.out.println(instance.getClass()  ); 
	}
	
	public static void main(String[] args) {    
		AppFactoryMan.addFactory(new Object() {
			/*VersionFactoryTest0 getInstance(){
				return new VersionFactoryTest0(); //可以的
			}*/
			Object getInstance(){//可以的
				return new VersionFactoryTest0();
			}  
		});
		
		AppFactoryMan.addFactory(new Object() {
			VersionFactoryTest1 getInstance(){
				return new VersionFactoryTest1(); //可以的
			}
			/*Object getInstance(){//可以的
				return new VersionFactoryTest0();
			}*/
		});
		
		VersionFactoryTest0 instance = AppFactoryMan.getInstance(VersionFactoryTest0.class);
		System.out.println(instance.getClass()  );
		instance.aaa();
		
		
		VersionFactoryTest0 instance1 = AppFactoryMan.getInstance(VersionFactoryTest1.class);
		System.out.println(instance1.getClass()  );
		instance1.aaa();
	}   
}
class VersionFactoryTest1 extends  VersionFactoryTest0  {
	
}