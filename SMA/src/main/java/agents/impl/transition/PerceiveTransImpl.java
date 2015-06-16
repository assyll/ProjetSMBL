package agents.impl.transition;

import agents.impl.AbstractPerceive;
import agents.impl.Message;
import agents.impl.RequestMessage;
import agents.impl.ResponseMessage;
import agents.interfaces.PullMessage;
import agents.interfaces.TransMemory;
import environnement.interfaces.EnvInfos;

public class PerceiveTransImpl extends
	AbstractPerceive<EnvInfos, TransMemory, PullMessage> {

	private String id;
	
	@Override
	public void makePerception() {
		
		Message message;
		
		if ((message = requires().getMessage().pullResponseMessage())
				!= null) {
			// J'essaie de recuperer une reponse
			requires().memory().setResponseMessage((ResponseMessage) message);
			
		} else if ((message = requires().getMessage().pullRequestMessage())
				!= null) {
			// J'essaie de recuperer une requete
			requires().memory().setRequestMessage((RequestMessage) message);
			
		}
		
	}

}
