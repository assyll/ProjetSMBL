package agents.impl.state;

import general.Memory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import trace.Action;
import agents.impl.RequestMessage;
import agents.impl.ResponseMessage;
import agents.interfaces.StateMemory;

public class StateMemImpl extends Memory<StateMemory> implements StateMemory {

	private String stateId;
	private boolean waitingForTraceElmt, gotRequest, waitingForResponse, gotResponse;
	private List<String> userNameList;
	private Map<String,Action> actionMap;
	private RequestMessage requestMsg;
	private ResponseMessage responseMsg;
	private boolean isRoot;
	
	public StateMemImpl(String id, boolean isRoot) {
		stateId = id;
		this.isRoot = isRoot;
		waitingForTraceElmt = false;
		gotRequest = false;
		waitingForResponse = false;
		userNameList = new ArrayList<String>();
		actionMap = new HashMap<String,Action>();
		requestMsg = null;
		responseMsg = null;
		gotResponse = false;
	}
	


	@Override
	protected StateMemory make_infos() {
		return this;
	}



	@Override
	public void setNextTraceElmtUserName(String userName) {
		userNameList.add(userName);
	}



	@Override
	public void setRequestMessage(RequestMessage msg) {
		gotRequest = true;
		requestMsg = msg;
	}



	@Override
	public void setResponseMessage(ResponseMessage msg) {
		gotResponse = true;
		responseMsg = msg;
	}



	@Override
	public void removeUserName(String userName) {
		userNameList.remove(userName);
	}



	@Override
	public void setWaitingForTraceElmt(boolean b) {
		waitingForTraceElmt = b;
	}



	@Override
	public void setWaitingForResponse(boolean b) {
		waitingForResponse = b;
	}



	@Override
	public boolean isWaitingForTraceElmt() {
		return waitingForTraceElmt;
	}

	@Override
	public boolean isWaitingForResponse() {
		return waitingForResponse;
	}



	@Override
	public RequestMessage getRequestMessage() {
		return requestMsg;
	}



	@Override
	public ResponseMessage getResponseMessage() {
		return responseMsg;
	}



	@Override
	public void addAction(String userName, Action newAction) {
		actionMap.put(userName, newAction);
	}



	@Override
	public Map<String,Action> getActionMap() {
		return actionMap;
	}



	@Override
	public boolean hasGotRequestMessage() {
		return gotRequest;
	}



	@Override
	public boolean hasGotResponseMessage() {
		return gotResponse;
	}



	@Override
	public boolean isRoot() {
		return isRoot;
	}



	@Override
	public void setIsRoot(boolean isRoot) {
		this.isRoot = isRoot;
	}


}
