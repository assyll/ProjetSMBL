package generalStructure.impl;

import general.Forward;
import general.Forward.Agent;
import generalStructure.interfaces.CycleAlert;

import java.util.ArrayList;
import java.util.List;

import agents.interfaces.Do;

public class ForwardImpl extends Forward<CycleAlert>{

	private List<AgentImpl> list = new ArrayList<AgentImpl>();
	
	@Override
	protected Agent<CycleAlert> make_Agent(){
		AgentImpl a = new AgentImpl();
		list.add(a);
		return a;
	}

	private class AgentImpl extends Agent<CycleAlert> implements CycleAlert {


		@Override
		public void endOfCycleAlert(String id) {
			eco_requires().i().endOfCycleAlert(id);
			
		}

		@Override
		protected CycleAlert make_a() {
			// TODO Auto-generated method stub
			return this;
		}
		
	}
}
