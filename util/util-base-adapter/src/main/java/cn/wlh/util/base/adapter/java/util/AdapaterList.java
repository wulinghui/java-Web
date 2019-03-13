package cn.wlh.util.base.adapter.java.util;

import java.util.Collection;
import java.util.List;
import java.util.ListIterator;

public class AdapaterList<E> extends AdapaterCollection<E> implements List<E>{

	public AdapaterList(List<E> source) {
		super(source);
	}
	@Override
	public List<E> getSource() {
		return (List<E>) super.getSource();
	}
	@Override
	public boolean addAll(int index, Collection<? extends E> c) {
		return this.getSource().addAll(c);
	}

	@Override
	public E get(int index) {
		return this.getSource().get(index);
	}

	@Override
	public E set(int index, E element) {
		return this.getSource().set(index, element);
	}

	@Override
	public void add(int index, E element) {
		this.getSource().add(index, element);
	}

	@Override
	public E remove(int index) {
		return this.getSource().remove(index);
	}

	@Override
	public int indexOf(Object o) {
		return this.getSource().indexOf(o);
	}

	@Override
	public int lastIndexOf(Object o) {
		return this.getSource().lastIndexOf(o);
	}

	@Override
	public ListIterator<E> listIterator() {
		return this.getSource().listIterator()
				;
	}

	@Override
	public ListIterator<E> listIterator(int index) {
		return this.getSource().listIterator(index);
	}

	@Override
	public List<E> subList(int fromIndex, int toIndex) {
		return this.getSource().subList(fromIndex, toIndex);
	}
	
}
