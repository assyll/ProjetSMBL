package interfaceGraphique;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class Fenetre extends JFrame implements ActionListener {

	private JPanel pan = new JPanel();
	private JButton buttonImport = new JButton("Import");
	private JTextField chemin = new JTextField("Chemin du fichier");

	public Fenetre() {
		this.setTitle("Projet");
		this.setSize(400, 600);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		chemin.setPreferredSize( new Dimension( 250, 20 ) );

		pan.add(buttonImport);
		pan.add(chemin);
		
		this.setContentPane(pan);
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