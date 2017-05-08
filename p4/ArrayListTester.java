///////////////////////////////////////////////////////////////////////////////
//
// Main Class File:  MovieDbTesterMain.java
// File:             ArrayListTester.java
// Semester:         CS367, Fall 2016
//
// Author:           Yahn-Chung Chen, chen666@wisc.edu
// CS Login:         yahn-chung
// Lecturer's Name:  Deb Deppeler
//
///////////////////////////////////////////////////////////////////////////////


import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
/**
 * Tester implementation using ArrayList as the data structure.
 *
 * @author apul
 */
public class ArrayListTester implements TesterADT<String, MovieRecord> {
	// ArrayList of movie records.
	private List<MovieRecord> movieRecordList;

	/**
	 * Constructor that initializes the movieRecordList.
	 * 
	 * @param movieRecordList
	 *            list of movie records.
	 */
	public ArrayListTester(List<MovieRecord> movieRecordList) {
		this.movieRecordList = movieRecordList;
	}

	/**
	 * Returns size of the data list.
	 * 
	 * @return number of movie records.
	 */
	public int size() {
		return movieRecordList.size();
	}

	/**
	 * Search for records having index value equal to key.
	 * 
	 * @param index
	 *            the index (attribute) we want to search for.
	 * @param key
	 *            the key value we are looking for.
	 * @return the list of movie records.
	 */
    @Override
	public List<MovieRecord> searchByKey(String index, String key) {
    	if (null == key) {
    		throw new IllegalArgumentException();
    	}
    	List<MovieRecord> result = new ArrayList<MovieRecord>();

    	for (MovieRecord movie : movieRecordList) {
    		if (key.equalsIgnoreCase(movie.getValByAttribute(index))) {
    			result.add(movie);
    		}
    	}
    	// Go over all movie records and compare the attribute (index
    	// e.g., director) with the key (e.g, Christopher Nolan)
   	    // NOTE: Ignore case while comparing the attribute (index) value
        // with the key.
    	return result;
    }

    /**
     * Search for records having index value within the range minVal and maxVal.
     * 
     * @param index
     *            the index tree to search.
     * @param minVal
     *            minimum value of the range (inclusive).
     * @param maxVal
     *            maximum value of the range (inclusive).
     * @return list of MovieRecords with index key in the specified range (both inclusive).
     */
	@Override
	public List<MovieRecord> rangeSearch(String index, String minVal, String maxVal) {
		if (null == minVal || null == maxVal) {
			throw new IllegalArgumentException();
		}

    	List<MovieRecord> result = new ArrayList<MovieRecord>();
    	for (MovieRecord movie : movieRecordList) { 
    		if (maxVal.compareTo(movie.getValByAttribute(index)) > 0 
    			&& minVal.compareTo(movie.getValByAttribute(index)) < 0) {
    			result.add(movie);
    		}
    	}
		return result;
    }

	/**
	 * Returns a sorted list of keys - index defines which key we want to sort on.
	 * Hint: You can define a Comparator to compare two MovieRecords based on
	 * specified index (e.g., director). You can then use this comparator class
	 * to directly sort using List.sort method.
	 * 
	 * @param index the index to which sort on.
	 * @return Sorted list of key values. 
	 * E.g., [..., "Christopher Nolan", ..., "James Cameron", ...] for director as index.
	 */
	@Override
	public List<String> allSortedKeys(String index) {
		List<String> temp = new ArrayList<String>();
		for (MovieRecord movie : movieRecordList) {
			temp.add(movie.getValByAttribute(index));
		}
		// usinf list.sort method
		temp.sort(new StringComparator());
		return temp;
	}
	/**
	 * Comparator calss for string comparing
	 */
	class StringComparator implements Comparator<String> {   
    public int compare(String s1, String s2) {
        return s1.compareTo(s2);
    }
}
}