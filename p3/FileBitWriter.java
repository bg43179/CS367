import java.util.*;
import java.io.*;

/**
 * The FileBitWriter class enables writing a binary file given a sequence of
 * bits.
 */
public class FileBitWriter {
    
    /** The output file stream. */
    private FileOutputStream out;
    
    /** The current byte count. */
    private int byteCount;
    
    /** The current bit number within the byte. */
    private int bit;

    /** The bit sequence that will be written out to file. */
    private BitSet bits;

    /**
     * Instantiates a new BitFileWriter, given an output filename.
     *
     * @param outfile the filename of the file which should be written upon
     * close().
     * @throws IOException if it fails to open the output file
     */
    public FileBitWriter(String outfile) throws IOException {
        out = new FileOutputStream(outfile);
        byteCount = 0;
        bit = 0;
        bits = new BitSet();
    }

    /**
     * Writes the sequence of bits described by bitSeq out to the filename given
     * in the constructor.
     *
     * @param bitSeq the sequence of bits to write to file
     */
    public void write(List<Boolean> bitSeq) {
        for(Boolean b : bitSeq) {
            if(bit == 8) {
                bit = 0;
                byteCount++;
            }
                        
            bits.set((byteCount*8)+(7-bit), b);
            bit++;
        }
    }
    
    /**
     * Closes the file.
     * @throws IOException if it fails to close the file
     */
    public void close() throws IOException {
        out.write(bits.toByteArray());
        out.close();
    }
}
