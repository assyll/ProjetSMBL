package agents.impl;

import agents.BigEco;
import agents.EcoAgentsEtat;
import agents.Forward;
import agents.Launcher;
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
	
	private class LauncherImpl extends Launcher implements Runnable {

		@Override
		public void run() {
			System.out.println("run Launcher");
			this.requires().lancer().doIt();
		}
	}
	
	@Override
	protected void start() {
		// TODO Auto-generated method stub
		super.start();
		this.newDynamicAssembly("Agent Etat 1");
		System.out.println("Start BigEco");

	}

}
