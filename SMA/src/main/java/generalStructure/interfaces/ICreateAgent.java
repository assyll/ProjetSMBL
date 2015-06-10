package generalStructure.interfaces;

import trace.Action;

public interface ICreateAgent {

	public void createNewState(String id, boolean isRoot);
	public void createNewTransition(String id, Action action, String sourceStateId);
}
