package generalStructure.impl;

import general.Launcher;
import generalStructure.interfaces.CycleAlert;
import generalStructure.interfaces.IStop;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import agents.interfaces.Callable;
import agents.interfaces.Do;
import agents.interfaces.IGetThread;

public class LauncherImpl extends Launcher implements Callable, CycleAlert, IGetThread, IStop{

	/**
	 * Temps en milisecondes entre deux cycles consecutifs d'un agent.
	 */
	public static int time_by_cycle = 3000;
	
	private int nbFinishedCycles = 0;
	private Map<String, Thread> threads = new HashMap<String,Thread>();
	private int nbAgentsTotal = 3;
	private ExecutorService execService = null;
	private Map<String,Runnable> agents = new HashMap<String,Runnable>();
	private int nbAgentsPerCycle = 0;
	
	boolean _launched = false;
	boolean _pause = true;
	boolean stop = false;

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
	
	/**
	 * Commence le tout premier cycle du premier agent.
	 */
	public void startCycle() {
		_launched = true;
		requires().start().run_start();
	}
	
	public void setPause() {
		_pause = !_pause;
	}
	
	public boolean getPause() {
		return _pause;
	}
	
	public boolean getStop() {
		return stop;
	}

	@Override
	public void run() {
		
		if (!_launched) {
			startCycle();
		} else if (!_pause) {
		
			System.out.println("Threads = "+Thread.activeCount());
			synchronized(agents){
				nbAgentsPerCycle = agents.size();
				System.out.println();
				System.out.println("-----------------------------------------------------------------------------");
				System.out.println("Nb agents par cycle = "+nbAgentsPerCycle);
				System.out.println("RUN!!!!!  "+ agents.size());
				if(!(execService == null)){
					
					synchronized (agents) {
						for(Runnable e: agents.values() )
							execService.execute(e);
					}
	
				}
			}
	
			System.out.println("End boucles");
			//	this.requires().lancer().doIt();
		}

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
		
		if(nbFinishedCycles == 	nbAgentsPerCycle && !stop){
			System.out.println("Run cycles!");
			nbFinishedCycles = 0;
			try {
				Thread.sleep(time_by_cycle);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			this.run();
		} else if(stop){
			synchronized (agents) {
				//execService.
				agents.remove(id);
				System.out.println("suppression--------------------------------"+agents.size());
				if(agents.size() == 0) {
					execService.shutdownNow();
					this.requires().stopProcessus().notifyStop();
					System.out.println(execService.isShutdown());
					System.out.println("Threads = "+Thread.activeCount()); 
				}
			}
		}
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
	public void setAgentsMap(Map<String,Runnable> agents) {
		synchronized(this.agents){
			
			if(execService != null)
				execService.shutdown();
 
			execService = Executors.newFixedThreadPool(agents.size() + 1);
			System.out.println("SIZE : "+this.agents.size()+" NEW SIZE : "+agents.size());
			
			if(this.agents.size() == 0 && !stop){
				this.agents.clear();
				this.agents.putAll(agents);
				this.run();
			}
			else if (!stop){
				this.agents.clear();
				this.agents.putAll(agents);
			}
		}

	}

	@Override
	public void stop() {
		stop = true;
		System.out.println("STOP!");
		
	}

	@Override
	protected IStop make_stopAllAgents() {
		return this;
	}

	@Override
	public void notifyStop() {
		this.stop();
	}

}
