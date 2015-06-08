package agents.impl;

import general.Act;

public abstract class AbstractAct<Actionable, ContextUpdate, SharedMemory, Push> extends Act<Actionable, ContextUpdate, SharedMemory, Push> {

	@Override
	protected abstract Actionable make_action();
}
