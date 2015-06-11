package agents.impl.state;

import general.Memory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import trace.Action;
import trace.ActionTrace;
import agents.impl.RequestMessage;
import agents.impl.ResponseMessage;
import agents.interfaces.StateMemory;

public class StateMemImpl extends Memory<StateMemory> implements StateMemory {

	private String _stateId, _fatherTransId;
	private boolean _waitingForTraceElmt, _gotRequest, _waitingForResponse, _gotResponse, _actionsToProcess;
	private List<String> _userNameWaitingForTraceList;
	private List<ActionTrace> _actionMap;
	private RequestMessage _requestMsg;
	private ResponseMessage _responseMsg;
	private boolean _isRoot;
	private Map<Action,String> _inputTransitionIdByAction;
	private Map<String,String> _stateChildIdByTransId;
	private int _userNameIndex;

	public StateMemImpl(String id, boolean isRoot) {
		_stateId = id;
		this._isRoot = isRoot;
		_waitingForTraceElmt = false;
		_gotRequest = false;
		_waitingForResponse = false;
		_userNameWaitingForTraceList = new ArrayList<String>();
		_actionMap = new LinkedList<ActionTrace>();
		_requestMsg = null;
		_responseMsg = null;
		_gotResponse = false;
		_stateChildIdByTransId = new HashMap<String, String>();
		_inputTransitionIdByAction = new HashMap<Action, String>();
		_actionsToProcess = false;
		_userNameIndex = 0;
	}



	@Override
	protected StateMemory make_infos() {
		return this;
	}



	@Override
	public void setNextTraceElmtUserName(String userName) {
		_userNameWaitingForTraceList.add(userName);
	}

	@Override
	public void setRequestMessage(RequestMessage msg) {
		_gotRequest = true;
		_requestMsg = msg;
	}



	@Override
	public void setResponseMessage(ResponseMessage msg) {
		_gotResponse = true;
		_responseMsg = msg;
	}



	@Override
	public void removeUserName(String userName) {
		_userNameWaitingForTraceList.remove(userName);

		if(_userNameWaitingForTraceList.isEmpty()) {
			_waitingForTraceElmt = false;
		}
	}



	@Override
	public void setWaitingForTraceElmt(boolean b) {
		_waitingForTraceElmt = b;
	}



	@Override
	public void setWaitingForResponse(boolean b) {
		_waitingForResponse = b;
	}



	@Override
	public boolean isWaitingForTraceElmt() {
		return _waitingForTraceElmt;
	}

	@Override
	public boolean isWaitingForResponse() {
		return _waitingForResponse;
	}



	@Override
	public RequestMessage getRequestMessage() {
		return _requestMsg;
	}



	@Override
	public ResponseMessage getResponseMessage() {
		return _responseMsg;
	}



	@Override
	public boolean addAction(ActionTrace newAction) {
		String user = newAction.getUserName();
		if(_userNameWaitingForTraceList.contains(user)) {

			_actionMap.add(newAction);
			_userNameWaitingForTraceList.remove(user);
			_actionsToProcess = true;

			if(_userNameWaitingForTraceList.isEmpty()) {
				_waitingForTraceElmt = false;
			}

			return true;
		}
		return false;

	}



	@Override
	public List<ActionTrace> getActionList() {
		return _actionMap;
	}



	@Override
	public boolean hasGotRequestMessage() {
		return _gotRequest;
	}



	@Override
	public boolean hasGotResponseMessage() {
		return _gotResponse;
	}



	@Override
	public boolean isRoot() {
		return _isRoot;
	}



	@Override
	public void setIsRoot(boolean isRoot) {
		this._isRoot = isRoot;
	}



	@Override
	public boolean gotTransitionWithAction(Action action) {
		return _inputTransitionIdByAction.containsKey(action);
	}



	@Override
	public String getTransitionWithAction(Action action) {
		return _inputTransitionIdByAction.get(action);
	}



	@Override
	public Map<String,String> getChildren() {
		return  _stateChildIdByTransId;
	}



	@Override
	public String getChildByTransition(String transId) {
		return _stateChildIdByTransId.get(transId);
	}



	@Override
	public void addNewOutputTransition(String id, Action action) {
		_inputTransitionIdByAction.put(action, id);
	}



	@Override
	public void addChild(String stateId, String transId) {
		_stateChildIdByTransId.put(transId, stateId);
	}



	@Override
	public void addNewUserName(String userName) {
		_userNameWaitingForTraceList.add(userName);
		_waitingForTraceElmt = true;

	}



	@Override
	public String getNextUserNameWaitingForAction() {
		String userName = _userNameWaitingForTraceList.get(_userNameIndex);
		_userNameIndex = (++_userNameIndex) % _userNameWaitingForTraceList.size();
		return null;
	}



	@Override
	public List<String> getUserNameWaitingForTraceList() {
		return _userNameWaitingForTraceList;
	}



	@Override
	public boolean hasActionToProcess() {
		return _actionsToProcess;
	}


	@Override
	public ActionTrace getNextAction() {
		ActionTrace action = _actionMap.remove(0);
		
		if(_actionMap.isEmpty()) {
			_actionsToProcess = false;
		}
		
		return action;
	}



	@Override
	public void addFather(String transId) {
		_fatherTransId = transId;
	}


}
