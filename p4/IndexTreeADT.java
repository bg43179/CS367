import java.util.Iterator;
import java.util.List;

/**
 * IndexTreeADT for index tree where each node is identified by a key and can
 * have a list of values i.e duplicate keys are allowed.
 *
 * DO NOT CHANGE THIS!
 * 
 * @author CS367
 */
public interface IndexTreeADT<K extends Comparable<K>, V> extends Iterable<IndexTreeNode<K, List<V>>> {

	/**
	 * Returns iterator with respect to the root node.
	 */
	public Iterator<IndexTreeNode<K, List<V>>> iterator();

	/**
	 * Inserts a key and value into the IndexTree.
	 * 
	 * @param key
	 *            key of the new data to be retrieved.
	 * @param value
	 *            data to be inserted.
	 * @throws IllegalArgumentException
	 *             if key is null.
	 */
	public void insert(K key, V value);

	/**
	 * Finds the data value for the node having key as key.
	 *
	 * @param key
	 *            the key to search.
	 * @return data value in the tree for the corresponding key.
	 * @throws IllegalArgumentException
	 *             if key is null.
	 */
	public List<V> search(K key);

	/**
	 * Returns the list of data values which have keys in the specified range
	 * (inclusive of minValue and maxValue).
	 *
	 * @param minValue
	 *            the minimum value of the desired range (inclusive).
	 * @param maxValue
	 *            the maximum value of the desired range (inclusive).
	 * @return the sorted list of keys in the specified range.
	 * @throws IllegalArgumentException
	 *             if either minValue or maxValue is null, or minValue is larger
	 *             than maxValue.
	 */
	public List<V> rangeSearch(K minValue, K maxValue);

	/**
	 * Returns number of nodes in a tree.
	 * 
	 * @return number of nodes
	 */
	public int size();

	/**
	 * Returns height of the tree.
	 * 
	 * @return the height
	 */
	public int getHeight();

	/**
	 * Returns total number of data values in the IndexTree.
	 * 
	 * @return the total data count
	 */
	public int getTotalDataCount();

	/**
	 * Returns average number of data values per node in the tree rounded to 3
	 * decimal places.
	 * 
	 * @return the average data count
	 */
	public double getAvgDataCount();

	/**
	 * Displays the top maxNumLevels of the tree. DO NOT CHANGE IT!
	 *
	 * @param maxNumLevels
	 *            from the top of the BST that will be displayed.
	 */
	public void displayTree(int maxNumLevels);
}
