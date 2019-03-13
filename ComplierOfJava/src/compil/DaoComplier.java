package compil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import compil.JavaComplier.SubAppoint;
import compil.JavaComplier.getAllSubAppoint;
import util.string.StringUtil;

/** 不同数据库的不同的编译方式
 * @author wlh
 */
public abstract class DaoComplier extends OprJavaSb{


	public static Map<String,DaoComplier> mapDao = new HashMap<String,DaoComplier>();
	public static Map<String,String> dataDictionary = new HashMap<String, String>();
	static{
		//注册编译的实例
		mapDao.put("ORACLE", new ComOracle());
		//注册数据字典:
		String [] dictionary = new String[]{
				 "#S","SELECT"//查询
				,"#D","DELETE"//删除    
				,"#I","UPDATE"//更新
				,"##","*"//代替*号,
				
		};
//		dataDictionary.put("#s", "SELECT");//查询;
		//遍历上面数组,放到数据字典里面
		int len = dictionary.length;
		for (int i = 0; i < len; i+=2) {
			dataDictionary.put(dictionary[i],dictionary[i+1]);
		}
	}
	//查询所有的U-FINDUSER  =>   SELECT  FROM USERZ;LIST LI = NEW ARRAYLIST()
	public void builder(String sqlz,StringBuffer sb){
		String d_s [] = sqlz.split("-");
		new OprJavaSb( sb ). appendNote(  d_s[0] );//	追加注释
//		appendAnno(sb, "");//追加注解Annotation --下移每个框架都不一样
//		String m_s [] = d_s[1].split("=>");//方法名
//		appendMethod(sb, "Word " + m_s[0] + "(Word word");// 追加方法-下移
		//交个子类处理
		handleSql(d_s[1],sb);
		sb.append('}');//结束
	}
	public abstract void handleSql(String sqlz,StringBuffer sb);
	
	/** Oracle的编译..
	 * @author wlh
	 */
	public static class ComOracle extends DaoComplier{
		String strNewSb = "StringBuilder sb = new StringBuilder();";
		static ComOracle [] pluns = new ComOracle[] { 
				new ComOracle()
		};
		//FINDUSER  =>   SELECT  FROM USERZ where :shengqinghao:; LIST LI = NEW ARRAYLIST()
		public void handleSql(String sqlz,StringBuffer sb) {
			String m_s[] = sqlz.split("=>|;");//把方法名，需要处理的sql，biz使用的指令   分割.
			//获得所有Sub信息,并处理里面的信息...
			getAllSubAppoint gasa =  new JavaComplier.getAllSubAppoint();
			JavaComplier.handleAllAppoint(new StringBuffer(m_s[1]), 0, gasa , ":",":");
			// 追加方法
			//public SqlStatement getWjjlId(TxnContext context, DataBus db) throws TxnException {
			this.setSb(sb).appendMethod( "SqlStatement " + m_s[0] + "(TxnContext context, DataBus db) throws TxnException");
//			//申明变量-获得内容
//			StringBuffer vars = stateVar(gasa.list);
//			//放入set去重
//			Set<SubAppoint> set = new HashSet<SubAppoint>();
//			set.addAll(gasa.list);
//			//获得varList变量
//			List<SubAppoint> varList = new ArrayList<SubAppoint>();
//			varList.addAll( gasa.list );//获得所有的list
//			varList.removeAll( set );//  把list和去重set相减   --如果list里面还有呢?--享元模式+标识          
			//替换-相应的字符串.
//			if(vars != null){//有就。。下面就不用判断了
//				this.appendInner( vars.toString() );//先把变量添加进去
//				//添加sql语句
////				String sql = repalice(gasa.list,m_s[1]);
//				this.appendInner( sql );
//			}
			//TODO 动态语句和插件语句.和只申明多个变量//这里可以采用命令模式.但是又需要DI
			StringBuffer var = new StringBuffer();
			String sql = m_s[1];
			for (int i = 0; i < pluns.length; i++) {
				sql = pluns[i].varAndSql( gasa.list, var, sql);
			}
			this.appendInner( var.toString() );
			this.appendInner( sql );
			
			//添加结尾.
			this.appendInner("SqlStatement stmt = new SqlStatement();"+
							 "stmt.addSqlStmt(sql.toString());"+
							 "return stmt;");
		}
		
