package agents.interfaces;

import trace.Action;

public interface TransMemory {
	
	public String getStateCibleId();

	public void setStateCibleId(String stateCibleId);

	public Action getAction();
	
	public void setAction(Action action);

	public String getTransId();

	public String getStateSourceId();
}
