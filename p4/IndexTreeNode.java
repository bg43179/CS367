/**
 * Node class for IndexTree which is a Binary Search Tree (BST).
 *
 * DO NOT MODIFY THIS CLASS.
 *
 * @author CS367
 */
public class IndexTreeNode<K extends Comparable<K>, V> {

	// Key value.
	private K key;

	// Left child
	private IndexTreeNode<K, V> left;

	// Right child.
	private IndexTreeNode<K, V> right;

	// Node data.
	private V data;

	/**
	 * Constructs a new leaf node containing key k and data value v.
	 * 
	 * @param k
	 *            the key.
	 * @param v
	 * 		      the associated data value.        
	 */
	public IndexTreeNode(K k, V v) {
		if (k == null || v == null) {
			throw new IllegalArgumentException();
		}
		key = k;
		data = v;
		left = right = null;
	}

	/**
	 * Get this node's key.
	 *
	 * @return the key.
	 */
	public K getKey() {
		return key;
	}

	/**
	 * Get this node's data.
	 *
	 * @return the data.
	 */
	public V getData() {
		return data;
	}

	/**
	 * Get this node's left child.
	 *
	 * @return the left child.
	 */
	public IndexTreeNode<K, V> getLeftChild() {
		return left;
	}

	/**
	 * Get this node's right child.
	 *
	 * @return the right child.
	 */
	public IndexTreeNode<K, V> getRightChild() {
		return right;
	}

	/**
	 * Set this node's data value.
	 *
	 * @param v
	 *            the data value.
	 */
	public void setData(V v) {
		data = v;
	}

	/**
	 * Set this node's left child.
	 *
	 * @param l
	 *            the left child.
	 */
	public void setLeftChild(IndexTreeNode<K, V> l) {
		left = l;
	}

	/**
	 * Set this node's right child.
	 * 
	 * @param r 
	 *            the right child.
	 */
	public void setRightChild(IndexTreeNode<K, V> r) {
		right = r;
	}
}
