package cn.wlh.framework.view;

public abstract class ViewFacatory{
	private ViewFacatory(){}
	private static ViewInterface viewInterface;
	public static ViewInterface getInstance() {
		return viewInterface;
	}
	/**
	 * @param viewInterface the viewInterface to set
	 */
	public static void setViewInterface(ViewInterface viewInterface) {
		ViewFacatory.viewInterface = viewInterface;
	}
}
