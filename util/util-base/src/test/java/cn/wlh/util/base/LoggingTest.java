package cn.wlh.util.base;

import java.util.logging.Logger;

import org.junit.Test;

public class LoggingTest {
	Logger log = Logger.getLogger( String.class.getName() );
	@Test
	public void test() {
		
		/*
		 * java.util.logging.LogRecord.inferCaller()
		 * 源码获得值栈  --八月 11, 2018 9:23:51 上午 cn.wlh.util.base.LoggingTest test 信息内容
		 */
		log.info("zzzzzzz");
		log.info("zzzzzzz"); //level	Level  (id=41)   resourceBundleName	"sun.util.logging.resources.logging" (id=72)	
		//sourceClassName	"cn.wlh.util.base.LoggingTest" (id=64)	   sourceClassName	"cn.wlh.util.base.LoggingTest" (id=64)	
		info("1111111111");
	}
	public void info(String message) {
		log.info(message);
	}
	
	public static void main(String[] args) {
		/* 直接在这里的时候
		 * depth=1
		 * cname=cn.wlh.util.base.LoggingTest
		 * getMethodName=main
		 */
		dierceng();
		/* 
		 * depth=2
cname=cn.wlh.util.base.LoggingTest
getMethodName=dierceng
cname=cn.wlh.util.base.LoggingTest
getMethodName=main
		 */
	}
	public static void dierceng() {
		// sun.misc.JavaLangAccess
		sun.misc.JavaLangAccess access = sun.misc.SharedSecrets.getJavaLangAccess();
        Throwable throwable = new Throwable();
        int depth = access.getStackTraceDepth(throwable);
        System.out.println( "depth="+depth );
        boolean lookingForLogger = true;
        
        /**一样的*/
        StackTraceElement[] stackTrace = throwable.getStackTrace();
        
        for (int ix = 0; ix < depth; ix++) {
            // Calling getStackTraceElement directly prevents the VM
            // from paying the cost of building the entire stack frame.
            StackTraceElement frame =
                access.getStackTraceElement(throwable, ix);
            String cname = frame.getClassName();
            System.out.println( "cname="+cname );
            System.out.println( "getMethodName="+frame.getMethodName() );
            System.out.println( "getLineNumber="+ frame.getLineNumber() );
            
        }
        // We haven't found a suitable frame, so just punt.  This is
        // OK as we are only committed to making a "best effort" here.
	}
}
