package cn.wlh.util.base;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.nio.channels.FileChannel;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.StringTokenizer;
import static cn.wlh.util.base.Config.*;
/**
 * @author 吴灵辉
 * @see <a>cn.wlh.util.base._Path</a>
 * 由于不同操作系统,不同的编译存放环境。导致 path <=> class  变得异常复杂
 * 于是学习java.lang.io.File的门面模式。来解决面临的问题。
 */

public abstract class _File {
	public static final String WOKE_PATH = _Path.getWorkPath0();//System.getProperty("user.dir");
	public static final String separator = "\\"+File.separator;
	/**创建文件*/
	public static boolean createFile(File file) throws IOException{
		File fileParent = file.getParentFile(); 
		//如果有文件的化就不创建文件夹了,这时再往下创建就失败了
		if(!fileParent.exists())	fileParent.mkdirs();
		//该文件是文件夹的话就先简单删除.
		if(file.exists() && file.isDirectory() ) file.delete();//不知道file.exists()是否是多余的校验 
		//如果文件不存在就创建
		if(!file.exists())	return	file.createNewFile();
		//TODO 强力创建文件,同时如果文件夹下面有文件,还不能简单的删除 
		return false;
	}
	 static class _Path{
		/**获得类加载根路径		/E:/workSpace/Util5/bin/  */
		public static URL getClassRoot(Class<?> c) {
			return c.getResource("/");//	/E:/workSpace/Util5/bin/
		}/**获得类加载根绝对路径*/
		public static URL getAbsoluteClassRoot(Class<?> c) {
			return c.getClassLoader().getResource("");//	file:/E:/workSpace/Util5/bin/
		}/**获得当前类所在的工程路径 */
		public static URL getClassPath(Class<?> c) {
			return c.getResource("");//	/E:/workSpace/Util5/bin/test/
		}/**项目路径
		 * @throws IOException */
		public static String getWorkPath() throws IOException {
			return new File("").getCanonicalPath();//	E:\workSpace\Util5
		}/**项目路径*/
		public static String getWorkPath0() {
//			E:\workSpace\Util5
			return System.getProperty("user.dir");
		}
		/**获得所有的类路径,包括jar包路径*/
		public static String getPath() {
			//E:\workSpace\Util5\bin;F:\xvxm\32\eclipse\plugins\org.junit_4.12.0.v201504281640\junit.jar;F:\xvxm\32\eclipse\plugins\org.hamcrest.core_1.3.0.v201303031735.jar;F:\xvxm\32\eclipse\configuration\org.eclipse.osgi\413\0\.cp;F:\xvxm\32\eclipse\configuration\org.eclipse.osgi\412\0\.cp
			return System.getProperty("java.class.path");
		}
	}
	/** 把完全限定名转化成不同系统的路径分隔符 */
	public static String toSeparator(String classFullName){return classFullName.replaceAll("\\.", separator);
	}/**把不同系统的路径分隔符转化成完全限定名*/
	public static String toPoint(String pathSeparater){return pathSeparater.replaceAll(separator, "\\.");}
	/**从工作空间获得文件名@param xddvlujn -- 相对路劲 */
	public static String getFileNameFromWork(String xddvlujn){return WOKE_PATH + separator + xddvlujn;}
	/**从工作空间获得绝对路径   @param xddvlujn -- 自动拼接  */
	public static String getAbsoluteFromWork(String...strings ) { return  WOKE_PATH + separator + joinPath(strings);}
	/** 获得工作空间的相对路径  开头没/*/
	public static String getWorkRelativePath(File file){ return file.getAbsolutePath().substring( (WOKE_PATH+separator) .length() -1 ); }
	/**从工作空间中获得存放源代码的路径
	 * @throws IOException */
	public static File getSourcePath(){
		// 直接用   先判断是否是maven的标准    再看 src
		File file = new File ( joinPath(WOKE_PATH ,  CONF[0][1] , CONF[0][2] , CONF[0][3] ) );
		file = file.exists() ? file : new File ( joinPath(WOKE_PATH ,  CONF[0][1] ) ) ;
		return file;
	}
	/** 拼接路径
	 * E:\workSpace\Util5\\src\test\StringTest.java  --正确的..
	 * */
	public static String joinPath(String...strings ){
		int len = strings.length;
		String string = strings[0];
		for (int i = 1; i < len ; i++) {
			string += separator + strings[i];
		}
		return string;
	}
	/**拼接的长度
	 * @param "abc","def"
	 * @return "abc\def".length();
	 * */
	public static int joinLength(String ...strings){
		int len = strings.length;
		if(len < 2) throw new IndexOutOfBoundsException();
		int poinLength = File.separator.length();
		int index = strings[0].length();
		for (int i = 1; i < len ; i++) {
			index += poinLength + strings[i].length();
		}
		return index;
	}
	/**通过src下面的绝对路径2完全限定名 适用性低*/@Deprecated
	public static String absolutePath2ClassNameFromSrc(String absolutePath){
		return toPoint( absolutePath.substring( 
				_File.joinLength( _File.WOKE_PATH,(String) Config.CONF[0][1])  +  Config.INT[0][0]  
				                  , absolutePath.indexOf(  (String) Config.CONF[0][0])  ) );
	}
	/** 绝对路径2完全限定名 适用性低*/@Deprecated
	public static String absolutePath2ClassName(String absolutePath,String src,String end){
		return toPoint( absolutePath.substring(
				_File.joinLength( _File.WOKE_PATH,src ) + Config.INT[0][0]  
				       , absolutePath.indexOf( end) ) );
	}
	/**通过class的Root--绝对路径2完全限定名 */@Deprecated
	public static String absolutePath2ClassName(String absolutePath,Class<?> c){
		
		int end = absolutePath.indexOf('.');
		int start = _Path.getClassRoot(c).getPath().length()-1;
		System.out.println( "_Path.getClassRoot(c).getPath()==" + _Path.getClassRoot(c).getPath() );
		System.out.println( "absolutePath=="+absolutePath + start + end ); 
		return toPoint(      
				absolutePath.substring( 			// TODO 这是length() --所以要-1
				_Path.getClassRoot(c).getPath().length()-1  , //start-classroot  TODO -1
				absolutePath.indexOf('.') )  );//end -- .class 或 .java都有.
	} /**通过class的Root--绝对路径2完全限定名 */         @Deprecated
	public static String absolutePath2ClassName(String absolutePath){
		return absolutePath2ClassName(absolutePath , String.class);
	}
	 /**通过.java的Root--绝对路径2完全限定名 */    @Deprecated  
	public static String absolutePath2ClassNameOfSrc(String absolutePath){
		int end = absolutePath.indexOf('.');
		int start = getSourcePath().getAbsolutePath().length() +1 ;
		System.out.println( "_Path.getClassRoot(c).getPath()==" + getSourcePath().getAbsolutePath() );
		System.out.println( "absolutePath=="+absolutePath + start + end ); 
		return toPoint(      
				absolutePath.substring( 			// TODO 这是length() --所以要-1
				getSourcePath().getAbsolutePath().length() +1 , //start-classroot TODO +1
				absolutePath.indexOf('.') )  );
	}
	/** 包名2绝对源路径 --src下的路径  ,不建议使用  */@Deprecated
	public static String package2SourcePath(String packa){ return package2SourcePath( (String) Config.CONF[0][1] , packa); }
	/** 包名2绝对源路径 --src  */@Deprecated
	public static String package2SourcePath(String src,String packa){
		return joinPath( _File.WOKE_PATH, src , toSeparator(packa) ); 
	}
	/** 包名2绝对class路径 --bin下的路径  ,不建议使用 */@Deprecated
	public static String package2ClassPath(Class<?> cla , String packa){
		return joinPath( _Path.getClassRoot(cla).getPath() ,toSeparator( packa ) ); 
	}
	/** cla-该包下面的一个类   包名2绝对路径*/@Deprecated
	public static String package2Path(Class<?> cla) {
		return _Path.getClassPath(cla).getPath();
	}
	/** 替换工作空间项目中的相对路径 */
	public static File repalceRelativePathOfWork(File file,String regex,String replacement)  {
		return 	new File( joinPath( _Path.getWorkPath0() , 
				getWorkRelativePath(file).replaceAll(regex, replacement) ) );
	}
	/**遍历了所有的文件,除了自己.
	 * @param fi
	 * @param h
	 * @throws Ex 
	 * @throws Throwable 
	 */@Deprecated
	public static <Ex extends Throwable> void forFile(File fi,FileFilter filter,HandlerFile<Ex> h) throws Ex {
		 if( !fi.isDirectory() ) return;
//		System.out.println("fi="+fi);
//		File files [] = fi.listFiles(filter);
//		_Sysout.print("forFile=",files);
		for (File file : fi.listFiles(filter) ) {
//			System.out.println("file="+file);
			//如果是文件夹,就递归处理
			if(file.isDirectory())	forFile(file,filter,h);
			//处理文件.
			h.handler(file);//异常交给自己处理.    
		}
	}
	
