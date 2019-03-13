package cn.wlh.util.base.exception;

import java.util.List;
import java.util.Map;

import cn.wlh.util.base.JavaUtilFactory;
import cn.wlh.util.base.exception.ExceptionUtil1.ExceptionCache;

/**
 * @author 吴灵辉
 * 增加历史记录
 */
public class ExceptionUtil1 extends ExceptionUtil {
	
	final ThreadLocal<List<Object>> HIS_EXCEPTION_INFO = new ThreadLocal<>();
	@Override
	protected void setThrowLocal(Throwable e, Object... infos) {
		super.setThrowLocal(e, infos);
		List<Object> list = HIS_EXCEPTION_INFO.get();
		//第一次
		if( list ==null) {
			list = JavaUtilFactory.newList(JavaUtilFactory.INSERT_OF_METHOD);
			HIS_EXCEPTION_INFO.set(list);
		}
		//存入历史
		for (Object object : list) {
			list.add(object);
		}
	}
	public ExceptionCache newExceptionCache() {
		return new ExceptionCache();
	}
	//在每个类中使用。
	/** ExceptionUtil1 eUtil单例所有项目共同使用。
	 *  ExceptionCache eUtil.new ExceptionCache();每个类使用。最后是只在本类中使用，因为他是以message为主键的。
	 *  自己在系统(封装jar--需要人员维护的地方)中使用来中断程序的地方本来就少。主键再重复就更加少了。
	 */
	public class ExceptionCache {
		Map<String,RuntimeException> runtimeException = JavaUtilFactory.newMap(JavaUtilFactory.SELECT_OF_METHOD);
		Map<String,ExceptionUtil> throwException = JavaUtilFactory.newMap(JavaUtilFactory.SELECT_OF_METHOD);
		/**message不要重复
		 * @param message
		 * @param infos
		 */
		public void interrupt(String message,Object ... infos) {
			RuntimeException runtimeException2 = runtimeException.get(message);
			if(runtimeException2 ==null) {
				runtimeException2 = new RuntimeException(message);
				runtimeException.put(message, runtimeException2);
			}
			setThrowLocal(runtimeException2,infos);
			throw runtimeException2;
		}
		/**message不要重复
		 * @param message
		 * @param infos
		 */
		public void doThrow(String message,Object ... infos) throws ExceptionUtil {
			ExceptionUtil runtimeException2 = throwException.get(message);
			if(runtimeException2 ==null) {
				runtimeException2 = new ExceptionUtil(message);
				throwException.put(message, runtimeException2);
			}
			setThrowLocal(runtimeException2,infos);
			throw runtimeException2;
		}
	}
	/*测试速度
	 * one:18048
	 * two:22242
	 */
	
	/*public static  void main(String[] args) {
		ExceptionUtil1 util = new ExceptionUtil1();
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
	} */
	 
}
class AAAA{  
	public static ExceptionUtil1 exceptionUtil1 = new ExceptionUtil1();
	public static ExceptionCache exceptionCache = exceptionUtil1.new ExceptionCache();
	public void test() throws ExceptionUtil {
		exceptionUtil1.doThrow();
		exceptionCache.doThrow("");
	}
}