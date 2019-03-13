package cn.wlh.util.base.adapter.servlet1;

import java.io.Writer;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

import cn.wlh.util.base.adapter.java.util.JavaUtilFactory;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

/**
 * @author 吴灵辉
 * DataBus2 -- 改名为Record一条记录.
 * Key - java.lang.String
 * 这是聚合json的jar
 * 和实现Map -- 通过  json 或者 里面的属性实现.
 * 
 */
public class Record implements Map<String,Object>{
	JSONObject json;
	
//	Map<Object,Object> map = _Field.getValueByField(json, "properties");
	
	/**
	 * @param  i   @seee cn.wlh.util.base.adapter.java.util.JavaUtilFactory.newflag(int)
	 */
	public  Record(int i) {
		init(i);
		newFlag = i ;
	}
	protected void init( int i ) {
		json = new JSONObject();
	}
	/**记录new级别的标识*/
	final int newFlag;  
	/**
	 *  适用于在方法体里面使用查询和修改   
	 */
	public static final int DEFAULT_LEVEL = JavaUtilFactory.SELECT_OF_METHOD;
	public Record() {  
		this( DEFAULT_LEVEL );
	}
	/** 校验,必须有值. <br/>
	 * @see cn.wlh.util.base.adapter.dbutils.apache.DaoMethodReturnCache.Sql99.dynamicWhere(Append, CachePreparedMap, Map<String, Object>)
	 */
	public Record requireNonEmpty() {
		if( this.isEmpty())
			throw new NullPointerException();
		return this;
	}
	public JSONObject accumulate(String key, boolean value) {
		return json.accumulate(key, value);
	}

	public JSONObject accumulate(String key, double value) {
		return json.accumulate(key, value);
	}

	public JSONObject accumulate(String key, int value) {
		return json.accumulate(key, value);
	}

	public JSONObject accumulate(String key, long value) {
		return json.accumulate(key, value);
	}

	public JSONObject accumulate(String key, Object value, JsonConfig jsonConfig) {
		return json.accumulate(key, value, jsonConfig);
	}

	public JSONObject accumulate(String key, Object value) {
		return json.accumulate(key, value);
	}

	public void accumulateAll(Map arg0, JsonConfig arg1) {
		json.accumulateAll(arg0, arg1);
	}

	public void accumulateAll(Map map) {
		json.accumulateAll(map);
	}

	public void clear() {
		json.clear();
	}

	public int compareTo(Object arg0) {
		return json.compareTo(arg0);
	}

	public  Object computeIfAbsent(String key, Function mappingFunction) {
		return json.computeIfAbsent(key, mappingFunction);
	}

	public  Object computeIfPresent(String key, BiFunction remappingFunction) {
		return json.computeIfPresent(key, remappingFunction);
	}

	public  Object compute(String key, BiFunction remappingFunction) {
		return json.compute(key, remappingFunction);
	}

	public boolean containsKey(Object key) {
		return json.containsKey(key);
	}

	public boolean containsValue(Object arg0, JsonConfig arg1) {
		return json.containsValue(arg0, arg1);
	}

	public boolean containsValue(Object value) {
		return json.containsValue(value);
	}

	public JSONObject discard(String key) {
		return json.discard(key);
	}

	public JSONObject element(String key, boolean value) {
		return json.element(key, value);
	}

	public JSONObject element(String key, Collection value, JsonConfig jsonConfig) {
		return json.element(key, value, jsonConfig);
	}

	public JSONObject element(String key, Collection value) {
		return json.element(key, value);
	}

	public JSONObject element(String key, double value) {
		return json.element(key, value);
	}

	public JSONObject element(String key, int value) {
		return json.element(key, value);
	}

	public JSONObject element(String key, long value) {
		return json.element(key, value);
	}

	public JSONObject element(String key, Map value, JsonConfig jsonConfig) {
		return json.element(key, value, jsonConfig);
	}

	public JSONObject element(String key, Map value) {
		return json.element(key, value);
	}

	public JSONObject element(String key, Object value, JsonConfig jsonConfig) {
		return json.element(key, value, jsonConfig);
	}

	public JSONObject element(String key, Object value) {
		return json.element(key, value);
	}

