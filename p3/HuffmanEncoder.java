import java.util.*;
import java.io.*;

/**
 * The Class HuffmanEncoder is used to encode files, given a HuffmanDB. It
 * works by taking each character in the input file, converting it to the
 * Huffman encoding, and adding writing those bits to the output file.
 */
public class HuffmanEncoder {
    HuffmanNode root;
    private Map<Character, List<Boolean>> charMap;

    /**
     * Instantiates a new huffman encoder, given the HuffmanDB. Set the root
     * node and get the character mapping from char:bit sequence,
     * e.g. "e":[0,1,1]
     *
     * @param t the t
     */
    public HuffmanEncoder(HuffmanDB t) {
        root = t.getRoot();

        // Map<char, List<Boolean>> indicating what the bit sequence for each
        // character is
        charMap = t.getKeyMap();
    }

    /**
     * Encode the (text) file pointed to by infile, and write out the (binary)
     * file pointed to by outfile.
     *
     * @param infile the file to read and encode
     * @param outfile the file to write the binary encoding to
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public void encode(String infile, String outfile) throws IOException {
        
        Scanner input = null;
        FileBitWriter bw = null;

        try {
            // Open input file for reading characters
            input = new Scanner(new File(infile));

            // Open output file for writing bits
            bw = new FileBitWriter(outfile);

            while(input.hasNext()) {
                // Make sure to add in a newline, which was removed by
                // nextLine()
                for(char c : (input.nextLine() + '\n').toCharArray()) {
                    checkAndWrite(c, bw);
                }
            }
            // Add EOF char so that we know when we reach the end of the file
            // when decoding.
            checkAndWrite((char) 1, bw);
        } finally {
            // Close input file
            if (input != null) {
                input.close();
            }

            // Write output file to disk
            bw.close();
        }
    }

    /**
     * Writes a character c to a FileBitWriter. Checks that the character is in
     * the Map that represents the mapping between characters and their bit
     * sequence according to the Huffman encoding.
     *
     * @param c the character to be written
     * @param bw the FileBitWriter to output the binary equivalent of this
     * character to
     */
    private void checkAndWrite(char c, FileBitWriter bw) {
        if(!charMap.containsKey(c)) {
            System.out.format(Config.ASCII_CHAR_NOT_FOUND, 
                              c > 32 ? 
                              c + "" : 
                              Config.specialChars[(int) c]);
            System.exit(-1);
        }
        bw.write(charMap.get(c));
    }
}
