package agents.impl.transition;

import agents.impl.AbstractAct;
import agents.interfaces.SendMessage;
import agents.interfaces.TransAction;
import agents.interfaces.TransMemory;
import environnement.interfaces.EnvUpdate;
import generalStructure.interfaces.ICreateAgent;

public class ActTransImpl extends AbstractAct<TransAction, EnvUpdate, TransMemory, ICreateAgent, SendMessage> implements TransAction {

	
	
	@Override
	protected TransAction make_action() {
		return this;
	}

}