	public JSONObject elementOpt(String key, Object value, JsonConfig jsonConfig) {
		return json.elementOpt(key, value, jsonConfig);
	}

	public JSONObject elementOpt(String key, Object value) {
		return json.elementOpt(key, value);
	}

	public Set entrySet() {
		return json.entrySet();
	}

	public boolean equals(Object arg0) {
		return json.equals(arg0);
	}

	public  void forEach(BiConsumer action) {
		json.forEach(action);
	}

	public Object get(Object key) {
		return json.get(key);
	}

	public boolean getBoolean(String key) {
		return json.getBoolean(key);
	}

	public double getDouble(String arg0) {
		return json.getDouble(arg0);
	}

	public int getInt(String key) {
		return json.getInt(key);
	}

	public JSONArray getJSONArray(String key) {
		return json.getJSONArray(key);
	}

	public JSONObject getJSONObject(String key) {
		return json.getJSONObject(key);
	}

	public long getLong(String key) {
		return json.getLong(key);
	}

	

	public  Object getOrDefault(Object key, Object defaultValue) {
		return json.getOrDefault(key, defaultValue);
	}

	public String getString(String key) {
		return json.getString(key);
	}

	public boolean has(String key) {
		return json.has(key);
	}

	public int hashCode() {
		return json.hashCode();
	}

	public boolean isArray() {
		return json.isArray();
	}

	public boolean isEmpty() {
		return json.isEmpty();
	}

	public boolean isNullObject() {
		return json.isNullObject();
	}

	public Set keySet() {
		return json.keySet();
	}

	public Iterator keys() {
		return json.keys();
	}

	public  void replaceAll(BiFunction function) {
		json.replaceAll(function);
	}

	public  Object merge(String key, Object value, BiFunction remappingFunction) {
		return json.merge(key, value, remappingFunction);
	}

	public JSONArray names() {
		return json.names();
	}

	public Object opt(String key) {
		return json.opt(key);
	}

	public boolean optBoolean(String arg0, boolean arg1) {
		return json.optBoolean(arg0, arg1);
	}

	public boolean optBoolean(String key) {
		return json.optBoolean(key);
	}

	public double optDouble(String arg0, double arg1) {
		return json.optDouble(arg0, arg1);
	}

	public double optDouble(String key) {
		return json.optDouble(key);
	}

	public int optInt(String arg0, int arg1) {
		return json.optInt(arg0, arg1);
	}

	public int optInt(String key) {
		return json.optInt(key);
	}

	public JSONArray optJSONArray(String key) {
		return json.optJSONArray(key);
	}

	public JSONObject optJSONObject(String key) {
		return json.optJSONObject(key);
	}

	public long optLong(String arg0, long arg1) {
		return json.optLong(arg0, arg1);
	}

	public long optLong(String key) {
		return json.optLong(key);
	}

	public String optString(String key, String Value) {
		return json.optString(key, Value);
	}

	public String optString(String key) {
		return json.optString(key);
	}

	public Object put(String key, Object value) {
		return json.put(key, value);
	}

	public void putAll(Map arg0, JsonConfig arg1) {
		json.putAll(arg0, arg1);
	}

	public void putAll(Map map) {
		json.putAll(map);
	}

	public  Object putIfAbsent(String key, Object value) {
		return json.putIfAbsent(key, value);
	}

	public  boolean remove(Object key, Object value) {
		return json.remove(key, value);
	}

	public Object remove(Object key) {
		return json.remove(key);
	}

	public Object remove(String key) {
		return json.remove(key);
	}

	public  boolean replace(String key, Object oldValue, Object newValue) {
		return json.replace(key, oldValue, newValue);
	}

	public  Object replace(String key, Object value) {
		return json.replace(key, value);
	}

	public int size() {
		return json.size();
	}

	public JSONArray toJSONArray(JSONArray arg0) {
		return json.toJSONArray(arg0);
	}

	public String toString() {
		return json.toString();
	}

	public String toString(int arg0, int arg1) {
		return json.toString(arg0, arg1);
	}

	public String toString(int indentFactor) {
		return json.toString(indentFactor);
	}

	public Collection values() {
		return json.values();
	}

	public Writer write(Writer arg0) {
		return json.write(arg0);
	}

}
