package agents.impl.transition;

import environnement.interfaces.EnvInfos;
import environnement.interfaces.EnvUpdate;
import agents.impl.AbstractAct;
import agents.interfaces.SendMessage;
import agents.interfaces.TransAction;
import agents.interfaces.TransMemory;

public class ActTransImpl extends AbstractAct<TransAction, EnvUpdate, TransMemory, SendMessage> implements TransAction {

	@Override
	protected TransAction make_action() {
		return this;
	}

}
