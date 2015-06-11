package generalStructure.impl;

import environnement.interfaces.ContextInfos;
import environnement.interfaces.EnvInfos;
import environnement.interfaces.EnvUpdate;
import general.Forward.TransForward;
import generalStructure.interfaces.CycleAlert;
import generalStructure.interfaces.IGraph;
import generalStructure.interfaces.ILog;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import trace.Action;
import trace.interfaces.ITakeAction;
import agents.impl.Message;
import agents.impl.RequestMessage;
import agents.impl.ResponseMessage;
import agents.interfaces.PullMessage;
import agents.interfaces.SendMessage;

public class TransForwardImpl extends TransForward<CycleAlert, ContextInfos, EnvInfos, EnvUpdate, SendMessage, PullMessage, ITakeAction> 
implements CycleAlert, EnvInfos, EnvUpdate, SendMessage, PullMessage, ILog, ILetterBox, IGraph {

	private String id;
	private List<RequestMessage> requestMessagesQueue;
	private List<ResponseMessage> responseMessagesQueue;
	
	public TransForwardImpl(String id) {
		this.id = id;
		requestMessagesQueue = new LinkedList<>();
		responseMessagesQueue = new LinkedList<>();
	}
	
	@Override
	public void move(String id, List<Action> currentPositionActions,
			Action newAction) {
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
	public void endOfCycleAlert(String id) {
		eco_requires().i().endOfCycleAlert(id);
	}

	@Override
	protected CycleAlert make_a() {
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
	protected EnvInfos make_b() {
		return this;
	}

	@Override
	protected ILog make_finishedCycleForLog() {
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

	@Override
	public void pushRequestMessage(RequestMessage request) {
		requestMessagesQueue.add(request);
	}

	@Override
	public void pushResponseMessage(ResponseMessage response) {
		responseMessagesQueue.add(response);
	}

	@Override
	protected IGraph make_graph() {
		return this;
	}

	@Override
	public void majTransitionAgent() {
		eco_requires().graph().majTransitionAgent();
	}

	@Override
	public void majStateAgent() {
		eco_requires().graph().majStateAgent();
	}
}
