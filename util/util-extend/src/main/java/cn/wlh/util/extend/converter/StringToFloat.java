package cn.wlh.util.extend.converter;

/**
 * @author �����
 * �հ�������..ֻ��Class.from("...");����..
 */
class StringToFloat  extends RegiStorOfConverter<String,Float>{
	 static {
		 //ע��һ��
		 new StringToFloat();
	 }
	@Override
	public Float toConverter(String src) {
		return Float.parseFloat(src);
	}
 }
