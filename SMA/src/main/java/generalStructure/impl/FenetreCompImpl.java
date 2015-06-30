package generalStructure.impl;

import java.util.Timer;
import java.util.TimerTask;

import org.graphstream.graph.Graph;

import general.FenetreComp;
import interfaceGraphique.Window;

public class FenetreCompImpl extends FenetreComp {
	
	private Window fenetre;
	
	public FenetreCompImpl() {
		fenetre = new Window();
		run();
	}
	
	public void run() {
		Timer timer = new Timer();
		
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				Graph graph = requires().updateGraph().getGraph();
				if (graph != null) {
					fenetre.setRightGraph(graph);
				}
			}
		}, LauncherImpl.time_by_cycle, LauncherImpl.time_by_cycle);
		
	}
	
}
