/**
 * Exception for UnknownMovieRecordAttribute field.
 *
 * @author CS367
 */
public class UnknownMovieRecordAttributeException extends RuntimeException{
    
	private static final long serialVersionUID = 1L;

	public UnknownMovieRecordAttributeException(String attribute){
        super("Unknown Movie Record attribute: " + attribute);
    }
}