package generatorGrapheRefAleat;

import java.util.HashMap;
import java.util.Map;

public class Transition {

	private final String _name;
	private Map<String, String> proprietes;
	
	public Transition(String name) {
		_name = name;
		proprietes = new HashMap<String, String>();
	}
	
	public String getName() {
		return _name;
	}
	
	public void addProprietes(String key, String value) {
		proprietes.put(key, value);
	}
	
	public Map<String, String> getProprietes() {
		return proprietes;
	}
}
