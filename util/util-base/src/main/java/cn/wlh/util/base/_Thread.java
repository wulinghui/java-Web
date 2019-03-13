package cn.wlh.util.base;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public  abstract class _Thread {
	public static final ThreadGroup group = new ThreadGroup("g");
	public static final ExecutorService pool = 
			Executors.newFixedThreadPool( 10000 );
	public static final ScheduledExecutorService poolOfTimer =
		Executors.newScheduledThreadPool( 100 );
	public static final TimeUnit timeUnit = TimeUnit.SECONDS;
//	public static final ForkJoinPool fjpool = ForkJoinPool.commonPool();
	public static int MD5 = -1;
//	public static Thread[] threadArray = new  Thread[100];
//	static int index = 0;  --对象的公共资源交给IOC.职责不能乱
	/**这个是执行被管理过的,为了服务降级
	 * @param thread
	 * @return
	 */
	public static Thread executor(Runnable thread){
		Thread th = new Thread(group,thread);
		pool.execute(th);
		return th;
	}
	/** 这个是执行没有被管理的
	 * @param thread
	 * @return
	 * @throws TsException 
	 */
	public static void executor(Thread thread,int md5)  {
		if(md5 != MD5)
		pool.execute(thread);
	}
	/**执行的是异步的方法,建议用户使用.没有登记--登记管理应该交给ioc去维护
	 * @param task
	 * @param tasks
	 * @return
	 */
//	public static <T> T executor(ForkJoinTask<?> task,ForkJoinTask<?>... tasks){
//		int len = tasks.length;
//		if(len == 0) {
//			return (T) fjpool.invoke(task);
//		}else {
//			List co =  Arrays.asList(tasks);
//			co.add(0, task);
//			return (T) fjpool.invokeAll(co);
//		}
//	}
	
	/**定时任务,执行一次
	 * @param command
	 * @param delay 单位是秒
	 * @return
	 */
	public static ScheduledFuture<?> timerOne(Runnable command,long delay){
		return poolOfTimer.schedule( command,delay, timeUnit);
	}
	/**@see timerOne */
	public static <V> ScheduledFuture<V> timerOne(Callable<V> command,long delay){
		return poolOfTimer.schedule( command,delay, timeUnit);
	}
	/**周期的执行,并且每次执行时间为上一次任务结束起向后推一个时间间隔period
	 * initialDelay初始延期一秒.
	 * @param command 
	 * @param period --周期时间,单位是秒
	 * @return
	 */
	public static ScheduledFuture<?> timerCycle(Runnable command,long period){
		return poolOfTimer.scheduleWithFixedDelay( command, 1 ,period, timeUnit);
	}
}
