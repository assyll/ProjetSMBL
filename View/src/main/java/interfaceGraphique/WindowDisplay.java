package interfaceGraphique;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import org.graphstream.graph.Graph;
import org.graphstream.ui.spriteManager.SpriteManager;
import org.graphstream.ui.view.View;
import org.graphstream.ui.view.Viewer;

@SuppressWarnings("serial")
public class WindowDisplay extends JFrame {

	public static final double ZOOM = -0.1;
	public static final double DEZOOM = 0.1;

	private JFrame frame;

	private JPanel panelFrame, panelGraph, panelOption, panelModif, panelZoom;

	private JButton zoomAvant, zoomArr, zoomCenter, addNode, deleteNode, addEdge,
			deleteEdge, structGraph, treeLayout;

	private JTextField text;

	private JScrollPane scrollGraph;

	private String graphName;

	private View view;

	private Viewer viewer;

	private int valZoom = 100;

	private Graph graph;

	private SpriteManager spriteManager;

	private boolean isGraphLoaded, isAutoLayout;

	private final int xWindow = 50, yWindow = 0, widthWindow = 1280,
			heightWindow = 700;

	public WindowDisplay(String name, Viewer viewerGraph, Graph oldGraph) {
		graphName = name;
		viewer = viewerGraph;
		view = viewer.getDefaultView();
		graph = oldGraph;

		// Initialisation de la fenêtre principale
		frame = new JFrame(name);

		// Initialisation et définition du 2ème panneau
		panelFrame = new JPanel(new BorderLayout());
		panelFrame.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createTitledBorder("Graph"),
				BorderFactory.createEmptyBorder(1, 1, 1, 1)));
		panelFrame.setLayout(new BorderLayout());

		// Initialisation du panneau qui contiendra le graph
		panelGraph = new JPanel();

		// Initialisation du panneau option
		panelOption = new JPanel(new FlowLayout());

		// Initialisation du panneau d'ajout de noeuds et de
		// transition
		panelModif = new JPanel(new FlowLayout());

		// Initialisation et définition du panneau pour zoomer le graphe
		panelZoom = new JPanel(new FlowLayout());

		// Initialisation des bouttons de zoom
		ImageIcon zoomIcon = new ImageIcon(
				"./src/main/resources/buttonsIcons/zoom+.png", "zoom +");
		ImageIcon dezoomIcon = new ImageIcon(
				"./src/main/resources/buttonsIcons/zoom-.png", "zoom -");
		ImageIcon centerIcon = new ImageIcon(
				"./src/main/resources/buttonsIcons/center.png", "center");

		zoomAvant = new JButton(zoomIcon);
		zoomAvant.setToolTipText(Window.ZOOM_TT);
		zoomAvant.setPreferredSize(Window.buttonsSize);
		zoomArr = new JButton(dezoomIcon);
		zoomArr.setToolTipText(Window.DEZOOM_TT);
		zoomArr.setPreferredSize(Window.buttonsSize);
		zoomCenter = new JButton(centerIcon);
		zoomCenter.setToolTipText(Window.CENTER_TT);
		zoomCenter.setPreferredSize(Window.buttonsSize);

		// initialisation de la zone de texte pour le pourcentage de zoom
		text = new JTextField();
		text.setText(valZoom + " %");
		text.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent evt) {
				Window.enterNewZoomValue(evt, isGraphLoaded, text, view);
			}

			public void keyReleased(KeyEvent evt) {
			}

			public void keyTyped(KeyEvent evt) {
			}

		});

		// Définition du panneau d'objet
		panelZoom.add(zoomAvant);
		panelZoom.add(zoomArr);
		panelZoom.add(text);
		panelZoom.add(zoomCenter);

		// Initialisation des boutons d'option
		ImageIcon addNodeIcon = new ImageIcon(
				"./src/main/resources/buttonsIcons/node+.png", "node +");
		ImageIcon deleteNodeIcon = new ImageIcon(
				"./src/main/resources/buttonsIcons/node-.png", "node -");
		ImageIcon addEdgeIcon = new ImageIcon(
				"./src/main/resources/buttonsIcons/edge+.png", "edge +");
		ImageIcon deleteEdgeIcon = new ImageIcon(
				"./src/main/resources/buttonsIcons/edge-.png", "edge -");
		ImageIcon treeLayoutIcon = new ImageIcon(
				"./src/main/resources/buttonsIcons/treeLayout.png",
				"tree layout");
		ImageIcon autoLayoutOnIcon = new ImageIcon(
				"./src/main/resources/buttonsIcons/autoLayoutOn.png",
				"automatic layout on");

		addNode = new JButton(addNodeIcon);
		addNode.setToolTipText(Window.ADD_NODE_TT);
		addNode.setPreferredSize(Window.buttonsSize);
		deleteNode = new JButton(deleteNodeIcon);
		deleteNode.setToolTipText(Window.DELETE_NODE_TT);
		deleteNode.setPreferredSize(Window.buttonsSize);
		addEdge = new JButton(addEdgeIcon);
		addEdge.setToolTipText(Window.ADD_EDGE_TT);
		addEdge.setPreferredSize(Window.buttonsSize);
		deleteEdge = new JButton(deleteEdgeIcon);
		deleteEdge.setToolTipText(Window.DELETE_EDGE_TT);
		deleteEdge.setPreferredSize(Window.buttonsSize);
		structGraph = new JButton(autoLayoutOnIcon);
		structGraph.setToolTipText(Window.AUTO_LAYOUT_ENABLED_TT);
		structGraph.setPreferredSize(Window.buttonsSize);
		treeLayout = new JButton(treeLayoutIcon);
		treeLayout.setToolTipText(Window.TREE_LAYOUT_TT);
		treeLayout.setPreferredSize(Window.buttonsSize);


		// Ajout des boutons dans le panneau
		panelModif.add(addNode);
		panelModif.add(deleteNode);
		panelModif.add(addEdge);
		panelModif.add(deleteEdge);
		panelModif.add(structGraph);
		panelModif.add(treeLayout);

		// Définition du panneau de gauche
		panelOption.add(panelZoom);
		panelOption.add(panelModif);

		// Initialisation du panneau que contiendra le graph
		scrollGraph = new JScrollPane();
		scrollGraph.setViewportView(panelGraph);

		// Définition du panel de la fenêtre
		panelFrame.add(panelOption, BorderLayout.NORTH);
		panelFrame.add(scrollGraph, BorderLayout.CENTER);

		// Action lors du clic sur l'item "+"
		zoomAvant.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Window.modifZoom(view, text, isGraphLoaded, ZOOM);
			}
		});

		// Action lors du clic sur l'item "-"
		zoomArr.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Window.modifZoom(view, text, isGraphLoaded, DEZOOM);
			}
		});

		// Action lors du clic sur l'item "Node +"
		addNode.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Window.addNode(isGraphLoaded, graph, graphName);
			}
		});

		// Action lors du clic sur l'item "Node -"
		deleteNode.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Window.deleteNode(isGraphLoaded, graph, spriteManager);
			}
		});

		// Action lors du clic sur l'item "Edge +"
		addEdge.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Window.addEdge(isGraphLoaded, graph, spriteManager);
			}
		});

		// Action lors du clic sur l'item "Edge -"
		deleteEdge.addActionListener(new ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				Window.deleteEdge(isGraphLoaded, graph, spriteManager);
			}
		});

		// Action lors du clic sur l'item "Automatic Layout"
		structGraph.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				autoLayout(isGraphLoaded, isAutoLayout, graphName, viewer,
						structGraph);
			}
		});
		
		// Action lors du clic sur l'item "Tree Layout"
				treeLayout.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if (isGraphLoaded) {
							CustomGraphRenderer.setTreeLayout(graph, viewer);
							Window.turnAutoLayoutButtonOff(structGraph);
							isAutoLayout = false;
						}
					}
				});

		// Action lors du clic sur l'item "Center"
		zoomCenter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				view.getCamera().resetView();
				valZoom = (int) (view.getCamera().getViewPercent() * 100);
			}
		});

		initGraphProperties();
		initPanelGraph();
		// Définition de la fenêtre principale
		frame.add(panelFrame);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setResizable(true);
		frame.setBounds(xWindow, yWindow, widthWindow, heightWindow);
		frame.setVisible(true);

	}

	public void initPanelGraph() {
		panelGraph.removeAll();
		panelGraph.setLayout(new BorderLayout());
		panelGraph.add((Component) view, BorderLayout.CENTER);
		scrollGraph.setViewportView(panelGraph);
	}

	public void initGraphProperties() {
		CustomGraphRenderer.setStyleGraphBasic(graph);
		GraphModifier.setNodesClass(graph);
		spriteManager = new SpriteManager(graph);
		GraphModifier.generateSprites(graph, spriteManager);

		viewer = new Viewer(graph,
				Viewer.ThreadingModel.GRAPH_IN_ANOTHER_THREAD);
		isGraphLoaded = true;

		viewer.enableAutoLayout();
		isAutoLayout = true;
		view = viewer.addDefaultView(false);

		// suppression du comportement par defaut du MouseListener de la view
		view.setMouseManager(new CustomMouseManager());

		Window.setListenerOnViewer(viewer, graph, text, isGraphLoaded);

	}

	public void autoLayout(Boolean isGraphLoaded, Boolean isAutoLayout,
			String graphName, Viewer viewer, JButton structGraph) {
		if (isGraphLoaded) {
			if (isAutoLayout) {
				Window.turnAutoLayoutButtonOff(structGraph);
				viewer.disableAutoLayout();
				isAutoLayout = false;
			} else {
				Window.turnAutoLayoutButtonOn(structGraph);
				viewer.enableAutoLayout();
				isAutoLayout = true;
			}
		}
	}
}
