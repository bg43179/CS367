///////////////////////////////////////////////////////////////////////////////
//
// Main Class File:  MovieDbMain.java
// File:             IndexTree.java
// Semester:         CS367, Fall 2016
//
// Author:           Yahn-Chung Chen, chen666@wisc.edu
// CS Login:         yahn-chung
// Lecturer's Name:  Deb Deppeler
//
///////////////////////////////////////////////////////////////////////////////

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Generic IndexTree implementation where each node is identified by a key and
 * can have a list of values i.e duplicate keys are allowed.
 * 
 * For example: You can insert &lt;K1, V1&gt; and &lt;K1, V2&gt; in the IndexTree.
 * After you insert, the node corresponding to key K1 will have a list of data
 * values [V1, V2].
 *
 * @author CS367
 */
public class IndexTree<K extends Comparable<K>, V> implements IndexTreeADT<K, V>, Iterable<IndexTreeNode<K, List<V>>> {

	// Root node.
	private IndexTreeNode<K, List<V>> root;

	/**
	 * Constructs a IndexTree and initializes the root node.
	 */
	public IndexTree() {
		root = null;
	}

	/**
	 * Returns iterator with respect to the root node.
	 * 
	 * @return the iterator for the IndexTree.
	 */
	public Iterator<IndexTreeNode<K, List<V>>> iterator() {
		return new IndexTreeIterator<K, List<V>>(root);
	}

	/**
	 * Search for the node with key equals to input key. Hint: Call
	 * a search helper method to recursively traverse the tree.
	 *
	 * @param key
	 *            the key to search.
	 * @return data value in the tree for the corresponding key.
	 * @throws IllegalArgumentException
	 *             if key is null.
	 * @throws KeyNotFoundException
	 * 				if key is not found in the tree.
	 */	
	public List<V> search(K key) {
		if (null == key) {
			throw new IllegalArgumentException();
		}
		if (null == search(root, key)) {
			throw new KeyNotFoundException();
		} else {
			return search(root, key);
		}
	}

	/**
	 * Recursive helper method of search.
	 * 
	 * @param n
	 *         initial value being root of IndexTree.
	 * @param key
	 *          the key to search.
	 * @return the data of list.
	 */
	private List<V> search(IndexTreeNode<K, List<V>> n, K key) {
		if (null == n) {
			return null;
		}
		if (n.getKey().compareTo(key) == 0) {
			return n.getData();
		}

		if (n.getKey().compareTo(key) > 0) {
			return search(n.getLeftChild(), key);
		} else {
			return search(n.getRightChild(), key);
		}
	}

	/**
	 * Inserts a (key, value) pair into the IndexTree. This will call a recursive
	 * method with root node and (key, value) to be inserted in the IndexTree.
	 * 
	 * @param key
	 *            key of the new data to be inserted.
	 * @param value
	 *            data to be inserted.
	 * @throws IllegalArgumentException
	 *             if key or value is null.
	 */
	public void insert(K key, V value) {
		if (null == key) {
			throw new IllegalArgumentException();
		}
		if (null == root) {
			root = insert(root, key, value);
		} else {
			insert(root, key, value);
		}
	}

	/** 
	 * Recursive helper method to find the position and insert a key and value
	 * into the IndexTree. 
	 * 
	 * @param node
	 *            node is the recursive parameter with initial value being root
	 *            of the IndexTree.
	 * @param key
	 *            key of the new data to be inserted.
	 * @param value
	 *            data to be inserted.
	 */	
	private IndexTreeNode<K, List<V>> insert(IndexTreeNode<K, List<V>> node, K key, V value) {
		// Check if node is null. If so, create a new IndexTreeNode<K,List<V>>.

		if (null == node) {
			IndexTreeNode<K, List<V>> newroot;
			ArrayList<V> list = new ArrayList<V>();
			list.add(value);
			newroot = new IndexTreeNode<K, List<V>>(key, list);
			return newroot;
		}
		// If node is not null, compare key with current node's key. 
		if (node.getKey().compareTo(key) == 0) {
			node.getData().add(value);
			return node;
		} else if (node.getKey().compareTo(key) < 0) {
			// If node doesn't have right children, create one.	
			if (null == node.getRightChild()) {
				node.setRightChild(insert(node.getRightChild(), key, value));
				return node.getRightChild();
			} else {
				return insert(node.getRightChild(), key, value);
			}
		} else {
			if (null == node.getLeftChild()) {
				node.setLeftChild(insert(node.getLeftChild(), key, value));
				return node.getLeftChild();
			} else {
				return insert(node.getLeftChild(), key, value);
			}
		}
		
	}

