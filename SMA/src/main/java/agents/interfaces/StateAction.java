package agents.interfaces;

import java.util.List;

import trace.Action;

public interface StateAction {
	public void move(String id, List<Action>currentPositionActions, Action newAction);
	public void addStateAgent(String id);
	public void addStateAgent(String id, List<Action> actions);
	public void removeAgent(String id, List<Action> actions);
	//public voi

}
