package interfaceGraphique;

import javax.swing.ToolTipManager;

import jsonAndGS.MyJsonGenerator;

import org.graphstream.graph.Graph;

public class CustomGraphRenderer {

	public static final String NODE_SOURCE = "node."
			+ MyJsonGenerator.FORMAT_NODE_SOURCE;
	public static final String NODE_FINAL = "node."
			+ MyJsonGenerator.FORMAT_NODE_FINAL;
	public static final String NODE_SOURCE_FINAL = "node."
			+ MyJsonGenerator.FORMAT_NODE_SOURCE
			+ MyJsonGenerator.FORMAT_NODE_FINAL;

	public static void SetRenderer() {
		// remplacement du renderer par d�faut
		System.setProperty("org.graphstream.ui.renderer",
				"org.graphstream.ui.j2dviewer.J2DGraphRenderer");
		ToolTipManager.sharedInstance().setInitialDelay(0);
	}

	public static void setStyleGraph(Graph graph) {
		graph.addAttribute("ui.quality");
		graph.addAttribute("ui.antialias");
		graph.addAttribute(
				"ui.stylesheet",
				"edge { z-index: 0; text-alignment: along; text-offset: 0,10; fill-color: grey; }"
						+ "node { z-index: 3; text-alignment: at-right; fill-color: rgb(50,150,100); }"
						+ NODE_SOURCE
						+ "{ size: 13px; shape: triangle; }"
						+ NODE_FINAL
						+ "{ size: 13px; fill-color: rgb(150,50,50); }"
						+ NODE_SOURCE_FINAL
						+ "{ size: 13px; shape: triangle; fill-color: rgb(150,50,50); }"
						+ "sprite { z-index: 2; shape: circle; fill-color: rgba(250,250,250,64); stroke-mode: plain; stroke-color: lightblue; }");
	}
}