package generalStructure.impl;

import trace.Action;
import trace.impl.ActionProviderImpl;
import trace.interfaces.ITakeAction;
import environnement.impl.EnvironnementImpl;
import environnement.interfaces.ContextInfos;
import environnement.interfaces.EnvInfos;
import environnement.interfaces.EnvUpdate;
import general.ActionProvider;
import general.BigEco;
import general.EcoAgents;
import general.Environnement;
import general.Forward;
import general.Launcher;
import generalStructure.interfaces.CycleAlert;
import generalStructure.interfaces.ICreateAgent;
import agents.impl.EcoAgentsImpl;
import agents.interfaces.PullMessage;
import agents.interfaces.SendMessage;
import agents.interfaces.StateAction;
import agents.interfaces.StateMemory;
import agents.interfaces.TransAction;
import agents.interfaces.TransMemory;

public class BigEcoImpl extends BigEco implements ICreateAgent{

	private final String path;

	private Thread t = null;

	public BigEcoImpl(String path) {
		this.path = path;
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

	}

	@Override
	protected Environnement<EnvInfos, EnvUpdate> make_envEco() {
		return new EnvironnementImpl();
	}

	@Override
	protected ActionProvider make_actionProvider() {
		return new ActionProviderImpl(path);
	}

	@Override
	protected ICreateAgent make_creatAgent() {
		return this;
	}

	@Override
	public void createNewState(String id) {
		newDynamicAssemblyAgentEtat(id);
	}

	@Override
	public void createNewTransition(String id, Action action, String stateSourceId) {
		newDynamicAssemblyAgentTransition(id, action, stateSourceId);
	}
}



