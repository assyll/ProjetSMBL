package generalStructure.impl;

import java.io.File;

import environnement.impl.EnvironnementImpl;
import environnement.interfaces.ContextInfos;
import environnement.interfaces.EnvInfos;
import environnement.interfaces.EnvUpdate;
import general.ActionProvider;
import general.BigEco;
import general.EcoAgents;
import general.Environnement;
import general.Forward;
import general.GraphComp;
import general.Launcher;
import general.LogComp;
import generalStructure.interfaces.CycleAlert;
import generalStructure.interfaces.ICreateAgent;
import trace.Action;
import trace.ActionTrace;
import trace.impl.ActionProviderImpl;
import trace.interfaces.ITakeAction;
import agents.impl.EcoAgentsImpl;
import agents.interfaces.PullMessage;
import agents.interfaces.SendMessage;

public class BigEcoImpl extends BigEco implements ICreateAgent{

	private final String path_graph;
	private final String path_traces;

	private Thread t = null;

	public BigEcoImpl(String path_traces, String path_graph) {
		this.path_graph = path_graph;
		this.path_traces = path_traces;
	}

	@Override
	protected EcoAgents make_ecoAE() {
		return new EcoAgentsImpl();
	}

	@Override
	protected Forward<CycleAlert, ContextInfos, EnvInfos, EnvUpdate, SendMessage, PullMessage, ITakeAction> make_fw() {
		return new ForwardImpl();
	}

	@Override
	protected Launcher make_launcher() {
		return new LauncherImpl();
	}


	@Override
	protected void start() {
		super.start();

		//newDynamicAssemblyAgentEtat("user1", true);
		
	}

	@Override
	protected Environnement<EnvInfos, EnvUpdate> make_envEco() {
		return new EnvironnementImpl();
	}

	@Override
	protected ActionProvider make_actionProvider() {
		return new ActionProviderImpl(path_traces);
	}

	@Override
	protected ICreateAgent make_creatAgent() {
		return this;
	}

	@Override
	public void createNewState(String id, boolean isRoot) {
		newDynamicAssemblyAgentEtat(id, isRoot);
	}

	@Override
	public void createNewTransition(String id, Action action, String stateSourceId) {
		//newDynamicAssemblyAgentTransition(id, action, stateSourceId);
	}

	@Override
	protected LogComp make_logComp() {
		return new LogCompImpl("target" + File.separator + "log.txt");
	}

	@Override
	public void createNewTransition(String id, ActionTrace action, String idSource) {
		newDynamicAssemblyAgentTransition(id, action, idSource);
	}

	@Override
	public String createNewState(boolean isRoot) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] createNewTransition(ActionTrace action, String idSource) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void createNewTransition(ActionTrace action, String idSource,
			String idCible) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void createNewTransition(String id, ActionTrace action,
			String idSource, String idCible) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected GraphComp make_graphComp() {
		return new GraphCompImpl(path_graph);
	}
}



