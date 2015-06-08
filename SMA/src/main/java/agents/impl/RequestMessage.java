package agents.impl;

import java.util.List;

public class RequestMessage {

	private RequestType type;//MERGE, UNMERGE, TRY_TO_MERGE, ADD_CHILD, GET_NB_CHILD 
	private String senderId;
	private String receiverId;
	private List<String> childrenWithActionsIds;
	private int nbChildrenWithNoAction;


	//Requ�te de fusion envoy�e par le nouvel agent arriv� dans la cellule
	public RequestMessage(String senderId, String receiverId, int nbChildrenWithNoAction, List<String> childrenWithActions) {
		this.senderId = senderId;
		this.receiverId = receiverId;
	//	type = RequestType.MERGE;
		childrenWithActionsIds = childrenWithActions;
		this.nbChildrenWithNoAction = nbChildrenWithNoAction;
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
