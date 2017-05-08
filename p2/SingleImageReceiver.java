///////////////////////////////////////////////////////////////////////////////
//
// Main Class File:  CacheImageApp.java
// File:             SingleImageReceiver.java
// Semester:         CS367, Fall 2016
//
// Author:           Yahn-Chung Chen, chen666@wisc.edu
// CS Login:         yahn-chung
// Lecturer's Name:  Deb Deppeler
//
///////////////////////////////////////////////////////////////////////////////

import java.io.IOException;
import java.util.Iterator;

/**
 * This class simulates a receiver application for a single image by maintaining
 * an image buffer, which is a linked list of packets of the transmitted image
 * file. It collects packets from our InputDriver and reconstructs the image
 * file using a PacketLinkedList&lt;SimplePacket&gt; for the image buffer.
 * 
 * @author honghui
 */
public class SingleImageReceiver {
	private InputDriver input;
	private PacketLinkedList<SimplePacket> list;

	/**
	 * Constructs a Receiver to obtain the image file transmitted.
	 * 
	 * @param file
	 *            the filename you want to receive
	 * 
	 * @throws IOException
	 *             if fails to retrieve the file
	 */
	public SingleImageReceiver(String file) throws IOException {
		input = new InputDriver(file);
		list = new PacketLinkedList<SimplePacket>();
	}

	/**
	 * Returns the PacketLinkedList buffer in the receiver
	 * 
	 * @return the PacketLinkedList object
	 */
	public PacketLinkedList<SimplePacket> getListBuffer() {
		return list;
	}

	/**
	 * Asks for retransmitting the packet with a sequence number. The requested
	 * packet will arrive later by using {@link #askForNextPacket()}. Notice
	 * that ONLY missing packet will be retransmitted. Pass seq=0 if the missing
	 * packet is the "End of Streaming Notification" packet.
	 * 
	 * @param seq
	 *            the sequence number of the requested missing packet
	 * @return true if the requested packet is added in the receiving queue;
	 *         otherwise, false
	 */
	public boolean askForMissingPacket(int seq) {
		return input.resendMissingPacket(seq);
	}

	/**
	 * Returns true if the maintained list buffer has a valid image content.
	 * Notice that when it returns false.
	 * 
	 * @return true if the maintained list buffer has a valid image content;
	 *         otherwise, false
	 */
	public boolean validImageContent() {
		return input.validFile(list);
	}

	/**
	 * Returns the next packet.
	 * 
	 * @return the next SimplePacket object; returns null if no more packet to
	 *         receive
	 */
	public SimplePacket askForNextPacket() {
		return input.getNextPacket();
	}

	/**
	 * Outputs the formatted content in the PacketLinkedList buffer. See course
	 * webpage for the formatting detail.
	 * 
	 * @param list
	 *            the PacketLinkedList buffer
	 */
	public void displayList(PacketLinkedList<SimplePacket> list) {
		String output = "";
		PacketLinkedListIterator<SimplePacket> itr = list.iterator();
		
		output = output + Integer.toString(itr.next().getSeq());
		while (itr.hasNext()) {
			output = output + ", ";
			output = output + Integer.toString(itr.next().getSeq());				
		} 
		System.out.print(output + "\n");
	}

	/**
	 * Reconstructs the file by arranging the {@link PacketLinkedList} in
	 * correct order. It uses {@link #askForNextPacket()} to get packets until
	 * no more packet to receive. It eliminates the duplicate packets and asks
	 * for retransmitting when getting a packet with invalid checksum.
	 */
	public void reconstructFile() {
		int endOfStreaming = 0;	
		boolean flag;
		SimplePacket temp;
		SimplePacket curr;
		SimplePacket packet = this.askForNextPacket();
		PacketLinkedList<SimplePacket> templist = new PacketLinkedList();

		// Two conditions may result in end_of_streaming
		while (packet != null && packet.getSeq() > 0) {
			list.add(packet);
			packet = this.askForNextPacket();			
		}
		// Two condition: 1. miss the last sequence 2. get the last sequence 		
		if (packet == null) {
			// Keeps requesting until requiring packet is added to the queue  	
			while(askForMissingPacket(0) != true);			
			// Keeps receiving the packet until receive missing one
			while ( packet == null || packet.getSeq() > 0) {
				packet = this.askForNextPacket();
			}
			endOfStreaming = -1 * packet.getSeq();;
		} else { // pack.getSeq < 0
			endOfStreaming = -1 * packet.getSeq();
			
		}
		
		//Asks for the missing packet first, then we only need to arrange and eliminate one time
		
		PacketLinkedListIterator<SimplePacket> itr = list.iterator();
		for (int i= 1; i <= endOfStreaming; i++) {
			// Uses flag to distinguish if the packet with seq i is in the LinkedList
			flag = false;
			while (itr.hasNext()) {
				temp = itr.next();
				//If packet with sequence i is found, flag = true
				if (temp.getSeq() == i) {
					flag = true;
				}
			}
			itr = list.iterator();
			// If packet with sequence i isn't found
			if (!flag) {
				// Keeps requesting until requiring packet is added to the queue  	
				while (askForMissingPacket(i) != true);
				// Keeps receiving the packet until receive missing one
				while (packet.getSeq() != i) {
					packet = this.askForNextPacket();
					list.add(packet);
				}
			}		
		}
		
		// Sorts (binary insertion sort)
		for (int i = 1; i < list.size(); i++) {
			SimplePacket x = list.get(i);
			int j = i-1;
			// Chooses one element then compares its left side element with it
			// Set i = 1 to avoid Index of out bound
			while ( j >= 0 && list.get(j).getSeq() > x.getSeq()) {
				// If the chosen element is smaller than its left side element, replace it
				list.add( j+1, list.get(j) );
				list.remove(j+2);
				//move to left to keep comparing
				j = j-1;
			}
			// j is now equal the position of left element
			// Since left one is smaller than chosen element, then set chosen one at j + 1 position
			list.add(j+1, x);
			list.remove(j+2);	
		}
		
		// Eliminates duplicate 
		curr = list.get(0);
		boolean found = false;
		for (int i = 0 ; i < list.size() ; i++) {
			// If former one(curr)is same as the recent one, found is true
			// Keep comparing until curr and recent one are different, then add curr into list and change curr
			if (curr.getSeq() == list.get(i).getSeq() && !found) {
				found = true;
			} else if ( curr.getSeq() != list.get(i).getSeq()) {
				templist.add(curr);
				curr = list.get(i);
				found = false;
			}
		}
		// the last one curr
		templist.add(curr);		
		list = templist;
		// displayList(templist); //uncomment during debugging only
	}
}

