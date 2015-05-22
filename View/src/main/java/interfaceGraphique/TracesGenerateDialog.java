package interfaceGraphique;

import generatorTracesTest.GeneratorTraces;
import generatorTracesTest.Trace;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.List;
import java.util.Map.Entry;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import jsonAndGS.TracesToJson;

import org.graphstream.graph.Graph;

import convertGraph.ConvertGStoNeo4j;
import convertGraph.Fichier;

@SuppressWarnings("serial")
public class TracesGenerateDialog extends JDialog {

	private String path;
	private boolean possible;
	private boolean oneByFile;
	private int methodEnabled;
	private JPanel methodPanel, tracesPanel, maxActionsPanel, stopToFinalPanel,
	               withRepetitionPanel, percentToCoverPanel;
	private JLabel methodLabel, nbTracesLabel, maxActionsLabel,
	               stopToFinalLabel, withRepetitionLabel, percentToCoverLabel;
	private JTextField nbTracesField, maxActionsField, percentToCoverField;
	private JRadioButton methodRButton1, methodRButton2, methodRButton3;
	private JComboBox stopToFinalComboBox, withRepetitionComboBox;
	
	private GeneratorTraces generatorTraces;
	
	public TracesGenerateDialog (JFrame jFrame,
			boolean oneByFile, Graph graphGS, boolean isLoaded) {
		
		super(jFrame, "Traces Generate", true);
		this.path = "./src/test/resources";
		this.oneByFile = oneByFile;
		
		setSize(700, 320);
		setLocationRelativeTo(null);
		setResizable(false);
		
		initComponent();
		activer(1);
		
		if (isLoaded) {
			initGeneratorTraces(graphGS);
			possible = (generatorTraces != null);
		} else {
			possible = false;
		}
	}
	
	public void close() {
		setVisible(false);
		dispose();
	}
	
	@Override
	public void show() {
		if (possible) {
			super.show();
		} else {
			errorGraph();
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void initComponent() {
		// method
		methodPanel = new JPanel();
		methodPanel.setBackground(Color.WHITE);
		methodPanel.setPreferredSize(new Dimension(650, 75));
		
		methodRButton1 = new JRadioButton("Randomly");
		methodRButton2 = new JRadioButton("Randomly covering");
		methodRButton3 = new JRadioButton("Intelligently covering");
		
		ButtonGroup buttonGroup = new ButtonGroup();
		buttonGroup.add(methodRButton1);
		buttonGroup.add(methodRButton2);
		buttonGroup.add(methodRButton3);
		
		methodLabel = new JLabel("Choose a method :");
		
		methodPanel.setBorder(BorderFactory.
				createTitledBorder("Traces generate method"));
		methodPanel.add(methodLabel);
		methodPanel.add(methodRButton1);
		methodPanel.add(methodRButton2);
		methodPanel.add(methodRButton3);
		
		methodRButton1.addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent arg0) {
				activer(1);
			}
			@Override
			public void focusLost(FocusEvent arg0) {
				// rien
			}
		});
		
