package interfaceGraphique;

import java.awt.Graphics;
import java.awt.Shape;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Collection;

import javax.swing.text.BadLocationException;
import javax.swing.text.Element;
import javax.swing.text.Position.Bias;

import org.graphstream.ui.graphicGraph.GraphicElement;
import org.graphstream.ui.graphicGraph.GraphicGraph;
import org.graphstream.ui.view.Camera;
import org.graphstream.ui.view.View;
import org.graphstream.ui.view.util.MouseManager;
import org.graphstream.ui.view.util.ShortcutManager;

public class CustomViewer extends javax.swing.text.View implements View {

	public CustomViewer(Element elem) {
		super(elem);
		// TODO Auto-generated constructor stub
	}

	public String getId() {
		// TODO Auto-generated method stub
		return null;
	}

	public Camera getCamera() {
		// TODO Auto-generated method stub
		return null;
	}

	public GraphicElement findNodeOrSpriteAt(double x, double y) {
		// TODO Auto-generated method stub
		return null;
	}

	public Collection<GraphicElement> allNodesOrSpritesIn(double x1, double y1,
			double x2, double y2) {
		// TODO Auto-generated method stub
		return null;
	}

	public void display(GraphicGraph graph, boolean graphChanged) {
		// TODO Auto-generated method stub
		
	}

	public void close(GraphicGraph graph) {
		// TODO Auto-generated method stub
		
	}

	public void beginSelectionAt(double x1, double y1) {
		// TODO Auto-generated method stub
		
	}

	public void selectionGrowsAt(double x, double y) {
		// TODO Auto-generated method stub
		
	}

	public void endSelectionAt(double x2, double y2) {
		// TODO Auto-generated method stub
		
	}

	public void freezeElement(GraphicElement element, boolean frozen) {
		// TODO Auto-generated method stub
		
	}

	public void moveElementAtPx(GraphicElement element, double x, double y) {
		// TODO Auto-generated method stub
		
	}

	public void setMouseManager(MouseManager manager) {
		// TODO Auto-generated method stub
		
	}

	public void setShortcutManager(ShortcutManager manager) {
		// TODO Auto-generated method stub
		
	}

	public void requestFocus() {
		// TODO Auto-generated method stub
		
	}

	public void addKeyListener(KeyListener l) {
		// TODO Auto-generated method stub
		
	}

	public void removeKeyListener(KeyListener l) {
		// TODO Auto-generated method stub
		
	}

	public void addMouseListener(MouseListener l) {
		// TODO Auto-generated method stub
		
	}

	public void removeMouseListener(MouseListener l) {
		// TODO Auto-generated method stub
		
	}

	public void addMouseMotionListener(MouseMotionListener l) {
		// TODO Auto-generated method stub
		
	}

	public void removeMouseMotionListener(MouseMotionListener l) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public float getPreferredSpan(int axis) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Shape modelToView(int pos, Shape a, Bias b)
			throws BadLocationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void paint(Graphics g, Shape allocation) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int viewToModel(float x, float y, Shape a, Bias[] biasReturn) {
		// TODO Auto-generated method stub
		return 0;
	}

}
