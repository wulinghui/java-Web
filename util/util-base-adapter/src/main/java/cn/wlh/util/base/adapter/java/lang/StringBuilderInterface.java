package cn.wlh.util.base.adapter.java.lang;

import java.util.stream.IntStream;

public interface StringBuilderInterface<K extends Appendable> {

	public K getSb() ;

	public void setSb(K sb) ;

	int length();

	int hashCode();

	int capacity();

	void ensureCapacity(int minimumCapacity);

	boolean equals(Object obj);

	IntStream chars();

	K append(Object obj);

	K append(String str);

	K append(StringBuffer sb);

	void trimToSize();

	void setLength(int newLength);

	IntStream codePoints();

	K append(CharSequence s);

	K append(CharSequence s, int start, int end);

	K append(char[] str);

	K append(char[] str, int offset, int len);

	K append(boolean b);

	K append(char c);

	K append(int i);

	K append(long lng);

	K append(float f);

	K append(double d);

	K appendCodePoint(int codePoint);

	char charAt(int index);

	K delete(int start, int end);

	K deleteCharAt(int index);

	K replace(int start, int end, String str);

	K insert(int index, char[] str, int offset, int len);

	int codePointAt(int index);

	K insert(int offset, Object obj);

	K insert(int offset, String str);

	K insert(int offset, char[] str);

	K insert(int dstOffset, CharSequence s);

	K insert(int dstOffset, CharSequence s, int start, int end);

	K insert(int offset, boolean b);

	int codePointBefore(int index);

	K insert(int offset, char c);

	K insert(int offset, int i);

	K insert(int offset, long l);

	K insert(int offset, float f);

	K insert(int offset, double d);

	int indexOf(String str);

	int indexOf(String str, int fromIndex);

	int codePointCount(int beginIndex, int endIndex);

	int lastIndexOf(String str);

	int lastIndexOf(String str, int fromIndex);

	K reverse();

	String toString();

	int offsetByCodePoints(int index, int codePointOffset);

	void getChars(int srcBegin, int srcEnd, char[] dst, int dstBegin);

	void setCharAt(int index, char ch);

	String substring(int start);

	CharSequence subSequence(int start, int end);

	String substring(int start, int end);

}