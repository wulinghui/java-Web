package cn.wlh.util.base;

public class StaticClassSuper {
	static String inner = "111";
	public static void main(String[] args) {
		StaticClassSuper staticClassSuper = new StaticClassSuper();
		System.out.println("staticClassSuper" + staticClassSuper.inner);
		StaticClassSub staticClassSub = new StaticClassSub();
		System.out.println("放入之后的:staticClassSub" + staticClassSub.inner);
		System.out.println("放入之后的:staticClassSuper" + staticClassSuper.inner);
		System.out.println("放入之后的:staticClassSuper11" + new StaticClassSuper().inner);
		System.out.println("这说明父子类共享一个静态变量..." );
	}
}
class StaticClassSub extends StaticClassSuper{
	StaticClassSub(){
		super.inner = "zzzzzz";
	}
}
