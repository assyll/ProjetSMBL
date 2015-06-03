package trace.interfaces;

import trace.ActionTrace;


public interface IGetAction {

	public ActionTrace getNextAction();
	public String getNextActionUser();
	
	
}
