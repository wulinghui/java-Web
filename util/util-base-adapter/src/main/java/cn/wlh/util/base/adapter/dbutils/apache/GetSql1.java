package cn.wlh.util.base.adapter.dbutils.apache;

/**
 * @author 吴灵辉
 * 对象变方法名.
 */@Deprecated
public class GetSql1 {
	/**
	 * @param keys
	 * @return  select aa,xx,id from 
	 */
	public StringBuilder select(String... keys) {
		int len= keys.length , i=0;
		StringBuilder sb = new StringBuilder("SELECT");
		sb.append(' ').append(keys[i++]);
		for (; i < len; i++) {
			sb.append(',').append(keys[i]);
		}
		sb.append("FROM ");
		return sb;
	}
	// 
			/**
			 * @param table	users
			 * @param keys  username,password,email,birthday
			 * @return  insert into users(username,password,email,birthday) values(?,?,?,?)
			 */
			public StringBuilder insert(String table,String... keys) {
				int len= keys.length , i=0;
				StringBuilder sb = new StringBuilder("INSERT INTO");
				sb.append(' ').append(table);
				sb.append('(').append(keys[i++]);
				for (; i < len; i++) {
					sb.append(',').append(keys[i]);
				}
				sb.append(") VALUES (").append('?');
				for (int j = 1; j < len; j++) {
					sb.append(',').append('?');
				}
				sb.append(')');
				return sb;
			}
			public StringBuilder update(String table,String... keys) {
				StringBuilder sb = new StringBuilder("INSERT INTO");
				return sb;
			}
}
