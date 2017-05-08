///////////////////////////////////////////////////////////////////////////////
//
// Main Class File:  Huffman.java
// File:             MinPriorityQueue.java
// Semester:         CS367, Fall 2016
//
// Author:           Yahn-Chung Chen, chen666@wisc.edu
// CS Login:         yahn-chung
// Lecturer's Name:  Deb Deppeler
//
///////////////////////////////////////////////////////////////////////////////

/**  CLASS HEADER
 * Implements MinPriorityQueueADT
 *
 * @author       Yahn-Chung Chen
 * @version      1.0
 */

public class MinPriorityQueue implements MinPriorityQueueADT {
	
	private HuffmanNode[] heap;
	private int numItem;

	public MinPriorityQueue(){
		heap = new HuffmanNode[129];
		numItem = 0;
	}
	    /**
     * Removes the minimum element from the Priority Queue, and returns it.
     *
     * @return the minimum element in the queue, according to the compareTo()
     * method of HuffmanNode.
     * @throws PriorityQueueEmptyException if the priority queue has no elements
     * in it
     */
    public HuffmanNode removeMin() throws PriorityQueueEmptyException {
    	if (this.isEmpty()) {
    		throw new PriorityQueueEmptyException();
    	}

    	HuffmanNode temp;
    	int parent = 1;
    	int child;
    	int minchild;
    	boolean done = false;

        // Swap the first one with the last one
    	temp = heap[numItem];
    	heap[numItem] = heap[parent];
    	heap[parent] = temp;

    	while (!done ) {
    		child = parent*2;
            if (child >= numItem){
    			done = true;
    		} else {
    		// choose the min-child
    		    if ((child + 1) >= numItem) {
                    minchild = child;
                } else if (heap[child].compareTo(heap[child + 1]) < 0) {
    			    minchild = child;
    		    } else {
    			    minchild = child + 1;
    		    }

    		// compare parent to min-child 
    		    if (heap[parent].compareTo(heap[minchild]) < 0) {
    			    done = true;
     		    // Swap
                } else {
     			    temp = heap[minchild];
    			    heap[minchild] = heap[parent];
    			    heap[parent] = temp;
                    parent = minchild;
     		    }
            }
    	}
    	numItem--;
        return heap[numItem+1];
    }

    /**
     * Inserts a HuffmanNode into the queue, making sure to keep the shape and
     * order properties intact.
     *
     * @param hn the HuffmanNode to insert
     * @throws PriorityQueueFullException if the priority queue is full.
     */
   
    public void insert(HuffmanNode hn) throws PriorityQueueFullException {
    	if (numItem >= heap.length-1) {
    		throw new PriorityQueueFullException();
    	}

    	int child = numItem + 1;
		int parent;
		HuffmanNode temp;
		boolean done = false;

        // Insert new hn as last element in array
    	heap[child] = hn;

    	// Compare children to their parents
        while (!done) {
    		parent = child/2;
    		if (parent == 0) {
    			done = true;
    		} else if (heap[parent].compareTo(heap[child]) < 0) {
    			done = true;    
    		} else {
    			temp = heap[child];
    			heap[child] = heap[parent];
    			heap[parent] = temp;
    			child = parent;
    		}
    	}
        numItem++;
    }

    /**
     * Checks if the queue is empty.
     *
     * @return true, if it is empty; false otherwise
     */
    public boolean isEmpty() {
    	return numItem <= 0;
    }
}
