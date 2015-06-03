package generalStructure.impl;

import general.BigEco;
import general.EcoAgentsEtat;
import general.Forward;
import general.Launcher;
import generalStructure.interfaces.CycleAlert;
import agents.impl.EcoAgentsEtatImpl;
import agents.interfaces.Do;

public class BigEcoImpl extends BigEco {

	Thread t = null;
	@Override
	protected EcoAgentsEtat make_ecoAE() {
		// TODO Auto-generated method stub
		return new EcoAgentsEtatImpl();
	}

	@Override
	protected Forward<CycleAlert> make_fw() {
		// TODO Auto-generated method stub
		return new ForwardImpl();
	}

	@Override
	protected Launcher make_launcher() {
		// TODO Auto-generated method stub
		return new LauncherImpl();
	}
	
	
	
	@Override
	protected void start() {
		// TODO Auto-generated method stub
		super.start();
		this.newDynamicAssembly("Agent Etat 1");
		this.newDynamicAssembly("Agent Etat 2");
		this.newDynamicAssembly("Agent Etat 3");
		this.newDynamicAssembly("Agent Etat 4");
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
				BigEcoImpl.this.newDynamicAssembly("Agent Etat 4");
				System.out.println("ajout");
				
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {

					e.printStackTrace();
				}
				
				BigEcoImpl.this.newDynamicAssembly("Agent Etat 5");
				BigEcoImpl.this.newDynamicAssembly("Agent Etat 6");
				
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {

					e.printStackTrace();
				}
				
				BigEcoImpl.this.parts().launcher().call().stop();
			}
			
		});
		t.start();

		
		


	}

}