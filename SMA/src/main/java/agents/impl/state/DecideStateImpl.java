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

		// Si j'ai une action en attente de traitement
		if(requires().memory().hasActionToProcess()) {
			ActionTrace action = requires().memory().getNextAction();
			this.requires().action().createTransitionAgent("", action);
		} else if(requires().memory().hasGotRequestMessage()) {
			// Sinon si j'ai une requete a traiter, la traiter
			requires().action().treatRequestMessage();
		} else {
			// Sinon je regarde si j'ai un message dans ma boite au lettre
			// au pire des cas
			requires().action().pullRequestMessage();
			
			//requires().action().doNothing();
		}
	}

}
