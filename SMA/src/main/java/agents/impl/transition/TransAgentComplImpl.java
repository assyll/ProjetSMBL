package agents.impl.transition;

import environnement.interfaces.EnvInfos;
import environnement.interfaces.EnvUpdate;
import general.Act;
import general.Agent;
import general.Decide;
import general.Memory;
import general.Perceive;
import generalStructure.interfaces.ICreateAgent;
import trace.ActionTrace;
import agents.interfaces.PullMessage;
import agents.interfaces.SendMessage;
import agents.interfaces.TransAction;
import agents.interfaces.TransMemory;


public class TransAgentComplImpl extends Agent<EnvInfos, EnvUpdate, TransAction, TransMemory, ICreateAgent, SendMessage, PullMessage> {

	private String id;
	private ActionTrace action;
	private String stateSourceId;
	private String stateCibleId;
	
	public TransAgentComplImpl(String id, ActionTrace action, String stateSourceId) {

		System.out.println("-------------------------------");
		this.id = id;
		this.action = action;
		this.stateSourceId = stateSourceId;
		this.stateCibleId = requires().create().createNewState(false);
	}
	
	public TransAgentComplImpl(String id, ActionTrace action, String stateSourceId, String stateCibleId) {
		this.id = id;
		this.action = action;
		this.stateSourceId = stateSourceId;
		this.stateCibleId = stateCibleId;
	}
	
	@Override
	protected Memory<TransMemory> make_memory() {
		return new TransMemImpl(id, action, stateSourceId);
	}

	@Override
	protected Perceive<EnvInfos, TransMemory, PullMessage> make_perceive() {
		return new PerceiveTransImpl();
	}

	@Override
	protected Decide<TransAction, TransMemory> make_decide() {
		return new DecideTransImpl();
	}

	@Override
	protected Act<TransAction, EnvUpdate, TransMemory, ICreateAgent, SendMessage> make_act() {
		return new ActTransImpl();
	}

}