		methodRButton2.addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent arg0) {
				activer(2);
			}
			@Override
			public void focusLost(FocusEvent arg0) {
				// rien
			}
		});
		
		methodRButton3.addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent arg0) {
				activer(3);
			}
			@Override
			public void focusLost(FocusEvent arg0) {
				// rien
			}
		});
		
		// Nombre de traces
		tracesPanel = new JPanel();
		tracesPanel.setBackground(Color.WHITE);
		tracesPanel.setPreferredSize(new Dimension(325, 75));
		
		nbTracesField = new JTextField();
		nbTracesField.setFont(new Font(" TimesRoman ", Font.PLAIN, 20));
		nbTracesField.setPreferredSize(new Dimension(150, 40));
		
		nbTracesLabel = new JLabel("Enter a number :");
		
		tracesPanel.setBorder(BorderFactory.
				createTitledBorder("Traces number"));
		tracesPanel.add(nbTracesLabel);
		tracesPanel.add(nbTracesField);
		
		// Nombre d'actions maximum
		maxActionsPanel = new JPanel();
		maxActionsPanel.setBackground(Color.WHITE);
		maxActionsPanel.setPreferredSize(new Dimension(325, 75));
		
		maxActionsField = new JTextField();
		maxActionsField.setFont(new Font(" TimesRoman ", Font.PLAIN, 20));
		maxActionsField.setPreferredSize(new Dimension(150, 40));
		
		maxActionsLabel = new JLabel("Enter a number :");
		
		maxActionsPanel.setBorder(BorderFactory.
				createTitledBorder("Max Actions number by trace"));
		maxActionsPanel.add(maxActionsLabel);
		maxActionsPanel.add(maxActionsField);
		
		// Stop to final
		stopToFinalPanel = new JPanel();
		stopToFinalPanel.setBackground(Color.white);
		stopToFinalPanel.setPreferredSize(new Dimension(200, 75));
		stopToFinalPanel.setBorder(BorderFactory.
				createTitledBorder("Stop to final state"));
		
	    stopToFinalComboBox = new JComboBox();
	    stopToFinalComboBox.addItem("true");
	    stopToFinalComboBox.addItem("false");
	    
	    stopToFinalLabel = new JLabel("Choose :");
	    
	    stopToFinalPanel.add(stopToFinalLabel);
	    stopToFinalPanel.add(stopToFinalComboBox);
	    
		// With Repetition
		withRepetitionPanel = new JPanel();
		withRepetitionPanel.setBackground(Color.white);
		withRepetitionPanel.setPreferredSize(new Dimension(200, 75));
		withRepetitionPanel.setBorder(BorderFactory.
				createTitledBorder("with traces repetition"));
		
	    withRepetitionComboBox = new JComboBox();
	    withRepetitionComboBox.addItem("true");
	    withRepetitionComboBox.addItem("false");
	    
	    withRepetitionLabel = new JLabel("Choose :");
	    
	    withRepetitionPanel.add(withRepetitionLabel);
	    withRepetitionPanel.add(withRepetitionComboBox);
	    
	    // percent to cover
 		percentToCoverPanel = new JPanel();
 		percentToCoverPanel.setBackground(Color.WHITE);
 		percentToCoverPanel.setPreferredSize(new Dimension(250, 75));
 		
 		percentToCoverField = new JTextField();
 		percentToCoverField.setFont(new Font(" TimesRoman ", Font.PLAIN, 20));
 		percentToCoverField.setPreferredSize(new Dimension(75, 40));
 		
 		percentToCoverLabel = new JLabel("Enter a number :");
 		
 		percentToCoverPanel.setBorder(BorderFactory.
 				createTitledBorder("Percent to cover (0 to 100)"));
 		percentToCoverPanel.add(percentToCoverLabel);
 		percentToCoverPanel.add(percentToCoverField);
		
		// panel principal
		JPanel mainPanel = new JPanel();
		mainPanel.add(methodPanel);
		mainPanel.add(tracesPanel);
		mainPanel.add(maxActionsPanel);
		mainPanel.add(stopToFinalPanel);
		mainPanel.add(withRepetitionPanel);
		mainPanel.add(percentToCoverPanel);
		mainPanel.setBackground(Color.WHITE);
		
		// panel des boutons
		JPanel buttonPanel = new JPanel();
		
		JButton okButton = new JButton("Generate");
		okButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				actionClickToGenerate();
			}
		});
		
		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				TracesGenerateDialog.this.close();
			}
			
		});
		
		buttonPanel.add(okButton);
		buttonPanel.add(cancelButton);
		buttonPanel.setBackground(Color.WHITE);
		
		getContentPane().add(mainPanel, BorderLayout.CENTER);
		getContentPane().add(buttonPanel, BorderLayout.SOUTH);
	}
	
	private void activer(int method) {
		methodEnabled = method;
		boolean isMethod1 = (method == 1);
		boolean isMethod2 = (method == 2);
		boolean isMethod3 = (method == 3);
		
		methodRButton1.setSelected(isMethod1);
		methodRButton2.setSelected(isMethod2);
		methodRButton3.setSelected(isMethod3);
		
		tracesPanel.setEnabled(isMethod1);
		nbTracesLabel.setEnabled(isMethod1);
		nbTracesField.setEnabled(isMethod1);
		
		maxActionsPanel.setEnabled(isMethod1 || isMethod3);
		maxActionsLabel.setEnabled(isMethod1 || isMethod3);
		maxActionsField.setEnabled(isMethod1 || isMethod3);
		
		stopToFinalPanel.setEnabled(isMethod1);
		stopToFinalLabel.setEnabled(isMethod1);
		stopToFinalComboBox.setEnabled(isMethod1);
		
		withRepetitionPanel.setEnabled(isMethod1);
		withRepetitionLabel.setEnabled(isMethod1);
		withRepetitionComboBox.setEnabled(isMethod1);
		
		percentToCoverPanel.setEnabled(isMethod2);
		percentToCoverLabel.setEnabled(isMethod2);
		percentToCoverField.setEnabled(isMethod2);
	}
	
	private void actionClickToGenerate() {
		List<Trace> traces = null;
		switch (methodEnabled) {
		case 1:
			traces = enableMethod1();
			break;
		case 2:
			traces = enableMethod2();
			break;
		case 3:
			traces = enableMethod3();
			break;
		}
		
		if (traces != null) {
			writeTracesInFichier(traces);
			close();
		}
	}
	
	private List<Trace> enableMethod1() {
		try {
			int nbTraces = Integer.parseInt(nbTracesField.getText());
			int maxActions = Integer.parseInt(maxActionsField.getText());
			boolean stopToFinal = Boolean.parseBoolean(
					(String) stopToFinalComboBox.getSelectedItem());
			boolean withRepetition = Boolean.parseBoolean(
					(String) withRepetitionComboBox.getSelectedItem());
			
			return generatorTraces.traceGenerateAleat(
					nbTraces, maxActions, stopToFinal, withRepetition);
		} catch (Exception e) {
			errorNumber();
			return null;
		}
	}
	
	private List<Trace> enableMethod2() {
		try {
			int percentToCover = Integer.parseInt(
					percentToCoverField.getText());
			return generatorTraces.traceGenerateCoverageAleat(percentToCover);
		} catch (Exception e) {
			errorNumber();
			return null;
		}
	}

	private List<Trace> enableMethod3() {
		try {
			int maxActions = Integer.parseInt(maxActionsField.getText());
			return generatorTraces.traceGenerateCoverageIntelligent(maxActions);
		} catch (Exception e) {
			errorNumber();
			return null;
		}
	}
	
	private void errorNumber() {
		JOptionPane.showMessageDialog(
				getContentPane(), "Enter a valid number please");
	}
	
	private void errorGraph() {
		JOptionPane.showMessageDialog(
				getContentPane(), "No graph");
	}
	
	private void successMessage() {
		JOptionPane.showMessageDialog(
				getContentPane(), "Traces generated with success");
	}
	
	private void failureMessage() {
		JOptionPane.showMessageDialog(
				getContentPane(), "Traces generate failure");
	}
	
	private void initGeneratorTraces(Graph graphGS) {
		
		String pathInter = "graphInterQuiVaEtreSupp";
		Fichier.deleteFileOrDirectory(pathInter);
		
		try {
			new ConvertGStoNeo4j(graphGS).convertToNeo4j(pathInter);
			generatorTraces = new GeneratorTraces(pathInter);
		} catch (Exception e) {
			errorGraph();
		}
		
		if (generatorTraces == null) {
			errorGraph();
		}
	}
	
	private void writeTracesInFichier(List<Trace> traces) {
		boolean isWrited = true;
		
		path += "/testTrace";
		if (oneByFile) {
			for (int i = 0; i < traces.size(); i++) {
				isWrited = isWrited &&
						TracesToJson.write(traces.get(i), path + "_" + i);
			}
		} else {
			isWrited = isWrited && TracesToJson.write(traces, path);
		}
		
		if (isWrited) {
			successMessage();
		} else {
			failureMessage();
		}
	}
	
}
