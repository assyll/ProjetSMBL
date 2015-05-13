package interfaceGraphique;

import jsonAndGS.MyJsonGenerator;

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
}
