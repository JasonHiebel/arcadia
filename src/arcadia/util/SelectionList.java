package arcadia.util;

import java.util.Collection;
import java.util.ArrayList;

public class SelectionList<E> extends ArrayList<E> {
	private int current;
	
	public SelectionList() {
		current = 0;
	}
	
	public SelectionList(Collection<? extends E> c) {
		super(c);
		current = 0;
	}
	
	public SelectionList(int initialCapacity) {
		super(initialCapacity);
		current = 0;
	}
	
	@Override
	public void add(int index, E element) {
		super.add(index, element);
		if(index <= current) { next(); }
	}

	public int currentIndex() {
		return current;
	}

	public E current() {
		return get(current);
	}
	
	public boolean firstSelected() {
		return current == 0;
	}
	
	public boolean lastSelected() {
		return current == size() - 1;
	}

	public boolean previous() {
		if(firstSelected()) { return false; }
		current = Math.max(0, Math.min(size() - 1, current - 1));
		return true;
	}

	public boolean next() {
		if(lastSelected()) { return false; }
		current = Math.max(0, Math.min(size() - 1, current + 1));
		return true;
	}
}