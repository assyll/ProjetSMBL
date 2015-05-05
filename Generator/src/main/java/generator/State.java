package generator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class State {

	private String _name;
	private List<Action> _possiblesActions;
	
	public State(String name) {
		_name = name;
		_possiblesActions = new ArrayList<Action>();
	}
	
	public void addAction(Action action) {
		if (!_possiblesActions.contains(action)) {
			_possiblesActions.add(action);
		}
	}
	
	public String getName() {
		return _name;
	}
	
	public Action getActionAleat() {
		if (_possiblesActions.size() == 0) {
			return null;
		}
		
		int i = new Random().nextInt(_possiblesActions.size() + 1);
		return (i < _possiblesActions.size())
				? _possiblesActions.get(i) : null;
	}
	
}
