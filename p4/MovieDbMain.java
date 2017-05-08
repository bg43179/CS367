import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Class to provide user interface which lets you interact with the MovieDb
 * application.
 *
 * @author apul
 */
public class MovieDbMain {

	// List of movie records.
	private List<MovieRecord> movieRecordList;

	// MovieDb object.
	MovieDb movieDb;

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
	 * <p>
	 * For example:
	 * </p>
	 * 
	 * <pre>
	 * java MovieDbMain data.csv
	 * </pre>
	 *
	 * @param args
	 *            filename
	 */
	public static void main(String[] args) {
		if (args.length != 1) {
			System.err.println("Bad invocation! Correct usage: " +
								"java MovieDbMain <Data file> ");
			System.exit(1);
		}

		// Data file.
		String dataFilename = args[0];
		MovieDbMain movieDbmain = new MovieDbMain(dataFilename);

		System.out.println(Config.WELCOME);

		String choice = "";
		Scanner scanner = new Scanner(System.in);

		while (true) {
			// Main menu.
			System.out.print(Config.MENU_PROMPT);
			choice = scanner.next();
			switch (choice) {
			case "S":
			case "s":
				System.out.println("Selected [S] Search by Index Key");
				System.out.println(Config.INDEX_NAMES);
				System.out.print("Enter <Index> <key>: ");
				String index = scanner.next();
				String value = scanner.nextLine().toLowerCase();
				movieDbmain.handleSearchByKey(index.trim(), value.trim());
				break;
	
			case "R":
			case "r":
				System.out.println("Selected [R] Range Search");
				System.out.println(Config.INDEX_NAMES);
				System.out.print("Enter <Index> <min-value> <max-value>: ");
				index = scanner.next();
				String minVal = scanner.next();
				String maxVal = scanner.next();
				movieDbmain.handleRangeSearch(index.trim(), minVal.trim().toLowerCase(), maxVal.trim().toLowerCase());
				break;
	
			case "P":
			case "p":
				System.out.println("Selected [P] Print Stats");
				movieDbmain.handlePrintStats();
				break;
			
			case "D":
			case "d":
				System.out.println("Selected [D] Display Tree");
				System.out.println(Config.INDEX_NAMES);
				System.out.print("Enter <index> <maxNumLevels>: ");
				index = scanner.next();
				int maxNumLevels = scanner.nextInt();
				movieDbmain.handleDisplayTree(index, maxNumLevels);
				break;
			
			case "H":
			case "h":
				System.out.println(Config.HELP);
				break;
			
			case "Q":
			case "q":
				scanner.close();
				return;
			
			default:
				System.out.println(Config.INVALID_RESPONSE);
			}
		}
	}

	/**
	 * Displays IndexTree upto specified maxNumLevels levels.
	 * 
	 * @param index
	 *            the IndexTree name.
	 * @param maxNumLevels
	 *            number of levels to print.
	 */
	void handleDisplayTree(String index, int maxNumLevels) {
		movieDb.getIndexTree(index).displayTree(maxNumLevels);
	}

	/**
	 * Print statistics for all the IndexTrees.
	 */
	void handlePrintStats() {
		System.out.println(movieDb.toString());
		System.out.println();
	}

	/**
	 * Search for records in the IndexTree having attribute (index)
	 * in the range minValue and maxValue (both inclusive).
	 * 
	 * @param index
	 *            the IndexTree we want to query on.
	 * @param minVal
	 *            the lower bound of our search (inclusive).
	 * @param maxVal
	 *            the upper bound of our search (inclusive).
	 */
	void handleRangeSearch(String index, String minValue, String maxValue) {
		List<MovieRecord> result;
		try {
			result = movieDb.getIndexTree(index).rangeSearch(minValue, maxValue);
			printData(result);
		} catch (IllegalArgumentException e) {
			System.out.println("Bad range argument for Index: " + index);
		}

		catch (InvalidIndexException e) {
			System.out.println(e.getMessage());
			System.out.println();
		}

	}

	/**
	 * Search IndexTree for the input index (attribute) having value equal to 
	 * key and print all the data values for that node. 
	 * 
	 * @param index
	 *           the IndexTree we want to query on.
	 * @param key
	 *           the key value we want to search in IndexTree.
	 */
	void handleSearchByKey(String index, String key) {
		List<MovieRecord> result;
		try {
			result = movieDb.getIndexTree(index).search(key);
			printData(result);
		} catch (KeyNotFoundException e) {
			System.out.println(e.getMessage());
			System.out.println();
		} catch (InvalidIndexException e) {
			System.out.println(e.getMessage());
			System.out.println();
		}
	}

	/**
	 * Prints the results returned from IndexTree.
	 * 
	 * @param result
	 *            the list of data values to print.
	 */
	void printData(List<MovieRecord> result) {
		int i = 1;
		System.out.println("===============Results================");
		for (MovieRecord rec : result) {
			System.out.println(i++ + "." + rec.toString());
			System.out.println("--------------------------------------");
		}
		System.out.println();
	}

	/**
	 * MovieDbMain constructor. This reads the input data file and parses it
	 * into a list of MovieRecords.
	 * 
	 * @param dataFile
	 *            file name from which we want to read records.
	 */
	public MovieDbMain(String dataFile) {
		// Read movie data from the file.
		try {
			movieRecordList = new ArrayList<MovieRecord>();
			Scanner fileScnr = new Scanner(new File(dataFile));
			// Read individual data values from the file
			while (fileScnr.hasNextLine()) {
				String line = fileScnr.nextLine();
				String[] tokens = line.split(",");
				MovieRecord record = new MovieRecord(tokens[0].trim(), tokens[1].trim(), tokens[2].trim(),
						tokens[3].trim(), tokens[4].trim(), tokens[5].trim(), tokens[6].trim());
				movieRecordList.add(record);
			}

			fileScnr.close();
			movieDb = new MovieDb(movieRecordList);

		} catch (FileNotFoundException e) {
			System.out.println("MovieDbMain failed..." + dataFile + " file not found.");
			System.exit(0);
		} catch (Exception e) {
			System.out.println("MovieDbMain failed..." + e.getMessage());
			System.exit(0);
		}
	}
}
