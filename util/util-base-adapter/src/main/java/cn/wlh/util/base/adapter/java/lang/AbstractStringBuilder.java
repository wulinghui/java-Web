package cn.wlh.util.base.adapter.java.lang;

import java.util.stream.IntStream;

/**
 * @author 吴灵辉
 * @see cn.wlh.util.base.adapter.java.lang.AdapterAppendable<K>
 * 自己定义抽象类..以扩展sb的方法
 */
public class AbstractStringBuilder implements StringBuilderInterface<StringBuilder>{
	StringBuilder sb;
	
	public AbstractStringBuilder(StringBuilder sb) {
		super();
		this.sb = sb;
	}

	public StringBuilder getSb() {
		return sb;
	}

	public void setSb(StringBuilder sb) {
		this.sb = sb;
	}

	/* (non-Javadoc)
	 * @see cn.wlh.util.base.adapter.java.lang.StringBuilderInterface#length()
	 */
	@Override
	public int length() {
		return sb.length();
	}

	/* (non-Javadoc)
	 * @see cn.wlh.util.base.adapter.java.lang.StringBuilderInterface#hashCode()
	 */
	@Override
	public int hashCode() {
		return sb.hashCode();
	}

	/* (non-Javadoc)
	 * @see cn.wlh.util.base.adapter.java.lang.StringBuilderInterface#capacity()
	 */
	@Override
	public int capacity() {
		return sb.capacity();
	}

	/* (non-Javadoc)
	 * @see cn.wlh.util.base.adapter.java.lang.StringBuilderInterface#ensureCapacity(int)
	 */
	@Override
	public void ensureCapacity(int minimumCapacity) {
		sb.ensureCapacity(minimumCapacity);
	}

	/* (non-Javadoc)
	 * @see cn.wlh.util.base.adapter.java.lang.StringBuilderInterface#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		return sb.equals(obj);
	}

	/* (non-Javadoc)
	 * @see cn.wlh.util.base.adapter.java.lang.StringBuilderInterface#chars()
	 */
	@Override
	public  IntStream chars() {
		return sb.chars();
	}

	/* (non-Javadoc)
	 * @see cn.wlh.util.base.adapter.java.lang.StringBuilderInterface#append(java.lang.Object)
	 */
	@Override
	public StringBuilder append(Object obj) {
		return sb.append(obj);
	}

	/* (non-Javadoc)
	 * @see cn.wlh.util.base.adapter.java.lang.StringBuilderInterface#append(java.lang.String)
	 */
	@Override
	public StringBuilder append(String str) {
		return sb.append(str);
	}

	/* (non-Javadoc)
	 * @see cn.wlh.util.base.adapter.java.lang.StringBuilderInterface#append(java.lang.StringBuffer)
	 */
//	@Override
//	public StringBuffer append(StringBuffer sb) {
//		return sb.append(sb);
//	}

	/* (non-Javadoc)
	 * @see cn.wlh.util.base.adapter.java.lang.StringBuilderInterface#trimToSize()
	 */
	@Override
	public void trimToSize() {
		sb.trimToSize();
	}

	/* (non-Javadoc)
	 * @see cn.wlh.util.base.adapter.java.lang.StringBuilderInterface#setLength(int)
	 */
	@Override
	public void setLength(int newLength) {
		sb.setLength(newLength);
	}

	/* (non-Javadoc)
	 * @see cn.wlh.util.base.adapter.java.lang.StringBuilderInterface#codePoints()
	 */
	@Override
	public  IntStream codePoints() {
		return sb.codePoints();
	}

	/* (non-Javadoc)
	 * @see cn.wlh.util.base.adapter.java.lang.StringBuilderInterface#append(java.lang.CharSequence)
	 */
	@Override
	public StringBuilder append(CharSequence s) {
		return sb.append(s);
	}

	/* (non-Javadoc)
	 * @see cn.wlh.util.base.adapter.java.lang.StringBuilderInterface#append(java.lang.CharSequence, int, int)
	 */
	@Override
	public StringBuilder append(CharSequence s, int start, int end) {
		return sb.append(s, start, end);
	}

	/* (non-Javadoc)
	 * @see cn.wlh.util.base.adapter.java.lang.StringBuilderInterface#append(char[])
	 */
	@Override
	public StringBuilder append(char[] str) {
		return sb.append(str);
	}

	/* (non-Javadoc)
	 * @see cn.wlh.util.base.adapter.java.lang.StringBuilderInterface#append(char[], int, int)
	 */
	@Override
	public StringBuilder append(char[] str, int offset, int len) {
		return sb.append(str, offset, len);
	}

	/* (non-Javadoc)
	 * @see cn.wlh.util.base.adapter.java.lang.StringBuilderInterface#append(boolean)
	 */
	@Override
	public StringBuilder append(boolean b) {
		return sb.append(b);
	}

