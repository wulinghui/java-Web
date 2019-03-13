package cn.wlh.util.base;

import java.lang.reflect.Type;
import java.util.Set;

import org.apache.commons.lang3.MyClassUtilPackger;
import org.junit.Ignore;
import org.junit.Test;

public class ClassUtilTest {
	ClassUtil util = new ClassUtil();
	@Test
	@Ignore
	public void getClassSet() {
		//jar里面的类和项目中自己使用的类都有。
//		Set<Class<?>> classSet = ClassUtil.getClassSet("org.apache.commons.lang3");
		Set<Class<?>> classSet = ClassUtil.getClassSet("java.util");
		for (Class<?> class1 : classSet) {
			System.out.println(class1);
			if( class1.toString().contains(MyClassUtilPackger.class.getSimpleName()) ) 
			System.err.println(class1);
		}
	}
	@Test
	@Ignore
	public void getClassArguments() {
		System.out.println("=========================");
		new DD();
		System.out.println("=========================");
		new CC();
		System.out.println("=========================");
		new BB<Object>();
		System.out.println("=========================");
		new EE();
		System.out.println("=========================");
	}
	@Test
	@Ignore
	public void isAssignableFrom() {
		System.out.println("==========assignableFrom===============");
		boolean assignableFrom = DD.class.isAssignableFrom(DD.class);
		System.out.println("==========assignableFrom==============="+assignableFrom);
		boolean assignableFrom2 = AA.class.isAssignableFrom(DD.class);
		System.out.println("==========assignableFrom==============="+assignableFrom2);
		boolean assignableFrom3 = Object.class.isAssignableFrom(AA.class);
		System.out.println("==========assignableFrom==============="+assignableFrom3);
	}
}	
class AA<T> {
	public AA() {
		Type[] classArguments = _Class.getClassArgumentsOfSuper(this.getClass());
		for (Type type : classArguments) {
			System.out.println( type );
		}
	}
}
class BB<T> extends AA<T>{
	
}
class CC extends BB<String>{}
class DD extends CC{}
interface IAA<T>{}
interface IBB<T> extends IAA<T>{}
class EE<T> implements IBB<T>{
	public EE() {
		Type[] classArguments = _Class.getClassArgumentsOfSuper(this.getClass());
		for (Type type : classArguments) {
			System.out.println( type );
		}
	}
}
