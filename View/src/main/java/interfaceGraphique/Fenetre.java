package interfaceGraphique;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class Fenetre extends JFrame implements ActionListener {

	private JPanel panButton = new JPanel();
	private JPanel panGraphs = new JPanel(new GridLayout(1,50,2,5));
	private JButton buttonImport = new JButton("Import");
	private JButton buttonGS = new JButton("to GraphStream");
	private JTextField chemin = new JTextField("Chemin du fichier");
	private JScrollPane graphJson = new JScrollPane();
	private JScrollPane graphAgent = new JScrollPane();

	public Fenetre() {
		this.setTitle("Projet");
		this.setSize(800, 600);
		this.getContentPane().setLayout(new GridLayout(2,50,1,5));
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		chemin.setPreferredSize(new Dimension(250, 20));
		
		panButton.add(buttonImport);
		panButton.add(chemin);
		panButton.add(buttonGS);
		this.getContentPane().add(panButton);
		
		panGraphs.add(graphJson);
		panGraphs.add(graphAgent);
		this.getContentPane().add(panGraphs);
		
		this.setVisible(true);
		
		buttonImport.addActionListener(this);
		
	}

	public static void main(String[] args) {

		Fenetre fen = new Fenetre();
	}

	public void actionPerformed(ActionEvent arg0) {
		
		JFileChooser dialogue = new JFileChooser(new File("C:\\"));
		File fichier;
		
		if(dialogue.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			fichier = dialogue.getSelectedFile();
			chemin.setText(fichier.toString());
		}
	}
	
}