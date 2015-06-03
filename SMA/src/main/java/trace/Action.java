package trace;

import java.util.HashMap;
import java.util.Map;

public class Action {
	private Map<String,String> _actionMap;
	
	public Action() {
		_actionMap = new HashMap<String, String>();
	}
	
	public void setNewActionField(String field, String value)
	{
		_actionMap.put(field, value);
	}

	public Map<String, String> getActionMap() {
		return _actionMap;
	}

	public void setActionMap(Map<String, String> actionMap) {
		this._actionMap = actionMap;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Action){
			Map<String,String> actionMap2 = ((Action) obj)._actionMap; 
			
			if(! ( (_actionMap.keySet().size() == actionMap2.size()) && 
					_actionMap.keySet().containsAll(actionMap2.keySet()) && 
					actionMap2.keySet().containsAll(_actionMap.keySet())))
				return false;
			
			for(String key: _actionMap.keySet()){
				if(!_actionMap.get(key).equals(actionMap2.get(key)))
						return false;
			}
			
			return true;
		}
		else {
			return false;
		}
	}
}
