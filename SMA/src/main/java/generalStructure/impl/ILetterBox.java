package generalStructure.impl;

import agents.impl.RequestMessage;
import agents.impl.ResponseMessage;

public interface ILetterBox {

	public RequestMessage pullRequestMessage();
	public ResponseMessage pullResponseMessage();
	
	public void pushRequestMessage(RequestMessage request);
	public void pushResponseMessage(ResponseMessage response);
}
