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
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
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
import jsonAndGS.MyJsonGenerator;

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

import convertGraph.ConvertGStoNeo4j;
import convertGraph.ConvertNeo4jToGS;
import convertGraph.Fichier;

@SuppressWarnings("serial")
public class Window extends JFrame {

	public final static String pathGraphTemp = "./src/test/resources/.grapheTemporaire";

	public static final String NO_FILE_SELECTED = "You have to select a file to import";
	public static final String INPUT_VALUE_NOT_INTEGER = "You have to enter an integer \n";
	public static final String NAME_FIELD_EMPTY = "You have to fill the name field";
	public static final String NAME_ALREADY_IN_USE = "This name is already in use, please choose an other one";
	public static final String NO_GRAPH_GENERATED = "You have to generate a graph before";
	public static final String NO_NODE_DETECTED = "You have to generate a graph or add node before";
	public static final String NO_EDGE_DETECTED = "You have to generate a graph or add edge before";
	public static final String ACTION_ON_NODE = "The node ";
	public static final String ACTION_ON_EDGE = "The edge ";
	public static final String ERASE_ACTION = " has been erased";
	public static final String GRAPH_JSON_NAME = "graphJson";
	public static final String GRAPH_AGENT_NAME = "graphAgent";

	public static final String ZOOM_TT = "Zoom on the view";
	public static final String DEZOOM_TT = "Dezoom on the view";
	public static final String CENTER_TT = "Center the view";
	public static final String DISPLAY_TT = "Display the graph in an other window";
	public static final String ADD_NODE_TT = "add a node";
	public static final String DELETE_NODE_TT = "delete a node";
	public static final String ADD_EDGE_TT = "Add an edge";
	public static final String DELETE_EDGE_TT = "delete an edge";
	public static final String AUTO_LAYOUT_ENABLED_TT = "turn off the automatic layout";
	public static final String AUTO_LAYOUT_DISABLED_TT = "turn on the automatic layout";
	public static final String TREE_LAYOUT_TT = "Apply a tree layout";
	public static final String CLEAN_TT = "Clean the view";
	public static final String SAVE_TT = "Save the graph";

	public static final Dimension buttonsSize = new Dimension(25, 25);

	public static final double tolerance = 10;
	public static final double ZOOM = -0.1;
	public static final double DEZOOM = 0.1;

	// Instanciation des diff�rents Components

	JFrame frame;

	JPanel panelFile, panelGraph, panelGraphJSon, panelGraphAgent,
			panelZoomJSon, panelZoomAgent, panelModifJSon, panelModifAgent,
			panelOptionJSon, panelOptionAgent;

	JSplitPane splitPaneHorizontale, splitPaneVerticale;

	JScrollPane scrollJSon, scrollAgent, scrollStatut;

	JTextField textDirectory, textJSon, textAgent;

	JColorTextPane textColorStatut;

	JButton buttonGS, zoomAvantJson, zoomArrJson, zoomAvantAgent, zoomArrAgent,
			addNodeJson, addEdgeJson, suppNodeJson, suppEdgeJson,
			suppNodeAgent, suppEdgeAgent, addNodeAgent, addEdgeAgent,
			structGraphJson, structGraphAgent, zoomCenterJson, zoomCenterAgent,
			cleanGraphJson, cleanGraphAgent, displayJson, displayAgent,
			buttonSave, treeLayoutJson, treeLayoutAgent;

	JMenuBar menu_bar1;

	JMenu menuFile, menuDisplay, menuTools, menuTraces;

	JMenuItem importMenuGauche, importMenuDroite, exitMenu, displayDefault,
			displayUML, displayAutomaton, displayBasic, jMenuItemGenererGraphe,
			jMenuItemGenererTraces1, jMenuItemGenererTraces2;

	Viewer viewerJson, viewerAgent;

	View viewJson, viewAgent;

	Graph graphJson, graphAgent;

	double zoom = 1, dezoom = 1;

	static Double x = null;

	static Double y = null;

	static Double x2 = null;

	static Double y2 = null;

	Double valZoom = 100.00;

	static DecimalFormat df = new DecimalFormat("###.00");

	final int xWindow = 50, yWindow = 0, widthWindow = 1280,
			heightWindow = 700, sizeSeparator = 5;

