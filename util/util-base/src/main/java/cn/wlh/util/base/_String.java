package cn.wlh.util.base;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public  abstract class _String{
	/** 把str转成 "str"  
	 * 这里为了防止 \n 符号导致 失效
	 *  */				
	public static String toStringFormatOfTwo(String str) {return "\"" + str.replaceAll("\\s", " ") + "\"";}
	/**
	 * @param str
	 * @return true- = null || =""
	 */
	public static boolean isEmpty(String str){    
		return null==str || str.isEmpty(); 
	}
	public static String array2String(String... s ) {
		String a = "";
		for (String string : s) {
			a += string;
		}
		return a;
	}
	/**通过指定的数据字典转化成完全的数据,满足正则替换*/
	public static String toFullDataByDictionary(String sqlInner,Map<String,String> map){
		for (String key : map.keySet()) {
			//下面满足正则替换
			sqlInner = sqlInner.replaceAll(key, map.get(key));
		}
		return sqlInner;
	}
	/** 修饰字符串本身的数组
	 * @param simple false-强力修饰*/
	public static String[] trimArrayOfMee(boolean simple,String[] trim) {
		int len = trim.length;
		if( simple ) {//简单
			for (int i = 0; i < len; i++) {
				trim[i] = trim[i].trim();//直接trim本身.
			}
		}else {//用了方法去空格..
			for (int i = 0; i < len; i++) {
				trim[i] = trim(trim[i]);//直接trim本身.
			}
		}
		
		return trim;//为了其他地方的使用,也可以不用放回.
	}
	/** 把类对象数组2字符串数组
	 * @param isSimple true-简单的类名 
	 * */
	public static String[] classs2Strings(boolean isSimple,Class<?>... clas) {
		int len = clas.length;
		String [] res  = new String[len];
		if( isSimple ) {//简单的类名 
			for (int i = 0; i < res.length; i++) {
				res[i] = clas[i].getSimpleName();
			}	
		}else {//完全限定名
			for (int i = 0; i < res.length; i++) {
				res[i] = clas[i].getName();
			}
		}
		return res;
	}
	/** 这个方法只替换第一个
	 * @param target 目标str
	 * @param startSymbol 开头的符号
	 * @param endSymbol 结尾的符号
	 * @param replacement 
	 * @return [0]:start-end中间的符号;[1]:替换之后存储的地方 
	 */
	public static String[] getAndReplaceFirst_startAndEnd(String target,String startSymbol,String endSymbol,String replacement){
		int a = target.indexOf(startSymbol);//a+1
		int b = target.indexOf(endSymbol);
		String [] strs = new String[2];
		if(a+b>0) 	
			strs[0] = target.substring(a+1, b);
		strs[1] = target.replaceFirst("["+startSymbol+"].+?["+endSymbol+"]",replacement);
		return  strs;
	}
	/**替换所有满足要求的
	 * @param target
	 * @param startSymbol
	 * @param endSymbol
	 * @param replacement
	 * @param list -- 每个替换之后的存放地方
	 * @return --最终替换的str
	 */
	public static String getAndReplaceAll_startAndEnd
		(String target,String startSymbol,String endSymbol,
				String replacement,List<String> list) {
		if(list == null) list = new ArrayList<String>();
//可能他不想要这里面的list呢?	throw new TsException("null","list = null","根据:不同方法里能操作,但是不能串改引用。这里new一个新的也没用");
		boolean flag = true;
		String store = null;
		while(flag){
			int a = target.indexOf(startSymbol);//a+1
			int b = target.indexOf(endSymbol);
			if(a+b>0){
				store = target.substring(a+1, b);
				list.add(store);
				target = target.replaceFirst("["+startSymbol+"].+?["+endSymbol+"]",replacement);
			}else{
				flag = false;
			}
		}	
		return  target;
	}
	/**不要使用,没写好 TODO  分割最后一个,字符.  如:.	在util.extend.complier.java.Dao 中的 (获得包名和类名并生成java对象)处用到..
	 * */
	public String[] getPackageAndClass(File file) {
		String fullClass = _File.absolutePath2ClassName( file.getAbsolutePath() );
		return fullClass.split("");
	}
	/**去除首尾任何空白字符，包括空格、制表符、换页符等。与 [\f\n\r\t\v] 等效。
	 * @param source
	 * @return
	 */
	public static String trim(String source) {
		return source.replaceAll("^\\s+?|\\s+?$", "");
	}
	/**  插入变量
	 * @param inner
	 * @return "+inner+"
	 */
	public static String insertVar(String inner) {
		return "\"+" + inner + "+\"";
	}
	/** 按tag拼接数组
	 * @param tag
	 * @param inner
	 * @return inner[0] + tag + inner[1]
	 */
	public static String join(String tag , Object ...inner ) {
		String str = String.valueOf( inner[0] );
		for (int i = 1; i < parerLength( inner ); i++) {
			str += String.valueOf(tag) + String.valueOf(inner[i]); 
		}
		return str;
	}
	public static String join(String tag ,  Collection<String> keys ) {
			Iterator<String> iterator = keys.iterator();
			String str =  String.valueOf( iterator.next() ); 
			while( iterator.hasNext() ) {
				str += String.valueOf(tag) + String.valueOf( iterator.next() ); 
			}
		return str;
	}
	/**华丽的分隔符
	 * @param split
	 * @param inner
	 * @return split + inner + split
	 */
	public static String lineBetween(String split , String inner) {
		return split + inner + split;
	}
	/** 防止为null 
	 * @param inner
	 * @return  -1 为null
	 */
	public static int parerLength( Object [] inner ) {
		if(inner==null ) return -1;
		return inner.length;
	}
////////////////////////////sub  包括自己
	/**sub  OfMe-包括自己*/
	public static String subStringOfMe(String str,int begin,String end){return str.substring( begin , str.indexOf(end) + 1 ); }
	/**sub OfMe-包括自己
	 * @param fromIndex 偏移量
	 * */
	public static String subStringOfMe(String str,int fromIndex,String begin,String end) {
		int beginIndex = str.indexOf(begin,fromIndex);
		int endIndex = str.indexOf(end, beginIndex + 1 );//防止相同的字符串..
		endIndex = endIndex == -1 ?  str.length() : endIndex;//防止没找到-1时报错..
		return str.substring(beginIndex, endIndex + 1);
	}
    ////////////////////////////        sub  不包括自己
	public static String subString(String str,int begin,String end){return str.substring( begin + 1 , str.indexOf(end)  ); }
	
	public static String subString(String str,int fromIndex,String begin,String end) {
		int beginIndex = indexPrefix( str , begin,fromIndex) + begin.length() ;
		int endIndex =   indexSuffix( str , end , beginIndex  );//不用加1了,已经偏移了
		return str.substring(beginIndex, endIndex  );
	}
	/** 获得前缀 , 如果没有找到就,为0*/
	public static int indexPrefix(String str , String inner , int fromIndex) {
		int i = str.indexOf(inner, fromIndex);
		return i == -1 ? 0 : i;
	}
	/** 获得后缀 , 如果没有找到就,为字符长度*/
	public static int indexSuffix(String str , String inner , int fromIndex) {
		int i = str.indexOf(inner, fromIndex);
		return i == -1 ? str.length() : i ;
	}
	/** 是否在数组中. */
	public static boolean lookUp(String [] strings , String tar) {
		for (String d : strings) {
			if( d == tar) return true; //在
		}
		return false;//不在
	}
}