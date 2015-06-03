package agents.impl;

import general.Act;
import general.Decide;
import general.EcoAgentsEtat;
import general.EcoAgentsEtat.AgentEtat;
import general.Perceive;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import agents.Suicide;
import agents.interfaces.AgentTrace;
import agents.interfaces.Do;
import agents.interfaces.IGetThread;

public class EcoAgentsEtatImpl extends EcoAgentsEtat implements AgentTrace {

	private Map<String,String> currentAgentsMap; // map<username, idAgent>
	private Map<String,Runnable> agentsMap;
	private  List<Runnable> listRunnable;
	
	public EcoAgentsEtatImpl(){
		currentAgentsMap = new HashMap<String,String>();
		agentsMap = new HashMap<String,Runnable>();
		listRunnable = new ArrayList<Runnable>();
	}
	
	@Override
	protected AgentEtat make_AgentEtat(String id) {
		AgentEtatImpl agent = new AgentEtatImpl(id);
	//	threadsMap.put(id, agent.getThread());
		synchronized (agentsMap) {
			agentsMap.put(id, agent);
		}

		return agent;
	}

	@Override
	public Map<String, String> getAllCurrentAgentsMap() {
		return currentAgentsMap;
	}

	@Override
	public String getCurrentAgent(String user) {
		return currentAgentsMap.get(user);
	}
	
	public void killAgent(String id) {

		System.out.println(id+" suicide!!!!!!!");
		

		System.out.println("Kill Agent");
		
		synchronized (agentsMap) {
		/*	listRunnable.remove(agentsMap.get(id));
			this.requires().threads().getAgents(listRunnable);*/

			agentsMap.remove(id);
			this.requires().threads().setAgentsMap(agentsMap);
			
			
		}
	}
	

	/****************************************************************** Private Classes ****************************************************************/
	private class AgentEtatImpl extends AgentEtat implements Runnable {

		@Override
		protected void start() {
			synchronized (agentsMap) {
				//listRunnable.add(this);
				eco_requires().threads().setAgentsMap(agentsMap);
			}
		}
		
		String id;
		Thread t;
		
		private void suicide() {
			//killAgent(id);
		}
		
		public Thread getThread(){
			return t;
		}

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
			this.parts().perceive().perception().doIt();
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
			private int i = (new Random().nextInt(2) +1);
			
			public ActImpl(String id){
				this.id = id;
			}
			
			@Override
			public void doIt() {
				System.out.println(id+": Action");
				i--;
				if(i == 0) suicide();
				this.requires().finishedCycle().endOfCycleAlert(id);

			}

			@Override
			protected Do make_action() {
				// TODO Auto-generated method stub
				return this;
			}
			
		}


	}
	



}
