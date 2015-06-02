package generalStructure.impl;

import general.Forward;
import general.Forward.Agent;
import generalStructure.interfaces.CycleAlert;

import java.util.ArrayList;
import java.util.List;

import agents.interfaces.Do;

public class ForwardImpl extends Forward<Do> implements Do{

	private List<AgentImpl> list = new ArrayList<AgentImpl>();
	
	@Override
	protected Do make_i() {
		// TODO Auto-generated method stub
		return this;
	}

	@Override
	public void doIt() {
		for(AgentImpl agent: list)
			agent.doIt();
	}
	

	@Override
	protected Agent<Do> make_Agent(){
		AgentImpl a = new AgentImpl();
		list.add(a);
		return a;
	}

	private class AgentImpl extends Agent<Do> implements Do, CycleAlert {

		@Override
		public void doIt() {
			this.requires().a().doIt();
		}

		@Override
		protected CycleAlert make_finishedCycle() {
			// TODO Auto-generated method stub
			return this;
		}

		@Override
		public void endOfCycleAlert(String id) {
			eco_requires().finishedCycle().endOfCycleAlert(id);
			
		}
		
	}
}
