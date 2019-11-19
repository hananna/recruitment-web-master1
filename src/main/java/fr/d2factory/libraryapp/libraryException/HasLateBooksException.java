package fr.d2factory.libraryapp.libraryException;

/**
 * This exception is thrown when a member who owns late books tries to borrow another book
 */
public class HasLateBooksException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1810210460394102844L;
	/**
	 * Constructor : create an ExecutionException with message of exception
	 * parameter
	 * 
	 * @param exception
	 *            the exception to get the message
	 */
	public HasLateBooksException(Exception exception) {
		super(exception.getMessage(), exception);
	}

	/**
	 * Constructor : create an ExecutionException with message
	 * 
	 * @param message
	 *            the message to set to the exception
	 */
	public HasLateBooksException(String message) {
		super(message);
	}

	/**
	 * Constructor : create an ExecutionException with message and exception
	 * 
	 * @param message
	 * @param e
	 */
	public HasLateBooksException(String message, Exception e) {
		super(message, e);
	}

}
