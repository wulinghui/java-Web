package springboot.com.wlh.biz.mdj;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.novalue.Util;

import dao.sql.Word;
import springboot.com.wlh.dao.OracleDao;
import springboot.com.wlh.dao.OracleDaoOfJPA;
import springboot.com.wlh.dao.mdj.Z_FA_WJSP_SCZTB;
import util.extend.complier1.java.ComplierClient;

@Component
//@Webservice
public class Test {   
	@Autowired  
	OracleDaoOfJPA dao;
	//测试类...
	public Object txn00001(Word word) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, SQLException {//必须是 txn---(Word word) return可以随意但不能为null
		System.out.println("===============");
//		word.putDao_Object( new Z_FA_WJSP_SCZTB() );
//		word.putRs(true);
		new OracleDao().execute("Z_FA_WJSP_SCZTB", "select", word, "out");  
//		new OracleDaoOfJPA().execute("Z_FA_WJSP_SCZTB", "select", word, "out");//没有bean
		//这样用.. 
		Util.SpringBean.getBean(OracleDaoOfJPA.class).execute("Z_FA_WJSP_SCZTB", "select", word, "out");
		dao.execute("Z_FA_WJSP_SCZTB", "select", word, "out");
		return "ffda";         
	}
	public Object txn00002(Word word) throws ClassNotFoundException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, InstantiationException, IOException, Throwable {
		ComplierClient.main(null);
		Class c = Class.forName("springboot.com.wlh.dao.mdj.ABC");    
		Method me = c.getMethod("findUser", Word.class);
		System.out.println( me.invoke( c.newInstance(), new Word() ) );
		
		return me.invoke( c.newInstance(), new Word() );   
	}
}     
