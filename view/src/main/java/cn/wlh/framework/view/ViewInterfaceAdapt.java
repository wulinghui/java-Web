package cn.wlh.framework.view;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.ArrayUtils;

import cn.wlh.util.base.JavaUtilFactory;
import cn.wlh.util.base.RecordNewOfLog;
import cn.wlh.util.base.exception.ThrowableFacade;

/**
 * @author �����
 * ��Ϊһ��ϵͳִ��service�ķ�ʽҲ����ȷ���Ĳ���Ҫ��̬�ı䡣���Բ���ҪManage����������ViewInterface��
 * ע����Ҫ�ֶ�����init������
 */
public  class ViewInterfaceAdapt extends RecordNewOfLog implements ViewInterface {
	private final Map<String,Method> handlerMap = JavaUtilFactory.newMap(JavaUtilFactory.SELECT_OF_FIELD);
	private final ThreadLocal<ServletRequest> threadHttpServletRequest = new ThreadLocal<ServletRequest>();
	private final ThreadLocal<ServletResponse> threadHttpServletResponse = new ThreadLocal<ServletResponse>();
	public ViewInterfaceAdapt() {
		super('w');
	}
	public ViewInterfaceAdapt(Collection<Class<?>> controllerClassSet) {
		super('w');
//		init(controllerClassSet);
	}
	/* (non-Javadoc)
	 * @see cn.wlh.framework.web.ViewInterface#getHandler(java.lang.String)
	 */
	/* (non-Javadoc)
	 * @see cn.wlh.framework.web.AAA#getHandler(java.lang.String)
	 */
	public Method getHandler(String uri) {
		return handlerMap.get(uri);
	}
	/* (non-Javadoc)
	 * @see cn.wlh.framework.web.ViewInterface#addHandler(java.lang.String, java.lang.reflect.Method)
	 */
	/* (non-Javadoc)
	 * @see cn.wlh.framework.web.AAA#addHandler(java.lang.String, java.lang.reflect.Method)
	 */
	public void addHandler(String uri,Method handler) {
		handlerMap.put(uri, handler);
		handler.setAccessible(true);
	}
	/* (non-Javadoc)
	 * @see cn.wlh.framework.web.ViewInterface#handleServlet(javax.servlet.ServletRequest, javax.servlet.ServletResponse)
	 */
	/* (non-Javadoc)
	 * @see cn.wlh.framework.web.AAA#handleServlet(javax.servlet.ServletRequest, javax.servlet.ServletResponse)
	 */
	public  final void handleServlet(ServletRequest request,ServletResponse response) {
		try {
			//���uri
			String uri = getUri(request);
			Method handler = getHandler(uri);
			//���ִ�з����Ķ���
			Object obj = getInvokeObject(handler, request);
			//���ִ�з����Ĳ���
			Object[] args = getInvokeArgs(handler, request, response);
			try {
				threadHttpServletRequest.set(request);
				threadHttpServletResponse.set(response);
				invokeMethod(handler, obj, args);
			} catch (Throwable e) {
				ThrowableFacade.THROW_RUN.doThrowOfRun(e, "please Method args");
			}   
		} finally {
			threadHttpServletRequest.remove();
			threadHttpServletResponse.remove();
		}
	}
	protected void invokeMethod(Method handler, Object obj, Object[] args)
			throws Throwable {
		handler.invoke(obj, args);
	}
	
	/* (non-Javadoc)
	 * @see cn.wlh.framework.web.AAA#getThreadServletResponse()
	 */
	public final ServletResponse getThreadServletResponse() {
		return threadHttpServletResponse.get();
	}
	/* (non-Javadoc)
	 * @see cn.wlh.framework.web.AAA#getThreadServletRequest()
	 */
	public final ServletRequest getThreadServletRequest(){
		return threadHttpServletRequest.get();
	}
	protected Object[] getInvokeArgs(Method handler, ServletRequest request, ServletResponse response) {
		return new Object[] {
				request , response
		};
	}
	protected String getUri(ServletRequest request) {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		String uri = httpRequest.getRequestURI();
		return uri;
	}
	/**Ĭ�Ϸ����ö���
	 * @param handler
	 * @param request
	 * @return
	 */
	protected Object getInvokeObject(Method handler, ServletRequest request) {
		try {
			return handler.getDeclaringClass().newInstance();
		} catch (Exception e) {
			return null;
		}
	}
	protected void init(Collection<Class<?>> controllerClassSet) {
		if (controllerClassSet != null && !controllerClassSet.isEmpty() ) {
            for (Class<?> controllerClass : controllerClassSet) {
                Method[] methods = getAllMethods(controllerClass);
                if (ArrayUtils.isNotEmpty(methods)) {
                    for (Method method : methods) {
                        if ( canInitHandler(method) ) {
                            String uri = toUri(method);
                            addHandler(uri, method);
                            //��������ÿ��Խ��롣
//                          method.setAccessible(true); ����add������
                        }
                    }
                }
            }
        }
	}
	/**�����Ҫ���й��˵ķ���
	 * Ĭ���Ǳ����������������ࡣ
	 * @param controllerClass
	 * @return
	 */
	protected Method[] getAllMethods(Class<?> controllerClass) {
		return controllerClass.getDeclaredMethods();
	}
	/**���uri��ʶ����Ӧ��ִ��·��
	 * @param method
	 * @return
	 */
	protected String toUri(Method method) {
		Class<?> declaringClass = method.getDeclaringClass();
		return declaringClass.getSimpleName() + "/" + method.getName();
	}
	/**����initHandler��method
	 * Ĭ�϶����Խ���
	 * @param method
	 * @return
	 */
	protected boolean canInitHandler(Method method) {
		return true;
	}
	@Override
	public String toString() {
		return super.toString();
	}
	/*public static void main(String[] args) {
		for (Method string : ViewInterfaceAdapt.class.getMethods()) {
			System.out.println(string.getName()+"\t"+string.getDeclaringClass());
		}
	}*/
}
