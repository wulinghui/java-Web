package com.wlh.complier.base.html;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.wlh.complier.base.Constant;
import com.wlh.complier.base.IEntity;

/**
 * @author wlh
 * ��ǩʵ����,һ���ı�ǩ�Ĵ��� <hr/>
 * < a > zz  < / a > ������ǩ.
 */
public class TagEntity implements IEntity, Constant {
	/**
	 * ��ǩ��
	 */
	final String tagName;
	/**
	 * ��ǩ����  "null" , Object.toString
	 */
	Object inner;
	/**
	 * ������
	 */
	final Map<String, String> props = new HashMap<String, String>();

	/**
	 * �����б�ǩ��
	 * 
	 * @param tagName
	 */
	public TagEntity(String tagName) {
		this.tagName = tagName;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(left);
		sb.append(tagName);
		if(inner != null) {
			appendAttribute(sb);
			sb.append(right).append(inner).append(leftLower).append(tagName).append(right);
		}else {
			appendAttribute(sb);
			sb.append(rightLower);
		}
		return sb.toString();
	}
	//�������.
	private void appendAttribute(StringBuilder sb) {
		for (Entry<String, String> element : props.entrySet()) {
			sb	.append(blank).append(element.getKey()).append(equal).append(doubleQuotation)
				.append(element.getValue())
				.append(doubleQuotation);
		}
	}

	/**
	 * ������
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public TagEntity putAttribute(String key, String value) {
		this.props.put(String.valueOf(key), String.valueOf(value));
		return this;
	}
	//��List
	public TagEntity putAttribute(List<String> keyAndValue) {
		if( keyAndValue == null ) return this;
		String sss [] = new String [ keyAndValue.size() ];
		return putAttribute(  keyAndValue.toArray(sss)	) ;
	}
	
	/**һ�����Ѿ�ȷ��,һ���ֽ����û�ȷ��
	 * @param strs
	 * @param keyAndValue
	 * @return
	 */
	public TagEntity putAttribute(String [] strs ,String... keyAndValue) {
		strs = arraysJion(strs, keyAndValue);
		return putAttribute(strs);
	}
	// ����ϲ�.
	public static String[] arraysJion(String[] strs, String... keyAndValue) {
		if( strs == null ||  keyAndValue == null) return null;
		int sLen = strs.length;
		int kLen = keyAndValue.length;
		strs = Arrays.copyOf(strs, sLen + kLen);
		System.arraycopy(keyAndValue, 0, strs,  sLen , kLen);
		return strs;
	}
	//��
	public TagEntity putAttribute(String... keyAndValue) {
		int len = 0;
		if (keyAndValue == null || (len = keyAndValue.length) == 0 || len%2 == 1)
			return this;
		for (int i = 0; i < len && keyAndValue[i] != null; i += 2) {
			putAttribute(keyAndValue[i], keyAndValue[i + 1]);
		}
		return this;
	}

	public TagEntity putAttribute(Object key) {
		return this;
	}

	public Object getInner() {
		return inner;
	}

	public TagEntity setInner(Object inner) {
		this.inner = inner;return this;
	}

	public String getTagName() {
		return tagName;
	}

	public Map<String, String> getProps() {
		return props;
	}
	public class TagInnerList extends LinkedList<TagEntity>{
		public TagInnerList(){ TagEntity.this.inner = this;}
		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder();
			int len = this.size();
			for (int i = 0; i < len ; i++) {
				sb.append( get(i) );
			}
			return sb.toString();
		}
	}
//	/**
//	 * �Զ����ǩ h1˫�߱�ǩ.
//	 * 
//	 * @return
//	 */
//	public String customTag(String tagName, String inner, String... props) {
//		StringBuilder sb = new StringBuilder(left);
//		sb.append(tagName);
//		if (props != null) {
//			int len = props.length;
//			for (int i = 0; i < len && props[i] != null /* && props[i+1] != null */; i += 2) {
//				sb.append(blank).append(props[i]).append(equal).append(doubleQuotation).append(props[i + 1])
//						.append(doubleQuotation);
//			}
//		}
//		sb.append(right).append(inner).append(leftLower).append(tagName).append(right);
//		return sb.toString();
//	}
}
