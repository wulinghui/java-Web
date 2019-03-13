package cn.wlh.util.base.adapter.java.lang;

import java.util.stream.IntStream;

public class AbstractStringBuffer implements StringBuilderInterface<StringBuffer>{
	StringBuffer sbffer;
	
	
	public AbstractStringBuffer(StringBuffer sbffer) {
		super();
		this.sbffer = sbffer;
	}

	public StringBuffer getSb() {
		return sbffer;
	}

	public void setSb(StringBuffer sbffer) {
		this.sbffer = sbffer;
	}

	public int hashCode() {
		return sbffer.hashCode();
	}

	public boolean equals(Object obj) {
		return sbffer.equals(obj);
	}

	public  IntStream chars() {
		return sbffer.chars();
	}

	public  IntStream codePoints() {
		return sbffer.codePoints();
	}

	public int length() {
		return sbffer.length();
	}

	public int capacity() {
		return sbffer.capacity();
	}

	public void ensureCapacity(int minimumCapacity) {
		sbffer.ensureCapacity(minimumCapacity);
	}

	public void trimToSize() {
		sbffer.trimToSize();
	}

	public void setLength(int newLength) {
		sbffer.setLength(newLength);
	}

	public char charAt(int index) {
		return sbffer.charAt(index);
	}

	public int codePointAt(int index) {
		return sbffer.codePointAt(index);
	}

	public int codePointBefore(int index) {
		return sbffer.codePointBefore(index);
	}

	public int codePointCount(int beginIndex, int endIndex) {
		return sbffer.codePointCount(beginIndex, endIndex);
	}

	public int offsetByCodePoints(int index, int codePointOffset) {
		return sbffer.offsetByCodePoints(index, codePointOffset);
	}

	public void getChars(int srcBegin, int srcEnd, char[] dst, int dstBegin) {
		sbffer.getChars(srcBegin, srcEnd, dst, dstBegin);
	}

	public void setCharAt(int index, char ch) {
		sbffer.setCharAt(index, ch);
	}

	public StringBuffer append(Object obj) {
		return sbffer.append(obj);
	}

	public StringBuffer append(String str) {
		return sbffer.append(str);
	}

	public StringBuffer append(StringBuffer sb) {
		return sbffer.append(sb);
	}

	public StringBuffer append(CharSequence s) {
		return sbffer.append(s);
	}

	public StringBuffer append(CharSequence s, int start, int end) {
		return sbffer.append(s, start, end);
	}

	public StringBuffer append(char[] str) {
		return sbffer.append(str);
	}

	public StringBuffer append(char[] str, int offset, int len) {
		return sbffer.append(str, offset, len);
	}

	public StringBuffer append(boolean b) {
		return sbffer.append(b);
	}

	public StringBuffer append(char c) {
		return sbffer.append(c);
	}

	public StringBuffer append(int i) {
		return sbffer.append(i);
	}

	public StringBuffer appendCodePoint(int codePoint) {
		return sbffer.appendCodePoint(codePoint);
	}

	public StringBuffer append(long lng) {
		return sbffer.append(lng);
	}

	public StringBuffer append(float f) {
		return sbffer.append(f);
	}

	public StringBuffer append(double d) {
		return sbffer.append(d);
	}

	public StringBuffer delete(int start, int end) {
		return sbffer.delete(start, end);
	}

	public StringBuffer deleteCharAt(int index) {
		return sbffer.deleteCharAt(index);
	}

	public StringBuffer replace(int start, int end, String str) {
		return sbffer.replace(start, end, str);
	}

	public String substring(int start) {
		return sbffer.substring(start);
	}

	public CharSequence subSequence(int start, int end) {
		return sbffer.subSequence(start, end);
	}

	public String substring(int start, int end) {
		return sbffer.substring(start, end);
	}

	public StringBuffer insert(int index, char[] str, int offset, int len) {
		return sbffer.insert(index, str, offset, len);
	}

	public StringBuffer insert(int offset, Object obj) {
		return sbffer.insert(offset, obj);
	}

	public StringBuffer insert(int offset, String str) {
		return sbffer.insert(offset, str);
	}

	public StringBuffer insert(int offset, char[] str) {
		return sbffer.insert(offset, str);
	}

	public StringBuffer insert(int dstOffset, CharSequence s) {
		return sbffer.insert(dstOffset, s);
	}

	public StringBuffer insert(int dstOffset, CharSequence s, int start, int end) {
		return sbffer.insert(dstOffset, s, start, end);
	}

	public StringBuffer insert(int offset, boolean b) {
		return sbffer.insert(offset, b);
	}

	public StringBuffer insert(int offset, char c) {
		return sbffer.insert(offset, c);
	}

	public StringBuffer insert(int offset, int i) {
		return sbffer.insert(offset, i);
	}

	public StringBuffer insert(int offset, long l) {
		return sbffer.insert(offset, l);
	}

	public StringBuffer insert(int offset, float f) {
		return sbffer.insert(offset, f);
	}

	public StringBuffer insert(int offset, double d) {
		return sbffer.insert(offset, d);
	}

	public int indexOf(String str) {
		return sbffer.indexOf(str);
	}

	public int indexOf(String str, int fromIndex) {
		return sbffer.indexOf(str, fromIndex);
	}

	public int lastIndexOf(String str) {
		return sbffer.lastIndexOf(str);
	}

	public int lastIndexOf(String str, int fromIndex) {
		return sbffer.lastIndexOf(str, fromIndex);
	}

	public StringBuffer reverse() {
		return sbffer.reverse();
	}

	public String toString() {
		return sbffer.toString();
	}
	
}