	/**参考文献: FilenameFilter 和  FileFilter的区别
public File [] listFiles(java.io.FilenameFilter) 
java.io.FilenameFilter：文件名过滤器接口。过滤器必须实现此接口。该接口定义了一个
这个是下面的                                 file.getPath		file.getName();
public boolean accept(File file, String filename)方法，第一个参数File file为正在被过滤的文件，第二个参数为正在被过滤的文件名。FilenameFilter.accept返回false的文件会被过滤掉。
该方法返回匹配FilenameFilter所指定条件的文件
public File [] listFiles(java.io.FileFilter)
public boolean accept(File file)方法，第一个参数File file为正在被过滤的文件。FileFilter.accept返回false的文件会被过滤掉。
该方法返回匹配FileFilter所指定条件的文件。
正在被过滤的文件是指自己本身..
@see Filter
	 */@Deprecated//建议用Filter
	public static interface FileFilter extends FilenameFilter{
		FileFilter JAVA = new FileFilter() {
			@Override
			public boolean accept(File dir, String name) {
				return name.endsWith(".java") || name.endsWith(".class");
			}
		};
		FileFilter NO_FILTER = new FileFilter() {
			@Override
			public boolean accept(File dir, String name) {
				return true;
			}
		};
		/**不递归,不是单例的*/@Deprecated
		public static class NoDiGui implements FileFilter{
			boolean flag = true;
			
