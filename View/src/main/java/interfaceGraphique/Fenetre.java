package interfaceGraphique;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.io.File;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextField;

import jsonAndGS.FileFormatException;
import jsonAndGS.JsonToGS;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Element;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.MultiGraph;
import org.graphstream.ui.graphicGraph.GraphicSprite;
import org.graphstream.ui.spriteManager.Sprite;
import org.graphstream.ui.spriteManager.SpriteManager;
import org.graphstream.ui.view.View;
import org.graphstream.ui.view.Viewer;

import com.fasterxml.jackson.core.JsonParseException;

@SuppressWarnings("serial")
public class Fenetre extends JFrame {

	public static final String NO_FILE_SELECTED = "Veuillez d'abord sélectionner un fichier à importer";
	public static final String GRAPH_JSON_NAME = "graphJson";
	public static final String GRAPH_AGENT_NAME = "graphAgent";
	public static final double tolerance = 10;
	Double x = null, y = null, x2 = null, y2 = null;

	// Instanciation des différents Components

	JFrame frame;

	JPanel panelFile, panelGraph, panelGraphJSon, panelGraphAgent,
			panelZoomJSon, panelZoomAgent, panelModifJSon, panelModifAg,
			panelOptionJSon, panelOptionAg;

	JSplitPane splitPane, splitPane2;

	JScrollPane scrollJSon, scrollAgent, scrollStatut;

	JTextField textDirectory, textJSon, textAg;

	JColorTextPane textColorStatut;

	JButton buttonGS, zoomAvantJSon, zoomArrJSon, zoomTextJSon, zoomAvantAg,
			zoomArrAg, zoomTextAg, addNodeJSon, addEdgeJSon, addNodeAg,
			addEdgeAg, structGraphJson, structGraphAg;

	JMenuBar menu_bar1;

	JMenu menu1;

	JMenuItem importMenu, exitMenu;

	Viewer viewerJson, viewerAgent;

	View viewJson, viewAgent;

	Graph graphJson, graphAgent;

	double zoom = 1, dezoom = 1;

	final int xWindow = 50, yWindow = 0, widthWindow = 1280,
			heightWindow = 700, sizeSeparator = 5;

	boolean isAutoLayoutJson, isAutoLayoutAg, isGraphJsonLoaded = false,
			isGraphAgentLoaded = false;

	SpriteManager spriteManagerJson, spriteManagerAgent;

