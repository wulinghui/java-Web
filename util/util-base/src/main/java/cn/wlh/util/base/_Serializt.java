package cn.wlh.util.base;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

public  abstract class _Serializt{
	public static void SerializePerson(Object obj,String filePath) throws FileNotFoundException, IOException {  
		serializeObj(obj, new File(filePath) );
	}
	public static void serializeObj(Object obj,File file) throws FileNotFoundException, IOException {  
    	_File.createFile( file );
        // ObjectOutputStream 对象输出流，将Person对象存储到E盘的Person.txt文件中，完成对Person对象的序列化操作  
    	FileOutputStream fos = new FileOutputStream(file);
        ObjectOutputStream oo = new ObjectOutputStream(fos);  
        oo.writeObject(obj);
        fos.close();
        oo.close();
    }  
    @SuppressWarnings("unchecked")
	public static <T> T deserializeObj(File file) throws  Exception {  
    	FileInputStream fis = new FileInputStream(file);
        ObjectInputStream ois = new ObjectInputStream(fis);  
        Object ob = ois.readObject();  
        fis.close();
        ois.close();
        return (T) ob;  
    }
    /**
	 * 序列化到指定的输出流中
	 * @param user
	 * @param out --不被关闭
	 * @throws IOException
	 */
	public static void serialize(Object user,OutputStream out) throws IOException {
		 ObjectOutputStream oos = null;
	     oos = new ObjectOutputStream(out);
	     oos.writeObject(user);
	     oos.flush();
	     oos.close();
	}
	/**反序列化成对象
	 * @param in --不被关闭
	 * @return
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public static <T> T deserialize(InputStream in) throws IOException, ClassNotFoundException {
		 ObjectInputStream ois = null;
	     ois = new ObjectInputStream(in);
	     T t = (T) ois.readObject();
	     ois.close();
	     return  t;
	}
    /**通过序列化实现的克隆对象
	 * @param obj
	 * @return 实现了序列化对象的类
     * @throws IOException 
     * @throws ClassNotFoundException 
	 * @throws TsException 
	 */
	public static   Object clone(Object obj) throws IOException, ClassNotFoundException  {  
        // 拷贝产生的对象  
          // 读取对象字节数据  
         ByteArrayOutputStream baos =  new ByteArrayOutputStream();  
         ObjectOutputStream oos = new ObjectOutputStream(baos);
         ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());  
         ObjectInputStream ois = new ObjectInputStream(bais);
		 oos.writeObject(obj);
//		 T	clonedObj = (T) ois.readObject();  
          // 分配内存空间，写入原始对象，生成新对象  
          //返回新对象，并做类型转换  
       return ois.readObject();  
    }
}
