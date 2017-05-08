/**
 * The MinPriorityQueueADT interface describes a data structure that maintains a minimum
 * priority queue, supporting isEmpty(), removeMin(), and insert().
 * 
 * You must implement this interface in a file named MinPriorityQueue.java using an
 * array-based heap.
 */
public interface MinPriorityQueueADT {
    /**
     * Removes the minimum element from the Priority Queue, and returns it.
     *
     * @return the minimum element in the queue, according to the compareTo()
     * method of HuffmanNode.
     * @throws PriorityQueueEmptyException if the priority queue has no elements
     * in it
     */
    public HuffmanNode removeMin() throws PriorityQueueEmptyException;

    /**
     * Inserts a HuffmanNode into the queue, making sure to keep the shape and
     * order properties intact.
     *
     * @param hn the HuffmanNode to insert
     * @throws PriorityQueueFullException if the priority queue is full.
     */
    public void insert(HuffmanNode hn) throws PriorityQueueFullException;

    /**
     * Checks if the queue is empty.
     * e.g. 
     * 
     * <pre>
     * {@code
     * m = new MinPriorityQueue(); 
     * // m.isEmpty(): true
     * m.insert(HuffmanNode hn);
     * // m.isEmpty(): false
     * m.remove();
     * // m.isEmpty(): true
     * }
     * </pre>
     *
     * @return true, if it is empty; false otherwise
     */
    public boolean isEmpty();
}
