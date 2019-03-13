package cn.wlh.util.base;

/**
 * @author 吴灵辉
 * 通过日志的形式记录new对象的过程。
 */
public class RecordNewOfLog {
	public static final Logger RECORD_NEW_OBJECT_OF_LOG = Logger.getLogger(Logger.class);
	/**子类重写toString，就行了。
	 * @param flag
	 */
	public RecordNewOfLog(char flag) {
			RECORD_NEW_OBJECT_OF_LOG.log(flag, "new ObjectClass=" + this.getClass() + "\nnew ObjectInner" + this.toString());
	}
}
