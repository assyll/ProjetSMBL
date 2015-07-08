package generalStructure.impl;

import environnement.interfaces.ContextInfos;
import environnement.interfaces.EnvInfos;
import environnement.interfaces.EnvUpdate;
import general.Forward.StateForward;
import generalStructure.interfaces.CycleAlert;
import generalStructure.interfaces.IGraph;
import generalStructure.interfaces.ILog;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import trace.Action;
import trace.ActionTrace;
import trace.interfaces.ITakeAction;
import agents.impl.RequestMessage;
import agents.impl.RequestType;
import agents.impl.ResponseMessage;
import agents.interfaces.PullMessage;
import agents.interfaces.SendMessage;
import agents.interfaces.StateMemory;
import agents.interfaces.TransMemory;

public class StateForwardImpl extends StateForward<CycleAlert, ContextInfos, EnvInfos, EnvUpdate, SendMessage, PullMessage, ITakeAction>
implements CycleAlert, ContextInfos, EnvUpdate, SendMessage, PullMessage, ILog, IGraph {

	private List<RequestMessage> requestMessagesQueue;
	private List<RequestMessage> requestMessagesFromChildQueue;

	private List<ResponseMessage> responseMessagesQueue;
	private List<ActionTrace> traceElementQueue;
	private String id;
	
	public StateForwardImpl(String id) {
		this.id = id;
		requestMessagesQueue = new LinkedList<>();
		responseMessagesQueue = new LinkedList<>();
		requestMessagesFromChildQueue = new LinkedList<RequestMessage>();
		traceElementQueue = new LinkedList<>();
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
	protected ContextInfos make_b() {
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
		return eco_requires().h().getAllAgentsInCell(listActions);
	}

	@Override
	public ActionTrace getActionTrace(String userName) {
		return eco_requires().j().getActionTrace(userName);
	}

	@Override
	public List<ActionTrace> newUsersTraceList() {
		return eco_requires().j().newUsersTraceList();
	}

	@Override
	protected ILog make_finishedCycleForLog() {
		return this;
	}
	
	@Override
	protected IGraph make_graph() {
		return this;
	}

	@Override
	public void ecrire(Map<String, String> informations) {
		eco_requires().log().ecrire(informations);
	}

	@Override
	public void closeFile() {
		eco_requires().log().closeFile();
	}

	@Override
	public void sendRequestMessage(RequestMessage request) {
		eco_provides().l().sendRequestMessage(request);
	}
	
	@Override
	public void sendResponseMessage(ResponseMessage response) {
		eco_provides().l().sendResponseMessage(response);
	}
	
	@Override
	public void majTransitionAgent(String id, TransMemory memory) {
		eco_requires().graph().majTransitionAgent(id, memory);
	}

	@Override
	public void majStateAgent(String id, StateMemory memory) {
		eco_requires().graph().majStateAgent(id, memory);
	}

	@Override
	public RequestMessage pullRequestMessage() {
		try {
			return requestMessagesQueue.remove(0);
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public ResponseMessage pullResponseMessage() {
		try {
			return responseMessagesQueue.remove(0);
		} catch (Exception e) {
			return null;
		}
	}
	
	public void pushRequestMessage(RequestMessage request) {
		if(request.getType().equals(RequestType.ADD_CHILD)) {
			requestMessagesFromChildQueue.add(request);
		} else {
			requestMessagesQueue.add(request);
		}
	}
	
	public void pushResponseMessage(ResponseMessage response) {
		responseMessagesQueue.add(response);
	}

	@Override
	public void closeGraph() {

	}

	@Override
	public RequestMessage pullRequestMessageFromChild() {
		try {
			return requestMessagesFromChildQueue.remove(0);
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public void giveToken(List<Action> actions) {
		eco_requires().k().giveToken(actions);
	}

	@Override
	public boolean takeToken(List<Action> listeActions) {
		return eco_requires().k().takeToken(listeActions);
	}

}
