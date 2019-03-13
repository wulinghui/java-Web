///gggg

package com.wlh.complier.base.html;

import org.junit.Test;
import static org.junit.Assert.*;
public class DHtmlTest {
	@Test//内部类是否同一对象.
	public void innerClass() {
		DHtml html = new DHtml();
		System.out.println(html.body.inner);
		System.out.println( html.bodyList);
		assertEquals(html.body.inner, html.bodyList);
	}
	@Test//获得上个方法的名称. 
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
				html.td( "第 1 行, 第 1 列文本。" ),
				html.td( "第 1 行, 第 2 列文本。" )	
			}) , 
			html.tr( new TagEntity[] {
				html.td( "第 2 行, 第 1 列文本。" ),
				html.td( "第 2 行, 第 2 列文本。" )	
			})
		});
		html.addBodyInner(tag);
		System.out.println(html.toString());
	}
}
