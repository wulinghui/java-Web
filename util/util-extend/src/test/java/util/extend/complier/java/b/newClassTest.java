package util.extend.complier.java.b;

import java.lang.reflect.Method;

public class newClassTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		B b = new B();
		b.init();
		Method[] declaredMethods = b.getClass().getSuperclass().getDeclaredMethods();
		for (Method method : declaredMethods) {
			System.out.println( method );
		}
		System.out.println( "-------------------------------" );
		Method[] declaredMethodzz = b.getClass().getDeclaredMethods();
		for (Method method : declaredMethodzz) {
			System.out.println( method );
		}
	}

}
class A{
	void init(){
		System.out.println(111);
		this.aaa();
	}
//	private void aaa() {
//		System.out.println(222);
//	}    222
	
//	 void aaa() {
//	System.out.println(222);
//}  3333
	 
	private	 void aaa() {
	System.out.println(222);
}
}
class B extends A{
//	private void aaa() {
//	System.out.println(333);
//}  

//	 void aaa() {
//	System.out.println(333);
//}  333  
	private void aaa() {
		System.out.println(333);
	} 
}