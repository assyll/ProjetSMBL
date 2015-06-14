package generalStructure.interfaces;

import trace.Action;
import trace.ActionTrace;

public interface ICreateAgent {

	public String createNewState(boolean isRoot);
	public String[] createNewTransition(ActionTrace action, String idSource);
	public void createNewState(String id, boolean isRoot);
	public void createNewTransition(String id, ActionTrace action, String idSource, String idCible, boolean createCible);
	
}
