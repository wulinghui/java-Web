package cn.wlh.util.base.exception;
@Deprecated //@see cn.wlh.util.base.exception.RuntimeExceptionCache 示例..
public abstract class StringExceptionCacha<T extends Throwable> extends AbstractExceptionOfFlyWeight<String, T> {
	
	/**获得实例
	 * @param message
	 * @param isCache  true--new   false--缓存中
	 * @return
	 */
	protected T getInstance(String message,boolean isNoCache) {
		if( isNoCache ) return newInstance(message);
		T t = get(message);
		if( t == null ) {
			t = newInstance(message);
			put(message, t);
		}
		return t;
	}
	
	/**执行抛异常..
	 * @param message
	 * @param isNoCache true--new   false--缓存中
	 * @throws T
	 */
	protected void doThrow(String message,boolean isNoCache) throws T {
		T instance = getInstance(message,isNoCache);
		super.excuteThrows(instance);
	}
	/**执行抛异常且缓存
	 * @param message
	 * @throws T
	 */
	public void doThrow(String message) throws T {
		doThrow(message, false);
	}
	/**执行抛异常不缓存
	 * @param message
	 * @throws T
	 */
	public void doThrowNoCache(String message) throws T {
		doThrow(message, true);
	}
	/** new 实例
	 * @param message
	 * @return
	 */
	protected abstract T newInstance(String message);
}
