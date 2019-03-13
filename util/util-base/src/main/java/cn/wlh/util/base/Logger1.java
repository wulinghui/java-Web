package cn.wlh.util.base;

public class Logger1 extends Logger {
	Logger1(org.slf4j.Logger lOGGER) {
		super(lOGGER);
	}

	/** 给用户设置。
	 * @param flag
	 */
	public static void setFlag(int flag) {
		Logger.flag = flag;
	}
}
