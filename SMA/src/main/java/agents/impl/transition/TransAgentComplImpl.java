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
import agents.impl.RequestMessage;
import agents.impl.RequestType;
import agents.interfaces.PullMessage;
import agents.interfaces.SendMessage;
import agents.interfaces.TransAction;
import agents.interfaces.TransMemory;


public class TransAgentComplImpl extends Agent<EnvInfos, EnvUpdate, TransAction, TransMemory, ICreateAgent, SendMessage, PullMessage> {

	private String id;
	private ActionTrace action;
	private String stateSourceId;
	private String stateCibleId;
	private boolean createCible;
	
	public TransAgentComplImpl(String id, ActionTrace action, String stateSourceId, String idCible, boolean createCible) {

		this.id = id;
		this.action = action;
		this.stateSourceId = stateSourceId;
		
		// creer un nouvel agent etat
		this.stateCibleId = idCible;
		this.createCible = createCible;
		
	}
	
	@Override
	protected void start() {
		super.start();
		if(createCible) {
			// Transmet a lagent son id.
			
			this.requires().create().createNewState(stateCibleId, false);
			RequestMessage request = new RequestMessage(
					id, stateCibleId, RequestType.ADD_FATHER_WITH_USERNAME,
					action.getUserName());
			requires().push().sendRequestMessage(request);
		}
		
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
		return new ActTransImpl(id);
	}

}
