package cn.wlh.framework.view;

import java.lang.reflect.Method;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public interface ViewInterface {

	/**通过uri获得对应处理的方法
	 * 并不仅限于uri，这个就是一个对应Method的标识。
	 * @param uri
	 * @return
	 */
	Method getHandler(String uri);

	/**供用户动态扩展
	 * @param uri
	 * @param handler
	 */
	void addHandler(String uri, Method handler);

	/**处理请求。
	 * @param request
	 * @param response
	 */
	void handleServlet(ServletRequest request, ServletResponse response);
	ServletResponse getThreadServletResponse();
	ServletRequest getThreadServletRequest();
}