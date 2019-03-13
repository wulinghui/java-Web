package cn.wlh.util.base;

import java.io.File;

/**
 * @author 吴灵辉
 * 默认是maven
 */
abstract class _PathFacade {
	
	/** 文件转类名
	 * @param file 文件名  c:/work/.../com/cn/Test.class
	 * @return com.cn.Test
	 */
	public abstract  String absolutePath2ClassName(File file);
	/**类名转路径
	 * @param className 类名  com.cn.Test
	 * @return c:/work/.../com/cn/Test
	 */
	public abstract  String className2AbsolutePath(String className);
	/**java源码相对路径
	 * @return
	 */
	public abstract String getSrc_relative() ;
	/**java源码绝对路径
	 * @return
	 */
	public abstract String getSrc_absolute() ;
}
