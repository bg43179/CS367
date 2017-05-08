import java.io.*;

/**
 * The Huffman class drives encoding and decoding, taking command line arguments
 * of the form:
 * 
 *   <pre>
 *   {@code
 *   Huffman -encode original.txt encoded.bin
 *   Huffman -decode encoded.bin decoded.txt
 *   }
 *   </pre>
 * 
 * For encoding, the input file ("original.txt") should contain 8-bit ASCII
 * encoded text with a newline ('\n') after the last line. The output,
 * "encoded.bin", is a binary file representing the original file, encoded
 * according to the encoding key "encoded.bin.key".
 * 
 * For decoding, the input file ("encoded.bin") should contain the binary file
 * produced by the -encode option above. Another file, "encoded.bin.key", must
 * also be present in order to decode. The output, "decoded.txt", should be the
 * same as the input to the original -encode option.
 * 
 * DO NOT MODIFY.
 */
public class Huffman {
    public static void main(String[] args) {
        if(args.length != 3) {
            System.out.format(Config.USAGE_ERROR);
            System.exit(-1);
        }
        if(args[0].substring(0,2).equalsIgnoreCase("-e")) {

            int[] frequencies = {};
            try {
                frequencies = FrequencyParser.getFrequencies(args[1]);
            } catch (IOException e) {
                System.out.format(Config.INPUT_FILE_ERROR, args[1]);
                e.printStackTrace();
                System.exit(-1);
            }

            MinPriorityQueueADT mpq = new MinPriorityQueue();

            // insert the frequencies into the queue
            try {
                for(int i = 0; i < frequencies.length; i++) {
                    if(frequencies[i] > 0) {
                        mpq.insert(new HuffmanNode((char) i, frequencies[i]));
                    }
                }
            } catch (PriorityQueueFullException e) {
                System.out.format(Config.PQUEUE_FULL);
                e.printStackTrace();
                System.exit(-1);
            }

            HuffmanDB ht = null;
            try {
                ht = new HuffmanDB(mpq);
            } catch (PriorityQueueFullException e) {
                System.out.format(Config.TREE_BUILD_FAIL);
                e.printStackTrace();
                System.exit(-1);
            } catch (PriorityQueueEmptyException e) {
                System.out.format(Config.TREE_BUILD_FAIL);
                e.printStackTrace();
                System.exit(-1);
            } 

            String keyFile = args[2] + ".key";
            try {
                PrintWriter pw = new PrintWriter(keyFile);
                pw.write(ht.getKeyString());
                pw.close();
            } catch (IOException e) {
                System.out.format(Config.TREE_WRITE_FAIL, keyFile);
                e.printStackTrace();
                System.exit(-1);
            }

            HuffmanEncoder he = new HuffmanEncoder(ht);
            try {
                he.encode(args[1], args[2]);
            } catch (IOException e) {
                System.out.format(Config.ENCODED_FILE_WRITE_FAIL);
                e.printStackTrace();
                System.exit(-1);
            }

            System.out.format(Config.SUCCESSFUL_ENCODING, args[1], args[2]);

            try {
                File in = new File(args[1]);
                File out = new File(args[2]);
                File key = new File(keyFile);

                long lengthIn = in.length();
                long lengthOut = out.length();
                long lengthKey = key.length();

                System.out.format(Config.SUMMARY_INT, 
                                  "Original file size", 
                                  lengthIn
                                  );
                System.out.format(Config.SUMMARY_INT, 
                                  "Encoded file size", 
                                  lengthOut
                                  );
                System.out.format(Config.SUMMARY_INT, 
                                  "Encoding key file size", 
                                  lengthKey
                                  );
                System.out.format(Config.SUMMARY_PERCENT, 
                                  "Compression ratio", 
                                  100 * (1 - (((double) lengthOut + lengthKey)/
                                        lengthIn))
                                  );
            } catch (NullPointerException e) {
                e.printStackTrace();
                System.exit(-1);
            }
        } else if(args[0].substring(0,2).equalsIgnoreCase("-d")) {
            // decoding

            HuffmanDB ht = null;
            String keyFile = args[1] + ".key";

            try {
                ht = new HuffmanDB(keyFile);
            } catch (IOException e) {
                System.out.format(Config.KEY_FILE_PARSE_FAIL, keyFile);
                e.printStackTrace();
                System.exit(-1);
            }

            HuffmanDecoder hd = new HuffmanDecoder(ht);

            try {
                hd.decode(args[1], args[2]);
            } catch (IOException e) {
                System.out.format(Config.DECODED_FILE_WRITE_FAIL);
                e.printStackTrace();
                System.exit(-1);
            }

            System.out.format(Config.SUCCESSFUL_DECODING);
        } else {
            System.out.format(Config.INVALID_ARGUMENT, args[0]);
            System.exit(-1);
        }

    }
}
