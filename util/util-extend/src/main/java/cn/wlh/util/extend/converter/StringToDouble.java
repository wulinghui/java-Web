package cn.wlh.util.extend.converter;

/**
 * @author �����
 * �հ�������..ֻ��Class.from("...");����..
 */
class StringToDouble  extends RegiStorOfConverter<String,Double>{
	 static {
		 //ע��һ��
		 new StringToDouble();
	 }
	@Override
	public Double toConverter(String src) {
		return Double.parseDouble(src);
	}
 }
