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

@SuppressWarnings("serial")
public class GraphGenerateDialog extends JDialog{

	private String path;
	private JLabel pathLabel, nodeLabel, transLabel;
	private JTextField pathField, nodeField, transField;
	
	public GraphGenerateDialog (JFrame jFrame) {
		super(jFrame, "Generate Graph", true);
		setSize(600, 300);
		setLocationRelativeTo(null);
		setResizable(false);
		initComponent();
	}
	
	private void initComponent() {
		// path
		JPanel pathPanel = new JPanel();
		pathPanel.setBackground(Color.WHITE);
		pathPanel.setPreferredSize(new Dimension(550, 75));
		pathField = new JTextField();
		pathField.setFont(new Font(" TimesRoman ",Font.PLAIN,20));
		pathField.setPreferredSize(new Dimension(350, 40));
		pathPanel.setBorder(BorderFactory.
				createTitledBorder("Save path"));
		pathLabel = new JLabel("Enter a name   :");
		pathPanel.add(pathLabel);
		pathPanel.add(pathField);
		
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
		
		// panel principal
		JPanel mainPanel = new JPanel();
		mainPanel.setBackground(Color.WHITE);
		mainPanel.add(pathPanel);
		mainPanel.add(nodePanel);
		mainPanel.add(transPanel);
		
		// panel des boutons
		JPanel buttonPanel = new JPanel();
		
		JButton okButton = new JButton("Generate");
		okButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				int nbNodes, nbTrans;
				path = pathField.getText();
				
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
				
				setVisible(false);
				String success = genererLeGraphe(nbNodes, nbTrans)
						? "success" : "failure";
				JOptionPane.showMessageDialog(
						GraphGenerateDialog.this.getContentPane(),
						"Graph generated with " + success + " !");
			}
		});
		
		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
			
		});
		
		buttonPanel.add(okButton);
		buttonPanel.add(cancelButton);
		
		getContentPane().add(mainPanel, BorderLayout.CENTER);
		getContentPane().add(buttonPanel, BorderLayout.SOUTH);
	}
	
	private boolean genererLeGraphe(int nbNodes, int nbTrans) {
		GeneratorGraph generator = new GeneratorGraph();
		
		try {
			generator.generateGrapheAleat("./src/test/resources/" + path,
					nbNodes, nbTrans);
		} catch (Exception e) {
			path = null;
			return false;
		}
		return true;
	}
	
	public String getPath() {
		return path;
	}
	
}
