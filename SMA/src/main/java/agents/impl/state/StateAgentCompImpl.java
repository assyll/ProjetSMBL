package agents.impl.state;

import agents.interfaces.PullMessage;
import agents.interfaces.SendMessage;
import agents.interfaces.StateAction;
import agents.interfaces.StateMemory;
import environnement.interfaces.ContextInfos;
import environnement.interfaces.EnvInfos;
import environnement.interfaces.EnvUpdate;
import general.Act;
import general.Agent;
import general.Decide;
import general.Memory;
import general.Perceive;
import generalStructure.interfaces.ICreateAgent;

public class StateAgentCompImpl extends Agent<ContextInfos, EnvUpdate, StateAction, StateMemory, ICreateAgent, SendMessage, PullMessage> {

	private String id;
	
	public StateAgentCompImpl(String id) {
		this.id = id;
	}
	
	@Override
	protected Memory<StateMemory> make_memory() {
		return new StateMemImpl(id);
	}

	@Override
	protected Perceive<ContextInfos, StateMemory, PullMessage> make_perceive() {
		return new PerceiveStateImpl(id);
	}

	@Override
	protected Decide<StateAction, StateMemory> make_decide() {
		return new DecideStateImpl(id);
	}

	@Override
	protected Act<StateAction, EnvUpdate, StateMemory, ICreateAgent, SendMessage> make_act() {
		return new ActStateImpl(id);
	}

}
