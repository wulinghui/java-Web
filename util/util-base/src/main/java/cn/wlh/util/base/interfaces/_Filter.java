package cn.wlh.util.base.interfaces;

/**
 * @author wlh
 * 所有自己需要定义过滤接口的父类
 * @param <T>
 */
public interface _Filter<T>{
	_Filter NO_FILTER = new _Filter(){
		/**
		 * @param t
		 * @return false --过滤掉
		 */
		@Override
		public boolean accept(Object t) {
			return true;
		}
	};
	_Filter All_FILTER = new _Filter(){
		/**
		 * @param t
		 * @return false --过滤掉
		 */
		@Override
		public boolean accept(Object t) {
			return false;
		}
	};
	boolean accept(T t);
}
