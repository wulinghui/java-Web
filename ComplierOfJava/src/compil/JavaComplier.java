package compil;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import util.file.FileUtil;
import util.file.FileUtil.Handler;
import util.io.reader._Reader;
import util.io.writer._Writer;
import util.string.buffer._StringBuffer;

/**先用最简单的方法来实现,一层架构
 * @author wlh
 *
 */
public class JavaComplier  implements Handler{
	public final String handlePackage;//处理的包名
	public JavaComplier(String handlePackage) {
		this.handlePackage = handlePackage.toLowerCase();//转小写
	}
	public static void main(String[] args) {
		FileUtil.forFile(new File(""), new FilenameFilter() {
			public boolean accept(File dir, String name) {
				return name.endsWith(".java");//如果是java文件就执行,否则
			}
		}, new JavaComplier("coM"));
	}
	public void handler(File file) {
		try {
			//1.先把文件全部读入jvm中--尽量不要操作sb.
			StringBuffer sb = new StringBuffer().append(
					_Reader.notOutOfMemory(file) );//转成sb
			String absolutePath = file.getAbsolutePath();//绝对路径名
			//2.获得Class对象--为了后面的分析
			Class<?> cl = getClassByFileName(absolutePath);
			//开始拆分,先拆分dao层
			splitToDao( copy(sb)  , cl, absolutePath);
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
/////////////////////////////////////////////////////////////////////////////////////////////////////////
//							D	
//							a
//							o
//							层
// TODO dao层
/////////////////////////////////////////////////////////////////////////////////////////////////////////
	public static final String  DAO= "dao";
	public static final String DAO_FLAG [] = new String[] { "TODO S-",";",";" };
	//分割成dao层,可以重写....
	public void splitToDao(StringBuffer sb,Class<?> cl,String absolutePath) throws IOException, NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		//有序的循环sql语句: 例子 		ToDO S-ORACLE:   查询所有的U-FINDUSER  =>   SELECT  FROM USERZ;LIST LI = NEW ARRAYLIST()
		StringBuffer sbInit = initFileSB(cl, DAO );
		//把sbInit放入this.sb里面
		OprJavaSb ojs = new OprJavaSb( sbInit );
		/**TODO	这里的初始化对每个框架不同.也不知道在哪个地方重构..就先这样把
		 * import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
		 */
//		ojs.impPackage( "cn.gwssi.common.component.exception.TxnException"); --用forInvoke方法
		ojs.forIvoke("impPackage", new String[] { 
				 "cn.gwssi.common.component.exception.TxnException"
				,"cn.gwssi.common.dao.BaseTable"
		});
		//获得所有的sql语句,并且循环处理生成Dao类.
		for(String str : getSBInner(sb, DAO_FLAG ) ) {//这里的sql语句必须加;号   ->这样就能知道,biz的接收方式是什么.如果复杂的化建议写一个占位方法.或者sql
			//str-区分大小写
			String [] typeSql = str.replaceAll("\\*", "").split(":");//分割成数据库类型和sql语句
			String dataType = typeSql[0].split("-")[1].trim();//获得数据库类型--ORACLE
			String sqlInner = typeSql[1].trim();//sql语句内容
			//把sql语句里面的内容通过数据字典进行转化.	--并全部转成大写,不建议在这里转成大写.应该在生成sql的时候转
			sqlInner = toFullData( sqlInner , DaoComplier.dataDictionary)/*.toUpperCase()*/;
			//这里把SELECT  FROM USERZ;LIST LI = NEW ARRAYLIST()交个子类处理
			DaoComplier.mapDao.get(dataType).builder(sqlInner, sbInit);
		};
		sb.append('}');
		//写入文件中
		writeFile(sbInit, DAO ,absolutePath);
	}
/////////////////////////////////////////////////////////////////////////////////////////////////////////
//							B							
//							i						
//							z
//							层
	// TODO biz层
/////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public void splitToBiz(StringBuffer sb,Class<?> cl,String absolutePath) throws IOException{//分割成dao层
		//注入Dao
		DI_Class(sb,  cl.getName().replaceFirst("biz", DAO ) );
		//替换之前的占位Dao操作语句.
		
		//剔除所有注解
		String [] [] s = new String [] [] { DAO_FLAG  };
		for (String[] strings : s) {
			deleSbInner(sb, strings );
		}
		//把重新生成biz,写入文件中
		writeFile( sb , "_biz" ,absolutePath);
	}
/////////////////////////////////////////////////////////////////////////////////////////////////////////
//							V	
//							i
//							e
//							w
	// TODO view层
/////////////////////////////////////////////////////////////////////////////////////////////////////////
	public void splitToView(StringBuffer sb,Class<?> cl){//分割成dao层
		//注入Biz
		//生成方法,
		
	}
/////////////////////////////////////////////////////////////////////////////////////////////////////////
//	h	
//	t
//	m
//	l
// TODO html层
/////////////////////////////////////////////////////////////////////////////////////////////////////////
	public void splitToHtml(StringBuffer sb,Class<?> cl){
		
	}

	
	
	
	
	
	
/////////////////////////////////////////////////////////////////////////////////////////////////////////
//				TODO 公共方法供上面的调用	
/////////////////////////////////////////////////////////////////////////////////////////////////////////
	/** 注入 Class */
	public void DI_Class( StringBuffer sb,String inner ) {
		_StringBuffer.insert(sb, "{" , 0,  "" + inner);
	}
	/**写入到文件中*/
	public void writeFile(StringBuffer sb,String newFile,String path) throws IOException{
//		sb.append('}');//添加类的结束符
		path = path.replaceFirst( "biz", newFile);//获得新路径,这里之前的路径不能包含biz
		File fi = FileUtil.createFile( new File(path) );//创建文件
		//覆盖原文件,一次性写入
		_Writer.oneWrite( fi , false, sb);
	}
	/**添加包名,把包名的第一个biz换成dao*/
	public StringBuffer initFileSB(Class<?> c,String newPack){
		StringBuffer sb =new StringBuffer("package ");
		//添加包名.这里只替换第一个.
		sb.append( c.getPackage().getName().replaceFirst("biz" ,newPack )).append(';');
		//添加类名,
		sb.append("public class ");
		sb.append(c.getSimpleName());
		sb.append( '_' + newPack + '{');
		return sb;
	}
	/**获得类名*/
	public Class<?> getClassByFileName(String absolutePath) throws ClassNotFoundException{
		int in = absolutePath.indexOf(this.handlePackage);
		int dian = absolutePath.indexOf(".");//文件后缀名
		String re = "";
		if(dian != -1){
			re = absolutePath.substring(in, dian);
			//把路劲名,换成类名
			re = FileUtil.toPoint(re);
			return Class.forName(re);
		}
		return null;//路径-包名正确,同时书写满足java规范的化.肯定不会为null的.
	}/**通过指定的字符串--获得sb里面的内容.*/
	public Set<String> getSBInner(StringBuffer sb,String... h ){
		return handleAllAppoint(sb,0,new GetAppoint(),h);
	}
	/** 通过指定的字符串 -- 删除sb里面的内容
	 * @return */
	public Set<StringBuffer> deleSbInner(StringBuffer sb,String... h ){
		return handleAllAppoint(sb,0,new FilterAppoint(),h);
	}
	/**通过指定的数据字典转化成完全的数据,满足正则替换*/
	public String toFullData(String sqlInner,Map<String,String> map){
		for (String key : map.keySet()) {
			//下面满足正则替换
			sqlInner = sqlInner.replaceAll(key, map.get(key));
		}
		return sqlInner;
	}
	/**获得处理所有指定字符之间的内容
	 * @param sb
	 * @param offsert
	 * @param s
	 * @return
	 */
	public static <T> T handleAllAppoint(StringBuffer sb,int offsert,HandleAppoint<T> h,String ... s){
		T t = null;
		int [] staEn = new int[2];
		int len = s.length;
		int ls = 0;
		loop:do{
			for (int i = 0; i < len; i++) {
				ls = sb.indexOf( s[i] , offsert);
				//如果没有的话就退出了
				if( ls < offsert || ls == -1 ) break loop;
//				offsert = ls+1;//否则就作为下次的偏移量
				offsert = ls+s[i].length();
				//第一个
				if( i == 0 )  staEn[0] = ls;
				//最后一个
				if( i == len-1 ) staEn[1] = ls;
			}
			t = h.handle(sb, staEn[0] , staEn[1] );
		}while(true);
		return t;
	}
	public static interface HandleAppoint<T>{ T handle(StringBuffer sb,int start,int end);}
	
