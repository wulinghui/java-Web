package cn.wlh.framework.view;

import java.lang.reflect.Method;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public interface ViewInterface {

	/**ͨ��uri��ö�Ӧ����ķ���
	 * ����������uri���������һ����ӦMethod�ı�ʶ��
	 * @param uri
	 * @return
	 */
	Method getHandler(String uri);

	/**���û���̬��չ
	 * @param uri
	 * @param handler
	 */
	void addHandler(String uri, Method handler);

	/**��������
	 * @param request
	 * @param response
	 */
	void handleServlet(ServletRequest request, ServletResponse response);
	ServletResponse getThreadServletResponse();
	ServletRequest getThreadServletRequest();
}