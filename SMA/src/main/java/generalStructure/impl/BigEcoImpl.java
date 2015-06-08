package generalStructure.impl;

import environnement.impl.EnvironnementImpl;
import environnement.interfaces.EnvInfos;
import environnement.interfaces.EnvUpdate;
import general.BigEco;
import general.EcoAgents;
import general.Environnement;
import general.Forward;
import general.Launcher;
import generalStructure.interfaces.CycleAlert;
import agents.impl.EcoAgentsImpl;
import agents.interfaces.PullMessage;
import agents.interfaces.SendMessage;
import agents.interfaces.StateAction;
import agents.interfaces.StateMemory;
import agents.interfaces.TransAction;
import agents.interfaces.TransMemory;

public class BigEcoImpl extends BigEco<StateAction, TransAction, EnvInfos,
EnvUpdate, StateMemory, TransMemory, SendMessage, PullMessage> {

	public static BigEcoImpl bigEcoImpl;

	private Thread t = null;

	public BigEcoImpl() {
		bigEcoImpl = this;
	}

	@Override
	protected EcoAgents<StateAction, TransAction, EnvInfos,
	EnvUpdate, StateMemory, TransMemory, SendMessage, PullMessage> make_ecoAE() {
		return new EcoAgentsImpl();
	}

	@Override
	protected Forward<CycleAlert, EnvInfos, EnvUpdate, SendMessage, PullMessage> make_fw() {
		return new ForwardImpl();
	}

	@Override
	protected Launcher make_launcher() {
		return new LauncherImpl();
	}


	@Override
	protected void start() {
		super.start();

		this.newDynamicAssemblyAgentEtat("Agent Etat 1");
		this.newDynamicAssemblyAgentEtat("Agent Etat 2");
		this.newDynamicAssemblyAgentEtat("Agent Etat 3");
		this.newDynamicAssemblyAgentEtat("Agent Etat 4");
		System.out.println("Start BigEco");

		t = new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println("debut ajout");
				BigEcoImpl.this.newDynamicAssemblyAgentEtat("Agent Etat 4");
				System.out.println("ajout");

				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {

					e.printStackTrace();
				}

				BigEcoImpl.this.newDynamicAssemblyAgentEtat("Agent Etat 5");
				BigEcoImpl.this.newDynamicAssemblyAgentEtat("Agent Etat 6");

				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {

					e.printStackTrace();
				}

				//BigEcoImpl.this.parts().launcher().call().stop();
			}

		});
		t.start();

	}

	@Override
	protected Environnement<EnvInfos, EnvUpdate> make_envEco() {
		return new EnvironnementImpl();
	}
}
