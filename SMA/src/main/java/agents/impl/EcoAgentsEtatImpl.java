package agents.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;

import agents.Act;
import agents.Decide;
import agents.EcoAgentsEtat;
import agents.Perceive;
import agents.interfaces.Action;
import agents.interfaces.AgentTrace;
import agents.interfaces.Decision;
import agents.interfaces.Do;
import agents.interfaces.Perception;

public class EcoAgentsEtatImpl extends EcoAgentsEtat implements AgentTrace {

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
	
	private class AgentEtatImpl extends AgentEtat implements Runnable {

		String id;
		
		public AgentEtatImpl(String id)
		{
			this.id = id;
		}

		@Override
		protected Act make_act() {
			// TODO Auto-generated method stub
			return new ActImpl(id);
		}

		@Override
		protected Decide make_decide() {
			// TODO Auto-generated method stub
			return new DecisionImpl(id);
		}

		@Override
		protected Perceive make_perceive() {
			// TODO Auto-generated method stub
			return new PerceptionImpl(id);
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			
		}


	}
	
	private class PerceptionImpl extends Perceive implements Do {

		String id = "";
		
		public PerceptionImpl(String id){
			this.id = id;
		}
		
		@Override
		public void doIt() {
			System.out.println(id+": Perception");
			this.requires().decision().doIt();
			
		}

		@Override
		protected Do make_perception() {
			// TODO Auto-generated method stub
			return this;
		}
		
	}
	
	private class DecisionImpl extends Decide implements Do {

		String id = "";
		
		public DecisionImpl(String id){
			this.id = id;
		}
		
		@Override
		public void doIt() {
			System.out.println(id+": Decision");
			this.requires().action().doIt();
			
		}

		@Override
		protected Do make_decision() {
			// TODO Auto-generated method stub
			return this;
		}
		
	}
	
	private class ActImpl extends Act implements Do{

		private String id = "";
		
		public ActImpl(String id){
			this.id = id;
		}
		
		@Override
		public void doIt() {
			System.out.println(id+": Action");
			
		}

		@Override
		protected Do make_action() {
			// TODO Auto-generated method stub
			return this;
		}
		
	}


}
