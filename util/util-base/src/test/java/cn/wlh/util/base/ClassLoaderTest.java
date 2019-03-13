package cn.wlh.util.base;

import javax.management.RuntimeErrorException;

public class ClassLoaderTest {
	public static void main(String[] args) throws ClassNotFoundException {
		Class.forName(BBB.class.getName());
	}
	
}
class AAA{
	static Object obj ;
	static {
		set(obj , "AAAAAAA");
		System.out.println(obj);
	}
	public static <T extends Object> void set(T src , T inner) {
		if( src == null ) {
			src = inner;
		}else {
			throw new RuntimeException("");
		}
	}
}
class BBB extends AAA{
	static {
		set(obj , "BBB");
		System.out.println(obj);
		
	}
}
