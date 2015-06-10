package agents.impl.state;

import java.util.List;

import trace.ActionTrace;
import agents.impl.AbstractDecide;
import agents.interfaces.StateAction;
import agents.interfaces.StateMemory;

public class DecideStateImpl extends AbstractDecide<StateAction, StateMemory> {

	private String id;
	
	public DecideStateImpl(String id) {
		this.id = id;
	}
	
	@Override
	public void makeDecision() {
		System.out.println("Decision de " + id);

		//Si j'ai une action en attente de traitement
		
		if(requires().memory().hasActionToProcess()) {
			ActionTrace action = requires().memory().getNextAction();
			this.requires().action().createTransitionAgent("", action);
		} else {
			// sinon je fais rien
			requires().action().doNothing();
		}
	}

}
