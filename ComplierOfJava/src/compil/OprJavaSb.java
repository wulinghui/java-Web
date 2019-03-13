package compil;

import java.lang.reflect.InvocationTargetException;

import util.invoke.InvokeRefUtil;
import util.invoke.InvokeReflect;
import util.string.buffer._StringBuffer;

/**
 * @author wlh
 * 所有方法都是操作sb,
 */
public  class OprJavaSb {
	StringBuffer sb = new StringBuffer();
	
	public StringBuffer getSb() {
		return sb;
	}
	public OprJavaSb setSb(StringBuffer sb) {
		this.sb = sb; return this;
	}
	
	public OprJavaSb() {
		super();
	}
	public OprJavaSb(StringBuffer sb) {
		super();
		this.sb = sb;
	}
	/**导入Class*/
	protected OprJavaSb impPackage(String className){
		String strPack = "import "+className+';';
		//防止重复导包引发的报错
		if( sb.indexOf(strPack) != -1) return this;
		_StringBuffer.insert(sb, ";", 0 , strPack);
		return this;
	}/**追加注解*/
	protected OprJavaSb appendNote(String describe){
		sb.append( "/**" + describe + "*/");
		return this;
	}/**追加注解*/
	protected OprJavaSb appendAnno(String anno){
		sb.append(anno);
		return this;
	}/**追加方法名*/
	protected OprJavaSb appendMethod(String method){
		sb.append( "public " + method + "{" /*"){"*/);
		return this;
	}
	protected OprJavaSb appendInner(String inner){
		sb.append(  inner ).append(';');
		return this;
	}
	protected OprJavaSb forIvoke(String method,String[] s) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		for (String string : s) {
			InvokeReflect.invoke(this, method, true, string);
		}
		return this;
	}
}