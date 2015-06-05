package generalStructure.impl;

import java.util.List;

import trace.Action;
import environnement.impl.EnvironnementImpl;
import environnement.impl.Forward2CellInfo;
import environnement.interfaces.CellInfo;
import environnement.interfaces.EnvInfos;
import environnement.interfaces.EnvUpdate;
import general.BigEco;
import general.EcoAgentsEtat;
import general.Environnement;
import general.Forward;
import general.Forward2;
import general.ForwardParam;
import general.Launcher;
import generalStructure.interfaces.CycleAlert;
import agents.impl.EcoAgentsEtatImpl;
import agents.interfaces.Do;

public class BigEcoImpl extends BigEco {

	public static BigEcoImpl bigEcoImpl;
	
	private Thread t = null;
	
	public BigEcoImpl() {
		bigEcoImpl = this;
	}
	
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
	
	public void newDynamicAssemblyAgentCellInstance(List<Action> actions) {
		//newDynamicAssemblyAgentCell(actions);
	}
	
	@Override
	protected void start() {
		// TODO Auto-generated method stub
		super.start();
		this.newDynamicAssemblyAgentEtat("Agent Etat 1", "user1", null);
		this.newDynamicAssemblyAgentEtat("Agent Etat 2", "user1", null);
		this.newDynamicAssemblyAgentEtat("Agent Etat 3", "user1", null);
		this.newDynamicAssemblyAgentEtat("Agent Etat 4", "user1", null);
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
				BigEcoImpl.this.newDynamicAssemblyAgentEtat("Agent Etat 4", "user1", null);
				System.out.println("ajout");
				
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {

					e.printStackTrace();
				}
				
				BigEcoImpl.this.newDynamicAssemblyAgentEtat("Agent Etat 5", "user1", null);
				BigEcoImpl.this.newDynamicAssemblyAgentEtat("Agent Etat 6", "user1", null);
				
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

	@Override
	protected Environnement make_envEco() {
		// TODO Auto-generated method stub
		return new EnvironnementImpl();
	}

	@Override
	protected Forward<EnvInfos> make_fwEnvInfos() {
		// TODO Auto-generated method stub
		return new ForwardEnvInfosImpl();
	}
	
	@Override
	protected Forward<EnvUpdate> make_fwEnvUpdate() {
		// TODO Auto-generated method stub
		return new ForwardEnvUpdateImpl();
	}

	@Override
	protected Forward2<CellInfo> make_fwEnvToCell() {
		// TODO Auto-generated method stub
		return new Forward2CellInfo();
	}

	@Override
	protected ForwardParam<CellInfo> make_fwCellInfo() {
		// TODO Auto-generated method stub
		return new ForwardParamCellInfoImpl();
	}

}