	/**
	 * Returns a list of data values which have keys in the specified range
	 * (inclusive of minValue and maxValue). 
	 * Note: Range values are always compared lexicographically For example,
	 * "15" &lt; "7" lexicographically.
	 *
	 * @param minValue
	 *            the minimum value of the desired range (inclusive).
	 * @param maxValue
	 *            the maximum value of the desired range (inclusive).
	 * @return list of data values having key in the specified range.
	 * @throws IllegalArgumentException
	 *             if either minValue or maxValue is null.
	 */	
    public List<V> rangeSearch(K minValue, K maxValue) {
		// Check for IllegalArgumentException
    	if (null == minValue || null == maxValue) {
    		throw new IllegalArgumentException();
    	}
    	// Ensure min is less than max
		if ( minValue.compareTo(maxValue) > 0 ) {
			K t = minValue;
			minValue = maxValue;
			maxValue = t;
		}
		// Use a list to save data in the range of searching
		List<V> list = new ArrayList<V>();
		this.rangeSearch(root, minValue, maxValue, list);
    	return list;
    }

    /**
     * Recursive helper method of rangesearch
     *
     * @param n
     * 			initial value being root of IndexTree.
     * @param minValue
     *			the minimum value of the desired range (inclusive).
     * @param maxValue
     *			the maximum value of the desired range (inclusive).
     * @param list
     *			Use a list to save data in the range of searching
     */
    private void rangeSearch(IndexTreeNode<K,List<V>> n, K minValue, K maxValue, List<V> list) {
    	if (null == n){
    		return ;
    	}
    	// If the value of key is smaller than minvalue, go right
    	// If the value of key is larger than maxvalue, go left
    	if (n.getKey().compareTo(minValue) < 0) {
    		this.rangeSearch(n.getRightChild(), minValue, maxValue, list);
    	} else if (n.getKey().compareTo(maxValue) > 0) {
    		this.rangeSearch(n.getLeftChild(), minValue, maxValue, list);
    	}

    	if (minValue.compareTo(n.getKey()) <= 0 && maxValue.compareTo(n.getKey()) >= 0 ) {   		
    		for (V item : n.getData()){
    			list.add(item);
    		}
    		this.rangeSearch(n.getLeftChild(), minValue, maxValue, list);
    		this.rangeSearch(n.getRightChild(), minValue, maxValue, list); 
    	}
    }
    
	/**
	 * Returns the number of nodes in the tree. This must be done recursively
	 * using the helper method to get the number of nodes.
	 * 
	 * @return number of nodes in the tree.
	 */
	public int size() {
		return this.size(root);
	}
	
	/**
	 * Recursive helper method of size 
	 * 
	 * @param n
	 *			initial value being root of IndexTree.
	 * @return number of nodes in the tree.
	 */
	private int size(IndexTreeNode<K, List<V>> n) {
		if (null == n) {
			return 0;
		}
		// Count the node and add the amount of its desendants
		if (n.getLeftChild() == null && n.getRightChild() == null){
			return 1;
		} else if (n.getLeftChild() == null) {
			return 1 + this.size(n.getRightChild());
		} else if (n.getRightChild() == null) {
			return 1 + this.size(n.getLeftChild());
		} else {
			return 1 + this.size(n.getLeftChild()) + this.size(n.getRightChild());
		}
 	}

