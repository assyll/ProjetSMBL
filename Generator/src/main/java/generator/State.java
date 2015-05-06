package generator;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

public class State {

	private String _name;
	private boolean _isFinal;
	private Map<Action, State> _possiblesActions;
	
	public State(String name, boolean isFinal) {
		_name = name;
		_isFinal = isFinal;
		_possiblesActions = new HashMap<Action, State>();
	}
	
	public void addAction(Action action, State state) {
		if (!_possiblesActions.containsKey(action)) {
			_possiblesActions.put(action, state);
		}
	}
	
	public String getName() {
		return toString();
	}
	
	public boolean isFinal() {
		return _isFinal;
	}
	
	/**
	 * 
	 * @return action aleatoire ou null pour FIN
	 */
	public Action getActionAleat() {
		
		Action actionAleat = null;
		
		int max = _possiblesActions.size() + (_isFinal ? 1 : 0); // max exclus
		int nbAleat = new Random().nextInt((max > 0) ? max : 1);
		
		if (_possiblesActions.size() == 0) {
			return actionAleat;
		}
				
		Iterator<Action> iterator = getActions();
		
		for (int i = 0; i <= nbAleat && iterator.hasNext(); i++) {
			actionAleat = iterator.next();
		}
				
		return actionAleat;
	}
	
	public Iterator<Action> getActions() {
		return _possiblesActions.keySet().iterator();
	}

	public State executeAction(Action action) {
		return (action != null) ? _possiblesActions.get(action) : null;
	}
	
	@Override
	public boolean equals(Object obj) {
		return (obj instanceof State)
				? _name.equals(((State)obj).getName()) : false;
	}
	
	@Override
	public String toString() {
		return _name;
	}
	
}
