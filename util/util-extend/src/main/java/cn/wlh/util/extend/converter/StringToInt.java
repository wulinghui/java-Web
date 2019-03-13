package cn.wlh.util.extend.converter;

/**
 * @author 吴灵辉
 * 闭包不可用..只能Class.from("...");加载..
 */
class StringToInt  extends RegiStorOfConverter<String,Integer>{
	 static {
		 //注册一次
		 new StringToInt();
	 }
	@Override
	public Integer toConverter(String src) {
		return Integer.parseInt(src);
	}
 }
