///////////////////////////////////////////////////////////////////////////////
//
// Main Class File:  MovieDbMain.java
// File:             IndexTreeIterator.java
// Semester:         CS367, Fall 2016
//
// Author:           Yahn-Chung Chen, chen666@wisc.edu
// CS Login:         yahn-chung
// Lecturer's Name:  Deb Deppeler
//
///////////////////////////////////////////////////////////////////////////////

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Stack;

/**
 * The Iterator for IndexTree that is built using Java's Stack class. This
 * iterator steps through the IndexTree using an INORDER traversal.
 *
 * @author apul
 */
public class IndexTreeIterator<K extends Comparable<K>, V> implements
	Iterator<IndexTreeNode<K, V>> {

	// Stack to track where the iterator is in the tree.
	Stack<IndexTreeNode<K, V>> stack;

	/**
	 * Constructs the iterator so that it is initially at the smallest value in
	 * the set. Hint: Go to farthest left node and push each node onto the stack
	 * while stepping down the IndexTree to get there.
	 *
	 * @param node
	 *            the root node of the IndexTree
	 */
	public IndexTreeIterator(IndexTreeNode<K, V> node) {
		stack = new Stack<IndexTreeNode<K, V>>();
		// Traverse to the leftest node and add all the node passed by into stack
		while (null != node) {
			stack.push(node);
			node = node.getLeftChild();
		}
	}

	/**
	 * Returns true iff the iterator has more items.
	 *
	 * @return true iff the iterator has more items
	 */
	public boolean hasNext() {
		return !stack.empty();
	}

	/**
	 * Returns the next node in the iteration.
	 *
	 * @return the next item in the iteration
	 * @throws NoSuchElementException
	 *             if the iterator has no more items.
	 */
	public IndexTreeNode<K, V> next() {
		if (stack.empty()) {
			throw new NoSuchElementException();
		}
		IndexTreeNode<K, V> item;
		IndexTreeNode<K, V> result;
		item = stack.pop();
		result = item;
		if (null != item.getRightChild()) {
			item = item.getRightChild();
			// If node has a right child, get it and traverse to the leftest child
			while (null != item) {
				// Add one right and all the other left children
				stack.push(item);
				item = item.getLeftChild();
			}
		}
		return result;
	}

	/**
	 * Not Supported
	 */
	public void remove() {
		throw new UnsupportedOperationException();
	}
}
