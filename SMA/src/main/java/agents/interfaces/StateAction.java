package agents.interfaces;

import java.util.List;

import trace.Action;
import trace.ActionTrace;
import agents.impl.RequestMessage;

public interface StateAction {
	
	public void move(String id, List<Action>currentPositionActions, Action newAction);
	public void addStateAgent(String id);
	public void addStateAgent(String id, List<Action> actions);
	public String[] createTransitionAgent(ActionTrace action);
	public void removeAgent(String id, List<Action> actions);
	public void doNothing();
	public void treatRequestMessage();
	public void treatResponseMessage();
	public void sendRequestMessage(RequestMessage msg);
	public void askToMerge(List<String> agentIdInMyCell);
	public void tryToTakeToken();
}