			public boolean accept(File dir, String name) {
				System.out.println("Dir="+dir); //是file.listFiles() 的file;
				System.out.println(name);//正在被过滤的file.getName();
				return dir.isFile();
				
//				if( flag ) { // 为了防止第一次是文件夹.
//					this.flag = false;
//					return true;
//				}else {  
////					System.out.println("dir="+dir);
////					System.out.println( !dir.isDirectory() );
//					return  !dir.isDirectory();
//				}
			}
		}
	}
	 public static <Ex extends Throwable> void forFile(File fi,Filter filter,HandlerFile<Ex> h) throws Ex {
//			System.out.println("fi="+fi);
//			File files [] = fi.listFiles(filter);
//			_Sysout.print("forFile=",files);
			for (File file : fi.listFiles(filter) ) {
//				System.out.println("file="+file);
				//如果是文件夹,就递归处理
				if(file.isDirectory())	forFile(file,filter,h);
				//处理文件.
				h.handler(file);//异常交给自己处理.    
			}
		}
	
	/**遍历了所有的文件,除了自己.
	 * @param fi
	 * @param h
	 * @throws Ex 
	 * @throws Throwable 
	 */@Deprecated
	 public static <Ex extends Throwable> void forFile(File fi,HandlerFile<Ex> h) throws Ex { forFile(fi, FileFilter.NO_FILTER, h);}
	 public static interface Filter extends java.io.FileFilter{
		 /**不递归*/Filter NODIGUI = new Filter() {
			//这个就是上面的 pathname.
			public boolean accept(File pathname) {
//				pathname.getParentFile();//accept.file
//				pathname.getName();// accept.name
				return pathname.isFile();
			}
		 };
		 /**获得文件*/Filter FILE = NODIGUI;
		 /**获得文件夹*/ Filter DIRECTORY = new Filter() {
				//这个就是上面的 pathname.
				public boolean accept(File pathname) {
					return pathname.isDirectory();
				}
			 };
	}
	/**
	 * @author wlh
	 * 用枚举来代替接口回调..
	 * 优点:单例,统一入口(用户不可以操作了,匿名内部类.--安全),可序列化
	 * 缺点:扩展性不好,且代码过于集中.
//	public static enum Handler{
//		DELETE {
//			public void handler(File file) {
//				file.delete();
//			}
//		};  
//		public abstract void handler(File file);
//	}
	 * 这里可以用接口的public static final .
	 * 优点:单例,扩展性好.相比enum效率高和内存低.
	 * 缺点:无法统一入口,不安全,不可序列化
	 */

