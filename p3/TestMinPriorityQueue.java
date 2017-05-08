/**
 * Does some basic tests to check whether the MinPriorityQueue is functioning as
 * expected. You should add your own tests to more thoroughly check its
 * functionality.
 *
 */
public class TestMinPriorityQueue {
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

    public static void main(String[] args) {
        boolean excThrown;
        MinPriorityQueueADT mpq = new MinPriorityQueue();

        // check that the PriorityQueueFullException is thrown when trying to
        // remove from an initially empty queue
        excThrown = false;
        try {
            mpq.removeMin();
        } catch (PriorityQueueEmptyException e) {
            excThrown = true;
        }
        test(excThrown, 
             "Expecting PriorityQueueEmptyException to be thrown.");

        // check that the queue is empty after initialization
        test(mpq.isEmpty(), "Priority queue not empty after initialization");

        try {
            for(int i = 0; i < 128; i++) {
                // insert a bunch of different HuffmanNodes, where the ASCII
                // value of the character is equal to its frequency (e.g. 'a'
                // has a frequency of 97, '\n' has a frequency of 10, etc.)
                mpq.insert(new HuffmanNode((char) i, i));
            }
        } catch (PriorityQueueFullException e) {
            e.printStackTrace();
        }

        try {
            // remove the previously inserted nodes one by one, checking whether
            // the correct one was removed
            for(int i = 0; i < 128; i++) {
                HuffmanNode hn = mpq.removeMin();
                System.out.println(hn.getFrequency());
                test(hn != null, "Removed node was null");
                test(hn.getFrequency() == i, 
                     String.format("Expecting frequency of removed node to be " +
                                   "%d, got %d.", i, hn.getFrequency()));
                test(hn.getSymbol() == (char) i, 
                     String.format("Expecting character of removed node to be " +
                                   "%c, got %c.", (char) i, hn.getSymbol()));
            }
        } catch (PriorityQueueEmptyException e) {
            e.printStackTrace();
        }

        // check that the PriorityQueueFullException is thrown when trying to
        // remove from a (now) empty queue
        excThrown = false;
        try {
            mpq.removeMin();
        } catch (PriorityQueueEmptyException e) {
            excThrown = true;
        }
        test(excThrown, "Expecting PriorityQueueEmptyException to be thrown.");
    }
}