	/**
	 * Returns height of the tree. Hint: Use a recursive helper method
	 * and call it with root node to calculate the height.
 	 *
 	 * @return the height of the tree.
	 */
	public int getHeight() {
		// Return the height of the root node.
		return this.getHeight(root);
	}

	/**
	 * Recursive helper method of getHeight.
	 * 
 	 * @param n 
	 * 			initial value being root of IndexTree.
 	 * @return the height of the tree.
	 */
	private int getHeight(IndexTreeNode<K,List<V>> n) {
		if (n == null) {
			return 0;
		}
		//No child or only one child
		if (n.getLeftChild() == null && n.getRightChild() == null) {
			return 1;
		} else if (n.getLeftChild() == null) {
			return 1 + this.getHeight(n.getRightChild());
		} else if (n.getRightChild() == null) {
			return 1 + this.getHeight(n.getLeftChild());
		}
		// Get max height of children(have two children)
		int max = 0;
		if (this.getHeight(n.getLeftChild()) > this.getHeight(n.getRightChild())) {
			max = this.getHeight(n.getLeftChild());
		} else {
			max = this.getHeight(n.getRightChild());
		}
		return 1+ max;
	}

	/**
	 * Returns total number of data values in the tree.
	 * 
	 * @return the total data count (values of all data values in the tree).
	 */
	public int getTotalDataCount() {	
		return getTotalDataCount(root);
	}
	/**
	 *
	 * @param n
	 *			initial value being root of IndexTree.
	 * @return the total number of data.
	 */
	private int getTotalDataCount(IndexTreeNode<K, List<V>> n){
		if (null == n) {
			return 0;
		}
		// Count the amount of data in this node then add the amount of data of its descendants 
		if (n.getLeftChild() == null && n.getRightChild() == null) {
			return n.getData().size();
		} else if (n.getLeftChild() == null) {
			return n.getData().size() + this.getTotalDataCount(n.getRightChild());
		} else if (n.getRightChild() == null) {
			return n.getData().size() + this.getTotalDataCount(n.getLeftChild());
		} else {
			return n.getData().size() + this.getTotalDataCount(n.getLeftChild())
					 + this.getTotalDataCount(n.getRightChild());
		}
	}

	/**
	 * Returns average number of data values per node (E.g., Node with key "key"
	 * and list of values List&lt;V&gt; = {v1, v2, v3} has number of data values as 3)
	 * rounded to 3 decimal places.
	 * Hint: Use getTotalDataCount() and size().
	 * 
	 * @return the average data count.
	 */
	public double getAvgDataCount() {
		double result;
		result = (double) this.getTotalDataCount()/this.size();
		DecimalFormat df = new DecimalFormat("####0.000");
		double finalValue = Double.valueOf(df.format(result));
		return finalValue;
	}
	
	/**
	 * Displays the top maxNumLevels of the tree. DO NOT CHANGE IT!
	 * You can use this method to debug your code.
	 *
	 * @param maxNumLevels
	 *            from the top of the IndexTree that will be displayed.
	 */
	public void displayTree(int maxNumLevels) {
		System.out.println("---------------------------" +
					"IndexTree Display--------------------------------");
		displayTree(root, 0, maxNumLevels);
	}

	/**
	 * Recursive helper function to display the top levels of the IndexTree.
	 * 
	 * @param node
	 *            initial value being root of IndexTree.
	 * @param curLevel
	 *            initial value 0.
	 * @param maxNumLevels
	 *            initial value being the number of levels of the tree to be
	 *            displayed.
	 */
	private void displayTree(IndexTreeNode<K, List<V>> node, int curLevel,
			int maxNumLevels) {
		if (maxNumLevels < curLevel)
			return;
		if (node == null)
			return;
		for (int i = 0; i < curLevel; i++) {
			System.out.print("|--");
		}
		System.out.println(node.getKey());
		displayTree(node.getLeftChild(), curLevel + 1, maxNumLevels);
		displayTree(node.getRightChild(), curLevel + 1, maxNumLevels);
	}

}
