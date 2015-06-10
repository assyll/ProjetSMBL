package agents.impl.state;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import trace.Action;
import trace.ActionTrace;
import agents.impl.AbstractAct;
import agents.interfaces.SendMessage;
import agents.interfaces.StateAction;
import agents.interfaces.StateMemory;
import environnement.interfaces.EnvUpdate;
import generalStructure.interfaces.ICreateAgent;

public class ActStateImpl extends AbstractAct<StateAction, EnvUpdate, StateMemory, ICreateAgent, SendMessage> implements StateAction {

	private String id;
	
	public ActStateImpl(String id) {
		this.id = id;
	}
	
	@Override
	public void move(String id, List<Action> currentPositionActions,
			Action newAction) {
		requires().setContext().move(id, currentPositionActions, newAction);
		requires().finishedCycle().endOfCycleAlert(id);
		logger("add new action", "action added", newAction.toString());
	}

	@Override
	public void addStateAgent(String id) {
		System.out.println("Action de " + id);
		requires().setContext().addStateAgent(id);
		requires().finishedCycle().endOfCycleAlert(id);
		logger("create a new state agent", "created agent id", "?");
	}

	@Override
	public void addStateAgent(String id, List<Action> actions) {
		requires().setContext().addStateAgent(id, actions);
		requires().finishedCycle().endOfCycleAlert(id);
		logger("create a new state agent", "created agent id", "?");
	}

	@Override
	public void removeAgent(String id, List<Action> actions) {
		requires().setContext().removeAgent(id, actions);
		requires().finishedCycle().endOfCycleAlert(id);
		logger("remove a agent", "removed agent id", "?");
	}

	@Override
	protected StateAction make_action() {
		return this;
	}
	
	private void logger(String ... dones) {
		Map<String, String> informations = new HashMap<>();
		
		informations.put("id", id);
		informations.put("agentType", "state");
		informations.put("done", dones[0]);
		
		for (int i = 1; i < dones.length;) {
			informations.put(dones[i++], dones[i++]);
		}
		
		requires().finishedCycleForLog().ecrire(informations);
	}

	@Override
	public void createTransitionAgent(String id, ActionTrace action) {
		this.requires().create().createNewTransition(id, action, this.id);
		
	}

	@Override
	public void doNothing() {
		this.requires().finishedCycle().endOfCycleAlert(id);
	}

}
