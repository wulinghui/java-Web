package cn.wlh.util.base.exception;

import java.io.PrintStream;
import java.io.PrintWriter;

public class ExceptionUtil2 extends RuntimeException {
	 final ThreadLocal<Throwable> THROWABLE = new ThreadLocal<>();
	 final ThreadLocal<Object []> EXCEPTION_INFO = new ThreadLocal<Object []>();
	 public void doThrowOfRun(Throwable e) throws RuntimeException{
		 doThrowOfRun(e,new Object[]{});
	 }
	public void doThrowOfRun(Throwable e,Object ...infos) throws RuntimeException{
		THROWABLE.set(e);
		EXCEPTION_INFO.set(infos);
		throw this;
	}
	
	
	/**
	 * @return the interrupt
	 */
	public  Throwable getThrowable() {
		return THROWABLE.get();
	}

	@Override
	public String getMessage() {
		return getThrowable().getMessage();
	}
	

	/* (non-Javadoc)
	 * @see java.lang.Throwable#getLocalizedMessage()
	 */
	@Override
	public String getLocalizedMessage() {
		// TODO Auto-generated method stub
		return getThrowable().getLocalizedMessage();
	}


	/* (non-Javadoc)
	 * @see java.lang.Throwable#toString()
	 */
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return getThrowable().toString();
	}


	/* (non-Javadoc)
	 * @see java.lang.Throwable#printStackTrace()
	 */
	@Override
	public void printStackTrace() {
		// TODO Auto-generated method stub
		getThrowable().printStackTrace();
	}


	/* (non-Javadoc)
	 * @see java.lang.Throwable#printStackTrace(java.io.PrintStream)
	 */
	@Override
	public void printStackTrace(PrintStream s) {
		// TODO Auto-generated method stub
		getThrowable().printStackTrace(s);
	}


	/* (non-Javadoc)
	 * @see java.lang.Throwable#printStackTrace(java.io.PrintWriter)
	 */
	@Override
	public void printStackTrace(PrintWriter s) {
		// TODO Auto-generated method stub
		getThrowable().printStackTrace(s);
	}


	/* (non-Javadoc)
	 * @see java.lang.Throwable#getStackTrace()
	 */
	@Override
	public StackTraceElement[] getStackTrace() {
		// TODO Auto-generated method stub
		return getThrowable().getStackTrace();
	}


	/* (non-Javadoc)
	 * @see java.lang.Throwable#setStackTrace(java.lang.StackTraceElement[])
	 */
	@Override
	public void setStackTrace(StackTraceElement[] stackTrace) {
		getThrowable().setStackTrace(stackTrace);
	}


	/**
	 * @return the throwable
	 */
	public  Object [] getExceptionInfo() {
		return EXCEPTION_INFO.get();
	}
}
