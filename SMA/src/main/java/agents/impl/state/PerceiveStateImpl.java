package agents.impl.state;

import agents.impl.AbstractPerceive;
import agents.interfaces.PullMessage;
import agents.interfaces.StateMemory;
import environnement.interfaces.ContextInfos;

public class PerceiveStateImpl extends AbstractPerceive<ContextInfos, StateMemory, PullMessage> {

	private String id;
	
	public PerceiveStateImpl(String id) {
		this.id = id;
	}
	
	@Override
	public void makePerception() {
		System.out.println("Perception de " + id);
	}

}
