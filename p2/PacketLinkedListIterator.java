///////////////////////////////////////////////////////////////////////////////
//
// Main Class File:  CacheImageApp.java
// File:             PacketLinkedListIterator.java
// Semester:         CS367, Fall 2016
//
// Author:           Yahn-Chung Chen, chen666@wisc.edu
// CS Login:         yahn-chung
// Lecturer's Name:  Deb Deppeler
//
///////////////////////////////////////////////////////////////////////////////

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * The iterator implementation for PacketLinkedList.
 * 
 * @author honghui
 */
public class PacketLinkedListIterator<T> implements Iterator<T> {
	private Listnode<T> curr;

	/**
	 * Constructs a PacketLinkedListIterator by passing a head node of
	 * PacketLinkedList.
	 * 
	 * @param head
	 *            the head node of PacketLinkedList.
	 */
	public PacketLinkedListIterator(Listnode<T> head) {
		curr = head;
	}

	/**
	 * Returns the next element in the iteration.
	 * 
	 * @return the next element in the iteration
	 * @throws NoSuchElementException
	 *             if the iteration has no more elements
	 */
	@Override
	public T next() {
		if (curr == null){
			throw new NoSuchElementException();
		} 
		T item = (T) new Object();
		item = curr.getData();
		curr = curr.getNext();
		return item;
	}

	/**
	 * Returns true if the iteration has more elements.
	 * 
	 * @return true if the iteration has more elements
	 */
	@Override
	public boolean hasNext() {
		return curr != null;
	}

	/**
	 * The remove operation is not supported by this iterator
	 * 
	 * @throws UnsupportedOperationException
	 *             if the remove operation is not supported by this iterator
	 */
	@Override
	public void remove() {
		throw new UnsupportedOperationException();
	}

}
