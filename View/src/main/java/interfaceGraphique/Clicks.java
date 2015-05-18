package interfaceGraphique;

import java.util.Iterator;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.ui.view.Viewer;
import org.graphstream.ui.view.ViewerListener;
import org.graphstream.ui.view.ViewerPipe;

public class Clicks implements ViewerListener {
    protected boolean loop = true;
 
    public static void main(String args[]) {
        new Clicks();
    }
    public Clicks() {
        // We do as usual to display a graph. This
        // connect the graph outputs to the viewer.
        // The viewer is a sink of the graph.
        Graph graph = new SingleGraph("Clicks");
        Viewer viewer = graph.display();
        Node node1 = graph.addNode("a");
        node1.addAttribute("ui.label", "tonCul");
        node1.addAttribute("ui.class", "bidule");
        node1.addAttribute("attribut 1", "att 1");
        node1.addAttribute("attribut 2", "att 2");
        node1.addAttribute("attribut 3", "att 3");
        node1.addAttribute("attribut 4", "att 4");
        node1.addAttribute("attribut 5", "att 5");
 
        // The default action when closing the view is to quit
        // the program.
        viewer.setCloseFramePolicy(Viewer.CloseFramePolicy.HIDE_ONLY);
 
        // We connect back the viewer to the graph,
        // the graph becomes a sink for the viewer.
        // We also install us as a viewer listener to
        // intercept the graphic events.
        ViewerPipe fromViewer = viewer.newViewerPipe();
        fromViewer.addViewerListener(this);
        fromViewer.addSink(graph);
 
        // Then we need a loop to do our work and to wait for events.
        // In this loop we will need to call the
        // pump() method before each use of the graph to copy back events
        // that have already occurred in the viewer thread inside
        // our thread.
        for(Node node : graph.getEachNode()){
        	System.out.println(node.getArray("a"));
    		for(Iterator<String> it = node.getAttributeKeyIterator(); it.hasNext(); ){
    			String attKey = it.next();
    			System.out.println(node.getAttribute(attKey));
    		}
        }
 
        while(loop) {
            fromViewer.pump(); // or fromViewer.blockingPump(); in the nightly builds
 
            // here your simulation code.
 
            // You do not necessarily need to use a loop, this is only an example.
            // as long as you call pump() before using the graph. pump() is non
            // blocking.  If you only use the loop to look at event, use blockingPump()
            // to avoid 100% CPU usage. The blockingPump() method is only available from
            // the nightly builds.
        }
    }
 
    public void viewClosed(String id) {
        loop = false;
    }
 
    public void buttonPushed(String id) {
        System.out.println("Button pushed on node "+id);
    }
 
    public void buttonReleased(String id) {
        System.out.println("Button released on node "+id);
    }
}