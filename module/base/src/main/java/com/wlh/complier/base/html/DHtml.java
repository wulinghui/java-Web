package com.wlh.complier.base.html;


import com.wlh.complier.base.Constant;
import com.wlh.complier.base.IEntity;


/**
 * @author wlh
 * ������html
 */
public class DHtml implements IEntity ,Constant{
	//��һ��
	TagEntity html = new TagEntity("HTML");
	//�ڶ���
	TagEntity head = new TagEntity("HEAD");
	TagEntity body = new TagEntity("BODY");
	//������   head����,���ϵ������ηŵ�List����
	TagEntity.TagInnerList headList = head.new TagInnerList();
	TagEntity.TagInnerList bodyList = body.new TagInnerList();

	
	public TagEntity table(TagEntity[] inner , String... keyAndValue) {
		TagEntity table = customTag();
		TagEntity.TagInnerList list = table.new TagInnerList();
		int len = inner.length;
		for (int i = 0; i < len; i++) {
			if( "THEAD".equalsIgnoreCase( inner[i].getTagName() )  ) {
				list.addFirst(inner[i]);
			}else if( "tFoot".equalsIgnoreCase( inner[i].getTagName() ) ) {
				list.addLast( inner[i] );
			}else {
				list.add ( inner[i] );
			}
		}
		return table;
	}
	public TagEntity tr(TagEntity[] inner , String... keyAndValue) {
		TagEntity tr = customTag();
		TagEntity.TagInnerList list = tr.new TagInnerList();
		int len = inner.length;
		for (int i = 0; i < len; i++) {
			list.add ( inner[i] );
		}
		return tr;
	}
	public TagEntity td(Object inner , String... keyAndValue) {
		return customTag(inner, keyAndValue) ;
	}
	public TagEntity a(Object inner , String... keyAndValue) {
		return customTag(inner, new String[]{
				this.href , this.js_void
			} , keyAndValue) ;
	}
	/** 
	 * @param inner
	 * @param href ��Ҫд  JScript ����
	 * @param keyAndValue
	 * @return
	 */
	public TagEntity a(Object inner,String href ,String... keyAndValue) {
		return customTag(inner, new String[]{
			this.href , href
		} ,keyAndValue) ;
	}
	/**
	 * һ�����Ѿ�ȷ��,һ���ֽ����û�ȷ��
	 * @param inner
	 * @param strs
	 * @param keyAndValue
	 * @return
	 */
	protected TagEntity customTag( Object inner, String [] strs ,String... keyAndValue) {
		return customTag().setInner(inner).putAttribute(strs , keyAndValue);
	}
	/** �����ߵķ��������Ǳ�ǩ��
	 * @param inner
	 * @param keyAndValue
	 * @return
	 */
	protected TagEntity customTag( Object inner, String... keyAndValue){
		//����ϲ㷽����
		String tag = Thread.currentThread().getStackTrace()[2].getMethodName();
//		= null;
//		System.out.println( tag );
//		StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
//		int len = stackTrace.length;
//		for (int i = 0; i < len ; i++) {
//			System.out.println(stackTrace[i].getClassName() + "||" + stackTrace[i].getMethodName());
//			if("customTag".equals(stackTrace[i].getMethodName())) {
//				tag = stackTrace[i+1].getMethodName();
////				break;
//			}
//		}
//		// Thread.currentThread().getStackTrace()[3].getMethodName();
//		System.out.println("tag=="+tag);
		return customTag0(tag, inner, keyAndValue);
	}
	/**	�����ߵķ��������Ǳ�ǩ��
	 * @return
	 */
	protected TagEntity customTag(){
		//����ϲ㷽����
		return  new TagEntity( Thread.currentThread().getStackTrace()[2].getMethodName() );
	}
	/** 
	 * @return
	 */
	protected TagEntity customTag0(String tag , Object inner, String... keyAndValue){
		return new TagEntity(tag).setInner(inner).putAttribute(keyAndValue);
	}
	
	/**
	 * 
	 * ���涼��get toString add����.
	 * 
	 * 
	 * 
	 * @return
	 */
	public TagEntity getHtml() {
		return html;
	}
	public TagEntity getHead() {
		return head;
	}
	public TagEntity getBody() {
		return body;
	}
	public TagEntity.TagInnerList getHeadList() {
		return headList;
	}
	public TagEntity.TagInnerList getBodyList() {
		return bodyList;
	}
	public DHtml addHeadInner(TagEntity e) {
		headList.add(e);
		return this;
	}
	public DHtml addBodyInner(TagEntity e) {
		bodyList.add(e);
		return this;
	}
	@Override
	public String toString() {
		TagEntity.TagInnerList list = html.new TagInnerList();
		list.add(head);
		list.add(body);
		return html.toString();
	}
	
	
	
	
//	/** �Զ����ǩ   h1˫�߱�ǩ.
//	 * @return
//	 */
//	public String customTag(String tagName,String inner,String...props) {
//		StringBuilder sb = new StringBuilder(left);
//		sb.append(tagName);
//		if(props != null) {
//			int len = props.length;
//			for (int i = 0; i < len && props[i] != null /*&& props[i+1] != null*/; i+=2) {
//				sb.append(blank).append(props[i]).append(equal)
//				  .append(doubleQuotation)
//				  .append(props[i+1])
//				  .append(doubleQuotation);
//			}
//		}
//		sb.append(right)
//		   	    .append(inner)
//		  .append(leftLower).append(tagName).append(right);
//		return sb.toString();
//	}
//	/** �Զ����ǩ    ������h1˫�߱�ǩ.
//	 * @return
//	 */
//	public String customTag(String tagName,String inner) {
//		return customTag(tagName, inner, null);
//	}
//	/** �Զ����ǩ    h1�պϱ�ǩ.
//	 * @return
//	 */
//	public String customTag(String tagName,String...props) {
//		StringBuilder sb = new StringBuilder(left);
//		sb.append(tagName);
//		if(props != null) {
//			int len = props.length;
//			for (int i = 0; i < len && props[i] != null /*&& props[i+1] != null*/; i+=2) {
//				sb.append(blank).append(props[i]).append(equal)
//				  .append(doubleQuotation)
//				  .append(props[i+1])
//				  .append(doubleQuotation);
//			}
//		}
//		sb.append(rightLower);
//		return sb.toString();
//	}
//	/** �Զ����ǩ    ������h1�պϱ�ǩ.
//	 * @return
//	 */
//	public String customTag(String tagName) {
//		return customTag(tagName,(String[]) null) ;
//	}
}
