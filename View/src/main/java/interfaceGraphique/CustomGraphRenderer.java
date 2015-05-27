package interfaceGraphique;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.swing.ToolTipManager;

import jsonAndGS.MyJsonGenerator;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.ui.graphicGraph.GraphicElement;
import org.graphstream.ui.graphicGraph.GraphicGraph;
import org.graphstream.ui.view.Viewer;

public class CustomGraphRenderer {

	public static final String NODE_SOURCE = "node."
			+ MyJsonGenerator.FORMAT_NODE_SOURCE;
	public static final String NODE_FINAL = "node."
			+ MyJsonGenerator.FORMAT_NODE_FINAL;
	public static final String NODE_SOURCE_FINAL = "node."
			+ MyJsonGenerator.FORMAT_NODE_SOURCE
			+ MyJsonGenerator.FORMAT_NODE_FINAL;
	public static final int GAP = 10;

	public void SetRenderer() {
		// remplacement du renderer par défaut
		System.setProperty("org.graphstream.ui.renderer",
				"org.graphstream.ui.j2dviewer.J2DGraphRenderer");
		ToolTipManager.sharedInstance().setInitialDelay(0);
	}

	public void setStyleGraphDoubleCircle(Graph... graphics) {
		for (Graph graph : graphics) {
			graph.removeAttribute("ui.stylesheet");
			graph.addAttribute("ui.quality");
			graph.addAttribute("ui.antialias");
			graph.addAttribute(
					"ui.stylesheet",
					"edge { z-index: 0; text-alignment: along; text-offset: 0,10; fill-color: grey; }"
							+ "node { z-index: 3; text-alignment: at-right; size: 20px; fill-image: url(\"./src/main/resources/Node_Basic.png\"); fill-mode: image-scaled; }"
							+ NODE_SOURCE
							+ "{ size: 20px; fill-image: url(\"./src/main/resources/Node_Source.png\"); fill-mode: image-scaled; }"
							+ NODE_FINAL
							+ "{ size: 20px; fill-image: url(\"./src/main/resources/Node_Final.png\"); fill-mode: image-scaled; }"
							+ NODE_SOURCE_FINAL
							+ "{ size: 20px; fill-image: url(\"./src/main/resources/Node_Source_Final.png\"); fill-mode: image-scaled; }"
							+ "sprite { z-index: 2; shape: circle; fill-color: rgba(250,250,250,64); stroke-mode: plain; stroke-color: lightblue; }");
		}
	}

	public void setStyleGraphRobot(Graph... graphics) {
		for (Graph graph : graphics) {
			graph.removeAttribute("ui.stylesheet");
			graph.addAttribute("ui.quality");
			graph.addAttribute("ui.antialias");
			graph.addAttribute(
					"ui.stylesheet",
					"edge { z-index: 0; text-alignment: along; text-offset: 0,10; fill-color: grey; }"
							+ "node { z-index: 3; text-alignment: at-right; size: 20px; fill-image: url(\"./src/main/resources/Node_Light-blue.png\"); fill-mode: image-scaled; }"
							+ NODE_SOURCE
							+ "{ size: 30px; fill-image: url(\"./src/main/resources/Node_Source_Light-blue.png\"); fill-mode: image-scaled; }"
							+ NODE_FINAL
							+ "{ size: 30px; fill-image: url(\"./src/main/resources/Node_Final_Red.png\"); fill-mode: image-scaled; }"
							+ NODE_SOURCE_FINAL
							+ "{ size: 30px; fill-image: url(\"./src/main/resources/Node_Source_Final_Red.png\"); fill-mode: image-scaled; }"
							+ "sprite { z-index: 2; shape: circle; fill-color: rgba(250,250,250,64); stroke-mode: plain; stroke-color: lightblue; }");
		}
	}

	public void setStyleGraphBasic(Graph... graphics) {
		for (Graph graph : graphics) {
			graph.removeAttribute("ui.stylesheet");
			graph.addAttribute("ui.quality");
			graph.addAttribute("ui.antialias");
			graph.addAttribute(
					"ui.stylesheet",
					"edge { z-index: 0; text-alignment: along; text-offset: 0,10; fill-color: grey; }"
							+ "node { z-index: 3; text-alignment: at-right;}"
							+ NODE_SOURCE
							+ "{ size: 13px; shape: triangle; }"
							+ NODE_FINAL
							+ "{ size: 13px; fill-color: rgb(150,50,50); }"
							+ NODE_SOURCE_FINAL
							+ "{ size: 13px; shape: triangle; fill-color: rgb(150,50,50); }"
							+ "sprite { z-index: 2; shape: circle; fill-color: rgba(250,250,250,64); stroke-mode: plain; stroke-color: lightblue; }");
		}
	}

