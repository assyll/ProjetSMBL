package agents.impl.transition;

import general.Memory;
import trace.Action;
import trace.ActionTrace;
import agents.impl.RequestMessage;
import agents.impl.ResponseMessage;
import agents.interfaces.TransMemory;

public class TransMemImpl extends Memory<TransMemory> implements TransMemory {

	// Caracteristique de l'agent transition
	private String transId;
	private String stateSourceId;
	private String stateCibleId;
	private ActionTrace action;
	
	// Gestion des boites aux lettres
	private RequestMessage request;
	private ResponseMessage response;
	
	public TransMemImpl() {
		action = null;
		transId = null;
		stateCibleId = null;
		stateSourceId = null;
		
		request = null;
		response = null;
	}
	
	@Override
	public String getStateCibleId() {
		return stateCibleId;
	}

	@Override
	public void setStateCibleId(String stateCibleId) {
		this.stateCibleId = stateCibleId;
	}

	@Override
	public ActionTrace getAction() {
		return action;
	}

	@Override
	public void setAction(ActionTrace action) {
		this.action = action;
	}

	@Override
	public String getTransId() {
		return transId;
	}

	@Override
	public String getStateSourceId() {
		return stateSourceId;
	}
	
	public TransMemImpl(String transId, ActionTrace action, String sourceId, String cibleId) {
		this.transId = transId;
		this.action = action;
		this.stateSourceId = sourceId;
		this.stateCibleId = cibleId;
	}
	
	public TransMemImpl(String transId, ActionTrace action, String sourceId) {
		this.transId = transId;
		this.action = action;
		this.stateSourceId = sourceId;
	}
	
	@Override
	protected TransMemory make_infos() {
		return this;
	}

	@Override
	public boolean hasRequestMessage() {
		return request != null;
	}

	@Override
	public boolean hasResponseMessage() {
		return response != null;
	}

	@Override
	public RequestMessage getRequestMessage() {
		RequestMessage requestClone = request.clone();
		request = null; // on consomme la requete !
		return requestClone;
	}

	@Override
	public ResponseMessage getResponseMessage() {
		ResponseMessage responseClone = response.clone();
		response = null; // on consomme la reponse !
		return responseClone;
	}

	@Override
	public void setRequestMessage(RequestMessage request) {
		this.request = request;
	}

	@Override
	public void setResponseMessage(ResponseMessage response) {
		this.response = response;
	}

}
