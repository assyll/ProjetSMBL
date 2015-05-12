package generatorGrapheRefAleat;

import java.util.HashMap;
import java.util.Map;

public class NodeToCreate {

	private final String _name;
	private Map<Transition, NodeToCreate> _transitions;
	
	public NodeToCreate(String name) {
		_name = name;
		_transitions = new HashMap<Transition, NodeToCreate>();
	}
	
	public String getName() {
		return _name;
	}
	
	public Map<Transition, NodeToCreate> getTransitions() {
		return _transitions;
	}
	
	public void addTransition(Transition transition, NodeToCreate nodeEnd) {
		_transitions.put(transition, nodeEnd);
	}
	
}
