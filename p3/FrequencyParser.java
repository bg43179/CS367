///////////////////////////////////////////////////////////////////////////////
// Title:            CS 367 Programming Assignment 3: Huffman Coding
// Files:            MinPriorityQueue.java, FrequencyPraser.java
// Semester:         CS367, Fall 2016
//
// Author:           Yahn-Chung Chen
// Email:            chen666@wisc.edu
// CS Login:         yahn-chung
// Lecturer's Name:  Deb Deppeler
///////////////////////////////////////////////////////////////////////////////

import java.io.*;
import java.util.*;

public class FrequencyParser{
    /**
     * This method takes a text file containing ASCII characters only and
     * returns an int array of length 128 which counts the occurrences of each
     * character.  
     * 
     * The entry at index i is the count of the character with ASCII
     * value i 'Start of Heading' (ASCII value 1) and null character (ASCII
     * value 0) should both have count 0.
     * 
     * @param file 
     *  the name of the file containing the mapping from symbols to codes
     * 
     * @return the array of frequencies of each character
     * 
     * @throws FileNotFoundException 
     *  if the file does not exist
     */
    public static int[] getFrequencies(String file) throws FileNotFoundException{
        
        Scanner input = new Scanner(new File(file));
        // Use an array to count frquency
        int[] freq = new int[128];
        char ch;
        int asciiVal = 0;
        String temp;
        
        while (input.hasNextLine()) {
            temp = input.nextLine();
            // add the frequency of \n 
            freq[10]++;
            for (int i = 0; i < temp.length(); i++) {
                ch = temp.charAt(i);
                asciiVal = (int) ch;
                freq[asciiVal]++; 
            }
        }
        input.close();
        return freq;
    }
}