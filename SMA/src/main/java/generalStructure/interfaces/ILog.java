package generalStructure.interfaces;

import java.util.Map;

public interface ILog {

	public void ecrire(Map<String, String> informations);
	
	public void closeFile();
	
}
