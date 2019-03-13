package cn.wlh.util.base.exception;

public class RuntimeExceptionCache extends StringExceptionCacha<RuntimeException>{
	public static final RuntimeExceptionCache exception = new RuntimeExceptionCache();
	@Override
	protected RuntimeException newInstance(String message) {
		return new RuntimeException(message);
	}
	public static void main(String[] args) {
		A a = new A();
		B b = new B();
		try {
			a.aaaa();
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			b.bbbbb();
//			 Thread.currentThread().currentThread().getStackTrace()[0].
		} catch (Exception e) {
			e.printStackTrace();
		}
		/*
		 * 从结果可知..他会把值栈给缓存起来..这也就说明是在 java.lang.Throwable.fillInStackTrace()给的值栈..
		 * 也就是说不能在不同方法里面使用，否则将增加运维的难度。。。
		 * 如果用值栈为主键的话。。。这样还不如不缓存呢。               Thread.currentThread().getStackTrace(); //  (new Exception()).getStackTrace();
		 * 把主键交给用户。这不是一个好的方式，一旦用户，错误使用将导致不可维护。		
		 * 
		 * 我们可以在一个地方统一初始化。。。在另一个地方使用。   不行值栈信息是定义的地方。
		 * 
		 * 只能把主键交给用户了。
		 */
//		xxx.doThrow("..");
	}
	static {
		try {
			RuntimeExceptionCache.exception.put("aaaa",  new RuntimeException("aaaa"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	static RuntimeException runtimeException = new RuntimeException("zzzz");
	
}

/**
 * @author 吴灵辉
 * 测试值栈的缓存 
 */
class A{
	void aaaa() {
		RuntimeExceptionCache.exception.doThrow("aaaa");
	}
}
class B{
	void bbbbb() {
		RuntimeExceptionCache.exception.doThrow("aaaa");
	}
}
