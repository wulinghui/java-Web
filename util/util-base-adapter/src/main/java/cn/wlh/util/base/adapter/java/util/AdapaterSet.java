package cn.wlh.util.base.adapter.java.util;

import java.util.Set;

public class AdapaterSet<E> extends AdapaterCollection<E> implements Set<E> {
	public AdapaterSet(Set<E> source) {
		super(source);
	}
	@Override
	public Set<E> getSource() {
		return (Set<E>) super.getSource();
	}
}
