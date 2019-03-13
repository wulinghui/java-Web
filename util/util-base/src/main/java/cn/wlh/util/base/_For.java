package cn.wlh.util.base;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

import cn.wlh.util.base.interfaces._Filter;

/**
 * @author wlh
 * 递归:
 * 1.什么时候结束
 * 2.排序
 * 3.过滤
 * 4.处理--回调
 * 5.内容的存储.--回调的结果
 * @param <T>
 * @param <H>
 */
public abstract class _For {
	/**遍历数组
	 * @param <T> 处理的类
	 * @param <R> 放回的类
	 * @param list 存储的
	 * @param ts   需要处理的数组
	 * @param f    过滤的    f = null 
	 * @param sort 排序的	sort=null --不排序
	 * @param h    处理者
	 * @return
	 */
	public static <T,R> List<R> forArray(List<R> list,T[] ts,_Filter<T> f,Comparator<T> sort,CallBack<T,R> h){
		//_For排序
		if(sort != null) Arrays.sort(ts, sort);
		//系统提供
		if(list == null ) list = new ArrayList<R>();
		//遍历
		for (T t : ts) {
			//过滤
			if( f==null || f.accept(t) ){
				//处理并存储
				R r = h.handle(t);//不存null
				if( r !=null ) list.add( r );  
			}
		}
		return list;
	}
	public static <T,R> List<R> forArray(List<R> list,Collection<T> ts,_Filter<T> f,Comparator<T> sort,CallBack<T,R> h){
		return forArray( list, (T[])ts.toArray()  , f ,sort , h );
	}
	public static <T,R> List<R> forArray(T[] ts , CallBack<T,R> h){
		return forArray(null,ts,null,null,h);
	}
	public static <T,R> List<R> forArray(Collection<T>  ts , CallBack<T,R> h){
		return forArray(null, (T[])ts.toArray() ,null,null,h);
	}
	/** 回调 */
	public static interface CallBack<T,R>{
		R handle(T t);
	}
	public static interface NoRutrun<T> extends CallBack<T,Void>{}
//	public static class A implements NoRutrun<Object>{
//
//		@Override
//		public Void handle(Object t) {
//			// TODO Auto-generated method stub
//			return null;
//		}
//		
//	}
}
