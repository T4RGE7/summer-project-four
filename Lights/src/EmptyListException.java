

/**
 * 
 * @author James Roberts jpr242
 *
 */
public class EmptyListException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2843800313498103350L;

	/**
	 * Creates a new EmptyListException with the default message
	 */
	public EmptyListException() {
		super("This list is Empty");
	}
	/**
	 * Creates a new EmptyListException with the desired message
	 * @param message The message to use
	 */
	public EmptyListException(String message) {
		super(message);
	}
	
}
