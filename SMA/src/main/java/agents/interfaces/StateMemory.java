package agents.interfaces;

import java.util.List;
import java.util.Map;

import trace.Action;
import trace.ActionTrace;
import agents.impl.RequestMessage;
import agents.impl.ResponseMessage;

public interface StateMemory {

	public void setNextTraceElmtUserName(String userName); //le nom de l'utilisateur dont on attend la prochaine trace
	
	public void setRequestMessage(RequestMessage msg); //Message de requete reçu (mit à jour par la phase de perception)
	public void setResponseMessage(ResponseMessage msg); //Message de réponse à une requête reçu (mit à jour par la phase de perception)
	public RequestMessage getRequestMessage(); // Récupération de la requête
	public ResponseMessage getResponseMessage(); // Récupération de la réponse
	
	public void setIsRoot(boolean isRoot);
	public void setWaitingForTraceElmt(boolean b); // pour savoir au prochaine cycle si on doit essayer de récupérer un élément de trace ou pas
	public void setWaitingForResponse(boolean b); // en attente d'une réponse à une requête émise 
	public boolean isWaitingForTraceElmt(); // Récupérer l'information "attente d'un élément de trace"
	public boolean hasGotRequestMessage(); // Récupérer l'information "reception d'une requête en attente de traitement"
	public boolean hasGotResponseMessage();// Récupérer l'information "reception d'une réponse en attente de traitement"
	public boolean isWaitingForResponse();// Récupérer l'information "est en attente d'une réponse à une requête"
	public boolean isRoot(); // récupération de l'information (est racine)
	public boolean hasActionToProcess();
	
	public void addNewUserName(String userName); // le nom de l'utilisateur dont on attend la prochaine trace (on est plus en attente d'une autre trace de lui)
	public void removeUserName(String userName); // le nom de l'utilisateur dont on a récupérer la prochaine trace (on est plus en attente d'une autre trace de lui)
	public String getNextUserNameWaitingForAction(); // renvoi le nom de d'un utilisateur dont en est en attente d'éléments de trace
	public List<String> getUserNameWaitingForTraceList();
	public boolean addAction(ActionTrace newAction); // ajout d'une action en attente de traitement relative à une trace d'un utilisateur donnée
	public List<ActionTrace> getActionList(); // récupération de la map des actions en attente de traitment par user
	public ActionTrace getNextAction();
	
	public boolean gotTransitionWithAction(Action action);
	public String getTransitionWithAction(Action action); 
	public Map<String, String> getChildren();
	public String getChildByTransition(String transId);
	public void addNewOutputTransition(String id, Action action);
	public void addChild(String stateId, String transId);
	
}
