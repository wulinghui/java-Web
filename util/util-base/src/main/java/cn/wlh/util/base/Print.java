package cn.wlh.util.base;

import java.lang.reflect.Array;



public abstract class Print {
//	static Print m = new Print() {
//		@Override
//		protected void print(String a) {       
//			System.out.print( a );  
//	}};
	static  String regex = "[{}]";
	static int flag = 0 ; 
	
	Class<?> c; // 这里是一个内部的日志,sysout用不到.
	
	
	public static  Print  m = new Print() {
		protected void print(int greade, String a) {
			System.out.println( a );
		}
	};
	public static  Print newInstance(Class<?> c) {
		return m; // 
	}
	
	public  Print log(String a , Object... replacement ){
		return log(-1,a, replacement);
	}
	public  Print log(int greade , String a , Object... replacement ){
			//解决数组打印为地址问题
			for (int i = 0; i < replacement.length; i++) {
				if( replacement[i].getClass().isArray() ){
					int length = Array.getLength( replacement[i] );//反射
				    StringBuffer sb = new StringBuffer();
				    sb.append('[');
				    for (int j = 0; j < length; j++) {
				    	sb.append('{');
				        sb.append( Array.get(replacement[i], j)/*对象*/);
				        sb.append("},");
				    }
				    sb.deleteCharAt(sb.length()-1);//
				    sb.append(']');
				 // 用 sb 代替 内容.
				    replacement[i] = sb;
				}
			}
			String b = null;
			//解决log
			int len = replacement.length;
			for (int i = 0; i < len; i++) {
				//不使用foreach
				b = a;
//				System.out.println("a==="+a);
				a = a.replaceFirst( regex , replacement[i].toString() );
//				System.out.println("b==="+a);
				//添加,直接追加问题:
				if(b!=null && b.equals(a)) {
						a += replacement[i].toString();
				}
			}
			//TODO 自定义定义的输出格式.
			print(greade , a);
		return (Print) this;
	}
	
	protected abstract void print( int greade , String a);
}
