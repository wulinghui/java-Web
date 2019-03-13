package cn.wlh.util.base;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

public  abstract class _Reader{
	public static char[] notOutOfMemory(File fi) throws IOException {
		FileInputStream i = null;
		InputStreamReader in = null;
		try{
			i = new FileInputStream(fi);
			in = new InputStreamReader(i);
			BufferedReader br = new BufferedReader(in);
			//能存储30W行的代码,这对于正常 的代码和配置文件是够了的
			char [] cs = new char[(int) fi.length()];
//			br.skip(10);//skip就是跳的一个char的长度
			br.read(cs);
//			char[] buf = new char[1024];
//			int bytesRead;
//			StringBuffer sb = new StringBuffer();
//			in.read(buf);
//				sb.append(buf);
			return cs;
		}finally{
			if(i != null){
				i.close();
				in.close();
			}
		}
	}
	
	public static byte[] notOutOfMemory0(File fi) throws IOException {
		InputStream fin = null;
		try{
			fin =  new FileInputStream(fi);
			//能存储30W行的代码,这对于正常 的代码和配置文件是够了的
			byte [] cs = new byte[(int) fi.length()];
			fin.read(cs);
			return cs;
		}finally{
			if( fin != null){
				fin.close();  
			}
		}
	}
	/**释放资源
	 * @throws IOException */
	public static void realise(Reader... reader) throws IOException {
		int l = reader.length;
		for (int i = 0; i < l; i++) {
				if (reader[i] != null)	reader[i].close();
		}
	}
	
	public static char[] OutOfMemory(File fi,int arrayLength,ReaderHander h) throws IOException{
		FileInputStream i = null;
		InputStreamReader in = null;
		try{
			i = new FileInputStream(fi);
			in = new InputStreamReader(i);
			BufferedReader br = new BufferedReader(in);
			char [] cs = new char[ arrayLength ];
			do{
				if( br.read(cs) <= 0) break;
				h.hander(cs);
			}while(true);
			return cs;
		}finally{
			if(i != null){
				i.close();
				in.close();
			}
		}
	}
	public static interface ReaderHander{
		void hander(char [] cs);
	}
	public static void relealise(InputStream ... ins) throws IOException{
		int l = ins.length;
		if(ins[0] != null){//通常第一个找到了文件,之后的不会为空
			for (int i = 0; i < l ; i++) {
				ins[i].close();
			}
		}
	}
}

