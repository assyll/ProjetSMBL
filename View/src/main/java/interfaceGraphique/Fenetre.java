package interfaceGraphique;

import java.awt.BorderLayout;
import java.awt.Choice;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
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

import org.graphstream.graph.Graph;
import org.graphstream.ui.swingViewer.Viewer;
import org.graphstream.ui.swingViewer.View;

@SuppressWarnings("serial")
public class Fenetre extends JFrame implements ActionListener {
	JFrame frame;
	JPanel panelFile;
	JPanel panelGraph;
	JScrollPane jsp;
	JSplitPane splitPane, splitPane2;
	JPanel panelboutton;
	JPanel panelgen;
	JPanel graphJSon;
	JScrollPane scrollJSon;
	JPanel graphAgent;
	JTextField directory;
	JButton buttonGS;

	final int xWindow = 300;
	final int yWindow = 100;
	final int widthWindow = 800;
	final int heightWindow = 600;
	final int sizeSeparator = 5;

	Fenetre() {
		
		frame = new JFrame("Projet SMBL");

		panelFile = new JPanel(new GridLayout(1,2,20,5));

		panelFile.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createTitledBorder("JSon File"),
				BorderFactory.createEmptyBorder(1, 1, 1, 1)));

		buttonGS = new JButton("To GraphStream");
		directory = new JTextField("Directory");
		directory.setPreferredSize(new Dimension(250, 20));
		directory.setEditable(false);

		panelFile.add(directory);
		panelFile.add(buttonGS);

		JMenuBar menu_bar1 = new JMenuBar();
		JMenu menu1 = new JMenu("File");
		
		/* differents choix du menu */
		JMenuItem importMenu = new JMenuItem("Import");
		JMenuItem aboutUs = new JMenuItem("About Us");
		JMenuItem exitMenu = new JMenuItem("Exit");

		menu1.add(importMenu);
		menu1.add(exitMenu);

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
		
		buttonGS.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Graph g;
				g = JsonToGS.generateGraph(directory.getText());
				Viewer vue = new Viewer(g, Viewer.ThreadingModel.GRAPH_IN_ANOTHER_THREAD);
			    vue.enableAutoLayout();
			   
			    View view = vue.addDefaultView(false);
			    graphJSon.setLayout(new BorderLayout());
			    graphJSon.add(view, BorderLayout.CENTER);
			    scrollJSon.setViewportView(graphJSon);
			}
		});

		menu_bar1.add(menu1);

		panelGraph = new JPanel();
		panelGraph.setLayout(null);
		panelGraph.setLayout(new BoxLayout(panelGraph, BoxLayout.Y_AXIS));
		panelGraph.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createTitledBorder("Graphs"),
				BorderFactory.createEmptyBorder(1, 1, 1, 1)));

		// on créé le splitPane avec une separation Horizontal (barre à la
		// vertical)
		splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, panelFile,
				panelGraph);
		
		// Place la barre de séparation a 50 px
		splitPane.setDividerLocation(50);

		graphJSon = new JPanel();
		graphAgent = new JPanel();
		scrollJSon = new JScrollPane();

		splitPane2 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, scrollJSon,
				graphAgent);
		splitPane2.setDividerSize(5);
		splitPane2.setDividerLocation((widthWindow - sizeSeparator) / 2);

		panelGraph.add(splitPane2);
		frame.add(splitPane);
		frame.setJMenuBar(menu_bar1);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setBounds(xWindow, yWindow, widthWindow, heightWindow);
		frame.setVisible(true);

	}

	public static void main(String[] args) {
		Fenetre f = new Fenetre();
	}

	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub

	}
}