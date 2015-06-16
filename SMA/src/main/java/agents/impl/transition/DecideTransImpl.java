package agents.impl.transition;

import agents.impl.AbstractDecide;
import agents.interfaces.TransAction;
import agents.interfaces.TransMemory;

public class DecideTransImpl extends AbstractDecide<TransAction, TransMemory> {

	@Override
	public void makeDecision() {
		
		if (requires().memory().hasResponseMessage()) {
			// Si j'ai une reponse, la traiter
			requires().action().treateResponseMessage();
		} else if (requires().memory().hasRequestMessage()) {
			// Si j'ai une requete, la traiter
			requires().action().treateRequestMessage();
		} else {
			// Ne rien faire
			requires().action().doNothing();
		}
	}

}
