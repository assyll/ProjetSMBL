package generator;

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
	
	@Override
	public String toString() {
		String string = "";
		for (Action action: _actions) {
			string += (action != null) ? action + " -> " : "X";
		}
		return string += _end;
	}
	
}
