import java.io.*;
import java.util.*;

/**
 * The Class HuffmanDB. It represents the output of the Huffman
 * algorithm. Each leaf node represents a symbol, and the code for that symbol
 * is the path from the root to the corresponding leaf. A 0 represents a move to
 * the left child, and a 1 represents a step to the right child.
 */
public class HuffmanDB {
        
        /** the root node of this Huffman tree */
        private HuffmanNode root;
        
        /** a map from characters to their binary encodings (i.e. a list of
         * booleans) under this tree */
        private Map<Character, List<Boolean>> keyMap;
        
        /**
         * Instantiates a new huffman tree, from a file containg a mapping from
         * symbols to binary codes. The format of the key file is lines
         * alternating between symbols and codes, i.e.  
         * <pre> 
         * {@code 
         * a 0101110 
         * b 101001000 
         * ...  
         * } 
         * </pre>
         *
         * @param keyFile 
         *      the name of the key file
         * @throws IOException
         *      if there is an issue while reading from the file
         */
        public HuffmanDB(String keyFile) throws IOException {
                root = new HuffmanNode();

                Scanner input = new Scanner(new File(keyFile));
                        
                while(input.hasNext()){
                    String symbolLine = input.nextLine();
                    String code = input.nextLine();

                    char symbol;

                    if(symbolLine.length() > 1) {
                        symbol = (char) 
                            ( (int) Config.specialCharMap.get(symbolLine) );
                    } else {
                        symbol = symbolLine.charAt(0);
                    }
                                
                    int index = 0;
                    HuffmanNode curr = root;
                    while (index < code.length()){
                        if(code.charAt(index) == '0'){
                            if (curr.getLeft() == null)
                                curr.setLeft(new HuffmanNode());
                                                
                            curr = curr.getLeft();
                        }
                        else{
                            if (curr.getRight() == null)
                                curr.setRight(new HuffmanNode());
                                                
                            curr = curr.getRight();     
                        }
                                        
                        index++;
                    }
                    curr.setSymbol(symbol);
                }
                        
                input.close();
                keyMap = buildCharMap();
        }
        
        /**
         * Instantiates a new Huffman tree. This constructor creates a tree from
         * a min priority queue containing leaf nodes, i.e. carries out the
         * actual Huffman tree making algorithm
         *
         * @param mpq an instance that inherits the MinPriorityQueueADT
         *      interface. It represents a min PQ containing all of the leaf nodes
         *      (the nodes which actually represent symbols) that this tree will
         *      contain.
         * @throws PriorityQueueEmptyException
         *      if the priority queue is empty when attempting to remove an element
         * @throws PriorityQueueFullException
         *      if the priority queue is full when attempting to add an element
         */
        HuffmanDB(MinPriorityQueueADT mpq) 
            throws PriorityQueueEmptyException, PriorityQueueFullException {

            HuffmanNode least, secondLeast;
                
            //this node represents an 'eof' character (actually a 'start of
            //heading', ASCII value (1), for which we need a code in order to
            //encode/decode binary files when encoding, we write all of the
            //actual codes, then add this code, then fill out the last byte
            //arbitrarily when decoding, after encountering this code, we know
            //to ignore the rest of the bits.
                
            HuffmanNode eof = new HuffmanNode((char) 1, 0);
            mpq.insert(eof);
                
            while(true){
                least = mpq.removeMin();
                if(mpq.isEmpty())
                    break;
                secondLeast = mpq.removeMin();
                        
                HuffmanNode parent = 
                    new HuffmanNode(
                                    least.getFrequency() + 
                                    secondLeast.getFrequency(), 
                                    least, 
                                    secondLeast
                                    );
                mpq.insert(parent);
            }
                
            root = least;
            keyMap = buildCharMap();
        }

        /**
         * Gets the root node of this HuffmanDB.
         *
         * @return 
         *      the root node
         */
        public HuffmanNode getRoot(){
                return root;
        }
        
        /**
         * Gets the key map, the mapping from symbols to binary codes
         * represented by this tree.
         *
         * @return 
         *      the key map
         */
        public Map<Character, List<Boolean>> getKeyMap(){
                return keyMap;
        }
        
        /**
         * Builds and returns the mapping from symbols to codes this tree
         * represents, in the form of an actual Java map
         *
         * @return 
         *      the Java map object
         */
        Map<Character, List<Boolean>> buildCharMap() {
                Map<Character, List<Boolean>> ret = 
                    new HashMap<Character, List<Boolean>>();

                // dfs
                Stack<HuffmanNode> s = new Stack<HuffmanNode>();
                Stack<ArrayList<Boolean>> paths = new Stack<ArrayList<Boolean>>();

                // which nodes have been marked
                Set<HuffmanNode> marked = new HashSet<HuffmanNode>();

                s.push(root);
                paths.push(new ArrayList<Boolean>());

                while(!s.isEmpty()) {
                        HuffmanNode node = s.pop();
                        List<Boolean> currPath = paths.pop();
                        
                        if(!marked.contains(node)) {
                        marked.add(node);
                        
                                // we found a char! add it to the hashmap
                                if(node.isLeaf()) {
                                        ret.put(node.getSymbol(), 
                                                new ArrayList<Boolean>(currPath));
                                        continue;
                                }

                                if(node.getRight() != null) {
                                        s.push(node.getRight());
                                        ArrayList<Boolean> nextPath = 
                                            new ArrayList<Boolean>(currPath);
                                        nextPath.add(true);
                                        paths.push(nextPath);
                                }
                                if(node.getLeft() != null) {
                                        s.push(node.getLeft());
                                        ArrayList<Boolean> nextPath = 
                                            new ArrayList<Boolean>(currPath);
                                        nextPath.add(false);
                                        paths.push(nextPath);
                                }
                        }
                }

                return ret;
    }
        
        /**
         * Forms a a string which is a table of symbols and codes, on
         * alternating lines, in the format below. For printable characters
         * (ascii values strictly greater than 32), the symbol is the actual
         * ascii character. For nonprintable characters, the symbol is a short `
         * * character code. See config.java for the mapping from nonprintable
         * characters to the strings that appear in these files.  <pre> {@code
         * <symbol 1> <code 1> <symbol 2> <code 2> ...  <symbol n> <code n> }
         * </pre>
         *
         * @return
         *      the key string
         */
        public String getKeyString(){
                String s = "";
                List<Character> chars = new ArrayList<Character>(keyMap.keySet());
                Collections.sort(chars);
                
                for(Character c : chars) {
                    // Special characters are 0(NUL)-32(space), inclusive
                    if( (int) c > 127) {
                        throw new RuntimeException(String.format("Character out " +
                            "of bounds! ch: %c; (int) ch: %d\n", c, (int) c ));
                    } else if( (int) c > 32) {
                        // normal, printable character --> add character to string
                        s += c + "\n";
                    } else {
                        // non-printable character --> add printable version of
                        // character to string
                        s += Config.specialChars[(int) c] + "\n";
                    }
                    for(Boolean b : keyMap.get(c)) {
                        if(b) {
                            s += "1";
                        } else {
                            s += "0";
                        }
                    }
                    s += "\n";
                }
                
                return s;
        }
}
