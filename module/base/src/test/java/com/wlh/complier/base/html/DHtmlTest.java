///gggg

package com.wlh.complier.base.html;

import org.junit.Test;
import static org.junit.Assert.*;
public class DHtmlTest {
	@Test//�ڲ����Ƿ�ͬһ����.
	public void innerClass() {
		DHtml html = new DHtml();
		System.out.println(html.body.inner);
		System.out.println( html.bodyList);
		assertEquals(html.body.inner, html.bodyList);
	}
	@Test//����ϸ�����������. 
	public void getForwardMethod() {
		DHtml html = new DHtml();
//		html.a();
		System.out.println("============");
		html.customTag( "", "");
	}
	@Test
	public void table() {
		DHtml html = new DHtml();
		TagEntity tag = 
		html.table(new TagEntity[]{
			html.tr( new TagEntity[] {
				html.td( "�� 1 ��, �� 1 ���ı���" ),
				html.td( "�� 1 ��, �� 2 ���ı���" )	
			}) , 
			html.tr( new TagEntity[] {
				html.td( "�� 2 ��, �� 1 ���ı���" ),
				html.td( "�� 2 ��, �� 2 ���ı���" )	
			})
		});
		html.addBodyInner(tag);
		System.out.println(html.toString());
	}
}
