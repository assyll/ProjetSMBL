package generatorGrapheRefAleat;

import java.util.HashMap;
import java.util.Map;

public class NodeToCreate {

	private final String _name;
	private Map<String, Object> _attributs;
	private Map<Transition, NodeToCreate> _transitions;
	
	public NodeToCreate(String name) {
		_name = name;
		_attributs = new HashMap<String, Object>();
		_transitions = new HashMap<Transition, NodeToCreate>();
	}
	
	public String getName() {
		return _name;
	}
	
	public Map<Transition, NodeToCreate> getTransitions() {
		return _transitions;
	}
	
	public Map<String, Object> getAttributs() {
		return _attributs;
	}
	
	public void addTransition(Transition transition, NodeToCreate nodeEnd) {
		_transitions.put(transition, nodeEnd);
	}
	
	public void addAttribut(String key, Object value) {
		_attributs.put(key, value);
	}
	
}
