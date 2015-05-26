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
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;

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
import org.graphstream.graph.Element;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.MultiGraph;
import org.graphstream.ui.graphicGraph.GraphicSprite;
import org.graphstream.ui.spriteManager.SpriteManager;
import org.graphstream.ui.view.View;
import org.graphstream.ui.view.Viewer;

import com.fasterxml.jackson.core.JsonParseException;

import convertGraph.ConvertGStoNeo4j;
import convertGraph.ConvertNeo4jToGS;
import convertGraph.Fichier;

//TODO Penser � demander si on doit mettre les m�thodes en static ou non (CustomGraphRender, GraphModifier, JsonToGS)

@SuppressWarnings("serial")
public class Fenetre extends JFrame {
	
	public final static String pathGraphTemp =
			"./src/test/resources/grapheTemporaire";
	
	public static final String NO_FILE_SELECTED = "Veuillez d'abord s�lectionner un fichier � importer";
	public static final String GRAPH_JSON_NAME = "graphJson";
	public static final String GRAPH_AGENT_NAME = "graphAgent";
	public static final double tolerance = 10;
	Double x = null, y = null, x2 = null, y2 = null;

	// Instanciation des diff�rents Components

	JFrame frame;

	JPanel panelFile, panelGraph, panelGraphJSon, panelGraphAgent,
			panelZoomJSon, panelZoomAgent, panelModifJSon, panelModifAgent,
			panelOptionJSon, panelOptionAgent;

	JSplitPane splitPaneHorizontale, splitPaneVerticale;

	JScrollPane scrollJSon, scrollAgent, scrollStatut;

	JTextField textDirectory, textJSon, textAgent;

	JColorTextPane textColorStatut;

	JButton buttonGS, zoomAvantJSon, zoomArrJSon, zoomTextJSon, zoomAvantAgent,
			zoomArrAgent, zoomTextAgent, addNodeJSon, addEdgeJSon,
			addNodeAgent, addEdgeAgent, structGraphJson, structGraphAgent,
			buttonSave;

	JMenuBar menu_bar1;

	JMenu menu1, menu2, menu21;

	JMenuItem importMenuGauche, importMenuDroite, exitMenu,
	          jMenuItemGenererGraphe, jMenuItemGenererTraces1,
	          jMenuItemGenererTraces2;

	Viewer viewerJson, viewerAgent;

	View viewJson, viewAgent;

	Graph graphJson, graphAgent;

	double zoom = 1, dezoom = 1;

	final int xWindow = 50, yWindow = 0, widthWindow = 1280,
			heightWindow = 700, sizeSeparator = 5;

	boolean isAutoLayoutJson, isAutoLayoutAgent, isGraphJsonLoaded = false,
			isGraphAgentLoaded = false, isDirectoryNeo4j, wantToGenerateToLeft;

	SpriteManager spriteManagerJson, spriteManagerAgent;

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
		panelModifJSon = new JPanel(new GridLayout(4, 1, 15, 20));

		// Initialisation et d�finition du panneau d'ajout de noeuds et de
		// transition droit
		panelModifAgent = new JPanel(new GridLayout(3, 1, 15, 50));

		// Initialisation des bouttons de zoom
		zoomAvantJSon = new JButton("<html><b>Zoom +</b></html>");
		zoomArrJSon = new JButton("<html><b>Zoom -</b></html>");
		zoomTextJSon = new JButton("<html><b>%</b></html>");
		zoomAvantAgent = new JButton("<html><b>Zoom +</b></html>");
		zoomArrAgent = new JButton("<html><b>Zoom -</b></html>");
		zoomTextAgent = new JButton("<html><b>%</b></html>");

		// initialisation de la zone de texte pour le pourcentage de zoom
		textJSon = new JTextField();
		textJSon.setPreferredSize(new Dimension(40, 30));
		textAgent = new JTextField();

		// Initialisation et d�finition du panneau pour zoomer le graphe Json
		panelZoomJSon = new JPanel(new GridLayout(2, 2, 20, 50));
		panelZoomJSon.add(zoomAvantJSon);
		panelZoomJSon.add(zoomArrJSon);
		panelZoomJSon.add(zoomTextJSon);
		panelZoomJSon.add(textJSon);

		// Initialisation et d�finition du panneau pour zoomer le graphe Agent
		panelZoomAgent = new JPanel(new GridLayout(2, 2, 20, 50));
		panelZoomAgent.add(zoomAvantAgent);
		panelZoomAgent.add(zoomArrAgent);
		panelZoomAgent.add(zoomTextAgent);
		panelZoomAgent.add(textAgent);

		// Initialisation des boutons d'option
		addNodeJSon = new JButton("Node +");
		addEdgeJSon = new JButton("Edge +");

		structGraphJson = new JButton("Structurer / D�structurer");
		structGraphJson = new JButton("Structurer / D�structurer");
		addNodeAgent = new JButton("Node +");
		addEdgeAgent = new JButton("Edge +");
		structGraphAgent = new JButton("Structurer / D�structurer");
		
		buttonSave = new JButton("Save");

