package cn.wlh.framework.aop.factory;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import cn.wlh.framework.aop.proxy.Proxy;
import cn.wlh.framework.aop.proxy.ProxyManager;
import cn.wlh.util.base.ClassUtil;
import cn.wlh.util.base.JavaUtilFactory;
import cn.wlh.util.base.exception.ThrowableFacade;


/**
 * ����û��Ϊ���Ը�ֵ
 */
public class AopFactory /*extends FactoryInterfaceAdapt*/{
	private final Map<Class<?>, Object> BEAN_MAP = JavaUtilFactory.newMap(JavaUtilFactory.SELECT_OF_FIELD);
	private final Set<Class<?>> CLASS_SET;
	private Map<Class<?>, List<Proxy>> targetMap;
	/**
	 * @param basePackageNama ��Ӧ�ð������Ӱ����Ҵ���ͱ������ࡣ
	 */
	public AopFactory(String basePackageNama) {
		this(ClassUtil.getClassSet(basePackageNama));
	}
	/**
	 * @param cLASS_SET ��set�����Ҵ���ͱ������ࡣ
	 */
	public AopFactory(Set<Class<?>> cLASS_SET) {
		CLASS_SET = cLASS_SET;
		init();
	}
	public void init() {
		try {
			//������е����������
            Map<Class<?>, Set<Class<?>>> proxyMap = createProxyMap();
            //
//          Map<Class<?>, List<Proxy>> targetMap = createTargetMap(proxyMap);
            this.targetMap = createTargetMap(proxyMap);
            for (Map.Entry<Class<?>, List<Proxy>> targetEntry : targetMap.entrySet()) {
                try {
					Class<?> targetClass = targetEntry.getKey();
					List<Proxy> proxyList = targetEntry.getValue();
					//���ﱨ���Ͳ�����Map�����ˡ�
					Object proxy = ProxyManager.createProxy(targetClass, proxyList);
					//Ŀ����Ϊkey��.
					BEAN_MAP.put(targetClass, proxy);
				} catch (Throwable e) { 
				}
            }
        } catch (Exception e) {
            ThrowableFacade.THROW_RUN.doThrowOfRun(e);
        }
	}

    protected  Map<Class<?>, Set<Class<?>>> createProxyMap() throws Exception {
        Map<Class<?>, Set<Class<?>>> proxyMap = new HashMap<Class<?>, Set<Class<?>>>();
        Set<Class<?>> proxyClassSet = ClassUtil.getClassSetBySuper(Proxy.class, CLASS_SET);
        for (Class<?> proxyClass : proxyClassSet) {   
        	Set<Class<?>> targetClassSet = createTargetClassSet(proxyClass , proxyClassSet);
        	//������ϵ
        	proxyMap.put(proxyClass, targetClassSet);
        }
        return proxyMap;
    }
    /**����Target�༯�ϡ�
     * @param proxyClassSet 
     * @param proxyClass 
     * @param aspect
     * @return
     * @throws Exception
     */
    protected  Set<Class<?>> createTargetClassSet(Class<?> proxyClass, Set<Class<?>> proxyClassSet) throws Exception {
    	//TODO Ĭ��ΪCLASS_SET���治��Proxy�ľͶ��ǡ������ǳ������ȵĿ��ơ�
    	Set<Class<?>> targetClassSet = new HashSet<Class<?>>();
    	targetClassSet.addAll(CLASS_SET);
    	targetClassSet.removeAll(proxyClassSet);
        return targetClassSet;
    }

    protected  Map<Class<?>, List<Proxy>> createTargetMap(Map<Class<?>, Set<Class<?>>> proxyMap) throws Exception {
        Map<Class<?>, List<Proxy>> targetMap = new HashMap<Class<?>, List<Proxy>>();
        //����proxyMap����ת��targetMap
        for (Map.Entry<Class<?>, Set<Class<?>>> proxyEntry : proxyMap.entrySet()) {
            Class<?> proxyClass = proxyEntry.getKey();
            Set<Class<?>> targetClassSet = proxyEntry.getValue();
            //����targetClassSet��������ࡣ
            for (Class<?> targetClass : targetClassSet) {
                try {	//���ﱨ��Ͳ�����List���档
					Proxy proxy = (Proxy) proxyClass.newInstance();
					//Ŀ�����д������ˡ�
					if (targetMap.containsKey(targetClass)) {
					    targetMap.get(targetClass).add(proxy);
					} else {//Ŀ����û�д����ࡣ
					    List<Proxy> proxyList = new ArrayList<Proxy>();
					    proxyList.add(proxy);
					    //Ŀ����ʹ������ࡣ
					    targetMap.put(targetClass, proxyList);
					}
				} catch (Throwable e) {
				}
            }
        }
        return targetMap;
    }

    /**�����������������
	 */
//	public <T> T getInstance(Class<T> key, Object[] pars) {
    public Object getInstance(Class<?> key) {
		return BEAN_MAP.get(key);
	}
    /**��new�������������
	 */
    public Object newInstance(Class<?> targetClass) {
    	return ProxyManager.createProxy(targetClass, this.targetMap.get(targetClass) );
    }
    
	/**
	 * @return the bEAN_MAP
	 */
	public Map<Class<?>, Object> getBEAN_MAP() {
		return BEAN_MAP;
	}
	/**���CLASS_SET�����ĺ������ֶ�����init����ͬ��Map;
	 * @return the cLASS_SET
	 */
	public Set<Class<?>> getCLASS_SET() {
		return CLASS_SET;
	}
}
