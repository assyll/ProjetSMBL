package agents.interfaces;

import agents.impl.RequestMessage;
import agents.impl.ResponseMessage;

public interface SendMessage {

	public void sendRequestMessage(RequestMessage request);
	public void sendResponseMessage(ResponseMessage response);
	
}