	public static interface HandlerFile<Ex extends Throwable>{
		/**删除文件*/
		HandlerFile<FileNotFoundException> DELETE = new HandlerFile<FileNotFoundException>(){
			@Override
			public void handler(File file) {file.delete();}
		};
		/** 如何操作文件或文件夹  */
		void handler(File file) throws Ex;
	}
	/** 删除文件或文件夹(本身) */
	public static boolean delete(File fi){
		if(  fi.isDirectory() ){  
			try {
				forFile(fi,HandlerFile.DELETE);     
			} catch (IOException e) {
				return false;
			}
		}//再把自己删除..
		return fi.delete();            
	}
	 /**
     * 删除文件，可以是文件或文件夹
     * @param fileName
     *            要删除的文件名
     * @return 删除成功返回true，否则返回false
     */
    public static boolean delete(String fileName) {return delete(new File(fileName));}
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////   
    /*
     * java复制文件的4种方式
     * 
     * @author Administrator 尽管Java提供了一个可以处理文件的IO操作类。 但是没有一个复制文件的方法。
     *         复制文件是一个重要的操作,当你的程序必须处理很多文件相关的时候。
     *         然而有几种方法可以进行Java文件复制操作,下面列举出4中最受欢迎的方式。*/
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * 使用FileStreams复制
	 * 
	 * 这是最经典的方式将一个文件的内容复制到另一个文件中。
	 * 使用FileInputStream读取文件A的字节，使用FileOutputStream写入到文件B。 这是第一个方法的代码:
	 * 
	 * @param source
	 * @param dest
	 * @throws IOException
	 */
	public static void copyFileUsingFileStreams(File source, File dest)
			throws IOException {
		InputStream input = null;
		OutputStream output = null;
		try {
			input = new FileInputStream(source);
			output = new FileOutputStream(dest);
//			Writer wr = null;
//			Reader r = null;
//			PrintWriter pw  = null;
			byte[] buf = new byte[1024];
			int bytesRead;
			while ((bytesRead = input.read(buf)) > 0) {
				output.write(buf, 0, bytesRead);
			}
		} finally {
			if(input!=null)input.close();
			if(output!=null)output.close();
		}
	}
	
	/**使用FileChannel复制
Java NIO包括transferFrom方法,根据文档应该比文件流复制的速度更快。 这是第二种方法的代码
	 * @param source
	 * @param dest
	 * @throws IOException
	 */
	public static void copyFileUsingFileChannels(File source, File dest) throws IOException {    
        FileChannel inputChannel = null;    
        FileChannel outputChannel = null;    
    try {
        inputChannel = new FileInputStream(source).getChannel();
        outputChannel = new FileOutputStream(dest).getChannel();
        outputChannel.transferFrom(inputChannel, 0, inputChannel.size());
    } finally {
        inputChannel.close();
        outputChannel.close();
    }
}
	 /**使用Commons IO复制
	 Apache Commons IO提供拷贝文件方法在其FileUtils类,可用于复制一个文件到另一个地方。它非常方便使用Apache Commons FileUtils类时,您已经使用您的项目。基本上,这个类使用Java NIO FileChannel内部。 这是第三种方法的代码:
	 * @param source
	 * @param dest
	 * @throws IOException
	 */
	public static void copyFileUsingApacheCommonsIO(File source, File dest)
	         throws IOException {
//	     FileUtils.copyFile(source, dest);
	 }