	public Fenetre() {

		// Initialisation de la fenêtre principale
		frame = new JFrame("Projet SMBL");

		// Initialisation et définition du 1er panneau
		panelFile = new JPanel(new GridLayout(1, 2, 20, 5));
		panelFile.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createTitledBorder("JSon File"),
				BorderFactory.createEmptyBorder(1, 1, 1, 1)));

		// Initialisation et définition du 2ème panneau
		panelGraph = new JPanel(new BorderLayout());
		panelGraph.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createTitledBorder("Graphs"),
				BorderFactory.createEmptyBorder(1, 1, 1, 1)));

		// Initialisation et définition du panneau d'ajout de noeuds et de
		// transition gauche
		panelModifJSon = new JPanel(new GridLayout(3, 1, 15, 50));

		// Initialisation et définition du panneau d'ajout de noeuds et de
		// transition droit
		panelModifAg = new JPanel(new GridLayout(3, 1, 15, 50));

		// Initialisation des bouttons de zoom
		zoomAvantJSon = new JButton("<html><b>Zoom +</b></html>");
		zoomArrJSon = new JButton("<html><b>Zoom -</b></html>");
		zoomTextJSon = new JButton("<html><b>%</b></html>");
		zoomAvantAg = new JButton("<html><b>Zoom +</b></html>");
		zoomArrAg = new JButton("<html><b>Zoom -</b></html>");
		zoomTextAg = new JButton("<html><b>%</b></html>");

		// initialisation de la zone de texte pour le pourcentage de zoom
		textJSon = new JTextField();
		textJSon.setPreferredSize(new Dimension(40, 30));
		textAg = new JTextField();

		// Initialisation et définition du panneau pour zoomer le graphe Json
		panelZoomJSon = new JPanel(new GridLayout(2, 2, 20, 50));
		panelZoomJSon.add(zoomAvantJSon);
		panelZoomJSon.add(zoomArrJSon);
		panelZoomJSon.add(zoomTextJSon);
		panelZoomJSon.add(textJSon);

		// Initialisation et définition du panneau pour zoomer le graphe Agent
		panelZoomAgent = new JPanel(new GridLayout(2, 2, 20, 50));
		panelZoomAgent.add(zoomAvantAg);
		panelZoomAgent.add(zoomArrAg);
		panelZoomAgent.add(zoomTextAg);
		panelZoomAgent.add(textAg);

		// Initialisation des boutons d'option
		addNodeJSon = new JButton("Node +");
		addEdgeJSon = new JButton("Edge +");
		structGraphJson = new JButton("Structurer / Déstructurer");
		addNodeAg = new JButton("Node +");
		addEdgeAg = new JButton("Edge +");
		structGraphAg = new JButton("Structurer / Déstructurer");

		// Ajout des boutons dans les panneaux respectifs
		panelModifJSon.add(addNodeJSon);
		panelModifJSon.add(addEdgeJSon);
		panelModifJSon.add(structGraphJson);
		panelModifAg.add(addNodeAg);
		panelModifAg.add(addEdgeAg);
		panelModifAg.add(structGraphAg);

		// Initialisation et définition panneau option gauche
		panelOptionJSon = new JPanel(new GridLayout(2, 1, 20, 50));
		panelOptionJSon.add(panelZoomJSon);
		panelOptionJSon.add(panelModifJSon);

		// Initialisation et définition panneau option droit
		panelOptionAg = new JPanel(new GridLayout(2, 1, 20, 50));
		panelOptionAg.add(panelZoomAgent);
		panelOptionAg.add(panelModifAg);

		// Initialisation de la zone de texte de la barre de statut
		textColorStatut = new JColorTextPane();
		textColorStatut.styleDoc();
		textColorStatut.setEditable(false);
		textColorStatut.setBackground(new Color(238, 238, 238));

		// Ajout de Components au 1er panneau
		buttonGS = new JButton("To GraphStream");
		textDirectory = new JTextField("Directory");
		textDirectory.setPreferredSize(new Dimension(250, 20));
		textDirectory.setEditable(false);
		panelFile.add(textDirectory);
		panelFile.add(buttonGS);

		// Initialisation et définition de la barre de menu et ses composants
		menu_bar1 = new JMenuBar();

		menu1 = new JMenu("File");

		importMenu = new JMenuItem("Import");
		exitMenu = new JMenuItem("Exit");

		menu1.add(importMenu);
		menu1.add(exitMenu);

		menu_bar1.add(menu1);

		// Initialisation des paramètres que va contenir le 2nd splitPane
		scrollJSon = new JScrollPane();
		scrollAgent = new JScrollPane();
		scrollStatut = new JScrollPane();
		scrollStatut.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createTitledBorder("Statut"),
				BorderFactory.createEmptyBorder(1, 1, 1, 1)));
		scrollStatut.setPreferredSize(new Dimension(panelGraph.getWidth(),
				panelGraph.getHeight() + 100));
		scrollStatut.setViewportView(textColorStatut);
		panelGraphJSon = new JPanel();
		panelGraphAgent = new JPanel();

		// Action lors du clic sur l'item "Import"
		importMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// TODO changer le chemin d'acces lors de la release
				JFileChooser dialogue = new JFileChooser(new File(
						"./src/test/resources"));
				File fichier;

				if (dialogue.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
					fichier = dialogue.getSelectedFile();
					textDirectory.setText(fichier.toString());
				}
			}
		});

		// Action lors du clic sur l'item "Exit"
		exitMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(1);
			}
		});

		// Action lors du clic sur l'item "To GraphStream"
		buttonGS.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Visualisation du graph généré par le fichier importé au
				// format .json

				if (!textDirectory.getText().equals("Directory")) {
					JsonToGS jSTGS = new JsonToGS();
					try {
						graphJson = jSTGS.generateGraph(
								textDirectory.getText(), GRAPH_JSON_NAME);

						CustomGraphRenderer.setStyleGraph(graphJson);
						GraphModifier.setNodeClass(graphJson);
						spriteManagerJson = new SpriteManager(graphJson);
						GraphModifier.generateSprite(graphJson,
								spriteManagerJson);

						viewerJson = new Viewer(graphJson,
								Viewer.ThreadingModel.GRAPH_IN_ANOTHER_THREAD);
						viewerJson.enableAutoLayout();
						isAutoLayoutJson = true;
						viewJson = viewerJson.addDefaultView(false);
						setListenerOnViewer(viewerJson);

						panelGraphJSon.removeAll();
						panelGraphJSon.setLayout(new BorderLayout());
						panelGraphJSon.add((Component) viewJson,
								BorderLayout.CENTER);
						scrollJSon.setViewportView(panelGraphJSon);

						isGraphJsonLoaded = true;

						graphAgent = GraphModifier.GraphToGraph(graphJson,
								GRAPH_AGENT_NAME);

						CustomGraphRenderer.setStyleGraph(graphAgent);
						GraphModifier.setNodeClass(graphAgent);
						spriteManagerAgent = new SpriteManager(graphAgent);
						GraphModifier.generateSprite(graphAgent,
								spriteManagerAgent);

						viewerAgent = new Viewer(graphAgent,
								Viewer.ThreadingModel.GRAPH_IN_ANOTHER_THREAD);
						viewerAgent.enableAutoLayout();
						isAutoLayoutAg = true;
						viewAgent = viewerAgent.addDefaultView(false);
						setListenerOnViewer(viewerAgent);

						panelGraphAgent.removeAll();
						panelGraphAgent.setLayout(new BorderLayout());
						panelGraphAgent.add((Component) viewAgent,
								BorderLayout.CENTER);
						scrollAgent.setViewportView(panelGraphAgent);

						isGraphAgentLoaded = true;

					} catch (JsonParseException e1) {
						textColorStatut
								.appendErrorMessage(JsonToGS.FILE_FORMAT_ERROR);
					} catch (IOException e1) {
						textColorStatut
								.appendErrorMessage(JsonToGS.FILE_FORMAT_ERROR);
					} catch (FileFormatException e1) {
						textColorStatut
								.appendErrorMessage(JsonToGS.FILE_FORMAT_ERROR);
					}
				} else {
					textColorStatut.appendDoc(NO_FILE_SELECTED);
				}
			}
		});

		// Action lors du clic sur l'item "+" de la partie gauche
		zoomAvantJSon.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (isGraphJsonLoaded) {
					zoom = viewJson.getCamera().getViewPercent();
					viewJson.getCamera().setViewPercent(zoom - 0.1);
				}
			}
		});

		// Action lors du clic sur l'item "-" de la partie gauche
		zoomArrJSon.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (isGraphJsonLoaded) {
					dezoom = viewJson.getCamera().getViewPercent();
					viewJson.getCamera().setViewPercent(dezoom + 0.1);
				}
			}
		});

		// Action lors du clic sur l'item "%" de la partie gauche
		zoomTextJSon.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (isGraphJsonLoaded) {
					String s = textJSon.getText();
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
						textColorStatut.appendDoc("Ce n'est pas un entier \n");
					} else {
						pourcentage = Integer.parseInt(s);
						if (pourcentage > 100) {
							zoomAvant = pourcentage - 100;
							total = 1 - (zoomAvant / 100);
							viewJson.getCamera().setViewPercent(total);
							textColorStatut.appendDoc("Zoom avant: "
									+ pourcentage + "% \n");
						} else if (pourcentage < 100) {
							zoomArr = 100 - pourcentage;
							total = 1 + (zoomArr / 100);
							viewJson.getCamera().setViewPercent(total);
							textColorStatut.appendDoc("Zoom arrière: "
									+ pourcentage + "% \n");
						} else {
							viewJson.getCamera().resetView();
						}
					}
				}

			}
		});

		// Action lors du clic sur l'item "Node +" de la partie gauche
		addNodeJSon.addActionListener(new ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				NodeDialog nodeLeft = new NodeDialog(frame, "Add Node");
				String s = nodeLeft.getName();
				if (!nodeLeft.getFerme()) {
					if (isGraphJsonLoaded) {
						if (!s.equals("")) {
							Node n = graphJson.getNode(s);
							if (n == null) {
								GraphModifier.addNode(nodeLeft, graphJson);
								GraphModifier.setNodeClass(graphJson);
							} else {
								msgError("Nom déjà existant");
							}
						} else {
							msgError("Nom invalide car vide");
						}
					} else if (graphJson == null && nodeLeft.getCheck()) {
						if (!s.equals("")) {
							graphJson = new MultiGraph(GRAPH_JSON_NAME);

							CustomGraphRenderer.setStyleGraph(graphJson);
							GraphModifier.addNode(nodeLeft, graphJson);
							GraphModifier.setNodeClass(graphJson);
							spriteManagerJson = new SpriteManager(graphJson);

							viewerJson = new Viewer(
									graphJson,
									Viewer.ThreadingModel.GRAPH_IN_ANOTHER_THREAD);
							viewerJson.enableAutoLayout();
							isAutoLayoutJson = true;
							viewJson = viewerJson.addDefaultView(false);
							viewJson.setMouseManager(new CustomMouseManager(
									graphJson, viewerJson));
							setListenerOnViewer(viewerJson);

							panelGraphJSon.removeAll();
							panelGraphJSon.setLayout(new BorderLayout());
							panelGraphJSon.add((Component) viewJson,
									BorderLayout.CENTER);
							scrollJSon.setViewportView(panelGraphJSon);

							isGraphJsonLoaded = true;
						} else {
							msgError("Nom invalide car vide");
						}
					}
				}
			}
		});

		// Action lors du clic sur l'item "Edge +" de la partie gauche
		addEdgeJSon.addActionListener(new ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				if (isGraphJsonLoaded) {
					EdgeDialog edgeLeft = new EdgeDialog(frame, "Add Edge",
							graphJson);
					String s = edgeLeft.getLabel();
					if (!edgeLeft.getFerme() && edgeLeft.getCheck()) {
						if (!s.equals("")) {
							Edge ed = graphJson.getEdge(s);
							if (ed == null) {
								try {
									GraphModifier.addEdge(edgeLeft, graphJson);
									Edge edge = graphJson.getEdge(s);
									Sprite sprite = spriteManagerJson
											.addSprite(edge.getId());
									sprite.attachToEdge(s);
									sprite.setPosition(0.5, 0, 0);
								} catch (NoSpecifiedNodeException e) {
									textColorStatut.appendErrorMessage(e
											.getMessage());
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

		// Action lors du clic sur l'item "Structurer / Déstructurer" de la
		// partie gauche
		structGraphJson.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (isGraphJsonLoaded) {
					if (isAutoLayoutJson) {
						viewerJson.disableAutoLayout();
						isAutoLayoutJson = false;
					} else {
						viewerJson.enableAutoLayout();
						isAutoLayoutJson = true;
					}
				}
			}
		});

		// Action lors du clic sur l'item "+" de la partie droite
		zoomAvantAg.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (isGraphAgentLoaded) {
					zoom = viewAgent.getCamera().getViewPercent();
					viewAgent.getCamera().setViewPercent(zoom - 0.1);
				}
			}
		});

		// Action lors du clic sur l'item "-" de la partie droite
		zoomArrAg.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (isGraphAgentLoaded) {
					dezoom = viewJson.getCamera().getViewPercent();
					viewAgent.getCamera().setViewPercent(dezoom + 0.1);
				}
			}
		});

		// Action lors du clic sur l'item "%" de la partie droite
		zoomTextAg.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (isGraphAgentLoaded) {
					String s = textAg.getText();
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
						textColorStatut.appendDoc("Ce n'est pas un entier \n");
					} else {
						pourcentage = Integer.parseInt(s);
						if (pourcentage > 100) {
							zoomAvant = pourcentage - 100;
							total = 1 - (zoomAvant / 100);
							viewAgent.getCamera().setViewPercent(total);
							textColorStatut.appendDoc("Zoom avant: "
									+ pourcentage + "% \n");
						} else if (pourcentage < 100) {
							zoomArr = 100 - pourcentage;
							total = 1 + (zoomArr / 100);
							viewAgent.getCamera().setViewPercent(total);
							textColorStatut.appendDoc("Zoom arrière: "
									+ pourcentage + "% \n");
						} else {
							viewJson.getCamera().resetView();
						}
					}
				}
			}
		});

		// Action lors du clic sur l'item "Node +" de la partie droite
		addNodeAg.addActionListener(new ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				NodeDialog nodeRight = new NodeDialog(frame, "Add Node");
				if (!nodeRight.getFerme()) {
					if (isGraphAgentLoaded) {
						GraphModifier.addNode(nodeRight, graphAgent);
						GraphModifier.setNodeClass(graphAgent);
					} else if (graphJson == null) {
						graphJson = new MultiGraph("GraphAgent");

						CustomGraphRenderer.setStyleGraph(graphJson);
						GraphModifier.addNode(nodeRight, graphJson);
						GraphModifier.setNodeClass(graphJson);
						spriteManagerAgent = new SpriteManager(graphJson);

						viewerAgent = new Viewer(graphJson,
								Viewer.ThreadingModel.GRAPH_IN_ANOTHER_THREAD);
						viewerAgent.enableAutoLayout();
						isAutoLayoutAg = true;
						viewJson = viewerAgent.addDefaultView(false);
						setListenerOnViewer(viewerAgent);

						panelGraphAgent.removeAll();
						panelGraphAgent.setLayout(new BorderLayout());
						panelGraphAgent.add((Component) viewJson,
								BorderLayout.CENTER);
						scrollAgent.setViewportView(panelGraphAgent);

						isGraphAgentLoaded = true;
					}
				}
			}
		});

		// Action lors du clic sur l'item "Edge +" de la partie droite
		addEdgeAg.addActionListener(new ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				if (isGraphAgentLoaded) {
					EdgeDialog edgeRight = new EdgeDialog(frame, "Add Edge",
							graphJson);
					if (!edgeRight.getFerme()) {
						try {
							GraphModifier.addEdge(edgeRight, graphJson);
							Edge edge = graphJson.getEdge(edgeRight.getLabel());
							Sprite sprite = spriteManagerAgent.addSprite(edge
									.getId());
							sprite.attachToEdge(edgeRight.getLabel());
							sprite.setPosition(0.5, 0, 0);
						} catch (NoSpecifiedNodeException e) {
							textColorStatut.appendErrorMessage(e.getMessage());
						}
					}
				}
			}
		});

		// Action lors du clic sur l'item "Structurer / Déstructurer" de la
		// partie droite
		structGraphAg.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (isGraphAgentLoaded) {
					if (isAutoLayoutAg) {
						viewerAgent.disableAutoLayout();
						isAutoLayoutAg = false;
					} else {
						viewerAgent.enableAutoLayout();
						isAutoLayoutAg = true;
					}
				}
			}
		});

		// Création et définition du splitPane de la fenêtre principale
		splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, panelFile,
				panelGraph);
		splitPane.setDividerLocation(50);

		// Création et définition du splitPane qui sera dans le 2nd panneau
		splitPane2 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, scrollJSon,
				scrollAgent);
		splitPane2.setDividerSize(5);
		splitPane2.setDividerLocation((widthWindow - sizeSeparator + 75) / 3);

		panelGraph.add(panelOptionAg, BorderLayout.EAST);
		panelGraph.add(panelOptionJSon, BorderLayout.WEST);
		panelGraph.add(splitPane2, BorderLayout.CENTER);
		panelGraph.add(scrollStatut, BorderLayout.SOUTH);

		// Définition de la fenêtre principale
		frame.add(splitPane);
		frame.setJMenuBar(menu_bar1);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(true);
		frame.setBounds(xWindow, yWindow, widthWindow, heightWindow);
		frame.setVisible(true);

		// Centrage de la fenetre
		pack();
		frame.setLocationRelativeTo(null);
	}

	public void setListenerOnViewer(final Viewer viewer) {
		// Action lors du déplacement de la souris sur le graphe
		final View view = viewer.getDefaultView();

		viewer.getDefaultView().addMouseMotionListener(
				new MouseMotionListener() {

					public void mouseMoved(MouseEvent e) {
						String s = "<html>";
						Element elem = findNodeOrSpriteAtWithTolerance(e, view);
						if (elem instanceof Node) {
							String idNode = elem.getId();
							Node node = graphJson.getNode(idNode);
							for (String attKey : node.getAttributeKeySet()) {
								s += attKey + " : " + node.getAttribute(attKey)
										+ "<br/>";
							}
							s += "</html>";
							viewer.getDefaultView().setToolTipText(s);
						} else if (elem instanceof GraphicSprite) {
							String idSprite = elem.getId();
							Edge edge = graphJson.getEdge(idSprite);
							for (String attKey : edge.getAttributeKeySet()) {
								s += attKey + " : " + edge.getAttribute(attKey)
										+ "<br/>";
							}
							s += "</html>";
							viewer.getDefaultView().setToolTipText(s);
						} else {
							viewer.getDefaultView().setToolTipText(null);
						}
						view.display(viewer.getGraphicGraph(), true);
					}

					public void mouseDragged(MouseEvent e) {
						Element elem = view.findNodeOrSpriteAt(e.getX(),
								e.getY());
						if (elem == null) {
							if (x == null && y == null) {
								x = (double) e.getX();
								y = (double) e.getY();
							} else {
								x = x2;
								y = y2;
							}
							x2 = (double) e.getX();
							y2 = (double) e.getY();

							double posX = view.getCamera().getViewCenter().x;
							double posY = view.getCamera().getViewCenter().y;
							double posZ = view.getCamera().getViewCenter().z;

							view.getCamera().setViewCenter(
									(posX + ((x2 - x) * (-1)) / 100),
									(posY + (y2 - y) / 100), posZ);
						}
					}
				});

		viewer.getDefaultView().addMouseListener(new MouseListener() {

			public void mouseClicked(MouseEvent arg0) {

			}

			public void mouseEntered(MouseEvent arg0) {

			}

			public void mouseExited(MouseEvent arg0) {

			}

			public void mousePressed(MouseEvent e) {

			}

			public void mouseReleased(MouseEvent arg0) {
				x = null;
				y = null;
			}

		});

		// Action lors de l'utilisation de la molette de la souris sur le graphe
		viewer.getDefaultView().addMouseWheelListener(new MouseWheelListener() {

			public void mouseWheelMoved(MouseWheelEvent e) {
				double wheelValue = e.getPreciseWheelRotation();
				if (wheelValue > 0) {
					dezoom = view.getCamera().getViewPercent();
					view.getCamera().setViewPercent(dezoom + 0.1);
				} else if (wheelValue < 0) {
					zoom = view.getCamera().getViewPercent();
					view.getCamera().setViewPercent(zoom - 0.1);
				}
			}
		});
	}

	public Element findNodeOrSpriteAtWithTolerance(MouseEvent e, View view) {
		Element elem = null;
		for (double yEvt = e.getY() - tolerance; yEvt < e.getY() + tolerance; yEvt += tolerance / 10) {
			for (double xEvt = e.getX() - tolerance; xEvt < e.getX()
					+ tolerance; xEvt += tolerance / 10) {
				elem = view.findNodeOrSpriteAt(xEvt, yEvt);
				if (elem != null) {
					return elem;
				}
			}
		}
		return null;
	}

	private void msgError(String s) {
		JOptionPane.showMessageDialog(this, s, "Error",
				JOptionPane.ERROR_MESSAGE);
	}

	public static void main(String[] args) {
		CustomGraphRenderer.SetRenderer();
		new Fenetre();
	}

}
