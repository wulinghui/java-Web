package cn.wlh.util.extend.converter;

/**
 * @author 吴灵辉
 * 闭包不可用..只能Class.from("...");加载..
 */
class StringToChar  extends RegiStorOfConverter<String,Character>{
	 static {
		 //注册一次
		 new StringToChar();
	 }
	@Override
	public Character toConverter(String src) {
		return src.charAt(0);
	}
//	public static void main(String[] args) throws ClassNotFoundException {
//	Class.forName("cn.wlh.util.extend.converter.StringToChar");
//		
//	Character executConverter = AbstarctConverter.executConverter("\n", Character.class);
//		System.out.println( executConverter  + "vexdfs");
//		System.out.println( AbstarctConverter.executConverter("（", Character.class)  + "vexdfs");
//	}
 }
