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
import java.util.Collection;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
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
import javax.swing.border.EtchedBorder;

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

	public final static String pathGraphTemp = "../View/src/test/resources/.grapheTemporaire";

	public static final String NO_FILE_SELECTED = "You have to select a file to import";
	public static final String INPUT_VALUE_NOT_INTEGER = "You have to enter an integer \n";
	public static final String NAME_FIELD_EMPTY = "You have to fill the name field";
	public static final String NAME_ALREADY_IN_USE = "This name is already in use, please choose an other one";
	public static final String NO_GRAPH_GENERATED = "You have to generate a graph before";
	public static final String NO_NODE_DETECTED = "You have to generate a graph or add a node before";
	public static final String NO_EDGE_DETECTED = "You have to generate a graph or add an edge before";
	public static final String ACTION_ON_NODE = "The node ";
	public static final String ACTION_ON_EDGE = "The edge ";
	public static final String ERASE_ACTION = " has been erased";
	public static final String GRAPH_JSON_NAME = "graphJson";
	public static final String GRAPH_AGENT_NAME = "graphAgent";

	public static final String ZOOM_TT = "Zoom on the view";
	public static final String DEZOOM_TT = "Dezoom on the view";
	public static final String CENTER_TT = "Center the view";
	public static final String DISPLAY_TT = "Display the graph in an other window";
	public static final String EDGE_DISPLAY_TT = "Change the text wich is attached to the edge";
	public static final String ADD_NODE_TT = "Add a node";
	public static final String DELETE_NODE_TT = "Delete a node";
	public static final String MODIF_NODE_TT = "Modify a node";
	public static final String ADD_EDGE_TT = "Add an edge";
	public static final String DELETE_EDGE_TT = "Delete an edge";
	public static final String MODIF_EDGE_TT = "Modify an edge";
	public static final String AUTO_LAYOUT_ENABLED_TT = "Turn off the automatic layout";
	public static final String AUTO_LAYOUT_DISABLED_TT = "Turn on the automatic layout";
	public static final String TREE_LAYOUT_TT = "Apply a tree layout";
	public static final String CLEAN_TT = "Clean the view";
	public static final String SAVE_TT = "Save the graph";

	public static final Dimension buttonsSize = new Dimension(25, 25);

	public static final double tolerance = 10;
	public static final double ZOOM = -0.1;
	public static final double DEZOOM = 0.1;

	// Instanciation des diff�rents Components

	private static JFrame frame;

	private JPanel panelFile, panelGraph, panelZoomJson, panelZoomAgent,
			panelModifJson, panelModifAgent, panelOptionJSon, panelOptionAgent;

	private static JPanel panelGraphJson, panelGraphAgent;

	private JSplitPane splitPaneHorizontale, splitPaneVerticale;

	private static JScrollPane scrollJSon, scrollAgent;

	private JScrollPane scrollStatut;

	private JTextField textDirectory;

	private static JTextField textJson, textAgent;

	private static JColorTextPane textColorStatut;

	private JButton buttonGS, zoomAvantJson, zoomArrJson, zoomCenterJson,
			displayJson, changeEdgeDisplayJson, addNodeJson, deleteNodeJson,
			modifyNodeJson, addEdgeJson, deleteEdgeJson, modifyEdgeJson,
			structGraphJson, treeLayoutJson, cleanGraphJson, buttonSave,
			zoomAvantAgent, zoomArrAgent, zoomCenterAgent, displayAgent,
			changeEdgeDisplayAgent, addNodeAgent, deleteNodeAgent,
			modifNodeAgent, addEdgeAgent, deleteEdgeAgent, modifEdgeAgent,
			structGraphAgent, treeLayoutAgent, cleanGraphAgent;

	private JMenuBar menu_bar1;

	private JMenu menuFile, menuDisplay, menuTools, menuTraces;

	private JMenuItem importLeftMenu, importRightMenu, exitMenu,
			displayDefault, displayUML, displayAutomaton, displayBasic,
			jMenuItemGenererGraphe, jMenuItemGenererTraces1,
			jMenuItemGenererTraces2;

	private static Viewer viewerJson, viewerAgent;

	private static View viewJson, viewAgent;

	private static Graph graphJson, graphAgent;

	private static Double x = null, y = null, x2 = null, y2 = null;

	private static int valZoomJson = 100, valZoomAgent = 100;

	private final int xWindow = 50, yWindow = 0, widthWindow = 1280,
			heightWindow = 700, sizeSeparator = 5;

	private static boolean isAutoLayoutJson, isAutoLayoutAgent,
			isGraphJsonLoaded = false, isGraphAgentLoaded = false,
			isDirectoryNeo4j, wantToGenerateToLeft, isButton1Dragging = false;
	private static SpriteManager spriteManagerJson, spriteManagerAgent;

	private static GraphicElement gElement = null;

	private static String currentEdgeDisplay = MyJsonGenerator.FORMAT_EDGE_ACTION;

	public Window() {
		CustomGraphRenderer.SetRenderer();

		// Initialisation de la fen�tre principale
		frame = new JFrame("Test Interface");

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

		// Initialisation des bouttons de zoom
		ImageIcon zoomIcon = new ImageIcon(
				"../View/src/main/resources/buttonsIcons/zoom+.png", "zoom +");
		ImageIcon dezoomIcon = new ImageIcon(
				"../View/src/main/resources/buttonsIcons/zoom-.png", "zoom -");
		ImageIcon centerIcon = new ImageIcon(
				"../View/src/main/resources/buttonsIcons/center.png", "center");
		ImageIcon displayIcon = new ImageIcon(
				"../View/src/main/resources/buttonsIcons/display.png", "display");
		ImageIcon edgeDisplayIcon = new ImageIcon(
				"../View/src/main/resources/buttonsIcons/edgeDisplay.png",
				"edge display");

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
		changeEdgeDisplayJson = new JButton(edgeDisplayIcon);
		changeEdgeDisplayJson.setToolTipText(EDGE_DISPLAY_TT);
		changeEdgeDisplayJson.setPreferredSize(buttonsSize);

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
		changeEdgeDisplayAgent = new JButton(edgeDisplayIcon);
		changeEdgeDisplayAgent.setToolTipText(EDGE_DISPLAY_TT);
		changeEdgeDisplayAgent.setPreferredSize(buttonsSize);

		// initialisation de la zone de texte pour le pourcentage de zoom
		textJson = new JTextField();
		textJson.setText(valZoomJson + " %");

		textAgent = new JTextField();
		textAgent.setText(valZoomAgent + " %");

		// Initialisation et d�finition du panneau pour zoomer le graphe Json
		panelZoomJson = new JPanel();
		panelZoomJson.setMaximumSize(new Dimension(50, 2000));
		panelZoomJson.setBorder(BorderFactory
				.createEtchedBorder(EtchedBorder.LOWERED));
		panelZoomJson.add(zoomAvantJson);
		panelZoomJson.add(zoomArrJson);
		panelZoomJson.add(textJson);
		panelZoomJson.add(zoomCenterJson);
		panelZoomJson.add(displayJson);
		panelZoomJson.add(changeEdgeDisplayJson);

		// Initialisation et d�finition du panneau pour zoomer le graphe Agent
		panelZoomAgent = new JPanel();
		panelZoomAgent.setMaximumSize(new Dimension(50, 2000));
		panelZoomAgent.setBorder(BorderFactory
				.createEtchedBorder(EtchedBorder.LOWERED));
		panelZoomAgent.add(zoomAvantAgent);
		panelZoomAgent.add(zoomArrAgent);
		panelZoomAgent.add(textAgent);
		panelZoomAgent.add(zoomCenterAgent);
		panelZoomAgent.add(displayAgent);
		panelZoomAgent.add(changeEdgeDisplayAgent);

		// Initialisation des boutons d'option.
		ImageIcon addNodeIcon = new ImageIcon(
				"../View/src/main/resources/buttonsIcons/node+.png", "node +");
		ImageIcon deleteNodeIcon = new ImageIcon(
				"../View/src/main/resources/buttonsIcons/node-.png", "node -");
		ImageIcon modifNodeIcon = new ImageIcon(
				"../View/src/main/resources/buttonsIcons/nodeModif.png", "node modif");
		ImageIcon addEdgeIcon = new ImageIcon(
				"../View/src/main/resources/buttonsIcons/edge+.png", "edge +");
		ImageIcon deleteEdgeIcon = new ImageIcon(
				"../View/src/main/resources/buttonsIcons/edge-.png", "edge -");
		ImageIcon modifEdgeIcon = new ImageIcon(
				"../View/src/main/resources/buttonsIcons/edgeModif.png", "edge modif");
		ImageIcon autoLayoutOnIcon = new ImageIcon(
				"../View/src/main/resources/buttonsIcons/autoLayoutOn.png",
				"automatic layout on");
		ImageIcon treeLayoutIcon = new ImageIcon(
				"../View/src/main/resources/buttonsIcons/treeLayout.png",
				"tree layout");
		ImageIcon cleanIcon = new ImageIcon(
				"../View/src/main/resources/buttonsIcons/clean.png", "clean");
		ImageIcon saveIcon = new ImageIcon(
				"../View/src/main/resources/buttonsIcons/save.png", "save");

		addNodeJson = new JButton(addNodeIcon);
		addNodeJson.setToolTipText(ADD_NODE_TT);
		addNodeJson.setPreferredSize(buttonsSize);
		deleteNodeJson = new JButton(deleteNodeIcon);
		deleteNodeJson.setToolTipText(DELETE_NODE_TT);
		deleteNodeJson.setPreferredSize(buttonsSize);
		modifyNodeJson = new JButton(modifNodeIcon);
		modifyNodeJson.setToolTipText(MODIF_NODE_TT);
		modifyNodeJson.setPreferredSize(buttonsSize);
		addEdgeJson = new JButton(addEdgeIcon);
		addEdgeJson.setToolTipText(ADD_EDGE_TT);
		addEdgeJson.setPreferredSize(buttonsSize);
		deleteEdgeJson = new JButton(deleteEdgeIcon);
		deleteEdgeJson.setToolTipText(DELETE_EDGE_TT);
		deleteEdgeJson.setPreferredSize(buttonsSize);
		modifyEdgeJson = new JButton(modifEdgeIcon);
		modifyEdgeJson.setToolTipText(MODIF_EDGE_TT);
		modifyEdgeJson.setPreferredSize(buttonsSize);
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
		deleteNodeAgent = new JButton(deleteNodeIcon);
		deleteNodeAgent.setToolTipText(DELETE_NODE_TT);
		deleteNodeAgent.setPreferredSize(buttonsSize);
		modifNodeAgent = new JButton(modifNodeIcon);
		modifNodeAgent.setToolTipText(MODIF_NODE_TT);
		modifNodeAgent.setPreferredSize(buttonsSize);
		addEdgeAgent = new JButton(addEdgeIcon);
		addEdgeAgent.setToolTipText(ADD_EDGE_TT);
		addEdgeAgent.setPreferredSize(buttonsSize);
		deleteEdgeAgent = new JButton(deleteEdgeIcon);
		deleteEdgeAgent.setToolTipText(DELETE_EDGE_TT);
		deleteEdgeAgent.setPreferredSize(buttonsSize);
		modifEdgeAgent = new JButton(modifEdgeIcon);
		modifEdgeAgent.setToolTipText(MODIF_EDGE_TT);
		modifEdgeAgent.setPreferredSize(buttonsSize);
		structGraphAgent = new JButton(autoLayoutOnIcon);
		structGraphAgent.setToolTipText(AUTO_LAYOUT_ENABLED_TT);
		structGraphAgent.setPreferredSize(buttonsSize);
		treeLayoutAgent = new JButton(treeLayoutIcon);
		treeLayoutAgent.setToolTipText(TREE_LAYOUT_TT);
		treeLayoutAgent.setPreferredSize(buttonsSize);
		cleanGraphAgent = new JButton(cleanIcon);
		cleanGraphAgent.setToolTipText(CLEAN_TT);
		cleanGraphAgent.setPreferredSize(buttonsSize);

		// Initialisation et d�finition du panneau d'ajout de noeuds et de
		// transition gauche
		panelModifJson = new JPanel();
		panelModifJson.setMaximumSize(new Dimension(50, 3800));
		panelModifJson.setBorder(BorderFactory
				.createEtchedBorder(EtchedBorder.LOWERED));
		panelModifJson.add(addNodeJson);
		panelModifJson.add(deleteNodeJson);
		panelModifJson.add(modifyNodeJson);
		panelModifJson.add(addEdgeJson);
		panelModifJson.add(deleteEdgeJson);
		panelModifJson.add(modifyEdgeJson);
		panelModifJson.add(structGraphJson);
		panelModifJson.add(treeLayoutJson);
		panelModifJson.add(cleanGraphJson);
		panelModifJson.add(buttonSave);

		// Initialisation et d�finition du panneau d'ajout de noeuds et de
		// transition droit
		panelModifAgent = new JPanel();
		panelModifAgent.setMaximumSize(new Dimension(50, 3800));
		panelModifAgent.setBorder(BorderFactory
				.createEtchedBorder(EtchedBorder.LOWERED));
		panelModifAgent.add(addNodeAgent);
		panelModifAgent.add(deleteNodeAgent);
		panelModifAgent.add(modifNodeAgent);
		panelModifAgent.add(addEdgeAgent);
		panelModifAgent.add(deleteEdgeAgent);
		panelModifAgent.add(modifEdgeAgent);
		panelModifAgent.add(structGraphAgent);
		panelModifAgent.add(treeLayoutAgent);
		panelModifAgent.add(cleanGraphAgent);

		// Initialisation et d�finition panneau option gauche
		panelOptionJSon = new JPanel();
		panelOptionJSon.setLayout(new BoxLayout(panelOptionJSon,
				BoxLayout.PAGE_AXIS));
		panelOptionJSon.setPreferredSize(new Dimension(50, 200));
		panelOptionJSon.add(panelZoomJson);
		panelOptionJSon.add(panelModifJson);

		// Initialisation et d�finition panneau option droit
		panelOptionAgent = new JPanel();
		panelOptionAgent.setLayout(new BoxLayout(panelOptionAgent,
				BoxLayout.PAGE_AXIS));
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

		importLeftMenu = new JMenuItem("Import to left");
		importRightMenu = new JMenuItem("Import to right");
		exitMenu = new JMenuItem("Exit");

		displayDefault = new JMenuItem("Default");
		displayUML = new JMenuItem("UML");
		displayAutomaton = new JMenuItem("Automaton");
		displayBasic = new JMenuItem("Basic");

		jMenuItemGenererGraphe = new JMenuItem("Generate graph");
		menuTraces = new JMenu("Generate traces");
		jMenuItemGenererTraces1 = new JMenuItem("Generate one trace by file");
		jMenuItemGenererTraces2 = new JMenuItem(
				"Generate multiple trace into one file");

		menuFile.add(importLeftMenu);
		menuFile.add(importRightMenu);
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
		panelGraphJson = new JPanel();
		panelGraphAgent = new JPanel();

		// Action lors du clic sur l'item "Import to left"
		importLeftMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clickToImport(true);
			}
		});

		// Action lors du clic sur l'item "Import to right"
		importRightMenu.addActionListener(new ActionListener() {
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

		// Action lors du clic sur l'item "Generate graph"
		jMenuItemGenererGraphe.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// Ouverture d'une fenetre de dialogue proposant:
				// chemin de sauvegarde, nombre de noeuds,
				// nombre de transition maximum par noeud
				GraphGenerateDialog dialog = new GraphGenerateDialog(
						Window.this);
				dialog.setVisible(true);

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

		// Action lors du clic sur l'item "To GraphStream"
		buttonGS.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Visualisation du graph g�n�r� par le fichier import�
				// au format .json

				if (!textDirectory.getText().equals("Directory")) {
					try {
						frame.setCursor(Cursor
								.getPredefinedCursor(Cursor.WAIT_CURSOR));
						textJson.setText("100 %");
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
								graphAgent = JsonToGS.generateGraph(
										textDirectory.getText(),
										GRAPH_AGENT_NAME);
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

		// Action lors du clic sur l'item "Zoom +" de la partie gauche
		zoomAvantJson.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				modifZoom(viewJson, textJson, isGraphJsonLoaded, ZOOM);
			}
		});

		// Action lors du clic sur l'item "Zoom -" de la partie gauche
		zoomArrJson.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				modifZoom(viewJson, textJson, isGraphJsonLoaded, DEZOOM);
			}
		});

		// Action lors de l'appui sur la touche entr�e dans la zone de zoom de
		// la partie gauche
		textJson.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent evt) {
				enterNewZoomValue(evt, isGraphJsonLoaded, textJson, viewJson);
			}

			public void keyReleased(KeyEvent evt) {
			}

			public void keyTyped(KeyEvent evt) {
			}

		});

		// Action lors du clic sur l'item "Center" de la partie gauche
		zoomCenterJson.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				centerView(isGraphJsonLoaded, viewJson, textJson);
			}
		});

		// Action lors du clic sur l'item "Display" de la partie gauche
		displayJson.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setDisplay(isGraphJsonLoaded, viewerJson, graphJson,
						GRAPH_JSON_NAME);
			}
		});

		// Action lors du clic sur l'item "change edge display" de la partie
		// gauche
		changeEdgeDisplayJson.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				changeDisplayEdge(frame, isGraphJsonLoaded, graphJson);
			}
		});

		// Action lors du clic sur l'item "Node +" de la partie gauche
		addNodeJson.addActionListener(new ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				addNode(frame, isGraphJsonLoaded, graphJson, GRAPH_JSON_NAME);
			}
		});

		// Action lors du clic sur l'item "Node -" de la
		// partie gauche
		deleteNodeJson.addActionListener(new ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				deleteNode(frame, isGraphJsonLoaded, graphJson,
						spriteManagerJson);
			}
		});

		// Action lors du clic sur l'item "modify node" de la partie gauche
		modifyNodeJson.addActionListener(new ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				selectNode(frame, isGraphJsonLoaded, graphJson);
			}
		});

		// Action lors du clic sur l'item "Edge +" de la partie gauche
		addEdgeJson.addActionListener(new ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				addEdge(frame, isGraphJsonLoaded, graphJson, spriteManagerJson);
			}
		});

		// Action lors du clic sur l'item "Edge -" de la
		// partie gauche
		deleteEdgeJson.addActionListener(new ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				deleteEdge(frame, isGraphJsonLoaded, graphJson,
						spriteManagerJson);
			}
		});

		// Action lors du clic sur l'item "modify edge" de la partie gauche
		modifyEdgeJson.addActionListener(new ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				selectEdge(frame, isGraphJsonLoaded, graphJson);
			}
		});

		// Action lors du clic sur l'item "Automatic Layout" de la
		// partie gauche
		structGraphJson.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				autoLayout(isGraphJsonLoaded, isAutoLayoutJson,
						GRAPH_JSON_NAME, viewerJson, structGraphJson);
			}
		});

		// Action lors du clic sur l'item "Tree Layout" de la partie gauche
		treeLayoutJson.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setTreeLayout(isGraphJsonLoaded, graphJson, viewerJson,
						structGraphJson, GRAPH_JSON_NAME);
			}
		});

		// Action lors du clic sur l'item "Clean" de la partie gauche
		cleanGraphJson.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cleanView(isGraphJsonLoaded, panelGraphJson, GRAPH_JSON_NAME);
			}
		});

		// Action lors du clic sur l'item "Sauvegarder" (partie gauche)
		buttonSave.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (isGraphJsonLoaded) {
					JFileChooser jFileChooser = new JFileChooser(new File(
							"../View/src/test/resources"));
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

		// Action lors du clic sur l'item "Zoom +" de la partie droite
		zoomAvantAgent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				modifZoom(viewAgent, textAgent, isGraphAgentLoaded, ZOOM);
			}
		});

		// Action lors du clic sur l'item "Zoom -" de la partie droite
		zoomArrAgent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				modifZoom(viewAgent, textAgent, isGraphAgentLoaded, DEZOOM);
			}
		});

		// Action lors de l'appui sur la touche entr�e dans la zone de zoom de
		// la partie droite
		textAgent.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent evt) {
				enterNewZoomValue(evt, isGraphAgentLoaded, textAgent, viewAgent);
			}

			public void keyReleased(KeyEvent evt) {
			}

			public void keyTyped(KeyEvent evt) {
			}

		});

		// Action lors du clic sur l'item "Center" de la partie droite
		zoomCenterAgent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				centerView(isGraphAgentLoaded, viewAgent, textAgent);
			}
		});

		// Action lors du clic sur l'item "Display" de la partie droite
		displayAgent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setDisplay(isGraphAgentLoaded, viewerAgent, graphAgent,
						GRAPH_JSON_NAME);
			}
		});

		// Action lors du clic sur l'item "change edge display" de la partie
		// droite
		changeEdgeDisplayAgent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				changeDisplayEdge(frame, isGraphAgentLoaded, graphAgent);
			}
		});

		// Action lors du clic sur l'item "Node +" de la partie droite
		addNodeAgent.addActionListener(new ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				addNode(frame, isGraphAgentLoaded, graphAgent, GRAPH_AGENT_NAME);
			}
		});

		// Action lors du clic sur l'item "Node -" de la
		// partie droite
		deleteNodeAgent.addActionListener(new ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				deleteNode(frame, isGraphAgentLoaded, graphAgent,
						spriteManagerAgent);
			}
		});

		// Action lors du clic sur l'item "modify node" de la partie droite
		modifNodeAgent.addActionListener(new ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				selectNode(frame, isGraphAgentLoaded, graphAgent);
			}
		});

		// Action lors du clic sur l'item "Edge +" de la partie droite
		addEdgeAgent.addActionListener(new ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				addEdge(frame, isGraphAgentLoaded, graphAgent,
						spriteManagerAgent);
			}
		});

		// Action lors du clic sur l'item "Edge -" de la
		// partie droite
		deleteEdgeAgent.addActionListener(new ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				deleteEdge(frame, isGraphAgentLoaded, graphAgent,
						spriteManagerAgent);
			}
		});

		// Action lors du clic sur l'item "modify edge" de la partie droite
		modifEdgeAgent.addActionListener(new ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				selectEdge(frame, isGraphAgentLoaded, graphAgent);
			}
		});

		// Action lors du clic sur l'item "Automatic layout" de la
		// partie droite
		structGraphAgent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				autoLayout(isGraphAgentLoaded, isAutoLayoutAgent,
						GRAPH_AGENT_NAME, viewerAgent, structGraphAgent);
			}
		});

		// Action lors du clic sur l'item "Tree Layout" de la partie droite
		treeLayoutAgent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setTreeLayout(isGraphAgentLoaded, graphAgent, viewerAgent,
						structGraphAgent, GRAPH_AGENT_NAME);
			}
		});

		// Action lors du clic sur l'item "Clean" de la partie droite
		cleanGraphAgent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cleanView(isGraphAgentLoaded, panelGraphAgent, GRAPH_AGENT_NAME);
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
				"../View/src/test/resources/jsonAndGSTest"));
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

	public static void setListenerOnViewer(final JFrame frame,
			final Viewer viewer, final Graph graph,
			final JTextField jTextField, final boolean isGraphLoaded) {
		// Action lors du d�placement de la souris sur le graphe
		final View view = viewer.getDefaultView();
		final JComponent jCompView = (JComponent) view;
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Image image = toolkit
				.getImage("../View/src/main/resources/mouseIcons/Drag_Hand.png");
		Point hotSpot = new Point(0, 0);
		final Cursor cursor = toolkit.createCustomCursor(image, hotSpot,
				"drag_hand");

		viewer.getDefaultView().addMouseMotionListener(
				new MouseMotionListener() {

					public void mouseMoved(MouseEvent e) {
						String s;
						Collection<GraphicElement> gElements = findNodesOrSpritesAtWithTolerance(
								e, view);
						gElement = nearestElement(e, gElements);
						if (gElement instanceof GraphicNode) {
							s = getNodeInformations(gElement, graph);
							if ((jCompView.getToolTipText() == null || !jCompView
									.getToolTipText().equals(s))) {
								jCompView.setToolTipText(s);
								view.display(viewer.getGraphicGraph(), true);
							}
						} else if (gElement instanceof GraphicSprite) {
							s = getEdgeInformations(gElement, graph);
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
						if (isButton1Dragging) {
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
								view.moveElementAtPx(gElement, e.getX(),
										e.getY());
							}
						}
					}
				});

		jCompView.addMouseListener(new MouseListener() {

			public void mouseClicked(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON3) {
					Collection<GraphicElement> gElements;

					if (isGraphLoaded) {
						if (gElement == null) {
							gElements = findNodesOrSpritesAtWithTolerance(e,
									view);
							gElement = nearestElement(e, gElements);
						}
						if (gElement instanceof GraphicNode) {
							modifyNode(frame, graph, gElement.getId());
						} else if (gElement instanceof GraphicSprite) {
							modifyEdge(frame, graph, gElement.getId());
						}
					}
				}
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
				Collection<GraphicElement> gElements;

				if (e.getButton() == MouseEvent.BUTTON1) {
					isButton1Dragging = true;
					gElements = findNodesOrSpritesAtWithTolerance(e, view);
					gElement = nearestElement(e, gElements);
					if (gElement instanceof GraphicNode) {
						view.moveElementAtPx(gElement, e.getX(), e.getY());
					}
				}
			}

			public void mouseReleased(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON1) {
					isButton1Dragging = false;
					x = null;
					y = null;
					gElement = null;
					jCompView.setCursor(Cursor
							.getPredefinedCursor(Cursor.HAND_CURSOR));
				}
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

	public static Collection<GraphicElement> findNodesOrSpritesAtWithTolerance(
			MouseEvent e, View view) {
		double topY = e.getY() - tolerance;
		double bottomY = e.getY() + tolerance;
		double leftX = e.getX() - tolerance;
		double rightX = e.getX() + tolerance;
		return view.allNodesOrSpritesIn(leftX, topY, rightX, bottomY);
	}

	public static GraphicElement nearestElement(MouseEvent e,
			Collection<GraphicElement> gElements) {
		int nbElem = gElements.size();
		GraphicElement gElem;
		double distance, distanceTemp;
		if (nbElem == 0) {
			return null;
		} else if (nbElem == 1) {
			return (GraphicElement) gElements.toArray()[0];
		} else {
			gElem = (GraphicElement) gElements.toArray()[0];
			distance = calculDistance(e, gElem);
			for (GraphicElement gElement : gElements) {
				distanceTemp = calculDistance(e, gElement);
				if (distanceTemp < distance) {
					gElem = gElement;
					distance = distanceTemp;
				}
			}
			return gElem;
		}
	}

	public static double calculDistance(MouseEvent e, GraphicElement gElem) {
		return Math.sqrt((gElem.getX() - e.getX()) * (gElem.getX() - e.getX())
				+ (gElem.getY() - e.getY()) * (gElem.getY() - e.getY()));
	}

	public static void initGraphPropertiesJson() {
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

		setListenerOnViewer(frame, viewerJson, graphJson, textJson,
				isGraphJsonLoaded);
	}

	public static void initGraphPropertiesAgent() {
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

		setListenerOnViewer(frame, viewerAgent, graphAgent, textAgent,
				isGraphAgentLoaded);
	}

	public static void initGraphProperties(String graphName) {
		if (graphName.equals(GRAPH_JSON_NAME)) {
			initGraphPropertiesJson();
		} else if (graphName.equals(GRAPH_AGENT_NAME)) {
			initGraphPropertiesAgent();
		}
	}

	public static void initPanelGraphJson() {
		panelGraphJson.removeAll();
		panelGraphJson.setLayout(new BorderLayout());
		panelGraphJson.add((Component) viewJson, BorderLayout.CENTER);
		scrollJSon.setViewportView(panelGraphJson);
	}

	public static void initPanelGraphAgent() {
		panelGraphAgent.removeAll();
		panelGraphAgent.setLayout(new BorderLayout());
		panelGraphAgent.add((Component) viewAgent, BorderLayout.CENTER);
		scrollAgent.setViewportView(panelGraphAgent);
	}

	public static void initPanelGraph(String graphName) {
		if (graphName.equals(GRAPH_JSON_NAME)) {
			initPanelGraphJson();
		} else if (graphName.equals(GRAPH_AGENT_NAME)) {
			initPanelGraphAgent();
		}
	}

	public static void modifZoom(View view, JTextField jTextField,
			boolean isGraphLoaded, double modif) {
		double zoom;
		int valZoom;
		if (isGraphLoaded) {
			zoom = view.getCamera().getViewPercent();
			view.getCamera().setViewPercent(zoom + modif);
			valZoom = (int) (view.getCamera().getViewPercent() * 100);
			jTextField.setText(valZoom + " %");
		}
	}

	public static String getNodeInformations(GraphicElement gElem, Graph graph) {
		String s = "<html>", fieldName, attValue = "";
		String idNode = gElem.getId();
		Node node = graph.getNode(idNode);

		fieldName = MyJsonGenerator.FORMAT_NODE_NAME;
		s += fieldName + " : " + node.getAttribute("ui.label") + "<br/>";
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
		s += fieldName + " : " + edge.getAttribute(fieldName) + "<br/>";
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

	public static void enterNewZoomValue(KeyEvent evt, Boolean isGraphLoaded,
			JTextField textZoom, View view) {
		if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
			int valZoom;

			if (isGraphLoaded) {
				String s = textZoom.getText();
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
						view.getCamera().setViewPercent(total);
						valZoom = (int) (view.getCamera().getViewPercent() * 100);
						textZoom.setText(valZoom + " %");
					} else if (pourcentage < 100) {
						zoomArr = 100 - pourcentage;
						total = 1 + (zoomArr / 100);
						view.getCamera().setViewPercent(total);
						valZoom = (int) (viewJson.getCamera().getViewPercent() * 100);
						textZoom.setText(valZoom + " %");
					} else {
						view.getCamera().resetView();
					}
				}
			} else {
				view.getCamera().resetView();
			}
		}
	}

	public void centerView(Boolean isGraphLoaded, View view, JTextField textZoom) {
		if (isGraphLoaded) {
			view.getCamera().resetView();
			textZoom.setText("100 %");
		}
	}

	public void setDisplay(Boolean isGraphLoaded, Viewer viewer, Graph graph,
			String graphName) {
		if (isGraphLoaded) {
			new WindowDisplay(graphName, viewer, graph);
		} else {
			textColorStatut.appendDoc(NO_GRAPH_GENERATED);
		}
	}

	public static void changeDisplayEdge(JFrame frame, boolean isGraphLoaded,
			Graph graph) {
		String displayToApply;

		if (isGraphLoaded) {
			ChangeDisplayEdgeDialog changeDisplayEdge = new ChangeDisplayEdgeDialog(
					frame, "Change Edge Display", graph);
			displayToApply = changeDisplayEdge.getDisplay();
			if (!changeDisplayEdge.getFerme()) {
				currentEdgeDisplay = displayToApply;
				for (Edge edge : graph.getEachEdge()) {
					GraphModifier.setUILabelEdge(displayToApply, edge);
				}
			}
		}
	}

	public static void addNode(JFrame frame, boolean isGraphLoaded,
			Graph graph, String graphName) {
		AddNodeDialog addNodeDialog = new AddNodeDialog(frame, "Add Node");
		String nodeName = addNodeDialog.getName();
		if (!addNodeDialog.getFerme()) {
			if (isGraphLoaded) {
				if (!nodeName.equals("")) {
					Node node = graph.getNode(nodeName);
					if (node == null) {
						GraphModifier.addNode(addNodeDialog, graph);
					} else {
						msgError(NAME_ALREADY_IN_USE);
					}
				} else {
					msgError(NAME_FIELD_EMPTY);
				}
			} else {
				if (!nodeName.equals("")) {
					if (graphName.equals(GRAPH_JSON_NAME)) {
						graphJson = new MultiGraph(graphName);
						graph = graphJson;
					} else if (graphName.equals(GRAPH_AGENT_NAME)) {
						graphAgent = new MultiGraph(graphName);
						graph = graphAgent;
					}
					initGraphProperties(graphName);
					initPanelGraph(graphName);
					GraphModifier.addNode(addNodeDialog, graph);
				} else {
					msgError(NAME_FIELD_EMPTY);
				}
			}
		}
	}

	public static void deleteNode(JFrame frame, boolean isGraphLoaded,
			Graph graph, SpriteManager spriteManager) {
		if (isGraphLoaded && graph.getNodeCount() != 0) {
			DeleteNodeDialog deleteNodeDialog = new DeleteNodeDialog(frame,
					"Delete Node", graph);
			if (!deleteNodeDialog.getFerme()) {
				String nodeName = deleteNodeDialog.getName();
				Node node = graph.getNode(nodeName);
				for (Edge edge : node.getEachEdge()) {
					spriteManager.removeSprite(edge.getId());
				}
				node = graph.removeNode(nodeName);
				if (node != null) {
					msgAlert(ACTION_ON_NODE + nodeName + ERASE_ACTION);
				}
			}
		} else {
			textColorStatut.appendDoc(NO_NODE_DETECTED);
		}
	}

	public static void selectNode(JFrame frame, boolean isGraphLoaded,
			Graph graph) {
		if (isGraphLoaded && graph.getNodeCount() != 0) {
			SelectNodeDialog selectNodeDialog = new SelectNodeDialog(frame,
					"Select Node", graph);
			if (!SelectNodeDialog.getFerme()) {
				String nodeName = selectNodeDialog.getNodeName();
				modifyNode(frame, graph, nodeName);
			}
		} else {
			textColorStatut.appendDoc(NO_NODE_DETECTED);
		}
	}

	public static void modifyNode(JFrame frame, Graph graph, String nodeName) {
		ChangeNodeDialog changeNodeDialog = new ChangeNodeDialog(frame,
				"Modify Node", nodeName, graph);
		String s = ChangeNodeDialog.getNameNode();
		if (!ChangeNodeDialog.getFerme()) {

			if (!s.equals("")) {
				Node n = graph.getNode(s);
				if (n == null || s.equals(nodeName)) {
					GraphModifier.changeNode(changeNodeDialog, graph, nodeName);
				} else {
					msgError(NAME_ALREADY_IN_USE);
				}
			} else {
				msgError(NAME_FIELD_EMPTY);
			}
		}
	}

	public static void addEdge(JFrame frame, Boolean isGraphLoaded,
			Graph graph, SpriteManager spriteManager) {
		if (isGraphLoaded) {
			AddEdgeDialog addEdgeDialog = new AddEdgeDialog(frame, "Add Edge",
					graph);
			if (!addEdgeDialog.getFerme()) {
				try {
					GraphModifier.addEdge(addEdgeDialog, graph, spriteManager);

				} catch (NoSpecifiedNodeException e) {
					textColorStatut.appendErrorMessage(e.getMessage());
				}
			}
		} else {
			textColorStatut.appendDoc(NO_NODE_DETECTED);
		}
	}

	public static void deleteEdge(JFrame frame, Boolean isGraphLoaded,
			Graph graph, SpriteManager spriteManager) {
		if (isGraphLoaded && graph.getEdgeCount() != 0) {
			DeleteEdgeDialog deleteEdgeDialog = new DeleteEdgeDialog(frame,
					"Delete Edge", graph);
			if (!deleteEdgeDialog.getFerme()) {
				String edgeName = deleteEdgeDialog.getName();
				Edge edge = graph.removeEdge(edgeName);
				spriteManager.removeSprite(edge.getId());
				if (edge != null) {
					msgAlert(ACTION_ON_EDGE + edgeName + ERASE_ACTION);
				}
			}
		} else {
			textColorStatut.appendDoc(NO_EDGE_DETECTED);
		}
	}

	public static void selectEdge(JFrame frame, Boolean isGraphLoaded,
			Graph graph) {
		if (isGraphLoaded && graph.getEdgeCount() != 0) {
			SelectEdgeDialog selectEdgeDialog = new SelectEdgeDialog(frame,
					"Select Edge", graph);
			if (!SelectEdgeDialog.getFerme()) {
				String edgeName = selectEdgeDialog.getEdgeName();
				modifyEdge(frame, graph, edgeName);
			}
		} else {
			textColorStatut.appendDoc(NO_NODE_DETECTED);
		}
	}

	public static void modifyEdge(JFrame frame, Graph graph, String edgeName) {
		ChangeEdgeDialog changeEdgeDialog = new ChangeEdgeDialog(frame,
				"Modify Edge", edgeName, graph);
		if (!changeEdgeDialog.getFerme()) {
			GraphModifier.changeEdge(changeEdgeDialog, graph, edgeName,
					currentEdgeDisplay);
		}
	}

	public static void turnAutoLayoutButtonOn(JButton button) {
		ImageIcon autoLayoutOnIcon = new ImageIcon(
				"../View/src/main/resources/buttonsIcons/autoLayoutOn.png",
				"automatic layout on");
		button.setIcon(autoLayoutOnIcon);
		button.setToolTipText(AUTO_LAYOUT_ENABLED_TT);
	}

	public static void turnAutoLayoutButtonOff(JButton button) {
		ImageIcon autoLayoutOffIcon = new ImageIcon(
				"../View/src/main/resources/buttonsIcons/autoLayoutOff.png",
				"automatic layout on");
		button.setIcon(autoLayoutOffIcon);
		button.setToolTipText(AUTO_LAYOUT_DISABLED_TT);
	}

	public void autoLayout(Boolean isGraphLoaded, Boolean isAutoLayout,
			String graphName, Viewer viewer, JButton structGraph) {
		if (isGraphLoaded) {
			if (isAutoLayout) {
				turnAutoLayoutButtonOff(structGraph);
				viewer.disableAutoLayout();
				if (graphName.equals(GRAPH_JSON_NAME)) {
					isAutoLayoutJson = false;
				} else if (graphName.equals(GRAPH_AGENT_NAME)) {
					isAutoLayoutAgent = false;
				}
			} else {
				turnAutoLayoutButtonOn(structGraph);
				viewer.enableAutoLayout();
				if (graphName.equals(GRAPH_JSON_NAME)) {
					isAutoLayoutJson = true;
				} else if (graphName.equals(GRAPH_AGENT_NAME)) {
					isAutoLayoutAgent = true;
				}
			}
		}
	}

	public void setTreeLayout(Boolean isGraphLoaded, Graph graph,
			Viewer viewer, JButton structGraph, String graphName) {
		if (isGraphLoaded) {
			CustomGraphRenderer.setTreeLayout(graph, viewer);
			turnAutoLayoutButtonOff(structGraph);
			if (graphName.equals(GRAPH_JSON_NAME)) {
				isAutoLayoutJson = false;
			} else if (graphName.equals(GRAPH_AGENT_NAME)) {
				isAutoLayoutAgent = false;
			}
		}
	}

	public void cleanView(Boolean isGraphLoaded, JPanel panelGraph,
			String graphName) {
		if (isGraphLoaded) {
			panelGraph.removeAll();
			if (graphName.equals(GRAPH_JSON_NAME)) {
				isGraphJsonLoaded = false;
			} else if (graphName.equals(GRAPH_AGENT_NAME)) {
				isGraphAgentLoaded = false;
			}
			panelGraph.updateUI();
		} else {
			textColorStatut.appendDoc(NO_GRAPH_GENERATED);
		}
	}

	public static void msgError(String s) {
		JOptionPane.showMessageDialog(null, s, "Error",
				JOptionPane.ERROR_MESSAGE);
	}

	public static void msgAlert(String s) {
		JOptionPane.showMessageDialog(null, s, "Alert",
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
				Window.textColorStatut.appendDoc(msg);
			}
		}).start();
	}

	public static void main(String[] args) {
		new Window();
	}
	
	public void setRightGraph(Graph graph) {		
		graphAgent = graph;
		isGraphAgentLoaded = true;
		
		textJson.setText("100 %");
		textAgent.setText("100 %");
		turnAutoLayoutButtonOn(structGraphAgent);
		turnAutoLayoutButtonOn(structGraphJson);
		
		initGraphPropertiesAgent();
		initPanelGraphAgent();
		
		//treeLayoutAgent.doClick();
	}

}
