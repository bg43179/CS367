///////////////////////////////////////////////////////////////////////////////
//
// Main Class File:  CacheImageApp.java
// File:             PacketLinkedList.java
// Semester:         CS367, Fall 2016
//
// Author:           Yahn-Chung Chen, chen666@wisc.edu
// CS Login:         yahn-chung
// Lecturer's Name:  Deb Deppeler
//
///////////////////////////////////////////////////////////////////////////////

/**
 * A Single-linked linkedlist with a "dumb" header node (no data in the node),
 * but without a tail node. It implements ListADT&lt;E&gt; and returns
 * PacketLinkedListIterator when requiring a iterator.
 * 
 * @author honghui
 */
public class PacketLinkedList<E> implements ListADT<E> {
	
	private Listnode<E> head;
	private int numItem;

	/**
	 * Constructs a empty PacketLinkedList
	 */
	public PacketLinkedList() {
		head = new Listnode<E>(null);
		numItem = 0;
	}

	@Override
	public void add(E item) {
		Listnode<E> curr = head;
		Listnode<E> newnode = new Listnode<E>(item);
		while(curr.getNext() != null ) {
			curr = curr.getNext();
		}		
		curr.setNext(newnode);
		numItem++;
	}

	@Override
	public void add(int pos, E item) {
		Listnode<E> curr = head;
		Listnode<E> newnode = new Listnode<E>(item);
		//pos need to be positive and smaller than total amount of Listnode 
		if ( pos < 0 || pos >= numItem) {
			throw new IndexOutOfBoundsException();
		}
		//get the n-1 Listnode
		for (int p = 0; p < pos ; p++) {
			curr = curr.getNext();
		}
		newnode.setNext(curr.getNext());
		curr.setNext(newnode);

		numItem++;
	}

	@Override
	public boolean contains(E item) {
		Listnode<E> curr = head;
		//traverse the chain of nodes to find if there are any data match item
		while (curr.getNext() != null) {
			curr = curr.getNext();
			if (curr.getData().equals(item)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public E get(int pos) {
		Listnode<E> curr = head;
		//pos need to be positive and smaller than total amount of Listnode 
		if ( pos < 0 || pos >= numItem) {
			throw new IndexOutOfBoundsException();
		}
		//get the n Listnode
		for (int p = 0; p < pos+1 ; p++) {
			curr = curr.getNext();
		}
		return curr.getData();
	}

	@Override
	public boolean isEmpty() {
		return numItem == 0 ;
	}

	@Override
	public E remove(int pos) {
		Listnode<E> curr = head;
		E item = (E) new Object();
		//pos need to be positive and smaller than total amount of Listnode 
		if ( pos < 0 || pos >= numItem) {
			throw new IndexOutOfBoundsException();
		}
		//get the n-1 Listnode
		for (int p = 0; p < pos ; p++) {
			curr = curr.getNext();
		}
		item = curr.getNext().getData();
		curr.setNext(curr.getNext().getNext());
		numItem--;
		return item;
	}

	@Override
	public int size() {
		return numItem;
	}

	@Override
	public PacketLinkedListIterator<E> iterator() {
		return new PacketLinkedListIterator<E>(head.getNext());
	}

}
