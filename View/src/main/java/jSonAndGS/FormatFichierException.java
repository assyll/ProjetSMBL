package jSonAndGS;

@SuppressWarnings("serial")
public class FormatFichierException extends Exception {
	
	//créé une nouvelle instance de TypeFichierException
	public FormatFichierException(){}
	
	/*créé une nouvelle instance de TypeFichierException
	 * @param message Le message détaillant l'exception */
	public FormatFichierException(String message){
		super(message);
	}
	
	/*créé une nouvelle instance de TypeFichierException
	 * @param cause L'exception à l'origine de cette exception */
	public FormatFichierException(Throwable cause){
		super(cause);
	}
	
	/*créé une nouvelle instance de TypeFichierException
	 * @param message Le message détaillant l'exception
	 * @param cause L'exception à l'origine de cette exception */
	public FormatFichierException(String message, Throwable cause){
		super(message, cause);
	}
	
}
