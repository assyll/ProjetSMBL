package jsonAndGS;

@SuppressWarnings("serial")
public class FileFormatException extends Exception {

	/**
	 * cr�� une nouvelle instance de FileFormatException
	 */
	public FileFormatException() {
	}

	/**
	 * cr�� une nouvelle instance de FileFormatException
	 * 
	 * @param message
	 *            Le message d�taillant l'exception
	 */
	public FileFormatException(String message) {
		super(message);
	}

	/**
	 * cr�� une nouvelle instance de FileFormatException
	 * 
	 * @param cause
	 *            L'exception � l'origine de cette exception
	 */
	public FileFormatException(Throwable cause) {
		super(cause);
	}

	/**
	 * cr�� une nouvelle instance de FileFormatException
	 * 
	 * @param message
	 *            Le message d�taillant l'exception
	 * 
	 * @param cause
	 *            L'exception � l'origine de cette exception
	 */
	public FileFormatException(String message, Throwable cause) {
		super(message, cause);
	}

}
