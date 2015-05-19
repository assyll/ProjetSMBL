package generatorTracesTest;

import java.util.ArrayList;
import java.util.List;

public class Trace {
	
	public final static String _end = "END";

	private List<Action> _actions;
	
	public Trace() {
		_actions = new ArrayList<Action>();
	}
	
	public void addAction(Action action) {
		_actions.add(action);
	}
	
	public List<Action> getActions() {
		return _actions;
	}
	
	@Override
	public String toString() {
		String string = "";
		for (Action action: _actions) {
			string += (action != null) ? action + " -> " : "";
		}
		return string += _end;
	}
	
	@Override
	public boolean equals(Object obj) {
		return (obj instanceof Trace)
				? toString().equals(((Trace)obj).toString()) : false;
	}
	
}
