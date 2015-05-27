package agents.impl;

import java.util.HashMap;
import java.util.Map;

import agents.EcoAgentsEtat;
import agents.interfaces.AgentTrace;

public class EcoAgentsEtatImpl extends EcoAgentsEtat implements AgentTrace{

	private Map<String,String> currentAgentsMap;
	
	public EcoAgentsEtatImpl(){
		currentAgentsMap = new HashMap<String,String>();
	}
	
	@Override
	protected AgentEtat make_AgentEtat(String id) {
		return new AgentEtatImpl(id);
	}

	@Override
	public Map<String, String> getAllCurrentAgentsMap() {
		return currentAgentsMap;
	}

	@Override
	protected AgentTrace make_currentAgents() {
		return this;
	}

	@Override
	public String getCurrentAgent(String user) {
		return currentAgentsMap.get(user);
	}
	
	private class AgentEtatImpl extends AgentEtat{

		String id;
		
		public AgentEtatImpl(String id)
		{
			this.id = id;
		}
		
		@Override
		protected String make_qlqChose() {
			return id;
		}
		
	}


}
