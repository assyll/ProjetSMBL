package interfaceGraphique;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
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
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;

import jsonAndGS.FileFormatException;
import jsonAndGS.JsonToGS;

import org.graphstream.graph.Element;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.ui.graphicGraph.GraphicNode;
import org.graphstream.ui.view.View;
import org.graphstream.ui.view.Viewer;

import com.fasterxml.jackson.core.JsonParseException;

@SuppressWarnings("serial")
public class Fenetre extends JFrame {
	
	public static final String NO_FILE_SELECTED = "Veuillez d'abord sélectionner un fichier à importer";

	// Instanciation des différents Components

	JFrame frame;

	JPanel panelFile, panelGraph, graphJSon, graphAgent, zoomJSon, zoomAgent,
			panelModifJSon, panelModifAg, panelOptionJSon, panelOptionAg;

	JSplitPane splitPane, splitPane2;

	JScrollPane scrollJSon, scrollAgent, scrollStatut;

	JTextField directory, textJSon, textAg;

	JColorTextPane textStatut;

	JButton buttonGS, zoomAvantJSon, zoomArrJSon, zoomTextJSon, zoomAvantAg,
			zoomArrAg, zoomTextAg, addNodeJSon, addEdgeJSon, addNodeAg,
			addEdgeAg, structGraphJson, structGraphAg;

	JMenuBar menu_bar1;

	JMenu menu1;

	JMenuItem importMenu, exitMenu;

	Viewer viewer, viewer2;

	View view, view2;

	Graph graph;

	double zoom = 1, dezoom = 1;

	final int xWindow = 50, yWindow = 0, widthWindow = 1280,
			heightWindow = 700, sizeSeparator = 5;

	boolean isAutoLayoutJson, isAutoLayoutAg, isGraphJsonLoaded = false,
			isGraphAgLoaded = false;

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
		zoomJSon = new JPanel(new GridLayout(2, 2, 20, 50));
		zoomJSon.add(zoomAvantJSon);
		zoomJSon.add(zoomArrJSon);
		zoomJSon.add(zoomTextJSon);
		zoomJSon.add(textJSon);

		// Initialisation et définition du panneau pour zoomer le graphe Agent
		zoomAgent = new JPanel(new GridLayout(2, 2, 20, 50));
		zoomAgent.add(zoomAvantAg);
		zoomAgent.add(zoomArrAg);
		zoomAgent.add(zoomTextAg);
		zoomAgent.add(textAg);

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
		panelOptionJSon.add(zoomJSon);
		panelOptionJSon.add(panelModifJSon);

		// Initialisation et définition panneau option droit
		panelOptionAg = new JPanel(new GridLayout(2, 1, 20, 50));
		panelOptionAg.add(zoomAgent);
		panelOptionAg.add(panelModifAg);

		// Initialisation de la zone de texte de la barre de statut
		textStatut = new JColorTextPane();
		textStatut.styleDoc();
		textStatut.setEditable(false);
		textStatut.setBackground(new Color(238, 238, 238));

		// Ajout de Components au 1er panneau
		buttonGS = new JButton("To GraphStream");
		directory = new JTextField("Directory");
		directory.setPreferredSize(new Dimension(250, 20));
		directory.setEditable(false);
		panelFile.add(directory);
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
		scrollStatut.setViewportView(textStatut);
		graphJSon = new JPanel();
		graphAgent = new JPanel();

		// Action lors du clic sur l'item "Import"
		importMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// TODO changer le chemin d'acces lors de la release
				JFileChooser dialogue = new JFileChooser(new File(
						"./src/test/resources"));
				File fichier;

