package springboot.com.wlh.dao;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.stereotype.Component;

import com.novalue.Util;

import dao.sql.Dao;
import dao.sql.Word;
@Component
public class OracleDao extends Dao{
	public OracleDao() {
		super("oracle-first");
	}
	@Override
	protected Object getDaoInner(Entry<Class<?>, Map<String, Method>> map, String method, Word word)
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		//获得Spring Bean; 
		Object obj = null;
		try {
			obj = Util.SpringBean.getBean(map.getKey());
		}catch(Throwable e) {
			//没有找到.报错	
			System.out.println( e.getMessage() );
		}
		//反射Bean..
		if( obj != null ) map.getValue().get(method).invoke( obj,  word );
		
		//否则就反射 静态方法..
		return super.getDaoInner(map, method, word);
	}
}