	/** 过滤内容,并存储
	 * @author wlh	Filter
	 */
	public static class FilterAppoint implements HandleAppoint<Set<StringBuffer>>{
		public final Set<StringBuffer>  set = new LinkedHashSet<StringBuffer>(); 
		public Set<StringBuffer> handle(StringBuffer sb, int start, int end) {
			set.add( sb.delete(start, end+1) );//该类删除并存储
			return set;
		}
	}
	/**获得内容*/
	public static class GetAppoint implements HandleAppoint<Set<String>>{
		public final Set<String>  set = new LinkedHashSet<String>();//有序
		@Override
		public Set<String> handle(StringBuffer sb, int start, int end) {
			String sub = sb.substring( start , end );//截取,但不删除
			//不建议select * 所以把他去掉.同时全部转为大写.满足数据库转化.
			//反对上面的做法.违反类单一职责.
			set.add( sub/*.replaceAll("\\*", "").replaceAll("\\/", "").toUpperCase()*/ );
			return set;
		}
	}
	
	/**截取子字符串的描述类.*/
	public static class SubAppoint{
		public List<Integer> start = new ArrayList<Integer>();
		public List<Integer> end = new ArrayList<Integer>();
		private int count = 1;
		public String inner;
		public static final Map<String,SubAppoint> map = new LinkedHashMap<String,SubAppoint>();
		private SubAppoint() {}
		public static SubAppoint getInstance(String inner,int start , int end) {
			SubAppoint sa = map.get(inner);
			if(sa == null) {
				sa = new SubAppoint(); 
//				sa.start = start;  
//				sa.end = end;   
				sa.inner = inner; 
			}else {//享元模式+标识
				sa.count ++ ;
			}
			sa.start.add( start );
			sa.end.add( end );
			return sa;
		}
		public int getCount() {
			return count;
		}
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((inner == null) ? 0 : inner.hashCode());
			return result;          
		}
		@Override            
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			SubAppoint other = (SubAppoint) obj;
			if (inner == null) {
				if (other.inner != null)
					return false;
			} else if (!inner.equals(other.inner))
				return false;
			return true;
		}
	}/**获得所有Sub并且用SubAppoint类处理*/
	public static class getAllSubAppoint  implements HandleAppoint<List<SubAppoint>>{
		public final List<SubAppoint> list = new ArrayList<SubAppoint>();
		public List<SubAppoint> handle(StringBuffer sb, int start, int end) { 
			//放入subA中. 
			String inner = sb.substring( start , end );
			SubAppoint subA = SubAppoint.getInstance(inner, start, end);
			//
			list.add(subA);
			return list;
		}
	}/** copy sb*/
	public StringBuffer copy(StringBuffer sb) { return new StringBuffer(sb);}
}
