package cn.wlh.util.extend.converter;

/**
 * @author �����
 * �հ�������..ֻ��Class.from("...");����..
 */
class StringToByte  extends RegiStorOfConverter<String,Byte>{
	 static {
		 //ע��һ��
		 new StringToByte();
	 }
	@Override
	public Byte toConverter(String src) {
		return Byte.parseByte(src);
	}
 }
