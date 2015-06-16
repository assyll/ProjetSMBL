package agents.interfaces;

import agents.impl.RequestMessage;
import agents.impl.ResponseMessage;

public interface PullMessage {

	public RequestMessage pullRequestMessage();
	public ResponseMessage pullResponseMessage();
	
}
