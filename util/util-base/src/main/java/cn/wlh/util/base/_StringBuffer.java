package cn.wlh.util.base;

import java.util.LinkedHashSet;
import java.util.Set;

public  abstract class _StringBuffer {
	/** 在 查找到str的地方插入inner
	 * @return 插入之后的位置*/  
	public static int insert(StringBuffer sb,String str,int offsert,Object inner){
		int i = sb.indexOf(str,offsert)+1;
		String ss = String.valueOf( inner );
		sb.insert( i , ss );
		return i+ss.length();
	}
	/**  在 查找到str的findex次的地方插入inner */      
	public static int insert(StringBuffer sb,String str,int oneOffsert ,int findex,Object inner){
		int i = getInsertOffsert(sb, str, oneOffsert, findex);
		String ss = String.valueOf( inner );
		sb.insert( i , ss );
		return i+ss.length();
	}
	/** 获得插入的偏移量
	 * @param sb
	 * @param inner
	 * @param oneOffsert --首次偏移量
	 * @param findex	-- 对index 第几次偏移   0-第一次
	 * @return
	 */
	public static int getInsertOffsert( StringBuffer sb,String inner,int oneOffsert ,int findex) {
		int i = 0;
		int ret = 0;
		do {
			ret = sb.indexOf(inner,oneOffsert)+1;
		}while( i<findex );
		return ret;
	}
	/**获得处理所有指定字符之间的内容
	 * @param sb
	 * @param offsert
	 * @param s
	 * @return
	 */
	public static <T> T handleAllAppoint(StringBuffer sb,int offsert,HandleAppoint<T> h,String ... s){
		T t = h.initT();
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
			}/**如果没到这里就NullExc了*/
			t = h.handle(sb, staEn[0] , staEn[1] );
		}while(true);
		return t;
	}
	public static interface HandleAppoint<T>{ T handle(StringBuffer sb,int start,int end); T initT();/*未防止NullExc*/}
	/** 过滤内容,并存储
	 * @author wlh	Filter
	 */
	public static class FilterAppoint implements HandleAppoint<Set<String>>{
		public final Set<String>  set = new LinkedHashSet<String>(); 
		public Set<String> handle(StringBuffer sb, int start, int end) {
			set.add( sb.delete(start, end+1).toString() );//该类删除并存储
			return set;          
		}
		public Set<String> initT() {
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
		public Set<String> initT() {
			return set;
		}
	}
}