	/* (non-Javadoc)
	 * @see cn.wlh.util.base.adapter.java.lang.StringBuilderInterface#append(char)
	 */
	@Override
	public StringBuilder append(char c) {
		return sb.append(c);
	}

	/* (non-Javadoc)
	 * @see cn.wlh.util.base.adapter.java.lang.StringBuilderInterface#append(int)
	 */
	@Override
	public StringBuilder append(int i) {
		return sb.append(i);
	}

	/* (non-Javadoc)
	 * @see cn.wlh.util.base.adapter.java.lang.StringBuilderInterface#append(long)
	 */
	@Override
	public StringBuilder append(long lng) {
		return sb.append(lng);
	}

	/* (non-Javadoc)
	 * @see cn.wlh.util.base.adapter.java.lang.StringBuilderInterface#append(float)
	 */
	@Override
	public StringBuilder append(float f) {
		return sb.append(f);
	}

	/* (non-Javadoc)
	 * @see cn.wlh.util.base.adapter.java.lang.StringBuilderInterface#append(double)
	 */
	@Override
	public StringBuilder append(double d) {
		return sb.append(d);
	}

	/* (non-Javadoc)
	 * @see cn.wlh.util.base.adapter.java.lang.StringBuilderInterface#appendCodePoint(int)
	 */
	@Override
	public StringBuilder appendCodePoint(int codePoint) {
		return sb.appendCodePoint(codePoint);
	}

	/* (non-Javadoc)
	 * @see cn.wlh.util.base.adapter.java.lang.StringBuilderInterface#charAt(int)
	 */
	@Override
	public char charAt(int index) {
		return sb.charAt(index);
	}

	/* (non-Javadoc)
	 * @see cn.wlh.util.base.adapter.java.lang.StringBuilderInterface#delete(int, int)
	 */
	@Override
	public StringBuilder delete(int start, int end) {
		return sb.delete(start, end);
	}

	/* (non-Javadoc)
	 * @see cn.wlh.util.base.adapter.java.lang.StringBuilderInterface#deleteCharAt(int)
	 */
	@Override
	public StringBuilder deleteCharAt(int index) {
		return sb.deleteCharAt(index);
	}

	/* (non-Javadoc)
	 * @see cn.wlh.util.base.adapter.java.lang.StringBuilderInterface#replace(int, int, java.lang.String)
	 */
	@Override
	public StringBuilder replace(int start, int end, String str) {
		return sb.replace(start, end, str);
	}

	/* (non-Javadoc)
	 * @see cn.wlh.util.base.adapter.java.lang.StringBuilderInterface#insert(int, char[], int, int)
	 */
	@Override
	public StringBuilder insert(int index, char[] str, int offset, int len) {
		return sb.insert(index, str, offset, len);
	}

	/* (non-Javadoc)
	 * @see cn.wlh.util.base.adapter.java.lang.StringBuilderInterface#codePointAt(int)
	 */
	@Override
	public int codePointAt(int index) {
		return sb.codePointAt(index);
	}

	/* (non-Javadoc)
	 * @see cn.wlh.util.base.adapter.java.lang.StringBuilderInterface#insert(int, java.lang.Object)
	 */
	@Override
	public StringBuilder insert(int offset, Object obj) {
		return sb.insert(offset, obj);
	}

	/* (non-Javadoc)
	 * @see cn.wlh.util.base.adapter.java.lang.StringBuilderInterface#insert(int, java.lang.String)
	 */
	@Override
	public StringBuilder insert(int offset, String str) {
		return sb.insert(offset, str);
	}

	/* (non-Javadoc)
	 * @see cn.wlh.util.base.adapter.java.lang.StringBuilderInterface#insert(int, char[])
	 */
	@Override
	public StringBuilder insert(int offset, char[] str) {
		return sb.insert(offset, str);
	}

	/* (non-Javadoc)
	 * @see cn.wlh.util.base.adapter.java.lang.StringBuilderInterface#insert(int, java.lang.CharSequence)
	 */
	@Override
	public StringBuilder insert(int dstOffset, CharSequence s) {
		return sb.insert(dstOffset, s);
	}

	/* (non-Javadoc)
	 * @see cn.wlh.util.base.adapter.java.lang.StringBuilderInterface#insert(int, java.lang.CharSequence, int, int)
	 */
	@Override
	public StringBuilder insert(int dstOffset, CharSequence s, int start, int end) {
		return sb.insert(dstOffset, s, start, end);
	}

	/* (non-Javadoc)
	 * @see cn.wlh.util.base.adapter.java.lang.StringBuilderInterface#insert(int, boolean)
	 */
	@Override
	public StringBuilder insert(int offset, boolean b) {
		return sb.insert(offset, b);
	}

	/* (non-Javadoc)
	 * @see cn.wlh.util.base.adapter.java.lang.StringBuilderInterface#codePointBefore(int)
	 */
	@Override
	public int codePointBefore(int index) {
		return sb.codePointBefore(index);
	}

