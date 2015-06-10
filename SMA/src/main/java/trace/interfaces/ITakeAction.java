package trace.interfaces;

import trace.Action;
import trace.ActionTrace;

public interface ITakeAction {
	public Action getActionTrace(String userName);
	public ActionTrace newUserTrace();
}
