package agents.impl.state;

import general.Memory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import trace.Action;
import trace.ActionTrace;
import agents.impl.Child;
import agents.impl.RequestMessage;
import agents.impl.ResponseMessage;
import agents.interfaces.StateMemory;

public class StateMemImpl extends Memory<StateMemory> implements StateMemory {

	private String _stateId;
	private boolean _waitingForTraceElmt, _gotRequest, _waitingForResponse, _gotResponse, _actionsToProcess;
	private List<String> _userNameWaitingForTraceList;
	private List<ActionTrace> _actionMap;
	private RequestMessage _requestMsg;
	private ResponseMessage _responseMsg;
	private boolean _isRoot;
	private Map<Action,String> _inputTransitionIdByAction;
	private Map<Action,String> _outputTransitionIdByAction;
	private Map<String,String> _stateChildIdByTransId;
	private Map<String, String> _stateFatherIdByTransId;
	private int _userNameIndex;
	private List<String> agentIdInMyCell;
	private List<Child>[] _childListArrayWithWithout;
	private boolean _hasMoved;

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
		_stateFatherIdByTransId = new HashMap<String, String>();
		_inputTransitionIdByAction = new HashMap<Action, String>();
		_outputTransitionIdByAction = new HashMap<Action, String>();
		_actionsToProcess = false;
		_userNameIndex = 0;
		agentIdInMyCell = new ArrayList<String>();
		
		_childListArrayWithWithout = new List[2];
		_childListArrayWithWithout[0] = new ArrayList<Child>();
		_childListArrayWithWithout[1] = new ArrayList<Child>();
		
		_hasMoved = false;
		
	}



	@Override
	protected StateMemory make_infos() {
		return this;
	}



	@Override
	public void setNextTraceElmtUserName(String userName) {
		_userNameWaitingForTraceList.add(userName);
		_waitingForTraceElmt = true;
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
		_actionMap.add(newAction);
		_actionsToProcess = true;
		String user = newAction.getUserName();
		
		if(_userNameWaitingForTraceList.contains(user)) {
			_userNameWaitingForTraceList.remove(user);		

			if(_userNameWaitingForTraceList.isEmpty()) {
				_waitingForTraceElmt = false;
			}

			return true;
		}
		return false;

	}



	@Override
	public List<ActionTrace> getActionTraceList() {
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
	public void addNewInputTransition(String id, Action action) {
		_inputTransitionIdByAction.put(action, id);
	}



	@Override
	public void addChild(String stateId, String transId,
			Action action, boolean childWithChild) {
		_stateChildIdByTransId.put(transId, stateId);
		_childListArrayWithWithout[childWithChild ? 0 : 1].
			add(new Child(transId, stateId, action));
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
	public void addFather(String transId, String stateId) {
		_stateFatherIdByTransId.put(transId, stateId);
	}



	@Override
	public boolean isFinal() {
		return _stateChildIdByTransId.size() == 0;
	}



	@Override
	public void removeRequestMsg() {
		_requestMsg = null;
		_gotRequest = false;
	}



	@Override
	public void removeResponseMsg() {
		_responseMsg = null;
		_gotResponse = false;
	}



	@Override
	public List<Action> getActionList() {
		return new ArrayList<Action>(_outputTransitionIdByAction.keySet());
	}



	@Override
	public void setAgentIdInMyCell(List<String> agentIds) {
		agentIdInMyCell = agentIds;
		agentIdInMyCell.remove(_stateId);
	}



	@Override
	public List<String> getAgentIdInMyCell() {
		return agentIdInMyCell;
	}



	@Override
	public List<Child> getChildrenWithSon() {
		return _childListArrayWithWithout[0];
	}



	@Override
	public List<Child> getChildrenWithoutSon() {
		return _childListArrayWithWithout[1];
	}



	@Override
	public List<String> getStateFatherList() {
		return new ArrayList<String> (_stateFatherIdByTransId.values());
	}



	@Override
	public List<String> getTransFatherList() {
		return new ArrayList<String> (_stateFatherIdByTransId.keySet());
	}



	@Override
	public List<String> getStateChildList() {
		return new ArrayList<String> (_stateChildIdByTransId.values());
	}



	@Override
	public List<String> getTransChildList() {
		return new ArrayList<String> (_stateChildIdByTransId.keySet());
	}

	@Override
	public void addAction(Action action, String destState) {
		_outputTransitionIdByAction.put(action, destState);
	}

	/**
	 * Consommation. Si _hasMoved a vrai alors le mettre a faux.
	 * @return sil vient de bouger.
	 */
	@Override
	public boolean hasMoved() {
		boolean retour = _hasMoved;
		_hasMoved = false;
		return retour;
	}

	@Override
	public void setHasMoved(boolean hasMoved) {
		_hasMoved = hasMoved;
	}



	@Override
	public List<Child> getAllChildren() {
		List<Child> children = new ArrayList<>();
		children.addAll(_childListArrayWithWithout[0]);
		children.addAll(_childListArrayWithWithout[1]);
		return children;
	}


}
