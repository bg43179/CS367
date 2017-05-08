import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

/**
 * Run several data experiments and display results, so you can analyze the
 * results and answer some questions about the relative efficiencies of various
 * data structures.
 * 
 * <p>
 * The data will be stored in two types of data structures: IndexTrees and
 * ArrayList. The analysis will include measuring time to search for records
 * with a given key.
 * </p>
 * 
 * <p>
 * This uses ArrayListTester and IndexTreeTester classes which implement
 * TesterADT for search method.
 * </p>
 *
 * @author apul
 */
public class MovieDbTesterMain {

	// List of movie records that we want to run analysis on.
	private List<MovieRecord> movieRecordList;

	// Sets of keys on which we can query.
	private Set<String> titleKeys;
	private Set<String> directorKeys;
	private Set<String> releaseYearKeys;
	private Set<String> ratingKeys;

	/**
	 * <p>
	 * Main method. Every application needs one. Command-line arguments are
	 * required.
	 * </p>
	 * 
	 * <ul>
	 * <li>args[0] is the name of an existing data file that contains a list of
	 * movie records described by comma-separated attributes. This is the data
	 * set that will be used to build our Index trees.</li>
	 * </ul>
	 * 
	 * <p>
	 * For example:
	 * </p>
	 * 
	 * <pre>
	 * java MovieDbTesterMain data.csv
	 * </pre>
	 *
	 * @param args
	 *            filename
	 */
	public static void main(String[] args) {
		if (args.length != 1) {
			System.err.println("Bad invocation! Correct usage: "
					+ "java MovieDbTesterMain <Data file> ");
			System.exit(1);
		}

		String dataFilename = args[0];
		MovieDbTesterMain movieDbTesterMain = new MovieDbTesterMain(dataFilename);
		movieDbTesterMain.initialize();
		movieDbTesterMain.evaluateAll();
	}

	/**
	 * Construct a set of all candidate keys on which we will query our
	 * performance on Index trees and ArrayList.
	 */
	public void initialize() {
		titleKeys = new HashSet<>();
		releaseYearKeys = new HashSet<>();
		directorKeys = new HashSet<>();
		ratingKeys = new HashSet<>();

		for (MovieRecord rec : movieRecordList) {
			titleKeys.add(rec.getTitle().toLowerCase());
			releaseYearKeys.add(rec.getReleaseYear());
			directorKeys.add(rec.getDirector().toLowerCase());
			ratingKeys.add(rec.getRating());
		}
	}

	/**
	 * Evaluate search by key time for all sets of keys for both the data
	 * structures.
	 */
	public void evaluateAll() {
		// Search By Key comparison.
		evaluateSearchByKey();
		System.out.println();
		// Sorted Keys comparison.
		evaluateSortedKeys();
	}

	/**
	 * Evaluates search by key performance.
	 * 
	 * @param arrayListTester
	 * @param indexTreeTester
	 * @param index
	 * @param keys
	 */
	private void evaluateSearchByKey() {
		long timeTaken1, timeTaken2;
		// Create testers for the two data structures.
		TesterADT<String, MovieRecord> arrayListTester =
				new ArrayListTester(movieRecordList);
		TesterADT<String, MovieRecord> indexTreeTester =
				new IndexTreeTester(movieRecordList);

		// Evaluate SearchByKey time for both ArrayList and IndexTree.
		System.out.println("Evaluating Search By Key time.");
		System.out.println("------------------------------------------");
		
		System.out.printf("%-15s %-15s %-15s\n", "Index", "ArrayList", "IndexTree");
		System.out.println("------------------------------------------");

		timeTaken1 = 
				evaluateSearchByKey(arrayListTester, Config.TITLE, titleKeys);
		timeTaken2 =
				evaluateSearchByKey(indexTreeTester, Config.TITLE, titleKeys);
		System.out.printf("%-15s %-15s %-15s\n", "title", timeTaken1 + "ms",
				timeTaken2 + "ms");

		timeTaken1 =
				evaluateSearchByKey(arrayListTester, Config.DIRECTOR, directorKeys);
		timeTaken2 =
				evaluateSearchByKey(indexTreeTester, Config.DIRECTOR, directorKeys);
		System.out.printf("%-15s %-15s %-15s\n", "director", timeTaken1 + "ms",
				timeTaken2 + "ms");
		
		timeTaken1 =
				evaluateSearchByKey(arrayListTester, Config.RELEASE_YEAR,
						releaseYearKeys);
		timeTaken2 =
				evaluateSearchByKey(indexTreeTester, Config.RELEASE_YEAR,
						releaseYearKeys);
		System.out.printf("%-15s %-15s %-15s\n", "releaseYear", timeTaken1 + "ms",
				timeTaken2 + "ms");

		timeTaken1 =
				evaluateSearchByKey(arrayListTester, Config.RATING, ratingKeys);
		timeTaken2 = 
				evaluateSearchByKey(indexTreeTester, Config.RATING, ratingKeys);

		System.out.printf("%-15s %-15s %-15s\n", "rating", timeTaken1 + "ms",
				timeTaken2 + "ms");
	}
	
