package agents.impl.state;

import java.util.List;

import trace.ActionTrace;
import agents.impl.AbstractDecide;
import agents.impl.RequestMessage;
import agents.impl.RequestType;
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
		
		// Si j'ai une reponse a traiter
		if (requires().memory().hasGotResponseMessage()) {
			System.out.println(id + ": reponse recu");
			requires().action().treatResponseMessage();
			
		// Si j'ai une requete a traiter
		} else if(requires().memory().hasGotRequestMessage()) {
			System.out.println(id + ": requete recu");
			this.requires().action().treatRequestMessage();
			
		// Si j'ai un element de trace
		} else if (requires().memory().hasActionToProcess()) {
			System.out.println(id + ": element de trace recu");
			processActionTrace(requires().memory().getNextAction());
			
		// Si j'ai au moins un voisin ET si je viens de bouger au cycle precedent.
		} else if(requires().memory().hasMoved() &&
				requires().memory().getAgentIdInMyCell().size() > 0) {
			requires().action().askToMerge(requires().memory().getAgentIdInMyCell());
			
		// Sinon
		} else {
			System.out.println(id + ": rien a faire");
			requires().action().doNothing();
		}
	}
	
	public void processActionTrace(ActionTrace action){
		String transitionId = requires().memory().getTransitionWithAction(action.getAction());
		
		if(transitionId == null) {
			System.out.println(id+ ": creation de la transition " + transitionId);
			requires().action().createTransitionAgent(action);
		} else {
			System.out.println(id+ ": pas de creation de la transition" + transitionId);
			//Envoyer une requete a l'etat fils pour qu'il
			//se mette en attente d'un nouvel element de trace
			String childId = requires().memory().
					getChildByTransition(transitionId);
			
			this.requires().action().sendRequestMessage(
					new RequestMessage(id, childId,
							RequestType.WAIT_FOR_NEXT_ACTION,
							action.getUserName()));
			System.out.println(id+ ": transmission du nom d'utilisateur " + action.getUserName() +" a " + childId);
		}
	}
	

}
