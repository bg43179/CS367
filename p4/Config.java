import java.util.Scanner;

/**
 * This class provides configuration for the MovieDb project. DO NOT EDIT.
 * Your edits will not be used in the evaluation of your program.
 */
public class Config {
	  
  
   // Define color codes that can be used on command prompt to highlight text.
  public static final String ANSI_RESET = "\u001B[0m";
  public static final String ANSI_BLACK = "\u001B[30m";
  public static final String ANSI_RED = "\u001B[31m";
  public static final String ANSI_GREEN = "\u001B[32m";
  public static final String ANSI_YELLOW = "\u001B[33m";
  public static final String ANSI_BLUE = "\u001B[34m";
  public static final String ANSI_PURPLE = "\u001B[35m";
  public static final String ANSI_CYAN = "\u001B[36m";
  public static final String ANSI_WHITE = "\u001B[37m";
  
  // Define message strings that can be used on command prompt.
  public static final String USAGE = "java MovieDbMain <data-file>";
  public static final String WELCOME = "Welcome to CS367: Movie Database!\n";
  
  // Attributes for the MovieRecord.
  public static final String TITLE = "title";
  public static final String RELEASE_YEAR = "releaseYear";
  public static final String DIRECTOR = "director";
  public static final String RATING  = "rating";
  public static final String GENRE = "genre";
  public static final String CAST = "cast";
  public static final String LANG = "lang";
  
  public static final String INDEX_NAMES = 
		  "Available Indices: " +
		  "title   releaseYear   director   rating";
  
  public static String MENU_PROMPT = "Please select your option:\n"
			+ "[S] Search by Index Key\n"
			+ "[R] Range Search\n"
			+ "[P] Print Stats\n"
			+ "[D] Display Tree\n"
			+ "[H] Help\n"
			+ "[Q] Quit\n"
			+ "Enter Choice: ";

  public static String HELP_SEARCH = "Usage: [S] Search By Index Key\n\n" +
		  "Enter Choice: S\n" +
		  "Selected [S] Search by Index Key\n" +
		  INDEX_NAMES + "\n" +
		  "Enter <Index> <value>: title Inception\n\n";
  
  public static String HELP_RANGE = "Usage: [R] Range Lookup\n\n" +
		  "Enter Choice: R\n" +
		  "Selected [R] Range Search\n" +
		  INDEX_NAMES + "\n" +
		  "Enter <Index> <min-value> <max-value>:  releaseYear 2015 2016\n\n";

  public static String HELP = HELP_SEARCH + HELP_RANGE;

  public static final String INVALID_PARAM = "Invalid Parameter\n";  
  public static final String INVALID_RESPONSE = 
		  "Invalid response: Please enter one of the menu options\n";
 
  public static final String INVALID_INDEX_KEY =
  "Invalid Index Key. Possible choices are: title, director, releaseYear and rating\n";
  
  public static final String INVALID_INT_INPUT = "Invalid input: please enter an integer between 1 and 151.";
    
  
  // THE SCANNER MUST NOT BE USED DIRECTLY.
  // Instead Students must call one of the following input methods.
  private static final Scanner STDIN = new Scanner(System.in);


  /**
   * Close the scanner.
   */
  public static void scannerClose() {
	  STDIN.close();
  }
 
  /**
   * Returns the next user input string token.
   * 
   * @return getNext()
   */
  public static String getNext() {
	  return STDIN.next();
  }
  
  /**
   * Returns the next line of user input as a String
   * 
   * @return getNextLine()
   */
  public static String getNextLine() {
       return STDIN.nextLine();
  }

  /** 
   * Returns the next user input if it is an integer.
   * If the input is not an integer a RuntimeException is thrown.
   * 
   * @return getNextInteger()
   */
  public static int getNextInteger() {
       int i = STDIN.nextInt();
       STDIN.nextLine(); // clear rest of current line from input stream
       return i;
  }

  /** 
   * Returns the next user input if it is an double.
   * If the input is not a valid double a RuntimeException is thrown.
   * 
   * @return getNextDouble()
   */
  public static double getNextDouble() {
       double d = STDIN.nextDouble();
       STDIN.nextLine(); // clear rest of current line from input stream
       return d;
  }
}