package agents.interfaces;

import trace.ActionTrace;

public interface TransMemory {
	
	public String getStateCibleId();

	public void setStateCibleId(String stateCibleId);

	public ActionTrace getAction();
	
	public void setAction(ActionTrace action);

	public String getTransId();

	public String getStateSourceId();
}
