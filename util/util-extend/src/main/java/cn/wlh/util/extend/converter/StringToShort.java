package cn.wlh.util.extend.converter;

/**
 * @author �����
 * �հ�������..ֻ��Class.from("...");����..
 */
class StringToShort  extends RegiStorOfConverter<String,Short>{
	 static {
		 //ע��һ��
		 new StringToShort();
	 }
	@Override
	public Short toConverter(String src) {
		return Short.parseShort(src);
	}
 }
