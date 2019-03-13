package cn.wlh.util.extend.database.file;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import cn.wlh.util.base._File;
import cn.wlh.util.base._Serializt;
import cn.wlh.util.base.interfaces.Seri_Clone;

@SuppressWarnings("serial")
public class _FileBase<T> implements Seri_Clone{
	final String suf;//表名--后缀
	final String directory;//分表--文件夹路径
	final String storePath;//存储的路径
	final Class<T> type;//该类可存储..
	static final String ALL_TABLE = _File.joinPath(_File.WOKE_PATH , "src" , "Table.all");
	static {
		try {
			_File.createFile( new File( ALL_TABLE ) );
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public _FileBase(String suf, String directory, String storePath, Class<T> type) {
		this.suf = suf;
		this.directory = directory;
		this.storePath = storePath;
		this.type = type;
		//TODO 登记模式.
		Map<String,Class<?>> map = new HashMap<String,Class<?>>();
		File f = new File( ALL_TABLE );
		try {
			map = _Serializt.deserializeObj( f );
		} catch (Exception e) {
			try {//说明没有文件..
				_File.createFile(f);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		map.put( _File.joinPath(this.storePath,this.directory,this.suf) , type );
		try {
			_Serializt.serializeObj(map, f);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public _FileBase(String suf, String directory, Class<T> type) {
		this( suf , directory , _File.WOKE_PATH , type);//默认是工作空间..
	}
	public  String getAbsolutePath(String id){//id文件名
		return _File.joinPath(storePath,directory, id2Suf(id) );
	}
	public T getBean(String id) throws Exception  {
		return _Serializt.deserializeObj( new File( getAbsolutePath(id) ) );
	}
	public  void storeBean(T t,String id) throws IOException {
		_Serializt.serializeObj(t, new File( getAbsolutePath(id) ) );
		
	}
	/** 	id是把 .当成 / 		*/
	protected String id2Suf(String id) { return _File.toSeparator(id) + suf;}
	
}
