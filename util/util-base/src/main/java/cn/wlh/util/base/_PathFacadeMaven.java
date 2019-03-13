package cn.wlh.util.base;

import java.io.File;

class _PathFacadeMaven extends _PathFacade{
	//相对路径
	String src_relative;
	//绝对路径
	String src_absolute; 
	public _PathFacadeMaven() {
		init();
		src_absolute = _Path.joinPath(System.getProperty("user.dir") , src_relative);
	}
	void init(){
		//src/main/java
		src_relative = _Path.joinPath("src","main","java" );
	}
	@Override
	public String absolutePath2ClassName(File file) {
		String absolutePath2 = file.getAbsolutePath();
		String substring = absolutePath2.substring(
				src_absolute.length() + _Path.SEPARATOR_LENGTH 
				, absolutePath2.lastIndexOf('.'));
		return _Path.toPoint(substring);
	}

	@Override
	public String className2AbsolutePath(String absolutePath) {
		String separator = _Path.toSeparator(absolutePath);
		return  _Path.joinPath( src_absolute , separator);
	}
	public String getSrc_relative() {
		return src_relative;
	}
	public String getSrc_absolute() {
		return src_absolute;
	}
}
