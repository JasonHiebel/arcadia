package arcadia.util;

/**
 *
 * @author Jason Hiebel
 **/
public interface Selection<E> {
	/**
	 *
	 **/
	public E current();
	
	/**
	 *
	 **/
	public boolean hasNext();
	
	/**
	 *
	 **/
	public E next();
	
	/**
	 *
	 **/
	public boolean hasPrevious();
	
	/**
	 *
	 **/
	public E previous();
	
	/**
	 *
	 **/
	public Selection<E> copy();
}