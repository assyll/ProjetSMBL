package interfaceGraphique;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

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

import jsonAndGS.JsonToGS;
import jsonAndGS.MyJsonGenerator;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.MultiGraph;
import org.graphstream.ui.swingViewer.View;
import org.graphstream.ui.swingViewer.Viewer;

@SuppressWarnings("serial")
public class Fenetre extends JFrame implements ActionListener {

	// Instanciation des différents Components

	JFrame frame;

	JPanel panelFile, panelGraph, panelboutton, panelgen, graphJSon,
			graphAgent, zoomJSon, zoomAgent;

	JSplitPane splitPane, splitPane2;

	JScrollPane scrollJSon, scrollAgent, scrollStatut;

	JTextField directory, textJSon, textAg;

	JTextArea textStatut;

	JButton buttonGS, zoomAvantJSon, zoomArrJSon, zoomTextJSon, zoomAvantAg,
			zoomArrAg, zoomTextAg;

	JMenuBar menu_bar1;

	JMenu menu1;

	JMenuItem importMenu, exitMenu;

	Viewer viewer, viewer2;

	View view, view2;

	Graph graph;

	double zoom = 1, dezoom = 1;

	final int xWindow = 50, yWindow = 0, widthWindow = 1280,
			heightWindow = 700, sizeSeparator = 5;

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

		// Initialisation des bouttons de zoom
		zoomAvantJSon = new JButton("<html><b>+</b></html>");
		zoomArrJSon = new JButton("<html><b>-</b></html>");
		zoomTextJSon = new JButton("<html><b>%</b></html>");
		zoomAvantAg = new JButton("<html><b>+</b></html>");
		zoomArrAg = new JButton("<html><b>-</b></html>");
		zoomTextAg = new JButton("<html><b>%</b></html>");

		// initialisation de la zone de texte pour le pourcentage de zoom
		textJSon = new JTextField();
		textAg = new JTextField();

		// Initialisation et définition du panneau pour zoomer le graphe Json
		zoomJSon = new JPanel(new GridLayout(4, 1, 20, 5));
		zoomJSon.add(zoomAvantJSon);
		zoomJSon.add(zoomArrJSon);
		zoomJSon.add(zoomTextJSon);
		zoomJSon.add(textJSon);

		// Initialisation et définition du panneau pour zoomer le graphe Agent
		zoomAgent = new JPanel(new GridLayout(4, 1, 20, 5));
		zoomAgent.add(zoomAvantAg);
		zoomAgent.add(zoomArrAg);
		zoomAgent.add(zoomTextAg);
		zoomAgent.add(textAg);