				if (dialogue.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
					fichier = dialogue.getSelectedFile();
					directory.setText(fichier.toString());
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

				if (!directory.getText().equals("Directory")) {
					JsonToGS jSTGS = new JsonToGS();
					try {
						graph = jSTGS.generateGraph(directory.getText());

						GraphRendererPerso.setStyleGraph(graph);
						GraphModifier.setNodeClass(graph);

						viewer = new Viewer(graph,
								Viewer.ThreadingModel.GRAPH_IN_ANOTHER_THREAD);
						viewer.enableAutoLayout();
						isAutoLayoutJson = true;
						view = viewer.addDefaultView(false);
						setListenerOnViewer(viewer);

						graphJSon.removeAll();
						graphJSon.setLayout(new BorderLayout());
						graphJSon.add((Component) view, BorderLayout.CENTER);
						scrollJSon.setViewportView(graphJSon);

						isGraphJsonLoaded = true;

						Graph graph2 = GraphModifier.GraphToGraph(graph,
								"graph2");

						GraphRendererPerso.setStyleGraph(graph2);
						GraphModifier.setNodeClass(graph2);

						viewer2 = new Viewer(graph2,
								Viewer.ThreadingModel.GRAPH_IN_ANOTHER_THREAD);
						viewer2.enableAutoLayout();
						isAutoLayoutAg = true;
						view2 = viewer2.addDefaultView(false);
						setListenerOnViewer(viewer2);

						graphAgent.removeAll();
						graphAgent.setLayout(new BorderLayout());
						graphAgent.add((Component) view2, BorderLayout.CENTER);
						scrollAgent.setViewportView(graphAgent);

						isGraphAgLoaded = true;

						// TODO
						// http://forum.hardware.fr/hfr/Programmation/Java/jtextarea-couleur-texte-sujet_43087_1.htm
					} catch (JsonParseException e1) {
						textStatut
								.appendErrorMessage(JsonToGS.FILE_FORMAT_ERROR);
						// e1.printStackTrace();
					} catch (IOException e1) {
						textStatut
								.appendErrorMessage(JsonToGS.FILE_FORMAT_ERROR);
						// e1.printStackTrace();
					} catch (FileFormatException e1) {
						textStatut
								.appendErrorMessage(JsonToGS.FILE_FORMAT_ERROR);
						// e1.printStackTrace();
					}
				} else {
					textStatut
							.appendDoc(NO_FILE_SELECTED);
				}
			}
		});

		// Action lors du clic sur l'item "+" de la partie gauche
		zoomAvantJSon.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (isGraphJsonLoaded) {
					zoom = view.getCamera().getViewPercent();
					view = viewer.getDefaultView();
					view.getCamera().setViewPercent(zoom - 0.1);
				}
			}
		});

		// Action lors du clic sur l'item "-" de la partie gauche
		zoomArrJSon.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (isGraphJsonLoaded) {
					dezoom = view.getCamera().getViewPercent();
					view = viewer.getDefaultView();
					view.getCamera().setViewPercent(dezoom + 0.1);
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
						textStatut.appendDoc("Ce n'est pas un entier \n");
					} else {
						pourcentage = Integer.parseInt(s);
						view = viewer.getDefaultView();
						if (pourcentage > 100) {
							zoomAvant = pourcentage - 100;
							total = 1 - (zoomAvant / 100);
							view.getCamera().setViewPercent(total);
							textStatut.appendDoc("Zoom avant: " + pourcentage
									+ "% \n");
						} else if (pourcentage < 100) {
							zoomArr = 100 - pourcentage;
							total = 1 + (zoomArr / 100);
							view.getCamera().setViewPercent(total);
							textStatut.appendDoc("Zoom arrière: " + pourcentage
									+ "% \n");
						} else {
							view.getCamera().resetView();
						}
					}
				}

			}
		});

		// Action lors du clic sur l'item "Node +" de la partie gauche

		addNodeJSon.addActionListener(new ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				if (isGraphJsonLoaded) {
					NodeDialog nodeLeft = new NodeDialog(frame, "Add Node");
				}
			}
		});

		// Action lors du clic sur l'item "Edge +" de la partie gauche

		addEdgeJSon.addActionListener(new ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				if (isGraphJsonLoaded) {
					EdgeDialog edgeLeft = new EdgeDialog(frame, "Add Edge");
				}
			}
		});

		// Action lors du clic sur l'item "Structurer / Déstructurer" de la
		// partie gauche
		structGraphJson.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (isGraphJsonLoaded) {
					if (isAutoLayoutJson) {
						viewer.disableAutoLayout();
						isAutoLayoutJson = false;
					} else {
						viewer.enableAutoLayout();
						isAutoLayoutJson = true;
					}
				}
			}
		});

		// Action lors du clic sur l'item "+" de la partie droite
		zoomAvantAg.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (isGraphAgLoaded) {
					zoom = view.getCamera().getViewPercent();
					view = viewer2.getDefaultView();
					view.getCamera().setViewPercent(zoom - 0.1);
				}
			}
		});

		// Action lors du clic sur l'item "-" de la partie droite
		zoomArrAg.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (isGraphAgLoaded) {
					dezoom = view.getCamera().getViewPercent();
					view = viewer2.getDefaultView();
					view.getCamera().setViewPercent(dezoom + 0.1);
				}
			}
		});

		// Action lors du clic sur l'item "%" de la partie droite
		zoomTextAg.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (isGraphAgLoaded) {
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
						textStatut.appendDoc("Ce n'est pas un entier \n");
					} else {
						pourcentage = Integer.parseInt(s);
						view = viewer2.getDefaultView();
						if (pourcentage > 100) {
							zoomAvant = pourcentage - 100;
							total = 1 - (zoomAvant / 100);
							view.getCamera().setViewPercent(total);
							textStatut.appendDoc("Zoom avant: " + pourcentage
									+ "% \n");
						} else if (pourcentage < 100) {
							zoomArr = 100 - pourcentage;
							total = 1 + (zoomArr / 100);
							view.getCamera().setViewPercent(total);
							textStatut.appendDoc("Zoom arrière: " + pourcentage
									+ "% \n");
						} else {
							view.getCamera().resetView();
						}
					}
				}
			}
		});

		// Action lors du clic sur l'item "Node +" de la partie droite

		addNodeAg.addActionListener(new ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				if (isGraphAgLoaded) {
					NodeDialog nodeRight = new NodeDialog(frame, "Add Node");
				}
			}
		});

		// Action lors du clic sur l'item "Edge +" de la partie droite

		addEdgeAg.addActionListener(new ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				if (isGraphAgLoaded) {
					EdgeDialog edgeRight = new EdgeDialog(frame, "Add Edge");
				}
			}
		});

		// Action lors du clic sur l'item "Structurer / Déstructurer" de la
		// partie droite
		structGraphAg.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (isGraphAgLoaded) {
					if (isAutoLayoutAg) {
						viewer2.disableAutoLayout();
						isAutoLayoutAg = false;
					} else {
						viewer2.enableAutoLayout();
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
		frame.setResizable(false);
		frame.setBounds(xWindow, yWindow, widthWindow, heightWindow);
		frame.setVisible(true);

		// Centrage de la fenetre
		pack();
		frame.setLocationRelativeTo(null);
	}

	public void setListenerOnViewer(final Viewer viewer) {
		// Action lors du déplacement de la souris sur le graphe de la partie
		// gauche
		viewer.getDefaultView().addMouseMotionListener(
				new MouseMotionListener() {

					public void mouseMoved(MouseEvent e) {
						String s = "<html>";
						Element elem = view.findNodeOrSpriteAt(e.getX(),
								e.getY());
						if (elem instanceof Node) {
							GraphicNode gNode = (GraphicNode) elem;
							Node node = graph.getNode(gNode.getId());
							for (String attKey : node.getAttributeKeySet()) {
								s += attKey + " : " + node.getAttribute(attKey) + "<br/>";
							}
							s += "</html>";
							viewer.getDefaultView().setToolTipText(s);
						} else {
							viewer.getDefaultView().setToolTipText(null);
						}
					}

					public void mouseDragged(MouseEvent e) {
						Element elem = view.findNodeOrSpriteAt(e.getX(),
								e.getY());
						if (elem == null) {
							// TODO deplace la view plutot que la souris
						}
					}
				});

		// Action lors de l'utilisation de la molette de la souris sur le graphe
		// de la partie gauche
		viewer.getDefaultView().addMouseWheelListener(new MouseWheelListener() {

			public void mouseWheelMoved(MouseWheelEvent e) {
				double wheelValue = e.getPreciseWheelRotation();
				if (wheelValue > 0) {
					dezoom = view.getCamera().getViewPercent();
					view = viewer.getDefaultView();
					view.getCamera().setViewPercent(dezoom + 0.1);
				} else if (wheelValue < 0) {
					zoom = view.getCamera().getViewPercent();
					view = viewer.getDefaultView();
					view.getCamera().setViewPercent(zoom - 0.1);
				}
			}
		});
	}

	public static void main(String[] args) {
		GraphRendererPerso.SetRenderer();
		new Fenetre();
	}

}
