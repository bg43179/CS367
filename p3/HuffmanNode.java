import java.lang.Comparable;
import java.lang.RuntimeException;

/**
 * The class HuffmanNode represents an ASCII symbol. It has fields for the the
 * symbol itself and its frequency. It also has fields for two children
 * HuffmanNode objects, because this class will be used as a vertex in a Huffman
 * tree.
 */
public class HuffmanNode implements Comparable<HuffmanNode>{
        
        /** The symbol. */
        private char symbol;
        
        /** The frequency. */
        private int frequency;
        
        /** The right and left children. */
        private HuffmanNode left, right;
        
        /**
         * Instantiates a new huffman node, which is a leaf node. 
         * Leaf nodes should have an ASCII symbol, and not have any child.
         *
         * @param symbol
         *      the symbol of the node which is to be created
         * @param frequency
         *      the frequency of this node's symbol
         */
        public HuffmanNode(char symbol, int frequency){
                this.symbol = symbol;
                this.frequency = frequency;
                left = null;
                right = null;
        }
        
        /**
         * Instantiates a new huffman node, which is an internal node. Internal
         * nodes should have a frequency and children, but no symbol (the null
         * character is used to denote the fact that there is no 'actual' 
         * symbol here)
         *
         *@param frequency
         *      the frequency of this node 
         *@param left
         *      the left child of this node
         *@param right
         *      the right child of this node
         */
        public HuffmanNode(int frequency, HuffmanNode left, HuffmanNode right){
                symbol = '\0';
                this.frequency = frequency;
                this.left = left;
                this.right = right;
        }
        
        /**
         * Instantiates a new, empty Huffman node. This is used when building a
         * HuffmanDB from a file containing a table of symbols and codes
         */
        public HuffmanNode(){
                left = null;
                right = null;
        }
        
        /**
         * Gets the symbol of this node.
         *
         * @return
         *      the symbol
         */
        public char getSymbol(){
                return symbol;
        }
        
        /**
         * Gets the frequency of this node's symbol.
         *
         * @return
         *      the frequency
         */
        public int getFrequency(){
                return frequency;
        }
        
        /**
         * Sets the symbol of this node
         *
         * @param s
         *      the new symbol
         */
        public void setSymbol(char s){
                this.symbol = s;
        }
        
        /**
         * Gets the left child of this node.
         *
         * @return
         *      the left child
         */
        public HuffmanNode getLeft(){
                return left;
        }
        
        /**
         * Gets the right child of this node.
         *
         * @return
         *      the right child
         */
        public HuffmanNode getRight(){
                return right;
        }
        
        /**
         * Sets the left child of this node.
         *
         * @param l
         *      the left child
         */
        public void setLeft(HuffmanNode l){
                this.left = l;
        }
        
        /**
         * Sets the right child of this node.
         *
         * @param r 
         *      the right child
         */
        public void setRight(HuffmanNode r){
                this.right = r;
        }
        
        /**
         * Checks if this node is a leaf node (it has no children).
         *
         * @return 
         *      true, if and only if this node is a leaf node 
         */
        public boolean isLeaf(){
                return left == null && right == null;
        }
        
        /* Returns a negative integer if this node's frequency is less than the
         * frequency of the parameter's node, and a positive integer if this
         * node's frequency is larger. If they are equal, the (ascii values of
         * the) symbols are compared in the same fashion.
         * 
         * @see java.lang.Comparable#compareTo(java.lang.Object)
         * 
         * @return a positive or negative integer representing the result of a
         *      comparison of two nodes
         */
        public int compareTo(HuffmanNode cf) {
                if(symbol != '\0' && 
                   symbol != ((char) 1) && 
                   cf.getSymbol() == symbol) {
                        throw new RuntimeException();
                }

                int cfFreq = cf.getFrequency();
                if(this.frequency < cfFreq) {
                        return -1;
                }
                else if (this.frequency > cfFreq) {
                        return 1;
                }
                else {
                    // Choose character nodes over internal nodes if the
                    // frequencies are tied.
                    /*if(symbol == '\0') {
                        return 1;
                    } else if(cf.getSymbol() == '\0') {
                        return -1;
                        }*/
                    return symbol < cf.getSymbol() ? -1 : 1;
                }
    }
}
