package generatorTracesTestAleat;

public class Action {

	private String _name;
	private State _stateEnd;
	private State _stateStart;
	
	private boolean _visited;
	
	public Action(String name, State stateStart, State stateEnd) {
		_name = name;
		_visited = false;
		_stateEnd = stateEnd;
		_stateStart = stateStart;
	}
	
	public State getStateEnd() {
		return _stateEnd;
	}
	
	public State getStateStart() {
		return _stateStart;
	}
	
	public boolean isVisited() {
		return _visited;
	}
	
	public void setVisited(boolean visited) {
		_visited = visited;
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
