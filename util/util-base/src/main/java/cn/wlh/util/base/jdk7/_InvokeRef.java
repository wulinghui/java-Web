package cn.wlh.util.base.jdk7;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

import cn.wlh.util.base._Class;

public class _InvokeRef {
	static java.lang.invoke.MethodHandles.Lookup look = MethodHandles.lookup();
	
	
	public static java.lang.invoke.MethodHandles.Lookup getLook() {
		return look;
	}
	/** 有对象时建议用bind , 不用 find...
	 * bind 用通过对象获得方法..
	 * find 用Clalss获得方法..
	 * */
	public static <T> T invokeOfBind(Object receiver, String name, MethodType type , Object... args) throws Throwable {
		MethodHandle method  = look.bind(receiver, name, type);
		return (T) method.invoke(args);
	}
	public static <T> T invokeOfBind( Object receiver, String name, Class<T> rtype, Object... args ) throws Throwable {
		MethodType type = null ; 
		int i = args.length;
		if(  i == 0 ) {
			type = MethodType.methodType(rtype);
		}else {
			Class<?> ptypes [] = _Class.obj2Class(args);
			type = MethodType.methodType(rtype,ptypes);
		}
		return invokeOfBind(receiver, name, type, args);
	}
	public static void main(String[] args) throws Throwable {
		//第一个编译阶段检查
		MethodHandle method  =	//不可以序列化.. 
				MethodHandles.lookup()//不可以序列化..
//				.bind("", "toString"
//						,MethodType.methodType(String.class)//可以序列化..
//						)//通过对象获得方法..
				.findVirtual( String.class, "toString"
									,MethodType.methodType(String.class)//可以序列化..
									)// 通过类获得方法..
				;
		System.out.println( method.toString() );
//		String sss = (String) method.invoke(); //bind - 不用对象了
		String sss = (String) method.invoke("");// 和Method.invoke 一样..
//		method.
//		method.invoke("");//而且,还会检验权限...不一定更快..
		//第二个运行阶段检查
		//	java.lang.invoke.InvokeDynamic  权限不公开了..就只有上面这一种了
		Object a  = new _InvokeRef();
		MethodHandle method1  = look.bind( a , "test"
				,MethodType.methodType(void.class , a.getClass() )
				);
		method1.invoke(null);
	}
	
	public void test(Object obj) { System.out.println("xxxxxx"); }
//	public class MethodHandle1 extends MethodHandle{
//
//		MethodHandle1(MethodType type) {
//			super(type, null);
//		}
//		
//	}
//
//	public class Method1 extends java.lang.reflect.Method{
//		
//	}
}
