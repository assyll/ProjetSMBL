package agents.interfaces;

import agents.impl.RequestMessage;
import agents.impl.ResponseMessage;
import trace.ActionTrace;

public interface TransMemory {
	
	// Caracteristique de l'agent transition
	public String getTransId();
	public ActionTrace getAction();
	public String getStateCibleId();
	public String getStateSourceId();
	public void setAction(ActionTrace action);
	public void setStateCibleId(String stateCibleId);
	
	// Gestion de la boite aux lettre
	public boolean hasRequestMessage();
	public boolean hasResponseMessage();
	public RequestMessage getRequestMessage();
	public ResponseMessage getResponseMessage();
	public void setRequestMessage(RequestMessage request);
	public void setResponseMessage(ResponseMessage response);
}
