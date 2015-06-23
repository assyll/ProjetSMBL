package generalStructure.interfaces;

import agents.interfaces.StateMemory;
import agents.interfaces.TransMemory;

public interface IGraph {

	public void majTransitionAgent(String id, TransMemory memory);
	public void majStateAgent(String id, StateMemory memory);
	public void deleteStateAgent(String id, boolean isRoot);
	public void closeGraph();
	
}
