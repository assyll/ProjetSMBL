package agents.impl;

public class ResponseMessage extends Message {

	public ResponseMessage(String senderId, String receiverId,
			ResponseType type, Object informations) {
		super(senderId, receiverId, type, informations);
	}
	
	public ResponseMessage clone() {
		return new ResponseMessage(
				senderId, receiverId, (ResponseType) type, informations);
	}
	
}
