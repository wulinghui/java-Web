package cn.wlh.util.extend;

/**
 * @author wlh
 * 由于map扩容的特性:扩一倍,假如我们只有一个51个但是被扩容到100个这是浪费的..
 * 所以这里采用的是让粒度尽量小,这样扩容的也就小..
 * 分配多级Map..但是总体来说不一定会节约内存..
 */
public class LinkMap<K,V> {
	K key;
	V value;
	LinkMap<K,V> next;
	//只有get,没有set.
	public K getKey() {
		return key;
	}
	public V getValue() {
		return value;
	}
	public LinkMap<K, V> getNext() {
		return next;
	}
	public LinkMap() {
		super();
	}
	public LinkMap(K key, V value) {
		super();
		this.key = key;
		this.value = value;
	}
	public V find(K key) {
		if( key ==null ) return null;
		LinkMap<K,V> l = this;
		do {
			//判断
			if( key.equals( l.key ) )
				return l.value;
			//下次循环
			l = l.next;
		}while( l !=null );
		return null;//没有找到..
	}
	
	public void put ( K key ,V value) {
		if( key ==null ) return;
		LinkMap<K,V> l = this;
		do {
			//判断
			if( 	key.equals ( l.key )	 )
				l.value = value;//修改.
//				return;//不放入..
			//下次循环
			l = l.next;
		}while( l !=null );
		// 这时  l==null
		l = new LinkMap<K,V>(key,value);//增加...
	}
	public void remove(K key) {
		if( key ==null ) return;
		LinkMap<K,V> forward = null;//保存前一个
		LinkMap<K,V> l = this;
		do {
			//判断
			if( 	key.equals ( l.key )	 ) {//找到了
				if( forward != null ) {//把前边接上就行了
					forward.next = l.next;
				}
			}else {//前移
				forward = l;
				l = l.next;
			}
			//下次循环
			l = l.next;
		}while( l !=null );
		// 这时  l==null
		l = new LinkMap<K,V>(key,value);
	}
}
