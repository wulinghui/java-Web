package cn.wlh.util.base.adapter.dbutils.apache;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 吴灵辉
 * 具体原因@see super<br/>
把自定义的规则,解析成sql。<br/>
 * insert into users(username,password,email,birthday)  values(:name,:pw,:email,:birth)<br/>
 * 
 * [name, pw ,email, birth, <br/>
 * insert into users(username,password,email,birthday)  values(?,?,?,?) ]<br/>
 * 
 */
public class PreparedNamedOfCache extends PreparedOfCache {
	protected PreparedNamedOfCache(char c) {
		setStartChar(new char[] {
				c //':'	
		});
	}
	
	@Override
	public CachePreparedMap getParameter(String sql) {
		char[] strChar = sql.toCharArray();
		int length = strChar.length;
		int endLength = 5; 
		List<Character> chars = new ArrayList<Character>();
		Object[] array ; 
		String str;
//		List<String> strs = new ArrayList<>();
		StringBuilder sb = new StringBuilder();
		int indexOf = 1; // 对应索引的位置
		CachePreparedMap preparedMap = new CachePreparedMap();
		//循环解析char
		for (int i = endLength ; i < length; i++) { //udpate delete 都在5个之上
			char c = strChar[i];
			if( flags[0] ) {//有: 
				if( lookUp(c , fileterChar) ) { // 有 :sd  where  的 sd  where空格了,
					sb.append("? ");//把里面换成? 了
					flags[0] = false;
					array = chars.toArray();
					str = String.valueOf(array);
					// 放入并自增..
					preparedMap.putOneOfAutoAdd( str, indexOf++ );
					chars = new ArrayList<Character>();
				}else{ 
					chars.add(c);
				}
			}else if( c == startChar[0] ) {
				 flags[0]  = true; // 有: ，不添加到sb里面去。
			}else {//添加到sb里面去。
				sb.append(c);
			}
		}
		preparedMap.setFinalSql( sb.toString() );
		preparedMap.saveAllKeyAndValue();
		return preparedMap;
	}
}
