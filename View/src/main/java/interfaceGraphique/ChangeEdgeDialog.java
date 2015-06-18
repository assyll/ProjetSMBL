package interfaceGraphique;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import jsonAndGS.MyJsonGenerator;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;

@SuppressWarnings("serial")
public class ChangeEdgeDialog extends JDialog implements ActionListener {

	private static JTextField actionEdge;
	private JTextField nbAttributsEdge;
	private JLabel labelAction, labelNbAtt;
	private JButton ok, cancel;
	private JFrame frame;
	private int nbAtt;
	private boolean ferme;
	private AttributDialog attDialog;
	private Graph graph;
	private String edgeName, action;
	private Edge oldEdge;

	@SuppressWarnings({ "static-access" })
	public ChangeEdgeDialog(JFrame f, String s, final String edgeId, Graph g) {
		super(f, s, true);
		this.setDefaultCloseOperation(this.DISPOSE_ON_CLOSE);

		edgeName = edgeId;
		graph = g;
		oldEdge = graph.getEdge(edgeName);
		action = oldEdge.getAttribute(MyJsonGenerator.FORMAT_EDGE_ACTION);

		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				ferme = true;
			}
		});

		frame = f;
		graph = g;
		Box boite = Box.createVerticalBox();
		JPanel panelDialog = new JPanel();
		panelDialog.setLayout(new GridLayout(3, 2, 20, 5));

		labelAction = new JLabel("What's the action?");
		panelDialog.add(labelAction);
		actionEdge = new JTextField(10);
		panelDialog.add(actionEdge);
		actionEdge.setText(action);

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

	public static String getAction() {
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

	public JFrame getFrame() {
		return frame;
	}

	public boolean getFerme() {
		return ferme;
	}

	public void actionPerformed(ActionEvent evt) {
		if (evt.getSource() == ok) {
			ferme = false;
			nbAtt = getNbAtt();

			if (nbAtt != 0) {
				attDialog = new AttributDialog(getFrame(), "Attributs Node",
						getNbAtt());
				if (attDialog.isExit()) {
					ferme = true;
				}
			}

			dispose();
		} else if (evt.getSource() == cancel) {
			ferme = true;
			dispose();
		}
	}
}
