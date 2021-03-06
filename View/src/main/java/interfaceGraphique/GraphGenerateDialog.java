package interfaceGraphique;

import generatorGrapheRefAleat.GeneratorGraph;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import convertGraph.Fichier;

@SuppressWarnings("serial")
public class GraphGenerateDialog extends JDialog{

	private static String nodeOld = "", transOld = "";
	
	private JFrame jFrame;
	private boolean generatedWithSuccess;
	private JLabel nodeLabel, transLabel, finauxLabel;
	private JTextField nodeField, transField, finauxField;
	
	public GraphGenerateDialog (JFrame jFrame) {
		super(jFrame, "Generate Graph", true);
		
		this.jFrame = jFrame;
		this.generatedWithSuccess = false;
		
		setSize(600, 325);
		setLocationRelativeTo(null);
		setResizable(false);
		initComponent();
		putValuesOld();
	}
	
	private void initComponent() {
		// node
		JPanel nodePanel = new JPanel();
		nodePanel.setBackground(Color.WHITE);
		nodePanel.setPreferredSize(new Dimension(550, 75));
		nodeField = new JTextField();
		nodeField.setFont(new Font(" TimesRoman ",Font.PLAIN,20));
		nodeField.setPreferredSize(new Dimension(350, 40));
		nodePanel.setBorder(BorderFactory.
				createTitledBorder("Nodes number"));
		nodeLabel = new JLabel("Enter a number :");
		nodePanel.add(nodeLabel);
		nodePanel.add(nodeField);
		
		// transition
		JPanel transPanel = new JPanel();
		transPanel.setBackground(Color.WHITE);
		transPanel.setPreferredSize(new Dimension(550, 75));
		transField = new JTextField();
		transField.setFont(new Font(" TimesRoman ",Font.PLAIN,20));
		transField.setPreferredSize(new Dimension(350, 40));
		transPanel.setBorder(BorderFactory.
				createTitledBorder("Max relationship number by node"));
		transLabel = new JLabel("Enter a number :");
		transPanel.add(transLabel);
		transPanel.add(transField);
		
		// etats finaux
		JPanel finauxPanel = new JPanel();
		finauxPanel.setBackground(Color.WHITE);
		finauxPanel.setPreferredSize(new Dimension(550, 75));
		finauxField = new JTextField();
		finauxField.setFont(new Font(" TimesRoman ",Font.PLAIN,20));
		finauxField.setPreferredSize(new Dimension(350, 40));
		finauxPanel.setBorder(BorderFactory.
				createTitledBorder("final state number"));
		finauxLabel = new JLabel("Enter a number :");
		finauxPanel.add(finauxLabel);
		finauxPanel.add(finauxField);
		
		// panel principal
		JPanel mainPanel = new JPanel();
		mainPanel.setBackground(Color.WHITE);
		mainPanel.add(nodePanel);
		mainPanel.add(transPanel);
		mainPanel.add(finauxPanel);
		
		// panel des boutons
		JPanel buttonPanel = new JPanel();
		
		JButton okButton = new JButton("Generate");
		okButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				registerValuesOld();
				
				int nbNodes, nbTrans, nbFinaux;
				try {
					nbNodes = Integer.parseInt(nodeField.getText());
				} catch (Exception e) {
					JOptionPane.showMessageDialog(
							GraphGenerateDialog.this.getContentPane(),
							"Enter a number for node please");
					return;
				}
				
				try {
					nbTrans = Integer.parseInt(transField.getText());
				} catch (Exception e) {
					JOptionPane.showMessageDialog(
							GraphGenerateDialog.this.getContentPane(),
							"Enter a number for relationship please");
					return;
				}
				
				try {
					nbFinaux = Integer.parseInt(finauxField.getText());
				} catch (Exception e) {
					JOptionPane.showMessageDialog(
							GraphGenerateDialog.this.getContentPane(),
							"Enter a number for final state please");
					return;
				}
				
				JOptionPane jOptionPane = new JOptionPane();
				
				((Window) jFrame).ajouterMessageToTextColorStatut(
						"Graph is building ...");
				/*jOptionPane.showMessageDialog(
						GraphGenerateDialog.this.getContentPane(),
						"Graph is building ...");*/
				
				setVisible(false);
				String success = genererLeGraphe(nbNodes, nbTrans, nbFinaux)
						? "success" : "failure";
				
				jOptionPane.setVisible(false);
				/*JOptionPane.showMessageDialog(
						GraphGenerateDialog.this.getContentPane(),
						"Graph generated with " + success + " !");*/
				((Window) jFrame).ajouterMessageToTextColorStatut(
						"Graph generated with " + success + " !");
			}
		});
		
		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				registerValuesOld();
				setVisible(false);
			}
			
		});
		
		buttonPanel.add(okButton);
		buttonPanel.add(cancelButton);
		buttonPanel.setBackground(Color.WHITE);
		
		getContentPane().add(mainPanel, BorderLayout.CENTER);
		getContentPane().add(buttonPanel, BorderLayout.SOUTH);
	}
	
	private void putValuesOld() {
		nodeField.setText(nodeOld);
		transField.setText(transOld);
	}
	
	private void registerValuesOld() {
		nodeOld = nodeField.getText();
		transOld = transField.getText();
	}
	
	private boolean genererLeGraphe(int nbNodes, int nbTrans, int nbFinaux) {
		GeneratorGraph generator = new GeneratorGraph();
		
		try {
			Fichier.deleteFileOrDirectory(Window.pathGraphTemp);
			generator.generateGrapheAleat(Window.pathGraphTemp,
					nbNodes, nbTrans, nbFinaux);
			generatedWithSuccess = true;
		} catch (Exception e) {
			return false;
		}
		return true;
	}
	
	public boolean isGeneratedWithSuccess() {
		return generatedWithSuccess;
	}
	
}
