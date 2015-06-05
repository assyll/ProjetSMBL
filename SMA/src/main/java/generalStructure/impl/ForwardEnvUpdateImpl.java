package generalStructure.impl;

import java.util.List;

import trace.Action;
import environnement.interfaces.EnvUpdate;
import general.Forward;

public class ForwardEnvUpdateImpl extends Forward<EnvUpdate> {

	@Override
	protected general.Forward.Agent<EnvUpdate> make_Agent() {
		// TODO Auto-generated method stub
		return new AgentImpl();
	}
	
	private class AgentImpl extends Agent<EnvUpdate> implements EnvUpdate {

		@Override
		public void move(String id, List<Action> currentPositionActions,
				Action newAction) {
			// TODO Auto-generated method stub
			eco_requires().i().move(id, currentPositionActions, newAction);
		}

		@Override
		public void addAgent(String id) {
			// TODO Auto-generated method stub
			eco_requires().i().addAgent(id);
		}

		@Override
		public void addAgent(String id, List<Action> actions) {
			// TODO Auto-generated method stub
			eco_requires().i().addAgent(id, actions);
		}

		@Override
		public void removeAgent(String id, List<Action> actions) {
			// TODO Auto-generated method stub
			eco_requires().i().removeAgent(id, actions);
		}

		@Override
		protected EnvUpdate make_a() {
			// TODO Auto-generated method stub
			return this;
		}
		
	}

}
