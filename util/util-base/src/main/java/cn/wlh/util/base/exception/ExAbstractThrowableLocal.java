package cn.wlh.util.base.exception;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;

public class ExAbstractThrowableLocal<Run extends Throwable> extends AbstractExceptionOfThreadLocal<Run> {
	Class<Run> classType ;
	public ExAbstractThrowableLocal(int arrayLenth  ) {
		super(arrayLenth);
		ParameterizedType genericSuperclass = (ParameterizedType)this.getClass().getGenericSuperclass();
		classType =( Class<Run> ) genericSuperclass.getActualTypeArguments()[0];
	}
	
	@Override
	protected  Run newInstance(String message) {
		try {
			return classType.getConstructor(String.class).newInstance(new Object[] {message});
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			try {
				return classType.getConstructor().newInstance();
			} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
					| InvocationTargetException | NoSuchMethodException | SecurityException e1) {
				CASE_THREADLOCAL.set(e1);
				return null;
				// throw new RuntimeException("zzz");
			}
		}
		
	}

}
