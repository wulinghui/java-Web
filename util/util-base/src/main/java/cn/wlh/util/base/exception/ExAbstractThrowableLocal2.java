package cn.wlh.util.base.exception;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;

public class ExAbstractThrowableLocal2<Run extends Throwable> extends AbstractExceptionOfThreadLocal<Run> {
	Constructor<Run> constructor ; 
	public ExAbstractThrowableLocal2(int arrayLenth  ) {
		super(arrayLenth);
		ParameterizedType genericSuperclass = (ParameterizedType)this.getClass().getGenericSuperclass();
		Class<Run> classType =( Class<Run> ) genericSuperclass.getActualTypeArguments()[0];
		try {
			constructor = classType.getConstructor(String.class);
		} catch (NoSuchMethodException | SecurityException e) {
			try {
				Throwable throwable = classType.getConstructor().newInstance();
				CASE_THREADLOCAL.set(throwable);
			} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
					| InvocationTargetException | NoSuchMethodException | SecurityException e1) {
//				CASE_THREADLOCAL.set(e1);
//				CASE_THREADLOCAL.set(null);
			}
		}
	}
	
	@Override
	protected  Run newInstance(String message) {
		try {
			return constructor.newInstance(new Object[] {message});
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			return (Run) getCase();
		}
	}

}