	boolean isAutoLayoutJson, isAutoLayoutAgent, isGraphJsonLoaded = false,
			isGraphAgentLoaded = false, isDirectoryNeo4j, wantToGenerateToLeft;

	SpriteManager spriteManagerJson, spriteManagerAgent;

	static GraphicElement gElement = null;

	public Window() {
		CustomGraphRenderer.SetRenderer();

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
		panelModifJSon = new JPanel();

		// Initialisation et d�finition du panneau d'ajout de noeuds et de
		// transition droit
		panelModifAgent = new JPanel();

		// Initialisation des bouttons de zoom
		ImageIcon zoomIcon = new ImageIcon(
				"./src/main/resources/buttonsIcons/zoom+.png", "zoom +");
		ImageIcon dezoomIcon = new ImageIcon(
				"./src/main/resources/buttonsIcons/zoom-.png", "zoom -");
		ImageIcon centerIcon = new ImageIcon(
				"./src/main/resources/buttonsIcons/center.png", "center");
		ImageIcon displayIcon = new ImageIcon(
				"./src/main/resources/buttonsIcons/display.png", "display");

		zoomAvantJson = new JButton(zoomIcon);
		zoomAvantJson.setToolTipText(ZOOM_TT);
		zoomAvantJson.setPreferredSize(buttonsSize);
		zoomArrJson = new JButton(dezoomIcon);
		zoomArrJson.setToolTipText(DEZOOM_TT);
		zoomArrJson.setPreferredSize(buttonsSize);
		zoomCenterJson = new JButton(centerIcon);
		zoomCenterJson.setToolTipText(CENTER_TT);
		zoomCenterJson.setPreferredSize(buttonsSize);
		displayJson = new JButton(displayIcon);
		displayJson.setToolTipText(DISPLAY_TT);
		displayJson.setPreferredSize(buttonsSize);

		zoomAvantAgent = new JButton(zoomIcon);
		zoomAvantAgent.setToolTipText(ZOOM_TT);
		zoomAvantAgent.setPreferredSize(buttonsSize);
		zoomArrAgent = new JButton(dezoomIcon);
		zoomArrAgent.setToolTipText(DEZOOM_TT);
		zoomArrAgent.setPreferredSize(buttonsSize);
		zoomCenterAgent = new JButton(centerIcon);
		zoomCenterAgent.setToolTipText(CENTER_TT);
		zoomCenterAgent.setPreferredSize(buttonsSize);
		displayAgent = new JButton(displayIcon);
		displayAgent.setToolTipText(DISPLAY_TT);
		displayAgent.setPreferredSize(buttonsSize);

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
							textColorStatut.appendDoc(INPUT_VALUE_NOT_INTEGER);
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
							textColorStatut.appendDoc(INPUT_VALUE_NOT_INTEGER);
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

		// Initialisation et d�finition du panneau pour zoomer le graphe Json
		panelZoomJSon = new JPanel();
		panelZoomJSon.add(zoomAvantJson);
		panelZoomJSon.add(zoomArrJson);
		panelZoomJSon.add(textJSon);
		panelZoomJSon.add(zoomCenterJson);
		panelZoomJSon.add(displayJson);

		// Initialisation et d�finition du panneau pour zoomer le graphe Agent
		panelZoomAgent = new JPanel();
		panelZoomAgent.add(zoomAvantAgent);
		panelZoomAgent.add(zoomArrAgent);
		panelZoomAgent.add(textAgent);
		panelZoomAgent.add(zoomCenterAgent);
		panelZoomAgent.add(displayAgent);

		// Initialisation des boutons d'option.
		ImageIcon addNodeIcon = new ImageIcon(
				"./src/main/resources/buttonsIcons/node+.png", "node +");
		ImageIcon deleteNodeIcon = new ImageIcon(
				"./src/main/resources/buttonsIcons/node-.png", "node -");
		ImageIcon addEdgeIcon = new ImageIcon(
				"./src/main/resources/buttonsIcons/edge+.png", "edge +");
		ImageIcon deleteEdgeIcon = new ImageIcon(
				"./src/main/resources/buttonsIcons/edge-.png", "edge -");
		ImageIcon cleanIcon = new ImageIcon(
				"./src/main/resources/buttonsIcons/clean.png", "clean");
		ImageIcon saveIcon = new ImageIcon(
				"./src/main/resources/buttonsIcons/save.png", "save");
		ImageIcon treeLayoutIcon = new ImageIcon(
				"./src/main/resources/buttonsIcons/treeLayout.png",
				"tree layout");
		ImageIcon autoLayoutOnIcon = new ImageIcon(
				"./src/main/resources/buttonsIcons/autoLayoutOn.png",
				"automatic layout on");

		addNodeJson = new JButton(addNodeIcon);
		addNodeJson.setToolTipText(ADD_NODE_TT);
		addNodeJson.setPreferredSize(buttonsSize);
		suppNodeJson = new JButton(deleteNodeIcon);
		suppNodeJson.setToolTipText(DELETE_NODE_TT);
		suppNodeJson.setPreferredSize(buttonsSize);
		addEdgeJson = new JButton(addEdgeIcon);
		addEdgeJson.setToolTipText(ADD_EDGE_TT);
		addEdgeJson.setPreferredSize(buttonsSize);
		suppEdgeJson = new JButton(deleteEdgeIcon);
		suppEdgeJson.setToolTipText(DELETE_EDGE_TT);
		suppEdgeJson.setPreferredSize(buttonsSize);
		structGraphJson = new JButton(autoLayoutOnIcon);
		structGraphJson.setToolTipText(AUTO_LAYOUT_ENABLED_TT);
		structGraphJson.setPreferredSize(buttonsSize);
		treeLayoutJson = new JButton(treeLayoutIcon);
		treeLayoutJson.setToolTipText(TREE_LAYOUT_TT);
		treeLayoutJson.setPreferredSize(buttonsSize);
		cleanGraphJson = new JButton(cleanIcon);
		cleanGraphJson.setToolTipText(CLEAN_TT);
		cleanGraphJson.setPreferredSize(buttonsSize);
		buttonSave = new JButton(saveIcon);
		buttonSave.setToolTipText(SAVE_TT);
		buttonSave.setPreferredSize(buttonsSize);

		addNodeAgent = new JButton(addNodeIcon);
		addNodeAgent.setToolTipText(ADD_NODE_TT);
		addNodeAgent.setPreferredSize(buttonsSize);
		suppNodeAgent = new JButton(deleteNodeIcon);
		suppNodeAgent.setToolTipText(DELETE_NODE_TT);
		suppNodeAgent.setPreferredSize(buttonsSize);
		addEdgeAgent = new JButton(addEdgeIcon);
		addEdgeAgent.setToolTipText(ADD_EDGE_TT);
		addEdgeAgent.setPreferredSize(buttonsSize);
		suppEdgeAgent = new JButton(deleteEdgeIcon);
		suppEdgeAgent.setToolTipText(DELETE_EDGE_TT);
		suppEdgeAgent.setPreferredSize(buttonsSize);
		structGraphAgent = new JButton(autoLayoutOnIcon);
		structGraphAgent.setToolTipText(AUTO_LAYOUT_ENABLED_TT);
		structGraphAgent.setPreferredSize(buttonsSize);
		treeLayoutAgent = new JButton(treeLayoutIcon);
		treeLayoutAgent.setToolTipText(TREE_LAYOUT_TT);
		treeLayoutAgent.setPreferredSize(buttonsSize);
		cleanGraphAgent = new JButton(cleanIcon);
		cleanGraphAgent.setToolTipText(CLEAN_TT);
		cleanGraphAgent.setPreferredSize(buttonsSize);

		// Ajout des boutons dans les panneaux respectifs
		panelModifJSon.add(addNodeJson);
		panelModifJSon.add(suppNodeJson);
		panelModifJSon.add(addEdgeJson);
		panelModifJSon.add(suppEdgeJson);
		panelModifJSon.add(structGraphJson);
		panelModifJSon.add(treeLayoutJson);
		panelModifJSon.add(cleanGraphJson);
		panelModifJSon.add(buttonSave);

		panelModifAgent.add(addNodeAgent);
		panelModifAgent.add(suppNodeAgent);
		panelModifAgent.add(addEdgeAgent);
		panelModifAgent.add(suppEdgeAgent);
		panelModifAgent.add(structGraphAgent);
		panelModifAgent.add(treeLayoutAgent);
		panelModifAgent.add(cleanGraphAgent);

		// Initialisation et d�finition panneau option gauche
		panelOptionJSon = new JPanel(new GridLayout(2, 1, 0, 25));
		panelOptionJSon.setPreferredSize(new Dimension(50, 200));
		panelOptionJSon.add(panelZoomJSon);
		panelOptionJSon.add(panelModifJSon);

		// Initialisation et d�finition panneau option droit
		panelOptionAgent = new JPanel(new GridLayout(2, 1, 0, 25));
		panelOptionAgent.setPreferredSize(new Dimension(50, 200));
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

		menuFile = new JMenu("File");
		menuDisplay = new JMenu("Display");
		menuTools = new JMenu("Tools");
		menuTraces = new JMenu("Generate traces");

		importMenuGauche = new JMenuItem("Import to left");
		importMenuDroite = new JMenuItem("Import to right");
		exitMenu = new JMenuItem("Exit");

		displayDefault = new JMenuItem("Default");
		displayUML = new JMenuItem("UML");
		displayAutomaton = new JMenuItem("Automaton");
		displayBasic = new JMenuItem("Basic");

		jMenuItemGenererGraphe = new JMenuItem("Generate graph");
		jMenuItemGenererTraces1 = new JMenuItem("Generate one trace by file");
		jMenuItemGenererTraces2 = new JMenuItem(
				"Generate multiple trace into one file");

		menuFile.add(importMenuGauche);
		menuFile.add(importMenuDroite);
		menuFile.add(exitMenu);
		menuDisplay.add(displayDefault);
		menuDisplay.add(displayUML);
		menuDisplay.add(displayAutomaton);
		menuDisplay.add(displayBasic);

		menuTraces.add(jMenuItemGenererTraces1);
		menuTraces.add(jMenuItemGenererTraces2);
		menuTools.add(jMenuItemGenererGraphe);
		menuTools.add(menuTraces);

		menu_bar1.add(menuFile);
		menu_bar1.add(menuDisplay);
		menu_bar1.add(menuTools);

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

		// Action lors du clic sur l'item "Import to left"
		importMenuGauche.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clickToImport(true);
			}
		});

		// Action lors du clic sur l'item "Import to right"
		importMenuDroite.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clickToImport(false);
			}
		});

		// Action lors du clic sur l'item "Exit"
		exitMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(1);
			}
		});

		// Action lors du clic sur l'item "Generate graph"
		jMenuItemGenererGraphe.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// Ouverture d'une fenetre de dialogue proposant:
				// chemin de sauvegarde, nombre de noeuds,
				// nombre de transition maximum par noeud
				GraphGenerateDialog dialog = new GraphGenerateDialog(
						Window.this);
				dialog.show();

				// Graphe genere avec succes
				if (dialog.isGeneratedWithSuccess()) {
					// Met a jour les a valeurs afin quon puisse appuyer sur
					// "To GraphStream"
					isDirectoryNeo4j = true;
					wantToGenerateToLeft = true;

					graphJson = new ConvertNeo4jToGS(pathGraphTemp)
							.convertToGS();
					textDirectory.setText("ready to load");
					Fichier.deleteFileOrDirectory(pathGraphTemp);
				} else {
					textDirectory.setText("Directory");
				}
			}
		});

		// Action lors du clic sur jMenuItemGenererTraces1
		jMenuItemGenererTraces1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				generateTraces(true);
			}
		});

		// Action lors du clic sur jMenuItemGenererTraces2
		jMenuItemGenererTraces2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				generateTraces(false);
			}
		});

		// Action lors du clic sur l'item "Default"
		displayDefault.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				CustomGraphRenderer.setStyleGraphDefault(graphJson, graphAgent);
			}
		});

		// Action lors du clic sur l'item "UML"
		displayUML.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				CustomGraphRenderer.setStyleGraphUML(graphJson, graphAgent);
			}
		});

		// Action lors du clic sur l'item "Automaton"
		displayAutomaton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				CustomGraphRenderer.setStyleGraphAutomaton(graphJson,
						graphAgent);
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
				// Visualisation du graph g�n�r� par le fichier import�
				// au
				// format .json

				if (!textDirectory.getText().equals("Directory")) {
					try {
						frame.setCursor(Cursor
								.getPredefinedCursor(Cursor.WAIT_CURSOR));
						textJSon.setText("100 %");
						textAgent.setText("100 %");
						turnAutoLayoutButtonOn(structGraphAgent);
						turnAutoLayoutButtonOn(structGraphJson);

						if (wantToGenerateToLeft) {
							if (!isDirectoryNeo4j) {
								graphJson = JsonToGS.generateGraph(
										textDirectory.getText(),
										GRAPH_JSON_NAME);
							}
							initGraphPropertiesJson();
							initPanelGraphJson();
						} else {
							if (!isDirectoryNeo4j) {
								graphAgent = GraphModifier.GraphToGraph(
										graphJson, GRAPH_AGENT_NAME);
							}
							initGraphPropertiesAgent();
							initPanelGraphAgent();
						}

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
					} catch (NullPointerException exception) {
						textColorStatut
								.appendErrorMessage("Graph Neo4j is already open somewhere!");
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
		zoomAvantJson.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				modifZoom(viewJson, textJSon, isGraphJsonLoaded, ZOOM);
			}
		});

		// Action lors du clic sur l'item "-" de la partie gauche
		zoomArrJson.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				modifZoom(viewJson, textJSon, isGraphJsonLoaded, DEZOOM);
			}
		});

		// Action lors du clic sur l'item "Node +" de la partie gauche
		addNodeJson.addActionListener(new ActionListener() {
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
								msgError(NAME_ALREADY_IN_USE);
							}
						} else {
							msgError(NAME_FIELD_EMPTY);
						}
					} else {
						if (!s.equals("")) {
							graphJson = new MultiGraph(GRAPH_JSON_NAME);
							initGraphPropertiesJson();
							initPanelGraphJson();
							GraphModifier.addNode(addNodeJSon, graphJson);
						} else {
							msgError(NAME_FIELD_EMPTY);
						}
					}
				}
			}
		});

		// Action lors du clic sur l'item "Node -" de la
		// partie gauche
		suppNodeJson.addActionListener(new ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				if (isGraphJsonLoaded) {
					SuppNodeDialog suppNodeJSon = new SuppNodeDialog(frame,
							"Suppr Node", graphJson);
					if (!suppNodeJSon.getFerme()) {
						String s = suppNodeJSon.getName();
						textColorStatut.appendDoc(s);
						Node n = graphJson.getNode(s);
						for (Edge edge : n.getEachEdge()) {
							spriteManagerJson.removeSprite(edge.getId());
						}
						n = graphJson.removeNode(s);
						if (n != null) {
							msgAlert(ACTION_ON_NODE + s + ERASE_ACTION);
						}
					}
				} else {
					textColorStatut.appendDoc(NO_NODE_DETECTED);
				}
			}
		});

		// Action lors du clic sur l'item "Edge +" de la partie gauche
		addEdgeJson.addActionListener(new ActionListener() {
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
								msgError(NAME_ALREADY_IN_USE);
							}
						} else {
							msgError(NAME_FIELD_EMPTY);
						}
					}
				} else {
					textColorStatut.appendDoc(NO_NODE_DETECTED);
				}
			}
		});

		// Action lors du clic sur l'item "Edge -" de la
		// partie gauche
		suppEdgeJson.addActionListener(new ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				if (isGraphJsonLoaded) {
					SuppEdgeDialog suppEdgeJSon = new SuppEdgeDialog(frame,
							"Suppr Edge", graphJson);
					if (!suppEdgeJSon.getFerme()) {
						String s = suppEdgeJSon.getName();
						Edge e = graphJson.removeEdge(s);
						spriteManagerJson.removeSprite(e.getId());
						if (e != null) {
							msgAlert(ACTION_ON_EDGE + s + ERASE_ACTION);
						}
					}
				} else {
					textColorStatut.appendDoc(NO_EDGE_DETECTED);
				}
			}
		});

		// Action lors du clic sur l'item "Auto Layout" de la
		// partie gauche
		structGraphJson.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (isGraphJsonLoaded) {
					if (isAutoLayoutJson) {
						turnAutoLayoutButtonOff(structGraphJson);
						viewerJson.disableAutoLayout();
						isAutoLayoutJson = false;
					} else {
						turnAutoLayoutButtonOn(structGraphJson);
						viewerJson.enableAutoLayout();
						isAutoLayoutJson = true;
					}
				}
			}
		});

		// Action lors du clic sur l'item "Tree Layout" de la partie gauche
		treeLayoutJson.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (isGraphJsonLoaded) {
					CustomGraphRenderer.setTreeLayout(graphJson, viewerJson);
					turnAutoLayoutButtonOff(structGraphJson);
					isAutoLayoutJson = false;
				}
			}
		});

		// Action lors du clic sur l'item "Sauvegarder" (partie gauche)
		buttonSave.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (isGraphJsonLoaded) {
					JFileChooser jFileChooser = new JFileChooser(new File(
							"./src/test/resources"));
					jFileChooser
							.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

					String path;
					if (jFileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {

						File file = jFileChooser.getSelectedFile();
						path = file.toString();

						if (file.exists()) {
							int option = JOptionPane.showConfirmDialog(
									null,
									(Fichier.isFolderNeo4j(path) ? "It seems "
											: "It doesn't seems ")
											+ "that the selected folder is a neo4J's folder"
											+ "\nWould you like to save in this folder ?"
											+ "\n(the folder will be erased).",
									"Save", JOptionPane.YES_NO_OPTION);

							if (option == JOptionPane.OK_OPTION) {
								// SAUVEGARDE
								ajouterMessageToTextColorStatut("graph is saving");
								new ConvertGStoNeo4j(graphJson)
										.convertToNeo4j(path);
								ajouterMessageToTextColorStatut("Graph saved ! ("
										+ path + ").");
							} else {
								ajouterMessageToTextColorStatut("Graph didn't save !");
							}
						} else {
							// SAUVEGARDE
							ajouterMessageToTextColorStatut("graph is saving");
							new ConvertGStoNeo4j(graphJson)
									.convertToNeo4j(path);
							ajouterMessageToTextColorStatut("Graph saved ! ("
									+ path + ").");
						}
					} else if (jFileChooser.getSelectedFile() == null) {
						ajouterMessageToTextColorStatut("Error: A file has the same name.");
					}
				} else {
					ajouterMessageToTextColorStatut("No graph to save.");
				}
			}
		});

		// Action lors du clic sur l'item "Center" de la partie gauche
		zoomCenterJson.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				viewJson.getCamera().resetView();
				textJSon.setText("100 %");
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
					textColorStatut.appendDoc(NO_GRAPH_GENERATED);
				}
			}
		});

		// Action lors du clic sur l'item "Display" de la partie gauche
		// TODO G�rez la view sur le nouvel affichage du graphe
		displayJson.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (isGraphJsonLoaded) {
					/* WindowDisplay windowJson = */new WindowDisplay(
							"Graph Json", viewJson, viewerJson, graphJson);
				} else {
					textColorStatut.appendDoc(NO_GRAPH_GENERATED);
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
							spriteManagerAgent.removeSprite(edge.getId());
						}
						n = graphAgent.removeNode(s);
						if (n != null) {
							msgAlert(ACTION_ON_NODE + s + ERASE_ACTION);
						}
					}
				} else {
					textColorStatut.appendDoc(NO_NODE_DETECTED);
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
					textColorStatut.appendDoc(NO_NODE_DETECTED);
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
						Edge e = graphAgent.removeEdge(s);
						spriteManagerAgent.removeSprite(e.getId());
						if (e != null) {
							msgAlert(ACTION_ON_EDGE + s + ERASE_ACTION);
						}
					}
				} else {
					textColorStatut.appendDoc(NO_EDGE_DETECTED);
				}
			}
		});

		// Action lors du clic sur l'item "Structurer / D�structurer" de la
		// partie droite
		structGraphAgent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (isGraphAgentLoaded) {
					if (isAutoLayoutAgent) {
						turnAutoLayoutButtonOff(structGraphAgent);
						viewerAgent.disableAutoLayout();
						isAutoLayoutAgent = false;
					} else {
						turnAutoLayoutButtonOn(structGraphAgent);
						viewerAgent.enableAutoLayout();
						isAutoLayoutAgent = true;
					}
				}
			}
		});

		// Action lors du clic sur l'item "Tree Layout" de la partie droite
		treeLayoutAgent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (isAutoLayoutAgent) {
					CustomGraphRenderer.setTreeLayout(graphAgent, viewerAgent);
					turnAutoLayoutButtonOff(structGraphAgent);
					isAutoLayoutAgent = false;
				}
			}
		});

		// Action lors du clic sur l'item "Center" de la partie droite
		zoomCenterAgent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				viewAgent.getCamera().resetView();
				textAgent.setText("100 %");
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
					textColorStatut.appendDoc(NO_GRAPH_GENERATED);
				}
			}
		});

		// Action lors du clic sur l'item "Display" de la partie droite
		// TODO G�rez la view sur le nouvel affichage du graphe
		displayAgent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (isGraphAgentLoaded) {
					/* WindowDisplay windowAgent = */new WindowDisplay(
							"Graph Agent", viewAgent, viewerAgent, graphAgent);
				} else {
					textColorStatut.appendDoc(NO_GRAPH_GENERATED);
				}
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
				.setDividerLocation((widthWindow - sizeSeparator + 350) / 3);

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

		// Redefinition de la fermeture de la fenetre
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				Fichier.deleteFileOrDirectory(pathGraphTemp);
				super.windowClosing(e);
			}
		});

		// Centrage de la fenetre
		pack();
		frame.setLocationRelativeTo(null);
	}

	// clique sur le bouton "import" nimporte lequel
	public void clickToImport(boolean importToLeft) {
		wantToGenerateToLeft = importToLeft;

		// TODO changer le chemin d'acces lors de la release
		JFileChooser dialogue = new JFileChooser(new File(
				"./src/test/resources/jsonAndGSTest"));
		dialogue.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		File fichier;

		isDirectoryNeo4j = false;
		if (dialogue.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {

			// Recupere le fichier a importer
			fichier = dialogue.getSelectedFile();
			textDirectory.setText(fichier.toString());

			isDirectoryNeo4j = Fichier.isFolderNeo4j(fichier.toString());

			// Si cest un dossier et que cest pas un neo4j
			if (fichier.isDirectory() && !isDirectoryNeo4j) {
				JOptionPane.showConfirmDialog(null, "It's not a Neo4J folder",
						"Import Problem", JOptionPane.CLOSED_OPTION);

				textDirectory.setText("Directory");
			} else {
				// sinon IMPORTER NEO4J
				ajouterMessageToTextColorStatut("graph is importing ...");

				if (wantToGenerateToLeft) {
					graphJson = new ConvertNeo4jToGS(fichier.toString())
							.convertToGS();
				} else {
					graphAgent = new ConvertNeo4jToGS(fichier.toString())
							.convertToGS();
				}
			}
		}
	}

	// clique sur generate Traces (nimporte lequel)
	public void generateTraces(boolean oneByFile) {
		ajouterMessageToTextColorStatut("traces Generate Dialog Opening ...");

		TracesGenerateDialog dialog = new TracesGenerateDialog(this, oneByFile,
				graphJson, isGraphJsonLoaded);
		dialog.show();
	}

	public static void setListenerOnViewer(final Viewer viewer,
			final Graph graph, final JTextField jTextField,
			final boolean isGraphLoaded) {
		// Action lors du d�placement de la souris sur le graphe
		final View view = viewer.getDefaultView();
		final JComponent jCompView = (JComponent) view;
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Image image = toolkit
				.getImage("./src/main/resources/mouseIcons/Drag_Hand.png");
		Point hotSpot = new Point(0, 0);
		final Cursor cursor = toolkit.createCustomCursor(image, hotSpot,
				"drag_hand");

		viewer.getDefaultView().addMouseMotionListener(
				new MouseMotionListener() {

					public void mouseMoved(MouseEvent e) {
						String s;
						GraphicElement gElem = findNodeOrSpriteAtWithTolerance(
								e, view);
						if (gElem instanceof GraphicNode) {
							s = getNodeInformations(gElem, graph);
							if ((jCompView.getToolTipText() == null || !jCompView
									.getToolTipText().equals(s))) {
								jCompView.setToolTipText(s);
								view.display(viewer.getGraphicGraph(), true);
							}
						} else if (gElem instanceof GraphicSprite) {
							s = getEdgeInformations(gElem, graph);
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

	public static GraphicElement findNodeOrSpriteAtWithTolerance(MouseEvent e,
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

	public static void modifZoom(View view, JTextField jTextField,
			boolean isGraphLoaded, double modif) {
		double zoom, valZoom;
		if (isGraphLoaded) {
			zoom = view.getCamera().getViewPercent();
			view.getCamera().setViewPercent(zoom + modif);
			valZoom = view.getCamera().getViewPercent() * 100;
			jTextField.setText(df.format(valZoom) + " %");
		}
	}

	public static String getNodeInformations(GraphicElement gElem, Graph graph) {
		String s = "<html>", fieldName, attValue = "";
		String idNode = gElem.getId();
		Node node = graph.getNode(idNode);

		fieldName = MyJsonGenerator.FORMAT_NODE_NAME;
		s += fieldName + " : " + node.getId() + "<br/>";
		fieldName = MyJsonGenerator.FORMAT_NODE_SOURCE;
		s += fieldName + " : " + node.getAttribute(fieldName) + "<br/>";
		fieldName = MyJsonGenerator.FORMAT_NODE_FINAL;
		s += fieldName + " : " + node.getAttribute(fieldName) + "<br/>";
		for (String attKey : node.getAttributeKeySet()) {
			if (attKey != MyJsonGenerator.FORMAT_NODE_NAME
					&& attKey != MyJsonGenerator.FORMAT_NODE_SOURCE
					&& attKey != MyJsonGenerator.FORMAT_NODE_FINAL
					&& !attKey.startsWith("ui.")) {
				attValue = node.getAttribute(attKey);
				s += attKey + " : " + attValue + "<br/>";
			}
		}
		s += "</html>";
		return s;
	}

	public static String getEdgeInformations(GraphicElement gElem, Graph graph) {
		String s = "<html>", fieldName, attValue = "";
		String idSprite = gElem.getId();
		Edge edge = graph.getEdge(idSprite);

		fieldName = MyJsonGenerator.FORMAT_EDGE_LABEL;
		s += fieldName + " : " + edge.getId() + "<br/>";
		fieldName = MyJsonGenerator.FORMAT_EDGE_ACTION;
		s += fieldName + " : " + edge.getAttribute("ui.label") + "<br/>";
		fieldName = MyJsonGenerator.FORMAT_EDGE_BEGIN_NODE;
		s += fieldName + " : " + edge.getSourceNode() + "<br/>";
		fieldName = MyJsonGenerator.FORMAT_EDGE_END_NODE;
		s += fieldName + " : " + edge.getTargetNode() + "<br/>";
		for (String attKey : edge.getAttributeKeySet()) {
			if (attKey != MyJsonGenerator.FORMAT_EDGE_LABEL
					&& attKey != MyJsonGenerator.FORMAT_EDGE_ACTION
					&& attKey != MyJsonGenerator.FORMAT_EDGE_BEGIN_NODE
					&& attKey != MyJsonGenerator.FORMAT_EDGE_END_NODE
					&& !attKey.startsWith("ui.")) {
				attValue = edge.getAttribute(attKey);
				s += attKey + " : " + attValue + "<br/>";
			}
		}
		s += "</html>";
		return s;
	}

	public void turnAutoLayoutButtonOn(JButton button) {
		ImageIcon autoLayoutOnIcon = new ImageIcon(
				"./src/main/resources/buttonsIcons/autoLayoutOn.png",
				"automatic layout on");
		button.setIcon(autoLayoutOnIcon);
		button.setToolTipText(AUTO_LAYOUT_ENABLED_TT);
	}

	public void turnAutoLayoutButtonOff(JButton button) {
		ImageIcon autoLayoutOffIcon = new ImageIcon(
				"./src/main/resources/buttonsIcons/autoLayoutOff.png",
				"automatic layout on");
		button.setIcon(autoLayoutOffIcon);
		button.setToolTipText(AUTO_LAYOUT_DISABLED_TT);
	}

	private void msgError(String s) {
		JOptionPane.showMessageDialog(this, s, "Error",
				JOptionPane.ERROR_MESSAGE);
	}

	private void msgAlert(String s) {
		JOptionPane.showMessageDialog(this, s, "Alert",
				JOptionPane.INFORMATION_MESSAGE);
	}

	/**
	 * Ajoute un message dans la barre de statut.
	 * 
	 * @param msg
	 *            Message a ecrire.
	 */
	public void ajouterMessageToTextColorStatut(final String msg) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				Window.this.textColorStatut.appendDoc(msg);
			}
		}).start();
	}

	public static void main(String[] args) {
		new Window();
	}

}
