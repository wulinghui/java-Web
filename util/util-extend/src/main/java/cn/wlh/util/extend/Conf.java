package cn.wlh.util.extend;

import cn.wlh.util.base._Class;
import cn.wlh.util.base._File;
  
/**   
 * @author wlh   
 * 有默认的,子类可以复写,也可以不复写.
 */
public abstract class Conf {
	public static  Conf config ;
	public Conf() { config = this;	}
	static {
		try {
			config =  (Conf) _Class.newObj(  _Class.forName("com.novalue.Conf" ) );
		} catch (Exception e) {
			config = new Conf() {};
		}
	}
	
	public String complierClient_STORE_PATH() {return _File.joinPath(cn.wlh.util.base.Config.CONF[0][4], "complier", "entity");}
}