	// Applique un placement sous forme d'arbre avec la(les) racine(s) en haut
	public void setTreeLayout(GraphicGraph gGraph, Graph graph, Viewer viewer) {
		int heightView = viewer.getDefaultView().getHeight();
		int widthView = viewer.getDefaultView().getHeight();
		int nbRacine = 0;
		int nbLevel = 0;
		List<Integer> nbNodesPerLevel = new ArrayList<Integer>();
		List<NodeLeveled> nodesPerLevel = new ArrayList<NodeLeveled>();
		List<Node> nodeInPlacement = new LinkedList<Node>();
		List<Node> nodeToBePlaced = new LinkedList<Node>();
		Node node;

		viewer.disableAutoLayout();
		for (Node gNode : gGraph.getEachNode()) {
			node = graph.getNode(gNode.getId());
			if (node.getAttribute(MyJsonGenerator.FORMAT_NODE_SOURCE).equals(
					true)) {
				nodeToBePlaced.addAll(getNexts(gNode, nodesPerLevel));
				nodesPerLevel.add(new NodeLeveled(gNode, 1));
				nbRacine++;
			}
		}
		nbNodesPerLevel.add(nbRacine);
		nbLevel = nbNodesPerLevel.size();

		while (!nodeToBePlaced.isEmpty()) {
			//System.out.println(nodeToBePlaced);
			nbNodesPerLevel.add(nodeToBePlaced.size());
			nbLevel = nbNodesPerLevel.size();
			//System.out.println(nbNodesPerLevel);
			nodeInPlacement.clear();
			nodeInPlacement.addAll(nodeToBePlaced);
			nodeToBePlaced.clear();
			for (Node gNode : nodeInPlacement) {
				nodesPerLevel.add(new NodeLeveled(gNode, nbLevel));
				System.out.println(getNexts(gNode, nodesPerLevel));
				nodeToBePlaced.addAll(getNexts(gNode, nodesPerLevel));
				/*System.out.println("node : " + gNode);
				System.out.println("placées : " + nodesPerLevel);
				System.out.println(nodeToBePlaced);*/
			}
		}

		for (int cptLevel = nbLevel; cptLevel > 0; cptLevel--) {
			nodeInPlacement = getNodePerLevel(nodesPerLevel, cptLevel);
			for (Node gNode : nodeInPlacement) {
				// System.out.println("node : " + gNode + " level : " + cptLevel
				// + " yNode : " + getYNode(cptLevel, nbLevel, heightView));
				viewer.getDefaultView().moveElementAtPx((GraphicElement) gNode,
						getXNode(gNode, nodeInPlacement, widthView),
						getYNode(cptLevel, nbLevel, heightView));
			}
		}

	}

	// récupère les nodes du niveau supérieur au niveau de la node passée en paramètre
	public List<Node> getNexts(Node node, List<NodeLeveled> nodesPlaced) {
		List<Node> targetNodes = new LinkedList<Node>();
		Node targetNode;

		for (Edge edge : node.getEachEdge()) {
			targetNode = edge.getTargetNode();
			if (!isNodeAlreadyPlaced(targetNode, nodesPlaced)) {
				if (!targetNode.getId().equals(node.getId())) {
					//System.out.println(targetNode);
					targetNodes.add(targetNode);
				}
			}
		}
		return targetNodes;
	}

	// calcul le placement en x de la node passée en paramètre
	public int getXNode(Node node, List<Node> nodeInLevel, int widthView) {
		int nbNodeInLevel = nodeInLevel.size();
		int nodeNumber = nodeInLevel.indexOf(node);
		int xNode = ((widthView) / nbNodeInLevel) * nodeNumber + GAP;
		return xNode;
	}

	// calcul le placement en y de la node par rapport à son niveau
	public int getYNode(int nodeLevel, int nbLevel, int heightView) {
		int yNode = ((heightView) / nbLevel) * nodeLevel + GAP;
		return yNode;
	}

	// Retourne la list des Node du lvl donné en paramètre
	public List<Node> getNodePerLevel(List<NodeLeveled> nodesPerLevel, int level) {
		List<Node> listNodes = new LinkedList<Node>();

		for (NodeLeveled nodeLeveled : nodesPerLevel) {
			// System.out.println("node : " + nodeLeveled.get_node() +
			// " level : " + nodeLeveled.get_level());
			if (nodeLeveled.get_level() == level) {
				listNodes.add(nodeLeveled.get_node());
			}
		}
		return listNodes;
	}

	// vérifie si la node passée en paramètre est déjà dans la list des nodes placées
	public boolean isNodeAlreadyPlaced(Node nodeToVerify,
			List<NodeLeveled> nodesPlaced) {
		for (NodeLeveled nodeLeveled : nodesPlaced) {
			if (nodeLeveled.get_node().getId().equals(nodeToVerify.getId())) {
				return true;
			}
		}
		return false;
	}
}
