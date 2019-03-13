package cn.wlh.util.extend.map;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import cn.wlh.util.base._File;
import cn.wlh.util.base._File.Filter;
import cn.wlh.util.base._File.HandlerFile;
import cn.wlh.util.base._Reader;
import cn.wlh.util.extend.wrap.StringWrap;

/**该类最初是设计用于FreeMarker框架用Map控制.
 * @author wlh
 * 获得一个文件的Map
 * 默认以文件名为Key  文件里面的内容为Value
 * 可以重写过滤的方式
 * 也可以重写获得Key的方式
 * 但不能重写获得Value的内容...
 */
public class FileMap {
	final Filter filter;
	final MapHanler handler;
	
	/**自己定义
	 * @param filter  	过滤的方式
	 * @param handler	自己定义key的方法
	 * 我们可以这样用递归的获得某一包名下所有的文件.不过滤文件就可以有优先级的说法了
	 */
	public FileMap(Filter filter, MapHanler handler) {
		this.filter = filter;
		this.handler = handler;
	}
	/** 不递归 的过滤
	 * */
	public FileMap() {
		this( Filter.NODIGUI , new MapHanler( new StringWrap.Map() ) );
	}
	
	/**
	 * @param strings 相对路径.
	 */
	public Map<String,String> initMap(String...strings) throws IOException{return initMap0( _File.getAbsoluteFromWork(strings));}
	/**
	 * @param file 绝对路径
	 */
	public Map<String,String> initMap0(String file ) throws IOException{ return initMap(new File(file));}
	/**
	 * @param fi 文件夹.遍历获得Map
	 */
	public Map<String,String> initMap(File fi ) throws IOException  {
//		System.out.println( "initMap=" + fi );
		_File.forFile( fi, this.filter,  this.handler);
		return handler.map;
	}
	public static void main(String[] args) throws IOException {
		//		src/util/base/jdk7
		File file = new File( "E:\\workSpace\\Util5\\src\\util\\extend");
//		System.out.println(file.exists());			
//		System.out.println( file.isFile() ); 		
//		System.out.println( file.isDirectory() );	 
//		file.listFiles(new java.io.FileFilter() {
//			
//			@Override
//			public boolean accept(File pathname) {
//				System.out.println("pathname="+pathname);
//				return false;
//			}
//		});
//		_Sysout.print( file.listFiles( new FileFilter.NoDiGui() ) );
		
		System.out.println(new FileMap().initMap("src","util","extend").size() );;
	}
	/**
	 * @author wlh
	 * getKey() -- 确定key
	 */
	public static class MapHanler implements HandlerFile<IOException>{
		final Map<String,String> map;
		public MapHanler(Map<String,String> map) {
			this.map = map;
		}
		@Override
		public final void handler(File file) throws IOException  {
			map.put( getKey(file) , 
					String.valueOf( _Reader.notOutOfMemory(file) ) );
		}
		protected String getKey(File file) { return file.getName(); }
	}
}