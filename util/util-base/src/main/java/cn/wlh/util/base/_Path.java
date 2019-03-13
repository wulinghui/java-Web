package cn.wlh.util.base;

import java.io.File;


public class _Path {
	static _PathFacade pfc = getInstance();
	
	public static _PathFacade getInstance() {
		return new _PathFacadeMaven();
	}
	/**项目路径 */
	public static final String WOKE_PATH = System.getProperty("user.dir");
	/**项目的文件夹*/
	public static final File WOKE_FILE = new File( WOKE_PATH );
	/**分割符的长度*/
	public static final int SEPARATOR_LENGTH = File.separator.length();
	/**拼接路径
	 * @param strings
	 * @return
	 */
	public final static   String joinPath(String...strings ){
		int len = strings.length;
		String string = strings[0];
		for (int i = 1; i < len ; i++) {
			string += File.separatorChar + strings[i];
		}
		return string;
	}
	/** 把完全限定名转化成不同系统的路径分隔符 */
	public final static String toSeparator(String classFullName){return classFullName.replaceAll("\\.", "\\"+ File.separator);
	}/**把不同系统的路径分隔符转化成完全限定名*/
	public final static String toPoint(String pathSeparater){return pathSeparater.replaceAll("\\"+File.separator, "\\.");}
	
	/** 文件转类名
	 * @param file 文件名  c:/work/.../com/cn/Test.class
	 * @return com.cn.Test
	 */
	public static String absolutePath2ClassName(File file) {
		return pfc.absolutePath2ClassName(file);
	}
	/**
	 * @param file 支持相对,绝对,统一定位.
	 * @return
	 */
	public static String absolutePath2ClassName(String file) {
		return pfc.absolutePath2ClassName(new File(file));
	}
	/**类名转路径
	 * @param className 类名  com.cn.Test
	 * @return c:/work/.../com/cn/Test
	 */
	public static  String className2AbsolutePath(String className) {
		return pfc.className2AbsolutePath(className);
	}
	/**java源码相对路径
	 * @return
	 */
	public static String getSrc_relative() { 
		return pfc.getSrc_relative();
	}
	/**java源码绝对路径
	 * @return
	 */
	public static String getSrc_absolute() {
		return pfc.getSrc_absolute();
	}
}
