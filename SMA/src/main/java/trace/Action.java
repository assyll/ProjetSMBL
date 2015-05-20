package trace;

import java.util.Map;

public class Action {
	private String _userName ;
	private Map<String,String> _actionMap;
	
	public Action(String userName){
		_userName = userName;
	}
	
	public Action(String userName, Map<String,String> actionMap) {
		_userName = userName;
		_actionMap = actionMap;
	}
	
	public String getUserName() {
		return _userName;
	}

	public void setUserName(String userName) {
		_userName = userName;
	}
	
	public Map<String, String> getActionMap() {
		return _actionMap;
	}

	public void setActionMap(Map<String, String> actionMap) {
		_actionMap = actionMap;
	}
	
	public void setNewActionField(String field, String value)
	{
		_actionMap.put(field, value);
	}
}
