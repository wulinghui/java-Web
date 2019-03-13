package cn.wlh.util.extend.string;

import cn.wlh.util.base._StringBuffer;

/**
 * @author wlh
 * 操作Java文件的SB..
 */
public abstract class OpretationJavaSb {
	StringBuffer sb = new StringBuffer();
	
	public StringBuffer getSb() {
		return sb;
	}
	public OpretationJavaSb setSb(StringBuffer sb) {
		this.sb = sb; return this;
	}  
	
	
	
	protected OpretationJavaSb impPackage(String className){
		String strPack = "import "+className+';';
		//防止重复导包引发的报错
		if( sb.indexOf(strPack) != -1) return this;
		_StringBuffer.insert(sb, ";", 0 , strPack);
		return this;
	}/**追加注解*/
	protected OpretationJavaSb appendNote(String describe){
		sb.append( "/**" + describe + "*/");
		return this;
	}/**追加注解*/
	protected OpretationJavaSb appendAnno(String anno){
		sb.append(anno);
		return this;
	}/**追加方法名*/
	protected OpretationJavaSb appendMethod(String method){
		sb.append( "public " + method + "{" /*"){"*/);
		return this;
	}
	protected OpretationJavaSb appendInner(String inner){
		sb.append(  inner ).append(';');
		return this;
	}
	
	//建议废弃下面的内容
	/**导入Class*/
	protected OpretationJavaSb impPackage(StringBuffer sb,String className){
		String strPack = "import "+className+';';
		//防止重复导包引发的报错
		if( sb.indexOf(strPack) != -1) return this;
		_StringBuffer.insert(sb, ";", 0 , strPack);
		return this;
	}/**追加注解*/
	protected OpretationJavaSb appendNote(StringBuffer sb,String describe){
		sb.append( "/**" + describe + "*/");
		return this;
	}/**追加注解*/
	protected OpretationJavaSb appendAnno(StringBuffer sb,String anno){
		sb.append(anno);
		return this;
	}/**追加方法名*/
	protected OpretationJavaSb appendMethod(StringBuffer sb,String method){
		sb.append( "public " + method + "{" /*"){"*/);
		return this;
	}
	protected OpretationJavaSb appendInner(StringBuffer sb,String inner){
		sb.append(  inner ).append(';');
		return this;
	}
}