	private void evaluateSortedKeys() {
		System.out.println("Evaluating Sort By Key time.");
		System.out.println("------------------------------------------");		
		System.out.printf("%-15s %-15s %-15s\n", "Index", "ArrayList", "IndexTree");
		System.out.println("------------------------------------------");

		long timeTaken1, timeTaken2;		
		// Create testers for the two data structures.
		TesterADT<String, MovieRecord> arrayListTester =
				new ArrayListTester(movieRecordList);
		TesterADT<String, MovieRecord> indexTreeTester =
				new IndexTreeTester(movieRecordList);

		timeTaken1 = evaluateSortedKeys(arrayListTester, Config.TITLE);
		timeTaken2 = evaluateSortedKeys(indexTreeTester, Config.TITLE);
		System.out.printf("%-15s %-15s %-15s\n", "title", timeTaken1 + "ms",
				timeTaken2 + "ms");

		timeTaken1 = evaluateSortedKeys(arrayListTester, Config.DIRECTOR);
		timeTaken2 = evaluateSortedKeys(indexTreeTester, Config.DIRECTOR);
		System.out.printf("%-15s %-15s %-15s\n", "director", timeTaken1 + "ms",
				timeTaken2 + "ms");

		timeTaken1 = evaluateSortedKeys(arrayListTester, Config.RELEASE_YEAR);
		timeTaken2 = evaluateSortedKeys(indexTreeTester, Config.RELEASE_YEAR);
		System.out.printf("%-15s %-15s %-15s\n", "releaseYear", timeTaken1 + "ms",
				timeTaken2 + "ms");

		timeTaken1 = evaluateSortedKeys(arrayListTester, Config.RATING);
		timeTaken2 = evaluateSortedKeys(indexTreeTester, Config.RATING);
		System.out.printf("%-15s %-15s %-15s\n", "rating", timeTaken1 + "ms",
				timeTaken2 + "ms");

	}

	/**
	 * Compares time taken to sort the records by keys specified by index.
	 */
	private long evaluateSortedKeys(TesterADT<String, MovieRecord> tester, String index) {
		long start = System.currentTimeMillis();
		tester.allSortedKeys(index);
		return System.currentTimeMillis() - start;
	}

	/**
	 * This function runs tester on the set of keys provided as input and
	 * returns the time taken to perform the search for each key. Index
	 * represents the index that we want to perform search on.
	 * 
	 * @param tester
	 *            the tester class we want to use for test.
	 * @param index
	 *            the index name (attribute) we want to search for.
	 * @param keys
	 * 		      the set of keys corresponding to the index key.
	 */
	private long evaluateSearchByKey(TesterADT<String, MovieRecord> tester,
			String index, Set<String> keys) {
		long start = System.currentTimeMillis();
		for (String key : keys) {
			List<MovieRecord> res = tester.searchByKey(index, key);
			if (res.isEmpty())
				System.out.println("No records found for the key: " + key);
		}
		return System.currentTimeMillis() - start;
	}

	/**
	 * Tester constructor.
	 * 
	 * @param dataFile
	 *            the file name to read movie records from.
	 */
	public MovieDbTesterMain(String dataFile) {
		// Read movie data from the file.
		try {
			movieRecordList = new ArrayList<MovieRecord>();
			Scanner fileScnr = new Scanner(new File(dataFile));
			// Read individual data values from the file
			while (fileScnr.hasNextLine()) {
				String line = fileScnr.nextLine();
				String[] tokens = line.split(",");
				MovieRecord record = new MovieRecord(tokens[0].trim(),
						tokens[1].trim(), tokens[2].trim(),
						tokens[3].trim(), tokens[4].trim(),
						tokens[5].trim(), tokens[6].trim());
				movieRecordList.add(record);
			}
			fileScnr.close();
		} catch (FileNotFoundException e) {
			System.out.println("MovieDbTesterMain Construction fails..."
							+ dataFile + " file not found.");
			System.exit(0);
		} catch (Exception e) {
			System.out.println("MovieDbTesterMain Construction fails..."
							+ e.getMessage());
			System.exit(0);
		}
	}
}