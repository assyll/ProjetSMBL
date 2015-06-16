package agents.impl;

public class Message {

	protected String senderId;
	protected String receiverId;
	
	protected Type type;
	
	protected Object informations;
	
	/**
	 * Requete permettant d'envoyer n'importe quelle informations.
	 * @param senderId Id de l'agent expediteur
	 * @param receiverId Id de l'agent destinataire
	 * @param type Type du message
	 * @param informations Informations transportees par le message.
	 *                     Son typage varie selon le type de message.
	 */
	public Message(String senderId, String receiverId, Type type,
			Object informations) {
		
		this.senderId = senderId;
		this.receiverId = receiverId;
		this.type = type;
		this.informations = informations;	
	}
	
	public Type getType() {
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
}
