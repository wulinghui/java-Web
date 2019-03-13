package cn.wlh.util.extend.wrap;

/**
 * @author wlh
 * 组合模式的基类...
 * 优点:为控制访问权限 或  重写不同返回值的方法..
 * 缺点:如果复用父类太多的方法,有点冗余代码
 * @param <K> --包装对象.
 */
public abstract class WrapObject<K> {
	protected K k = newObject();
	
	/**为了 this.k = newObject();   默认的初始化.   */
	protected abstract K newObject();
	
	/**
	 * @author wlh
	 * 解决父类的缺点:如果复用父类太多的方法,有点冗余代码
	 * 把属性暴露给用户
	 */
	public static abstract class WrapSource<K> extends WrapObject<K> {
		/** return K k */
		public final K getSource(){return super.k;};
		
		protected final void setSource(K k) { this.k = k; }
	}
	
	
}
