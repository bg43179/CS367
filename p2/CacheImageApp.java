///////////////////////////////////////////////////////////////////////////////
// 
// Title:            CS 367 Programming Assignment 2: Image Builder
// Files:            
// Semester:         CS367, Fall 2016
//
// Author:           Yahn-Chung Chen
// Email:            chen666@wisc.edu
// CS Login:         yahn-chung
// Lecturer's Name:  Deb Deppeler

///////////////////////////////////////////////////////////////////////////////


import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

/**
 * This is the main class that simulates an image viewer application with a
 * cache. For each image, it checks whether the image is in the cache. If not,
 * it created a SingleImageReceiver object to build the image and then store it
 * in the cache. After getting the PacketLinkedList of the cache, we can simply
 * send it to the ImageDriver to open it.
 * 
 * @author honghui
 */
public class CacheImageApp {

	// Used to open an image from a SimplePacketList.
	// Keep it as field for performance to avoid having to
	// reconstruct the driver for each image.
	private ImageDriver img;

	// Used to store a list of PacketLists for completed images.
	// This means that we do not have to rebuild the PacketLinkedList for
	// images that we have already processed.
	private PacketLinkedList<CacheImage<SimplePacket>> cachePacketLinkedList;

	/**
	 * Constructs a CacheImageApp to build and return completed image linked
	 * lists for image files that have been transmitted. You need to initialize
	 * private members of this class.
	 */
	public CacheImageApp() {
		img = new ImageDriver();
		cachePacketLinkedList = new PacketLinkedList<CacheImage<SimplePacket>>();
	}

	/**
	 * Returns the CacheImage LinkedList, so that it may be tested independently
	 * of the rest of the program.
	 * 
	 * @return the CachePacket LinkedList
	 */
	public PacketLinkedList<CacheImage<SimplePacket>> getCachePacketLinkedList() {
		return cachePacketLinkedList;
	}

	/**
	 * Receive a image file. If the image is in the cache, it simply gets its
	 * packetLinkedList from the cache.
	 * 
	 * Otherwise, it creates a SingleImageReceiver object to receive the image
	 * and then stores it in the cache.
	 * 
	 * @param filename
	 *            the filename you want to receive
	 * @throws IOException
	 *             if the constructor of SingleImageReceiver fails and throw
	 *             IOException
	 * @throws InvalidImageException
	 *             if the image is not in the cache and the reconstructed list
	 *             is null
	 * 
	 * @return the packet list for the specific image file.
	 */
	public PacketLinkedList<SimplePacket> retrieveImage(String filename)
			throws IOException, InvalidImageException {
		
		
		PacketLinkedListIterator<CacheImage<SimplePacket>> itr = cachePacketLinkedList.iterator();
		CacheImage<SimplePacket> temp;

		// Find existing packet list if we have it in the cache
		// Keep the value of itr.next() by temp
		while (itr.hasNext()) {
			temp = itr.next();
			if (temp.getImageName().equals(filename)) {
				return temp.getPacketLinkedList();
			}
		}
		// If no PacketLinkedList found in the cache, must build one
		SingleImageReceiver receiver = new SingleImageReceiver(filename);	
		receiver.reconstructFile();
		
		// If the reconstructed list is null, throw InvalidImageException.
		// Otherwise add it to the cache for the next time.		
		if (receiver.getListBuffer() == null) {
			throw new InvalidImageException(filename);
		} else {
			temp = new CacheImage(filename, receiver.getListBuffer());
			cachePacketLinkedList.add(temp);
		}

		// Return the newly build packet list
		return receiver.getListBuffer();
	}

	/**
	 * Opens the image file by sending the packet list for the image to the
	 * ImageDriver.
	 * 
	 * @param packetLinkedList
	 *            packet list for the image
	 */
	public void openImage(PacketLinkedList<SimplePacket> packetLinkedList) {
		try {
			img.openImage(packetLinkedList);
		} catch (Exception e) {
			System.out.println("Unable to open image packet list");
			e.printStackTrace();
		}
	}

	/**
	 * Initiates a CacheImageApp to build and open images.
	 * 
	 * @param args
	 *            command line arguments
	 */
	public static void main(String[] args) {
		CacheImageApp app = new CacheImageApp();
		Scanner sc = new Scanner(System.in);
		String filename = "";
		while (!filename.equals("0")) {
			System.out.print("Enter 0 to quit or image filename: ");
			filename = sc.nextLine().trim();
			if (filename.equals("0")) {
				System.out.println("Program End.");
			} else if (filename.isEmpty()) {
				System.out.println("The input is empty. Please input a vaild vaule.");
			} else {
				try {
					System.out.println("Retrieve Image = " + filename);
					PacketLinkedList<SimplePacket> packetList = app
							.retrieveImage(filename);
					app.openImage(packetList);
				} catch (FileNotFoundException e) {
					System.out.println("Sorry, " + filename
							+ " can not be retrieved.");
				} catch (Exception e) {
					System.out.println("Sorry, something unexpected happened.");
					e.printStackTrace();  // uncomment during debugging only
				}
			}
		}
		sc.close();
	}
}
