import java.util.List;

public interface TesterADT<K, V> {

	/**
	 * Search for node having index equal to key and return the corresponding
	 * data values.
	 * 
	 * @param index
	 *            index (attribute) of the movie record by which search is to be
	 *            done.
	 * @param key
	 *            key to be searched.
	 * @return list of movie records that match the key.
	 */
	public List<V> searchByKey(String index, String key);

	/**
	 * Search for records having index value within the range minVal and
	 * maxVal.
	 * 
	 * @param index
	 *            attribute of the movie record by which search is to be done.
	 * @param minVal
	 *            lower bound of the range search (inclusive).
	 * @param maxVal
	 *            upper bound of the range search (inclusive).
	 * @return list of movie records that fall in the given range.
	 */
	public List<V> rangeSearch(String index, String minVal, String maxVal);

	/**
	 * Returns a sorted list of keys - index defines which key we want to sort
	 * on.
	 * 
	 * @param index
	 *            the index to which sort on.
	 * @return list of index values sorted in lexicographically increasing order.
	 */
	public List<K> allSortedKeys(String index);
}