	/**	使用Java7的Files类复制
	如果你有一些经验在Java 7中你可能会知道,可以使用复制方法的Files类文件,从一个文件复制到另一个文件。 这是第四个方法的代码:
	 * @param source
	 * @param dest
	 * @throws IOException
	 */
	public static void copyFileUsingJava7Files(File source, File dest)
	        throws IOException {    
//	      Files.copy(source.toPath(), dest.toPath());
	}
	
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/*
	 * 为文件排序.-如时间,大小,名字,类型,或者这些组合.
	 * file.compareTo(pathname);--只能对路径排序.. 		OrderByFile
	 */
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	  /**1：按 文件名称 排序
	   * 
	 * @param filePath  
	 */
	public static void orderByName(String filePath) {
	        File file = new File(filePath);
	        File[] files = file.listFiles();
	        List fileList = Arrays.asList(files);
	        Collections.sort(fileList, new Comparator<File>() {
	            @Override
	            public int compare(File o1, File o2) {
	                if (o1.isDirectory() && o2.isFile())
	                    return -1;
	                if (o1.isFile() && o2.isDirectory())
	                    return 1;
	                return o1.getName().compareTo(o2.getName());
	            }
	        });
	        for (File file1 : files) {
//	            System.out.println(file1.getName());

	        }
	 }
	
	/**2：按 文件日期 排序
	 * 按 文件修改日期：递增 
	 * @param filePath
	 */
	public static void orderByDate(String filePath) {
        File file = new File(filePath);
        File[] files = file.listFiles();
        Arrays.sort(files, new Comparator<File>() {
            public int compare(File f1, File f2) {
                long diff = f1.lastModified() - f2.lastModified();
                if (diff > 0)
                    return 1;
                else if (diff == 0)
                    return 0;
                else
                    return -1;//如果 if 中修改为 返回-1 同时此处修改为返回 1  排序就会是递减
            }

            public boolean equals(Object obj) {
                return true;
            }

        });
//        for (int i = 0; i < files.length; i++) {
//            System.out.println(files[i].getName());
//            System.out.println(new Date(files[i].lastModified()));
//        }
    }
	   /** 3 按 文件创建日期 排序    
     * @param _file _file
     * @return datetime datetime
     * 测试发现这个方法好用，在 windows环境下
     */
	public static String getFileCreateDate(File _file) {

        File file = _file;

        try {

            Process ls_proc = Runtime.getRuntime().exec("cmd.exe /c dir " + file.getAbsolutePath() + " /tc");

            BufferedReader br = new BufferedReader(new InputStreamReader(ls_proc.getInputStream()));

            for (int i = 0; i < 5; i++) {

                br.readLine();

            }


            String stuff = br.readLine();

            if (stuff == null) {
                return "";
            }

            StringTokenizer st = new StringTokenizer(stuff);

            String dateC = st.nextToken();

            String time = st.nextToken();

            String datetime = dateC.concat(time);

            br.close();

            return datetime;

        } catch (Exception e) {

            return null;

        }

    }
	/**4：按 文件大小 排序
	 * @param filePath
	 */
	public static void orderByLength(String filePath) {
        File file = new File(filePath);
        File[] files = file.listFiles();
        List<File> fileList = Arrays.asList(files);
        Collections.sort(fileList, new Comparator<File>() {
            public int compare(File f1, File f2) {
                long diff = f1.length() - f2.length();
                if (diff > 0)
                    return 1;
                else if (diff == 0)
                    return 0;
                else
                    return -1;//如果 if 中修改为 返回-1 同时此处修改为返回 1  排序就会是递减
            }

            public boolean equals(Object obj) {
                return true;
            }
        });
//        for (File file1 : files) {
//            if (file1.isDirectory()) continue;
//            System.out.println(file1.getName() + ":" + file1.length());
//        }
    }
}
