package cn.wlh.util.extend.wrap;

import java.util.Map;

import cn.wlh.util.base.JavaUtilFactory;
import cn.wlh.util.base._Map;
import cn.wlh.util.extend.wrap.WrapObject.WrapSource;

/** Value Of Map
 * @author wlh
 * Map 里面 有   Map   
 * @param <K> subMap
 * @param <K1> k
 * @param <V>  v
 * @see cn.wlh.util.extend.wrap.K1_k2_V_Map<K1, K2, V>
 */@Deprecated 
public abstract  class V_Map<K,K1,V> extends WrapSource< Map<K, Map<K1,V> > >{
	
	protected Map<K1,V> cache ;    
	
	protected abstract Map<K1,V>  newSubMap (); 
	
	public V_Map<K,K1,V> put(K k,K1 k1,V v){
		getOrNewSub(k);
		this.cache.put(k1, v);
		return this;
	}
	public V_Map<K,K1,V> put(K k,K1[] k1,V[] v){
		_Map.array2Map( getOrNewSub(k) , k1, v) ;
		return this;
	}
	public V_Map<K,K1,V> put(K k,Object[] kAndv){
		_Map.array2Map( getOrNewSub(k) ,  kAndv , 2) ;
		return this;
	}
	public V_Map<K,K1,V> put(K1 k1,V v){
		this.cache.put(k1, v);
		return this;
	}
	/**从map里面获得SubMap
	 * @param k 
	 * @return Map<K1,V> 
	 */
	public Map<K1,V> getOrNewSub(K k){
		Map<K1,V> mapSub = getMap(k);
		if(mapSub == null) mapSub = newSubMap();
		return mapSub;
	}
	
	public Map<K1,V> getMap(K k){ 
		this.cache = getSource().get(k);
		return this.cache; 
	}
	
	public  V get(K1 k1){ return (V) this.cache.get(k1); }
	  
	public  V get(K k,K1 k1){
		getMap(k);
		return get(k1);
	}
}
