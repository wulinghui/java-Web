package cn.wlh.util.base;

import java.io.File;
import java.io.IOException;

import javax.tools.JavaFileObject.Kind;
/* 
 *  实现热部署，自定义ClassLoader，加载的是.class 	.java
 */  
public class _ClassLoader  {
	String url;
	String kind;
	/** url 下面只能放2进制的.class文件内容
	 *  @param url  --基目录.
	 *  @param suff --文件后缀.
	 */
	public _ClassLoader(String url, String suff) {
		super();
		this.url = url;
		this.kind = suff;
	}
	public _ClassLoader(String url, Kind kind) { this(url, kind.extension); }
	public _ClassLoader(String url) {this(url,Kind.CLASS);}  
	
	public class Loader extends ClassLoader {
		File file;
		String fullClassName;
		
		public Loader(String fullClassName) throws IOException {
			super(null); // 指定父类加载器为 null
			loader(fullClassName);
		}
		public Loader(String[] fullClassNames) throws IOException {
			super();//默认指定 系统的加载器..
			for (String string : fullClassNames) {
				loader(string);
			}
		}
		private void loader(String fullClassName) throws IOException {
			this.fullClassName = fullClassName;
			 String str = _File.joinPath(url , _File.toSeparator(fullClassName) + kind );
			file = new File ( str ) ;
			byte[] raw = _Reader.notOutOfMemory0(file);
			// 这个,把class的内容加载到jvm里  -- 这个一定要有...否则没有用jvm都没加载
			super.defineClass(fullClassName , raw, 0, raw.length);
		}
//	   @Override // 不重写也行
//	    protected Class loadClass(String name, boolean resolve)  
//	            throws ClassNotFoundException {
//	        Class cls = null;  
//	        cls = super.findLoadedClass(name);  // 系统的 有     
//	        if (cls == null)  
//	            cls = super.getSystemClassLoader().loadClass(name); // 系统没有  
//	        if (cls == null)  
//	            throw new ClassNotFoundException(name);  
//	        if (resolve)  
//	        	super.resolveClass(cls);  //系统有
//	        return cls;  
//	    }
	         
	}
}  
