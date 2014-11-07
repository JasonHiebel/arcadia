package arcadia.util;

import java.util.*;

public class SelectionList<E> extends AbstractList<E> {
	private List<E> backing;
	private Set<ArrayListSelection> selections;
	
	public SelectionList() {
		this(10);
	}
	
	public SelectionList(Collection<? extends E> c) {
		this(2 * c.size());
		this.backing.addAll(c);
	}
	
	public SelectionList(int initialCapacity) {
		this.backing = new ArrayList<E>(initialCapacity);
		this.selections = Collections.newSetFromMap(new WeakHashMap<ArrayListSelection, Boolean>());
	}
	
	
	@Override
	public void add(int index, E element) {
		backing.add(index, element);
		for(ArrayListSelection selection : selections) { selection.added(index); }
	}
	
	@Override
	public E remove(int index) {
		E ret = backing.remove(index);
		for(ArrayListSelection selection : selections) { selection.removed(index); }
		return ret;
	}
	
	@Override
	public E get(int index) {
		return backing.get(index);
	}
	
	@Override
	public E set(int index, E element) {
		return backing.set(index, element);
	}
	
	@Override
	public int size() {
		return backing.size();
	}
	
	public Selection<E> selection() {
		return selection(0);
	}
	
	public Selection<E> selection(int index) {
		return new ArrayListSelection(index);
	}
	
	private class ArrayListSelection implements Selection<E> {
		private int index;
	
		public ArrayListSelection(int index) {
			this.index = index;
		}
	
		@Override
		public E current() {
			if(isEmpty()) { return null; }
			return backing.get(index);
		}
	
		@Override
		public boolean hasNext() {
			return index < size() - 1;
		}
		
		@Override
		public E next() {
			return hasNext() ? backing.get(++index) : null;
		}
		
		@Override
		public boolean hasPrevious() {
			return index > 0;
		}
	
		public E previous() {
			return hasPrevious() ? backing.get(--index) : null;
		}

		public Selection<E> copy() {
			return selection(index);
		}
		
		private void added(int i) {
			if(index <= i) index += 1;
		}
		
		private void removed(int i) {
			if(index == i && index == size() || index < i) { index -= 1; }
		}
	}
}