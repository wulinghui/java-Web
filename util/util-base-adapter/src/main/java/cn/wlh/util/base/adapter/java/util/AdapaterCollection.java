package cn.wlh.util.base.adapter.java.util;

import java.util.Collection;
import java.util.Iterator;

import cn.wlh.util.extend.wrap.WrapObject1;

public class AdapaterCollection<E> extends WrapObject1<Collection<E>> implements Collection<E> {

	public AdapaterCollection(Collection<E> source) {
		super(source);
	}

	@Override
	public int size() {
		return super.getSource().size();
	}

	@Override
	public boolean isEmpty() {
		return super.getSource().isEmpty();
	}

	@Override
	public boolean contains(Object o) {
		return super.getSource().contains(o);
	}

	@Override
	public Iterator<E> iterator() {
		return super.getSource().iterator();
	}

	@Override
	public Object[] toArray() {
		return super.getSource().toArray();
	}

	@Override
	public <T> T[] toArray(T[] a) {
		return super.getSource().toArray(a);
	}

	@Override
	public boolean add(E e) {
		return super.getSource().add(e);
	}

	@Override
	public boolean remove(Object o) {
		return super.getSource().remove(o);
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		return super.getSource().containsAll(c);
	}

	@Override
	public boolean addAll(Collection<? extends E> c) {
		return super.getSource().addAll(c);
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		return super.getSource().removeAll(c);
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		return super.getSource().retainAll(c);
	}

	@Override
	public void clear() {
		super.getSource().clear();
	}
	
}
