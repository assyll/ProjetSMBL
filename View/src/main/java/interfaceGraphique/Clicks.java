package interfaceGraphique;

import org.graphstream.ui.view.ViewerListener;
import org.graphstream.ui.view.ViewerPipe;

public class Clicks implements ViewerListener {
	protected boolean loop = true;

	public Clicks(ViewerPipe fromViewer) {

		// We connect back the viewer to the graph,
		// the graph becomes a sink for the viewer.
		// We also install us as a viewer listener to
		// intercept the graphic events.
		

		// Then we need a loop to do our work and to wait for events.
		// In this loop we will need to call the
		// pump() method before each use of the graph to copy back events
		// that have already occurred in the viewer thread inside
		// our thread.

		while (loop) {
			try {
				fromViewer.blockingPump();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} // or fromViewer.blockingPump(); in the nightly builds

			// here your simulation code.

			// You do not necessarily need to use a loop, this is only an
			// example.
			// as long as you call pump() before using the graph. pump() is non
			// blocking. If you only use the loop to look at event, use
			// blockingPump()
			// to avoid 100% CPU usage. The blockingPump() method is only
			// available from
			// the nightly builds.
		}
	}

	public void viewClosed(String id) {
		loop = false;
	}

	public void buttonPushed(String id) {
		System.out.println("Button pushed on node " + id);
	}

	public void buttonReleased(String id) {
		System.out.println("Button released on node " + id);

	}
}