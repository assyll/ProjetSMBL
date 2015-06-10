package agents.impl;

import general.Act;

public abstract class AbstractAct<Actionable, ContextUpdate, SharedMemory, CreateAgent, Push> extends Act<Actionable, ContextUpdate, SharedMemory, CreateAgent, Push> {

	@Override
	protected abstract Actionable make_action();
}
