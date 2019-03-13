package cn.wlh.util.base.adapter.json;

import org.junit.Ignore;
import org.junit.Test;

import cn.wlh.util.base.adapter.java.lang.AdapterAppendable;
import net.sf.json.JSONObject;

/**
 * @author 吴灵辉
 * net.sf.json.JSONObject   Json-Lib api
 */
public class AdapterAppendableTest {
	private static final String INSERT_INTO_SHENQINGH_VALUES = "insert into (shenqingh values (?";
	AdapterAppendable sb = new AdapterAppendable(new StringBuffer());
	@Test@Ignore  // 只能追加..
	public void test0() {	
		sb.append(INSERT_INTO_SHENQINGH_VALUES);
		String csq = ",zz"; 
//		sb.append( csq , 2, csq.length());
		sb.append( csq , 1, csq.length());
		System.out.println(sb.getSb().toString());
	}
	@Test @Ignore //insert 
	public void test1() {
		StringBuffer sb = new StringBuffer(INSERT_INTO_SHENQINGH_VALUES);
		sb.insert(2, "zzz");
		System.out.println(sb.toString());
	}
	@Test @Ignore // 用insert 代替append
	public void test2() {
		StringBuffer sb = new StringBuffer(INSERT_INTO_SHENQINGH_VALUES);
		sb.insert(INSERT_INTO_SHENQINGH_VALUES.length(), "zzz");
		System.out.println(sb.toString());
	}
}
