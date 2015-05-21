package interfaceGraphique;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
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

//TODO Penser � demander si on doit mettre les m�thodes en static ou non (CustomGraphRender, GraphModifier, JsonToGS)

@SuppressWarnings("serial")
public class Fenetre extends JFrame {

	public static final String NO_FILE_SELECTED = "Veuillez d'abord s�lectionner un fichier � importer";
	public static final String GRAPH_JSON_NAME = "graphJson";
	public static final String GRAPH_AGENT_NAME = "graphAgent";

	public static final double tolerance = 10;

	// Instanciation des diff�rents Components

	JFrame frame;

	JPanel panelFile, panelGraph, panelGraphJSon, panelGraphAgent,
			panelZoomJSon, panelZoomAgent, panelModifJSon, panelModifAgent,
			panelOptionJSon, panelOptionAgent, panelZoomTextJSon,
			panelZoomTextAgent;

	JSplitPane splitPaneHorizontale, splitPaneVerticale;

	JScrollPane scrollJSon, scrollAgent, scrollStatut;

	JTextField textDirectory, textJSon, textAgent;

	JColorTextPane textColorStatut;

	JButton buttonGS, zoomAvantJSon, zoomArrJSon, zoomTextJSon, zoomAvantAgent,
			zoomArrAgent, zoomTextAgent, addNodeJSon, addEdgeJSon,
			suppNodeJSon, suppEdgeJSon, suppNodeAgent, suppEdgeAgent,
			addNodeAgent, addEdgeAgent, structGraphJson, structGraphAgent,
			zoomCenterJSon, zoomCenterAgent, cleanGraph;

	JMenuBar menu_bar1;

	JMenu menu1;

	JMenuItem importMenu, exitMenu;

	Viewer viewerJson, viewerAgent;

	View viewJson, viewAgent;

	Graph graphJson, graphAgent;

	double zoom = 1, dezoom = 1;

	Double x = null, y = null, x2 = null, y2 = null;

	Double valZoom;
	
	DecimalFormat df = new DecimalFormat("###.00");

	final int xWindow = 50, yWindow = 0, widthWindow = 1280,
			heightWindow = 700, sizeSeparator = 5;

	boolean isAutoLayoutJson, isAutoLayoutAgent, isGraphJsonLoaded = false,
			isGraphAgentLoaded = false;

	SpriteManager spriteManagerJson, spriteManagerAgent;

	GraphicElement gElement = null;

