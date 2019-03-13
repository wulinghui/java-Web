package cn.wlh.util.extend.converter;

/**
 * @author 吴灵辉
 * 闭包不可用..只能Class.from("...");加载..
 */
class StringToShort  extends RegiStorOfConverter<String,Short>{
	 static {
		 //注册一次
		 new StringToShort();
	 }
	@Override
	public Short toConverter(String src) {
		return Short.parseShort(src);
	}
 }
