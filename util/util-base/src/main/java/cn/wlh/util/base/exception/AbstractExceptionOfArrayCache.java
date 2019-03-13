package cn.wlh.util.base.exception;

/**
 * @author 吴灵辉  --一个类里面的一行代码对应一个实体类
 * 享元模式,实现一些不变类的共享..
 * 以数组的方式实现..用户如果想注册,就必须修改构造方法里面的数值来扩容数组,也就是说用户必须清楚知道,我现在添加的是最后一个。
 * 但是这里也有一个问题，一开始用户就把数组写的很大，造成后续人员用序列时的错误。
 */@Deprecated  //@see cn.wlh.util.base.exception.RuntimeExceptionCache 示例..
public class AbstractExceptionOfArrayCache<T extends Throwable> {
	/***/
	private static final long serialVersionUID = 3870803932126272408L;
	final Object cache [];
	public AbstractExceptionOfArrayCache(int arrayLenth) {
		cache = new Object[arrayLenth];
	}

	protected void put(int index  , T e) {
		cache[index] = e;
	}
	
	protected T get(int index) {
		return  (T) cache[index];
	}
	
	protected final  void excuteThrows(T e) throws T {
		throw e;
	}
}










 class AbstractExceptionOfArrayCacheSub extends AbstractExceptionOfArrayCache<RuntimeException>{
	public AbstractExceptionOfArrayCacheSub(int arrayLenth) {
		super(arrayLenth);
	}
	
	protected RuntimeException getInstance(int index , String message , boolean isNoCache){
		if( isNoCache ) return newInstance(message);
		RuntimeException t = get(index);
		if( t == null ) {
			t = newInstance(message);
			put(index, t);
		}
		return t;
	}
	protected  RuntimeException newInstance(String message) {
		return new RuntimeException(message);
	};

	/**执行抛异常..
	 * @param message
	 * @param isNoCache true--new   false--缓存中
	 * @throws T
	 */
	protected void doThrow(int index , String message,boolean isNoCache) throws RuntimeException {
		RuntimeException instance = getInstance(index,message,isNoCache);
		super.excuteThrows(instance);
	}
	/**执行抛异常且缓存
	 * @param message
	 * @throws T
	 */
	public void doThrow(int index ,String message) throws RuntimeException {
		doThrow(index ,message, false);
	}
	/**执行抛异常不缓存
	 * @param message
	 * @throws T
	 */
	public void doThrowNoCache(int index ,String message) throws RuntimeException {
		doThrow(index ,message, true);
	}
}
class AA{
	static AbstractExceptionOfArrayCacheSub EX_RUNTIME  = new AbstractExceptionOfArrayCacheSub(2) ;
	/*
	 * 这种做法，我每次都需要修改到
	 */
	void aaa(){
		EX_RUNTIME.doThrow(0, "zzz");
	}
	
	void bbb() {
		EX_RUNTIME.doThrow(0, "zzz");
	}
}