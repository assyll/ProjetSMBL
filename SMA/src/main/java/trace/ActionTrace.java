package trace;

import java.util.Map;

public class ActionTrace {
	private String _userName ;
	private Action _action;
	
	
	public ActionTrace(String userName){
		_userName = userName;
	}
	
	public ActionTrace(String userName, Action action) {
		_userName = userName;
		_action = action;
	}
	
	public String getUserName() {
		return _userName;
	}

	public void setUserName(String userName) {
		_userName = userName;
	}
	
	public Action getAction() {
		return _action;
	}

	public void setActionMap(Action action) {
		_action = action;
	}
	

	@Override
	public boolean equals(Object obj) {
		if(obj instanceof ActionTrace){
			if(_action.equals(((ActionTrace)obj).getAction()) && (_userName.equals(((ActionTrace)obj).getUserName())))
				return true;
			
			return false;
		}
		else {
			return false;
		}
	}
}
