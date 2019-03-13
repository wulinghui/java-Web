package cn.wlh.util.extend.modle.factory;


/**
 * @author wlh
 * 自定义的Map,linkedMap不会有扩容的情况..
 * 只多几个Object的方法区..
 * @param <K>
 * @param <V>
 */
public class Cache<K,V> {
	Link<K,V> first;//第一个
	Link<K,V> last;//最后一个
	public Cache(){
		first = new Link<K,V>();
		last = first;
	}
	
	/**该put只是放入最后,并没有加校验.不会对put去重.
	 * @param key
	 * @param value
	 */
	public void put ( K key ,V value) {
		Link<K,V> link  = new Link<K,V>();
		link.key = key;
		link.value = value;
		//last上登记
		last.next = link;//第一次就已经把 first给接上了
		//替换last
		last = link;
	}
	public V get(K key) {
		return first.find(key);
	}
	private class Link<K,V>{
		K key;
		V value;
		Link<K,V> next;
		public Link() {}
		public Link(K key, V value) {
			super();
			this.key = key;
			this.value = value;
		}
		public Link(Link<K,V> link) {
			this.next = link;
		}
		public V find(K key) {
			Link<K,V> l = this;
			do {
				if( key.equals(l.key) )
					return value;
				l = l.next;
			}while( l !=null );
			return null;//没有找到..
		}
	}
}
