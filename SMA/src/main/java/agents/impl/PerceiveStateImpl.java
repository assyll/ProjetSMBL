package agents.impl;

import agents.interfaces.PullMessage;
import agents.interfaces.StateMemory;
import environnement.interfaces.EnvInfos;

public class PerceiveStateImpl extends AbstractPerceive<EnvInfos, StateMemory, PullMessage> {

	private String id;
	
	public PerceiveStateImpl(String id) {
		this.id = id;
	}
	
	@Override
	public void makePerception() {
		System.out.println("Perception de " + id);
	}

}
