package cn.wlh.util.base.adapter.servlet;

import java.io.Writer;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

import cn.wlh.util.base._Field;
import cn.wlh.util.base.adapter.java.util.JavaUtilFactory;
import net.sf.json.JSONArray;
import net.sf.json.JSONNull;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
/**
 * @author 吴灵辉
 * 这是聚合json的jar
 * 和实现List -- 通过  json 或者 里面的属性实现.
 * 
 */
public class RecordSet2  implements List<Object> , JsonInterface{
	JSONArray json;
	
//	List list = _Field.getValueByField(json, "elements");
	
	/**记录new级别的标识*/
	final int newFlag;  
	/**
	 * @param  i   @seee cn.wlh.util.base.adapter.java.util.JavaUtilFactory.newflag(int)
	 */
	public RecordSet2(int i) {
		init(i);
		newFlag = i;
	}
	
	/**
	 *  适用于在方法体里面使用查询和修改 
	 */
	public RecordSet2() {
		this( JavaUtilFactory.SELECT_OF_METHOD );
	}
	
	protected void init( int i ) {
		json = new JSONArray();
	}
	
	public <T> T getOne(int index , String key ) {
		Map<String, Object> map = (Map<String, Object>) this.get(index);
		return (T) map.get(key);
	}
	
	public <T> T getOneOfFirst(String key) {
		return getOne(0, key);
	}
	
	/**只放已有的key,没有其他多余的操作。
	 * @param index
	 * @param key
	 * @param value
	 * @return
	 */
	public RecordSet2 putOne(int index , String key , Object value) {
		Map<String, Object> map = (Map<String, Object>) this.get(index);
		map.put(key, value);
		return this;
	}
	public RecordSet2 putOneOfFirst( String key , Object value ,int newflag) {
		return putOneOfAutoAdd(0, key, value, newflag);
	}
	/** 一定可以put进去。
	 * @param index
	 * @param key
	 * @param value
	 * @param newflag @seee cn.wlh.util.base.adapter.java.util.JavaUtilFactory.newflag(int)
	 * @return
	 */
	public RecordSet2 putOneOfAutoAdd(int index , String key , Object value ,int newflag) {
		Map<String, Object> map = (Map<String, Object>) this.get(index);
		if( map == null ) {
			map = JavaUtilFactory.newMap(newflag);
		}
		map.put(key, value);
		return this;
	}

	@Override
	public String toJson() {
		return toString();
	}

	public  void forEach(Consumer action) {
		json.forEach(action);
	}

	public  void replaceAll(UnaryOperator operator) {
		json.replaceAll(operator);
	}

	public  boolean removeIf(Predicate filter) {
		return json.removeIf(filter);
	}

	public  void sort(Comparator c) {
		json.sort(c);
	}

	public  Stream stream() {
		return json.stream();
	}

	public  Stream parallelStream() {
		return json.parallelStream();
	}

	public  Spliterator spliterator() {
		return json.spliterator();
	}

	public void add(int index, Object value) {
		json.add(index, value);
	}

	public void add(int index, Object value, JsonConfig jsonConfig) {
		json.add(index, value, jsonConfig);
	}

	public boolean add(Object value) {
		return json.add(value);
	}

	public boolean add(Object value, JsonConfig jsonConfig) {
		return json.add(value, jsonConfig);
	}

	public boolean addAll(Collection collection) {
		return json.addAll(collection);
	}

	public boolean addAll(Collection collection, JsonConfig jsonConfig) {
		return json.addAll(collection, jsonConfig);
	}

	public boolean addAll(int index, Collection collection) {
		return json.addAll(index, collection);
	}

	public boolean addAll(int index, Collection collection, JsonConfig jsonConfig) {
		return json.addAll(index, collection, jsonConfig);
	}

	public void clear() {
		json.clear();
	}

	public int compareTo(Object obj) {
		return json.compareTo(obj);
	}

	public boolean contains(Object o) {
		return json.contains(o);
	}

	public boolean contains(Object o, JsonConfig jsonConfig) {
		return json.contains(o, jsonConfig);
	}

	public boolean containsAll(Collection collection) {
		return json.containsAll(collection);
	}

	public boolean containsAll(Collection collection, JsonConfig jsonConfig) {
		return json.containsAll(collection, jsonConfig);
	}

	public JSONArray discard(int index) {
		return json.discard(index);
	}

	public JSONArray discard(Object o) {
		return json.discard(o);
	}

	public JSONArray element(boolean value) {
		return json.element(value);
	}

	public JSONArray element(Collection value) {
		return json.element(value);
	}

	public JSONArray element(Collection value, JsonConfig jsonConfig) {
		return json.element(value, jsonConfig);
	}

	public JSONArray element(double value) {
		return json.element(value);
	}

	public JSONArray element(int value) {
		return json.element(value);
	}

	public JSONArray element(int index, boolean value) {
		return json.element(index, value);
	}

	public JSONArray element(int index, Collection value) {
		return json.element(index, value);
	}

	public JSONArray element(int index, Collection value, JsonConfig jsonConfig) {
		return json.element(index, value, jsonConfig);
	}

	public JSONArray element(int index, double value) {
		return json.element(index, value);
	}

	public JSONArray element(int index, int value) {
		return json.element(index, value);
	}

	public JSONArray element(int index, long value) {
		return json.element(index, value);
	}

