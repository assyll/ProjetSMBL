package interfaceGraphique;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

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
public class AddEdgeDialog extends JDialog implements ActionListener {

	private JTextField nameEdge, actionEdge, nbAttributsEdge;
	private JLabel labelName, labelSource, labelEnd, labelAction, labelNbAtt;
	private JButton ok, cancel;
	private JFrame frame;
	private JComboBox<String> sourceEdge, endEdge;
	private String[] nodes;
	private int nbAtt;
	private boolean ferme, check;
	private AttributDialog attDialog;
	private Graph graph;

	@SuppressWarnings({ "static-access", "unchecked", "rawtypes" })
	public AddEdgeDialog(JFrame f, String s, Graph g) {
		super(f, s, true);
		this.setDefaultCloseOperation(this.DISPOSE_ON_CLOSE);

		this.addWindowListener(new WindowListener() {

			public void windowOpened(WindowEvent e) {

			}

			public void windowIconified(WindowEvent e) {

			}

			public void windowDeiconified(WindowEvent e) {

			}

			public void windowDeactivated(WindowEvent e) {

			}

			public void windowClosing(WindowEvent e) {
				ferme = true;
				check = false;
				nameEdge.setText("");
			}

			public void windowClosed(WindowEvent e) {

			}

			public void windowActivated(WindowEvent e) {

			}
		});

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
		if (nbAttributsEdge.getText().isEmpty()) {
			return 0;
		} else {
			return Integer.parseInt(nbAttributsEdge.getText());
		}
	}

	public String[][] getAttributs() {
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

	public boolean getCheck() {
		return check;
	}

	public void actionPerformed(ActionEvent evt) {
		if (evt.getSource() == ok) {
			ferme = false;
			check = true;

			if (!ferme) {
				nbAtt = getNbAtt();
				
				if (nbAtt != 0) {
					attDialog = new AttributDialog(getFrame(),
							"Attributs Node", getNbAtt());
					if (attDialog.isExit()) {
						ferme = true;
						check = false;
						nameEdge.setText("");
					}
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
