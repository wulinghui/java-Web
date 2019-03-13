package cn.wlh.util.extend;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;


/**
 * @author Administrator
 * 该类主要是代替打印日志,
 */
public abstract class Console {
	Console(String flag,String f){
		this.flag = flag;
		this.f = f;
	}
	static Set<String> store = new HashSet<String>();
	final String flag;//这个日志的标识
	final String f;//输出格式
	static  String regex = "\\{}";
	static{
		init("err");
	}
	public static void over(String ... i){
		store.clear();
		for (String e : i) {
			store.add(e);
		}
	}
	public static void init(String... store){
//		for (Object object : store) {
//			String flag = null;
//			
//			Console.store.add(  );
//		}
		Console.store.addAll( Arrays.asList(store) );// store--String的时候
	}
	public static Console getInstance(String flag,String f){
		return new Console(flag,f){};
	}
	public static Console[] getInstances(int... flag){
		int len = flag.length;
		Console cons []= new Console[len];
		String [] s = new String[len];
		for (int i = 0; i < len; i++) {
			String z =String.valueOf(flag[i]);
			cons[i] = new Console(z,flag[i]+"::{f}\n") {};
			store.add(z);//登记
		}
		return cons;
	}
	public static Map<String,Console> getInstances(String... flag){
		int len = flag.length;
		Console cons []= new Console[len];
		Map<String,Console> map = new LinkedHashMap<String, Console>();
		for (int i = 0; i < len; i++) {
			map.put(flag[i], new Console(flag[i],flag[i]+"::{f}\n") {});
			store.add(flag[i]);//登记
		}
		return map;
	}
	
	public static Console getInstance(String flag){
		return new Console(flag,"{f}\n"){};
	}
	public static Console getInstance(Class<?> flag){return getInstance(flag.getSimpleName());}
	public  Console log(String a,Object... replacement ){
		if(store.contains(this.flag)){
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
				a = a.replaceFirst( regex , replacement[i].toString());
//				System.out.println("b==="+a);
				//添加,直接追加问题:
				if(b!=null && b.equals(a)) {
						a += replacement[i].toString();
				}
			}
			System.out.print( handle(a) );
		}
		return this;
	}
	public Console log(Object a){ return log("","",a);	}
	public  String handle(String a){
		return this.f.replace("{f}", a);
	}
}
