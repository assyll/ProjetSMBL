package generator;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

public class State {

	private String _name;
	private Map<Action, State> _possiblesActions;
	
	public State(String name) {
		_name = name;
		_possiblesActions = new HashMap<Action, State>();
	}
	
	public void addAction(Action action, State state) {
		if (!_possiblesActions.containsKey(action)) {
			_possiblesActions.put(action, state);
		}
	}
	
	public String getName() {
		return _name;
	}
	
	/**
	 * 
	 * @return action aleatoire ou null pour FIN
	 */
	public Action getActionAleat() {
		int nbAleat = new Random().nextInt(_possiblesActions.size() + 1);
		
		Action actionAleat = null;
		Iterator<Action> iterator = _possiblesActions.keySet().iterator();
		
		for (int i = 0; i <= nbAleat && i < _possiblesActions.size(); i++) {
			actionAleat = iterator.next();
		}
		
		return actionAleat;
	}

	public State executeAction(Action actionAleat) {
		return _possiblesActions.get(actionAleat);
	}
	
	@Override
	public boolean equals(Object obj) {
		return (obj instanceof State)
				? _name.equals(((State)obj).getName()) : false;
	}
	
}
