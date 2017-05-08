import java.io.*;
import java.util.*;

/**
 * The FileBitStream class is for reading a binary input file, bit by bit. It
 * implements Iterator, and therefore has a next() method that should be used to
 * traverse the bits in the file.
 */
public class FileBitStream implements Iterator<Boolean> {
    
    /** The input binary file. */
    FileInputStream in;
    
    /** The current byte being looked at. */
    int currByte;
    
    /** The current bit being looked at. */
    int currBit;

    /**
     * Instantiates a new file bit stream. Opens the file with the pathname
     * represented by infile, and reads the first byte.
     *
     * @param infile the infile
     * @throws IOException if opening the input file fails, or reading the first
     * byte fails
     */
    public FileBitStream(String infile) throws IOException {
        currBit = 1;
        in = new FileInputStream(infile);
        currByte = in.read();
    }

    /* (non-Javadoc)
     * @see java.util.Iterator#hasNext()
     */
    public boolean hasNext() {
        return in != null && currByte != -1;
    }

    /* 
     * Reads and returns the next bit in the file.
     * @see java.util.Iterator#next()
     */
    public Boolean next() {
        boolean res = checkBit((byte) currByte, currBit);
        if(currBit == 8) {
            try {
                currByte = in.read();
            } catch (IOException e) {
                System.out.format(Config.BITSTREAM_READ_BYTE_FAIL);
                e.printStackTrace();
                System.exit(-1);
            }
        }
        currBit = (currBit % 8) + 1;
        return res;
    }

    /* (non-Javadoc)
     * @see java.util.Iterator#remove()
     */
    public void remove() {
        throw new RuntimeException();
    }

    /**
     * Given a byte b (8 bits), returns true if the bit'th bit in b is set to 1,
     * otherwise returns false.
     *
     * e.g. checkBit(00000010, 7) == true, checkBit(00000010, 8) == false
     * @param b the byte to be checked
     * @param bit the position to check it at
     * @return true, if the bit'th bit in b is set; false otherwise
     */
    private static boolean checkBit(byte b, int bit)
    {
        return (b & (1 << (8-bit))) != 0;
    }

    /**
     * Close the input file.
     * @throws IOException if closing the file fails.
     */
    public void close() throws IOException {
        if (in != null) {
            in.close();
        }
    }
}
