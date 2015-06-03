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
}
