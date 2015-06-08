package agents.interfaces;

import java.util.List;

import trace.Action;
import agents.impl.RequestMessage;
import agents.impl.ResponseMessage;

public interface StateMemory {

	public void setNextTraceElmtUserName(String userName);
	public void setRequestMessage(RequestMessage msg);
	public void setResponseMessage(ResponseMessage msg);
	public void removeUserName(String userName);
	public void setWaitingForTraceElmt(boolean b);
	public void setWaitingForResponse(boolean b);
	public boolean isWaitingForTraceElmt();
	public boolean hasGotRequestMessage();
	public boolean hasGotResponseMessage();
	public boolean isWaitingForResponse();
	public RequestMessage getRequestMessage();
	public ResponseMessage getResponseMessage();
	public void addAction(Action newAction);
	public List<Action> getActionList();
}
