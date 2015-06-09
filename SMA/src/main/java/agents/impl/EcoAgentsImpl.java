package agents.impl;

import environnement.interfaces.ContextInfos;
import environnement.interfaces.EnvInfos;
import environnement.interfaces.EnvUpdate;
import general.Agent;
import general.EcoAgents;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import trace.Action;
import agents.impl.state.StateAgentCompImpl;
import agents.impl.transition.TransAgentComplImpl;
import agents.interfaces.AgentTrace;
import agents.interfaces.PullMessage;
import agents.interfaces.SendMessage;
import agents.interfaces.StateAction;
import agents.interfaces.StateMemory;
import agents.interfaces.TransAction;
import agents.interfaces.TransMemory;

public class EcoAgentsImpl extends EcoAgents <StateAction, TransAction, ContextInfos , EnvInfos, EnvUpdate, StateMemory, TransMemory, SendMessage, PullMessage> implements AgentTrace{

	private Map<String,String> currentAgentsMap; // map<username, idAgent>
	private Map<String,Runnable> agentsMap;
	private  List<Runnable> listRunnable;

	public EcoAgentsImpl(){
		currentAgentsMap = new HashMap<String,String>();
		agentsMap = new HashMap<String,Runnable>();
		listRunnable = new ArrayList<Runnable>();
	}

	@Override
	protected StateAgent<StateAction, TransAction, ContextInfos , EnvInfos, EnvUpdate,  StateMemory, TransMemory, SendMessage, PullMessage> make_StateAgent(String id) {
		StateAgentImpl agent = new StateAgentImpl(id);

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
			agentsMap.remove(id);
			this.requires().threads().setAgentsMap(agentsMap);	
		}
	}

	@Override
	protected TransitionAgent<StateAction, TransAction, ContextInfos, EnvInfos, EnvUpdate, StateMemory, TransMemory, SendMessage, PullMessage> make_TransitionAgent(String id, Action action, String stateSourceId) {
		TransAgentImpl agent = new TransAgentImpl(id, action, stateSourceId);
		
		synchronized (agentsMap) {
			agentsMap.put(id, agent);
		}

		return agent ;
	}

	/**************************** Private Classes **************************/

	private class StateAgentImpl extends StateAgent<StateAction, TransAction, ContextInfos, EnvInfos, EnvUpdate, StateMemory, TransMemory, SendMessage, PullMessage> implements Runnable {

		String id; 

		@Override
		protected void start() {
			synchronized (agentsMap) {
				eco_requires().threads().setAgentsMap(agentsMap);
			}
		}

		public StateAgentImpl(String id) {
			this.id = id;
		}

		@Override
		public void run() {
			this.parts().agentComponent().cycle().doIt();
		}

		@Override
		protected Agent<ContextInfos, EnvUpdate, StateAction, StateMemory, SendMessage, PullMessage> make_agentComponent() {
			return new StateAgentCompImpl(id);
		}

	}

	/**************************** Private Classes **************************/

	private class TransAgentImpl extends TransitionAgent<StateAction, TransAction, ContextInfos, EnvInfos, EnvUpdate, StateMemory, TransMemory, SendMessage, PullMessage> implements Runnable {

		private String id; 
		private Action action;
		private String stateSource;

		public TransAgentImpl(String id, Action action, String stateSource) {
			this.id = id;
			this.action = action;
			this.stateSource = stateSource;
		}

		@Override
		protected void start() {

			synchronized (agentsMap) {
				eco_requires().threads().setAgentsMap(agentsMap);
			}

		}

		@Override
		public void run() {
			this.parts().agentComponent().cycle().doIt();
		}

		@Override
		protected Agent<EnvInfos, EnvUpdate, TransAction, TransMemory, SendMessage, PullMessage> make_agentComponent() {
			Map<String,String>  map = new HashMap<String, String>();
			map.put("action", "Action");
			return new TransAgentComplImpl(id, new Action(), stateSource);
		}

	}






}
