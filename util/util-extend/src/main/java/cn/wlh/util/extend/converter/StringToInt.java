package cn.wlh.util.extend.converter;

/**
 * @author �����
 * �հ�������..ֻ��Class.from("...");����..
 */
class StringToInt  extends RegiStorOfConverter<String,Integer>{
	 static {
		 //ע��һ��
		 new StringToInt();
	 }
	@Override
	public Integer toConverter(String src) {
		return Integer.parseInt(src);
	}
 }
