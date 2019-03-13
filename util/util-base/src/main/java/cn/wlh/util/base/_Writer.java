package cn.wlh.util.base;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

public  abstract class _Writer {
	/**一次性写入,小心jvm内存,csq.toString()*/
	public static void oneWrite(File fi,boolean appendFlag,Object csq) throws IOException{
		FileWriter fw = null;
		BufferedWriter bf= null;
		try{
			fw = new FileWriter(fi,appendFlag);//会有异常
			bf  = new BufferedWriter(fw);//没有异常..
			//空指针
			if( csq != null)	bf.write(csq.toString());
		}finally{
//			realise(fw,bf);
			realise(bf , fw);
		}
	}
	/**释放资源
	 * @throws IOException */
	public static void realise(Writer... writer) throws IOException {
		int len = writer.length;
		for (int i = 0; i < len; i++) {
			if (writer[i] != null)	writer[i].close();
		}
	}
}
