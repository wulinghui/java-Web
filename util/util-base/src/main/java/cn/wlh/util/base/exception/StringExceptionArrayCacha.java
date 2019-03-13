package cn.wlh.util.base.exception;
/**
 * @author 吴灵辉
 * TODO 这里如果一些不需要缓存的处理方式是直接new
 * 这里也就控制不了...
 * 1.通过缓存池来解决...但是当高并发过来的时候.内存和CPU调度同样也有问题
 * 2.这里只缓存-值栈信息。具体的信息放到线程变量中。在处理异常的时候在取出里面的值，而不是从异常里面获得。
 * --也就是说，异常的作用也就只用于控制程序流程，而提示信息放到线程变量中，需要的时候直接从线程变量中获得。
 * @param <T>
 */
@Deprecated //@see cn.wlh.util.base.exception.RuntimeExceptionCache 示例..
public abstract class StringExceptionArrayCacha<T extends Throwable> extends AbstractExceptionOfArrayCache<T> {
	
	public StringExceptionArrayCacha(int arrayLenth) {
		super(arrayLenth);
	}

	/**获得实例
	 * @param message
	 * @param index 
	 * @param isCache  true--new   false--缓存中
	 * @return
	 */
	protected T getInstance(int index ,String message,boolean isNoCache ) {
		if( isNoCache ) return newInstance(message);
		T t = get(index);
		if( t == null ) {
			t = newInstance(message);
			put(index, t);
		}
		return t;
	}
	
	/**执行抛异常..
	 * @param message
	 * @param isNoCache true--new   false--缓存中
	 * @throws T
	 */
	protected void doThrow(int index ,String message,boolean isNoCache) throws T {
		T instance = getInstance(index ,message,isNoCache);
		super.excuteThrows(instance);
	}
	/**执行抛异常且缓存
	 * @param message
	 * @throws T
	 */
	public void doThrow(int index ,String message) throws T {
		doThrow(index ,message, false);
	}
	/**执行抛异常不缓存
	 * @param message
	 * @throws T
	 */
	public void doThrowNoCache(int index ,String message) throws T {
		doThrow(index ,message, true);
	}
	/** new 实例
	 * @param message
	 * @return
	 */
	protected abstract T newInstance(String message);
}
