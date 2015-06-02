package agents.impl;

import agents.BigEco;
import agents.EcoAgentsEtat;
import agents.Forward;
import agents.Launcher;
import agents.interfaces.Callable;
import agents.interfaces.Do;

public class BigEcoImpl extends BigEco {

	@Override
	protected EcoAgentsEtat make_ecoAE() {
		// TODO Auto-generated method stub
		return new EcoAgentsEtatImpl();
	}

	@Override
	protected Forward<Do> make_fw() {
		// TODO Auto-generated method stub
		return new ForwardImpl();
	}

	@Override
	protected Launcher make_launcher() {
		// TODO Auto-generated method stub
		return new LauncherImpl();
	}
	
	private class LauncherImpl extends Launcher implements Callable{

		@Override
		public void run() {
			System.out.println("Launcher lancé!");
			this.requires().lancer().doIt();
			
		}

		@Override
		protected Callable make_call() {
			// TODO Auto-generated method stub
			return this;
		}


	}
	
	@Override
	protected void start() {
		// TODO Auto-generated method stub
		super.start();
		this.newDynamicAssembly("Agent Etat 1");
		this.newDynamicAssembly("Agent Etat 2");
		this.newDynamicAssembly("Agent Etat 3");
		System.out.println("Start BigEco");
		this.parts().launcher().call().run();


	}

}
