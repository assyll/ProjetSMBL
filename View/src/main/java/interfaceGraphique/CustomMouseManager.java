package interfaceGraphique;

import java.awt.event.MouseEvent;

import javax.swing.JComponent;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Element;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.ui.graphicGraph.GraphicGraph;
import org.graphstream.ui.graphicGraph.GraphicSprite;
import org.graphstream.ui.view.View;
import org.graphstream.ui.view.Viewer;
import org.graphstream.ui.view.util.MouseManager;

public class CustomMouseManager implements MouseManager {
	
	public static final double _tolerance = 10;
	Double _previousEvtX = null, _previousEvtY = null, _actualEvtX = null, _actualEvtY = null;
	
	Graph _graph;
	Viewer _viewer;
	View _view;
	
	public CustomMouseManager(Graph graph, Viewer viewer){
		_graph = graph;
		_viewer = viewer;
		_view = viewer.getDefaultView();
	}

	public void mouseClicked(MouseEvent arg0) {
		System.out.println("a");
		
	}

	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void mouseReleased(MouseEvent arg0) {
		_previousEvtX = null;
		_previousEvtY = null;
	}

		public void mouseDragged(MouseEvent e) {
			Element elem = _view.findNodeOrSpriteAt(e.getX(),
					e.getY());
			if (elem == null) {
				if (_previousEvtX == null && _previousEvtY == null) {
					_previousEvtX = (double) e.getX();
					_previousEvtY = (double) e.getY();
				} else {
					_previousEvtX = _actualEvtX;
					_previousEvtY = _actualEvtY;
				}
				_actualEvtX = (double) e.getX();
				_actualEvtY = (double) e.getY();

				double xCenter = _view.getCamera().getViewCenter().x;
				double yCenter = _view.getCamera().getViewCenter().y;
				double posZ = _view.getCamera().getViewCenter().z;

				_view.getCamera().setViewCenter(
						(xCenter + ((_actualEvtX - _previousEvtX) * (-1)) / 100),
						(yCenter + (_actualEvtY - _previousEvtY) / 100), posZ);
			}
		}

	public void mouseMoved(MouseEvent e) {
		String s = "<html>";
		Element elem = findNodeOrSpriteAtWithTolerance(e, _view);
		if (elem != null)
			System.out.println(elem);
		if (elem instanceof Node) {
			String idNode = elem.getId();
			Node node = _graph.getNode(idNode);
			for (String attKey : node.getAttributeKeySet()) {
				s += attKey + " : " + node.getAttribute(attKey)
						+ "<br/>";
			}
			s += "</html>";
			((JComponent) _view).setToolTipText(s);
		} else if (elem instanceof GraphicSprite) {
			String idSprite = elem.getId();
			Edge edge = _graph.getEdge(idSprite);
			for (String attKey : edge.getAttributeKeySet()) {
				s += attKey + " : " + edge.getAttribute(attKey)
						+ "<br/>";
			}
			s += "</html>";
			_viewer.getDefaultView().setToolTipText(s);
		} else {
			_viewer.getDefaultView().setToolTipText(null);
		}
		
	}
	
	public Element findNodeOrSpriteAtWithTolerance(MouseEvent e, View view) {
		Element elem = null;
		for (double yEvt = e.getY() - _tolerance; yEvt < e.getY() + _tolerance; yEvt += _tolerance / 10) {
			for (double xEvt = e.getX() - _tolerance; xEvt < e.getX()
					+ _tolerance; xEvt += _tolerance / 10) {
				elem = view.findNodeOrSpriteAt(xEvt, yEvt);
				if (elem != null) {
					return elem;
				}
			}
		}
		return null;
	}

	public void init(GraphicGraph graph, View view) {
		// TODO Auto-generated method stub
	}

	public void release() {
		// TODO Auto-generated method stub
		
	}

}
