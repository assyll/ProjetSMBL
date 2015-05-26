package interfaceGraphique;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
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
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.MultiGraph;
import org.graphstream.ui.graphicGraph.GraphicElement;
import org.graphstream.ui.graphicGraph.GraphicNode;
import org.graphstream.ui.graphicGraph.GraphicSprite;
import org.graphstream.ui.spriteManager.SpriteManager;
import org.graphstream.ui.view.View;
import org.graphstream.ui.view.Viewer;

import com.fasterxml.jackson.core.JsonParseException;

//TODO Penser à demander si on doit mettre les méthodes en static ou non (CustomGraphRender, GraphModifier, JsonToGS)

@SuppressWarnings("serial")
public class Window extends JFrame {

	public static final String NO_FILE_SELECTED = "Veuillez d'abord sélectionner un fichier à importer";
	public static final String GRAPH_JSON_NAME = "graphJson";
	public static final String GRAPH_AGENT_NAME = "graphAgent";

	public static final double tolerance = 10;
	public static final double ZOOM = -0.1;
	public static final double DEZOOM = 0.1;

	// Instanciation des différents Components

	JFrame frame;

	JPanel panelFile, panelGraph, panelGraphJSon, panelGraphAgent,
			panelZoomJSon, panelZoomAgent, panelModifJSon, panelModifAgent,
			panelOptionJSon, panelOptionAgent;

	JSplitPane splitPaneHorizontale, splitPaneVerticale;

	JScrollPane scrollJSon, scrollAgent, scrollStatut;

	JTextField textDirectory, textJSon, textAgent;

	JColorTextPane textColorStatut;

	JButton buttonGS, zoomAvantJSon, zoomArrJSon, zoomAvantAgent,
			zoomArrAgent, addNodeJSon, addEdgeJSon,
			suppNodeJSon, suppEdgeJSon, suppNodeAgent, suppEdgeAgent,
			addNodeAgent, addEdgeAgent, structGraphJson, structGraphAgent,
			zoomCenterJSon, zoomCenterAgent, cleanGraphJson, cleanGraphAgent,
			displayJson, displayAgent;

	JMenuBar menu_bar1;

	JMenu menuFile, menuDisplay;

	JMenuItem importMenu, exitMenu, displayDoubleCircle, displayRobot,
			displayBasic;

	Viewer viewerJson, viewerAgent;

	View viewJson, viewAgent;

	Graph graphJson, graphAgent;

	double zoom = 1, dezoom = 1;

	Double x = null, y = null, x2 = null, y2 = null;

	Double valZoom = 100.00;

	DecimalFormat df = new DecimalFormat("###.00");

	final int xWindow = 50, yWindow = 0, widthWindow = 1280,
			heightWindow = 700, sizeSeparator = 5;

	boolean isAutoLayoutJson, isAutoLayoutAgent, isGraphJsonLoaded = false,
			isGraphAgentLoaded = false;

	SpriteManager spriteManagerJson, spriteManagerAgent;

	GraphicElement gElement = null;

