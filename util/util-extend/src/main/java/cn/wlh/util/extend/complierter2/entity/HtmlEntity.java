package cn.wlh.util.extend.complierter2.entity;

public class HtmlEntity {
	String ss ;
	public HtmlEntity table(String table) {
		ss = "<table for-text = 'a in as'>" + table + "</table>";
		return this;
	}
	public HtmlEntity table(String tr , String... td) {
		String table = "<tr>";
		for (String string : td) {
			table += "<td >" + string + "</td>";
		}
		table += "</tr>";
		return table(table);
	}
	
}
