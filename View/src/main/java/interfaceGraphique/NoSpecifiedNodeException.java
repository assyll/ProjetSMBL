package interfaceGraphique;

@SuppressWarnings("serial")
public class NoSpecifiedNodeException extends Exception {

	/**
	 * créé une nouvelle instance de TypeFichierException
	 */
	public NoSpecifiedNodeException() {
	}

	/**
	 * créé une nouvelle instance de TypeFichierException
	 * 
	 * @param message
	 *            Le message détaillant l'exception
	 */
	public NoSpecifiedNodeException(String message) {
		super(message);
	}

	/**
	 * créé une nouvelle instance de TypeFichierException
	 * 
	 * @param cause
	 *            L'exception à l'origine de cette exception
	 */
	public NoSpecifiedNodeException(Throwable cause) {
		super(cause);
	}

	/**
	 * créé une nouvelle instance de TypeFichierException
	 * 
	 * @param message
	 *            Le message détaillant l'exception
	 * 
	 * @param cause
	 *            L'exception à l'origine de cette exception
	 */
	public NoSpecifiedNodeException(String message, Throwable cause) {
		super(message, cause);
	}

}
