package cn.wlh.framework.web.cms.aop;

import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.util.Arrays;

import cn.wlh.framework.aop.proxy.AspectProxy;
import cn.wlh.util.base.Logger;

public class LogAop extends AspectProxy {
	private static final Logger log = Logger.getLogger(LogAop.class);
	@Override
	public void after(Class<?> cls, Method method, Object[] params, Object result) throws Throwable {
		if( result ==null) return;
		String name = method.getName();
		if (name.contains("Log")) {
			result = ArrayFont(result);
			char[] charArray = name.toCharArray();
			log.log(charArray[charArray.length - 1],   result.toString() + "\t Of " + method);
		}
	}
	/** 格式化数组。
	 * @param result
	 * @return
	 */
	public static Object ArrayFont(Object result) {
		if (result.getClass().isArray()) {
			int length = Array.getLength(result);// 反射
			StringBuilder sb = new StringBuilder();
			sb.append('[');
			for (int j = 0; j < length; j++) {
				sb.append('{');
				sb.append(Array.get(result, j)/* 对象 */);
				sb.append("},");
			}
			sb.deleteCharAt(sb.length() - 1);//
			sb.append(']');
			// 用 sb 代替 内容.
			result = sb;
		}
		return result;
	}
}
