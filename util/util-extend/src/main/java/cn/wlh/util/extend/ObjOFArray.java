package cn.wlh.util.extend;

import java.lang.reflect.Array;

import cn.wlh.util.extend.wrap.WrapObject.WrapSource;

public   class ObjOFArray extends WrapSource<Object>{
	public ObjOFArray(Object obj) {
		if( ! obj.getClass().isArray() ) throw new NullPointerException("obj  is not array");
		super.setSource(obj);
	}
	@Override
	protected Object newObject() {
		return null;
	}
	public int length() {
		return Array.getLength(super.getSource());
	}
	public <T> T get(int index) {
		return (T) Array.get(super.getSource(), index);
	}
	public ObjOFArray get(int index,Object value) {
		Array.set(super.getSource(), index, value);
		return this;
	}
	public Class<?> getClass(int index){
		if( index >= length() ) throw new IndexOutOfBoundsException();
		return get(index).getClass();
	}
	
	public <P,R> Object[] forArray( ForArr<P,R> fa ) {
		int l = this.length();
		Object [] objs = new Object[l];
		fa.objArray = super.getSource();
		for (int i = 0; i < l ; i++) {
			objs[i] = fa.handle( (P) get(i)  ); 
		}
		return objs;
	}
	
	public <T> T[] obj2Array(Class<T> c) { return (T[]) super.getSource() ;}
	
	public abstract class ForArr<P,R>{
		protected Object objArray;
		public abstract R handle( P p );
	}
//	public interface ForArr<P,R>{
//		R handle( P p , P[] ps);
//	}//不能用接口,接口不能用getSource() 方法.. 内部类
	public static void main(String[] args) {
		ObjOFArray a = new ObjOFArray(null);
		ForArr<String,String> bb = a.new ForArr<String,String>(){

			@Override
			public String handle(String p) {
				Object ob = super.objArray;
				
//				a.getSource();//可以用
				return null;
			}
			
		};
	}
}