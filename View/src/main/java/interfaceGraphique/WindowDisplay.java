package interfaceGraphique;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.DecimalFormat;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.MultiGraph;
import org.graphstream.ui.spriteManager.SpriteManager;
import org.graphstream.ui.view.View;
import org.graphstream.ui.view.Viewer;

@SuppressWarnings("serial")
public class WindowDisplay extends JFrame {

	public static final double ZOOM = -0.1;
	public static final double DEZOOM = 0.1;
	
	public static final String GRAPH_NAME = "graph";

	JFrame frame;

	JPanel panelFrame, panelGraph, panelOption, panelModif, panelZoom;

	JButton zoomAvant, zoomArr, zoomText, zoomCenter, display, addNode,
			suppNode, addEdge, suppEdge, structGraph;

	JTextField text;

	JScrollPane scrollGraph;

	View view;

	Viewer viewer;

	Double valZoom = 100.00;

	Graph graph;

	DecimalFormat df;
	
	SpriteManager spriteManager;
	
	CustomGraphRenderer cGraphRenderer;

	boolean isGraphLoaded, isAutoLayout;

	final int xWindow = 50, yWindow = 0, widthWindow = 1280,
			heightWindow = 700;

	public WindowDisplay(String name, View viewGraph, Viewer viewerGraph, Graph oldGraph) {
		cGraphRenderer = new CustomGraphRenderer();

		view = viewGraph;
		viewer = viewerGraph;
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

		// Initialisation du panneau option gauche
		panelOption = new JPanel(new FlowLayout());

		// Initialisation du panneau d'ajout de noeuds et de
		// transition gauche
		panelModif = new JPanel(new FlowLayout());

		// Initialisation et définition du panneau pour zoomer le graphe Json
		panelZoom = new JPanel(new FlowLayout());

		// Initialisation des bouttons de zoom
		zoomAvant = new JButton("<html><b>Zoom +</b></html>");
		zoomArr = new JButton("<html><b>Zoom -</b></html>");
		zoomText = new JButton("<html><b>%</b></html>");
		zoomCenter = new JButton("<html><b>Center</b></html>");

		// initialisation de la zone de texte pour le pourcentage de zoom
		text = new JTextField();
		text.setText(valZoom + " %");
		text.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent evt) {
				if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
					if (isGraphLoaded) {
						String s = text.getText();
						double pourcentage = 0, zoomAvant = 0, zoomArr = 0, total = 0;
						boolean isNumber = false;
						for (int i = 0; i < s.length(); i++) {
							if (!Character.isDigit(s.charAt(i))) {
								isNumber = false;
							} else {
								isNumber = true;
							}
						}

						if (!isNumber) {
							msgError("Veuillez rentrer un nombre dans le champ");
						} else {
							pourcentage = Integer.parseInt(s);
							if (pourcentage > 100) {
								zoomAvant = pourcentage - 100;
								total = 1 - (zoomAvant / 100);
								view.getCamera().setViewPercent(total);
								valZoom = view.getCamera().getViewPercent() * 100;
								text.setText(df.format(valZoom) + " %");
							} else if (pourcentage < 100) {
								zoomArr = 100 - pourcentage;
								total = 1 + (zoomArr / 100);
								view.getCamera().setViewPercent(total);
								valZoom = view.getCamera().getViewPercent() * 100;
								text.setText(df.format(valZoom) + " %");
							} else {
								view.getCamera().resetView();
							}
						}

					} else {
						view.getCamera().resetView();
					}
				}
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
		addNode = new JButton("Node +");
		suppNode = new JButton("Node -");
		addEdge = new JButton("Edge +");
		suppEdge = new JButton("Edge -");
		structGraph = new JButton("Manual");

		// Ajout des boutons dans le panneau
		panelModif.add(addNode);
		panelModif.add(suppNode);
		panelModif.add(addEdge);
		panelModif.add(suppEdge);
		panelModif.add(structGraph);

		// Définition du panneau de gauche
		panelOption.add(panelZoom);
		panelOption.add(panelModif);

		// Initialisation du panneau que contiendra le graph
		scrollGraph = new JScrollPane();
		scrollGraph.setViewportView(panelGraph);

		// Définition du panel de la fenêtre
		panelFrame.add(panelOption, BorderLayout.NORTH);
		panelFrame.add(scrollGraph, BorderLayout.CENTER);