		// Ajout des boutons dans les panneaux respectifs
		panelModifJSon.add(addNodeJSon);
		panelModifJSon.add(addEdgeJSon);
		panelModifJSon.add(structGraphJson);
		panelModifJSon.add(buttonSave);
		panelModifAgent.add(addNodeAgent);
		panelModifAgent.add(addEdgeAgent);
		panelModifAgent.add(structGraphAgent);

		// Initialisation et d�finition panneau option gauche
		panelOptionJSon = new JPanel(new GridLayout(2, 1, 20, 50));
		panelOptionJSon.add(panelZoomJSon);
		panelOptionJSon.add(panelModifJSon);

		// Initialisation et d�finition panneau option droit
		panelOptionAgent = new JPanel(new GridLayout(2, 1, 20, 50));
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
		menu2 = new JMenu("Tools");
		menu21 = new JMenu("Generate traces");
		
		importMenuGauche = new JMenuItem("Import to left");
		importMenuDroite = new JMenuItem("Import to right");
		exitMenu = new JMenuItem("Exit");

		menu1.add(importMenuGauche);
		menu1.add(importMenuDroite);
		menu1.add(exitMenu);
		
		jMenuItemGenererGraphe = new JMenuItem("Generate graph");
		jMenuItemGenererTraces1 = new JMenuItem(
				"Generate one trace by file");
		jMenuItemGenererTraces2 = new JMenuItem(
				"Generate multiple trace into one file");
		
		menu21.add(jMenuItemGenererTraces1);
		menu21.add(jMenuItemGenererTraces2);
		menu2.add(jMenuItemGenererGraphe);
		menu2.add(menu21);

		menu_bar1.add(menu1);
		menu_bar1.add(menu2);
		

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
				GraphGenerateDialog dialog =
						new GraphGenerateDialog(Fenetre.this);
				dialog.show();
				
				// Graphe genere avec succes
				if (dialog.isGeneratedWithSuccess()) {
					// Met a jour les a valeurs afin quon puisse appuyer sur
					// "To GraphStream"
					isDirectoryNeo4j = true;
					wantToGenerateToLeft = true;
					
					graphJson = new ConvertNeo4jToGS(pathGraphTemp).
							convertToGS();
					textDirectory.setText(pathGraphTemp);
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
				// Visualisation du graph g�n�r� par le fichier import� au
				// format .json

				if (!textDirectory.getText().equals("Directory")) {
					JsonToGS jSTGS = new JsonToGS();
					try {
						if (wantToGenerateToLeft) {
							if (!isDirectoryNeo4j) {
								graphJson = jSTGS.generateGraph(
										textDirectory.getText(),
										GRAPH_JSON_NAME);
							}
							initGraphPropertiesJson();
							initPanelGraphJson();
						} else {
							if (!isDirectoryNeo4j) {
								graphAgent = jSTGS.generateGraph(
										textDirectory.getText(),
										GRAPH_JSON_NAME);
							}
							initGraphPropertiesAgent();
							initPanelGraphAgent();
						}
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
						textColorStatut.appendErrorMessage(
								"Graphe Neo4j deja ouvert quelque part!");
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
							textColorStatut.appendDoc("Zoom arri�re: "
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
							} else {
								msgError("Nom d�j� existant");
							}
						} else {
							msgError("Nom invalide car vide");
						}
					} else if (graphJson == null && nodeLeft.getCheck()) {
						if (!s.equals("")) {
							graphJson = new MultiGraph(GRAPH_JSON_NAME);
							initGraphPropertiesJson();
							initPanelGraphJson();
							GraphModifier.addNode(nodeLeft, graphJson);
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
									GraphModifier.addEdge(edgeLeft, graphJson,
											spriteManagerJson);
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
		
		// Action lors du clic sur l'item "Sauvegarder" (partie gauche)
		buttonSave.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (isGraphJsonLoaded) {
					JFileChooser jFileChooser = new JFileChooser(
							new File("./src/test/resources"));
					jFileChooser.setFileSelectionMode(
							JFileChooser.DIRECTORIES_ONLY);

					String path;
					if (jFileChooser.showSaveDialog(null) ==
							JFileChooser.APPROVE_OPTION) {
						
						File file = jFileChooser.getSelectedFile();
						path = file.toString();
						
						if (file.exists()) {
							int option = JOptionPane.showConfirmDialog(null,
								(Fichier.isFolderNeo4j(path)
								? "Il semblerait " : "Il ne semblerait pas ")+
								"que ce soit un dossier Neo4J" +
								"\nVoulez-vous sauvegarder dans ce dossier ?" +
								"\nNB: le dossier sera supprimé.",
								"Sauvegarder", JOptionPane.YES_NO_OPTION);
							
							if (option == JOptionPane.OK_OPTION) {
								new ConvertGStoNeo4j(graphJson).
									convertToNeo4j(path);
								textColorStatut.appendDoc(
										"Graphe sauvegarde! (" +
										path + ").\n");
							} else {
								textColorStatut.appendDoc(
										"Graphe non sauvegarde.\n");
							}
						} else {
							new ConvertGStoNeo4j(graphJson).
								convertToNeo4j(path);
							textColorStatut.appendDoc(
									"Graphe sauvegarde! (" + path + ").\n");
						}
					} else if(jFileChooser.getSelectedFile() == null) {
						textColorStatut.appendDoc(
								"Erreur: Un Fichier porte le meme nom");
					}
				} else {
					textColorStatut.appendDoc(
							"Pas de graphe a sauvegarder.\n");
				}
			}
		});

