package cn.wlh.util.extend.converter;

/**
 * @author 吴灵辉
 * 闭包不可用..只能Class.from("...");加载..
 */
class StringToDouble  extends RegiStorOfConverter<String,Double>{
	 static {
		 //注册一次
		 new StringToDouble();
	 }
	@Override
	public Double toConverter(String src) {
		return Double.parseDouble(src);
	}
 }
