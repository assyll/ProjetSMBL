package interfaceGraphique;

import java.awt.BorderLayout;
import java.awt.Choice;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import jsonAndGS.*;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JTree;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.MultiGraph;
import org.graphstream.ui.swingViewer.Viewer;
import org.graphstream.ui.swingViewer.View;

@SuppressWarnings("serial")
public class Fenetre extends JFrame implements ActionListener {

	// Instanciation des diff�rents Components

	JFrame frame;

	JPanel panelFile, panelGraph, panelboutton, panelgen, graphJSon,
			graphAgent, zoomJSon, zoomAgent, barreStatut;

	JSplitPane splitPane, splitPane2;

	JScrollPane scrollJSon, scrollAgent, scrollStatut;

	JTextField directory;

	JButton buttonGS, zoomAvantJSon, zoomArrJSon, zoomTotalJSon, zoomAvantAg,
			zoomArrAg, zoomTotalAg;

	JMenuBar menu_bar1;

	JMenu menu1;

	JMenuItem importMenu, exitMenu;

	final int xWindow = 50, yWindow = 0, widthWindow = 1280,
			heightWindow = 700, sizeSeparator = 5;

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

		// Initialisation des bouttons de zoom
		zoomAvantJSon = new JButton("<html><b>+</b></html>");
		zoomArrJSon = new JButton("<html><b>-</b></html>");
		zoomTotalJSon = new JButton("<html><b>%</b></html>");
		zoomAvantAg = new JButton("<html><b>+</b></html>");
		zoomArrAg = new JButton("<html><b>-</b></html>");
		zoomTotalAg = new JButton("<html><b>%</b></html>");

		// Initialisation et d�finition du panneau pour zoomer le graphe Json
		zoomJSon = new JPanel(new GridLayout(3, 1, 20, 5));
		zoomJSon.add(zoomAvantJSon);
		zoomJSon.add(zoomArrJSon);
		zoomJSon.add(zoomTotalJSon);

		// Initialisation et d�finition du panneau pour zoomer le graphe Agent
		zoomAgent = new JPanel(new GridLayout(3, 1, 20, 5));
		zoomAgent.add(zoomAvantAg);
		zoomAgent.add(zoomArrAg);
		zoomAgent.add(zoomTotalAg);

		// Initialisation du panneau de statut
		barreStatut = new JPanel();
		barreStatut.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createTitledBorder("Statut"),
				BorderFactory.createEmptyBorder(1, 1, 1, 1)));
		barreStatut.setPreferredSize(new Dimension(700,150));

		// Ajout de Components au 1er panneau
		buttonGS = new JButton("To GraphStream");
		directory = new JTextField("Directory");
		directory.setPreferredSize(new Dimension(250, 20));
		directory.setEditable(false);
		panelFile.add(directory);
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
		scrollStatut.setViewportView(barreStatut);
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
				// Visualisation du graph g�n�r� par le fichier import� au
				// format .json
				Graph graph;
				JsonToGS jSTGS = new JsonToGS();
				graph = jSTGS.generateGraph(directory.getText());
				setStyleGraph(graph);
				setNodeClass(graph);
				Viewer vue = new Viewer(graph,
						Viewer.ThreadingModel.GRAPH_IN_ANOTHER_THREAD);
				vue.enableAutoLayout();

				View view = vue.addDefaultView(false);

				graphJSon.removeAll();
				graphJSon.setLayout(new BorderLayout());
				graphJSon.add(view, BorderLayout.CENTER);
				scrollJSon.setViewportView(graphJSon);

				Graph g2 = new MultiGraph("graph2");

				Viewer vue2 = new Viewer(g2,
						Viewer.ThreadingModel.GRAPH_IN_ANOTHER_THREAD);
				vue2.enableAutoLayout();

				View view2 = vue2.addDefaultView(false);

				graphAgent.removeAll();
				graphAgent.setLayout(new BorderLayout());
				graphAgent.add(view2,  BorderLayout.CENTER);
				scrollAgent.setViewportView(graphAgent);

				// Visualisation du graphe g�n�r� par rapport au 1er graphe
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

		// Action lors du clic sur l'item "+"
		zoomAvantJSon.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
			}
		});

		// Action lors du clic sur l'item "+"
		zoomArrJSon.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
			}
		});

		// Action lors du clic sur l'item "+"
		zoomTotalJSon.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
			}
		});

		// Action lors du clic sur l'item "+"
		zoomAvantAg.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
			}
		});

		// Action lors du clic sur l'item "+"
		zoomArrAg.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
			}
		});

		// Action lors du clic sur l'item "+"
		zoomTotalAg.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
			}
		});

		// Cr�ation et d�finition du splitPane de la fen�tre principale
		splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, panelFile,
				panelGraph);
		splitPane.setDividerLocation(50);

		// Cr�ation et d�finition du splitPane qui sera dans le 2nd panneau
		splitPane2 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, scrollJSon,
				scrollAgent);
		splitPane2.setDividerSize(5);
		splitPane2.setDividerLocation((widthWindow - sizeSeparator - 130) / 2);

		panelGraph.add(zoomAgent, BorderLayout.EAST);
		panelGraph.add(zoomJSon, BorderLayout.WEST);
		panelGraph.add(splitPane2, BorderLayout.CENTER);
		panelGraph.add(scrollStatut, BorderLayout.SOUTH);

		// D�finition de la fen�tre principale
		frame.add(splitPane);
		frame.setJMenuBar(menu_bar1);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(true);
		frame.setBounds(xWindow, yWindow, widthWindow, heightWindow);
		frame.setVisible(true);

	}
	
	public void setStyleGraph(Graph graph) {
		graph.addAttribute("ui.quality");
		graph.addAttribute("ui.antialias");
		graph.addAttribute("ui.stylesheet", "edge { fill-color: grey; }"
				+ "node." + MyJsonGenerator.FORMAT_NODE_SOURCE + "{ shape: cross; }"
				+ "node." + MyJsonGenerator.FORMAT_NODE_FINAL + "{ fill-color: red; }"
				+ "node." + MyJsonGenerator.FORMAT_NODE_SOURCE + MyJsonGenerator.FORMAT_NODE_FINAL + "{ shape: cross; fill-color: red; }");
	}

	public void setNodeClass(Graph graph) {
		Boolean isSource, isFinal;
		for (Node node : graph.getEachNode()) {
			isSource = node.getAttribute(MyJsonGenerator.FORMAT_NODE_SOURCE)
					.equals("true");
			isFinal = node.getAttribute(MyJsonGenerator.FORMAT_NODE_FINAL)
					.equals("true");
			if (isSource && isFinal) {
				node.setAttribute("ui.class",
						MyJsonGenerator.FORMAT_NODE_SOURCE + MyJsonGenerator.FORMAT_NODE_FINAL);
			} else if(isSource){
				node.setAttribute("ui.class",
						MyJsonGenerator.FORMAT_NODE_SOURCE);
			} else if(isFinal){
				node.setAttribute("ui.class",
						MyJsonGenerator.FORMAT_NODE_FINAL);
			}
		}
	}

	public static void main(String[] args) {
		Fenetre f = new Fenetre();
	}

	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub

	}
}