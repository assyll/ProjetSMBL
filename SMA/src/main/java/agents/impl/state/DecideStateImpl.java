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

		// Si j'ai une action en attente de traitement
		if(requires().memory().hasGotRequestMessage()) {
			System.out.println(id + ": requete recu");
			this.requires().action().treatRequestMessage();
		} else if(requires().memory().hasActionToProcess()) {
			System.out.println(id + ": element de trace recu");
			processActionTrace(requires().memory().getNextAction());
		} else {
			System.out.println(id + ": rien a faire");
			requires().action().doNothing();
		}
	}
	
	public void processActionTrace(ActionTrace action){
		String transitionId = requires().memory().getTransitionWithAction(action.getAction());
		
		if(transitionId == null) {
			System.out.println(id + ": cree une nouvelle transition");
			String[] ids = this.requires().action().createTransitionAgent(action);
			requires().memory().addNewOutputTransition(ids[0], action.getAction());
			requires().memory().addChild(ids[1], ids[0]);
			System.out.println(id + ": creation de la transition "+ids[0]+" avec l'action "+ action.getAction().getActionMap().get("action"));
			System.out.println(id + ": nouveau fils " + ids[1]);
			requires().action().doNothing();
		} else {
			//Envoyer une requête à l'état fils pour qu'il se mette en attente d'un nouvel élément de trace
			String childId = requires().memory().getChildByTransition(transitionId);
			this.requires().action().sendRequestMessage(new RequestMessage(id, childId, RequestType.WAIT_FOR_NEXT_ACTION, action.getUserName()));
		}
	}
	

}
