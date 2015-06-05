package environnement.interfaces;

import java.util.List;

import trace.Action;

public interface CellInfo {

	public List<Action> getListOfActions();
	public List<String> getAgentIDList();
	public void addNewStateAgent(String id);
	public void removeStateAgent(String id);
}
