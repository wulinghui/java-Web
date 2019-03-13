package cn.wlh.util.base.exception;

import java.io.PrintStream;
import java.io.PrintWriter;

/**
 * @author 吴灵辉
 *@see /util-base/src/main/java/cn/wlh/util/base/exception/info.txt
 *
 *这种方法的好处：  1.最快的处理速度。
 *             2.无需定义异常
 *这种方法的缺点: 	1.自己定义的值栈信息没有。--用户准确定位报错的位置。
 *				2.不能直接通过异常获得里面的信息。
 *
 *
 *TODO 	自己定义的值栈信息没有。--用户准确定位报错的位置，所以该类只适用于最上层的使用。
 *		如需自定义异常，则不要使用该类，否则将给下层人员带来极大困扰。
 *		别人无法查看源码就更是噩梦（没有值栈信息，如果再没错误提示-GG）。
 */
public class ExceptionUtil extends Exception{
	// 结束流程，值栈信息不准。
	 final RuntimeException INTERRUPT = new RuntimeException("end liucheng,zhizhan xinxi bu zhun");
//	 final ExceptionUtil INTERRUPT0 = new ExceptionUtil("end liucheng,zhizhan xinxi bu zhun");
	 
	 final ThreadLocal<Throwable> THROWABLE = new ThreadLocal<>();
	 final ThreadLocal<Object []> EXCEPTION_INFO = new ThreadLocal<Object []>();
	 final String string;
	 public ExceptionUtil(String string) {
		this.string = string;
	 }
	 public ExceptionUtil() {
		 this(":next- \t -next:");
	 }
		/**1.使用异常中止程序执行。--这里用户不明白。值栈信息
		 * 仅仅提供中止和提示信息，无值栈信息。
		 * @param infos
		 */
		public  void interrupt(Object... infos ) {
			setThrowLocal(INTERRUPT,infos);
			throw INTERRUPT;
		}
		/**1.使用异常中止程序执行。--
		 * 仅仅提供中止和提示信息，无值栈信息。
		 * @param infos
		 * @throws Exception 
		 */
		public  void doThrow(Object... infos ) throws ExceptionUtil {
			setThrowLocal(this,infos);
			throw this;
		}
	
	
	// 2.对系统异常进行处理并忽视该异常继续执行后续流程。
	/**3.对系统异常进行处理之后把该异常抛出。
	 * @throws T 
	 * 
	 */
	public  <T extends Throwable> void HandleAfterThrow(T e) throws T {
		setThrowLocal(e,new Object[]{});
		throw e;
	}
	/**4.对系统异常进行处理之后把该异常转化成自定义异常并抛出。
	 * @throws T 
	 * 
	 */
	public  <T extends Throwable> void HandleAfterThrow(T e,Object... infos ) throws T {
		setThrowLocal(e, infos);
		throw e;
	}
	
	//5.对系统异常直接抛出，由上层处理。
	
	//setThrowLocal
	protected   void setThrowLocal(Throwable e,Object... infos) {
		THROWABLE.set(e);
		EXCEPTION_INFO.set(infos);
	}


	
	/**
	 * @return the interrupt
	 */
	public  Throwable getThrowable() {
		return THROWABLE.get();
	}

	@Override
	public String getMessage() {
		return getThrowable().getMessage();
	}
	

	/* (non-Javadoc)
	 * @see java.lang.Throwable#getLocalizedMessage()
	 */
	@Override
	public String getLocalizedMessage() {
		// TODO Auto-generated method stub
		return getThrowable().getLocalizedMessage();
	}


	/* (non-Javadoc)
	 * @see java.lang.Throwable#toString()
	 */
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return getThrowable().toString();
	}


	/* (non-Javadoc)
	 * @see java.lang.Throwable#printStackTrace()
	 */
	@Override
	public void printStackTrace() {
		// TODO Auto-generated method stub
		getThrowable().printStackTrace();
	}


	/* (non-Javadoc)
	 * @see java.lang.Throwable#printStackTrace(java.io.PrintStream)
	 */
	@Override
	public void printStackTrace(PrintStream s) {
		// TODO Auto-generated method stub
		getThrowable().printStackTrace(s);
	}


	/* (non-Javadoc)
	 * @see java.lang.Throwable#printStackTrace(java.io.PrintWriter)
	 */
	@Override
	public void printStackTrace(PrintWriter s) {
		// TODO Auto-generated method stub
		getThrowable().printStackTrace(s);
	}


	/* (non-Javadoc)
	 * @see java.lang.Throwable#getStackTrace()
	 */
	@Override
	public StackTraceElement[] getStackTrace() {
		// TODO Auto-generated method stub
		return getThrowable().getStackTrace();
	}


	/* (non-Javadoc)
	 * @see java.lang.Throwable#setStackTrace(java.lang.StackTraceElement[])
	 */
	@Override
	public void setStackTrace(StackTraceElement[] stackTrace) {
		getThrowable().setStackTrace(stackTrace);
	}


	/**
	 * @return the throwable
	 */
	public  Object [] getExceptionInfo() {
		return EXCEPTION_INFO.get();
	}
	
	/*测试速度
	 *one:16441
	 *two:24785
	 */
	/*public static void main(String[] args) {
		ExceptionUtil util = new ExceptionUtil();
		int length = 10000000;
		long currentTimeMillis = System.currentTimeMillis();
		for (int i = 0; i < length; i++) {
			try {
				throw new Throwable("zzz"); // 模拟抛出
			} catch (Throwable e) {
				try {
					util.HandleAfterThrow(e); // 模拟处理
				} catch (Throwable e1) {
					//模拟记录
					util.getExceptionInfo();
					util.getThrowable();
				}
			}
		}
		long currentTimeMillis1 = System.currentTimeMillis();
		System.out.println("one:"+( currentTimeMillis1 - currentTimeMillis ));
		long currentTimeMillis2 = System.currentTimeMillis();
		for (int i = 0; i < length; i++) {
			try {
				throw new Throwable("zzz"); // 模拟抛出
			} catch (Throwable e) {
				try {
					throw new Throwable(e.getMessage()) {
					}; // 自定义转换异常.
				} catch (Throwable e1) {
					//模拟记录
					e.getMessage();
					e.getCause();
					e.getLocalizedMessage();
				}
			}
		}
		long currentTimeMillis3 = System.currentTimeMillis();
		System.out.println("two:"+( currentTimeMillis3 - currentTimeMillis2 ));
	}*/
}