	public JSONArray element(int index, Map value) {
		return json.element(index, value);
	}

	public JSONArray element(int index, Map value, JsonConfig jsonConfig) {
		return json.element(index, value, jsonConfig);
	}

	public JSONArray element(int index, Object value) {
		return json.element(index, value);
	}

	public JSONArray element(int index, Object value, JsonConfig jsonConfig) {
		return json.element(index, value, jsonConfig);
	}

	public JSONArray element(int index, String value) {
		return json.element(index, value);
	}

	public JSONArray element(int index, String value, JsonConfig jsonConfig) {
		return json.element(index, value, jsonConfig);
	}

	public JSONArray element(JSONNull value) {
		return json.element(value);
	}

	public JSONArray element(JSONObject value) {
		return json.element(value);
	}

	public JSONArray element(long value) {
		return json.element(value);
	}

	public JSONArray element(Map value) {
		return json.element(value);
	}

	public JSONArray element(Map value, JsonConfig jsonConfig) {
		return json.element(value, jsonConfig);
	}

	public JSONArray element(Object value) {
		return json.element(value);
	}

	public JSONArray element(Object value, JsonConfig jsonConfig) {
		return json.element(value, jsonConfig);
	}

	public JSONArray element(String value) {
		return json.element(value);
	}

	public JSONArray element(String value, JsonConfig jsonConfig) {
		return json.element(value, jsonConfig);
	}

	public boolean equals(Object obj) {
		return json.equals(obj);
	}

	public Object get(int index) {
		return json.get(index);
	}

	public boolean getBoolean(int index) {
		return json.getBoolean(index);
	}

	public double getDouble(int index) {
		return json.getDouble(index);
	}

	public int getInt(int index) {
		return json.getInt(index);
	}

	public JSONArray getJSONArray(int index) {
		return json.getJSONArray(index);
	}

	public JSONObject getJSONObject(int index) {
		return json.getJSONObject(index);
	}

	public long getLong(int index) {
		return json.getLong(index);
	}

	public String getString(int index) {
		return json.getString(index);
	}

	public int hashCode() {
		return json.hashCode();
	}

	public int indexOf(Object o) {
		return json.indexOf(o);
	}

	public boolean isArray() {
		return json.isArray();
	}

	public boolean isEmpty() {
		return json.isEmpty();
	}

	public boolean isExpandElements() {
		return json.isExpandElements();
	}

	public Iterator iterator() {
		return json.iterator();
	}

	public String join(String separator) {
		return json.join(separator);
	}

	public String join(String separator, boolean stripQuotes) {
		return json.join(separator, stripQuotes);
	}

	public int lastIndexOf(Object o) {
		return json.lastIndexOf(o);
	}

	public ListIterator listIterator() {
		return json.listIterator();
	}

	public ListIterator listIterator(int index) {
		return json.listIterator(index);
	}

	public Object opt(int index) {
		return json.opt(index);
	}

	public boolean optBoolean(int index) {
		return json.optBoolean(index);
	}

	public boolean optBoolean(int index, boolean Value) {
		return json.optBoolean(index, Value);
	}

	public double optDouble(int index) {
		return json.optDouble(index);
	}

	public double optDouble(int index, double Value) {
		return json.optDouble(index, Value);
	}

	public int optInt(int index) {
		return json.optInt(index);
	}

	public int optInt(int index, int Value) {
		return json.optInt(index, Value);
	}

	public JSONArray optJSONArray(int index) {
		return json.optJSONArray(index);
	}

	public JSONObject optJSONObject(int index) {
		return json.optJSONObject(index);
	}

	public long optLong(int index) {
		return json.optLong(index);
	}

	public long optLong(int index, long Value) {
		return json.optLong(index, Value);
	}

	public String optString(int index) {
		return json.optString(index);
	}

	public String optString(int index, String Value) {
		return json.optString(index, Value);
	}

	public Object remove(int index) {
		return json.remove(index);
	}

	public boolean remove(Object o) {
		return json.remove(o);
	}

	public boolean removeAll(Collection collection) {
		return json.removeAll(collection);
	}

	public boolean removeAll(Collection collection, JsonConfig jsonConfig) {
		return json.removeAll(collection, jsonConfig);
	}

	public boolean retainAll(Collection collection) {
		return json.retainAll(collection);
	}

	public boolean retainAll(Collection collection, JsonConfig jsonConfig) {
		return json.retainAll(collection, jsonConfig);
	}

	public Object set(int index, Object value) {
		return json.set(index, value);
	}

	public Object set(int index, Object value, JsonConfig jsonConfig) {
		return json.set(index, value, jsonConfig);
	}

	public void setExpandElements(boolean expandElements) {
		json.setExpandElements(expandElements);
	}

	public int size() {
		return json.size();
	}

	public List subList(int fromIndex, int toIndex) {
		return json.subList(fromIndex, toIndex);
	}

	public Object[] toArray() {
		return json.toArray();
	}

	public Object[] toArray(Object[] array) {
		return json.toArray(array);
	}

	public JSONObject toJSONObject(JSONArray names) {
		return json.toJSONObject(names);
	}

	public String toString() {
		return json.toString();
	}

	public String toString(int indentFactor) {
		return json.toString(indentFactor);
	}

	public String toString(int indentFactor, int indent) {
		return json.toString(indentFactor, indent);
	}

	public Writer write(Writer writer) {
		return json.write(writer);
	}

	
}
