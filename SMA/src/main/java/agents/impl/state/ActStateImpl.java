package agents.impl.state;

import java.nio.file.AccessMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import trace.Action;
import trace.ActionTrace;
import agents.impl.AbstractAct;
import agents.impl.Child;
import agents.impl.RequestMessage;
import agents.impl.RequestType;
import agents.impl.ResponseMessage;
import agents.impl.ResponseType;
import agents.interfaces.SendMessage;
import agents.interfaces.StateAction;
import agents.interfaces.StateMemory;
import environnement.interfaces.EnvUpdate;
import generalStructure.interfaces.ICreateAgent;

public class ActStateImpl extends AbstractAct<StateAction, EnvUpdate, StateMemory, ICreateAgent, SendMessage> implements StateAction {

	private String id;
	
	public ActStateImpl(String id) {
		this.id = id;
	}
	
	@Override
	public void move(String id, List<Action> currentPositionActions,
			Action newAction) {
		
		System.out.println(id + ": Je me deplace");
		
		requires().setContext().move(id, currentPositionActions, newAction);
		
		logger("add new action", "action added", newAction.toString());
	}

	@Override
	public void addStateAgent(String id) {
		System.out.println(id + "Jajoute un etat agent");
		
		requires().setContext().addStateAgent(id);
		requires().finishedCycle().endOfCycleAlert(id);
		logger("create a new state agent", "created agent id", "?");
	}

	@Override
	public void addStateAgent(String id, List<Action> actions) {
		System.out.println(id + "Jajoute un etat agent");
		
		requires().setContext().addStateAgent(id, actions);
		requires().finishedCycle().endOfCycleAlert(id);
		logger("create a new state agent", "created agent id", "?");
	}

	@Override
	public void removeAgent(String id, List<Action> actions) {
		System.out.println(id + "Jefface un etat agent");
		
		requires().setContext().removeAgent(id, actions);
		requires().finishedCycle().endOfCycleAlert(id);
		logger("remove a agent", "removed agent id", "?");
	}
	
	@Override
	public void askToMerge(List<String> agentIds) {
		System.out.println(id + " --- FUSION --- Je demande a fusionner");
		
		String agentIdList = "[";
		for (String agentId: agentIds) {
			
			if (agentId != id) {
				agentIdList += (agentIdList.equals("[") ? "" : " ") + agentId;
				
				// --------------- request ---------------
				// Je lui envoie mes fils afin qu'il puisse evaluer si cest
				// possible de fusionner ensemble.
				RequestMessage request = new RequestMessage(
						id, agentId, RequestType.TRY_TO_MERGE,
						new List[] {requires().memory().getChildrenWithSohn(),
								requires().memory().getChildrenWithoutSohn()});
				requires().sendMessage().sendRequestMessage(request);
				// ---------------------------------------
			}
		}
		
		logger("asks to merge", "receiver agent id", agentIdList);
		endOfCycle();
	}

	@Override
	protected StateAction make_action() {
		return this;
	}
	
