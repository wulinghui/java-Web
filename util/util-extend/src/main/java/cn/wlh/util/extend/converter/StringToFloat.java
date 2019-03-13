package cn.wlh.util.extend.converter;

/**
 * @author 吴灵辉
 * 闭包不可用..只能Class.from("...");加载..
 */
class StringToFloat  extends RegiStorOfConverter<String,Float>{
	 static {
		 //注册一次
		 new StringToFloat();
	 }
	@Override
	public Float toConverter(String src) {
		return Float.parseFloat(src);
	}
 }
