/**
 * Exception for Invalid Index.
 *
 * @author apul
 */
public class InvalidIndexException extends RuntimeException{
    
	private static final long serialVersionUID = 1L;

	public InvalidIndexException(String index){
        super("Unknown Index Exception: " + index);
    }
}

