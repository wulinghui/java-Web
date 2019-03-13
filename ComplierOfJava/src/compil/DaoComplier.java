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

/** ��ͬ���ݿ�Ĳ�ͬ�ı��뷽ʽ
 * @author wlh
 */
public abstract class DaoComplier extends OprJavaSb{


	public static Map<String,DaoComplier> mapDao = new HashMap<String,DaoComplier>();
	public static Map<String,String> dataDictionary = new HashMap<String, String>();
	static{
		//ע������ʵ��
		mapDao.put("ORACLE", new ComOracle());
		//ע�������ֵ�:
		String [] dictionary = new String[]{
				 "#S","SELECT"//��ѯ
				,"#D","DELETE"//ɾ��    
				,"#I","UPDATE"//����
				,"##","*"//����*��,
				
		};
//		dataDictionary.put("#s", "SELECT");//��ѯ;
		//������������,�ŵ������ֵ�����
		int len = dictionary.length;
		for (int i = 0; i < len; i+=2) {
			dataDictionary.put(dictionary[i],dictionary[i+1]);
		}
	}
	//��ѯ���е�U-FINDUSER  =>   SELECT  FROM USERZ;LIST LI = NEW ARRAYLIST()
	public void builder(String sqlz,StringBuffer sb){
		String d_s [] = sqlz.split("-");
		new OprJavaSb( sb ). appendNote(  d_s[0] );//	׷��ע��
//		appendAnno(sb, "");//׷��ע��Annotation --����ÿ����ܶ���һ��
//		String m_s [] = d_s[1].split("=>");//������
//		appendMethod(sb, "Word " + m_s[0] + "(Word word");// ׷�ӷ���-����
		//�������ദ��
		handleSql(d_s[1],sb);
		sb.append('}');//����
	}
	public abstract void handleSql(String sqlz,StringBuffer sb);
	
	/** Oracle�ı���..
	 * @author wlh
	 */
	public static class ComOracle extends DaoComplier{
		String strNewSb = "StringBuilder sb = new StringBuilder();";
		static ComOracle [] pluns = new ComOracle[] { 
				new ComOracle()
		};
		//FINDUSER  =>   SELECT  FROM USERZ where :shengqinghao:; LIST LI = NEW ARRAYLIST()
		public void handleSql(String sqlz,StringBuffer sb) {
			String m_s[] = sqlz.split("=>|;");//�ѷ���������Ҫ�����sql��bizʹ�õ�ָ��   �ָ�.
			//�������Sub��Ϣ,�������������Ϣ...
			getAllSubAppoint gasa =  new JavaComplier.getAllSubAppoint();
			JavaComplier.handleAllAppoint(new StringBuffer(m_s[1]), 0, gasa , ":",":");
			// ׷�ӷ���
			//public SqlStatement getWjjlId(TxnContext context, DataBus db) throws TxnException {
			this.setSb(sb).appendMethod( "SqlStatement " + m_s[0] + "(TxnContext context, DataBus db) throws TxnException");
//			//��������-�������
//			StringBuffer vars = stateVar(gasa.list);
//			//����setȥ��
//			Set<SubAppoint> set = new HashSet<SubAppoint>();
//			set.addAll(gasa.list);
//			//���varList����
//			List<SubAppoint> varList = new ArrayList<SubAppoint>();
//			varList.addAll( gasa.list );//������е�list
//			varList.removeAll( set );//  ��list��ȥ��set���   --���list���滹����?--��Ԫģʽ+��ʶ          
			//�滻-��Ӧ���ַ���.
//			if(vars != null){//�о͡�������Ͳ����ж���
//				this.appendInner( vars.toString() );//�Ȱѱ�����ӽ�ȥ
//				//���sql���
////				String sql = repalice(gasa.list,m_s[1]);
//				this.appendInner( sql );
//			}
			//TODO ��̬���Ͳ�����.��ֻ�����������//������Բ�������ģʽ.��������ҪDI
			StringBuffer var = new StringBuffer();
			String sql = m_s[1];
			for (int i = 0; i < pluns.length; i++) {
				sql = pluns[i].varAndSql( gasa.list, var, sql);
			}
			this.appendInner( var.toString() );
			this.appendInner( sql );
			
			//��ӽ�β.
			this.appendInner("SqlStatement stmt = new SqlStatement();"+
							 "stmt.addSqlStmt(sql.toString());"+
							 "return stmt;");
		}
		
		/**
		 * //������
		//��������-�������
		 * �����ṩ���.....������д
		 * @param list	-- ���е�:ffsd: ������Ϣ..    --��ò�Ҫ����list,����֮��Ĳ������ܵ�Ӱ��
		 * @param var	-- var���� 	-- ��֮����Ӱ�� 
		 * @param sql	-- sql���	-- ��֮����Ӱ��
		 * @return sql���	-- ��֮����Ӱ��
		 *///����ֻ�Ǽ򵥵İ�:shen: -> �滻�� shen 
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
					}else{//����1�Ͱ������ shenqingh ���� db.getValue('shenqingh') 
//						string.inner = "db.getValue('" + string.inner + "')"; 
						sql = sql.replaceAll( ":"+ string.inner+":"  , "\"+ db.getValue('" + string.inner + "') +\"" );
					}
				}	
				sb.append( strNewSb );//����sb���
			}//û�з���null
			return sql;
		}
		protected String getSource(String inner ) {
			return ":"+ inner +":";
		}
		protected String getRepalce(String inner) {
			return StringUtil.insertVar(inner);
		}//util��
		@Deprecated //��varAndSql()
		public StringBuffer stateVar(List<SubAppoint> list){
//			//����setȥ��
//			Set<SubAppoint> set = new HashSet<SubAppoint>();
//			set.addAll(list);
			
			//����set,��������
			int len = list.size();
			if(list !=null && len != 0 ){
				StringBuffer sb = new StringBuffer();
				for (int i = 0; i < len ; i++) {
					SubAppoint string = list.get( i ); 
					if( string.getCount() != 1 ) {
						sb.append("String ").append ( string.inner )
						  .append( " = db.getValue('" ).append( string.inner )
						  .append("');");
					}else {//����1�Ͱ������ shenqingh ���� db.getValue('shenqingh') 
						string.inner = "db.getValue('" + string.inner + "')"; 
					}
				}	
				sb.append( strNewSb );//����sb���
				return sb;
			}//û�з���null
			return null;
		}
		//�滻�����:shenqingh:������  ----
//		public String repalice(List<SubAppoint> list,String sql){
//			StringBuffer sb = new StringBuffer(sql);
//			int len = list.size();
//			//��һ��:��λ��
//			int frist = list.get(0).start;
//			repaliceSql(sb, 0, frist, list.get(0).inner );
//			//�м��::��λ��
//			for (int i = 0; i < len; i++) {
//				SubAppoint sa = list.get(i);
//				repaliceSql(sb, sa.start , sa.end, sa.inner );
//			}
//			//���һ��:��λ��
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
