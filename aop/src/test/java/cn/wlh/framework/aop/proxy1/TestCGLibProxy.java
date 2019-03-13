package cn.wlh.framework.aop.proxy1;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.Callback;
import net.sf.cglib.proxy.CallbackFilter;
import net.sf.cglib.proxy.Enhancer;

/**
 * Create by zxb on 2017/4/22
 */
public class TestCGLibProxy {
 
    public static void main(String[] args) {
        DBQueryProxy dbQueryProxy = new DBQueryProxy();
        DBQueryProxy2 dbQueryProxy2 = new DBQueryProxy2();
        Enhancer enhancer = new Enhancer();
        enhancer.setUseCache(true);
        DBQuery dbQuery = getBean(DBQuery.class,dbQueryProxy, dbQueryProxy2, enhancer);
        System.out.println("====="+dbQuery.getClass());
        System.out.println( dbQuery.getClass().getClassLoader().getResource("/") );
        dbQuery = getBean(dbQuery.getClass(),dbQueryProxy, dbQueryProxy2, enhancer);
        System.out.println("========Inteceptor By DBQueryProxy ========");
        System.out.println(dbQuery.getElement("Hello"));
        System.out.println();
        System.out.println("========Inteceptor By DBQueryProxy2 ========");
        System.out.println(dbQuery.getAllElements());
    }

	public static DBQuery getBean(Class<?> cla,DBQueryProxy dbQueryProxy, DBQueryProxy2 dbQueryProxy2, Enhancer enhancer) {
		enhancer.setSuperclass(cla);
        enhancer.setCallbacks(new Callback[]{dbQueryProxy, dbQueryProxy2});
        enhancer.setCallbackFilter(new CallbackFilter() {
            public int accept(Method method) {
                if (method.getName().equals("getElement")) {
                    return 0;
                } else {
                    return 1;
                }
            }
        });
        DBQuery dbQuery = (DBQuery) enhancer.create();
		return dbQuery;
	}
}
