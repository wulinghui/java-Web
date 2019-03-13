package cn.wlh.framework.web.cms.view.doget;

import java.sql.SQLException;

import cn.wlh.framework.web.dao.DaoHelper;
import cn.wlh.framework.web.entity.Context;

public class TestAopLog {
	public void txn1234(Context context) {
		printLoge(context);
	}
	public void txn12345(Context context) {
		try {
			DaoHelper.update("delete from z_fa_wjsp_scdjlb where rownum = ?", context.get("num"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private String[] printLoge(Context context) {
		System.out.println(11111);
		System.out.println(context);
		return new String[] {"1","2"};
	}
}
