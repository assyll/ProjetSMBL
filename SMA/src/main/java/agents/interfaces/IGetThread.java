package agents.interfaces;

import java.util.List;
import java.util.Map;

public interface IGetThread {
	public void getThreadsMap( Map<String,Thread> threadMap);
	public void getAgents( List<Runnable> agents);
}
