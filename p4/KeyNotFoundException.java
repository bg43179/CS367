/**
 * Exception for Key not found.
 *
 * @author CS367
 */
public class KeyNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public KeyNotFoundException() {
	    super("Key not found in the IndexTree.");
	}
}