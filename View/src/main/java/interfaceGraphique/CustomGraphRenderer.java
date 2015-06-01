package interfaceGraphique;

import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

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

	public static final int GAP_SIDE = 10;
	public static final int GAP_Y_BETWEEN_NODE = 50;

	public void SetRenderer() {
		// remplacement du renderer par défaut
		System.setProperty("org.graphstream.ui.renderer",
				"org.graphstream.ui.j2dviewer.J2DGraphRenderer");
		ToolTipManager.sharedInstance().setInitialDelay(0);
	}

	public void setStyleGraphDefault(Graph... graphics) {
		for (Graph graph : graphics) {
			if (graph != null) {
				graph.removeAttribute("ui.stylesheet");
			}
		}
	}

	public void setStyleGraphDoubleCircle(Graph... graphics) {
		for (Graph graph : graphics) {
			if (graph != null) {
				graph.removeAttribute("ui.stylesheet");
				graph.addAttribute("ui.quality");
				graph.addAttribute("ui.antialias");
				graph.addAttribute(
						"ui.stylesheet",
						"edge { z-index: 0; text-alignment: center; text-offset: 0,10; fill-color: grey; }"
								+ "node { z-index: 3; text-alignment: at-right; size: 20px; fill-image: url(\"./src/main/resources/Node_Basic.png\"); fill-mode: image-scaled; }"
								+ NODE_SOURCE
								+ "{ size: 25px; fill-image: url(\"./src/main/resources/Node_Source.png\"); fill-mode: image-scaled; }"
								+ NODE_FINAL
								+ "{ size: 20px; fill-image: url(\"./src/main/resources/Node_Final.png\"); fill-mode: image-scaled; }"
								+ NODE_SOURCE_FINAL
								+ "{ size: 25px; fill-image: url(\"./src/main/resources/Node_Source_Final.png\"); fill-mode: image-scaled; }"
								+ "sprite { z-index: 2; shape: circle; fill-color: rgba(250,250,250,64); stroke-mode: plain; stroke-color: lightblue; }");
			}
		}
	}

	public void setStyleGraphAutomaton(Graph... graphics) {
		for (Graph graph : graphics) {
			if (graph != null) {
				graph.removeAttribute("ui.stylesheet");
				graph.addAttribute("ui.quality");
				graph.addAttribute("ui.antialias");
				graph.addAttribute(
						"ui.stylesheet",
						"edge { z-index: 0; text-alignment: center; text-offset: 0,10; fill-color: grey; }"
								+ "node { z-index: 3; text-alignment: at-right; size: 20px; fill-image: url(\"./src/main/resources/Node.png\"); fill-mode: image-scaled; }"
								+ NODE_SOURCE
								+ "{ size: 40px; fill-image: url(\"./src/main/resources/NodeSource.png\"); fill-mode: image-scaled; }"
								+ NODE_FINAL
								+ "{ size: 40px; fill-image: url(\"./src/main/resources/NodeFinal.png\"); fill-mode: image-scaled; }"
								+ NODE_SOURCE_FINAL
								+ "{ size: 40px; fill-image: url(\"./src/main/resources/NodeSourceFinal.png\"); fill-mode: image-scaled; }"
								+ "sprite { z-index: 2; shape: circle; fill-color: rgba(250,250,250,64); stroke-mode: plain; stroke-color: lightblue; }");
			}
		}
	}

	public void setStyleGraphBasic(Graph... graphics) {
		for (Graph graph : graphics) {
			if (graph != null) {
				graph.removeAttribute("ui.stylesheet");
				graph.addAttribute("ui.quality");
				graph.addAttribute("ui.antialias");
				graph.addAttribute(
						"ui.stylesheet",
						"edge { z-index: 0; text-alignment: center; text-offset: 0,10; fill-color: grey; }"
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
	}

	// TODO essayer de rapprocher les nodes connectées
	// Applique un placement sous forme d'arbre avec la(les) racine(s) en haut
	public void setTreeLayout(GraphicGraph gGraph, Graph graph, Viewer viewer) {
		int heightView = viewer.getDefaultView().getHeight();
		int widthView = viewer.getDefaultView().getWidth();
		float ratioXY = ((float) widthView) / ((float) heightView);
		int cptLevel = 1;
		int nbMaxNodeInLevel = 0;
		Node node;

		Set<NodeLeveled> nodesPerLevel = new LinkedHashSet<NodeLeveled>();
		List<Node> nodeInPlacement = new LinkedList<Node>();
		List<Node> nodeToBePlaced = new LinkedList<Node>();

		viewer.disableAutoLayout();

		// Récupère toutes les informations nécessaire pour organiser les nodes
		// par niveau
		for (Node gNode : gGraph.getEachNode()) {
			node = graph.getNode(gNode.getId());
			if (node.getAttribute(MyJsonGenerator.FORMAT_NODE_SOURCE).equals(
					true)
					|| node.getAttribute(MyJsonGenerator.FORMAT_NODE_SOURCE)
							.equals("true")) {
				nodeToBePlaced.addAll(getNexts(gNode, nodesPerLevel,
						nodeInPlacement));
				nodesPerLevel.add(new NodeLeveled(gNode, cptLevel));
			}
		}

		while (!nodeToBePlaced.isEmpty()) {
			cptLevel++;
			/*
			 * nbNodesPerLevel.add(nodeToBePlaced.size()); nbLevel =
			 * nbNodesPerLevel.size();
			 */
			nodeInPlacement.clear();
			nodeInPlacement.addAll(nodeToBePlaced);
			nodeToBePlaced.clear();
			for (Node gNode : nodeInPlacement) {
				nodesPerLevel.add(new NodeLeveled(gNode, cptLevel));
				nodeToBePlaced.addAll(getNexts(gNode, nodesPerLevel,
						nodeInPlacement));
			}
		}

		cptLevel--;

		for (int cpt = 1; cpt < cptLevel; cpt++) {
			int nbNodes = getNbNodeInLevel(cpt, nodesPerLevel);
			if (nbNodes > nbMaxNodeInLevel) {
				nbMaxNodeInLevel = nbNodes;
			}
		}

		// Calcul de la taille en x et en y de la fenetre
		heightView = (cptLevel * GAP_Y_BETWEEN_NODE) + 2 * GAP_SIDE;
		widthView = (int) (heightView * ratioXY);

		// Placement des nodes
		for (int indexLevel = 1; indexLevel <= cptLevel; indexLevel++) {
			nodeInPlacement = getNodePerLevel(indexLevel, nodesPerLevel);
			for (Node gNode : nodeInPlacement) {
				viewer.getDefaultView().moveElementAtPx((GraphicElement) gNode,
						getXNode(gNode, nodeInPlacement, widthView),
						getYNode(indexLevel, cptLevel, heightView));
			}
		}
		viewer.getDefaultView().getCamera().resetView();
	}

	// récupère les nodes du niveau supérieur au niveau de la node passée en
	// paramètre connectées à celle-ci
	public List<Node> getNexts(Node node, Set<NodeLeveled> nodesPlaced,
			List<Node> nodesBeingPlaced) {
		List<Node> targetNodes = new LinkedList<Node>();
		Node targetNode;

		for (Edge edge : node.getEachEdge()) {
			targetNode = edge.getTargetNode();
			if (isNodeYetToBePlaced(targetNode, nodesPlaced, nodesBeingPlaced)) {
				if (!targetNode.getId().equals(node.getId())) {
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
		int xNode;

		if (nbNodeInLevel == 1) {
			xNode = widthView / 2;
		} else {
			xNode = GAP_SIDE
					+ ((widthView - 2 * GAP_SIDE) / (nbNodeInLevel - 1))
					* nodeNumber;
		}
		return xNode;
	}

	// calcul le placement en y de la node par rapport à son niveau
	public int getYNode(int nodeLevel, int nbLevel, int heightView) {
		int yNode = ((heightView) / (nbLevel - 1)) * nodeLevel + GAP_SIDE;
		return yNode;
	}

	// Retourne la list des Node du level donné en paramètre
	public List<Node> getNodePerLevel(int level, Set<NodeLeveled> nodesPerLevel) {
		List<Node> listNodes = new LinkedList<Node>();

		for (NodeLeveled nodeLeveled : nodesPerLevel) {
			if (nodeLeveled.get_level() == level) {
				listNodes.add(nodeLeveled.get_node());
			}
		}
		return listNodes;
	}

	public int getNbNodeInLevel(int level, Set<NodeLeveled> nodePlaced) {
		int nbNode = 0;
		for (NodeLeveled nodeLeveled : nodePlaced) {
			if (nodeLeveled.get_level() == level) {
				nbNode++;
			}
		}
		return nbNode;
	}

	// TODO vérifié que ces méthodes sont bien inutiles du fait de l'utilisation
	// d'une linkedHashSet

	// vérifie si la node passée en paramètre est déjà dans la list des nodes
	// placées
	public boolean isNodeAlreadyPlaced(Node nodeToVerify,
			Set<NodeLeveled> nodesPlaced) {
		for (NodeLeveled nodeLeveled : nodesPlaced) {
			if (nodeLeveled.get_node().getId().equals(nodeToVerify.getId())) {
				return true;
			}
		}
		return false;
	}

	// vérifie si la node passée en paramètre est dans la list des nodes en
	// cours de placement
	public boolean isNodeBeingPlaced(Node nodeToVerify,
			List<Node> nodesBeingPlaced) {
		for (Node node : nodesBeingPlaced) {
			if (node.getId().equals(nodeToVerify.getId())) {
				return true;
			}
		}
		return false;
	}

	// vérifie si la node passée en paramètre est encore à placer
	public boolean isNodeYetToBePlaced(Node nodeToVerify,
			Set<NodeLeveled> nodesPlaced, List<Node> nodesBeingPlaced) {
		return (!isNodeAlreadyPlaced(nodeToVerify, nodesPlaced) && !isNodeBeingPlaced(
				nodeToVerify, nodesBeingPlaced));
	}
}