	/* (non-Javadoc)
	 * @see cn.wlh.util.base.adapter.java.lang.StringBuilderInterface#insert(int, char)
	 */
	@Override
	public StringBuilder insert(int offset, char c) {
		return sb.insert(offset, c);
	}

	/* (non-Javadoc)
	 * @see cn.wlh.util.base.adapter.java.lang.StringBuilderInterface#insert(int, int)
	 */
	@Override
	public StringBuilder insert(int offset, int i) {
		return sb.insert(offset, i);
	}

	/* (non-Javadoc)
	 * @see cn.wlh.util.base.adapter.java.lang.StringBuilderInterface#insert(int, long)
	 */
	@Override
	public StringBuilder insert(int offset, long l) {
		return sb.insert(offset, l);
	}

	/* (non-Javadoc)
	 * @see cn.wlh.util.base.adapter.java.lang.StringBuilderInterface#insert(int, float)
	 */
	@Override
	public StringBuilder insert(int offset, float f) {
		return sb.insert(offset, f);
	}

	/* (non-Javadoc)
	 * @see cn.wlh.util.base.adapter.java.lang.StringBuilderInterface#insert(int, double)
	 */
	@Override
	public StringBuilder insert(int offset, double d) {
		return sb.insert(offset, d);
	}

	/* (non-Javadoc)
	 * @see cn.wlh.util.base.adapter.java.lang.StringBuilderInterface#indexOf(java.lang.String)
	 */
	@Override
	public int indexOf(String str) {
		return sb.indexOf(str);
	}

	/* (non-Javadoc)
	 * @see cn.wlh.util.base.adapter.java.lang.StringBuilderInterface#indexOf(java.lang.String, int)
	 */
	@Override
	public int indexOf(String str, int fromIndex) {
		return sb.indexOf(str, fromIndex);
	}

	/* (non-Javadoc)
	 * @see cn.wlh.util.base.adapter.java.lang.StringBuilderInterface#codePointCount(int, int)
	 */
	@Override
	public int codePointCount(int beginIndex, int endIndex) {
		return sb.codePointCount(beginIndex, endIndex);
	}

	/* (non-Javadoc)
	 * @see cn.wlh.util.base.adapter.java.lang.StringBuilderInterface#lastIndexOf(java.lang.String)
	 */
	@Override
	public int lastIndexOf(String str) {
		return sb.lastIndexOf(str);
	}

	/* (non-Javadoc)
	 * @see cn.wlh.util.base.adapter.java.lang.StringBuilderInterface#lastIndexOf(java.lang.String, int)
	 */
	@Override
	public int lastIndexOf(String str, int fromIndex) {
		return sb.lastIndexOf(str, fromIndex);
	}

	/* (non-Javadoc)
	 * @see cn.wlh.util.base.adapter.java.lang.StringBuilderInterface#reverse()
	 */
	@Override
	public StringBuilder reverse() {
		return sb.reverse();
	}

	/* (non-Javadoc)
	 * @see cn.wlh.util.base.adapter.java.lang.StringBuilderInterface#toString()
	 */
	@Override
	public String toString() {
		return sb.toString();
	}

	/* (non-Javadoc)
	 * @see cn.wlh.util.base.adapter.java.lang.StringBuilderInterface#offsetByCodePoints(int, int)
	 */
	@Override
	public int offsetByCodePoints(int index, int codePointOffset) {
		return sb.offsetByCodePoints(index, codePointOffset);
	}

	/* (non-Javadoc)
	 * @see cn.wlh.util.base.adapter.java.lang.StringBuilderInterface#getChars(int, int, char[], int)
	 */
	@Override
	public void getChars(int srcBegin, int srcEnd, char[] dst, int dstBegin) {
		sb.getChars(srcBegin, srcEnd, dst, dstBegin);
	}

	/* (non-Javadoc)
	 * @see cn.wlh.util.base.adapter.java.lang.StringBuilderInterface#setCharAt(int, char)
	 */
	@Override
	public void setCharAt(int index, char ch) {
		sb.setCharAt(index, ch);
	}

	/* (non-Javadoc)
	 * @see cn.wlh.util.base.adapter.java.lang.StringBuilderInterface#substring(int)
	 */
	@Override
	public String substring(int start) {
		return sb.substring(start);
	}

	/* (non-Javadoc)
	 * @see cn.wlh.util.base.adapter.java.lang.StringBuilderInterface#subSequence(int, int)
	 */
	@Override
	public CharSequence subSequence(int start, int end) {
		return sb.subSequence(start, end);
	}

	/* (non-Javadoc)
	 * @see cn.wlh.util.base.adapter.java.lang.StringBuilderInterface#substring(int, int)
	 */
	@Override
	public String substring(int start, int end) {
		return sb.substring(start, end);
	}

	@Override
	public StringBuilder append(StringBuffer sb) {
		return getSb().append(sb);
	}
}
