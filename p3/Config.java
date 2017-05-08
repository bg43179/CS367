import java.util.*;

/**
 * The Config class stores various configuration parameters, including error
 * strings to be printed and a map for special characters to human-readable
 * format (and vice versa).
 */
public class Config{
    
    public static String ASCII_CHAR_NOT_FOUND = 
        "Character to boolean list mapping doesn't contain a mapping for " +
        "ASCII: %s\n";
    public static String SUMMARY_INT = "%s:\t%d B\n";
    public static String SUMMARY_PERCENT = "%s:\t%f%%\n";
    public static String BITSTREAM_READ_BYTE_FAIL = 
        "Couldn't read a byte from input file.\n";
    public static String BITSTREAM_FILE_READ_FAIL = 
        "Couldn't find the input file (%s) for input, or failed to read the " +
        "first byte.\n";
    public static String INVALID_ARGUMENT = "Invalid arg: %s\n";
    public static String DECODED_FILE_WRITE_FAIL = 
        "Failed to write decoded output to file.\n";
    public static String KEY_FILE_PARSE_FAIL = 
        "Failed to parse key file %s.\n";
    public static String ENCODED_FILE_WRITE_FAIL = 
        "Failed to write encoded file.\n";
    public static String TREE_WRITE_FAIL = "Failed to write key to file: %s.\n";
    public static String TREE_BUILD_FAIL = "Failed to build Huffman Tree.\n";
    public static String PQUEUE_FULL = 
        "Failed to insert character frequency to the MinPriorityQueue.\n";
    public static String INPUT_FILE_ERROR = "Failed to read file %s.\n";
    public static String USAGE_ERROR = 
        "usage: java Huffman -encode input.txt output.bin, or java Huffman " +
        "-decode input.bin output.out\n";
    public static String SUCCESSFUL_ENCODING = 
        "Input file: %s encoded successfully!\nOutput file: %s\n";
    public static String SUCCESSFUL_DECODING = "File decoded successfully!\n";
        
    static final Map<String, Integer> specialCharMap;
    static final String[] specialChars = {"NUL","EOF","STX","ETX","EOT","ENQ",
                                          "ACK","BEL","BS","\\t","\\n","VT",
                                          "FF","\\r","SO","SI","DLE","DC1",
                                          "DC2","DC3","DC4","NAK","SYN","ETB",
                                          "CAN","EM","SUB","ESC","FS","GS","RS",
                                          "US", "(space)"};

    static {
        specialCharMap = new HashMap<String, Integer>();
        // special chars are ASCII values 0-32, inclusive
        for(int i = 0; i < specialChars.length; i++) {
            specialCharMap.put(specialChars[i], i);
        }
    }

}
