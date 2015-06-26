package agents.impl.transition;

import java.util.HashMap;
import java.util.Map;

import agents.impl.AbstractAct;
import agents.impl.RequestMessage;
import agents.impl.RequestType;
import agents.impl.ResponseMessage;
import agents.interfaces.SendMessage;
import agents.interfaces.TransAction;
import agents.interfaces.TransMemory;
import environnement.interfaces.EnvUpdate;
import generalStructure.interfaces.ICreateAgent;

public class ActTransImpl extends
AbstractAct<TransAction, EnvUpdate, TransMemory, ICreateAgent, SendMessage>
implements TransAction {

	private String id;
	
	public ActTransImpl(String id) {
		this.id = id;
	}
	
	@Override
	protected TransAction make_action() {
		return this;
	}

	@Override
	public void doNothing() {
		System.out.println(id + ": Je ne fais rien");
		
		requires().finishedCycle().endOfCycleAlert(id);
		logger("do nothing");
	}
	
	@Override
	public void treateRequestMessage() {
		System.out.println(id + ": Je traite une requete");
		
		RequestMessage request = requires().memory().getRequestMessage();
		switch ((RequestType) request.getType()) {
		case UPDATE_CHILD:
			// On me demande de mettre a jour mes fils
			// Il faut que je redirige mon endState vers le noeud fusionne
			
			// Pour cela, je recupere d'abord le noeud fusionne
			String mergeStateId = (String) request.getInformations();
			
			// Ensuite je fais la redirection
			requires().memory().setStateCibleId(mergeStateId);
			System.out.println(id + ": a ete redirg� vers " + mergeStateId);
			break;
			
		case SUICIDE_HIERARCHY:
			// On me demande de me suicider apres avoir demander a mon etat
			// (sans transitions entrantes) de se suicider a son tour.
			
			// Demande de suicide a mon fils
			String transChildId = requires().memory().getStateCibleId();
			
			// Creation de la requete
			RequestMessage suicideRequest = new RequestMessage(
					id, transChildId, RequestType.SUICIDE_HIERARCHY, null);
			
			// Envoie de celle-ci
			requires().sendMessage().sendRequestMessage(suicideRequest);
			System.out.println(id + ": a demande a " + transChildId +" de se suicider");
			
			// Je me suicide
			System.out.println(id + ": se suicide");
			suicide();
			
			break;
			
		}
		
		logger("traitement de requete");
		requires().finishedCycle().endOfCycleAlert(id);
	}
	
	@Override
	public void treateResponseMessage() {
		System.out.println(id + ": traite une reponse");
		ResponseMessage response = requires().memory().getResponseMessage();
		
		logger("traitement de reponse");
		requires().finishedCycle().endOfCycleAlert(id);
	}
	
	private void logger(String ... dones) {
		Map<String, String> informations = new HashMap<>();
		
		informations.put("id", id);
		informations.put("agentType", "state");
		informations.put("done", dones[0]);
		
		for (int i = 1; i < dones.length;) {
			informations.put(dones[i++], dones[i++]);
		}
		
		majGraph();
		requires().finishedCycleForLog().ecrire(informations);
	}
	
	private void majGraph() {
		requires().graph().majTransitionAgent(id, requires().memory());
	}
	
	private void suicide() {
		requires().graph().majTransitionAgent(id, null);
		requires().suicide().suicide();
	}

}
