package cn.wlh.util.base.adapter.dbutils.apache;

/**
 * @author 吴灵辉
 * 获得不同语句的语句名.
 */
@Deprecated
public enum GetSql {
	SELECT ("SELECT"){
		@Override
		// select aa,xx,id from 
		public StringBuilder getSql(String[] keys) {
			int len= keys.length , i=0;
			StringBuilder sb = new StringBuilder(this.flag);
			sb.append(' ').append(keys[i++]);
			for (; i < len; i++) {
				sb.append(',').append(keys[i]);
			}
			sb.append("FROM ");
			return sb;
		}
	},UPDATE ("UPDATE"){
		@Override// insert into users(username,password,email,birthday) values(?,?,?,?)
		public StringBuilder getSql(String[] keys) {
			StringBuilder sb = new StringBuilder(this.flag);
			return sb;
		}
	},DELETE ("DELETE"){
		@Override
		public StringBuilder getSql(String[] keys) {
			StringBuilder sb = new StringBuilder(this.flag);
			return sb;
		}
	},INSERT ("INSERT"){
		@Override
		public StringBuilder getSql(String[] keys) {
			StringBuilder sb = new StringBuilder(this.flag);
			return sb;
		}
	};
	final String flag;

	private GetSql(String flag) {
		this.flag = flag;
	}
	public abstract StringBuilder getSql(String [] keys);
}
