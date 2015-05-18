package interfaceGraphique;

import jsonAndGS.MyJsonGenerator;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.MultiGraph;
import org.graphstream.ui.spriteManager.Sprite;
import org.graphstream.ui.spriteManager.SpriteManager;

public class GraphModifier {

	public static void setNodeClass(Graph graph) {
		//TODO modifer l'attribut boolean en string dans le addNode
		Boolean isSource, isFinal;
		for (Node node : graph.getEachNode()) {
			isSource = (node.getAttribute(MyJsonGenerator.FORMAT_NODE_SOURCE).equals(true));
			isFinal = (node.getAttribute(MyJsonGenerator.FORMAT_NODE_FINAL).equals(true));
			if (isSource && isFinal) {
				node.setAttribute("ui.class",
						MyJsonGenerator.FORMAT_NODE_SOURCE
								+ MyJsonGenerator.FORMAT_NODE_FINAL);
			} else if (isSource) {
				node.setAttribute("ui.class",
						MyJsonGenerator.FORMAT_NODE_SOURCE);
			} else if (isFinal) {
				node.addAttribute("ui.class", MyJsonGenerator.FORMAT_NODE_FINAL);
			}
		}
	}

	public static Graph GraphToGraph(Graph graph, String graphName) {
		Graph graphRes = new MultiGraph(graphName);
		// Génération du graphe par rapport au 1er graphe
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

	public static Graph addNode(NodeDialog n, Graph g) {
		int cpt = 1;

		Node node = g.addNode(n.getName());
		node.addAttribute("ui.label", n.getName());
		node.addAttribute(MyJsonGenerator.FORMAT_NODE_SOURCE, n.getRoot());
		node.addAttribute(MyJsonGenerator.FORMAT_NODE_FINAL, n.getFinal());

		if (n.getNbAtt() != 0) {
			for (String attribut : n.getAttributs()) {
				node.addAttribute(MyJsonGenerator.FORMAT_NODE_ATTRIBUT + cpt++,
						attribut);
			}
		}

		return g;
	}

	public static Graph addEdge(EdgeDialog e, Graph g)
			throws NoSpecifiedNodeException {
		int cpt = 1;

		if ((g.getNode(e.getSource()) == null)
				|| (g.getNode(e.getEnd()) == null)) {
			throw new NoSpecifiedNodeException(
					"La node source ou finale n'existe pas");
		} else {
			Edge edge = g
					.addEdge(e.getLabel(), e.getSource(), e.getEnd(), true);
			edge.addAttribute("ui.label", e.getAction());

			if (e.getNbAtt() != 0) {
				for (String attribut : e.getAttributs()) {
					edge.addAttribute(MyJsonGenerator.FORMAT_EDGE_ATTRIBUT
							+ cpt++, attribut);
				}
			}

			return g;
		}
	}

	public static void generateSprite(Graph graph, SpriteManager spriteManager) {
		for (Edge edge : graph.getEachEdge()) {
			Sprite sprite = spriteManager.addSprite(edge.getId());
			sprite.attachToEdge(edge.getId());
			sprite.setPosition(0.5, 0, 0);
		}
	}
}
