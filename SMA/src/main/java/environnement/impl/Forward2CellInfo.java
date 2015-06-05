package environnement.impl;

import java.util.List;

import trace.Action;
import environnement.interfaces.CellInfo;
import general.Forward2;

public class Forward2CellInfo extends Forward2<CellInfo> {

	@Override
	protected CellInfo make_i() {
		// TODO Auto-generated method stub
		return new AgentImpl();
	}
	
	private class AgentImpl extends Agent<CellInfo> implements CellInfo {

		@Override
		public List<Action> getListOfActions() {
			// TODO Auto-generated method stub
			return eco_provides().i().getListOfActions();
		}

		@Override
		public List<String> getAgentIDList() {
			// TODO Auto-generated method stub
			return eco_provides().i().getAgentIDList();
		}

		@Override
		public void addNewStateAgent(String id) {
			// TODO Auto-generated method stub
			eco_provides().i().addNewStateAgent(id);
		}

		@Override
		public void removeStateAgent(String id) {
			// TODO Auto-generated method stub
			eco_provides().i().removeStateAgent(id);
		}
		
	}
	
}
