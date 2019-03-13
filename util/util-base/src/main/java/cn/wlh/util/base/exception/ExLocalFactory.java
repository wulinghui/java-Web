package cn.wlh.util.base.exception;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * @author 吴灵辉
 * 使用它，统一获得实例接口。注意这里的实例不能缓存.
 * --一个类对应一个实体类
 * --他这实例里面的缓存-(即下标)一个类里面的一行代码对应一个实体类
 */
public abstract class ExLocalFactory  {
	private ExLocalFactory() {}
	
	public static <T extends Throwable> AbstractExceptionOfThreadLocal<T>  newAbstractExceptionOfThreadLocal(int arrayLenth ,Class<T> classType) {
		return new ExLocal1(arrayLenth, classType);
	}
	
//	static class AA{
//		//--一个类对应一个实体类
//		static AbstractExceptionOfThreadLocal<RuntimeException> newAbstractExceptionOfThreadLocal = ExLocalFactory.newAbstractExceptionOfThreadLocal(1, RuntimeException.class);
//		
//		void aaa() {
//			//在代码里面的一行对应一个下标
//			newAbstractExceptionOfThreadLocal.doThrowOfNew(0, "hahaha shenqingh cuo wu");
//		}
//	}
	
	
	static class ExLocal1<Run extends Throwable> extends  AbstractExceptionOfThreadLocal<Run>{

		Constructor<Run> constructor ; 
		public ExLocal1(int arrayLenth ,Class<Run> classType ) {
			super(arrayLenth);
			try {
				constructor = classType.getConstructor(String.class);
			} catch (NoSuchMethodException | SecurityException e) {
				try {
					Throwable throwable = classType.getConstructor().newInstance();
					CASE_THREADLOCAL.set(throwable);
				} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
						| InvocationTargetException | NoSuchMethodException | SecurityException e1) {
//					CASE_THREADLOCAL.set(e1);
//					CASE_THREADLOCAL.set(null);
				}
			}
		}
		@Override
		protected Run newInstance(String message) {
			try {
				return constructor.newInstance(new Object[] {message});
			} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
					| InvocationTargetException e) {
				return (Run) getCase();
			}
		}
	}
}
