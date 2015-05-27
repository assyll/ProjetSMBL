package agents.interfaces;

import java.util.Map;

public interface AgentTrace {
	
	public Map<String,String> getAllCurrentAgentsMap();
	
	public String getCurrentAgent(String user);
}
