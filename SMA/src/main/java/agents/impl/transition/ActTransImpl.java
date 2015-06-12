package agents.impl.transition;

import java.util.HashMap;
import java.util.Map;

import agents.impl.AbstractAct;
import agents.interfaces.SendMessage;
import agents.interfaces.TransAction;
import agents.interfaces.TransMemory;
import environnement.interfaces.EnvUpdate;
import generalStructure.interfaces.ICreateAgent;

public class ActTransImpl extends AbstractAct<TransAction, EnvUpdate, TransMemory, ICreateAgent, SendMessage> implements TransAction {

	private String id;
	
	public ActTransImpl(String id) {
		this.id = id;
	}
	
	@Override
	protected TransAction make_action() {
		return this;
	}

	@Override
	public void doNothing() {
		logger("do nothing");
	}
	
	private void logger(String ... dones) {
		Map<String, String> informations = new HashMap<>();
		
		informations.put("id", id);
		informations.put("agentType", "state");
		informations.put("done", dones[0]);
		
		for (int i = 1; i < dones.length;) {
			informations.put(dones[i++], dones[i++]);
		}
		
		majGraph();
		requires().finishedCycleForLog().ecrire(informations);
	}
	
	private void majGraph() {
		requires().graph().majTransitionAgent(id, requires().memory());
	}

}
