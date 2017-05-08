import java.io.*;

/**
 * The HuffmanDecoder class is used to decode files, given a HuffmanDB. It
 * works by traversing the tree starting at the root node until it finds a
 * leaf. At the leaf, it writes the character to the file and continues until
 * the end of the file.
 */
public class HuffmanDecoder {
    
    // The root node of the tree.
    private HuffmanNode root;

    /**
     * Instantiates a new huffman decoder.
     *
     * @param t the HuffmanDB which should be used by this object to decode
     * files.
     */
    public HuffmanDecoder(HuffmanDB t) {
        root = t.getRoot();
    }

    /**
     * Decode.
     *
     * @param infile the binary file to read from
     * @param outfile the text file to write the decoded text to
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public void decode(String infile, String outfile) throws IOException {
        FileBitStream fbs = null;
        FileWriter outputStream = null;

        try {
            fbs = new FileBitStream(infile);
        } catch (IOException e) {
            System.out.format(Config.BITSTREAM_FILE_READ_FAIL, infile);
            e.printStackTrace();
            System.exit(-1);
        }

        try {
            outputStream = new FileWriter(outfile);

            HuffmanNode currNode = root;
            Boolean next;

            while(fbs.hasNext()) {
                next = fbs.next();
                currNode = next ? currNode.getRight() : currNode.getLeft();
                if(currNode.isLeaf()) {
                    // If we've reached EOF, then break out of the loop
                    if(currNode.getSymbol() == (char) 1){
                        break;
                    }
                    // Otherwise, write the char to file
                    outputStream.write(currNode.getSymbol());
                    currNode = root;
                }
            }

        } 
        finally {
            if (outputStream != null) {
                outputStream.close();
            }
            if (fbs != null) {
                fbs.close();
            }
        }

    }
}
