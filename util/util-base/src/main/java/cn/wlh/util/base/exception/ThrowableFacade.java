package cn.wlh.util.base.exception;

import cn.wlh.util.base.exception.ExceptionUtil1.ExceptionCache;

public interface ThrowableFacade {
	/**1.使用异常中止程序执行
	 * 3.对系统异常进行处理之后把该异常抛出。
	 * 4.对系统异常进行处理之后把该异常转化成自定义异常并抛出。
	 * */
	ExceptionUtil SYSTEM = new ExceptionUtil();
	/**
	 * 获得值栈信息的。
	 */
	ExceptionUtil1 STACK_TRACES_INFO = new ExceptionUtil1();
	/**这是系统级的值栈缓存。
	 * */
	ExceptionCache STACK_TRACES_INFO_OF_SYSTEM = STACK_TRACES_INFO.new ExceptionCache();	
	/**4.对系统异常进行处理之后把该异常转化成运行时异常并抛出*/
	ExceptionUtil2 THROW_RUN = new ExceptionUtil2();  
}
