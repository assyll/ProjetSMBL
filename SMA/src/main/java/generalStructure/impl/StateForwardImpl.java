package generalStructure.impl;

import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

import trace.Action;
import trace.ActionTrace;
import environnement.interfaces.EnvInfos;
import environnement.interfaces.EnvUpdate;
import general.Forward.StateForward;
import generalStructure.interfaces.CycleAlert;
import agents.impl.RequestMessage;
import agents.impl.ResponseMessage;
import agents.interfaces.PullMessage;
import agents.interfaces.SendMessage;

public class StateForwardImpl extends StateForward<CycleAlert, EnvInfos, EnvUpdate, SendMessage, PullMessage> implements CycleAlert, EnvInfos, EnvUpdate, SendMessage, PullMessage {

	private Queue<RequestMessage> requestMessagesQueue;
	private Queue<ResponseMessage> responseMessagesQueue;
	private Queue<ActionTrace> traceElementQueue;
	private String id;
	
	public StateForwardImpl(String id) {
		this.id = id;
		requestMessagesQueue = new PriorityQueue<>();
		responseMessagesQueue = new PriorityQueue<>();
		traceElementQueue = new PriorityQueue<>();
	}
	
	@Override
	public void endOfCycleAlert(String id) {
		eco_requires().i().endOfCycleAlert(id);
	}

	@Override
	protected CycleAlert make_a() {
		return this;
	}

	@Override
	protected EnvInfos make_b() {
		return this;
	}

	@Override
	protected EnvUpdate make_c() {
		return this;
	}

	@Override
	protected SendMessage make_d() {
		return this;
	}

	@Override
	protected PullMessage make_e() {
		return this;
	}

	@Override
	public void move(String id, List<Action> currentPositionActions,
			Action newAction) {
		eco_requires().k().move(id, currentPositionActions, newAction);
	}

	@Override
	public void addStateAgent(String id) {
		eco_requires().k().addStateAgent(id);
	}

	@Override
	public void addStateAgent(String id, List<Action> actions) {
		eco_requires().k().addStateAgent(id, actions);
	}

	@Override
	public void removeAgent(String id, List<Action> actions) {
		eco_requires().k().removeAgent(id, actions);
	}

	@Override
	public List<String> getAllAgentsInCell(List<Action> listActions) {
		return eco_requires().j().getAllAgentsInCell(listActions);
	}
}
