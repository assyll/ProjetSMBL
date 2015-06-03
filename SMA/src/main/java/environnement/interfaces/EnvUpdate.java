package environnement.interfaces;

import java.util.List;

import trace.Action;

public interface EnvUpdate {

	public void move(String id, List<Action>currentPositionActions, Action newAction);
	public void addAgent(String id);
	public void addAgent(String id, List<Action> actions);
	public void removeAgent(String id, List<Action> actions);
}
