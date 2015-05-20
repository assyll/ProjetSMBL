package interfaceGraphique;

@SuppressWarnings("serial")
public class NoSpecifiedNodeException extends Exception {

	/**
	 * cr�� une nouvelle instance de TypeFichierException
	 */
	public NoSpecifiedNodeException() {
	}

	/**
	 * cr�� une nouvelle instance de TypeFichierException
	 * 
	 * @param message
	 *            Le message d�taillant l'exception
	 */
	public NoSpecifiedNodeException(String message) {
		super(message);
	}

	/**
	 * cr�� une nouvelle instance de TypeFichierException
	 * 
	 * @param cause
	 *            L'exception � l'origine de cette exception
	 */
	public NoSpecifiedNodeException(Throwable cause) {
		super(cause);
	}

	/**
	 * cr�� une nouvelle instance de TypeFichierException
	 * 
	 * @param message
	 *            Le message d�taillant l'exception
	 * 
	 * @param cause
	 *            L'exception � l'origine de cette exception
	 */
	public NoSpecifiedNodeException(String message, Throwable cause) {
		super(message, cause);
	}

}
