package agents.interfaces;

import java.util.List;
import java.util.Map;

import trace.Action;
import trace.ActionTrace;
import agents.impl.RequestMessage;
import agents.impl.ResponseMessage;

public interface StateMemory {

	public void setNextTraceElmtUserName(String userName); //le nom de l'utilisateur dont on attend la prochaine trace
	
	public void setRequestMessage(RequestMessage msg); //Message de requete re�u (mit � jour par la phase de perception)
	public void setResponseMessage(ResponseMessage msg); //Message de r�ponse � une requ�te re�u (mit � jour par la phase de perception)
	public RequestMessage getRequestMessage(); // R�cup�ration de la requ�te
	public ResponseMessage getResponseMessage(); // R�cup�ration de la r�ponse
	
	public void setIsRoot(boolean isRoot);
	public void setWaitingForTraceElmt(boolean b); // pour savoir au prochaine cycle si on doit essayer de r�cup�rer un �l�ment de trace ou pas
	public void setWaitingForResponse(boolean b); // en attente d'une r�ponse � une requ�te �mise 
	public boolean isWaitingForTraceElmt(); // R�cup�rer l'information "attente d'un �l�ment de trace"
	public boolean hasGotRequestMessage(); // R�cup�rer l'information "reception d'une requ�te en attente de traitement"
	public boolean hasGotResponseMessage();// R�cup�rer l'information "reception d'une r�ponse en attente de traitement"
	public boolean isWaitingForResponse();// R�cup�rer l'information "est en attente d'une r�ponse � une requ�te"
	public boolean hasActionToProcess();
	
	public void addNewUserName(String userName); // le nom de l'utilisateur dont on attend la prochaine trace (on est plus en attente d'une autre trace de lui)
	public void removeUserName(String userName); // le nom de l'utilisateur dont on a r�cup�rer la prochaine trace (on est plus en attente d'une autre trace de lui)
	public String getNextUserNameWaitingForAction(); // renvoi le nom de d'un utilisateur dont en est en attente d'�l�ments de trace
	public List<String> getUserNameWaitingForTraceList();
	public boolean addAction(ActionTrace newAction); // ajout d'une action en attente de traitement relative � une trace d'un utilisateur donn�e
	public List<ActionTrace> getActionList(); // r�cup�ration de la map des actions en attente de traitment par user
	public ActionTrace getNextAction();
	
	public boolean gotTransitionWithAction(Action action);
	public String getTransitionWithAction(Action action); 
	public String getChildByTransition(String transId);
	public void addNewOutputTransition(String id, Action action);
	public void addChild(String stateId, String transId);
	public void addFather(String transId);
	
	// Utiles aussi pour creer le graphe
	public boolean isRoot(); // recuperation de l'information (est racine)
	public boolean isFinal();
	public Map<String, String> getChildren();
	
}
