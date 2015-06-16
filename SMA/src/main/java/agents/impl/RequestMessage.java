package agents.impl;

public class RequestMessage extends Message {

	public RequestMessage(String senderId, String receiverId, RequestType type,
			Object informations) {
		
		super(senderId, receiverId, type, informations);
	}
	
	public RequestMessage clone() {
		return new RequestMessage(
				senderId, receiverId, (RequestType) type, informations);
	}
}
