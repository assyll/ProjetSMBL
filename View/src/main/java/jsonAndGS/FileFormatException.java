package jsonAndGS;

@SuppressWarnings("serial")
public class FileFormatException extends Exception {
	
	//créé une nouvelle instance de TypeFichierException
	public FileFormatException(){}
	
	/*créé une nouvelle instance de TypeFichierException
	 * @param message Le message détaillant l'exception */
	public FileFormatException(String message){
		super(message);
	}
	
	/*créé une nouvelle instance de TypeFichierException
	 * @param cause L'exception à l'origine de cette exception */
	public FileFormatException(Throwable cause){
		super(cause);
	}
	
	/*créé une nouvelle instance de TypeFichierException
	 * @param message Le message détaillant l'exception
	 * @param cause L'exception à l'origine de cette exception */
	public FileFormatException(String message, Throwable cause){
		super(message, cause);
	}
	
}