	public Window() {

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
		panelModifJSon = new JPanel(new GridLayout(6, 1, 0, 15));

		// Initialisation et définition du panneau d'ajout de noeuds et de
		// transition droit
		panelModifAgent = new JPanel(new GridLayout(6, 1, 0, 15));

		// Initialisation des boutons de clean des graphes
		cleanGraphJson = new JButton("<html><b>Clean</b></html>");
		cleanGraphAgent = new JButton("<html><b>Clean</b></html>");

		// Initialisation des bouttons de zoom
		zoomAvantJSon = new JButton("<html><b>Zoom +</b></html>");
		zoomArrJSon = new JButton("<html><b>Zoom -</b></html>");
		zoomCenterJSon = new JButton("<html><b>Center</b></html>");
		displayJson = new JButton("<html><b>Display</b></html>");
		zoomAvantAgent = new JButton("<html><b>Zoom +</b></html>");
		zoomArrAgent = new JButton("<html><b>Zoom -</b></html>");
		zoomCenterAgent = new JButton("<html><b>Center</b></html>");
		displayAgent = new JButton("<html><b>Display</b></html>");

		// initialisation de la zone de texte pour le pourcentage de zoom
		textJSon = new JTextField();
		textJSon.setText(valZoom + " %");
		textJSon.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent evt) {
				if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
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
							textColorStatut
									.appendDoc("Ce n'est pas un entier \n");
						} else {
							pourcentage = Integer.parseInt(s);
							if (pourcentage > 100) {
								zoomAvant = pourcentage - 100;
								total = 1 - (zoomAvant / 100);
								viewJson.getCamera().setViewPercent(total);
								valZoom = viewJson.getCamera().getViewPercent() * 100;
								textJSon.setText(df.format(valZoom) + " %");
							} else if (pourcentage < 100) {
								zoomArr = 100 - pourcentage;
								total = 1 + (zoomArr / 100);
								viewJson.getCamera().setViewPercent(total);
								valZoom = viewJson.getCamera().getViewPercent() * 100;
								textJSon.setText(df.format(valZoom) + " %");
							} else {
								viewJson.getCamera().resetView();
							}
						}
					} else {
						viewJson.getCamera().resetView();
					}
				}
			}

			public void keyReleased(KeyEvent evt) {
			}

			public void keyTyped(KeyEvent evt) {
			}

		});

		textAgent = new JTextField();
		textAgent.setText(valZoom + " %");
		textAgent.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent evt) {
				if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
					if (isGraphAgentLoaded) {
						String s = textAgent.getText();
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
							textColorStatut
									.appendDoc("Ce n'est pas un entier \n");
						} else {
							pourcentage = Integer.parseInt(s);
							if (pourcentage > 100) {
								zoomAvant = pourcentage - 100;
								total = 1 - (zoomAvant / 100);
								viewAgent.getCamera().setViewPercent(total);
								valZoom = viewAgent.getCamera()
										.getViewPercent() * 100;
								textAgent.setText(df.format(valZoom) + " %");
							} else if (pourcentage < 100) {
								zoomArr = 100 - pourcentage;
								total = 1 + (zoomArr / 100);
								viewJson.getCamera().setViewPercent(total);
								valZoom = viewAgent.getCamera()
										.getViewPercent() * 100;
								textAgent.setText(df.format(valZoom) + " %");
							} else {
								viewAgent.getCamera().resetView();
							}
						}
					} else {
						viewAgent.getCamera().resetView();
					}
				}
			}

			public void keyReleased(KeyEvent evt) {
			}

			public void keyTyped(KeyEvent evt) {
			}

		});

		// Initialisation et définition du panneau pour zoomer le graphe Json
		panelZoomJSon = new JPanel(new GridLayout(5, 1, 0, 20));
		panelZoomJSon.add(zoomAvantJSon);
		panelZoomJSon.add(zoomArrJSon);
		panelZoomJSon.add(textJSon);
		panelZoomJSon.add(zoomCenterJSon);
		panelZoomJSon.add(displayJson);

		// Initialisation et définition du panneau pour zoomer le graphe Agent
		panelZoomAgent = new JPanel(new GridLayout(5, 1, 0, 20));
		panelZoomAgent.add(zoomAvantAgent);
		panelZoomAgent.add(zoomArrAgent);
		panelZoomAgent.add(textAgent);
		panelZoomAgent.add(zoomCenterAgent);
		panelZoomAgent.add(displayAgent);

		// Initialisation des boutons d'option
		addNodeJSon = new JButton("Node +");
		suppNodeJSon = new JButton("Node -");
		addEdgeJSon = new JButton("Edge +");
		suppEdgeJSon = new JButton("Edge -");
		structGraphJson = new JButton("Manual");
		addNodeAgent = new JButton("Node +");
		suppNodeAgent = new JButton("Node -");
		addEdgeAgent = new JButton("Edge +");
		suppEdgeAgent = new JButton("Edge -");
		structGraphAgent = new JButton("Manual");

		// Ajout des boutons dans les panneaux respectifs
		panelModifJSon.add(addNodeJSon);
		panelModifJSon.add(suppNodeJSon);
		panelModifJSon.add(addEdgeJSon);
		panelModifJSon.add(suppEdgeJSon);
		panelModifJSon.add(structGraphJson);
		panelModifJSon.add(cleanGraphJson);
		panelModifAgent.add(addNodeAgent);
		panelModifAgent.add(suppNodeAgent);
		panelModifAgent.add(addEdgeAgent);
		panelModifAgent.add(suppEdgeAgent);
		panelModifAgent.add(structGraphAgent);
		panelModifAgent.add(cleanGraphAgent);

		// Initialisation et définition panneau option gauche
		panelOptionJSon = new JPanel(new GridLayout(2, 1, 0, 50));
		panelOptionJSon.setPreferredSize(new Dimension(80, 200));
		panelOptionJSon.add(panelZoomJSon);
		panelOptionJSon.add(panelModifJSon);

		// Initialisation et définition panneau option droit
		panelOptionAgent = new JPanel(new GridLayout(2, 1, 0, 50));
		panelOptionAgent.setPreferredSize(new Dimension(80, 200));
		panelOptionAgent.add(panelZoomAgent);
		panelOptionAgent.add(panelModifAgent);

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

		menuFile = new JMenu("File");
		menuDisplay = new JMenu("Display");

		importMenu = new JMenuItem("Import");
		exitMenu = new JMenuItem("Exit");

		displayDoubleCircle = new JMenuItem("Double Circle");
		displayRobot = new JMenuItem("Robot");
		displayBasic = new JMenuItem("Basic");

		menuFile.add(importMenu);
		menuFile.add(exitMenu);
		menuDisplay.add(displayDoubleCircle);
		menuDisplay.add(displayRobot);
		menuDisplay.add(displayBasic);

		menu_bar1.add(menuFile);
		menu_bar1.add(menuDisplay);

		// Initialisation des paramètres que va contenir le 2nd splitPane
		scrollJSon = new JScrollPane();
		scrollAgent = new JScrollPane();
		scrollStatut = new JScrollPane();
		scrollStatut.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createTitledBorder("Statut"),
				BorderFactory.createEmptyBorder(1, 1, 1, 1)));
		scrollStatut.setPreferredSize(new Dimension(panelGraph.getWidth(),
				panelGraph.getHeight() + 60));
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

		// Action lors du clic sur l'item "Double Circle"
		displayDoubleCircle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				CustomGraphRenderer.setStyleGraphDoubleCircle(graphJson,
						graphAgent);
			}
		});

		// Action lors du clic sur l'item "Robot"
		displayRobot.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				CustomGraphRenderer.setStyleGraphRobot(graphJson, graphAgent);
			}
		});

		// Action lors du clic sur l'item "Basic"
		displayBasic.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				CustomGraphRenderer.setStyleGraphBasic(graphJson, graphAgent);
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
						frame.setCursor(Cursor
								.getPredefinedCursor(Cursor.WAIT_CURSOR));
						graphJson = jSTGS.generateGraph(
								textDirectory.getText(), GRAPH_JSON_NAME);
						initGraphPropertiesJson();
						initPanelGraphJson();
						textJSon.setText("100 %");
						textAgent.setText("100 %");
						structGraphAgent.setText("Manual");
						structGraphJson.setText("Manual");

						graphAgent = GraphModifier.GraphToGraph(graphJson,
								GRAPH_AGENT_NAME);
						initGraphPropertiesAgent();
						initPanelGraphAgent();

						frame.setCursor(Cursor
								.getPredefinedCursor(Cursor.DEFAULT_CURSOR));

					} catch (JsonParseException exception) {
						textColorStatut.appendErrorMessage(exception
								.getMessage());
					} catch (IOException exception) {
						textColorStatut.appendErrorMessage(exception
								.getMessage());
					} catch (FileFormatException exception) {
						textColorStatut.appendErrorMessage(exception
								.getMessage());
					} finally {
						frame.setCursor(Cursor
								.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
					}
				} else {
					textColorStatut.appendDoc(NO_FILE_SELECTED);
				}
			}
		});

		// Action lors du clic sur l'item "+" de la partie gauche
		zoomAvantJSon.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				modifZoom(viewJson, textJSon, isGraphJsonLoaded, ZOOM);
			}
		});

		// Action lors du clic sur l'item "-" de la partie gauche
		zoomArrJSon.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				modifZoom(viewJson, textJSon, isGraphJsonLoaded, DEZOOM);
			}
		});

		// Action lors du clic sur l'item "Node +" de la partie gauche
		addNodeJSon.addActionListener(new ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				AddNodeDialog addNodeJSon = new AddNodeDialog(frame, "Add Node");
				String s = addNodeJSon.getName();
				if (!addNodeJSon.getFerme()) {
					if (isGraphJsonLoaded) {
						if (!s.equals("")) {
							Node n = graphJson.getNode(s);
							if (n == null) {
								GraphModifier.addNode(addNodeJSon, graphJson);
							} else {
								msgError("Nom déjà existant");
							}
						} else {
							msgError("Nom invalide car vide");
						}
					} else {
						if (!s.equals("")) {
							graphJson = new MultiGraph(GRAPH_JSON_NAME);
							initGraphPropertiesJson();
							initPanelGraphJson();
							GraphModifier.addNode(addNodeJSon, graphJson);
						} else {
							msgError("Nom invalide car vide");
						}
					}
				}
			}
		});

		// Action lors du clic sur l'item "Node -" de la
		// partie gauche
		suppNodeJSon.addActionListener(new ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				if (isGraphJsonLoaded) {
					SuppNodeDialog suppNodeJSon = new SuppNodeDialog(frame,
							"Suppr Node", graphJson);
					if (!suppNodeJSon.getFerme()) {
						String s = suppNodeJSon.getName();
						textColorStatut.appendDoc(s);
						Node n = graphJson.getNode(s);
						for (Edge edge : n.getEachEdge()) {
							System.out.println(edge.getId());
							spriteManagerJson.removeSprite(edge.getId());
						}
						n = graphJson.removeNode(s);
						if (n != null) {
							msgAlert("Le noeud " + s + " a été supprimé.");
						}
					}
				} else {
					textColorStatut
							.appendDoc("Générez un graphe ou ajoutez un noeud avant tout");
				}
			}
		});

		// Action lors du clic sur l'item "Edge +" de la partie gauche
		addEdgeJSon.addActionListener(new ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				if (isGraphJsonLoaded) {
					AddEdgeDialog addEdgeJSon = new AddEdgeDialog(frame,
							"Add Edge", graphJson);
					String s = addEdgeJSon.getLabel();
					if (!addEdgeJSon.getFerme() && addEdgeJSon.getCheck()) {
						if (!s.equals("")) {
							Edge ed = graphJson.getEdge(s);
							if (ed == null) {
								try {
									GraphModifier.addEdge(addEdgeJSon,
											graphJson, spriteManagerJson);
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
				} else {
					textColorStatut
							.appendDoc("Il faut d'abord créer le graphe avec des nodes");
				}
			}
		});

		// Action lors du clic sur l'item "Edge -" de la
		// partie gauche
		suppEdgeJSon.addActionListener(new ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				if (isGraphJsonLoaded) {
					SuppEdgeDialog suppEdgeJSon = new SuppEdgeDialog(frame,
							"Suppr Edge", graphJson);
					if (!suppEdgeJSon.getFerme()) {
						String s = suppEdgeJSon.getName();
						textColorStatut.appendDoc(s);
						Edge e = graphJson.removeEdge(s);
						spriteManagerJson.removeSprite(e.getId());
						if (e != null) {
							msgAlert("La transition " + s + " a été supprimé.");
						}
					}
				} else {
					textColorStatut
							.appendDoc("Générez un graphe ou ajoutez une transition avant tout");
				}
			}
		});

		// Action lors du clic sur l'item "Structurer / Déstructurer" de la
		// partie gauche
		structGraphJson.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (isGraphJsonLoaded) {
					if (isAutoLayoutJson) {
						structGraphJson.setText("Auto");
						viewerJson.disableAutoLayout();
						isAutoLayoutJson = false;
					} else {
						structGraphJson.setText("Manual");
						viewerJson.enableAutoLayout();
						isAutoLayoutJson = true;
					}
				}
			}
		});

		// Action lors du clic sur l'item "Center" de la partie gauche
		zoomCenterJSon.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				viewJson.getCamera().resetView();
				valZoom = viewJson.getCamera().getViewPercent() * 100;
				textJSon.setText(df.format(valZoom) + " %");
			}
		});

		// Action lors du clic sur l'item "Clean" de la partie gauche
		cleanGraphJson.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (isGraphJsonLoaded) {
					panelGraphJSon.removeAll();
					isGraphJsonLoaded = false;
					panelGraphJSon.updateUI();
				} else {
					textColorStatut.appendDoc("Générez un graphe au préalable");
				}
			}
		});

		// Action lors du clic sur l'item "Display" de la partie gauche
		// TODO Gérez la view sur le nouvel affichage du graphe
		displayJson.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (isGraphJsonLoaded) {
					graphJson.display();
				} else {
					textColorStatut.appendDoc("Générez un graphe au préalable");
				}
			}
		});

		// Action lors du clic sur l'item "+" de la partie droite
		zoomAvantAgent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				modifZoom(viewAgent, textAgent, isGraphAgentLoaded, ZOOM);
			}
		});

		// Action lors du clic sur l'item "-" de la partie droite
		zoomArrAgent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				modifZoom(viewAgent, textAgent, isGraphAgentLoaded, DEZOOM);
			}
		});

		// Action lors du clic sur l'item "Node +" de la partie droite
		addNodeAgent.addActionListener(new ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				AddNodeDialog addNodeAgent = new AddNodeDialog(frame,
						"Add Node");
				if (!addNodeAgent.getFerme()) {
					if (isGraphAgentLoaded) {
						GraphModifier.addNode(addNodeAgent, graphAgent);
					} else {
						graphAgent = new MultiGraph(GRAPH_AGENT_NAME);
						initGraphPropertiesAgent();
						initPanelGraphAgent();
						GraphModifier.addNode(addNodeAgent, graphAgent);
					}
				}
			}
		});

		// Action lors du clic sur l'item "Node -" de la
		// partie droite
		suppNodeAgent.addActionListener(new ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				if (isGraphAgentLoaded) {
					SuppNodeDialog suppNodeAgent = new SuppNodeDialog(frame,
							"Suppr Node", graphAgent);
					if (!suppNodeAgent.getFerme()) {
						String s = suppNodeAgent.getName();
						textColorStatut.appendDoc(s);
						Node n = graphAgent.getNode(s);
						for (Edge edge : n.getEachEdge()) {
							System.out.println(edge.getId());
							spriteManagerAgent.removeSprite(edge.getId());
						}
						n = graphAgent.removeNode(s);
						if (n != null) {
							msgAlert("Le noeud " + s + " a été supprimé.");
						}
					}
				} else {
					textColorStatut
							.appendDoc("Générez un graphe ou ajoutez un noeud avant tout");
				}
			}
		});

		// Action lors du clic sur l'item "Edge +" de la partie droite
		addEdgeAgent.addActionListener(new ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				if (isGraphAgentLoaded) {
					AddEdgeDialog addEdgeAgent = new AddEdgeDialog(frame,
							"Add Edge", graphAgent);
					if (!addEdgeAgent.getFerme()) {
						try {
							GraphModifier.addEdge(addEdgeAgent, graphAgent,
									spriteManagerAgent);

						} catch (NoSpecifiedNodeException e) {
							textColorStatut.appendErrorMessage(e.getMessage());
						}
					}
				} else {
					textColorStatut
							.appendDoc("Il faut d'abord créer le graphe avec des nodes");
				}
			}
		});

		// Action lors du clic sur l'item "Edge -" de la
		// partie droite
		suppEdgeAgent.addActionListener(new ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				if (isGraphAgentLoaded) {
					SuppEdgeDialog suppEdgeAgent = new SuppEdgeDialog(frame,
							"Suppr Edge", graphAgent);
					if (!suppEdgeAgent.getFerme()) {
						String s = suppEdgeAgent.getName();
						textColorStatut.appendDoc(s);
						Edge e = graphAgent.removeEdge(s);
						spriteManagerAgent.removeSprite(e.getId());
						if (e != null) {
							msgAlert("La transition " + s + " a été supprimé.");
						}
					}
				} else {
					textColorStatut
							.appendDoc("Générez un graphe ou ajoutez une transition avant tout");
				}
			}
		});

		// Action lors du clic sur l'item "Structurer / Déstructurer" de la
		// partie droite
		structGraphAgent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (isGraphAgentLoaded) {
					if (isAutoLayoutAgent) {
						structGraphJson.setText("Auto");
						viewerAgent.disableAutoLayout();
						isAutoLayoutAgent = false;
					} else {
						structGraphJson.setText("Manual");
						viewerAgent.enableAutoLayout();
						isAutoLayoutAgent = true;
					}
				}
			}
		});

		// Action lors du clic sur l'item "Center" de la partie droite
		zoomCenterAgent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				viewAgent.getCamera().resetView();
				valZoom = viewAgent.getCamera().getViewPercent() * 100;
				textAgent.setText(df.format(valZoom) + " %");
			}
		});

		// Action lors du clic sur l'item "Clean" de la partie droite
		cleanGraphAgent.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if (isGraphAgentLoaded) {
					panelGraphAgent.removeAll();
					isGraphAgentLoaded = false;
					panelGraphAgent.updateUI();
				} else {
					textColorStatut.appendDoc("Générez un graphe au préalable");
				}
			}
		});

		// Action lors du clic sur l'item "Display" de la partie droite
		// TODO Gérez la view sur le nouvel affichage du graphe
		displayAgent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (isGraphAgentLoaded) {
					graphAgent.display();
				} else {
					textColorStatut.appendDoc("Générez un graphe au préalable");
				}
			}
		});

		// Création et définition du splitPane de la fenêtre principale
		splitPaneHorizontale = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
				panelFile, panelGraph);
		splitPaneHorizontale.setDividerLocation(50);

		// Création et définition du splitPane qui sera dans le 2nd panneau
		splitPaneVerticale = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
				scrollJSon, scrollAgent);
		splitPaneVerticale.setDividerSize(5);
		splitPaneVerticale
				.setDividerLocation((widthWindow - sizeSeparator + 350) / 3);

		panelGraph.add(panelOptionAgent, BorderLayout.EAST);
		panelGraph.add(panelOptionJSon, BorderLayout.WEST);
		panelGraph.add(splitPaneVerticale, BorderLayout.CENTER);
		panelGraph.add(scrollStatut, BorderLayout.SOUTH);

		// Définition de la fenêtre principale
		frame.add(splitPaneHorizontale);
		frame.setJMenuBar(menu_bar1);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(true);
		frame.setBounds(xWindow, yWindow, widthWindow, heightWindow);
		frame.setVisible(true);

		// Centrage de la fenetre
		pack();
		frame.setLocationRelativeTo(null);
	}

	public void setListenerOnViewer(final Viewer viewer, final Graph graph,
			final JTextField jTextField, final boolean isGraphLoaded) {
		// Action lors du déplacement de la souris sur le graphe
		final View view = viewer.getDefaultView();
		final JComponent jCompView = (JComponent) view;
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Image image = toolkit.getImage("./src/main/resources/Drag_Hand.png");
		Point hotSpot = new Point(0, 0);
		final Cursor cursor = toolkit.createCustomCursor(image, hotSpot,
				"drag_hand");

		viewer.getDefaultView().addMouseMotionListener(
				new MouseMotionListener() {

					public void mouseMoved(MouseEvent e) {
						String s = "<html>";
						GraphicElement elem = findNodeOrSpriteAtWithTolerance(
								e, view);
						if (elem instanceof GraphicNode) {
							String idNode = elem.getId();
							Node node = graph.getNode(idNode);
							for (String attKey : node.getAttributeKeySet()) {
								s += attKey + " : " + node.getAttribute(attKey)
										+ "<br/>";
							}
							s += "</html>";
							if ((jCompView.getToolTipText() == null || !jCompView
									.getToolTipText().equals(s))) {
								jCompView.setToolTipText(s);
								view.display(viewer.getGraphicGraph(), true);
							}
						} else if (elem instanceof GraphicSprite) {
							String idSprite = elem.getId();
							Edge edge = graph.getEdge(idSprite);
							for (String attKey : edge.getAttributeKeySet()) {
								s += attKey + " : " + edge.getAttribute(attKey)
										+ "<br/>";
							}
							s += "</html>";
							if ((jCompView.getToolTipText() == null || !jCompView
									.getToolTipText().equals(s))) {
								jCompView.setToolTipText(s);
								view.display(viewer.getGraphicGraph(), true);
							}
						} else if (jCompView.getToolTipText() != null) {
							jCompView.setToolTipText(null);
							view.display(viewer.getGraphicGraph(), true);
						}
					}

					public void mouseDragged(MouseEvent e) {
						jCompView.setCursor(cursor);
						if (!(gElement instanceof GraphicNode)) {
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
						} else if (gElement instanceof GraphicNode) {
							view.moveElementAtPx(gElement, e.getX(), e.getY());
						}
					}
				});

		jCompView.addMouseListener(new MouseListener() {

			public void mouseClicked(MouseEvent arg0) {

			}

			public void mouseEntered(MouseEvent arg0) {
				jCompView.setCursor(Cursor
						.getPredefinedCursor(Cursor.HAND_CURSOR));
			}

			public void mouseExited(MouseEvent arg0) {
				jCompView.setCursor(Cursor
						.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}

			public void mousePressed(MouseEvent e) {
				gElement = findNodeOrSpriteAtWithTolerance(e, view);
				if (gElement instanceof GraphicNode) {
					view.moveElementAtPx(gElement, e.getX(), e.getY());
				}
			}

			public void mouseReleased(MouseEvent arg0) {
				x = null;
				y = null;
				gElement = null;
				jCompView.setCursor(Cursor
						.getPredefinedCursor(Cursor.HAND_CURSOR));
			}

		});

		// Action lors de l'utilisation de la molette de la souris sur le graphe
		viewer.getDefaultView().addMouseWheelListener(new MouseWheelListener() {

			public void mouseWheelMoved(MouseWheelEvent e) {
				double wheelValue = e.getPreciseWheelRotation();
				if (wheelValue > 0) {
					modifZoom(view, jTextField, isGraphLoaded, DEZOOM);
				} else if (wheelValue < 0) {
					modifZoom(view, jTextField, isGraphLoaded, ZOOM);
				}
			}
		});
	}

	public GraphicElement findNodeOrSpriteAtWithTolerance(MouseEvent e,
			View view) {
		GraphicElement elem = null;
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

	public void initGraphPropertiesJson() {
		CustomGraphRenderer.setStyleGraphBasic(graphJson);
		GraphModifier.setNodesClass(graphJson);
		spriteManagerJson = new SpriteManager(graphJson);
		GraphModifier.generateSprites(graphJson, spriteManagerJson);

		viewerJson = new Viewer(graphJson,
				Viewer.ThreadingModel.GRAPH_IN_ANOTHER_THREAD);
		isGraphJsonLoaded = true;

		viewerJson.enableAutoLayout();
		isAutoLayoutJson = true;

		viewJson = viewerJson.addDefaultView(false);

		// suppression du comportement par defaut du MouseListener de la view
		viewJson.setMouseManager(new CustomMouseManager());

		setListenerOnViewer(viewerJson, graphJson, textJSon, isGraphJsonLoaded);
	}

	public void initGraphPropertiesAgent() {
		CustomGraphRenderer.setStyleGraphBasic(graphAgent);
		GraphModifier.setNodesClass(graphAgent);
		spriteManagerAgent = new SpriteManager(graphAgent);
		GraphModifier.generateSprites(graphAgent, spriteManagerAgent);

		viewerAgent = new Viewer(graphAgent,
				Viewer.ThreadingModel.GRAPH_IN_ANOTHER_THREAD);
		isGraphAgentLoaded = true;

		viewerAgent.enableAutoLayout();
		isAutoLayoutAgent = true;
		viewAgent = viewerAgent.addDefaultView(false);

		// suppression du comportement par defaut du MouseListener de la view
		viewAgent.setMouseManager(new CustomMouseManager());

		setListenerOnViewer(viewerAgent, graphAgent, textAgent,
				isGraphAgentLoaded);
	}

	public void initPanelGraphJson() {
		panelGraphJSon.removeAll();
		panelGraphJSon.setLayout(new BorderLayout());
		panelGraphJSon.add((Component) viewJson, BorderLayout.CENTER);
		scrollJSon.setViewportView(panelGraphJSon);
	}

	public void initPanelGraphAgent() {
		panelGraphAgent.removeAll();
		panelGraphAgent.setLayout(new BorderLayout());
		panelGraphAgent.add((Component) viewAgent, BorderLayout.CENTER);
		scrollAgent.setViewportView(panelGraphAgent);
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

	private void msgError(String s) {
		JOptionPane.showMessageDialog(this, s, "Error",
				JOptionPane.ERROR_MESSAGE);
	}

	private void msgAlert(String s) {
		JOptionPane.showMessageDialog(this, s, "Alert",
				JOptionPane.INFORMATION_MESSAGE);
	}

	public static void main(String[] args) {
		CustomGraphRenderer.SetRenderer();
		new Window();
	}

}
