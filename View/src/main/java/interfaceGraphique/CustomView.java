package interfaceGraphique;

import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Collection;

import org.graphstream.graph.Graph;
import org.graphstream.ui.graphicGraph.GraphicElement;
import org.graphstream.ui.graphicGraph.GraphicGraph;
import org.graphstream.ui.graphicGraph.GraphicSprite;
import org.graphstream.ui.view.Camera;
import org.graphstream.ui.view.View;
import org.graphstream.ui.view.Viewer;
import org.graphstream.ui.view.util.MouseManager;
import org.graphstream.ui.view.util.ShortcutManager;

public class CustomView implements View {
	View view;
	
	public CustomView(Viewer viewer, Graph graph) {
		view = viewer.addDefaultView(false);
	}

	public View getView() {
		return view;
	}
	
	public String getId() {
		return view.getId();
	}

	public Camera getCamera() {
		return view.getCamera();
	}

	public GraphicElement findNodeOrSpriteAt(double x, double y) {
		return view.findNodeOrSpriteAt(x, y);
	}

	public Collection<GraphicElement> allNodesOrSpritesIn(double x1, double y1,
			double x2, double y2) {
		return view.allNodesOrSpritesIn(x1, y1, x2, y2);
	}

	public void display(GraphicGraph graph, boolean graphChanged) {
		view.display(graph, graphChanged);
		
	}

	public void close(GraphicGraph graph) {
		view.close(graph);
		
	}

	public void beginSelectionAt(double x1, double y1) {
		
	}

	public void selectionGrowsAt(double x, double y) {
		
	}

	public void endSelectionAt(double x2, double y2) {
		
	}

	public void freezeElement(GraphicElement element, boolean frozen) {
		view.freezeElement(element, frozen);
	}

	public void moveElementAtPx(GraphicElement element, double x, double y) {
		if(!(element instanceof GraphicSprite)){
			view.moveElementAtPx(element, x, y);
		}
	}

	public void setMouseManager(MouseManager manager) {
		view.setMouseManager(manager);
	}

	public void setShortcutManager(ShortcutManager manager) {
		view.setShortcutManager(manager);
	}

	public void requestFocus() {
		view.requestFocus();
	}

	public void addKeyListener(KeyListener l) {
		view.addKeyListener(l);
	}

	public void removeKeyListener(KeyListener l) {
		view.removeKeyListener(l);
	}

	public void addMouseListener(MouseListener l) {
		view.addMouseListener(l);
	}

	public void removeMouseListener(MouseListener l) {
		view.removeMouseListener(l);
	}

	public void addMouseMotionListener(MouseMotionListener l) {
		view.addMouseMotionListener(l);
	}

	public void removeMouseMotionListener(MouseMotionListener l) {
		view.removeMouseMotionListener(l);
	}

}
