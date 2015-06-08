package generalStructure.impl;

import java.util.List;

import trace.Action;
import environnement.interfaces.EnvInfos;
import environnement.interfaces.EnvUpdate;
import general.Forward.TransForward;
import generalStructure.interfaces.CycleAlert;
import agents.interfaces.PullMessage;
import agents.interfaces.SendMessage;

public class TransForwardImpl extends TransForward<CycleAlert, EnvInfos, EnvUpdate, SendMessage, PullMessage> implements CycleAlert, EnvInfos, EnvUpdate, SendMessage, PullMessage {

	
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
		return eco_requires().j().getAllAgentsInCell(listActions);
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

}