		// Action lors du clic sur l'item "+" de la partie droite
		zoomAvantAgent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (isGraphAgentLoaded) {
					zoom = viewAgent.getCamera().getViewPercent();
					viewAgent.getCamera().setViewPercent(zoom - 0.1);
				}
			}
		});

		// Action lors du clic sur l'item "-" de la partie droite
		zoomArrAgent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (isGraphAgentLoaded) {
					dezoom = viewAgent.getCamera().getViewPercent();
					viewAgent.getCamera().setViewPercent(dezoom + 0.1);
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
							textColorStatut.appendDoc("Zoom avant: "
									+ pourcentage + "% \n");
						} else if (pourcentage < 100) {
							zoomArr = 100 - pourcentage;
							total = 1 + (zoomArr / 100);
							viewAgent.getCamera().setViewPercent(total);
							textColorStatut.appendDoc("Zoom arri�re: "
									+ pourcentage + "% \n");
						} else {
							viewAgent.getCamera().resetView();
						}
					}
				}
			}
		});

		// Action lors du clic sur l'item "Node +" de la partie droite
		addNodeAgent.addActionListener(new ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				NodeDialog nodeRight = new NodeDialog(frame, "Add Node");
				if (!nodeRight.getFerme()) {
					if (isGraphAgentLoaded) {
						GraphModifier.addNode(nodeRight, graphAgent);
					} else if (graphAgent == null) {
						graphAgent = new MultiGraph(GRAPH_AGENT_NAME);
						initGraphPropertiesAgent();
						initPanelGraphAgent();
						GraphModifier.addNode(nodeRight, graphAgent);
					}
				}
			}
		});

		// Action lors du clic sur l'item "Edge +" de la partie droite
		addEdgeAgent.addActionListener(new ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				if (isGraphAgentLoaded) {
					EdgeDialog edgeRight = new EdgeDialog(frame, "Add Edge",
							graphAgent);
					if (!edgeRight.getFerme()) {
						try {
							GraphModifier.addEdge(edgeRight, graphAgent,
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
				"./src/test/resources"));
		dialogue.setFileSelectionMode(
				JFileChooser.FILES_AND_DIRECTORIES);
		File fichier;

		isDirectoryNeo4j = false;
		if (dialogue.showOpenDialog(null)
				== JFileChooser.APPROVE_OPTION) {
			
			// Recupere le fichier a importer
			fichier = dialogue.getSelectedFile();
			textDirectory.setText(fichier.toString());

			isDirectoryNeo4j =
					Fichier.isFolderNeo4j(fichier.toString());
			
			// Si cest un dossier et que cest pas un neo4j
			if (fichier.isDirectory() && !isDirectoryNeo4j) {
				JOptionPane.showConfirmDialog(null,
						"It's not a Neo4J folder",
						"Import Problem",
						JOptionPane.CLOSED_OPTION);
				
				textDirectory.setText("Directory");
			} else {
				// sinon IMPORTER NEO4J
				if (wantToGenerateToLeft) {
					graphJson = new ConvertNeo4jToGS(
							fichier.toString()).convertToGS();
				} else {
					graphAgent = new ConvertNeo4jToGS(
							fichier.toString()).convertToGS();
				}
			}
		}
	}
	
	// clique sur generate Traces (nimporte lequel)
	public void generateTraces(boolean oneByFile) {
		TracesGenerateDialog dialog =
				new TracesGenerateDialog(this, oneByFile,
						graphJson, isGraphJsonLoaded);
		dialog.show();
	}

	public void setListenerOnViewer(final Viewer viewer, final Graph graph) {
		// Action lors du d�placement de la souris sur le graphe
		final View view = viewer.getDefaultView();
		final JComponent jCompView = (JComponent) view;

		viewer.getDefaultView().addMouseMotionListener(
				new MouseMotionListener() {

					public void mouseMoved(MouseEvent e) {
						String s = "<html>";
						Element elem = findNodeOrSpriteAtWithTolerance(e, view);
						if (elem instanceof Node) {
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

	public void initGraphPropertiesJson() {
		CustomGraphRenderer.setStyleGraph(graphJson);
		GraphModifier.setNodesClass(graphJson);
		spriteManagerJson = new SpriteManager(graphJson);
		GraphModifier.generateSprites(graphJson, spriteManagerJson);

		viewerJson = new Viewer(graphJson,
				Viewer.ThreadingModel.GRAPH_IN_ANOTHER_THREAD);
		viewerJson.enableAutoLayout();
		viewJson = viewerJson.addDefaultView(false);
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

	public static void main(String[] args) {
		CustomGraphRenderer.SetRenderer();
		new Fenetre();
	}

}
