package generalStructure.impl;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Timer;
import java.util.TimerTask;

import org.graphstream.graph.Graph;

import general.FenetreComp;
import interfaceGraphique.Window;

public class FenetreCompImpl extends FenetreComp {
	
	private Window fenetre;
	
	public FenetreCompImpl() {
		fenetre = new Window();
		
		fenetre.setListenerPlayPauseButton(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				((LauncherImpl) requires().callable()).setPause();
				requires().callable().run();
			}
		});
		
		fenetre.setListenerStopButton(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				requires().callable().stop();
			}
		});
		
		run();
	}
	
	public void run() {
		Timer timer = new Timer();
		
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				if (!((LauncherImpl) requires().callable()).getPause() &&
						!((LauncherImpl) requires().callable()).getStop()) {
					Graph graph = requires().updateGraph().getGraph();
					if (graph != null) {
						fenetre.setRightGraph(graph);
					}
				}
			}
		}, LauncherImpl.time_by_cycle, LauncherImpl.time_by_cycle);
		
	}
	
}