	/**
	 * Permet de logguer chaque action.
	 * @param dones Suite de chaines de caractere.
	 *              Premier string: nom de l'action fait par l'agent
	 *              Ensuite entrer paire de string par paire de string
	 */
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
		requires().graph().majStateAgent(id, requires().memory());
	}

	@Override
	public String[] createTransitionAgent(ActionTrace action) {
		System.out.println(id + "Je cree une transition fils");
		
		List<Action> actions = requires().memory().getActionList();
		
		// Je cree un fils sans fils
		String[] ids = requires().create().createNewTransition(action, this.id);
		
		// mis a jour de mes connaissances
		requires().memory().addChild(ids[1], ids[0], false);
		requires().memory().addAction(action.getAction(), ids[0]);
		requires().memory().addNewOutputTransition(ids[0], action.getAction());
		
		// javertis mes pere du rajout de mon fils
		for (String fatherId: requires().memory().getStateFatherList()) {
			RequestMessage request = new RequestMessage(
					id, fatherId, RequestType.ADD_CHILD, null);
			requires().sendMessage().sendRequestMessage(request);
		}
		
		// Je bouge sur une autre cellule
		move(id, actions, action.getAction());
		
		// Je finis mon cycle
		requires().finishedCycle().endOfCycleAlert(id);
		
		// Je retourne le couple (mon id, l'id de la transition fille)
		return ids;
	}

	@Override
	public void doNothing() {
		System.out.println(id + "Je ne fais rien");
		logger("do nothing");
		endOfCycle();
	}

	@Override
	public void treatRequestMessage() {
		System.out.println(id + "Je traite une requete");
		
		RequestMessage request = requires().memory().getRequestMessage();
		switch ((RequestType) request.getType()) {
		case ADD_FATHER_WITH_USERNAME:
			// Mettre a jour son pere
			requires().memory().addFather(request.getSenderId(),
					((String[]) request.getInformations())[0]);
			// Deviens etat courant en mettant a jour son username
			requires().memory().setNextTraceElmtUserName(
					((String[]) request.getInformations())[1]);
			break;
		
		case  WAIT_FOR_NEXT_ACTION :
			System.out.println("RECU UN NOUVEAU USERRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRR");
			String user = (String) request.getInformations();
			requires().memory().addNewUserName(user);
			break;
			
		case ADD_CHILD:
			// Mon fils a un fils
			String childId = request.getSenderId();
			Child child = findChildById(childId);
			// Mis a jour
			if (child != null && requires().memory().getChildrenWithoutSohn().
					contains(childId)) {
				requires().memory().addChild(
						child.getEndStateId(), child.getTransId(), true);
			}
			break;
			
		case TRY_TO_MERGE:
			System.out.println(id + "---FUSION--- On me demande de fusionner");
			// On me demande de fusionner
			// Je verifie si on peut le faire
			boolean canMerge = isPossibleToMerge(
					requires().memory().getChildrenWithSohn(),
					requires().memory().getChildrenWithoutSohn(),
					((List []) request.getInformations())[0],
					((List []) request.getInformations())[1]);
			// Je lui reponds
			ResponseMessage response =
					new ResponseMessage(id, request.getSenderId(),
					canMerge ? ResponseType.ACCEPT_MERGE :
						ResponseType.REFUSE_MERGE, null);
			requires().sendMessage().sendResponseMessage(response);
			break;
			
		case SUICIDE_HIERARCHY:
			// Une transition m'a demande de me suicider.
			// Je me suicide apres avoir fait la reaction en chaine.
			// Je procede a cette demarche seulement si je n'ai pas de
			// transition entrante.
			
			if (requires().memory().getTransFatherList().size() == 0) {
				// Je cree et envoie la requete a tous mes transitions fils
				for (String transId: requires().memory().getTransFatherList()) {
					RequestMessage suicideRequest = new RequestMessage(
							id, transId, RequestType.SUICIDE_HIERARCHY, null);
				}
				
				// Ensuite je me suicide
				suicide();
				
			} else {
				doNothing();
			}
			
			break;
			
		}
		
		requires().memory().removeRequestMsg();
		endOfCycle();
	}
	
	@Override
	public void treatResponseMessage() {
		System.out.println(id + "Je traite une reponse");
		
		ResponseMessage response = requires().memory().getResponseMessage();
		switch ((ResponseType) response.getType()) {
		case ACCEPT_MERGE:
			// Peut fusionner avec lui, lancer la procedure de fusion
			mergeWith(response.getSenderId(), response.getInformations());
			break;
			
		case REFUSE_MERGE:
			// Peut pas fusionner avec lui, ne rien faire
			System.out.println(id + "---FUSION--- on ma refuser la fusion");
			doNothing();
			break;
		}
		
		requires().memory().removeResponseMsg();
		endOfCycle();
	}

	@Override
	public void sendRequestMessage(RequestMessage msg) {
		System.out.println(id + "Jenvoie une requete");
		
		this.requires().sendMessage().sendRequestMessage(msg);
		endOfCycle();
	}
	
	private void endOfCycle() {
		this.requires().finishedCycle().endOfCycleAlert(id);
	}
	
	/**
	 * Recherche dans sa liste des fils celui avec l'id passe en parametre.
	 * @param childId id du fils
	 * @return l'objet Child du fils recherche
	 */
	private Child findChildById(String childId) {
		for (Child child: requires().memory().getChildrenWithoutSohn()) {
			if (child.getEndStateId().equals(childId)) {
				return child;
			}
		}
		for (Child child: requires().memory().getChildrenWithSohn()) {
			if (child.getEndStateId().equals(childId)) {
				return child;
			}
		}
		// pas possible
		return null;
	}
	
	/**
	 * Verifie si deux agents etats ont la possibilite de fusionner.
	 * @param childWith1 fils avec fils du premier agent
	 * @param childWithout1 fils sans fils du premier agent
	 * @param childWith2 fils avec fils du second agent
	 * @param childWithout2 fils sans fils du second agent
	 * @return vrai sils sont fusionnables, faux sinon
	 */
	private boolean isPossibleToMerge(
			List<Child> childWith1, List<Child> childWithout1,
			List<Child> childWith2, List<Child> childWithout2) {
		
		// 1er cas: ils n'ont que des fils sans fils
		if (childWith1.size() == 0 && childWith2.size() == 0) {
			return true;
		}
		
		// 2e cas: ils ont le meme nombre de fils sans fils
		//         et leurs actions (des fils avec fils) menent
		//            deux a deux au meme fils (de meme id).
		if (childWithout1.size() == childWithout2.size() &&
				actionsLeadSameState(childWith1, childWith2)) {
			return true;
		}
		
		// Sinon pas possible
		return false;
	}
	
	/**
	 * Verifie si chaque action deux a deux mene bien au meme etat.
	 * @param children1 enfants du premier etat
	 * @param children2 enfants du second etat
	 * @return vrai s'ils les menent, faux sinon
	 */
	private boolean actionsLeadSameState(
			List<Child> children1, List<Child> children2) {
		
		// Parcours les enfants du premier etat
		for (Child child1: children1) {
			// recupere l'id de la transition
			String transId1 = child1.getTransId();
			// recupere le fils avec le meme id de transition
			Child child2 = findChildByTransId(children2, transId1);
			// Si ces enfants n'ont pas le meme id, retourner FAUX
			if (child1.getEndStateId().equals(child2.getEndStateId())) {
				return false;
			}
		}
		// Si tout cest bien passe, retourner VRAI
		return true;
	}
	
	/**
	 * Cherche le fils dont l'id de sa transition est la meme que celle
	 * passee en parametre.
	 * @param children les fils
	 * @param transId l'id de la transition
	 * @return le fils avec cette meme transition
	 */
	private Child findChildByTransId(List<Child> children, String transId) {
		for (Child child: children) {
			if (child.getTransId().equals(transId)) {
				return child;
			}
		}
		return null;
	}
	
	/**
	 * Procedure de fusion entre l'etat courant et celui passe en parametre.
	 * L'etat resultant de la fusion est celui passe en parametre.
	 * L'etat courant sera detruit a l'issue de cette fusion.
	 * @param mergeStateId id de l'agent resultant de la fusion
	 * @param infosMergeStateId toutes informations utiles de l'agent
	 *                          resultant de la fusion
	 */
	private void mergeWith(String mergeStateId, Object infosMergeStateId) {
		RequestMessage request;
		
		System.out.println(id + "--- FUSION --- Je fusionne avec " + mergeStateId);
		
		// Demande a ses transitions entrantes de mettre a jour leur fils
		// (Les rediriger vers le noeud resultant de la fusion)
		for (String transId: requires().memory().getTransFatherList()) {
			request = new RequestMessage(
					id, transId, RequestType.UPDATE_CHILD, mergeStateId);
			requires().sendMessage().sendRequestMessage(request);
		}
		
		// Demande a ses transitions sortantes de se suicider
		// Ne pas oublier de faire une reaction en chaine
		// cad: les transitions sortantes demandent a leur tour
		//      a leurs etats d'arrives de se suicider s'ils n'ont pas
		//      de transition entrante. Et ces etats qui se suicident
		//      demandent aussi a leur tour a leurs transitions de se
		//      suicider ect ...
		for (String transId: requires().memory().getTransChildList()) {
			request = new RequestMessage(
					id, transId, RequestType.SUICIDE_HIERARCHY, null);
			requires().sendMessage().sendRequestMessage(request);
		}
		
		// Notifie ses peres pour eventuellement fusionner a leur tour
		for (String stateId: requires().memory().getStateFatherList()) {
			RequestMessage askMergeFatherRequest = new RequestMessage(
					id, stateId, RequestType.ASK_THE_FATHER_TO_MERGE, null);
			requires().sendMessage().sendRequestMessage(askMergeFatherRequest);
		}
		
		// Se suicide
		suicide();
		
	}
	
	private void suicide() {
		requires().suicide().suicide();
	}

}
