package agents.impl;

import environnement.interfaces.ContextInfos;
import environnement.interfaces.EnvInfos;
import environnement.interfaces.EnvUpdate;
import general.Agent;
import general.EcoAgents;
import generalStructure.interfaces.ICreateAgent;
import generalStructure.interfaces.IInit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import trace.Action;
import trace.ActionTrace;
import agents.IStart;
import agents.impl.state.StateAgentCompImpl;
import agents.impl.transition.TransAgentComplImpl;
import agents.interfaces.AgentTrace;
import agents.interfaces.ISuicide;
import agents.interfaces.PullMessage;
import agents.interfaces.SendMessage;
import agents.interfaces.StateAction;
import agents.interfaces.StateMemory;
import agents.interfaces.TransAction;
import agents.interfaces.TransMemory;

public class EcoAgentsImpl extends EcoAgents implements AgentTrace, IInit {

	private Map<String,String> currentAgentsMap; // map<username, idAgent>
	private Map<String,Runnable> agentsMap;
	private List<Runnable> listRunnable;
	private int nbStateAgentsCreated;
	private int nbTransAgentsCreated;
	private String rootId;

	public EcoAgentsImpl(){
		init();
	}
	
	@Override
	public void init() {
		currentAgentsMap = new HashMap<String,String>();
		agentsMap = new HashMap<String,Runnable>();
		listRunnable = new ArrayList<Runnable>();
		nbStateAgentsCreated = 0;
		nbTransAgentsCreated = 0;
		rootId = "";
	}
	
	@Override
	protected StateAgent make_StateAgent(String id, boolean isRoot) {
		StateAgentImpl agent = new StateAgentImpl(id, isRoot);

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

		System.out.println(id+": SUICIDE");

		synchronized (agentsMap) {
			agentsMap.remove(id);
			this.requires().threads().setAgentsMap(agentsMap);	
		}
	}

	@Override
	protected TransitionAgent make_TransitionAgent(String id, ActionTrace action, String idSource, String idCible, boolean createCible) {
		TransAgentImpl agent = new TransAgentImpl(id, action, idSource, idCible, createCible);
		
		synchronized (agentsMap) {
			agentsMap.put(id, agent);
		}

		return agent ;
	}
	
	@Override
	protected IStart make_startCycle() {
		return new IStart() {
			@Override
			public void run_start() {
				System.out.println("RUN_START");
				if(nbStateAgentsCreated == 0) {
					System.out.println("CREATION NOUVEAU AGENT");
					rootId = "S0";
					requires().createAgent().createNewState(rootId, true);
					nbStateAgentsCreated++;
				}
			}
		};
	}

	@Override
	protected IInit make_init() {
		return this;
	}
	

	/**************************** Private Classes **************************/

	private class StateAgentImpl extends StateAgent
	implements Runnable, ICreateAgent, ISuicide {

		String id; 
		boolean isRoot;

		@Override
		protected void start() {
			synchronized (agentsMap) {
				eco_requires().threads().setAgentsMap(agentsMap);
			}
		}

		public StateAgentImpl(String id, boolean isRoot) {
			this.id = id;
			this.isRoot = isRoot;
		}

		@Override
		public void run() {
			this.parts().agentComponent().cycle().doIt();
		}

		@Override
		protected Agent<ContextInfos, EnvUpdate, StateAction, StateMemory, ICreateAgent, SendMessage, PullMessage> make_agentComponent() {
			return new StateAgentCompImpl(id, isRoot);
		}

		@Override
		public void createNewState(String id, boolean isRoot) {
			eco_requires().createAgent().createNewState("S"+nbStateAgentsCreated, isRoot);
		}

		@Override
		protected ICreateAgent make_create() {
			return this;
		}
		
		@Override
		public String createNewState(boolean isRoot) {
			String id = "S"+nbStateAgentsCreated;
			eco_requires().createAgent().createNewState("S"+nbStateAgentsCreated, isRoot);
			return id;		
		}

		@Override
		public String[] createNewTransition(ActionTrace action, String idSource) {
			String[] ids = new String[2];
			ids[0] = "T"+ nbTransAgentsCreated++;
			ids[1] = "S"+ nbStateAgentsCreated++;
			eco_requires().createAgent().createNewTransition(ids[0], action, idSource, ids[1], true);
			return ids;
		}

		@Override
		public void createNewTransition(String id, ActionTrace action,
				String idSource, String idCible, boolean createCible) {
			eco_requires().createAgent().createNewTransition(id, action, idSource, idCible, createCible);
		}

		@Override
		protected ISuicide make_suicide() {
			return this;
		}

		@Override
		public void suicide() {
			killAgent(id);
		}

	}

	/**************************** Private Classes **************************/

	private class TransAgentImpl extends TransitionAgent
	implements Runnable, ICreateAgent, ISuicide {

		private String id; 
		private ActionTrace action;
		private String stateSource;
		private String stateCible;
		private boolean createCible;


		public TransAgentImpl(String id, ActionTrace action, String idSource, String idCible, boolean createCible) {
			System.out.println("Constructeur");

			this.id = id;
			this.action = action;
			this.stateSource = idSource;
			this.stateCible = idCible;
			this.createCible = createCible;
		}

		@Override
		protected void start() {
			System.out.println("start");
		
			synchronized (agentsMap) {
				eco_requires().threads().setAgentsMap(agentsMap);
			}			

		}

		@Override
		public void run() {
			this.parts().agentComponent().cycle().doIt();
		}

		@Override
		protected Agent<EnvInfos, EnvUpdate, TransAction, TransMemory, ICreateAgent, SendMessage, PullMessage> make_agentComponent() {
			return new TransAgentComplImpl(id, action, stateSource, stateCible, createCible);
		}

		@Override
		protected ICreateAgent make_create() {
			return this;
		}

		@Override
		public void createNewState(String id, boolean isRoot) {
			eco_requires().createAgent().createNewState(id, isRoot);
		}

		@Override
		public String createNewState(boolean isRoot) {
			return null;
		}

		@Override
		public String[] createNewTransition(ActionTrace action, String idSource) {
			return null;
		}
		
		@Override
		public void createNewTransition(String id, ActionTrace action,
				String idSource, String idCible, boolean createCible) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void suicide() {
			killAgent(id);
		}

		@Override
		protected ISuicide make_suicide() {
			return this;
		}

	}

}