	public Fenetre() {

		// Initialisation de la fen�tre principale
		frame = new JFrame("Projet SMBL");

		// Initialisation et d�finition du 1er panneau
		panelFile = new JPanel(new GridLayout(1, 2, 20, 5));
		panelFile.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createTitledBorder("JSon File"),
				BorderFactory.createEmptyBorder(1, 1, 1, 1)));

		// Initialisation et d�finition du 2�me panneau
		panelGraph = new JPanel(new BorderLayout());
		panelGraph.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createTitledBorder("Graphs"),
				BorderFactory.createEmptyBorder(1, 1, 1, 1)));

		// Initialisation et d�finition du panneau d'ajout de noeuds et de
		// transition gauche
		panelModifJSon = new JPanel(new GridLayout(5, 1, 0, 25));

		// Initialisation et d�finition du panneau d'ajout de noeuds et de
		// transition droit
		panelModifAgent = new JPanel(new GridLayout(5, 1, 0, 25));

		// Initialisation et d�finition du panneau de modification du zoom de la
		// partie gauche
		panelZoomTextJSon = new JPanel(new GridLayout(1, 2, 20, 5));

		// Initialisation et d�finition du panneau de modification du zoom de la
		// partie droite
		panelZoomTextAgent = new JPanel(new GridLayout(1, 2, 20, 5));

		// Initialisation du bouton de clean des graphes
		cleanGraph = new JButton("<html><b>Clean</b></html>");

		// Initialisation des bouttons de zoom
		zoomAvantJSon = new JButton("<html><b>Zoom +</b></html>");
		zoomArrJSon = new JButton("<html><b>Zoom -</b></html>");
		zoomTextJSon = new JButton("<html><b>%</b></html>");
		zoomCenterJSon = new JButton("<html><b>Center</b></html>");
		zoomAvantAgent = new JButton("<html><b>Zoom +</b></html>");
		zoomArrAgent = new JButton("<html><b>Zoom -</b></html>");
		zoomTextAgent = new JButton("<html><b>%</b></html>");
		zoomCenterAgent = new JButton("<html><b>Center</b></html>");

		// initialisation de la zone de texte pour le pourcentage de zoom
		textJSon = new JTextField();
		textJSon.setText(valZoom+" %");
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
								valZoom = viewJson.getCamera()
										.getViewPercent()*100;
								textJSon.setText(df.format(valZoom) + " %");
							} else if (pourcentage < 100) {
								zoomArr = 100 - pourcentage;
								total = 1 + (zoomArr / 100);
								viewJson.getCamera().setViewPercent(total);
								valZoom = viewJson.getCamera()
										.getViewPercent()*100;
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
		textAgent.setText(valZoom+" %");
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
										.getViewPercent()*100;
								textAgent.setText(df.format(valZoom) + " %");
							} else if (pourcentage < 100) {
								zoomArr = 100 - pourcentage;
								total = 1 + (zoomArr / 100);
								viewJson.getCamera().setViewPercent(total);
								valZoom = viewAgent.getCamera()
										.getViewPercent()*100;
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

		// Ajout des boutons de zoom et pourcent dans leur panel respectif
		panelZoomTextJSon.add(textJSon);
		panelZoomTextJSon.add(zoomTextJSon);
		panelZoomTextAgent.add(textAgent);
		panelZoomTextAgent.add(zoomTextAgent);

		// Initialisation et d�finition du panneau pour zoomer le graphe Json
		panelZoomJSon = new JPanel(new GridLayout(4, 1, 20, 50));
		panelZoomJSon.add(zoomAvantJSon);
		panelZoomJSon.add(zoomArrJSon);
		panelZoomJSon.add(panelZoomTextJSon);
		panelZoomJSon.add(zoomCenterJSon);

		// Initialisation et d�finition du panneau pour zoomer le graphe Agent
		panelZoomAgent = new JPanel(new GridLayout(4, 1, 20, 50));
		panelZoomAgent.add(zoomAvantAgent);
		panelZoomAgent.add(zoomArrAgent);
		panelZoomAgent.add(panelZoomTextAgent);
		panelZoomAgent.add(zoomCenterAgent);

		// Initialisation des boutons d'option
		addNodeJSon = new JButton("Node +");
		suppNodeJSon = new JButton("Node -");
		addEdgeJSon = new JButton("Edge +");
		suppEdgeJSon = new JButton("Edge -");
		structGraphJson = new JButton("Structurer / D�structurer");
		addNodeAgent = new JButton("Node +");
		suppNodeAgent = new JButton("Node -");
		addEdgeAgent = new JButton("Edge +");
		suppEdgeAgent = new JButton("Edge -");
		structGraphAgent = new JButton("Structurer / D�structurer");

		// Ajout des boutons dans les panneaux respectifs
		panelModifJSon.add(addNodeJSon);
		panelModifJSon.add(suppNodeJSon);
		panelModifJSon.add(addEdgeJSon);
		panelModifJSon.add(suppEdgeJSon);
		panelModifJSon.add(structGraphJson);
		panelModifAgent.add(addNodeAgent);
		panelModifAgent.add(suppNodeAgent);
		panelModifAgent.add(addEdgeAgent);
		panelModifAgent.add(suppEdgeAgent);
		panelModifAgent.add(structGraphAgent);

		// Initialisation et d�finition panneau option gauche
		panelOptionJSon = new JPanel(new GridLayout(2, 1, 0, 50));
		panelOptionJSon.setPreferredSize(new Dimension(200, 200));
		panelOptionJSon.add(panelZoomJSon);
		panelOptionJSon.add(panelModifJSon);

		// Initialisation et d�finition panneau option droit
		panelOptionAgent = new JPanel(new GridLayout(2, 1, 0, 50));
		panelOptionAgent.setPreferredSize(new Dimension(200, 200));
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

		// Initialisation et d�finition de la barre de menu et ses composants
		menu_bar1 = new JMenuBar();

		menu1 = new JMenu("File");

		importMenu = new JMenuItem("Import");
		exitMenu = new JMenuItem("Exit");

		menu1.add(importMenu);
		menu1.add(exitMenu);

		menu_bar1.add(menu1);

		// Initialisation des param�tres que va contenir le 2nd splitPane
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

		// Action lors du clic sur l'item "To GraphStream"
		buttonGS.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Visualisation du graph g�n�r� par le fichier import� au
				// format .json

				if (!textDirectory.getText().equals("Directory")) {
					JsonToGS jSTGS = new JsonToGS();
					try {
						graphJson = jSTGS.generateGraph(
								textDirectory.getText(), GRAPH_JSON_NAME);
						initGraphPropertiesJson();
						initPanelGraphJson();
						textJSon.setText("100 %");
						textAgent.setText("100 %");

						graphAgent = GraphModifier.GraphToGraph(graphJson,
								GRAPH_AGENT_NAME);
						initGraphPropertiesAgent();
						initPanelGraphAgent();

					} catch (JsonParseException exception) {
						textColorStatut.appendErrorMessage(exception
								.getMessage());
					} catch (IOException exception) {
						textColorStatut.appendErrorMessage(exception
								.getMessage());
					} catch (FileFormatException exception) {
						textColorStatut.appendErrorMessage(exception
								.getMessage());
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
					valZoom = viewJson.getCamera()
							.getViewPercent() * 100;
					textJSon.setText(df.format(valZoom) + " %");
				}
			}
		});

		// Action lors du clic sur l'item "-" de la partie gauche
		zoomArrJSon.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (isGraphJsonLoaded) {
					dezoom = viewJson.getCamera().getViewPercent();
					viewJson.getCamera().setViewPercent(dezoom + 0.1);
					valZoom = viewJson.getCamera()
							.getViewPercent() * 100;
					textJSon.setText(df.format(valZoom) + " %");
				}
			}
		});

		// Action lors du clic sur l'item "%" de la partie gauche
		zoomTextJSon.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// TODO faire une fonction pour traiter les donn�es de la
				// zone de texte car elle apparait 4 fois (textJSon,
				// textAgent, zoomTextJSon, zoomTextAgent

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
							valZoom = viewJson.getCamera()
									.getViewPercent() * 100;
							textJSon.setText(df.format(valZoom) + " %");
						} else if (pourcentage < 100) {
							zoomArr = 100 - pourcentage;
							total = 1 + (zoomArr / 100);
							viewJson.getCamera().setViewPercent(total);
							valZoom = viewJson.getCamera()
									.getViewPercent() * 100;
							textJSon.setText(df.format(valZoom) + " %");
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
				AddNodeDialog addNodeJSon = new AddNodeDialog(frame, "Add Node");
				String s = addNodeJSon.getName();
				if (!addNodeJSon.getFerme()) {
					if (isGraphJsonLoaded) {
						if (!s.equals("")) {
							Node n = graphJson.getNode(s);
							if (n == null) {
								GraphModifier.addNode(addNodeJSon, graphJson);
							} else {
								msgError("Nom d�j� existant");
							}
						} else {
							msgError("Nom invalide car vide");
						}
					} else if (graphJson == null && addNodeJSon.getCheck()) {
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
							msgAlert("Le noeud " + s + " a �t� supprim�.");
						}
					}
				} else {
					textColorStatut
							.appendDoc("G�n�rez un graphe ou ajoutez un noeud avant tout");
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
								msgError("Nom d�j� existant");
							}
						} else {
							msgError("Nom invalide car vide");
						}
					}
				} else {
					textColorStatut
							.appendDoc("Il faut d'abord cr�er le graphe avec des nodes");
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
							msgAlert("La transition " + s + " a �t� supprim�.");
						}
					}
				} else {
					textColorStatut
							.appendDoc("G�n�rez un graphe ou ajoutez une transition avant tout");
				}
			}
		});

		// Action lors du clic sur l'item "Structurer / D�structurer" de la
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

		// Action lors du clic sur l'item "Center" de la partie gauche
		zoomCenterJSon.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				viewJson.getCamera().resetView();
				valZoom = viewJson.getCamera().getViewPercent() * 100;
				textJSon.setText(df.format(valZoom) + " %");
			}
		});

		// Action lors du clic sur l'item "+" de la partie droite
		zoomAvantAgent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (isGraphAgentLoaded) {
					zoom = viewAgent.getCamera().getViewPercent();
					viewAgent.getCamera().setViewPercent(zoom - 0.1);
					valZoom = viewAgent.getCamera()
							.getViewPercent() * 100;
					textAgent.setText(df.format(valZoom) + " %");
				}
			}
		});

		// Action lors du clic sur l'item "-" de la partie droite
		zoomArrAgent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (isGraphAgentLoaded) {
					dezoom = viewAgent.getCamera().getViewPercent();
					viewAgent.getCamera().setViewPercent(dezoom + 0.1);
					valZoom = viewAgent.getCamera()
							.getViewPercent() * 100;
					textAgent.setText(df.format(valZoom) + " %");
				}
			}
		});

		// Action lors du clic sur l'item "%" de la partie droite
		zoomTextAgent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
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
						textColorStatut.appendDoc("Ce n'est pas un entier \n");
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
							viewAgent.getCamera().setViewPercent(total);
							valZoom = viewAgent.getCamera()
									.getViewPercent() * 100;

						} else {
							viewAgent.getCamera().resetView();
							textAgent.setText(df.format(valZoom) + " %");
						}
					}
				}
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
					} else if (graphAgent == null) {
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
							msgAlert("Le noeud " + s + " a �t� supprim�.");
						}
					}
				} else {
					textColorStatut
							.appendDoc("G�n�rez un graphe ou ajoutez un noeud avant tout");
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
							.appendDoc("Il faut d'abord cr�er le graphe avec des nodes");
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
							msgAlert("La transition " + s + " a �t� supprim�.");
						}
					}
				} else {
					textColorStatut
							.appendDoc("G�n�rez un graphe ou ajoutez une transition avant tout");
				}
			}
		});

		// Action lors du clic sur l'item "Structurer / D�structurer" de la
		// partie droite
		structGraphAgent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (isGraphAgentLoaded) {
					if (isAutoLayoutAgent) {
						viewerAgent.disableAutoLayout();
						isAutoLayoutAgent = false;
					} else {
						viewerAgent.enableAutoLayout();
						isAutoLayoutAgent = true;
					}
				}
			}
		});

		// Action lors du clic sur l'item "Center" de la partie gauche
		zoomCenterAgent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				viewAgent.getCamera().resetView();
				valZoom = viewAgent.getCamera().getViewPercent() * 100;
				textAgent.setText(df.format(valZoom) + " %");
			}
		});

		// Cr�ation et d�finition du splitPane de la fen�tre principale
		splitPaneHorizontale = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
				panelFile, panelGraph);
		splitPaneHorizontale.setDividerLocation(50);

		// Cr�ation et d�finition du splitPane qui sera dans le 2nd panneau
		splitPaneVerticale = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
				scrollJSon, scrollAgent);
		splitPaneVerticale.setDividerSize(5);
		splitPaneVerticale
				.setDividerLocation((widthWindow - sizeSeparator + 75) / 3);

		panelGraph.add(panelOptionAgent, BorderLayout.EAST);
		panelGraph.add(panelOptionJSon, BorderLayout.WEST);
		panelGraph.add(splitPaneVerticale, BorderLayout.CENTER);
		panelGraph.add(scrollStatut, BorderLayout.SOUTH);

		// D�finition de la fen�tre principale
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

	public void setListenerOnViewer(final Viewer viewer, final Graph graph) {
		// Action lors du d�placement de la souris sur le graphe
		final View view = viewer.getDefaultView();
		final JComponent jCompView = (JComponent) view;

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
							jCompView.setToolTipText(s);
						} else if (elem instanceof GraphicSprite) {
							String idSprite = elem.getId();
							Edge edge = graph.getEdge(idSprite);
							for (String attKey : edge.getAttributeKeySet()) {
								s += attKey + " : " + edge.getAttribute(attKey)
										+ "<br/>";
							}
							s += "</html>";
							jCompView.setToolTipText(s);
						} else {
							jCompView.setToolTipText(null);
						}
						view.display(viewer.getGraphicGraph(), true);
					}

					public void mouseDragged(MouseEvent e) {
						if (gElement == null) {
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
							view.getCamera().resetView();

						}
					}
				});

		jCompView.addMouseListener(new MouseListener() {

			public void mouseClicked(MouseEvent arg0) {

			}

			public void mouseEntered(MouseEvent arg0) {

			}

			public void mouseExited(MouseEvent arg0) {

			}

			public void mousePressed(MouseEvent e) {
				gElement = findNodeOrSpriteAtWithTolerance(e, view);
				if (gElement instanceof GraphicNode) {
					view.moveElementAtPx(gElement, e.getX(), e.getY());
					view.getCamera().resetView();
				}
			}

			public void mouseReleased(MouseEvent arg0) {
				x = null;
				y = null;
				gElement = null;
			}

		});

		// Action lors de l'utilisation de la molette de la souris sur le graphe
		viewer.getDefaultView().addMouseWheelListener(new MouseWheelListener() {

			public void mouseWheelMoved(MouseWheelEvent e) {
				double wheelValue = e.getPreciseWheelRotation();
				if (wheelValue > 0) {
					dezoom = view.getCamera().getViewPercent();
					view.getCamera().setViewPercent(dezoom + 0.1);
					valZoom = view.getCamera().getViewPercent() * 100;
					// TODO Savoir sur quel view est la souris
					textAgent.setText(valZoom + " %");
					textJSon.setText(valZoom + " %");
				} else if (wheelValue < 0) {
					zoom = view.getCamera().getViewPercent();
					view.getCamera().setViewPercent(zoom - 0.1);
					valZoom = view.getCamera().getViewPercent() * 100;
					// TODO Savoir sur quel view est la souris
					textAgent.setText(valZoom + " %");
					textJSon.setText(valZoom + " %");
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
		CustomGraphRenderer.setStyleGraph(graphJson);
		GraphModifier.setNodesClass(graphJson);
		spriteManagerJson = new SpriteManager(graphJson);
		GraphModifier.generateSprites(graphJson, spriteManagerJson);

		viewerJson = new Viewer(graphJson,
				Viewer.ThreadingModel.GRAPH_IN_ANOTHER_THREAD);
		viewerJson.enableAutoLayout();
		viewJson = viewerJson.addDefaultView(false);

		// suppression du comportement par defaut du MouseListener de la view
		viewJson.setMouseManager(new CustomMouseManager());

		setListenerOnViewer(viewerJson, graphJson);

		isGraphJsonLoaded = true;
		isAutoLayoutJson = true;
	}

	public void initGraphPropertiesAgent() {
		CustomGraphRenderer.setStyleGraph(graphAgent);
		GraphModifier.setNodesClass(graphAgent);
		spriteManagerAgent = new SpriteManager(graphAgent);
		GraphModifier.generateSprites(graphAgent, spriteManagerAgent);

		viewerAgent = new Viewer(graphAgent,
				Viewer.ThreadingModel.GRAPH_IN_ANOTHER_THREAD);
		viewerAgent.enableAutoLayout();
		viewAgent = viewerAgent.addDefaultView(false);

		// suppression du comportement par defaut du MouseListener de la view
		viewAgent.setMouseManager(new CustomMouseManager());

		setListenerOnViewer(viewerAgent, graphAgent);

		isGraphAgentLoaded = true;
		isAutoLayoutAgent = true;
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
		new Fenetre();
	}

}
