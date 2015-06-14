package agents.impl;

import java.util.List;

public class RequestMessage extends Message {

	private List<String> childrenWithActionsIds;
	private int nbChildrenWithNoAction;
	private String userName;

	/**
	 * Requete permettant d'envoyer n'importe quelle informations.
	 * @param senderId Id de l'agent expediteur
	 * @param receiverId Id de l'agent destinataire
	 * @param type Type du message
	 * @param informations Informations transportees par le message.
	 *                     Son typage varie selon le type de message.
	 */
	public RequestMessage(String senderId, String receiverId, RequestType type,
			Object informations) {
		
		super.senderId = senderId;
		super.receiverId = receiverId;
		super.type = type;
		super.informations = informations;	
	}
	
	//Requete de fusion envoyee par le nouvel agent arrive dans la cellule
	public RequestMessage(String senderId, String receiverId, int nbChildrenWithNoAction, List<String> childrenWithActions) {
		this.senderId = senderId;
		this.receiverId = receiverId;
	//	type = RequestType.MERGE;
		childrenWithActionsIds = childrenWithActions;
		this.nbChildrenWithNoAction = nbChildrenWithNoAction;
	}
	
	public RequestType getType() {
		return type;
	}
	
	public String getSenderId() {
		return senderId;
	}
	
	public String getReceiverId() {
		return receiverId;
	}
	
	public Object getInformations() {
		return informations;
	}

	//Requ�te de d�fusion envoy�e par le p�re 
	/*public RequestMessage(String senderId, String receiverId, int nbChildrenWithNoAction, List<String> childrenWithActions) {
		this.senderId = senderId;
		this.receiverId = receiverId;
		type = RequestType.MERGE;
		childrenWithActionsIds = childrenWithActions;
		this.nbChildrenWithNoAction = nbChildrenWithNoAction;
	}*/
}
