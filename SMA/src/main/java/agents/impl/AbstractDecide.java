package agents.impl;

import agents.interfaces.Do;
import general.Decide;

public abstract class AbstractDecide<Actionable, SharedMemory> extends Decide<Actionable, SharedMemory> implements Do {
	
	public abstract void makeDecision();
	
	@Override
	public void doIt() {
		makeDecision();
		//TODO Lorsqu'on recoit un nouvel élément de traces, on doit savoir d'où on est arrivé! (utilse en cas de défusion)
	}

	@Override
	protected Do make_decision() {
		return this;
	}

}
