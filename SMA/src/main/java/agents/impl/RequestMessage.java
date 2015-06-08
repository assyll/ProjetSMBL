package agents.impl;

import java.util.List;

public class RequestMessage {

	private RequestType type;//MERGE, UNMERGE, TRY_TO_MERGE, ADD_CHILD, GET_NB_CHILD 
	private String senderId;
	private String receiverId;
	private List<String> childrenWithActionsIds;
	private int nbChildrenWithNoAction;


	//Requête de fusion envoyée par le nouvel agent arrivé dans la cellule
	public RequestMessage(String senderId, String receiverId, int nbChildrenWithNoAction, List<String> childrenWithActions) {
		this.senderId = senderId;
		this.receiverId = receiverId;
	//	type = RequestType.MERGE;
		childrenWithActionsIds = childrenWithActions;
		this.nbChildrenWithNoAction = nbChildrenWithNoAction;
	}

	//Requête de défusion envoyée par le père 
	/*public RequestMessage(String senderId, String receiverId, int nbChildrenWithNoAction, List<String> childrenWithActions) {
		this.senderId = senderId;
		this.receiverId = receiverId;
		type = RequestType.MERGE;
		childrenWithActionsIds = childrenWithActions;
		this.nbChildrenWithNoAction = nbChildrenWithNoAction;
	}*/
}
