package interfaceGraphique;

import java.util.ArrayList;
import java.util.List;

import jsonAndGS.MyJsonGenerator;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.MultiGraph;
import org.graphstream.ui.spriteManager.Sprite;
import org.graphstream.ui.spriteManager.SpriteManager;

public class GraphModifier {

	public static void setNodesClass(Graph graph) {
		for (Node node : graph.getEachNode()) {
			setNodeClass(graph, node);
		}
	}

	public static void setNodeClass(Graph graph, Node node) {
		Boolean isSource, isFinal;
		isSource = (node.getAttribute(MyJsonGenerator.FORMAT_NODE_SOURCE)
				.equals(true));
		isFinal = (node.getAttribute(MyJsonGenerator.FORMAT_NODE_FINAL)
				.equals(true));
		if (isSource && isFinal) {
			node.setAttribute("ui.class", MyJsonGenerator.FORMAT_NODE_SOURCE
					+ MyJsonGenerator.FORMAT_NODE_FINAL);
		} else if (isSource) {
			node.setAttribute("ui.class", MyJsonGenerator.FORMAT_NODE_SOURCE);
		} else if (isFinal) {
			node.setAttribute("ui.class", MyJsonGenerator.FORMAT_NODE_FINAL);
		} else {
			node.removeAttribute("ui.class");
		}
	}

	public static Graph GraphToGraph(Graph graph, String graphName) {
		Graph graphRes = new MultiGraph(graphName);
		// G�n�ration du graphe par rapport au 1er graphe
		for (Node n : graph.getEachNode()) {
			Node node = graphRes.addNode(n.getId());
			for (String attributeKey : n.getAttributeKeySet()) {
				node.addAttribute(attributeKey, n.getAttribute(attributeKey));
			}
		}

		for (Edge ed : graph.getEachEdge()) {
			Edge edge = graphRes.addEdge(ed.getId(),
					ed.getSourceNode().getId(), ed.getTargetNode().getId(),
					true);
			for (String attributeKey : ed.getAttributeKeySet()) {
				edge.addAttribute(attributeKey, ed.getAttribute(attributeKey));
			}
		}

		return graphRes;
	}

	public static Graph addNode(AddNodeDialog n, Graph g) {
		String attKey, attValue;

		Node node = g.addNode(n.getName());
		node.addAttribute("ui.label", n.getName());
		node.addAttribute(MyJsonGenerator.FORMAT_NODE_SOURCE, n.getRoot());
		node.addAttribute(MyJsonGenerator.FORMAT_NODE_FINAL, n.getFinal());

		if (n.getNbAtt() != 0) {
			for (String[] attribut : n.getAttributs()) {
				attKey = attribut[0];
				attValue = attribut[1];
				if (!attKey.equals("")) {
					node.addAttribute(attKey, attValue);
				}
			}
		}

		setNodeClass(g, node);

		return g;
	}

	public static Graph addEdge(AddEdgeDialog e, Graph g,
			SpriteManager spriteManager) throws NoSpecifiedNodeException {
		String attKey, attValue;

		if ((g.getNode(e.getSource()) == null)
				|| (g.getNode(e.getEnd()) == null)) {
			throw new NoSpecifiedNodeException(
					"La node source ou finale n'existe pas");
		} else {
			Edge edge = g
					.addEdge(e.getLabel(), e.getSource(), e.getEnd(), true);
			edge.addAttribute("ui.label", e.getAction());

			if (e.getNbAtt() != 0) {
				for (String[] attribut : e.getAttributs()) {
					attKey = attribut[0];
					attValue = attribut[1];
					if (!attKey.equals("")) {
						edge.addAttribute(attKey, attValue);
					}
				}
			}

			generateSprite(spriteManager, edge);

			return g;
		}
	}

	public static Graph changeNode(ChangeNodeDialog cND, Graph graph,
			String nodeId) {
		String attKey, attValue;
		Node oldNode = graph.getNode(nodeId);

		oldNode.clearAttributes();
		oldNode.setAttribute("ui.label", ChangeNodeDialog.getNameNode());
		oldNode.setAttribute(MyJsonGenerator.FORMAT_NODE_SOURCE, cND.getRoot());
		oldNode.setAttribute(MyJsonGenerator.FORMAT_NODE_FINAL, cND.getFinal());

		if (cND.getNbAtt() != 0) {
			for (String[] attribut : cND.getAttributs()) {
				attKey = attribut[0];
				attValue = attribut[1];
				if (!attKey.equals("")) {
					oldNode.setAttribute(attKey, attValue);
				}
			}
		}

		setNodeClass(graph, oldNode);

		return graph;
	}

	public static Graph changeEdge(ChangeEdgeDialog cED, Graph graph,
			String edgeId, String currentEdgeDisplay) {
		String attKey, attValue;
		Edge edge = graph.getEdge(edgeId);
		List<String> attToRemove = new ArrayList<String>();

		for (String attributeKey : edge.getAttributeKeySet()) {
			if (!attributeKey.startsWith("ui.sprite")) {
				attToRemove.add(attributeKey);
			}
		}

		for (String attributeKey : attToRemove) {
			edge.removeAttribute(attributeKey);
		}

		edge.setAttribute(MyJsonGenerator.FORMAT_EDGE_ACTION,
				ChangeEdgeDialog.getAction());

		if (cED.getNbAtt() != 0) {
			for (String[] attribut : cED.getAttributs()) {
				attKey = attribut[0];
				attValue = attribut[1];
				if (!attKey.equals("")) {
					edge.setAttribute(attKey, attValue);
				}
			}
		}
		
		setUILabelEdge(currentEdgeDisplay, edge);
		
		return graph;
	}

	public static void setUILabelEdge(String displayToApply, Edge edge) {
		String attValue;
		
		if (displayToApply.equals("Label")) {
			edge.setAttribute("ui.label", edge.getId());
		} else {
			attValue = edge.getAttribute(displayToApply);
			if (attValue != null) {
				edge.setAttribute("ui.label", attValue);
			}
		}
	}

	public static void generateSprites(Graph graph, SpriteManager spriteManager) {
		for (Edge edge : graph.getEachEdge()) {
			generateSprite(spriteManager, edge);
		}
	}

	public static void generateSprite(SpriteManager spriteManager, Edge edge) {
		Sprite sprite = spriteManager.addSprite(edge.getId());
		sprite.attachToEdge(edge.getId());
		sprite.setPosition(0.5, 0, 0);
	}
}
