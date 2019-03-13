package cn.wlh.framework.aop.factory;

import cn.wlh.framework.ioc.factory.AppFactoryMan;

public class AopFactoryTest {
	static {
		AppFactoryMan.addFactory(new AopFactory("cn.wlh.framework.aop"));
	}
	public static void main(String[] args) {
		TestA a = AppFactoryMan.getInstance(TestA.class);
		a.aaa();  
	}
}  
