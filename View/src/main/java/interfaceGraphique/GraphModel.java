package interfaceGraphique;

import jsonAndGS.MyJsonGenerator;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

public class GraphModel {

	public static void setNodeClass(Graph graph) {
		Boolean isSource, isFinal;
		for (Node node : graph.getEachNode()) {
			isSource = node.getAttribute(MyJsonGenerator.FORMAT_NODE_SOURCE)
					.equals("true");
			isFinal = node.getAttribute(MyJsonGenerator.FORMAT_NODE_FINAL)
					.equals("true");
			if (isSource && isFinal) {
				node.setAttribute("ui.class",
						MyJsonGenerator.FORMAT_NODE_SOURCE,
						MyJsonGenerator.FORMAT_NODE_FINAL);
			} else if (isSource) {
				node.setAttribute("ui.class",
						MyJsonGenerator.FORMAT_NODE_SOURCE);
			} else if (isFinal) {
				node.addAttribute("ui.class", MyJsonGenerator.FORMAT_NODE_FINAL);
			}
		}
	}

	public static void GraphToGraph(Graph graph, Graph graphRes) {
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
	}
}
