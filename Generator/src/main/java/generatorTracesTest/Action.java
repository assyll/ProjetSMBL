package generatorTracesTest;

import java.util.HashMap;
import java.util.Map;

public class Action {

	private String _name;
	private Map<String, Object> _proprietes;
	
	private State _stateEnd;
	private State _stateStart;
	
	private boolean _visited;
	
	public Action(String name, State stateStart, State stateEnd) {
		_name = name;
		_visited = false;
		_stateEnd = stateEnd;
		_stateStart = stateStart;
		_proprietes = new HashMap<String, Object>();
	}
	
	public State getStateEnd() {
		return _stateEnd;
	}
	
	public State getStateStart() {
		return _stateStart;
	}
	
	public Map<String, Object> getProprietes() {
		return _proprietes;
	}
	
	public boolean isVisited() {
		return _visited;
	}
	
	public void setVisited(boolean visited) {
		_visited = visited;
	}
	
	public void addProprietes(String key, Object value) {
		_proprietes.put(key, value);
	}
	
	@Override
	public String toString() {
		return _name;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Action)) {
			return false;
		} else {
			Action a2 = (Action) obj;
			return _name.equals(a2._name)
					&& _stateEnd.equals(a2._stateEnd)
					&& _stateStart.equals(a2._stateStart);
		}
	}
	
}