		// Initialisation de la zone de texte de la barre de statut
		textStatut = new JTextArea("");
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
				panelGraph.getHeight() + 150));
		scrollStatut.setViewportView(textStatut);
		graphJSon = new JPanel();
		graphAgent = new JPanel();

		// Action lors du clic sur l'item "Import"
		importMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser dialogue = new JFileChooser(new File("C:\\"));
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

				JsonToGS jSTGS = new JsonToGS();
				graph = jSTGS.generateGraph(directory.getText());
				setStyleGraph(graph);
				setNodeClass(graph);
				
				viewer = new Viewer(graph,
						Viewer.ThreadingModel.GRAPH_IN_ANOTHER_THREAD);
				viewer.enableAutoLayout();
				view = viewer.addDefaultView(false);

				graphJSon.removeAll();
				graphJSon.setLayout(new BorderLayout());
				graphJSon.add(view, BorderLayout.CENTER);
				scrollJSon.setViewportView(graphJSon);

				Graph g2 = new MultiGraph("graph2");

				viewer2 = new Viewer(g2,
						Viewer.ThreadingModel.GRAPH_IN_ANOTHER_THREAD);
				viewer2.enableAutoLayout();

				view2 = viewer2.addDefaultView(false);

				graphAgent.removeAll();
				graphAgent.setLayout(new BorderLayout());
				graphAgent.add(view2, BorderLayout.CENTER);
				scrollAgent.setViewportView(graphAgent);

				// Visualisation du graphe généré par rapport au 1er graphe
				for (Node n : graph.getEachNode()) {
					Node node = g2.addNode(n.getId());
					for (String attributeKey : n.getAttributeKeySet()) {
						node.addAttribute(attributeKey,
								n.getAttribute(attributeKey));
					}
				}

				for (Edge ed : graph.getEachEdge()) {
					Edge edge = g2.addEdge(ed.getId(), ed.getSourceNode()
							.getId(), ed.getTargetNode().getId(), true);
					for (String attributeKey : ed.getAttributeKeySet()) {
						edge.addAttribute(attributeKey,
								ed.getAttribute(attributeKey));
					}
				}

			}
		});

		// Action lors du clic sur l'item "+" de la partie gauche
		zoomAvantJSon.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				zoom = view.getCamera().getViewPercent();
				view = viewer.getDefaultView();
				view.getCamera().setViewPercent(zoom - 0.1);
			}
		});

		// Action lors du clic sur l'item "-" de la partie gauche
		zoomArrJSon.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dezoom = view.getCamera().getViewPercent();
				view = viewer.getDefaultView();
				view.getCamera().setViewPercent(dezoom + 0.1);
			}
		});

		// Action lors du clic sur l'item "%" de la partie gauche
		zoomTextJSon.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
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
					textStatut.append("Ce n'est pas un entier \n");
				} else {
					pourcentage = Integer.parseInt(s);
					view = viewer.getDefaultView();
					if (pourcentage > 100) {
						zoomAvant = pourcentage - 100;
						total = 1 - (zoomAvant / 100);
						view.getCamera().setViewPercent(total);
						textStatut
								.append("Zoom avant: " + pourcentage + "% \n");
					} else {
						zoomArr = 100 - pourcentage;
						total = 1 + (zoomArr / 100);
						view.getCamera().setViewPercent(total);
						textStatut.append("Zoom arrière: " + pourcentage
								+ "% \n");
					}
				}

			}
		});

		// Action lors du clic sur l'item "+" de la partie droite
		zoomAvantAg.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				zoom = view.getCamera().getViewPercent();
				view = viewer2.getDefaultView();
				view.getCamera().setViewPercent(zoom - 0.1);
			}
		});

		// Action lors du clic sur l'item "-" de la partie droite
		zoomArrAg.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dezoom = view.getCamera().getViewPercent();
				view = viewer2.getDefaultView();
				view.getCamera().setViewPercent(dezoom + 0.1);
			}
		});

		// Action lors du clic sur l'item "%" de la partie droite
		zoomTextAg.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
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
					textStatut.append("Ce n'est pas un entier \n");
				} else {
					pourcentage = Integer.parseInt(s);
					view = viewer2.getDefaultView();
					if (pourcentage > 100) {
						zoomAvant = pourcentage - 100;
						total = 1 - (zoomAvant / 100);
						view.getCamera().setViewPercent(total);
						textStatut
								.append("Zoom avant: " + pourcentage + "% \n");
					} else {
						zoomArr = 100 - pourcentage;
						total = 1 + (zoomArr / 100);
						view.getCamera().setViewPercent(total);
						textStatut.append("Zoom arrière: " + pourcentage
								+ "% \n");
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
		splitPane2.setDividerLocation((widthWindow - sizeSeparator - 130) / 2);

		panelGraph.add(zoomAgent, BorderLayout.EAST);
		panelGraph.add(zoomJSon, BorderLayout.WEST);
		panelGraph.add(splitPane2, BorderLayout.CENTER);
		panelGraph.add(scrollStatut, BorderLayout.SOUTH);

		// Définition de la fenêtre principale
		frame.add(splitPane);
		frame.setJMenuBar(menu_bar1);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(true);
		frame.setBounds(xWindow, yWindow, widthWindow, heightWindow);
		frame.setVisible(true);

	}

	public static void setStyleGraph(Graph graph) {
		graph.addAttribute("ui.quality");
		graph.addAttribute("ui.antialias");
		graph.addAttribute("ui.stylesheet", "edge { fill-color: grey; }"
				+ "node." + MyJsonGenerator.FORMAT_NODE_SOURCE
				+ "{ shape: triangle; }" + "node."
				+ MyJsonGenerator.FORMAT_NODE_FINAL + "{ fill-color: red;  }"
				+ "node." + MyJsonGenerator.FORMAT_NODE_SOURCE
				+ MyJsonGenerator.FORMAT_NODE_FINAL
				+ "{ shape: triangle; fill-color: red; }");
	}

	public static void setNodeClass(Graph graph) {
		Boolean isSource, isFinal;
		for (Node node : graph.getEachNode()) {
			isSource = node.getAttribute(MyJsonGenerator.FORMAT_NODE_SOURCE)
					.equals("true");
			isFinal = node.getAttribute(MyJsonGenerator.FORMAT_NODE_FINAL)
					.equals("true");
			if (isSource && isFinal) {
				node.setAttribute("ui.class",
						MyJsonGenerator.FORMAT_NODE_SOURCE
								+ MyJsonGenerator.FORMAT_NODE_FINAL);
			} else if (isSource) {
				node.setAttribute("ui.class",
						MyJsonGenerator.FORMAT_NODE_SOURCE);
			} else if (isFinal) {
				node.addAttribute("ui.class", MyJsonGenerator.FORMAT_NODE_FINAL);
			}
		}
	}

	public static void main(String[] args) {
		// remplacement du renderer par défaut
		System.setProperty("org.graphstream.ui.renderer",
				"org.graphstream.ui.j2dviewer.J2DGraphRenderer");
		Fenetre f = new Fenetre();
	}

	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub

	}
}