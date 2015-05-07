package jSonAndGS;

@SuppressWarnings("serial")
public class FormatFichierException extends Exception {
	
	//cr�� une nouvelle instance de TypeFichierException
	public FormatFichierException(){}
	
	/*cr�� une nouvelle instance de TypeFichierException
	 * @param message Le message d�taillant l'exception */
	public FormatFichierException(String message){
		super(message);
	}
	
	/*cr�� une nouvelle instance de TypeFichierException
	 * @param cause L'exception � l'origine de cette exception */
	public FormatFichierException(Throwable cause){
		super(cause);
	}
	
	/*cr�� une nouvelle instance de TypeFichierException
	 * @param message Le message d�taillant l'exception
	 * @param cause L'exception � l'origine de cette exception */
	public FormatFichierException(String message, Throwable cause){
		super(message, cause);
	}
	
}
