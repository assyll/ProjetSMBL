package environnement.interfaces;

import java.util.List;
import java.util.Map;

import general.Environnement.Cell;
import trace.Action;

public interface CellInfos {

	public List<Action> getListOfActions();
	public List<String> getAgentIDList();
	public void addNewStateAgent(String id);
	public void removeStateAgent(String id);
	
	
}
