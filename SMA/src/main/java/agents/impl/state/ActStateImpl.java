package agents.impl.state;

import java.util.List;

import trace.Action;
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addStateAgent(String id) {
		System.out.println("Action de " + id);
		this.requires().finishedCycle().endOfCycleAlert(id);
	}

	@Override
	public void addStateAgent(String id, List<Action> actions) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeAgent(String id, List<Action> actions) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected StateAction make_action() {
		return this;
	}

}
