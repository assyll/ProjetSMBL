package trace.interfaces;

import java.util.List;

import trace.Action;
import trace.ActionTrace;

public interface ITakeAction {
	public Action getActionTrace(String userName);
	public List<ActionTrace> newUsersTraceList();
}
