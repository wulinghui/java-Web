package cn.wlh.util.base.adapter.java.lang;

import org.apache.commons.lang3.StringUtils;
import org.junit.Ignore;
import org.junit.Test;

import net.sf.json.JSONObject;

/**
 * @author 吴灵辉
 * net.sf.json.JSONObject   Json-Lib api
 */
public class JsonLibTest {
	@Test @Ignore
	public void test0() {
		boolean zz = StringUtils.containsAny("zzzzzz、3/1、null	、zzz", new String[] {
				"111null2","null"
		});
		System.out.println( zz );
		JSONObject obj = null;
//		obj.get
		org.json.JSONObject  zz11 = null;
		
//		String key = null;
//		//		zz.get
//		zz.get(key);
	}
}
