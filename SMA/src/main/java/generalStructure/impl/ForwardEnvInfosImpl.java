package generalStructure.impl;

import java.util.List;

import trace.Action;
import environnement.interfaces.EnvInfos;
import general.Forward;

public class ForwardEnvInfosImpl extends Forward<EnvInfos> {

	@Override
	protected Agent<EnvInfos> make_Agent() {
		// TODO Auto-generated method stub
		return new AgentImpl();
	}
	
	private class AgentImpl extends Agent<EnvInfos> implements EnvInfos {

		@Override
		public List<String> getAllAgentsInCell(List<Action> listActions) {
			// TODO Auto-generated method stub
			return eco_requires().i().getAllAgentsInCell(listActions);
		}

		@Override
		protected EnvInfos make_a() {
			// TODO Auto-generated method stub
			return this;
		}
		
	}

}
