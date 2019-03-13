package cn.wlh.util.base;


public  abstract class _Exception{
	/**输出堆栈且退出线程
	 * @return 有些方法需要返回值,我们在这里给他放回---占位符。美化程序*/
	public static <T> T printStackAndExit(Throwable e){
		e.printStackTrace();
		Thread.currentThread().interrupt();
		return null;
	}
	/** 避免使用throw关键字.同时还可以吧e也交给Ioc管理
	 * @param <T>
	 * @param e
	 * @throws T
	 */
	public static <T extends Throwable> void throwable(T e) throws T{
		 throw e;
	}
	public static <T extends RuntimeException> void runtime(T e) {
		 throw e;
	}
	public static <T extends Throwable> void throwable( Class<T> e ) throws T{ 
		try {
			throwable( e.newInstance() );
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
	public static <T extends RuntimeException> void runtime(Class<T> e) {
		try {
			runtime( e.newInstance() );
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
	/** 转化为运行时异常.
	 *  可以用于public static final R t = new .... throws 的情况
	 *  在static final的时候有异常.但static{}又不能处理的时候
	 * @param h
	 */
	public static <R,Run extends RuntimeException> R toRuntime(ToException<R,Run> h){
		try{
			return h.handle();
		}catch(Throwable e){
			e.printStackTrace();   
			throw h.toException();
		}
	}
	public static interface ToException<R,Run extends RuntimeException>{
		R handle() throws Throwable;
		Run toException();
	}
	public static abstract class ToNullPointerException<R> implements ToException<R,NullPointerException>{
		public NullPointerException toException() {return new NullPointerException();}
	}
	/**
	 * 处理所有异常,确保程序正常运行
	 */
	public static <R> R handleAll(HandleException<R> h) {
		try{
			return h.normal();
		}catch(Throwable e){
			return h.exception(e);
		}
	}
	public static interface HandleException<R>{
		/** 正常处理 */
		R normal() throws Throwable;
		/** 异常处理 */
		R exception(Throwable e);
	}
	public static abstract class Print<R> implements HandleException<R>{
		public R exception(Throwable e) {
			e.printStackTrace();//简单的打印吧.
			return null;
		}
	}
	/**
	 * 不准用户使用.直接异常
	 */
	public static final String EXCEPTION_MASEEGE = "不准使用.直接异常";

	/**
	 * 不允许用户往下操作了
	 */
	public static void notAllowUser() {
		throw new RuntimeException(EXCEPTION_MASEEGE);
	}
}