		/**
		 * //这里拆分
		//申明变量-获得内容
		 * 这里提供插件.....子类重写
		 * @param list	-- 所有的:ffsd: 内容信息..    --最好不要操作list,否则之后的操作将受到影响
		 * @param var	-- var变量 	-- 对之后有影响 
		 * @param sql	-- sql语句	-- 对之后无影响
		 * @return sql语句	-- 对之后有影响
		 *///该类只是简单的把:shen: -> 替换成 shen 
		public String varAndSql(List<SubAppoint> list,StringBuffer var,String sql) {
			int len = list.size();
			if(list !=null && len != 0 ){
				for (int i = 0; i < len ; i++) {
					SubAppoint string = list.get( i ); 
					if( string.getCount() != 1 ) {
						var.append("String ").append ( string.inner )
						  .append( " = db.getValue('" ).append( string.inner )
						  .append("');");
						sql = sql.replaceAll(":"+ string.inner+":" , "\"+" + string.inner + "+\"");
					}else{//等于1就把里面的 shenqingh 换成 db.getValue('shenqingh') 
//						string.inner = "db.getValue('" + string.inner + "')"; 
						sql = sql.replaceAll( ":"+ string.inner+":"  , "\"+ db.getValue('" + string.inner + "') +\"" );
					}
				}	
				sb.append( strNewSb );//最后把sb添加
			}//没有返回null
			return sql;
		}
		protected String getSource(String inner ) {
			return ":"+ inner +":";
		}
		protected String getRepalce(String inner) {
			return StringUtil.insertVar(inner);
		}//util类
		@Deprecated //用varAndSql()
		public StringBuffer stateVar(List<SubAppoint> list){
//			//放入set去重
//			Set<SubAppoint> set = new HashSet<SubAppoint>();
//			set.addAll(list);
			
			//遍历set,申明变量
			int len = list.size();
			if(list !=null && len != 0 ){
				StringBuffer sb = new StringBuffer();
				for (int i = 0; i < len ; i++) {
					SubAppoint string = list.get( i ); 
					if( string.getCount() != 1 ) {
						sb.append("String ").append ( string.inner )
						  .append( " = db.getValue('" ).append( string.inner )
						  .append("');");
					}else {//等于1就把里面的 shenqingh 换成 db.getValue('shenqingh') 
						string.inner = "db.getValue('" + string.inner + "')"; 
					}
				}	
				sb.append( strNewSb );//最后把sb添加
				return sb;
			}//没有返回null
			return null;
		}
		//替换里面的:shenqingh:等内容  ----
//		public String repalice(List<SubAppoint> list,String sql){
//			StringBuffer sb = new StringBuffer(sql);
//			int len = list.size();
//			//第一个:的位置
//			int frist = list.get(0).start;
//			repaliceSql(sb, 0, frist, list.get(0).inner );
//			//中间的::的位置
//			for (int i = 0; i < len; i++) {
//				SubAppoint sa = list.get(i);
//				repaliceSql(sb, sa.start , sa.end, sa.inner );
//			}
//			//最后一个:的位置
//			int last = list.get(len-1).end;
//			repaliceSql(sb, last, sb.length()-1, list.get(len-1).inner);
//			return sb.toString();
//		}
//		private void repaliceSql(StringBuffer sb,int start,int end,String sql){
//			String arg2 = "sb.append(" + sql +");";
//			sb.replace(start, end, arg2);
//		}
	}
	
	
	
	
	public static void main(String[] args) {
		System.out.println("========");
		String[] s = "FINDUSER  =>   SELECT  FROM USERZ;LIST LI = NEW ARRAYLIST()".split("=>|;");
		for (String string : s) {
			System.out.println(string);
		}
	}
}
