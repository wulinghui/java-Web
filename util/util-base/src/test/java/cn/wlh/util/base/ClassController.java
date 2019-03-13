package cn.wlh.util.base;

import org.junit.Test;

public class ClassController {
	@Test
	public void testB() {
		new B();//AAAAAAAAAAAAA
	}
	@Test
	public void testD() {
		new D();//AAAAAAAAAAAAA
	}
	//��˵����init�ڸ���Ĺ��캯��ִ��֮ǰ������.
	//�����һ��Ӧ�ó���.�ҵ������������صĻ��Ϳ���ʹ�����ַ�ʽ��.������ֱ�������Ժ͹��췽�����渳ֵ.
	@Test
	public void testE() throws ClassNotFoundException{
		//one class no sysout
		Class z = E.class; // no sysou
		// Class.forName  sysout
//		Class.forName(z.getName()); // sysou
		// user static field sysout
//		System.out.println( E.INNER ); // sysou
		//user static methode sysout
		E.getBean();
	}
}
class A{
	A(){
		init();
	}

	private void init() {
		System.out.println("AAAAAAAAAAAAA");
	}
}
class B extends A{
	B(){
		super();
	}
	private void init() {
		System.out.println("BBBBBBBBBBBBb");
	}
}
class C{
	C(){
		init();
	}

	protected void init() {
		System.out.println("CCCCCCCCCCCCCC");
	}
}
class D extends C{
	D(){
		super();
	}
	protected void init() {
		System.out.println("DDDDDDDDDDDDDDD");
	}
}
class E{
	public static String INNER ;
	static {
		System.out.println("===EEEEEEEEEEEEEEEEE"
				+ "==========");
	}
	public static void getBean() {}
}