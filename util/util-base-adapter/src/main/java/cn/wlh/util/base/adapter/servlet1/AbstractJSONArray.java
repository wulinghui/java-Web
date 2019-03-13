package cn.wlh.util.base.adapter.servlet1;

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

import cn.wlh.util.base.adapter.servlet.JsonInterface;
import net.sf.json.JSONArray;
import net.sf.json.JSONNull;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

public abstract class AbstractJSONArray<E> implements List<E> , JsonInterface{
	protected JSONArray json = new JSONArray();
	@Override
	public String toJson() {
		return json.toString();
	}
	
	@Override
	public int size() {
		return json.size();
	}

	@Override
	public boolean isEmpty() {
		return json.isEmpty();
	}

	@Override
	public boolean contains(Object o) {
		return json.contains(o);
	}

	@Override
	public Iterator<E> iterator() {
		return json.iterator();
	}

	@Override
	public Object[] toArray() {
		return json.toArray();
	}

	@Override
	public <E> E[] toArray(E[] a) {
		return (E[]) json.toArray(a);
	}

	@Override
	public boolean add(E e) {
		return json.add(e);
	}

	@Override
	public boolean remove(Object o) {
		return json.remove(o);
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		return json.containsAll(c);
	}

	@Override
	public boolean addAll(Collection<? extends E> c) {
		return json.addAll(c);
	}

	@Override
	public boolean addAll(int index, Collection<? extends E> c) {
		return json.addAll(index, c);
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		return json.removeAll(c);
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		return json.retainAll(c)	;
	}

	@Override
	public void clear() {
		json.clear();
	}

//	{ // 子类
//	Object object = json.get(index);
//	if( object instanceof JSONObject) {
//		JSONObject new_name = (JSONObject) object;
//		
//	}
//	//看看真实的类.
//	System.out.println( object.getClass().getName() );
//	return  (E) object; 
//}

	@Override
	public E set(int index, E element) {
		return (E) json.set(index, element);
	}

	@Override
	public void add(int index, E element) {
		json.add(index, element);
	}

	@Override
	public E remove(int index) {
		return (E) json.remove(index);
	}

	@Override
	public int indexOf(Object o) {
		return json.indexOf(o);
	}

	@Override
	public int lastIndexOf(Object o) {
		return json.lastIndexOf(o);
	}

	@Override
	public ListIterator<E> listIterator() {
		return json.listIterator();
	}

	@Override
	public ListIterator<E> listIterator(int index) {
		return json.listIterator(index);
	}

	@Override
	public List<E> subList(int fromIndex, int toIndex) {
		return json.subList(fromIndex, toIndex);
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

	public void add(int index, Object value, JsonConfig jsonConfig) {
		json.add(index, value, jsonConfig);
	}

	public boolean add(Object value, JsonConfig jsonConfig) {
		return json.add(value, jsonConfig);
	}

	public boolean addAll(Collection collection, JsonConfig jsonConfig) {
		return json.addAll(collection, jsonConfig);
	}

	public boolean addAll(int index, Collection collection, JsonConfig jsonConfig) {
		return json.addAll(index, collection, jsonConfig);
	}

	public int compareTo(Object obj) {
		return json.compareTo(obj);
	}

	public boolean contains(Object o, JsonConfig jsonConfig) {
		return json.contains(o, jsonConfig);
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

	public boolean isArray() {
		return json.isArray();
	}

	public boolean isExpandElements() {
		return json.isExpandElements();
	}

	public String join(String separator) {
		return json.join(separator);
	}

	public String join(String separator, boolean stripQuotes) {
		return json.join(separator, stripQuotes);
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

	public boolean removeAll(Collection collection, JsonConfig jsonConfig) {
		return json.removeAll(collection, jsonConfig);
	}

	public boolean retainAll(Collection collection, JsonConfig jsonConfig) {
		return json.retainAll(collection, jsonConfig);
	}

	public Object set(int index, Object value, JsonConfig jsonConfig) {
		return json.set(index, value, jsonConfig);
	}

	public void setExpandElements(boolean expandElements) {
		json.setExpandElements(expandElements);
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
