package interfaceGraphique;

import jsonAndGS.MyJsonGenerator;

import org.graphstream.graph.Graph;

public class GraphRendererPerso {

	public static void SetRenderer() {
		// remplacement du renderer par défaut
		System.setProperty("org.graphstream.ui.renderer",
				"org.graphstream.ui.j2dviewer.J2DGraphRenderer");
	}

	public static void setStyleGraph(Graph graph) {
		graph.addAttribute("ui.quality");
		graph.addAttribute("ui.antialias");
		graph.addAttribute(
				"ui.stylesheet",
				"edge { fill-color: grey; }"
						+ "node." + MyJsonGenerator.FORMAT_NODE_SOURCE
						+ "{ shape: triangle; }" + "node."
						+ MyJsonGenerator.FORMAT_NODE_FINAL
						+ "{ fill-color: red;  }"
						+ "node." + MyJsonGenerator.FORMAT_NODE_SOURCE + MyJsonGenerator.FORMAT_NODE_FINAL + "{ fill-color: red; shape : triangle; }");
	}
}
