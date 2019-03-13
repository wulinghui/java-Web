package cn.wlh.util.extend.complier.java;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Set;

import cn.wlh.util.base._File;
import cn.wlh.util.base._Map;
import cn.wlh.util.base._String;
import cn.wlh.util.base._StringBuffer;
import cn.wlh.util.extend.complier.ComplierClient.ComplierFile;
import cn.wlh.util.extend.dao.Word;

/**
 * @author wlh
 * 重新获得一个新的java文件.不会打扰之前的java文件.
 */
public  class NewJavaFile implements ComplierFile<JavaFile>{
	//第二版本...上面的特殊符号不统一,当需要的时候又需要转义字符.麻烦..   统一分隔符 - 
	/* TODO S - oracle  查询所有的u - - findUser -
	 * select * from userz
	 * 	* from userz	* from userz * from userz * from userz
	 * * from userz;
	 *///要处理的标识
	private final String []  flags ;//获得,下面操作的标识		"TODO S-",";",";"
	public NewJavaFile(String[] flags) {
		if( flags.length <2 ) throw new IndexOutOfBoundsException();
		this.flags = flags;
	}
	/*TODO 该处用.sb操作一层一层放入,太浪费                还是sb一个贯穿所有,不安全
	 * */
	/**
	 * @param file 原文件           --不能处理
	 * @param sb   可以操作	--上层处理下来的,建议最后在操作拆分原文件的sb.
	 * @return 
	 */
	File file;
	public final JavaFile handle(File file,StringBuffer sb) {
		//赋值
		this.file = file; 
		//获得一个文件的内容...一个文件一个对象e
		//获得文件里面所有需要指定操作的内容..
		Set<String> ss = getSBInner(sb, this.flags );
		for(String str :  ss) {
			System.out.println( "getSBInner=" +  str);
			try {
				handlerJava(str);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}
	public void handlerJava(String str  ) throws IOException {
		//替换 * 号
		str = str.replaceAll("\\*", "");
//				TODO S - 	   oracle   查询所有的u -  -findUser - select * from biz;
//		分割成	指定的指定标识字符、数据库类型、方法注解 、    注解 、方法名、 	方法体的语句  
		//小心有indexException
		String [] splits = _String.trimArrayOfMee( false,str.split("-") );//强力修饰
		//登记.
		array2InnerField(splits);
		handleInner(   );
	}
	//其他的可以复写...
	protected String method_describe = "";//注解
	protected String method_annotation= "";//
	protected String method_master= "";//主人.主内容
	protected String method_innner= "";//内容.
	public  void handleInner(  ) throws IOException {
		
		//把sql语句里面的内容通过数据字典进行转化.	--应该在生成sql的时候转大写.    
		String inner = _String.toFullDataByDictionary( this.method_innner , this.initDataDictionary() )/*.toUpperCase()*/;
		System.out.println( "splits[4]="+ this.method_innner );
		System.out.println( "String inner=" + inner );
		//在数据字典操作之后才new
		JavaFile java = toJavaFile();
		java.addImport(Word.class); 
		//由子类处理并添加方法..
		java.addMethod( java.new Method( this.method_describe , "" ," String " + this.method_master +"(Word word)",
				//通过配置文件交给子类.获得具体的操作inner的方法,不在这里用,为了子类可以复写本方法..
				//Confi.config.complierSplitJavaFiles().get( splits[0] +":"+splits[1] ).
				this.handleInner( inner  )) );
		String s = "C:\\Users\\wlh\\eclipse-workspace\\util\\util-extend\\src\\main\\java\\\\util\\extend\\complier\\java\\a\\\\Test0.java";
		System.out.println( s +"==="+ new File(s).exists() );
		System.out.println( "java.addMethod"  + java );
		//每次处理完就序列化,保存 
		java.storeBean();
	}
	
	//这是处理  
	public String handleInner(String inner ){
		return "return " + _String.toStringFormatOfTwo(inner) + ";";
	}
	public  Map<String,String> initDataDictionary(){
		 String [] dictionary = new String[]{
				 "#S","SELECT"//查询
				,"#D","DELETE"//删除
				,"#I","UPDATE"//更新
				,"##","*"//代替*号,
				
		};
		return _Map.array2Map(dictionary, 2);
	}
	/**通过指定的字符串--获得sb里面的内容.*/
	public Set<String> getSBInner(StringBuffer sb,String... h ){
		return _StringBuffer.handleAllAppoint(
				sb,0,new _StringBuffer.GetAppoint()/*new _StringBuffer.FilterAppoint()*/ ,h);
	}
	
	/** 转成属性.供下面使用.统一入口	 */
	protected void array2InnerField(String [] spilts) {
		 method_describe = spilts[3];//注解
		 method_annotation= "";//
		 method_master= spilts[5];//主人.主内容
		 method_innner= spilts[6];//内容.
	}
	protected JavaFile toJavaFile() {
		//获得包名和类名并生成java对象
//		String fullClass = _File.absolutePath2ClassName( file.getAbsolutePath() );
		String fullClass = _File.absolutePath2ClassNameOfSrc( file.getAbsolutePath() );
		int i = fullClass.lastIndexOf('.'); 
		//TODO 子类可以把里面的包名或类名不断变化..   该类是通过原文件的路径.生成java文件.,可能会替换源文件.
		//也就是说一个Biz就生成一个Dao...
		System.out.println( " fullClass=="+ fullClass );
		System.out.println( "fullClass.substring(0, i)=="+ fullClass.substring(0, i) );
		System.out.println( "fullClass.substring(i+1)=="+ fullClass.substring(i+1) );
		JavaFile java = JavaFile.getInstance( fullClass.substring(0, i) , fullClass.substring(i+1) +"00" );
		System.out.println( "JavaFile=" + java );
		return java;
	}
}
