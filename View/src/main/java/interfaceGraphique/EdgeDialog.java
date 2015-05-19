package interfaceGraphique;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

@SuppressWarnings("serial")
public class EdgeDialog extends JDialog implements ActionListener {

	JTextField nameEdge, actionEdge, nbAttributsEdge;
	JLabel labelName, labelSource, labelEnd, labelAction, labelNbAtt;
	JButton ok, cancel;
	JFrame frame;
	JComboBox<String> sourceEdge, endEdge;
	String name, sourceE, endE, actionE;
	String[] nodes;
	int nbAtt;
	boolean ferme, check;
	AttributDialog attDialog;
	Graph graph;

	@SuppressWarnings({ "static-access", "unchecked", "rawtypes" })
	public EdgeDialog(JFrame f, String s, Graph g) {
		super(f, s, true);
		this.setDefaultCloseOperation(this.DISPOSE_ON_CLOSE);
		frame = f;
		graph = g;
		nodes = getNodes();
		Box boite = Box.createVerticalBox();
		JPanel panelDialog = new JPanel();
		panelDialog.setLayout(new GridLayout(6, 2, 20, 5));

		labelName = new JLabel("Label of Edge?");
		panelDialog.add(labelName);
		nameEdge = new JTextField(10);
		panelDialog.add(nameEdge);

		labelSource = new JLabel("What's the start Node?");
		panelDialog.add(labelSource);
		sourceEdge = new JComboBox(nodes);
		panelDialog.add(sourceEdge);

		labelEnd = new JLabel("What's the end Node?");
		panelDialog.add(labelEnd);
		endEdge = new JComboBox(nodes);
		panelDialog.add(endEdge);

		labelAction = new JLabel("What's the action?");
		panelDialog.add(labelAction);
		actionEdge = new JTextField(10);
		panelDialog.add(actionEdge);

		labelNbAtt = new JLabel("How many attributs?");
		panelDialog.add(labelNbAtt);
		nbAttributsEdge = new JTextField(10);
		panelDialog.add(nbAttributsEdge);

		ok = new JButton("Ok");
		panelDialog.add(ok);

		cancel = new JButton("Cancel");
		panelDialog.add(cancel);

		ok.addActionListener(this);
		cancel.addActionListener(this);

		boite.add(panelDialog);
		this.add(boite);
		this.setBounds(400, 200, 300, 300);
		this.setVisible(true);
	}

	public String getLabel() {
		return (nameEdge.getText());
	}

	public String getSource() {
		return (sourceEdge.getSelectedItem().toString());
	}

	public String getEnd() {
		return (endEdge.getSelectedItem().toString());
	}

	public String getAction() {
		return (actionEdge.getText());
	}

	public int getNbAtt() {
		if(nbAttributsEdge.getText().isEmpty()){
			return 0;
		} else {
			return Integer.parseInt(nbAttributsEdge.getText());
		}
	}

	public String[] getAttributs() {
		if (attDialog == null) {
			return null;
		} else {
			return (attDialog.getAttributs());
		}
	}

	public String[] getNodes() {
		String[] tmp = new String[graph.getNodeCount()];
		int cpt = 0;
		for (Node node : graph.getEachNode()) {
			tmp[cpt++] = node.toString();
		}
		return tmp;
	}

	public JFrame getFrame() {
		return frame;
	}

	public boolean getFerme() {
		return ferme;
	}
	
	public boolean getCheck(){
		return check;
	}

	public void actionPerformed(ActionEvent evt) {
		if (evt.getSource() == ok) {
			ferme = false;
			check = true;

			if (!ferme) {
				name = getLabel();
				sourceE = getSource();
				endE = getEnd();
				actionE = getAction();
				nbAtt = getNbAtt();

				if (nbAtt != 0) {
					attDialog = new AttributDialog(getFrame(),
							"Attributs Node", getNbAtt());
				}

				dispose();
			}
		} else if (evt.getSource() == cancel) {
			ferme = true;
			check = false;
			dispose();
		}
	}
}
