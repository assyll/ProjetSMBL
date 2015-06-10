package generalStructure.interfaces;

import trace.Action;
import trace.ActionTrace;

public interface ICreateAgent {

	public void createNewState(String id, boolean isRoot);
	public void createNewTransition(String id, Action action, String sourceStateId);
	public void createNewTransition(String id, ActionTrace action);
}
