package cn.wlh.util.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/** 
 * @author wulinghui
 * 必须\n结束--且后者覆盖前者
 */
public abstract class _Properties {
	public static Properties   pro = new Properties();//+"\\wlh_sql\\"自己单独再写
	public static  String  projectPath = System.getProperty("user.dir");
	public static final String configPath = projectPath + "\\wlh\\";
	public static Properties getInstance(File file) throws IOException {
		FileInputStream in1 = null;
		Properties   pro = new Properties();
		try {
			in1 = new FileInputStream( file );
			pro.load(in1);
		}finally {
			if(in1 !=null) in1.close();
		}
		return pro;
	}
	/**
	 * @param path
	 * @param key	
	 * @param defaultValue 如果没有的话,他将会将会代替
	 * @return
	 * @throws IOException 
	 * @throws TsException
	 */
	public static String  get(String absolutePath,String key, String defaultValue) throws IOException  {
		FileInputStream in1 = null;
		try {
			in1 = new FileInputStream(absolutePath);
			pro.load(in1);
		}finally {
			if(in1 !=null) in1.close();
		}
		return pro.getProperty(key, defaultValue);
	}
	/**
	 * @param writer
	 * @param comments --描述
	 * @throws IOException 
	 * @throws TsException
	 */
	public static void put(FileWriter writer,String comments) throws IOException {
			pro.store(writer, comments);
	}
	
	
	/**
	 * @param absolutePath
	 * @param append 覆盖还是追加
	 * @param describe --这次操作的描述
	 * @param keyAndValue
	 * @throws IOException 
	 * @throws TsException
	 */
	public static void put(String absolutePath,boolean append,String describe,String... keyAndValue) throws IOException  {
		FileOutputStream oFile = null;
		try {
			oFile = new FileOutputStream(absolutePath,append);//true表示追加打开
			 int len = keyAndValue.length;
			for (int i = 0; i < len; i += 2) {
				pro.setProperty(keyAndValue[i], keyAndValue[i+1]);
			}
			pro.store(oFile, describe);
		}finally {
				oFile.close();
		}
	}
	public static List<String> foreachKey(File file,ForKey fo) throws IOException  {
		List<String> list = new ArrayList<String>();
		FileInputStream in1 = null;
		try{
			in1 = new FileInputStream(file);
			pro.load(in1);
			for (Object iterable_element : pro.keySet()) {
				String str = fo.keyAndValue((String)iterable_element,(String) pro.get(iterable_element));
				list.add(str);
			}
		}finally {
				if(null != in1)	in1.close();
		}
		return list;
	}
	public static abstract class ForKey{
		public abstract String keyAndValue(String key,String value);
	}
}
