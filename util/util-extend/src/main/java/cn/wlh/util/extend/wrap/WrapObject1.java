package cn.wlh.util.extend.wrap;

public class WrapObject1<K> {
	K source;

	public WrapObject1(K source) {
		super();
		this.source = source;
	}

	public K getSource() {
		return source;
	}

	protected void setSource(K source) {
		this.source = source;
	}
	
}
