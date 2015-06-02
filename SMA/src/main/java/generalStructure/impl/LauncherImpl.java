package generalStructure.impl;

import general.Launcher;
import generalStructure.interfaces.CycleAlert;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import agents.interfaces.Callable;
import agents.interfaces.IGetThread;

public class LauncherImpl extends Launcher implements Callable, CycleAlert, IGetThread{

	private int nbFinishedCycles = 0;
	private Map<String, Thread> threads = new HashMap<String,Thread>();
	private int nbAgentsTotal = 3;
	private ExecutorService  execService = null;
	private List<Runnable> agents = new ArrayList<Runnable>();
	private int nbAgentsPerCycle = 0;

	/*	@Override
	protected void start() {
		super.start();
		thread= new Thread(new Runnable(){

			@Override
			public void run() {
				while(!thread.isInterrupted()){

				}
			}
		});
	}*/

	@Override
	public void run() {
		synchronized(agents){
		nbAgentsPerCycle = agents.size();
		System.out.println();
		System.out.println("-----------------------------------------------------------------------------");
		System.out.println("Nb agents par cycle = "+nbAgentsPerCycle);
		System.out.println("RUN!!!!!  "+ agents.size());

		
			if(!(execService == null)){
				synchronized (agents) {
					for(Runnable e: agents )
						execService.execute(e);
				}

			}}

		System.out.println("End boucles");
		//	this.requires().lancer().doIt();

	}

	@Override
	protected Callable make_call() {
		// TODO Auto-generated method stub
		return this;
	}

	@Override
	protected CycleAlert make_finishedCycle() {
		// TODO Auto-generated method stub
		return this;
	}

	@Override
	public void endOfCycleAlert(String id) {
		System.out.println(new Date() + " : Agent Etat "+id+" a fini son cycle!");
		nbFinishedCycles++;

		System.out.println("nbFinished = "+nbFinishedCycles+ " :  size = "+agents.size() + " : nbAgentPC = "+ nbAgentsPerCycle);
		if(nbFinishedCycles == 	nbAgentsPerCycle){
			System.out.println("Run cycles!");
			nbFinishedCycles = 0;
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			this.run();


		}
	}

	@Override
	public void stop() {
		//	thread.interrupt();

	}

	@Override
	protected IGetThread make_threads() {
		// TODO Auto-generated method stub
		return this;
	}

	@Override
	public void getThreadsMap(Map<String, Thread> threadMap) {
		this.threads = threadMap;
		execService = Executors.newFixedThreadPool(threadMap.size());


	}

	@Override
	public void getAgents(List<Runnable> agents) {
		synchronized(this.agents){
			execService = Executors.newFixedThreadPool(agents.size() + 1);
			System.out.println("SIZE : "+this.agents.size()+" NEW SIZE : "+agents.size());
			if(this.agents.size() == 0){
				this.agents.clear();
				this.agents.addAll(agents);
				System.out.println("New Run Run Run");
				this.run();
			}
			else{
				this.agents.clear();
				this.agents.addAll(agents);
			}
		}

	}

}
