package cn.wlh.util.extend.converter;

/**
 * @author �����
 * �հ�������..ֻ��Class.from("...");����..
 */
class StringToLong  extends RegiStorOfConverter<String,Long>{
	 static {
		 //ע��һ��
		 new StringToLong();
	 }
	@Override
	public Long toConverter(String src) {
		return Long.parseLong(src);
	}
 }
