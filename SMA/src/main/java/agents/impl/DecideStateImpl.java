package agents.impl;

import agents.interfaces.StateAction;
import agents.interfaces.StateMemory;

public class DecideStateImpl extends AbstractDecide<StateAction, StateMemory> {

	private String id;
	
	public DecideStateImpl(String id) {
		this.id = id;
	}
	
	@Override
	public void makeDecision() {
		System.out.println("Decision de " + id);

		this.requires().action().addStateAgent(id);
	}

}
