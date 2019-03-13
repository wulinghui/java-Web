package cn.wlh.util.base.exception;

/**
 * @author 吴灵辉
 * 这里如果不考虑事务的传播行为-不考虑不同线程的化..这里的内容已经够用了。
 * 只不过用户需要，按规范写缓存。
 * @param <T>
 */
public abstract class AbstractExceptionOfThreadLocal <T extends Throwable> extends AbstractExceptionOfArrayCache<T>{
	 static final ThreadLocal<Throwable> CASE_THREADLOCAL = new ThreadLocal<Throwable>();
	 static final ThreadLocal<Object[]> INFO_THREADLOCAL = new ThreadLocal<Object[]>();
	
	/**
	 * @return
	 * @see java.lang.ThreadLocal#get()
	 */
	public final static Throwable getCase() {
		return CASE_THREADLOCAL.get();
	}
	/**
	 * @return  获得存放在异常里面的信息
	 * @see java.lang.ThreadLocal#get()
	 */
	public final static Object[] getInfo() {
		return INFO_THREADLOCAL.get();
	}

	public AbstractExceptionOfThreadLocal(int arrayLenth) {
		super(arrayLenth);
	}
	
	/**一个类里面的一行代码对应一个实体类
	 * @param index
	 * @param message
	 * @return
	 */
	protected T getInstance(int index , String message ){
		T t = get(index);//从缓存获得
		if( t == null ) {
			t = newInstance(message);
			put(index, t);
		}
		return t;
	}
	/**new 
	 * @param message
	 * @return
	 */
	protected abstract  T newInstance(String message);
	
	
	/**生成自定义异常并且执行抛异常..
	 * @param message
	 * @throws T
	 */ // 必须缓存..只是缓存值栈信息和打印控制台的信息(message).
	public void doThrowOfNew(int index , String message,Object ... infos ) throws  T {
		T instance = getInstance(index,message);
		doThrow(instance, infos);
	}
	/**生成自定义异常并且执行抛异常..
	 * @param index 
	 * @param message 
	 * @param cause -- 系统在不同的try位置抛出来
	 * @param infos
	 * @throws T
	 */
	public void doThrowOfNew(int index , String message, T cause,Object ... infos ) throws  T {
//		new Throwable(message , cause);
		T instance = getInstance(index,message);
		//这里线程放cause，因为这里系统可以在不同的try位置抛出来,不能直接放到instance里面.
		CASE_THREADLOCAL.set(cause);
		INFO_THREADLOCAL.set(infos);
		super.excuteThrows(instance);
	}
	/**执行抛异常..
	 * 通常用于系统产生的异常。我们没法缓存。只能借用。
	 * @param message
	 * @throws T
	 */ // bu缓存..
	public void doThrow(T e,Object ... infos ) throws  T {
		//放入
		CASE_THREADLOCAL.set(e);
		INFO_THREADLOCAL.set(infos);
		super.excuteThrows(e);
	}
	
}
