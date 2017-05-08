import java.io.*;

/**
 * Does some basic tests to check whether the FrequencyParser is functioning as
 * expected. You should add your own tests to more thoroughly check its
 * functionality.
 *
 */
public class TestFrequencyParser {
    /**
     * You can use test() to check whether a condition is true, and if not throw
     * an exception. For example, if you got a return value from a function and
     * want to check if it's null, you might call:
     * <pre>
     * {@code
     * test(var != null, "Oh no! The var was null!");
     * }
     * </pre>
     * If the condition evaluates to false, then an exception will be thrown
     * and execution will stop. Otherwise, it will print "Passed!" and return.
     */
    private static void test(boolean b, String s) {
        if(!b) {
            throw new RuntimeException(s);
        } else {
            System.out.println("Passed!");
        }
    }

    /**
     * We have included a few tests to get you started in the right direction.
     * You should write other tests. For example, try testing on files other
     * than what we have given you. Try on files where you know the character
     * distribution so that you can verify the results. Is the array you
     * return the right length? Does it include the right frequency in the
     * newline (ASCII 10) position?
     * 
     * @param args, the command line arguments (none expected)
     */
    public static void main(String[] args) {
        int[] frequencies = null;
        try {
            frequencies = 
                FrequencyParser.getFrequencies("sample_text_files/all_ascii_values.txt");
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
        
        // Check that the return value is not null
        test(frequencies != null, "Returned array is null.");
    }
}
