package cn.wlh.util.extend.converter;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author �����
 * �հ�������..ֻ��Class.from("...");����..
 */
class DateToString  extends RegiStorOfConverter<Date,String>{

	@Override
	public String toConverter(Date src) {
		return toConverter(src,null);
	}

	@Override
	public String toConverter(Date obj, Object... objects) {
		SimpleDateFormat simpleDateFormat = StringToDate.getSimpleDateFormat(objects);
		return simpleDateFormat.format(obj);
	}
	
 }
