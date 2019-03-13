package cn.wlh.util.extend.converter;

/**
 * @author 吴灵辉
 * 闭包不可用..只能Class.from("...");加载..
 */
class StringToLong  extends RegiStorOfConverter<String,Long>{
	 static {
		 //注册一次
		 new StringToLong();
	 }
	@Override
	public Long toConverter(String src) {
		return Long.parseLong(src);
	}
 }
