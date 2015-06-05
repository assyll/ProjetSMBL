package generalStructure.impl;

import java.util.List;

import trace.Action;
import environnement.interfaces.CellInfo;
import general.ForwardParam;

public class ForwardParamCellInfoImpl extends ForwardParam<CellInfo> {
	
	@Override
	protected Agent<CellInfo> make_Agent(List<Action> actions) {
		return new AgentImpl(actions);
	}
	
	private class AgentImpl extends Agent<CellInfo> implements CellInfo {

		private List<Action> actions;
		
		public AgentImpl(List<Action> actions) {
			this.actions = actions;
		}
		
		@Override
		public List<Action> getListOfActions() {
			return eco_requires().i().getListOfActions();
		}

		@Override
		public List<String> getAgentIDList() {
			// TODO Auto-generated method stub
			return eco_requires().i().getAgentIDList();
		}

		@Override
		public void addNewStateAgent(String id) {
			eco_requires().i().addNewStateAgent(id);
		}

		@Override
		public void removeStateAgent(String id) {
			eco_requires().i().removeStateAgent(id);
		}

		@Override
		protected CellInfo make_a() {
			// TODO Auto-generated method stub
			return this;
		}
		
	}

}
