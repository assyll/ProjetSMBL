package agents.impl.transition;

import trace.Action;
import general.Memory;
import agents.interfaces.TransMemory;

public class TransMemImpl extends Memory<TransMemory> implements TransMemory{

	private String transId;
	private String stateSourceId;
	private String stateCibleId;
	private Action action;
	
	@Override
	public String getStateCibleId() {
		return stateCibleId;
	}

	@Override
	public void setStateCibleId(String stateCibleId) {
		this.stateCibleId = stateCibleId;
	}

	@Override
	public Action getAction() {
		return action;
	}

	@Override
	public void setAction(Action action) {
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

	public TransMemImpl() {
		this.transId = "";
		this.action = null;
		this.stateSourceId = "";
		this.stateCibleId = "";
	}
	
	public TransMemImpl(String transId, Action action, String sourceId, String cibleId) {
		this.transId = transId;
		this.action = action;
		this.stateSourceId = sourceId;
		this.stateCibleId = cibleId;
	}
	
	public TransMemImpl(String transId, Action action, String sourceId) {
		this.transId = transId;
		this.action = action;
		this.stateSourceId = sourceId;
	}
	
	@Override
	protected TransMemory make_infos() {
		return this;
	}

}
