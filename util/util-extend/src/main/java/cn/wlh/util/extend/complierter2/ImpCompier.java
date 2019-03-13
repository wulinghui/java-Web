package cn.wlh.util.extend.complierter2;

import java.util.List;

import cn.wlh.util.extend.complierter2.entity.HtmlEntity;
import cn.wlh.util.extend.complierter2.entity.JavaScriptEntity;

public class ImpCompier extends AbstractComplier{
	String[] strs = new String[] {" a.colA ","a.colB" };	//不断扩展
	
	@Override
	//生成一个html
	public void complierHtml(HtmlEntity t) {
		String a = "<table for-text = 'a in as' >"
				+ "<tr>"
				+ "{{ a.colA }}"
				+ "<td>"
				+ "</td>"
				+ "</tr>"
				+ "</table>";
//		t.table("aaa" , "xxx","aaa");
//		t.table("for-text = 'a in as'" ,"{{ a.colA }}","{{a.colB}}");
//		t.table("for-text = 'a in as'" ," a.colA ","a.colB");
//		t.table("for-text = 'a in as'" ,new String[] {" a.colA ","a.colB"});
		t.table("for-text = 'a in as'" , this.strs );
	}
	//生成一个modle.js
	public void complierModleOfJs(JavaScriptEntity js) {
		String a = "{ tagert : '#id' , modles : { "+"a.colA:" + "javaDataA" +" } }";
	}
	
	//
	public void complierController(java.util.List t) {
		t.size();
		
	}
}
