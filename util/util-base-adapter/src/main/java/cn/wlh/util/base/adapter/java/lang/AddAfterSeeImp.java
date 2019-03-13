package cn.wlh.util.base.adapter.java.lang;

import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

import cn.wlh.util.base.adapter.java.util.AddAfterSeeListInterface;

/**
 * @author 吴灵辉
 *有一个应用场景：
  当add完了之后，我确定不再需要add了，接下来就只是查询或者修改了。这时候用定长的数组就行了
 * @param <E>
 */
public class AddAfterSeeImp<E> implements  AddAfterSeeListInterface<E> {
	List<E> list;
	boolean isSave;
	Object [] array;
	
	public AddAfterSeeImp(List<E> list) {
		super();
		this.list = list;
	}
	public AddAfterSeeImp() {
		this(new LinkedList<E>());
	}
	@Override
	public boolean isSave() {
		return isSave;
	}
	@Override
	public void save() {
		this.isSave = true;
//		this.array = new Object[ this.size() ] ;
		this.array = list.toArray();
		list = null; //之后的操作自然报null
	}
	
	public  void forEach(Consumer<? super E> action) {
		list.forEach(action);
	}
	public int size() {
		if( isSave ) return array.length;
		return list.size();
	}
	public boolean isEmpty() {
		return list.isEmpty();
	}
	public boolean contains(Object o) {
		return list.contains(o);
	}
	public Iterator<E> iterator() {
		if( isSave ) return new Itr();
		return list.iterator();
	}
	public Object[] toArray() {
		return list.toArray();
	}
	public <T> T[] toArray(T[] a) {
		return list.toArray(a);
	}
	public boolean add(E e) {
		return list.add(e);
	}
	public boolean remove(Object o) {
		return list.remove(o);
	}
	public boolean containsAll(Collection<?> c) {
		return list.containsAll(c);
	}
	public boolean addAll(Collection<? extends E> c) {
		return list.addAll(c);
	}
	public boolean addAll(int index, Collection<? extends E> c) {
		return list.addAll(index, c);
	}
	public boolean removeAll(Collection<?> c) {
		return list.removeAll(c);
	}
	public boolean retainAll(Collection<?> c) {
		return list.retainAll(c);
	}
	public  void replaceAll(UnaryOperator<E> operator) {
		list.replaceAll(operator);
	}
	public  boolean removeIf(Predicate<? super E> filter) {
		return list.removeIf(filter);
	}
	public  void sort(Comparator<? super E> c) {
		list.sort(c);
	}
	public void clear() {
		list.clear();
	}
	public boolean equals(Object o) {
		return list.equals(o);
	}
	public int hashCode() {
		return list.hashCode();
	}
	public E get(int index) {
		if( isSave ) return (E) this.array[index];
		return list.get(index);
	}
	/**
	 */
	public E set(int index, E element) {
		if( isSave ) {
			E e = (E) this.array[index];
			this.array[index] = element;
			return e;
		}
		return list.set(index, element);
	}
	public void add(int index, E element) {
		list.add(index, element);
	}
	public  Stream<E> stream() {
		return list.stream();
	}
	public E remove(int index) {
		return list.remove(index);
	}
	public  Stream<E> parallelStream() {
		return list.parallelStream();
	}
	public int indexOf(Object o) {
		return list.indexOf(o);
	}
	public int lastIndexOf(Object o) {
		return list.lastIndexOf(o);
	}
	public ListIterator<E> listIterator() {
		return list.listIterator();
	}
	public ListIterator<E> listIterator(int index) {
		return list.listIterator(index);
	}
	public List<E> subList(int fromIndex, int toIndex) {
		return list.subList(fromIndex, toIndex);
	}
	public  Spliterator<E> spliterator() {
		return list.spliterator();
	}
	
	
	
	public class Itr implements Iterator<E> {
        /**
         * Index of element to be returned by subsequent call to next.
         */
        int cursor = 0;

        /**
         * Index of element returned by most recent call to next or
         * previous.  Reset to -1 if this element is deleted by a call
         * to remove.
         */
        int lastRet = -1;
		
		@Override
		public boolean hasNext() {
			return cursor != size();
		}

		@Override
		public E next() {
            try {
                int i = cursor;
                E next = get(i);
                lastRet = i;
                cursor = i + 1;
                return next;
            } catch (IndexOutOfBoundsException e) {
              throw new NoSuchElementException();
            }
		}
	}
}