		// Action lors du clic sur l'item "+" de la partie gauche
		zoomAvant.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				modifZoom(view, text, isGraphLoaded, ZOOM);
			}
		});

		// Action lors du clic sur l'item "-" de la partie gauche
		zoomArr.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				modifZoom(view, text, isGraphLoaded, DEZOOM);
			}
		});
		
		// Action lors du clic sur l'item "Node +" de la partie gauche
		addNode.addActionListener(new ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				AddNodeDialog addNodeJSon = new AddNodeDialog(frame, "Add Node");
				String s = addNodeJSon.getName();
				if (!addNodeJSon.getFerme()) {
					if (isGraphLoaded) {
						if (!s.equals("")) {
							Node n = graph.getNode(s);
							if (n == null) {
								GraphModifier.addNode(addNodeJSon, graph);
							} else {
								msgError("Nom déjà existant");
							}
						} else {
							msgError("Nom invalide car vide");
						}
					} else {
						if (!s.equals("")) {
							graph = new MultiGraph(GRAPH_NAME);
							initGraphPropertiesJson();
							initPanelGraphJson();
							GraphModifier.addNode(addNodeJSon, graph);
						} else {
							msgError("Nom invalide car vide");
						}
					}
				}
			}
		});

		// Action lors du clic sur l'item "Node -" de la
		// partie gauche
		suppNode.addActionListener(new ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				if (isGraphLoaded) {
					SuppNodeDialog suppNodeJSon = new SuppNodeDialog(frame,
							"Suppr Node", graph);
					if (!suppNodeJSon.getFerme()) {
						String s = suppNodeJSon.getName();
						Node n = graph.getNode(s);
						for (Edge edge : n.getEachEdge()) {
							System.out.println(edge.getId());
							spriteManager.removeSprite(edge.getId());
						}
						n = graph.removeNode(s);
						if (n != null) {
							msgAlert("Le noeud " + s + " a été supprimé.");
						}
					}
				}
			}
		});

		// Action lors du clic sur l'item "Edge +" de la partie gauche
		addEdge.addActionListener(new ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				if (isGraphLoaded) {
					AddEdgeDialog addEdgeJSon = new AddEdgeDialog(frame,
							"Add Edge", graph);
					String s = addEdgeJSon.getLabel();
					if (!addEdgeJSon.getFerme() && addEdgeJSon.getCheck()) {
						if (!s.equals("")) {
							Edge ed = graph.getEdge(s);
							if (ed == null) {
								try {
									GraphModifier.addEdge(addEdgeJSon,
											graph, spriteManager);
								} catch (NoSpecifiedNodeException e) {
									
								}
							} else {
								msgError("Nom déjà existant");
							}
						} else {
							msgError("Nom invalide car vide");
						}
					}
				}
			}
		});

		// Action lors du clic sur l'item "Edge -" de la
		// partie gauche
		suppEdge.addActionListener(new ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				if (isGraphLoaded) {
					SuppEdgeDialog suppEdgeJSon = new SuppEdgeDialog(frame,
							"Suppr Edge", graph);
					if (!suppEdgeJSon.getFerme()) {
						String s = suppEdgeJSon.getName();
						Edge e = graph.removeEdge(s);
						spriteManager.removeSprite(e.getId());
						if (e != null) {
							msgAlert("La transition " + s + " a été supprimé.");
						}
					}
				}
			}
		});

		// Action lors du clic sur l'item "Structurer / Déstructurer" de la
		// partie gauche
		structGraph.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (isGraphLoaded) {
					if (isAutoLayout) {
						structGraph.setText("Auto");
						viewer.disableAutoLayout();
						isAutoLayout = false;
					} else {
						structGraph.setText("Manual");
						cGraphRenderer.setTreeLayout(viewer.getGraphicGraph(), graph, viewer);
						isAutoLayout = true;
					}
				}
			}
		});

		// Action lors du clic sur l'item "Center" de la partie gauche
		zoomCenter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				view.getCamera().resetView();
				valZoom = view.getCamera().getViewPercent() * 100;
			}
		});
		
		initGraphPropertiesJson();
		initPanelGraphJson();
		// Définition de la fenêtre principale
		frame.add(panelFrame);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setResizable(true);
		frame.setBounds(xWindow, yWindow, widthWindow, heightWindow);
		frame.setVisible(true);

	}

	private void msgError(String s) {
		JOptionPane.showMessageDialog(this, s, "Error",
				JOptionPane.ERROR_MESSAGE);
	}

	private void msgAlert(String s) {
		JOptionPane.showMessageDialog(this, s, "Alert",
				JOptionPane.INFORMATION_MESSAGE);
	}

	public void modifZoom(View view, JTextField jTextField,
			boolean isGraphLoaded, double modif) {
		double zoom, valZoom;
		if (isGraphLoaded) {
			zoom = view.getCamera().getViewPercent();
			view.getCamera().setViewPercent(zoom + modif);
			valZoom = view.getCamera().getViewPercent() * 100;
			jTextField.setText(df.format(valZoom) + " %");
		}
	}
	
	public void initPanelGraphJson() {
		panelGraph.removeAll();
		panelGraph.setLayout(new BorderLayout());
		panelGraph.add((Component) view, BorderLayout.CENTER);
		scrollGraph.setViewportView(panelGraph);
	}
	
	public void initGraphPropertiesJson() {
		cGraphRenderer.setStyleGraphBasic(graph);
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

		Window.setListenerOnViewer(viewer, graph, text,
				isGraphLoaded);
		
	}

}
