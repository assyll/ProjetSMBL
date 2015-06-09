package agents.impl;

import agents.interfaces.Do;
import general.Perceive;

public abstract class AbstractPerceive<Context, SharedMemory, Pull> extends Perceive<Context, SharedMemory,Pull> implements Do {

	public abstract void makePerception();
	

	@Override
	protected Do make_perception() {
		return this;
	}
	

	@Override
	public void doIt() {
		System.out.println("bete");
		makePerception();
		this.requires().decision().doIt();
	}


}
