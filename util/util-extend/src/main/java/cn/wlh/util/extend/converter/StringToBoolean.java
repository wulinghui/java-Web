package cn.wlh.util.extend.converter;

/**
 * @author 吴灵辉
 * 闭包不可用..只能Class.from("...");加载..
 */
class StringToBoolean  extends RegiStorOfConverter<String,Boolean>{
	 static {
		 //注册一次
		 new StringToBoolean();
	 }
	@Override
	public Boolean toConverter(String src) {
		return Boolean.parseBoolean(src);
	}
 }
